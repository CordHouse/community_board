package com.example.community_board.contoller;

import com.example.community_board.dto.board.CreateBoardRequestDto;
import com.example.community_board.dto.board.EditRequestDto;
import com.example.community_board.response.Response;
import com.example.community_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    @ResponseStatus(HttpStatus.OK)
    public Response getBoards() {
        return Response.success(boardService.getBoards());
    }

    @GetMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getBoard(@PathVariable Long id) {
        return Response.success(boardService.getBoard(id));
    }

    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public Response saveBoard(@RequestBody @Valid CreateBoardRequestDto requestDto) {
        return Response.success(boardService.save(requestDto));
    }

    @PutMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response editBoard(@PathVariable Long id, @RequestBody @Valid EditRequestDto requestDto) {
        return Response.success(boardService.edit(id, requestDto));
    }

    @DeleteMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return Response.success("id == " + id + " 게시물을 삭제하였습니다.");
    }
}
