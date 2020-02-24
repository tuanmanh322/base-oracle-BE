package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.web.dto.request.SearchNewsRequest;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;
import org.springframework.data.domain.Page;

public interface NewsDao {
     Page<NewsResponse> getAll(SearchNewsRequest request);

    Page<NewsResponse> getAllByAd(SearchNewsRequest request);
}
