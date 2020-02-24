package com.mockapi.mockapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.Date;

@Getter
@Setter
@Log4j
public class Issues_HistoryDTO extends BaseDTO{
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateDate = new Date();

    private String comment;

    private String change;

    @Override
    public String toString() {
        return "Issues_HistoryDTO{" +
                "id=" + id +
                ", updateDate=" + updateDate +
                ", comment='" + comment + '\'' +
                ", change='" + change + '\'' +
                '}';
    }
}
