package com.mockapi.mockapi.web.dto;

import com.mockapi.mockapi.model.Employee;
import com.mockapi.mockapi.model.NewsCategory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class NewsDTO{
    private long id;

    @NotNull
    private String thumbnail;

    @NotNull
    private String title;

    private boolean posted;

    @NotNull
    private Date timePost;

    @NotNull
    private String summary;

    @NotNull
    private String content;

    private String username;

    private String newsCategoryName;

}
