package com.example.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.BoardDto;
import com.example.board.dto.ReplyDto;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
@RestController
public class ReplyController {

    private final ReplyService service;

    // http://localhost:8080/replies/board/100
    @GetMapping("/board/{bno}")
    public List<ReplyDto> getListByBoard(@PathVariable("bno") Long bno) {

        log.info("댓글 가져오기 {} ", bno);

        List<ReplyDto> replies = service.getReplies(bno);

        return replies;
    }

    // replies/new + POSt

    @PostMapping("/new")
    public ResponseEntity<Long> postCreate(@RequestBody ReplyDto dto) {

        log.info("댓글 등록 {} ", dto);

        return new ResponseEntity<Long>(service.create(dto), HttpStatus.OK);
    }

    // /{rno} + DELETE

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> delete(@PathVariable("rno") Long rno) {

        log.info("댓글 제거 {} ", rno);

        // 서비스 레이어를 통해 해당 댓글을 삭제
        service.remove(rno);

        // 삭제가 성공적으로 이루어졌음을 나타내는 문자열 "success" 와 함께 HTTP 상태 코드 200(OK)를 반환
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // /replies/{rno} + GET
    @GetMapping("/{rno}")
    public ResponseEntity<ReplyDto> getRow(@PathVariable("rno") Long rno) {
        log.info("댓글 하나 요청 {} ", rno);

        // 해당 댓글 정보를 가져와서 응답으로 전송
        return new ResponseEntity<ReplyDto>(service.getReply(rno), HttpStatus.OK);
    }

}
