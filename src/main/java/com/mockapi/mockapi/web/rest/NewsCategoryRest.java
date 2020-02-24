package com.mockapi.mockapi.web.rest;

import com.mockapi.mockapi.service.ISNewsCategoryService;
import com.mockapi.mockapi.web.dto.NewsCategoryDTO;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.NewsCategoryResponse;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newCategory")
@CrossOrigin("*")
public class NewsCategoryRest {
    private static final Logger log = LoggerFactory.getLogger(NewsCategoryRest.class);

    @Autowired
    private ISNewsCategoryService newsCategoryService;

    @GetMapping(value = "/all")
    public ResponseEntity<GetListDataResponseDTO<NewsCategoryResponse>> getAll(){
        log.info("-----start request to get all NewsCategory----");
        GetListDataResponseDTO<NewsCategoryResponse> result = newsCategoryService.getAll();
        if(result== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("-----response to get all NewsCategory :{}");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<GetListDataResponseDTO<NewsResponse>> getById(@PathVariable("id")long id){
        log.info("-----start request to get getById NewsCategory----");
        GetListDataResponseDTO<NewsResponse> result = newsCategoryService.getAllById(id);
        if(result== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("-----response to get all NewsCategory :{}");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
