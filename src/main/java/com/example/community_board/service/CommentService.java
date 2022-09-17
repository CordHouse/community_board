package com.example.community_board.service;

import com.example.community_board.dto.comment.CommentsResponseDto;
import com.example.community_board.dto.comment.CreateCommentsRequestDto;
import com.example.community_board.dto.comment.EditCommentsRequestDto;
import com.example.community_board.entity.Board;
import com.example.community_board.entity.Comment;
import com.example.community_board.exception.board.BoardNotFoundException;
import com.example.community_board.repository.BoardRepository;
import com.example.community_board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓글 생성
    @Transactional
    public void createComments(Long id, CreateCommentsRequestDto createCommentsRequestDto){
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });
        Comment comment = new Comment(createCommentsRequestDto.getComment(), "홍길동", board);
        commentRepository.save(comment);
    }

    // 게시글 별 댓글 조회
    @Transactional
    public List<CommentsResponseDto> getComments(Long id){
        List<Comment> commentList = commentRepository.findAllByBoard_Id(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });

        List<CommentsResponseDto> commentsResponseDtoList = new LinkedList<>();
        commentList.forEach(boardId -> commentsResponseDtoList.add(new CommentsResponseDto().toDto(boardId)));
        return commentsResponseDtoList;
    }

    // 댓글 수정
    @Transactional
    public void editComments(Long id, EditCommentsRequestDto editCommentsRequestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });
        comment.setComment(editCommentsRequestDto.getComment());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}
