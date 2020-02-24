package com.mockapi.mockapi.web.rest;

import com.mockapi.mockapi.model.Employee;
import com.mockapi.mockapi.repository.EmployeeRepo;
import com.mockapi.mockapi.service.ISEmployeeService;
import com.mockapi.mockapi.util.Constants;
import com.mockapi.mockapi.web.dto.EmployeeDTO;
import com.mockapi.mockapi.web.dto.request.EmployeeEditRequest;
import com.mockapi.mockapi.web.dto.request.EmployeeRequest;
import com.mockapi.mockapi.web.dto.request.SearchEmployeeRequest;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.SearchRequestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
@Slf4j
public class EmployeeRest {
    @Autowired
    private ISEmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping(value = "/add")
    public ResponseEntity<GetSingleDataResponseDTO<EmployeeDTO>> addEmp(@Valid @RequestBody EmployeeRequest employeeRequest) throws IOException {
//        , @RequestParam("image")MultipartFile file
        log.info("--request to add new Employee: {} ");
        //employeeRequest.setImage(compressBytes(file.getBytes()));
        GetSingleDataResponseDTO<EmployeeDTO> emp = employeeService.add(employeeRequest);
        if (emp == null) {
            log.error("Faile to add employee :{}", emp);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("---Rest response of add new employee: {} " + emp.getMessage());
        return new ResponseEntity<>(emp, HttpStatus.OK);

    }

    @PostMapping(value = "/getAll")
    public ResponseEntity<GetListDataResponseDTO<SearchRequestResponse>> getAll(@RequestBody SearchEmployeeRequest request) {
        log.info("---Rest request getAll page--");
        GetListDataResponseDTO<SearchRequestResponse> data = employeeService.All(request);
        if (data == null) {
            log.error("can't get all employee ");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("---Rest Response getAll Employee----");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/getAll-by-params")
//    @RolesAllowed({Constants.ROLE_HR,Constants.ROLE_MANAGER})
    public ResponseEntity<GetListDataResponseDTO<SearchRequestResponse>> getAllByParams(@RequestBody SearchEmployeeRequest request) {
        log.info("---Rest request getAll by params--");
        GetListDataResponseDTO<SearchRequestResponse> data = employeeService.AllByParams(request);
        if (data == null) {
            log.error("can't get all employee ");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("---Rest Response getAll Employee----");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GetSingleDataResponseDTO<EmployeeDTO>> getById(@PathVariable("id") Long id) {
        log.info("fetch Employee with id : {}", id);
        GetSingleDataResponseDTO<EmployeeDTO> resp = employeeService.findById(id);
        if (resp == null) {
            log.error("can't find Employee with id :{}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/allrp")
    public ResponseEntity<GetListDataResponseDTO<EmployeeDTO>> getAll() {
        log.info("--request to GET All Employee: {} ");
        GetListDataResponseDTO<EmployeeDTO> resp = employeeService.getAll();
        if (resp == null) {
            log.error("can't get all employee :{}");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("--- response to getAll Employee : {}");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetSingleDataResponseDTO<EmployeeEditRequest>> update(@RequestPart("info") EmployeeEditRequest requestDTO,@RequestPart(value = "multipartImage", required = false) MultipartFile multipartImage)throws Exception {
        log.info("--- Rest request to update employee {}: " + requestDTO.toString());
        if(null != multipartImage){
            String fileName = multipartImage.getOriginalFilename();
            log.info("Saving uploaded image: " + fileName);
            GetSingleDataResponseDTO<EmployeeEditRequest> result = employeeService.update(requestDTO,multipartImage);
            log.info("Rest response of update employee {}: " + result.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else{
            GetSingleDataResponseDTO<EmployeeEditRequest> result = employeeService.update(requestDTO);
            log.info("Rest response of update employee {}: " + result.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("--request delete id {}");
        employeeService.delete(id);
//        Employee employee = employeeRepo.findById(id).get();
//        if(employee!=null){
//            try{
//                log.info("----re delete----");
//                employeeRepo.delete(employee);
//                return ResponseEntity.ok().build();
//            }catch (Exception ex){
//                log.error(ex.getMessage(),ex);
//            }
//            log.info("----deleted!----");
//        }
        log.info("--success delete id {}");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/verify-account/{token}")
    public ResponseEntity<?> verifyAcc(@PathVariable String token) {
        employeeService.activateAccount(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> checkAccount(@PathVariable("username") String username) {
        GetSingleDataResponseDTO<Employee> result = employeeService.checkUsername(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pw/{email}")
    public ResponseEntity forgotPW(@PathVariable("email") String email) {
        employeeService.forgotPW(email);
        return ResponseEntity.ok().build();
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
