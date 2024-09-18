package com.micro.discussions.services;

import com.micro.discussions.entities.Comments;
import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.CommentPojo;
import com.micro.discussions.repositories.CommentRepository;
import com.micro.discussions.repositories.DiscussionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;


    public List<CommentPojo> findAll() {
        List<Comments> comments = commentRepository.findAll();
        return CommentPojo.convertCommentsToPojo(comments);
    }

    public CommentPojo createComment(CommentPojo pojo, String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User not found with username: " + username));

        Discussion discussion = discussionRepository.findById(pojo.getDiscussion_id());

        commentRepository.save(CommentPojo.toEntity(pojo, user, discussion));
        return pojo;
    }

    public boolean deleteById(long pk, String username) {
        if (commentRepository.existsById(pk)) {
            User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                    new EntityNotFoundException("User not found with username: " + username));
            Comments comment = commentRepository.findById(pk).orElseThrow(() ->
                    new EntityNotFoundException("Comment not found with id: " + pk));

            // чтобы пользователь (не админ) мог удалять только свои дискуссии
            if (comment.getAuthor().getId() == user.getId() || user.getRole() == UserRole.ROLE_ADMIN) {
                commentRepository.deleteById(pk);
                return true;
            }
        }
        return false;
    }
}
