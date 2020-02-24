package com.mockapi.mockapi.service;

import com.mockapi.mockapi.model.Employee;
import com.mockapi.mockapi.web.dto.EmployeeDTO;
import com.mockapi.mockapi.web.dto.request.EmployeeEditRequest;
import com.mockapi.mockapi.web.dto.request.EmployeeRequest;
import com.mockapi.mockapi.web.dto.request.LoginRequest;
import com.mockapi.mockapi.web.dto.request.SearchEmployeeRequest;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.SearchRequestResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ISEmployeeService {

    GetSingleDataResponseDTO<EmployeeDTO> add(EmployeeRequest employeeRequest);

    GetSingleDataResponseDTO<EmployeeDTO> addByAd(EmployeeDTO dto);
    boolean findByUsername(String  username);

    GetSingleDataResponseDTO<EmployeeDTO> findById(Long id);


    GetListDataResponseDTO<EmployeeDTO> getAll();

    boolean resetPW();

    void activateAccount(String token);


    void delete(Long id);

    GetListDataResponseDTO<SearchRequestResponse> All(SearchEmployeeRequest request);

    GetListDataResponseDTO<SearchRequestResponse> AllByParams(SearchEmployeeRequest request);

    GetSingleDataResponseDTO<EmployeeEditRequest> update(EmployeeEditRequest dto, MultipartFile multipartImage);

    GetSingleDataResponseDTO<EmployeeEditRequest> update(EmployeeEditRequest dto);

    GetSingleDataResponseDTO<EmployeeDTO> updateByAd(EmployeeDTO dto);

    GetSingleDataResponseDTO<Employee> checkUsername(String username);

    void forgotPW(String email);
}
