package com.franc.vo.postgres;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(of = "content_seq")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentVO {
    private Long content_seq;
    private String content_name;
    private String description;
    private Boolean is_valid;
    private LocalDateTime created;
}
