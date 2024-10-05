package com.micro.discussions.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.DiscussionPojo;
import com.micro.discussions.repositories.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final ApiClient apiClient;

    public List<DiscussionPojo> findAllByAdvertisement(long pk) {
        List<Discussion> discussions = discussionRepository.findByAdvertisement(pk);
        return DiscussionPojo.convertDiscussionsToPojo(discussions);
    }

    public Map<String, Object> findById(long pk) {
        ObjectMapper objectMapper = new ObjectMapper();
        DiscussionPojo pojo = DiscussionPojo.fromEntity(discussionRepository.findById(pk));

        Map<String, Object> discussion = objectMapper.convertValue(pojo, Map.class);
        discussion.put("advertisement", apiClient.getAdvertisement(pojo.getAdvertisement()));
        return discussion;
    }

    public DiscussionPojo createDiscussion(DiscussionPojo pojo, String username) {
        long user = 1;

        discussionRepository.save(DiscussionPojo.toEntity(pojo, user));
        return pojo;
    }

    public boolean deleteById(long pk, String username) {
        if (discussionRepository.existsById(pk)) {
//            User user = userRepository.findUserByUsername(username).orElseThrow(() ->
//                    new EntityNotFoundException("User not found with username: " + username));
            Discussion discussion = discussionRepository.findById(pk);

            // чтобы пользователь (не админ) мог удалять только свои дискуссии
//            if (discussion.getAuthor().getId() == user.getId() || user.getRole() == UserRole.ROLE_ADMIN) {
            if (true) { // todo
                discussionRepository.deleteById(pk);
                return true;
            }
        }
        return false;
    }
}
