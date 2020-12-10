package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Data;

@Data
public class Notes {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;
}
