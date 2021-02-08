package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    User getNote(Integer userId);

    @Insert("INSERT INTO NOTES (title, description, userId) VALUES(#{title},#{description},#{username})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET title=#{title}, description =#{description} WHERE noteId =#{noteId}")
    void updateNote(Note note);

    @Delete("DELETE FROM village WHERE id =#{id}")
    void deleteNote(int noteId);
}
