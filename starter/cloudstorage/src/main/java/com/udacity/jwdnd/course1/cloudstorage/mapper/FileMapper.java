package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getAllFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileId(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileName = #{filename} AND userid = #{userId}")
    File getFile(String filename, Integer userId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData)" +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Update("UPDATE FILES set fileName = #{fileName}, contentType = #{contenttype}, fileSize = #{filesize}, " +
            "userId = #{userId}, fileData = #{filedata} where fileId = #{fileId}")
    Integer update(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(Integer fileId);


}

