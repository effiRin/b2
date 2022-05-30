package org.zerock.b2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {   // entity는 소중함. DTO는 쓰고 버리는 존재

    private Integer bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private long replyCount; // JPA는 결과가 long으로 나오므로 얘도 long으로 나오는게 깔끔

}
