package com.example.demo.src.login.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.login.dto.LoginDTO;
import com.example.demo.src.login.response.LoginResDTO;
import com.example.demo.src.login.service.LoginService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.AuthProvider;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {


    private final LoginService loginService;

    @PostMapping("/login")
    public BaseResponse<LoginResDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {

            LoginResDTO loginResDTO = loginService.login(loginDTO);

            return new BaseResponse<>(loginResDTO);
        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
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
        // validation 오류 메시지는 DTO의 애노테이션에서 처리
        return new BaseResponse<>(ex.getMessage());
    }
}
