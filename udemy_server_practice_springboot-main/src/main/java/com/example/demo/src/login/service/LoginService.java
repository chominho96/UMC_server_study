package com.example.demo.src.login.service;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.config.redis.RedisService;
import com.example.demo.src.login.dto.LoginDTO;
import com.example.demo.src.login.response.LoginResDTO;
import com.example.demo.src.user.model.User;
import com.example.demo.src.user.repository.UserRepository;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.AuthProvider;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.USERS_INCORRECT_PASSWORD;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisService redisService;

    public LoginResDTO login(LoginDTO loginDTO) throws BaseException {
        Optional<User> findUser = userRepository.findByEmail(loginDTO.getEmail());

        // 넘겨받은 회원 정보가 없을 시
        if (!findUser.isPresent()) {
            throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
        }

        String encryptPassword;
        try {
            encryptPassword = SHA256.encrypt(loginDTO.getPassword());
        }
        catch (Exception e) {
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }

        if (findUser.get().getPassword().equals(encryptPassword)) {
            Long userIdx = findUser.get().getId();
            String jwt = jwtService.createJwt(userIdx);

            // Redis에 Token 저장
            redisService.setValues(jwt, String.valueOf(findUser.get().getId()));

            return new LoginResDTO(userIdx, jwt);
        }
        else{
            throw new BaseException(USERS_INCORRECT_PASSWORD);
        }
    }
}
