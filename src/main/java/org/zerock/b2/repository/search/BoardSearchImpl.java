package org.zerock.b2.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.entity.Board;
import org.zerock.b2.entity.QBoard;
import org.zerock.b2.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;

import static org.zerock.b2.entity.QBoard.board;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public void search1(Pageable pageable){

        log.info("search1................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply) // 댓글이 없는 게시물도 있기 때문에 leftJoin
                .on(reply.board.eq(board));  // outer join 조건 주는 것

//        query.select -> 하면 리턴이 <Tuple>로 됨
        JPQLQuery<Tuple> query1 =
                query.select(board.bno, board.title, board.writer, reply.count() ); // 원하는 데이터 추출하기
        query.groupBy(board);  // board를 groupby 해보기

        getQuerydsl().applyPagination(pageable, query1);  // 페이징

        List<Tuple> tupleList = query1.fetch();

        long totalCount = query1.fetchCount();   // fetch카운트

        List<Object[]> arr =
                tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
        // <Tuple> - object 배열로 바꿔서 처리한다 ???
        //object 배열을 한번에 바꿔주고 싶어서 쓰는 것이 projection의 bean??이다. (클래스에서 DTO를 바로 뽑을 수 있음)


//        (이전)
//        QBoard qBoard = board;
//        JPQLQuery<Board> query = from(board);
//        getQuerydsl().applyPagination(pageable, query);
//        query.fetchCount();  // 카운트 값 가져오기 - 쿼리문 하나 가져오기
//        query.fetch(); // 이걸 해야 쿼리문 실행 가능 (fetch - 가져온다)

    }


    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board; // 쿼리 도메인
        JPQLQuery<Board> query = from(board);

        // types가 null이 아닐때만 체크해서 들어가기
        if(types != null){   // 배열이니까 루프 돌리기
            BooleanBuilder booleanBuilder = new BooleanBuilder(); // import 주의

            for(String type:types){
                if(type.equals("t")){
                    booleanBuilder.or(board.title.contains(keyword));
                }else if(type.equals("c")){
                    booleanBuilder.or(board.content.contains(keyword));
                }else if(type.equals("w")){
                    booleanBuilder.or(board.writer.contains(keyword));
                }
            }//end for
            query.where(booleanBuilder);
        }// end if
        query.where(board.bno.gt(0));

        //페이징 처리
        getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch(); // JPA에서 숫자 관련된 것은 Long으로 처리한다. int로 처리하지 않음. 데이터가 많을 수 있어서

        long count = query.fetchCount();

        return new PageImpl<Board>(list, pageable, count);

    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        log.info("search1................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply) // 댓글이 없는 게시물도 있기 때문에 leftJoin
                .on(reply.board.eq(board));  // outer join 조건 주는 것
        query.groupBy(board);

        JPQLQuery<BoardListReplyCountDTO> dtojpqlQuery =
                query.select(Projections.bean(BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")) );

        this.getQuerydsl().applyPagination(pageable, dtojpqlQuery);  // 페이징 쿼리를 여기에 걸어줌

        List<BoardListReplyCountDTO> list = dtojpqlQuery.fetch();

        long totalCount = dtojpqlQuery.fetchCount();    // BoardRepositoryTests에서 testSearch1 만들기

        return new PageImpl<>(list, pageable, totalCount);
    }
}
