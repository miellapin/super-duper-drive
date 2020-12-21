package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import org.apache.ibatis.annotations.*;
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

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    void update(Notes note);
}
