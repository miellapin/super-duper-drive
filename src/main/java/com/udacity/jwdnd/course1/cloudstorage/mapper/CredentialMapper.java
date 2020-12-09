package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface CredentialMapper {

    @Select("SELECT * FROM NOTES WHERE credentialid = #{credentialid}")
    public Credentials findCredential(Integer credentialid);

    @Insert("INSERT INTO NOTES (credentialid, url, username, key, password, userid) "
            +"VALUES(#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public Integer addCredential(Credentials credential);
}
