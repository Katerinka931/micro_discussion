package com.micro.discussions.repositories;

import com.micro.discussions.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
