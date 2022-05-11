package com.example.demo.src.comment.repository;

import com.example.demo.src.comment.Comment;
import com.example.demo.src.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
