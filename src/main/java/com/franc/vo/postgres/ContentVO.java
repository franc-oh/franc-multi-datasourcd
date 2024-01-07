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
    @JsonSetter("name")
    private String content_name;
    private String description;
    private LocalDateTime created;

}
