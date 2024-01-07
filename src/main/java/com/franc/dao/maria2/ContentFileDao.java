package com.franc.dao.maria2;

import com.franc.vo.maria2.ContentFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContentFileDao {
    ContentFileVO selectContentFileById(@Param("file_seq") Long file_Seq) throws Exception;
    List<ContentFileVO> selectContentFileListByContent(@Param("content_seq") Long content_seq) throws Exception;
}
