package com.franc.dao.postgres;

import com.franc.vo.postgres.EmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestDao {
    EmpVO selectEmpById(@Param("empno") Long empno) throws Exception;
    List<EmpVO> selectEmpList() throws Exception;
}
