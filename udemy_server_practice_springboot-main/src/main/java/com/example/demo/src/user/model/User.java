package com.example.demo.src.user.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_idx")
    private Long id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "name")
    private String name;

    @Column(name = "pwd")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Column(name = "website")
    private String website;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private User(String nickName, String name, String password, String email) {
        this.nickName = nickName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.status = "ACTIVE";

    }

    public static User createUser(PostUserReq postUserReq, String pwd) {
        return new User(postUserReq.getNickName(), postUserReq.getName(), pwd, postUserReq.getEmail());
    }

}
