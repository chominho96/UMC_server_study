package com.example.demo.src.post.service;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.PostImg;
import com.example.demo.src.post.QPost;
import com.example.demo.src.post.dto.PostDTO;
import com.example.demo.src.post.dto.PostResDTO;
import com.example.demo.src.post.dto.QPostDTO;
import com.example.demo.src.post.repository.PostImgRepository;
import com.example.demo.src.post.repository.PostQueryRepository;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.user.model.*;
import com.example.demo.src.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;
import static com.example.demo.src.post.QPost.post;
import static com.example.demo.src.user.model.QUser.user;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;
    private final PostQueryRepository postQueryRepository;

    public List<PostDTO> findPostsByUserIdx(Long userIdx) throws BaseException {

        Optional<User> findUser = userRepository.findById(userIdx);

        if (!findUser.isPresent()) {
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        return postQueryRepository.findPostsByUserIdx(userIdx);
    }

    public List<PostResDTO> retrievePosts(Long userIdx) throws BaseException {

        Boolean isMyFeed = true;
        // JWT 토큰에서 로그인된 사용자를 반환해서 현재 나의 피드인지 확인

        if(!isUserExist(userIdx)) {
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try {
            return postQueryRepository.findAllPosts(userIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean isUserExist(Long userIdx){
        return userRepository.findById(userIdx).isPresent();

    }
}
