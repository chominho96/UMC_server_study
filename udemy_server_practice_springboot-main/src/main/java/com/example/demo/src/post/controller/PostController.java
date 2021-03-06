package com.example.demo.src.post.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.PostStatus;
import com.example.demo.src.post.dto.*;
import com.example.demo.src.post.repository.PostQueryRepository;
import com.example.demo.src.post.service.PostService;
import com.example.demo.src.user.model.GetUserPostsRes;
import com.example.demo.utils.JwtService;
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
import java.util.Objects;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostQueryRepository postQueryRepository;
    private final JwtService jwtService;


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
            // JWT ?????? ?????? ?????? ??????
            Long userIdx = jwtService.getUserIdx();
            // request??? JWT??? DTO??? jwt??? ?????????????????? ?????? ??????
            if (!Objects.equals(createPostDTO.getUserIdx(), userIdx)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            CreatePostResDTO createPostResDTO = postService.createPost(createPostDTO.getUserIdx(), createPostDTO);
            return new BaseResponse<>(createPostResDTO);
        }
        catch (BaseException e1) {
            // Refresh Token??? ????????????, Access Token??? ????????????????????? ??????
            try {
                String newAccessToken = jwtService.updateToken();
                // TODO : ????????? ?????? ???????????? ????????????, ????????? Header ????????? ????????? ??????
                // ??????????????? ?????? ?????? ???????????? ??????????????? ??????????????? ??????

                // ?????? ?????? ??????
                CreatePostResDTO createPostResDTO = postService.createPost(createPostDTO.getUserIdx(), createPostDTO);
                return new BaseResponse<>(createPostResDTO);
            }
            catch (Exception e2) {
                return new BaseResponse<>(e1.getStatus());
            }
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
        // @Valid ?????????????????? ?????? ???????????? ????????? Validation??? ???????????????, ?????? ???????????? ?????? ExceptionHandler??? ????????? ?????????
        return new BaseResponse<>(BaseResponseStatus.REQUEST_BODY_VALIDATION_ERROR);
    }

}
