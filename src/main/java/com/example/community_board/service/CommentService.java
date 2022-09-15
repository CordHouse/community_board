package com.example.community_board.service;

import com.example.community_board.dto.comment.CreateCommentsRequestDto;
import com.example.community_board.dto.comment.CreateCommentsResponseDto;
import com.example.community_board.dto.comment.EditCommentsRequestDto;
import com.example.community_board.dto.comment.EditCommentsResponseDto;
import com.example.community_board.entity.Board;
import com.example.community_board.entity.Comment;
import com.example.community_board.exception.comment.NotFoundcommentsException;
import com.example.community_board.repository.BoardRepository;
import com.example.community_board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentsResponseDto createComments(Long id, CreateCommentsRequestDto createCommentsRequestDto){
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundcommentsException();
        });
        Comment comment = new Comment(createCommentsRequestDto.getComment(), "홍길동", board);
        commentRepository.save(comment);
        return new CreateCommentsResponseDto().toDto(comment);
    }

    @Transactional
    public EditCommentsResponseDto editComments(Long id, EditCommentsRequestDto editCommentsRequestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundcommentsException();
        });
        comment.setComment(editCommentsRequestDto.getComment());
        return new EditCommentsResponseDto().toDto(comment);
    }

    @Transactional
    public String deleteComment(Long id){
        commentRepository.deleteById(id);
        return "해당 댓글이 삭제되었습니다.";
    }
}
