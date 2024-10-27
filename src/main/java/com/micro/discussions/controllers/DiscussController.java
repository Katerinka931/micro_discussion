package com.micro.discussions.controllers;

import com.micro.discussions.pojos.DiscussionPojo;
import com.micro.discussions.services.DiscussionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forum/discussions")
public class DiscussController {
    private final DiscussionService discussionService;

    public DiscussController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping("/advertisement/{pk}")
    public List<DiscussionPojo> findAllByAdvertisement(@PathVariable long pk) {
        return discussionService.findAllByAdvertisement(pk);
    }

    @GetMapping("/count_advertisement/{pk}")
    public int countAdvertisementDiscussions(@PathVariable long pk) {
        return discussionService.countAdvertisementDiscussions(pk);
    }

    @GetMapping("/{pk}")
    public Map<String, Object> findById(@PathVariable long pk){
        return discussionService.findById(pk);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<DiscussionPojo> createDiscussion(@RequestBody DiscussionPojo pojo) {
        try {
            return new ResponseEntity<>(discussionService.createDiscussion(pojo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pk}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean deleteDiscussion(@PathVariable long pk) {
        return discussionService.deleteById(pk);
    }
}
