package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Data;

@Data
public class Users {
    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;
}
