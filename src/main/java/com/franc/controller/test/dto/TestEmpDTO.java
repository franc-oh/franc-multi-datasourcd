package com.franc.controller.test.dto;

import com.franc.common.BaseResponse;
import com.franc.vo.postgres.EmpVO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEmpDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Request {
        @NotNull
        @Min(1)
        private Long empno;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    @ToString
    public static class Response extends BaseResponse {
        private Map<String, Object> data;
    }
}
