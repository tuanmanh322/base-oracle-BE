package com.mockapi.mockapi.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity(name = "FILE_RESPONSE")
public class FileResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "FILE_ID_SEQ")
    @SequenceGenerator(name = "FILE_ID_SEQ",sequenceName = "AUTO_INCRE_SEQ_FILE",initialValue = 1,allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME_FILE")
    private String name;

    @Column(name = "URI_FILE")
    private String uri;

    @Column(name = "TYPE_FILE")
    private String type;

    @Column(name = "SIZE_FILE")
    private long sizeFile;

    public FileResponse(String name, String uri, String type, long sizeFile) {
        this.name = name;
        this.uri = uri;
        this.type = type;
        this.sizeFile = sizeFile;
    }

    public FileResponse(List<String> name, List<String> uri) {
        this.name = String.valueOf(name);
        this.uri = String.valueOf(uri);
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", type='" + type + '\'' +
                ", sizeFile=" + sizeFile +
                '}';
    }
}
