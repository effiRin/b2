package org.zerock.b2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.entity.Board;

import java.util.Optional;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository repository;

    @Test
    public void testSearchAll() {
        String[] types = new String[]{"t", "c"};
        String keyword = "5";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());  // 페이지 0부터 시작
        Page<Board> result = repository.searchAll(types, keyword, pageable);

        log.info(result);
    }

    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());  // 페이지 0부터 시작
        repository.search1(pageable);    // 테스트 실행하면 limit가 걸려있다
    }

    @Test
    public void testInsert() {
        log.info("---------------------");
        log.info("---------------------");
        log.info(repository);
        log.info("---------------------");

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            repository.save(board);
        });
    }

    @Test
    public void testRead() {

        Integer bno = 100;

        Optional<Board> result = repository.findById(bno);

        Board board = result.orElseThrow();
        // orElseThrow - 문제가 있으면 예외를 발생시켜라

        log.info(board);
    }

    @Test
    public void testUpdate() {

        Integer bno = 100;

        Optional<Board> result = repository.findById(bno);

        Board board = result.get();
        board.changeTitle("100Title...update");
        board.changeContent("100Content...update");
        repository.save(board);
        log.info(board);
    }

    @Test
    public void testDelete() {
        Integer bno = 100;
        repository.deleteById(bno);
    }

    //
//        Board board = Board.builder()
//                .bno(100)
//                .title("100 Title")
//                .content("100 content")
//                .writer("user11")
//                .build();
//
//        repository.save(board);


    //        Integer bno = 100;
//
//        Optional<Board> result = repository.findById(bno);
//
//        Board board = result.orElseThrow();
//        // orElseThrow - 문제가 있으면 예외를 발생시켜라
//
//        log.info(board);
    @Test
    public void testPage1() {  // 페이지 번호는 0부터 기억한다! 명심!
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        Page<Board> result = repository.findAll(pageable);  // 뒤에 데이터가 더 있다고 생각하면 countquery가 날라간다.
        log.info("TOTAL" + result.getTotalElements());
        log.info("TOTALPAGES" + result.getTotalPages());
        log.info("CURRENT: " + result.getNumber());
        log.info("SIZE: " + result.getSize());

        result.getContent().forEach(board -> log.info(board));
    }


    @Test
    public void testQueryMethod1() {
        String keyword = "5";
        List<Board> list = repository.findByTitleContaining(keyword);
        log.info(list);
    }

    @Test
    public void testQueryMethod2() {
        String keyword = "5";
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
        Page<Board> list = repository.findByTitleContaining(keyword, pageable);
        log.info(list);

    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<BoardListReplyCountDTO> result
                = repository.searchWithReplyCount(null, null, pageable);
        // 검색조건은 일단 null로 하고 데이터 나오는지 확인


    }

}