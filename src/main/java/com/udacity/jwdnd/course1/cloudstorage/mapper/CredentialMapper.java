package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credentials findCredential(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) "
            +"VALUES(#{credentialid}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer addCredential(Credentials credential);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credentials> getAllCredentials();

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(Integer credentialid);
}
