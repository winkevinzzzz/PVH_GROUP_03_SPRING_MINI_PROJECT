package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.example.spring_boot_mini_project.config.typeHandler;
import org.example.spring_boot_mini_project.model.Otp;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Mapper
public interface OtpRepository {

    @Insert("""
       INSERT INTO otps (otp_code,issued_at,expiration,verify,user_id)
       VALUES (#{otpRequest.otpCode},#{otpRequest.issuedAt},#{otpRequest.expiration},#{otpRequest.verify},
        #{otpRequest.user})
    """)
    void insertOtp(@Param("otpRequest") OtpRequest otpRequest);

    @Select("""
       SELECT * FROM otps
       where otp_code=#{code}
    """)
    @Result(property ="otpCode",column = "otp_code")
//    @Result(property ="issuedAt",column = "issued_at")
//    @Result(property ="userId",column = "user_id")
    Otp getOtpByCode(String code);

    @Update("""
    UPDATE otps SET verify = #{otp.verify} WHERE otp_code = #{otp.otpCode}
    """)
    void updateVerifyAfterVerified(@Param("otp") Otp otp);
    @Select("""
    SELECT o.*, u.username  -- Select all Otp columns (o.*) and username from User (u)
    FROM otps o
    INNER JOIN users u ON o.user_id = u.id  -- Join otps and users on user_id
    WHERE o.user_id = #{user}
""")
    @Results(id = "OtpMapping", value = {
            @Result(property = "otpId", column = "otp_id", typeHandler = typeHandler.class),
            @Result(property = "otp_code", column = "otp_code"),
            @Result(property = "issuedAt", column = "issued_at"),
            @Result(property = "user", column = "user_id", javaType = User.class, typeHandler = typeHandler.class)  // Map user_id to user property
    })
   // Otp getOtpByUserId(UUID userId);

    Otp getOtpByUserId(UUID user);

    //    @Result(property = "userId",typeHandler = typeHandler.class,column = "user_id")
   // Otp getOtpByUserId(UUID userId);
}
