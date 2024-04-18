package org.example.spring_boot_mini_project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApiResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer code;
}