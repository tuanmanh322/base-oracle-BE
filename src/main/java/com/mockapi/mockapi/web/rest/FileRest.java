package com.mockapi.mockapi.web.rest;

import com.mockapi.mockapi.model.FileResponse;
import com.mockapi.mockapi.service.ISStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin("*")
@RequestMapping("/file")
public class FileRest {
    private static final Logger log =LoggerFactory.getLogger(FileRest.class);


    @Autowired
    private ISStorageService isStorageService;

    @GetMapping
    @ResponseBody
    public FileResponse listAllFiles(){
       List<String> uri = isStorageService.loadAll().map(
                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(path.getFileName().toString())
                .toUriString()
        ).collect(Collectors.toList());

        List<String> name = isStorageService.loadAll().map(
                path -> path.getFileName().toString().substring(0,path.getFileName().toString().indexOf("."))
        ).collect(Collectors.toList());
       log.info("--- request get all item -----------");
        return new FileResponse(name,uri);
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename){
        Resource resource = isStorageService.loadAsResource(filename);
        log.info("---- download success----------- !!");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @PostMapping("/upload-file")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String name = isStorageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(name)
                .toUriString();

        log.info("---- successfile upload!!!");
        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    @ResponseBody
    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        log.info("----  upload multifile!!!");

        return Arrays.stream(files)
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}
