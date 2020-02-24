package com.mockapi.mockapi.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class NewsRequest {
    private long id;

    @NotNull
    private String content;
    @NotNull
    private String summary;

    private String thumbnail;

    @NotNull
    private String title;
    @NotNull
    private String newCategory;
}
