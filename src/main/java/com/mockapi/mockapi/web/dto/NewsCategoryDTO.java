package com.mockapi.mockapi.web.dto;

import com.mockapi.mockapi.model.News;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewsCategoryDTO extends BaseDTO{
    private long id;

    private String name;

    private List<NewsDTO> news;
}
