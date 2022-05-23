package com.example.demo.src.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userIdx;
    private String name;
    private String nickName;
    private String email;
    private String password;
}
