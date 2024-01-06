package com.franc.dao.maria1;

import com.franc.vo.maria1.EmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface M1TestDao {
    EmpVO selectEmpById(@Param("empno") Long empno) throws Exception;
    List<EmpVO> selectEmpList() throws Exception;
}
