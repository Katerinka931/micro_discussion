package com.micro.discussions.services;

import com.micro.discussions.entities.Comments;
import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.CommentPojo;
import com.micro.discussions.repositories.CommentRepository;
import com.micro.discussions.repositories.DiscussionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;

    public CommentPojo createComment(CommentPojo pojo) {
        long user = 1;

        Discussion discussion = discussionRepository.findById(pojo.getDiscussion_id());

        commentRepository.save(CommentPojo.toEntity(pojo, user, discussion));
        return pojo;
    }

    public boolean deleteById(long pk) {
        if (commentRepository.existsById(pk)) {
            Comments comment = commentRepository.findById(pk).orElseThrow(() ->
                    new EntityNotFoundException("Comment not found with id: " + pk));

            // todo чтобы пользователь (не админ) мог удалять только свои дискуссии и комменты
//            if (comment.getAuthor().getId() == user.getId() || user.getRole() == UserRole.ROLE_ADMIN) {
            if (true) {
                commentRepository.deleteById(pk);
                return true;
            }
        }
        return false;
    }
}
