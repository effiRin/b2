package org.zerock.b2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    @NotEmpty
    @Size(min =3, max= 100)
    private Integer bno;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
