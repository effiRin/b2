package org.zerock.b2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b2.entity.Reply;
import org.zerock.b2.repository.search.BoardSearch;

public interface ReplyRepository extends JpaRepository<Reply, Long>, BoardSearch {

    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Integer bno, Pageable pageable);// long으로 받아왔는데 Integer로 바꿔서 맞춰주기?

}
