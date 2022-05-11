package com.example.demo.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteDTO {

    @NotNull
    private Long userIdx;

    @NotBlank
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "Incorrect ID form")
    private String nickName;
}
