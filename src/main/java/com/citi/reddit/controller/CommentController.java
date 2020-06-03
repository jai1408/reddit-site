package com.citi.reddit.controller;

import com.citi.reddit.dto.CommentsDto;
import com.citi.reddit.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComments(@RequestBody CommentsDto commentsDto){
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(id));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllForUser(userName));
    }
}
