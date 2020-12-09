package com.udacity.jwdnd.course1.cloudstorage.entity;

import java.io.InputStream;

public class Files {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private InputStream filedata;

    public int getFileId() {
        return fileId;
    }

    public String getFilename() {
        return filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public int getUserid() {
        return userid;
    }

    public InputStream getFiledata() {
        return filedata;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setFiledata(InputStream filedata) {
        this.filedata = filedata;
    }
}
