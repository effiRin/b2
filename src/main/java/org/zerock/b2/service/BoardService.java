package org.zerock.b2.service;

import org.zerock.b2.dto.BoardDTO;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.dto.PageRequestDTO;
import org.zerock.b2.dto.PageResponseDTO;
import org.zerock.b2.entity.Board;

public interface BoardService {

    Integer register(BoardDTO boardDTO);  // 새로 등록된 게시물의 번호 반환

    BoardDTO readOne(Integer bno);

    void modify(BoardDTO boardDTO);

    void remove(Integer bno);

    PageResponseDTO<BoardListReplyCountDTO> list(PageRequestDTO pageRequestDTO);

}
