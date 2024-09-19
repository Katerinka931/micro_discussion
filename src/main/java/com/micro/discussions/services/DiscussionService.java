package com.micro.discussions.services;

import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.DiscussionPojo;
import com.micro.discussions.repositories.DiscussionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    public List<DiscussionPojo> findAll() {
        List<Discussion> discussions = discussionRepository.findAll();
        return DiscussionPojo.convertDiscussionsToPojo(discussions);
    }

    public List<DiscussionPojo> findAllByAdvertisement(long pk) {
        List<Discussion> discussions = discussionRepository.findByAdvertisement(pk);
        return DiscussionPojo.convertDiscussionsToPojo(discussions);
    }

    public DiscussionPojo findById(long pk) {
        return DiscussionPojo.fromEntity(discussionRepository.findById(pk));
    }

    public DiscussionPojo createDiscussion(DiscussionPojo pojo, String username) {
//        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
//                new EntityNotFoundException("User not found with username: " + username));
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
