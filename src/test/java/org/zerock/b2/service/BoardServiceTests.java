package org.zerock.b2.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b2.dto.BoardDTO;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.dto.PageRequestDTO;
import org.zerock.b2.dto.PageResponseDTO;

@Log4j2
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("Test Register...")
                .content("Test Content...")
                .writer("user00")
                .build();

        Integer bno = boardService.register(dto);
        log.info("===========================");
        log.info(bno);
    }

    @Test
    public void testRead(){

    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);

    }
}
