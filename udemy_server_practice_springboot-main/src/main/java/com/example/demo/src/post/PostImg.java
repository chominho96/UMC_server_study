package com.example.demo.src.post;

import com.example.demo.src.common.BaseEntity;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;

@Entity
@Getter
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


}
