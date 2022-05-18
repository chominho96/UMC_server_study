package com.example.demo.src.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostImgsUrlReq {
    @NotBlank
    private String imgUrl;

}
