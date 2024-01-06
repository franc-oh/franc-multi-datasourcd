package com.franc.controller.test;

import com.franc.common.BaseResponse;
import com.franc.common.Code;
import com.franc.controller.test.dto.TestEmpDTO;
import com.franc.exception.BizException;
import com.franc.exception.ExceptionResult;
import com.franc.service.TestService;
import com.franc.vo.postgres.EmpVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final TestService testService;



    @GetMapping("/emp")
    public ResponseEntity<BaseResponse> empList() throws Exception {
        TestEmpDTO.Response response = new TestEmpDTO.Response();

        Map<String, Object> result = testService.getMultiDatabaseDatas();
        if(result.isEmpty())
            throw new BizException(ExceptionResult.DATA_NOT_FOUND);

        // #2. 응답처리
        response.setData(result);
        response.setCode(Code.RESPONSE_CODE_SUCCESS);
        response.setMessage(Code.RESPONSE_MESSAGE_SUCCESS);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
