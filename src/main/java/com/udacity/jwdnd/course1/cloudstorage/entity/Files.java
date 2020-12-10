package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Data;

import java.io.InputStream;

@Data
public class Files {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private InputStream filedata;
}
