package com.example.demo.src.follow.repository;

import com.example.demo.src.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFollowerIdx(Long idx);
    List<Follow> findByFolloweeIdx(Long idx);
}
