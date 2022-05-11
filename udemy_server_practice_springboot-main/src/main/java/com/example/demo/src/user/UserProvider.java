package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.GetUserFeedRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import com.example.demo.src.user.model.GetUserPostsRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.repository.UserRepository;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

//Provider : Read의 비즈니스 로직 처리
@Service
@RequiredArgsConstructor
@Slf4j
public class UserProvider {

    private final UserDao userDao;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());



    public GetUserFeedRes retrieveUserFeed(int userIdxByJwt, Long userIdx) throws BaseException {

        Boolean isMyFeed = true;
        // JWT 토큰에서 로그인된 사용자를 반환해서 현재 나의 피드인지 확인

        if(!isUserExist(userIdx)) {
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        try {

            if (userIdxByJwt != userIdx) {
                isMyFeed = false;
            }

            GetUserInfoRes getUserInfoRes = userDao.selectUserInfo(userIdx);
            List<GetUserPostsRes> getUserPostsRes = userDao.selectUserPosts(userIdx);

            return new GetUserFeedRes(isMyFeed, getUserInfoRes, getUserPostsRes);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUsersByIdx(int userIdx) throws BaseException{
        try{
            return userDao.getUsersByIdx(userIdx);
        }
        catch (Exception exception) {
            System.out.println("서비스 요청 실패");
            log.info("데이터베이스 service에서 연결 실패");
            throw new BaseException(DATABASE_ERROR);
        }
    }


    /*public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }*/


    public boolean isUserExist(Long userIdx){
        return userRepository.findById(userIdx).isPresent();

    }


}
