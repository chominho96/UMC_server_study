package com.example.demo.src.post.repository;

import com.example.demo.src.post.dto.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.src.comment.QComment.comment1;
import static com.example.demo.src.follow.QFollow.follow;
import static com.example.demo.src.post.QLikes.likes;
import static com.example.demo.src.post.QPost.post;
import static com.example.demo.src.post.QPostImg.postImg;
import static com.example.demo.src.user.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<PostDTO> findPostsByUserIdx(Long userIdx) {

        return queryFactory.select(new QPostDTO(post.id, user.id, user.nickName,
                        post.status, post.content))
                .from(post)
                .join(post.user, user)
                .where(user.id.eq(userIdx))
                .fetch();

    }

    public List<PostResDTO> findAllPosts(Long userIdx) {


        // TODO : 모든 게시글을 가져오는 메서드로, 적절한 페이징 필요
        List<PostResDTO> result =  queryFactory.select(new QPostResDTO(post.id, user.id, user.nickName, user.profileImgUrl,
                post.content, post.likeCount, comment1.id.count(), post.updatedDate))
                .from(comment1)
                .join(comment1.post, post).on(comment1.post.eq(post))
                .join(post.user, user).on(post.user.eq(user))
                .groupBy(post.id)
                .where(post.user.eq(user).and(user.id.in(
                        queryFactory.select(follow.followerIdx)
                                .from(follow)
                                .where(follow.followeeIdx.eq(userIdx)))))
                .fetch();

        System.out.println("성공");

        for (PostResDTO dto : result) {
            dto.setImgList(queryFactory.select(new QPostImgResDTO(postImg.postImgUrlIdx, postImg.imgUrl))
                    .from(postImg)
                    .join(postImg.post, post)
                    .where(post.id.eq(dto.getPostIdx()))
                    .fetch());

            if (!queryFactory.select(likes.id)
                    .from(likes)
                    .where(likes.post.id.eq(dto.getPostIdx()).and(likes.user.id.eq(userIdx)))
                    .fetch().isEmpty()) {
                // if like
                dto.setLikeOrNot(true);
            }

        }


        return result;
    }


}
