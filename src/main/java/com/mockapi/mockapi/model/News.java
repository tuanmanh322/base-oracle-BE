package com.mockapi.mockapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "NEWS")
@Table
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "NEWS_ID_SEQ")
    @SequenceGenerator(name = "NEWS_ID_SEQ",sequenceName = "AUTO_INCRE_SEQ_NEWS",initialValue = 1,allocationSize = 1)
    @Column(name = "ID")
    private long id;


    @Column(name = "THUMBNAIL")
    private String thumbnail;

    @Lob
    @Column(name = "TITLE")
    private String title;


    @Column(name = "POSTED")
    private boolean posted;

    @Column(name = "TIME_POST")
    private Date timePost;

    @Lob
    @Column(name = "SUMMARY")
    private String summary;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    @JsonBackReference(value = "news-employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "NEWSCATEGORY_ID")
    @JsonBackReference(value = "new-cate")
    private NewsCategory newsCategory;
}
