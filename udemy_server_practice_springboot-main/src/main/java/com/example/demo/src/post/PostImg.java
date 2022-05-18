package com.example.demo.src.post;

import com.example.demo.src.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostImg extends BaseEntity {

    @GeneratedValue
    @Id
    @Column(name = "post_img_url_idx")
    private Long postImgUrlIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private Post post;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private PostImg(Post post, String imgUrl) {
        this.post = post;
        this.imgUrl = imgUrl;
        this.status = PostStatus.ACTIVE;
    }

    public static PostImg createPostImg(Post post, String imgUrl) {
        return new PostImg(post, imgUrl);
    }
}
