package com.example.demo.src.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDTO {

    private Long userIdx;

    @NotBlank
    @Length(max = 450, message = "내용의 글자수를 확인해주세요.")
    private String content;
    @NotEmpty(message = "게시물의 이미지를 입력해주세요.")
    private List<PostImgsUrlReq> postImgUrls;
}
