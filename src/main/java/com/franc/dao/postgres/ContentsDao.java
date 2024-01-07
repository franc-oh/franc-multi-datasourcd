package com.franc.dao.postgres;

import com.franc.vo.postgres.ContentFileVO;
import com.franc.vo.postgres.ContentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentsDao {
    ContentVO selectContentById(@Param("content_seq") Long content_seq) throws Exception;

    void insertContent(ContentVO vo) throws Exception;
    void insertContentFile(ContentFileVO vo) throws Exception;
}
