package com.example.community_board.controller;

import com.example.community_board.contoller.CommentController;
import com.example.community_board.dto.comment.CreateCommentsRequestDto;
import com.example.community_board.dto.comment.EditCommentsRequestDto;
import com.example.community_board.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class controllerTest {
    @InjectMocks
    CommentController commentController;

    @Mock
    CommentService commentService;
    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    @DisplayName("댓글 작성")
    public void createCommentsTest() throws Exception{
        //given
        Long id = 1L;
        CreateCommentsRequestDto createCommentsRequestDto = new CreateCommentsRequestDto("안녕하세요");

        //when, then
        mockMvc.perform(post("/api/comments/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCommentsRequestDto))
        ).andExpect(status().isCreated());

        verify(commentService).createComments(id, createCommentsRequestDto);

    }

    @Test
    @DisplayName("댓글 조회")
    public void getComments() throws Exception{
        //given
        Long id = 1L;

        //when, then
        mockMvc.perform(get("/api/comments/{id}", id))
                .andExpect(status().isOk());

        verify(commentService).getComments(id);

    }

    @Test
    @DisplayName("댓글 수정")
    public void editComments() throws Exception{
        //given
        Long id = 1L;
        EditCommentsRequestDto editCommentsRequestDto = new EditCommentsRequestDto("수정되었습니다.");

        //when, then
        mockMvc.perform(put("/api/comments/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editCommentsRequestDto))
        ).andExpect(status().isOk());

        verify(commentService).editComments(id, editCommentsRequestDto); // 이걸 쓰는 이유 -> dto 에서 예외처리 확인하기 위함
    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteComments() throws Exception{
        //given
        Long id = 1L;

        //when, then
        mockMvc.perform(delete("/api/comments/{id}", id)
        ).andExpect(status().isOk());

        verify(commentService).deleteComment(id); // 이걸 쓰는 이유 -> dto 에서 예외처리 확인하기 위함
    }
}