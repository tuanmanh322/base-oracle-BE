package com.mockapi.mockapi.web.rest.auth;

import com.mockapi.mockapi.config.jwt1.TokenUtils;
import com.mockapi.mockapi.model.EmployeeToken;
import com.mockapi.mockapi.service.ISEmployeeService;
import com.mockapi.mockapi.service.impl.UserCustomDetail;
import com.mockapi.mockapi.web.dto.EmployeeDTO;
import com.mockapi.mockapi.web.dto.request.LoginRequest;
import com.mockapi.mockapi.web.dto.request.PasswordChangerRequest;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthRest {
    @Autowired
    private TokenUtils tokenUtils;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserCustomDetail userCustomDetail;

    @Autowired
    private ISEmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationEmp(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userCustomDetail.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<EmployeeToken> refreshAuthenticationToken(HttpServletRequest request) {
        return new ResponseEntity<>(userCustomDetail.refreshAuthenticationToken(request), HttpStatus.OK);
    }


    @PostMapping("/change-password/{username}")
    public ResponseEntity changePassword(@Valid @RequestBody PasswordChangerRequest passwordChanger,
                                         @PathVariable("username") String username
                                         ) {
        userCustomDetail.ChangePass(passwordChanger.getOldPassword(), passwordChanger.getNewPassword(),username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<GetSingleDataResponseDTO<EmployeeDTO>> updateByAdmin(@Valid @RequestBody EmployeeDTO requestDTO) {
        log.info("--- Rest request to update employee {}: " + requestDTO.toString());
        GetSingleDataResponseDTO<EmployeeDTO> result = employeeService.updateByAd(requestDTO);
        log.info("Rest response of update employee {}: " + result.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<GetSingleDataResponseDTO<EmployeeDTO>> add(@Valid @RequestBody EmployeeDTO dto) {
        log.info("---Rest request to add employee by Admin---");
        GetSingleDataResponseDTO<EmployeeDTO> result = employeeService.addByAd(dto);
        log.info("---Rest response of add employee by Admin {}: " + result.getMessage());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
