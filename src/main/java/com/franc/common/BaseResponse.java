package com.franc.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {
    private String code;
    private String message;
}
