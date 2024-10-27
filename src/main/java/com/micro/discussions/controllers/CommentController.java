package com.micro.discussions.controllers;

import com.micro.discussions.pojos.CommentPojo;
import com.micro.discussions.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/forum/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/count/{discussion_id}")
    public int countAllByDiscussion(@PathVariable long discussion_id) {
        return commentService.countAllByDiscussion(discussion_id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CommentPojo> createComment(@RequestBody CommentPojo pojo) {
        try {
            return new ResponseEntity<>(commentService.createComment(pojo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pk}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean deleteComment(@PathVariable long pk) {
        return commentService.deleteById(pk);
    }
}
