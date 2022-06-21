package org.effirin.b2.service;

import org.effirin.b2.dto.PageRequestDTO;
import org.effirin.b2.dto.PageResponseDTO;
import org.effirin.b2.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Integer bno, PageRequestDTO pageRequestDTO);

}
