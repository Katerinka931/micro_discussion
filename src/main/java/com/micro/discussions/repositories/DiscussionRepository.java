package com.micro.discussions.repositories;

import com.micro.discussions.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Discussion findById(long id);
    List<Discussion> findByAdvertisement(long pk);
    int countAllByAdvertisement(long pk);
}
