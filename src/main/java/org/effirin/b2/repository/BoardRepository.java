package org.effirin.b2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.effirin.b2.entity.Board;
import org.effirin.b2.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardSearch {

    List<Board> findByTitleContaining(String keyword);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);


    @EntityGraph(attributePaths = "boardImages")
    @Query("select b from Board b")
    Page<Board> getList1(Pageable pageable);

}
