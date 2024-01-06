package com.franc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionResult {

    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "데이터가 없습니다."),

    PARAMETER_NOT_VALID(HttpStatus.BAD_REQUEST, "잘못된 요청 데이터입니다."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. <br/>고객센터(1588-9999)로 문의주세요.");

    private final HttpStatus code;
    private final String message;
}
