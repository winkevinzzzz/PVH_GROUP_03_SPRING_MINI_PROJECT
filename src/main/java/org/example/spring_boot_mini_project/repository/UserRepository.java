package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.config.typeHandler;

import java.util.UUID;

@Mapper
public interface UserRepository {

    @Select("""
           SELECT * FROM users
           WHERE user_id = #{id}::UUID
           """)
    @Results(id = "UserMapping",value ={
            @Result(property = "profileImage", column = "profile_image"),
            @Result(property = "userId", column = "user_id",typeHandler = typeHandler.class)
    })
    User findById(UUID id);
    @Select("""
           SELECT * FROM users
           WHERE email = #{email}
           """)
    @ResultMap("UserMapping")
    User findByEmail(String email);

    @Select("""
           INSERT INTO users (email, password, profile_image)
           VALUES ( #{u.email}, #{u.password}, #{u.profileImage})
           Returning *
           """)
    @ResultMap("UserMapping")
    User insert(@Param("u") AppUserRequest User);
}
