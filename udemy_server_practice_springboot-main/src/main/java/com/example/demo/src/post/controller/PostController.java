package com.example.demo.src.post.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.PostStatus;
import com.example.demo.src.post.dto.*;
import com.example.demo.src.post.repository.PostQueryRepository;
import com.example.demo.src.post.service.PostService;
import com.example.demo.src.user.model.GetUserPostsRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @GetMapping(path = {"/posts", "/posts/{userIdx}"})
    public BaseResponse<List<PostResDTO>> getAllPosts(@PathVariable(value = "userIdx", required = false) Long userIdx) {
        if (userIdx == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            List<PostResDTO> postResDTO = postService.retrievePosts(userIdx);
            return new BaseResponse<>(postResDTO);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/post")
    public BaseResponse<CreatePostResDTO> createPost(@RequestBody CreatePostDTO createPostDTO) {

        try {
            CreatePostResDTO createPostResDTO = postService.createPost(createPostDTO.getUserIdx(), createPostDTO);
            return new BaseResponse<>(createPostResDTO);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PatchMapping("/post/{postIdx}")
    public BaseResponse<String> updatePost(@PathVariable("postIdx") Long postIdx,
                                                      @RequestBody UpdatePostDTO updatePostDTO) {

        try {
            postService.updatePost(updatePostDTO.getUserIdx(), postIdx, updatePostDTO);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PatchMapping(path = {"/post/status", "/post/status/{postIdx}"})
    public BaseResponse<String> deletePost(@PathVariable(value = "postIdx", required = false) Long postIdx) {

        if (postIdx == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }

        try {
            postService.deletePost(postIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<String> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        // @Valid 애노테이션이 있는 부분에서 형식적 Validation이 이루어지고, 이를 실패하면 해당 ExceptionHandler가 예외를 핸들링
        return new BaseResponse<>(BaseResponseStatus.REQUEST_BODY_VALIDATION_ERROR);
    }

}
