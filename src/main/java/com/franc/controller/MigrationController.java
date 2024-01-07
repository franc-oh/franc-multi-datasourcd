package com.franc.controller;

import com.franc.common.BaseResponse;
import com.franc.common.Code;
import com.franc.exception.BizException;
import com.franc.exception.ExceptionResult;
import com.franc.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/migration")
@RequiredArgsConstructor
public class MigrationController {

    private static final Logger logger = LoggerFactory.getLogger(MigrationController.class);

    private final MigrationService migrationService;

    /**
     * 데이터 마이그레이션 (DB 이관)
     * @return response
     * @throws Exception
     */
    @PostMapping("/db")
    public ResponseEntity<BaseResponse> get_content_file() throws Exception {
        BaseResponse response = new BaseResponse();

        logger.info("데이터 마이그레이션(DB)_Request => {}", new Object());

        Map<String, Object> result = migrationService.dataMigrationFromDb();
        if(result.isEmpty())
            throw new BizException(ExceptionResult.DATA_NOT_FOUND);

        // #2. 응답처리
        response.setData(result);
        response.setCode(Code.RESPONSE_CODE_SUCCESS);
        response.setMessage(Code.RESPONSE_MESSAGE_SUCCESS);

        logger.info("데이터 마이그레이션(DB)_Response => {}", response.toString());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
