package com.mockapi.mockapi.web.dto.response.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NewsResponse {

    private long id;

    private String content;

    private boolean posted;

    private String summary;

    private String thumbnail;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date timePost;

    private String title;

    private String username;

    private String name;
}
