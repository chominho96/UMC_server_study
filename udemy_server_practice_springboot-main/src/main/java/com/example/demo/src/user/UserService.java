package com.example.demo.src.user;


import com.example.demo.config.BaseException;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.dto.UserDeleteDTO;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.src.user.model.User;
import com.example.demo.src.user.repository.UserRepository;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService, UserRepository userRepository) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 이메일 중복 확인
        /*if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }*/

        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());  postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            // TODO: jwt는 다음주차에서 배울 내용입니다!
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public BaseResponse<String> deleteUser(UserDeleteDTO dto) {
        Optional<User> findUser = userRepository.findById(dto.getUserIdx());

        if (findUser.isPresent()) {
            // 삭제하려는 User가 DB에 존재하면
            userRepository.delete(findUser.get());
            return new BaseResponse<>(SUCCESS);
        }
        else {
            // 삭제하려는 User가 DB에존재하지 않으면
            return new BaseResponse<>(REQUEST_ERROR);
        }
    }

}
