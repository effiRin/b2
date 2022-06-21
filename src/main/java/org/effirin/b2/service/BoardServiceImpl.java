package org.effirin.b2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.effirin.b2.dto.*;
import org.effirin.b2.entity.Board;
import org.effirin.b2.repository.BoardRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;


    @Override
    public Integer register(BoardDTO boardDTO) {

        //
        Board board = dtoToEntity(boardDTO);

        log.info("----------------------------");
        log.info(board);
        log.info(board.getBoardImages());
        Integer bno = boardRepository.save(board).getBno();

        return bno;

    }

    @Override
    public BoardDTO readOne(Integer bno) {

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;

    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
    }

    @Override
    public void remove(Integer bno) {

        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardListWithImageDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListWithImageDTO> result =
                boardRepository.searchWithImage(types, keyword, pageable);

        return PageResponseDTO.<BoardListWithImageDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();

    }
}


