package com.micro.discussions.controllers;

import com.micro.discussions.pojos.CommentPojo;
import com.micro.discussions.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/comments")
public class CommentController {
    private final String tempUsername = "username";

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentPojo> findAll() {
        return commentService.findAll();
    }

    @PostMapping
    public ResponseEntity<CommentPojo> createComment(@RequestBody CommentPojo pojo) {
        try {
            return new ResponseEntity<>(commentService.createComment(pojo, this.tempUsername), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pk}")
    public boolean deleteDiscussion(@PathVariable long pk) {
        return commentService.deleteById(pk, this.tempUsername);
    }
}
