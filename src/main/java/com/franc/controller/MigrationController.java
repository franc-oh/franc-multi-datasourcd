package com.franc.controller;

import com.franc.common.BaseResponse;
import com.franc.common.Code;
import com.franc.exception.BizException;
import com.franc.exception.ExceptionResult;
import com.franc.service.MigrationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<BaseResponse> migration_from_db() throws Exception {
        BaseResponse response = new BaseResponse();

        logger.info("데이터 마이그레이션(DB)_Request => {}", new Object());

        // #1. 이관
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


    /**
     * 데이터 마이그레이션 (Excel 이관)
     * @param file
     * @return response
     * @throws Exception
     */
    @PostMapping(value = "/excel", consumes = {"multipart/form-data"})
    public ResponseEntity<BaseResponse> migration_from_excel(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        BaseResponse response = new BaseResponse();

        //if(file.isEmpty())
            //throw new BizException(ExceptionResult.PARAMETER_NOT_VALID);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        logger.info("데이터 마이그레이션(Excel)_Request => {}", file.getOriginalFilename());

        // #1. 엑셀파일 이관하기
        Map<String, Object> result = migrationService.dataMigrationFromExcel(file);
        if(result.isEmpty())
            throw new BizException(ExceptionResult.DATA_NOT_FOUND);

        // #2. 응답처리
        response.setData(result);
        response.setCode(Code.RESPONSE_CODE_SUCCESS);
        response.setMessage(Code.RESPONSE_MESSAGE_SUCCESS);

        logger.info("데이터 마이그레이션(Excel)_Response => {}", response.toString());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
