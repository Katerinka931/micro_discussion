package com.micro.discussions.controllers;

import com.micro.discussions.pojos.DiscussionPojo;
import com.micro.discussions.services.DiscussionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/discussion")
public class DiscussController {
    private final DiscussionService discussionService;

    public DiscussController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping
    public List<DiscussionPojo> findAll() {
        return discussionService.findAll();
    }

    @GetMapping("/advertisement/{pk}")
    public List<DiscussionPojo> findAllByAdvertisement(@PathVariable long pk) {
        return discussionService.findAllByAdvertisement(pk);
    }

    @GetMapping("/{pk}")
    public DiscussionPojo findById(@PathVariable long pk) {
        return discussionService.findById(pk);
    }

    @PostMapping
    public ResponseEntity<DiscussionPojo> createDiscussion(@RequestBody DiscussionPojo pojo) {
        try {
            return new ResponseEntity<>(discussionService.createDiscussion(pojo, authService.getUsername()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pk}")
    public boolean deleteDiscussion(@PathVariable long pk) {
        return discussionService.deleteById(pk, authService.getUsername());
    }
}
