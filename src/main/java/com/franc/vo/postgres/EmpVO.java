package com.franc.vo.postgres;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(of = "empno")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmpVO {
    private Long empno;
    private String ename;
    private LocalDateTime hiredate;
    @Builder.Default
    private Integer sal = 1000000;
    private String email;

}
