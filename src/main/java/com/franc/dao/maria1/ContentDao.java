package com.franc.dao.maria1;

import com.franc.vo.postgres.ContentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentDao {

    List<ContentVO> selectContentListToPostgreVo() throws Exception;
}
