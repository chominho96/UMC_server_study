package com.example.demo.src.post;

import com.example.demo.src.comment.Comment;
import com.example.demo.src.common.BaseEntity;
import com.example.demo.src.post.dto.CreatePostDTO;
import com.example.demo.src.post.dto.UpdatePostDTO;
import com.example.demo.src.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_idx")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    User user;

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(mappedBy = "post")
    private List<PostImg> postImgList;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;


    private Post(User user, String content) {
        this.user = user;
        this.content = content;
        this.likeCount = 0L;
        this.status = PostStatus.ACTIVE;

    }

    public static Post createPost(User user, CreatePostDTO createPostDTO) {
        return new Post(user, createPostDTO.getContent());
    }

    public void updatePost(UpdatePostDTO updatePostDTO) {
        this.content = updatePostDTO.getContent();
    }

    public void deletePost() {
        this.status = PostStatus.DELETED;
    }


}
