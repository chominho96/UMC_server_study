package com.example.demo.src.post.repository;

import com.example.demo.src.post.Post;
import com.example.demo.src.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

}
