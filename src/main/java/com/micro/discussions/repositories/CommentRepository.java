package com.micro.discussions.repositories;

import com.micro.discussions.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findCommentsByDiscussion_Id(long discussion);
}
