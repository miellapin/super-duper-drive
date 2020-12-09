package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM NOTES WHERE fileId = #{fileId}")
    public Files findFile(Integer fileId);

    @Insert("INSERT INTO NOTES (fileId, filename, contenttype, filesize, userid, filedata) "
            +"VALUES(#{fileId}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer addFile(Files file);
}
