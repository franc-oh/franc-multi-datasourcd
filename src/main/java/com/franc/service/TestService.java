package com.franc.service;

import com.franc.dao.maria1.M1TestDao;
import com.franc.dao.maria2.M2TestDao;
import com.franc.vo.postgres.EmpVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private final com.franc.dao.postgres.TestDao postgresTestDao;
    private final M1TestDao maria1M1TestDao;
    private final M2TestDao maria2M2TestDao;


    public Map<String, Object> getMultiDatabaseDatas() throws Exception {
        Map<String, Object> result = new HashMap<>();

        // #1. postgresDataList
        List<EmpVO> postgresList = postgresTestDao.selectEmpList();
        result.put("postgres_list", postgresList);

        // #2. maria1DataInfo
        com.franc.vo.maria1.EmpVO maria1Info = maria1M1TestDao.selectEmpById(1l);
        result.put("maria1_info", maria1Info);

        com.franc.vo.maria2.EmpVO maria2Info = maria2M2TestDao.selectEmpById(1L);
        result.put("maria2_info", maria2Info);


        return result;
    }

}
