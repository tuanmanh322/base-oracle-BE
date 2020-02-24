package com.mockapi.mockapi.service.impl;

import com.mockapi.mockapi.config.StorageProperties;
import com.mockapi.mockapi.exception.FileNotFoundException;
import com.mockapi.mockapi.exception.StorageException;
import com.mockapi.mockapi.service.ISStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@Transactional
public class SStorageServiceImpl implements ISStorageService {
    private static final Logger log = LoggerFactory.getLogger(SStorageServiceImpl.class);

    private final Path rootLocation;

    @Autowired
    public SStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    /*
        @PostConstruct phương thức chỉ khởi tạo khi bean được hoàn thành

     */
    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException ex) {
            throw new StorageException("Không thể khởi tạo vị trí lưu trữ", ex);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Không thể lưu trữ tệp trống " + fileName);
            }
            if (file.getOriginalFilename().contains("..")) {
                // kiểm tra bảo mật
                throw new StorageException("Không thể lưu trữ tệp với đường dẫn tương đối bên ngoài thư mục hiện tại " + fileName);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            }
        } catch (IOException e) {
            throw new StorageException("Không thể lưu trữ tệp " + fileName, e);
        }
        return fileName;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation,1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation :: relativize);
        }catch (IOException e){
            throw new StorageException("Không thể đọc các tập tin được lưu trữ",e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Không thể đọc tập tin: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Không thể đọc tập tin: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
