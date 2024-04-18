package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface OtpRepository {
    @Insert("""
       INSERT INTO otps (otp_code,issued_at,expiration,verify,user_id)
       VALUES (#{otpRequest.otpCode},#{otpRequest.issuedAt},#{otpRequest.expiration},#{otpRequest.verify},
        #{otpRequest.user})
    """)
    void insertOtp(@Param("otpRequest") OtpRequest otpRequest);
}
