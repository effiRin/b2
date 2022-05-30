package org.zerock.b2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b2.dto.BoardDTO;
import org.zerock.b2.dto.BoardListReplyCountDTO;
import org.zerock.b2.dto.PageRequestDTO;
import org.zerock.b2.dto.PageResponseDTO;
import org.zerock.b2.entity.Board;
import org.zerock.b2.repository.BoardRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor  // 필요한 것들은 생성자 통해서 주입받도록
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository; // 반드시 final

    @Override
    public Integer register(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class);
        log.info("register..." + board);

        Board result = boardRepository.save(board);  // DB와 동기화되어서 값을 뽑아올 수 있다?

        return result.getBno();
    }

    @Override
    public BoardDTO readOne(Integer bno){
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
    public PageResponseDTO<BoardListReplyCountDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

//        List<BoardDTO> dtoList = result.getContent().stream()
//                .map(board -> modelMapper.map(board,BoardDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }
}



