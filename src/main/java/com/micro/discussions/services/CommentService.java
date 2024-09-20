package com.micro.discussions.services;

import com.micro.discussions.entities.Comments;
import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.CommentPojo;
import com.micro.discussions.repositories.CommentRepository;
import com.micro.discussions.repositories.DiscussionRepository;
import com.micro.discussions.utils.JsonUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder, DiscussionRepository discussionRepository, CommentRepository commentRepository) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
        this.discussionRepository = discussionRepository;
        this.commentRepository = commentRepository;
    }

    public Map<String, Object> findAllForDiscussion(long discussion_id) {
        Map<String, Object> dictionary = new HashMap<>();

        ServiceInstance serviceInstance = discoveryClient.getInstances("advertisement").get(0);
        Map<String, Object> result = JsonUtil.stringToJson(
                restClient.get()
                        .uri(serviceInstance.getUri() + "/api/advertisement/{discussion}", discussion_id)
                        .retrieve()
                        .body(String.class)
        );

        Discussion discussion = discussionRepository.findById(discussion_id);
        List<Comments> comments = commentRepository.findCommentsByDiscussion_Id(discussion_id);

        dictionary.put("comments", CommentPojo.convertCommentsToPojo(comments));
        dictionary.put("advertisement", result);
        dictionary.put("discussion", discussion.getName());
        return dictionary;
    }

    public CommentPojo createComment(CommentPojo pojo) {
//        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
//                new EntityNotFoundException("User not found with username: " + username));
        long user = 1;

        Discussion discussion = discussionRepository.findById(pojo.getDiscussion_id());

        commentRepository.save(CommentPojo.toEntity(pojo, user, discussion));
        return pojo;
    }

    public boolean deleteById(long pk) {
        if (commentRepository.existsById(pk)) {
//            User user = userRepository.findUserByUsername(username).orElseThrow(() ->
//                    new EntityNotFoundException("User not found with username: " + username));
//            long user = 1;
            Comments comment = commentRepository.findById(pk).orElseThrow(() ->
                    new EntityNotFoundException("Comment not found with id: " + pk));

            // чтобы пользователь (не админ) мог удалять только свои дискуссии
//            if (comment.getAuthor().getId() == user.getId() || user.getRole() == UserRole.ROLE_ADMIN) {
            if (true) { // todo !
                commentRepository.deleteById(pk);
                return true;
            }
        }
        return false;
    }
}
