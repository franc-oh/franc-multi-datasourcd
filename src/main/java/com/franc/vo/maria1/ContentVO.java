package com.franc.vo.maria1;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(of = "content_seq")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContentVO {
    private Long content_seq;
    private String name;
    private String description;
    private LocalDateTime created;

}
