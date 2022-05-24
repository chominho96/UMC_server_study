package com.example.demo.utils;


import com.example.demo.config.BaseException;
import com.example.demo.config.redis.RedisService;
import com.example.demo.config.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RedisService redisService;

    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(Long userIdx){
        Date now = new Date();
        String token = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000L))
                // Token 예제 확인을 위해 유효시간을 1분으로 설정
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();

        // Redis에 Refresh Token 저장
        // { Key : Refresh Token, Value : userIdx } 으로 저장
        redisService.setValues(String.valueOf(userIdx), token);

        return token;
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    Header에서 X-REFRESH-TOKEN 으로 JWT 추출
    @return String
     */
    public String getRefreshToken(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-REFRESH-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public Long getUserIdx() throws BaseException{
        //1. JWT 추출
        String accessToken = getAccessToken();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception i) {
            // 만료된 Token인 경우 Refresh Token 확인
            // Refresh Token이 있다면
            if (getRefreshToken() != null) {
                System.out.println("Refresh Token 재발급 요청발생");
                throw new BaseException(REQUIRED_UPDATE_ACCESS_TOKEN);
                // Access Token 재발급 요청
            }
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userIdx", Long.class);
    }

    // Access Token이 만료되었을 때, 재발급
    public String updateToken() throws BaseException {

        String userIdx = redisService.getValues(getRefreshToken());
        if (userIdx != null) {
            // Access Token 재발급
            return createJwt(Long.valueOf(userIdx));
        }
        else {
            throw new BaseException(INVALID_JWT);
        }
    }

}
