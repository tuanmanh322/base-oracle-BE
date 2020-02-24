package com.mockapi.mockapi.service;

import com.mockapi.mockapi.web.dto.IssueDTO;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;

public interface ISIssuesService {
    GetListDataResponseDTO<IssueDTO> getAll();


}
