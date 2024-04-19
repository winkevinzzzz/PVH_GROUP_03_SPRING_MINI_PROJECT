package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.typeHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Mapper
public interface UserRepository {

    @Select("""
           SELECT * FROM users
           WHERE user_id = #{id}
           """)
    @Results(id = "userMapping", value = {
            @Result(property = "profileImage", column = "profile_image"),
            @Result(property = "userId", column = "user_id",typeHandler = typeHandler.class),
            @Result(property = "email", column = "email")
    })
    User findById(UUID id);
    @Select("""
           SELECT * FROM users
           WHERE email = #{email}
           """)
    @ResultMap("userMapping")
    User findByEmail(String email);
    @Result(property = "profileImage", column = "profile_image")
    @Result(property = "userId", column = "user_id",typeHandler = typeHandler.class)
    @Select("""
           INSERT INTO users (email, password, profile_image)
           VALUES ( #{u.email}, #{u.password}, #{u.profileImage})
           Returning *
           """)
    User insert(@Param("u") AppUserRequest User);
}
