package com.franc.dao.maria1;

import com.franc.vo.maria1.ContentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContentDao {
    ContentVO selectContentById(@Param("content_seq") Long content_seq) throws Exception;
    List<ContentVO> selectContentList() throws Exception;
}
