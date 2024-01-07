package com.franc.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {
    private String code;
    private String message;
    private Map<String, Object> data;
}
