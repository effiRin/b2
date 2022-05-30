package org.zerock.b2.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.entity.Board;

public interface BoardSearch {

    void search1(Pageable pageable); // 파라미터가 Pageable이면 리턴 타입은 페이지 타입으로 나온다!!

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);
            // 게시물의 목록은 검색조건이 들어가므로 동적쿼리를 쓸수밖에 없다!
}
