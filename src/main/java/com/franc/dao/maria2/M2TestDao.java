package com.franc.dao.maria2;

import com.franc.vo.maria2.EmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface M2TestDao {
    EmpVO selectEmpById(@Param("empno") Long empno) throws Exception;
    List<EmpVO> selectEmpList() throws Exception;
}
