package org.zerock.b2.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.dto.BoardListWithImageDTO;
import org.zerock.b2.entity.Board;
import org.zerock.b2.entity.QBoard;
import org.zerock.b2.entity.QBoardImage;
import org.zerock.b2.entity.QReply;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public void search1(Pageable pageable) {

        log.info("search1...................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));


        JPQLQuery<Tuple> query1 = query.select(board.bno, board.title, board.writer, reply.count() );
        query1.groupBy(board);

        getQuerydsl().applyPagination(pageable,query1);

        List<Tuple> tupleList = query1.fetch();

        long totalCount = query1.fetchCount();

        List<Object[]> arr =
                tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());



    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if(types != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type:types) {
                if(type.equals("t")){
                    booleanBuilder.or(board.title.contains(keyword));
                }else if(type.equals("c")){
                    booleanBuilder.or(board.content.contains(keyword));
                }else if(type.equals("w")){
                    booleanBuilder.or(board.writer.contains(keyword));
                }
            }//end for
            query.where(booleanBuilder);
        }//end if
        query.where(board.bno.gt(0));

        //페이징 처리
        getQuerydsl().applyPagination(pageable,query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));
        query.groupBy(board);

        JPQLQuery<BoardListReplyCountDTO> dtojpqlQuery =
                query.select(Projections.bean(BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")) );

        this.getQuerydsl().applyPagination(pageable, dtojpqlQuery);

        List<BoardListReplyCountDTO> list = dtojpqlQuery.fetch();

        long totalCount = dtojpqlQuery.fetchCount();


        return new PageImpl<>(list,pageable,totalCount);
    }

    @Override
    public Page<BoardListWithImageDTO> searchWithImage(String[] types, String keyword, Pageable pageable) {

        log.info("==================================");

        log.info("==================================");

        QBoard board = QBoard.board;
        QBoardImage boardImage = QBoardImage.boardImage;
        QReply reply =QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(board.boardImages, boardImage);
        query.leftJoin(reply).on(reply.board.eq(board));

        //query.where(boardImage.ord.eq(0));

        if(types != null && keyword != null){

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            Arrays.stream(types).forEach(t -> {

                if(t.equals("t")){
                    booleanBuilder.or(board.title.contains(keyword));
                }else if(t.equals("w")){
                    booleanBuilder.or(board.writer.contains(keyword));
                }else if(t.equals("c")){
                    booleanBuilder.or(board.content.contains(keyword));
                }

            });
            query.where(booleanBuilder);

        }//end if

        query.where(board.bno.gt(0));
        query.where(boardImage.ord.goe(0));

        query.groupBy(board);

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<BoardListWithImageDTO> tupleJPQLQuery
                = query.select(
                        Projections.bean(BoardListWithImageDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        boardImage.fileLink.as("imgPath"),
                        reply.countDistinct().as("replyCount")
                        )
                );

        List<BoardListWithImageDTO> dtoList = tupleJPQLQuery.fetch();

        long totalCount = tupleJPQLQuery.fetchCount();



        // 2번 게시물에 속한 모든 이미지를 다 가져오는 경우
        //group by
        //        query.groupBy(board);
        //
        //        this.getQuerydsl().applyPagination(pageable,query);
        //
        //        List<Board> list = query.fetch();
        //
        //        list.forEach(board1 -> {
        //            log.info(board1.getBno() +":" + board1.getTitle());
        //            log.info(board1.getBoardImages());
        //        });
        //==================================================================
        log.info("==================================");

        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}






