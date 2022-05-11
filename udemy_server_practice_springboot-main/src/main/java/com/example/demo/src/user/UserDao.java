package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.follow.Follow;
import com.example.demo.src.follow.repository.FollowRepository;
import com.example.demo.src.post.Post;
import com.example.demo.src.post.PostImg;
import com.example.demo.src.post.QPost;
import com.example.demo.src.post.QPostImg;
import com.example.demo.src.post.repository.PostImgRepository;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.user.model.*;
import com.example.demo.src.user.repository.UserRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;
import static com.example.demo.src.post.QPost.post;
import static com.example.demo.src.post.QPostImg.postImg;
import static com.example.demo.src.user.model.QUser.user;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*public List<GetUserRes> selectUserInfo(){
        String getUsersQuery = "select userIdx,name,nickName,email from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserInfoRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")
                ));
    }*/

    public GetUserInfoRes selectUserInfo(Long userIdx) {
        Optional<User> findUser = userRepository.findById(userIdx);
        if(!findUser.isPresent()) {
            return null;
        }

        List<Follow> findFollowers = followRepository.findByFollowerIdx(userIdx);
        List<Post> findPosts = postRepository.findByUser(findUser.get());


        System.out.println("selectUserInfo 성공");
        return GetUserInfoRes.builder()
                .name(findUser.get().getName())
                .followerCount(findFollowers.size())
                .introduction(findUser.get().getIntroduce())
                .nickName(findUser.get().getNickName())
                .postCount(findPosts.size())
                .website(findUser.get().getWebsite())
                .profileImgUrl(findUser.get().getProfileImgUrl())
                .build();
    }

    public List<GetUserPostsRes> selectUserPosts(Long userIdx) throws BaseException {
        System.out.println("selectUserPosts 진입");

        Optional<User> findUser = userRepository.findById(userIdx);

        if(!findUser.isPresent()) {
            throw new BaseException(USERS_EMPTY_USER_ID);
        }

        return queryFactory
                .select(new QGetUserPostsRes(post.id, postImg.imgUrl))
                .from(postImg)
                .join(postImg.post, post)
                .where(post.user.eq(findUser.get()))
                .fetch();
    }

    public GetUserRes getUsersByEmail(String email){
        String getUsersByEmailQuery = "select userIdx,name,nickName,email from User where email=?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getLong("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByEmailParams);
    }


    public GetUserRes getUsersByIdx(int userIdx){
        String getUsersByIdxQuery = "select userIdx,name,nickName from user where userIdx=?";
        int getUsersByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getLong("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")));
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (name, nickName, phone, password) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getNickName(),postUserReq.getPhone(), postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    /*public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }*/



    public boolean isUserExist(Long userIdx){
        return userRepository.findById(userIdx).isPresent();

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set nickName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }




}
