package com.mockapi.mockapi.service;

import com.mockapi.mockapi.web.dto.NewsCategoryDTO;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.NewsCategoryResponse;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;

import java.util.List;

public interface ISNewsCategoryService {
    GetListDataResponseDTO<NewsCategoryResponse> getAll();

    GetListDataResponseDTO<NewsResponse> getAllById(long id);
}
