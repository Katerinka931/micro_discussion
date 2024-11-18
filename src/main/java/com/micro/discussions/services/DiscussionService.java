package com.micro.discussions.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.DiscussionPojo;
import com.micro.discussions.repositories.DiscussionRepository;
import com.micro.discussions.service_auth.CustomUserDetails;
import com.micro.discussions.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public int countAdvertisementDiscussions(long pk) {
        return discussionRepository.countAllByAdvertisement(pk);
    }

    public Map<String, Object> findById(long pk) {
        ObjectMapper objectMapper = new ObjectMapper();
        DiscussionPojo pojo = DiscussionPojo.fromEntity(discussionRepository.findById(pk));

        Map<String, Object> discussion = objectMapper.convertValue(pojo,
                new TypeReference<>() {
                }
        );
        discussion.put("advertisement", apiClient.getAdvertisement(pojo.getAdvertisement(),
                AuthUtil.getRole()));
        return discussion;
    }

    public DiscussionPojo createDiscussion(DiscussionPojo pojo) {
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        discussionRepository.save(DiscussionPojo.toEntity(pojo, details.getUsername()));
        return pojo;
    }

    public boolean deleteById(long pk) {
        if (discussionRepository.existsById(pk)) {
            discussionRepository.deleteById(pk);
            return true;
        }
        return false;
    }

    public void deleteByAdvertisementId(int advertisementId) {
         List<Discussion> discussions = discussionRepository.findByAdvertisement(advertisementId);
         for (Discussion discussion : discussions)
             deleteById(discussion.getId());
    }
}
