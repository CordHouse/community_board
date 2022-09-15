package com.example.community_board.service;

import com.example.community_board.dto.board.*;
import com.example.community_board.dto.comment.CommentsListDto;
import com.example.community_board.entity.Board;
import com.example.community_board.entity.Comment;
import com.example.community_board.exception.board.BoardNotFoundException;
import com.example.community_board.exception.board.ListNotFoundException;
import com.example.community_board.exception.comment.NotFoundcommentsException;
import com.example.community_board.repository.BoardRepository;
import com.example.community_board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

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

        List<Comment> commentsList = commentRepository.findAllByBoard_Id(id).orElseThrow(() -> {
            throw new NotFoundcommentsException();
        });
        List<CommentsListDto> commentsListDtos = new LinkedList<>();
        commentsList.forEach(comments -> commentsListDtos.add(new CommentsListDto(comments)));

        return new BoardResponseDto().toDto(board, commentsListDtos);
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
