package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;

import java.util.UUID;

@Mapper
public interface UserRepository {
    @Select("""
           SELECT * FROM users
           WHERE user_id = #{id}
           """)
    User findById(UUID id);
    @Select("""
           SELECT * FROM users
           WHERE email = #{email}
           """)
    User findByEmail(String email);
    @Result(property = "profileImage", column = "profile_image")
    @Select("""
           INSERT INTO users (email, password, profile_image)
           VALUES ( #{u.email}, #{u.password}, #{u.profileImage})
           Returning *
           """)
    String insert(@Param("u") AppUserRequest User);
}
