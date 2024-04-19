package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.spring_boot_mini_project.config.typeHandler;
import org.example.spring_boot_mini_project.model.Otp;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Mapper
public interface OtpRepository {

    @Insert("""
       INSERT INTO otps (otp_code,issued_at,expiration,verify,user_id)
       VALUES (#{otpRequest.otpCode},#{otpRequest.issuedAt},#{otpRequest.expiration},#{otpRequest.verify},
        #{otpRequest.user}::UUID)
    """)
    @Results(id = "OtpMapping", value = {
            @Result(property = "otpId", column = "otp_id", typeHandler = typeHandler.class),
            @Result(property = "otpCode", column = "otp_code"),  // Use camelCase for property names
            @Result(property = "issuedAt", column = "issued_at"),
            @Result(property = "userId", column = "user_id",typeHandler = typeHandler.class, one = @One(select = "org.example.spring_boot_mini_project.repository.UserRepository.findById"))
    })
    void insertOtp(@Param("otpRequest") OtpRequest otpRequest);
    @Select("""
        SELECT * FROM otps
        WHERE otp_code = #{otpCode}
    """)
    @Results(id = "otpMapping", value = {
            @Result(property = "otpId", column = "otp_id"),
            @Result(property = "otpCode", column = "otp_code"),
            @Result(property = "issuedAt", column = "issued_at"),
            @Result(property = "user",column = "user_id",
            one = @One(select = "org.example.spring_boot_mini_project.repository.UserRepository.findById"))
    })
    Otp getOtpByCode (@Param("otpCode") String otpCode );

    @Update("""
    UPDATE otps
    SET otp_code = #{otp.otpCode},issued_at = #{otp.issuedAt},expiration = #{otp.expiration}
    WHERE user_id=#{userId}::UUID
    """)
    void updateOtpCodeAfterResend(@Param("otp") OtpRequest otpRequest, UUID userId);

    @Update("""
    UPDATE otps SET verify = #{otp.verify} WHERE otp_code = #{otp.otpCode}
    """)
    void updateVerifyAfterVerified(@Param("otp") Otp otp);
    @Select("""
    SELECT * FROM otps
    WHERE user_id =#{user}::UUID
""")
    @Results(id = "OtpMapping", value = {
            @Result(property = "otpId", column = "otp_id", typeHandler = typeHandler.class),
            @Result(property = "otpCode", column = "otp_code"),  // Use camelCase for property names
            @Result(property = "issuedAt", column = "issued_at"),
            @Result(property = "user",typeHandler = typeHandler.class, column = "user_id", one = @One(select = "org.example.spring_boot_mini_project.repository.UserRepository.findById"))
    })

    Otp getOtpByUserId(UUID user);
}



