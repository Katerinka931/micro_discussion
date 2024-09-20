package com.micro.discussions.controllers;

import com.micro.discussions.pojos.DiscussionPojo;
import com.micro.discussions.services.DiscussionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

@RestController
@RequestMapping("/api/forum/discussions")
public class DiscussController {
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final DiscussionService discussionService;

    public DiscussController(DiscussionService discussionService, DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discussionService = discussionService;
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    @GetMapping("/temp_test") //связь через эврику с сервисом объявлений
    public String helloWorld() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("advertisement").get(0);
        return restClient.get()
                .uri(serviceInstance.getUri() + "/api/advertisement")
                .retrieve()
                .body(String.class);
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
        String username = "username";
        try {
            return new ResponseEntity<>(discussionService.createDiscussion(pojo, username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{pk}")
    public boolean deleteDiscussion(@PathVariable long pk) {
        String username = "username";
        return discussionService.deleteById(pk, username);
    }
}
