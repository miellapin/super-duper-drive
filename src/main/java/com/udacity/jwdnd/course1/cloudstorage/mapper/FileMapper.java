package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files findFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) "
            +"VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer addFiles(Files files);

    @Select("SELECT * FROM FILES")
    List<Files> findAllFiles();

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(Integer fileId);
}
