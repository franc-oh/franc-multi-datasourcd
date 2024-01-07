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
    private String content_name; // TODO : ObjectMapper.convertValue 시 이름이 다른경우, 맵핑은 어떻게 해야 하나?? => @JsonSetter("[맵핑할 이름]")
    private String description;
    private LocalDateTime created;

}
