package org.effirin.b2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.effirin.b2.dto.BoardListReplyCountDTO;
import org.effirin.b2.dto.BoardListWithImageDTO;
import org.effirin.b2.entity.Board;
import org.effirin.b2.entity.BoardImage;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository repository;

    @Transactional
    @Test
    public void testWithImage(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<BoardListWithImageDTO> result = repository.searchWithImage(null,null, pageable);

        result.getContent().forEach(boardListWithImageDTO -> {
            log.info(boardListWithImageDTO);
        });
    }

    @Transactional
    @Commit
    @Test
    public void testUpdateImage() {

        Board board = repository.getById(20);

        board.changeTitle("제목 수정...20");

        board.clearImages();

        for (int i = 0; i < 3; i++) {

            BoardImage boardImage = BoardImage.builder()
                    .fileLink("aaa"+i+"jpg")
                    .build();
            board.addImage(boardImage);
        }

        repository.save(board);

    }



    @Transactional
    @Test
    public void testWithQuery() {

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Board> result = repository.getList1(pageable);

        result.getContent().forEach(board -> {
            log.info(board);
            log.info(board.getBoardImages());
            log.info("=====================");
        });

    }

    @Test
    public void testEager() {

        Integer bno = 124;

        Optional<Board> result = repository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);

    }


    @Transactional
    @Test
    public void testPageImage(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = repository.findAll(pageable);

        result.getContent().forEach(board -> {
            log.info(board);
            log.info(board.getBoardImages());
            log.info("================");
        });
    }


    @Test
    public void testInsertWithImage() {

        for (int i = 0; i < 20; i++) {

            Board board = Board.builder()
                    .title("fileTest.." +i)
                    .content("fileTest")
                    .writer("user" + (i % 10))
                    .build();

            for (int j = 0; j < 2; j++) {
                BoardImage boardImage = BoardImage.builder()
                        .fileLink(i+"aaa.jpg")
                        .build();
                board.addImage(boardImage);
            }//image

            repository.save(board);
        }

    }


    @Test
    public void testSearchAll() {

        String[] types = new String[]{"t","c"};
        String keyword = "5";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Board> result = repository.searchAll(types,keyword, pageable);

        log.info(result);

    }

    @Test
    public void testSearch1() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        repository.search1(pageable);

    }


    @Test
    public void testInsesrt(){

        log.info("---------------------");
        log.info("---------------------");
        log.info(repository);
        log.info("---------------------");

        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title... "+i)
                    .content("Content...."+i)
                    .writer("user"+ (i %10))
                    .build();

            repository.save(board);
        });

    }

    @Test
    public void testRead(){

        Integer bno = 100;

        Optional<Board> result = repository.findById(bno);

        Board board = result.get();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Integer bno = 100;

        Optional<Board> result = repository.findById(bno);

        Board board = result.get();
        board.changeTitle("100title..updated");
        board.changeContent("100Content...updated");

        repository.save(board);

        log.info(board);
    }

    @Test
    public void testDelete() {

        //없는 번호 삭제
        Integer bno = 100;

        repository.deleteById(bno);

    }
    @Test
    public void testPage1() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = repository.findAll(pageable);

        log.info("TOTAL " +result.getTotalElements());
        log.info("TOTALPAGES: " + result.getTotalPages());
        log.info("CURRENT: " + result.getNumber());
        log.info("SIZE: " + result.getSize());

        result.getContent().forEach(board -> log.info(board));


    }

    @Test
    public void testQueryMethod1(){

        String keyword = "5";

        List<Board> list = repository.findByTitleContaining(keyword);

        log.info(list);

    }

    @Test
    public void testQueryMethod2(){

        String keyword = "5";

        Pageable pageable = PageRequest.of(0,5, Sort.by("bno").descending());

        Page<Board> list = repository.findByTitleContaining(keyword, pageable);

        log.info(list);

    }

    @Test
    public void testWithReplyCount() {

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result

                = repository.searchWithReplyCount(null,null, pageable);

    }

}
