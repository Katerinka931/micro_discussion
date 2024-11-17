package com.micro.discussions.services;

import com.micro.discussions.entities.Comments;
import com.micro.discussions.entities.Discussion;
import com.micro.discussions.pojos.CommentPojo;
import com.micro.discussions.repositories.CommentRepository;
import com.micro.discussions.repositories.DiscussionRepository;
import com.micro.discussions.service_auth.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;

    public int countAllByDiscussion(long pk) {
        return commentRepository.countAllByDiscussion_Id(pk);
    }

    public CommentPojo createComment(CommentPojo pojo) {
        Discussion discussion = discussionRepository.findById(pojo.getDiscussion_id());
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentRepository.save(CommentPojo.toEntity(pojo, details.getUsername(), discussion));
        return pojo;
    }

    public boolean deleteById(long pk) {
        if (commentRepository.existsById(pk)) {
            CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Comments comment = commentRepository.findById(pk).orElseThrow(() ->
                    new EntityNotFoundException("Comment not found with id: " + pk));

            // чтобы пользователь (не админ) мог удалять только свои дискуссии и комменты
            if (Objects.equals(comment.getAuthor(), details.getUsername()) || Objects.equals(details.getRole(), "ROLE_ADMIN")) {
                commentRepository.deleteById(pk);
                return true;
            }
        }
        return false;
    }
}
