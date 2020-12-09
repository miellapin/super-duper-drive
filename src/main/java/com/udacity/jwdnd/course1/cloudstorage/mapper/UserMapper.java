package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    Users findUser(Integer userid);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) "
    +"VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int addUser(Users user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Users findUserByUsername(String username);
}
