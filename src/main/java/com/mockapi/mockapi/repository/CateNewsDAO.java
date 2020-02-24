package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;

import java.util.List;

public interface CateNewsDAO {
    List<NewsResponse> getAllById(Long id);
}
