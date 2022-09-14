package com.example.community_board.service;

import com.example.community_board.dto.board.*;
import com.example.community_board.entity.Board;
import com.example.community_board.exception.board.BoardNotFoundException;
import com.example.community_board.exception.board.ListNotFoundException;
import com.example.community_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardListResponseDto> getBoards() {

        List<BoardListResponseDto> lst = boardRepository.findAll().stream()
                .map(s -> new BoardListResponseDto().toDto(s)).collect(Collectors.toList());

        if(lst.isEmpty()) {
            throw new ListNotFoundException();
        }

        return lst;
    }

    @Transactional
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        board.setViewCount(board.getViewCount() + 1); // 조회수 증가

        return new BoardResponseDto().toDto(board);
    }

    @Transactional
    public CreateBoardResponseDto save(CreateBoardRequestDto requestDto) {
        Board board = new Board(requestDto.getTitle(), requestDto.getContent());
        boardRepository.save(board);

        return new CreateBoardResponseDto().toDto(board);
    }

    @Transactional
    public EditResponseDto edit(Long id, EditRequestDto requestDto) {
        Board findItem = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        findItem.setTitle(requestDto.getTitle());
        findItem.setContent(requestDto.getContent());

        return new EditResponseDto().toDto(findItem);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        boardRepository.deleteById(id);
    }
}
