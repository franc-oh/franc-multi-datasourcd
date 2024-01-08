package com.franc.dao.maria2;

import com.franc.vo.postgres.ContentFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContentFileDao {
    List<ContentFileVO> selectContentFileListByContentToPostgreVO(@Param("content_seq") Long content_seq) throws Exception;
    List<ContentFileVO> bulkSelectContentFileListByContentToPostgreVO(List<Long> content_seq_List) throws Exception;
}
