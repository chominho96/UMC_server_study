package com.example.demo.src.post.repository;

import com.example.demo.src.post.Post;
import com.example.demo.src.post.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {

    List<PostImg> findByPost(Post post);
}
