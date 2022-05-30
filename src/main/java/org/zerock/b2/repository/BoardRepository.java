package org.zerock.b2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b2.entity.Board;
import org.zerock.b2.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardSearch { // 두가지 타입을 준다. entity 타입과 pk 타입

    List<Board> findByTitleContaining(String keyword);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
