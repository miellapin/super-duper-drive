package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes findNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) "
            +"VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer addNote(Notes note);

    @Select("SELECT * FROM NOTES")
    List<Notes> findAllNotes();
}
