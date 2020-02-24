package com.mockapi.mockapi.service;

import com.mockapi.mockapi.web.dto.NewsDTO;
import com.mockapi.mockapi.web.dto.request.NewsRequest;
import com.mockapi.mockapi.web.dto.request.SearchNewsRequest;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;

public interface ISNewsService {
    GetListDataResponseDTO<NewsResponse> getAllNews(SearchNewsRequest request);

    GetListDataResponseDTO<NewsResponse> getAllNewsByAd(SearchNewsRequest request);

    GetSingleDataResponseDTO<NewsDTO> delete(Long id);

    GetSingleDataResponseDTO<NewsDTO> update(NewsDTO newsDTO);

    GetSingleDataResponseDTO<NewsDTO> add(NewsRequest newsRequest,Long id);

    GetSingleDataResponseDTO<NewsDTO> getById(Long id);

    void activeNews(Long id);
}
