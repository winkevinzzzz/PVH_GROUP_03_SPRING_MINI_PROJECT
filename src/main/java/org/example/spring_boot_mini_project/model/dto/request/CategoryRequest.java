package org.example.spring_boot_mini_project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "name can't be blank")
    private String name;
    @NotBlank(message = "description can't be blank")
    private String description;

}
