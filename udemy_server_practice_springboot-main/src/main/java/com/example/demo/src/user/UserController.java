package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.dto.UserDeleteDTO;
import com.example.demo.src.user.model.*;
import com.example.demo.src.user.repository.UserRepository;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;






    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, UserRepository userRepository){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }



    /**
     * 회원 조회 API
     * [GET] /users
     * 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<GetUserFeedRes>
     */
    //Query String
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/users
    public BaseResponse<GetUserFeedRes> getUsers(@PathVariable("userIdx") Long userIdx) {
        try{

            GetUserFeedRes getUserFeedRes = userProvider.retrieveUserFeed(1, userIdx);
            return new BaseResponse<>(getUserFeedRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/{userIdx}/X") // (GET) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<GetUserRes> getUserByIdx(@PathVariable("userIdx") Long userIdx) {
        System.out.println("여기 들어옴");
        log.info("여기 들어옴");
        try {
            Optional<User> findUser = userRepository.findById(userIdx);
            return new BaseResponse<>(new GetUserRes(userIdx, findUser.get().getName(), findUser.get().getNickName(), findUser.get().getEmail()));

        }catch (Exception e) {
            return new BaseResponse<>(REQUEST_ERROR);
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("") // (POST) 127.0.0.1:9000/users
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        // 이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}") // (PATCH) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") Long userIdx, @RequestBody User user){
        try {
            //TODO: jwt는 다음주차에서 배울 내용입니다!
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }


            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getNickName());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     *  User Delete API
     */
    @PostMapping("/delete")
    public BaseResponse<String> deleteUser(@Valid @RequestBody UserDeleteDTO dto) {
        return userService.deleteUser(dto);
    }

    /**
     *
     * DTO validation 오류
     *
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<String> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        // @Valid 애노테이션이 있는 부분에서 형식적 Validation이 이루어지고, 이를 실패하면 해당 ExceptionHandler가 예외를 핸들링
        return new BaseResponse<>(REQUEST_ERROR);
    }

}
