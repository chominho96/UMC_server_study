package com.example.demo.src.post.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.PostStatus;
import com.example.demo.src.post.dto.PostDTO;
import com.example.demo.src.post.dto.PostResDTO;
import com.example.demo.src.post.repository.PostQueryRepository;
import com.example.demo.src.post.service.PostService;
import com.example.demo.src.user.model.GetUserPostsRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostQueryRepository postQueryRepository;


    @PostMapping("/post/{userIdx}")
    public Object selectPosts(@PathVariable Long userIdx) {

        try {
            return postService.findPostsByUserIdx(userIdx);
        }
        catch (Exception e) {
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

    }

    @GetMapping("/post/all/{userIdx}")
    public BaseResponse<List<PostResDTO>> getAllPosts(@PathVariable("userIdx") Long userIdx) {
        try {
            List<PostResDTO> postResDTO = postService.retrievePosts(userIdx);
            return new BaseResponse<>(postResDTO);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     *
     * DTO validation 오류 (Path Vairable 관련)
     *
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<String> handleMethodArgumentTypeMismatchException(
            HttpMessageNotReadableException ex) {
        // Path Variable이 없을 때 발생하는 예외 핸들링
        return new BaseResponse<>(REQUEST_ERROR);
    }
}
