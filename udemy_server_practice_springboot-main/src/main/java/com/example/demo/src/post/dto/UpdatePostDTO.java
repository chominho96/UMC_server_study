package com.example.demo.src.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDTO {

    @NotNull
    private Long userIdx;
    @Length(max = 450)
    private String content;
}
