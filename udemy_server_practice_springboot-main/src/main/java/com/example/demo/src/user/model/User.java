package com.example.demo.src.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long userIdx;
    private String nickName;
    private String name;
    private String profileImgUrl;
    private String website;
    private String introduce;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //private String phone;
    //private String email;
    //private String password;

}
