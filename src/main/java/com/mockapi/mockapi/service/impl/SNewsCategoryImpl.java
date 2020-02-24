package com.mockapi.mockapi.service.impl;


import com.mockapi.mockapi.model.News;
import com.mockapi.mockapi.model.NewsCategory;
import com.mockapi.mockapi.repository.CateNewsDAO;
import com.mockapi.mockapi.repository.NewsCategoryRepo;
import com.mockapi.mockapi.repository.NewsRepo;
import com.mockapi.mockapi.service.ISNewsCategoryService;
import com.mockapi.mockapi.web.dto.NewsCategoryDTO;
import com.mockapi.mockapi.web.dto.NewsDTO;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.NewsCategoryResponse;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SNewsCategoryImpl implements ISNewsCategoryService {
    private  static final Logger log = LoggerFactory.getLogger(SNewsCategoryImpl.class);

    @Autowired
    private NewsCategoryRepo newsCategoryRepo;

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private CateNewsDAO cateNewsDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GetListDataResponseDTO<NewsCategoryResponse> getAll() {
        GetListDataResponseDTO<NewsCategoryResponse> result = new GetListDataResponseDTO<>();
        List<NewsCategoryResponse> responseList = new ArrayList<>();
        List<NewsCategory> newsCategory = newsCategoryRepo.findAll();
        try{
            newsCategory.stream().map(re -> {
                NewsCategoryResponse response = modelMapper.map(re,NewsCategoryResponse.class);
                responseList.add(response);
                result.setValue(responseList);
                return result;
            }).collect(Collectors.toList());
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            result.value(null);
            result.setMessage("Not have record!");
        }
        log.info("--response SNeweCategoryImpl!!!,{}");
        return result;
    }

    @Override
    public GetListDataResponseDTO<NewsResponse> getAllById(long id) {
        GetListDataResponseDTO<NewsResponse> result = new GetListDataResponseDTO<>();
        try{
            List<NewsResponse> newsCategoryDTOS = cateNewsDAO.getAllById(id);
            result.setValue(newsCategoryDTOS);
            return result;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            result.setMessage("Not have record!");
            result.setValue(null);
        }
        return result;
    }
}
