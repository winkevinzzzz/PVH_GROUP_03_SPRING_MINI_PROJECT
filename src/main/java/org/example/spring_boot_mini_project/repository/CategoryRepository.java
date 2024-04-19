package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.typehandler.UUIDTypeHandler;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CategoryRepository {
    @Select("""
    SELECT * FROM categories
    WHERE user_id = #{userId}
    """)
    @Results(id = "catMapping" , value = {
            @Result(property = "categoryID", column = "category_id",typeHandler = UUIDTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "description" , column = "description"),
            @Result(property = "userId", column = "user_id",
            one = @One (select = "org.example.spring_boot_mini_project.repository.UserRepository.findById"))
    })
    List<Category> getAllCategory(UUID userId);

    @Select("""
    INSERT INTO categories(name, description, user_id)
    VALUES (#{category.name},(#category.description),(#{category.user.userId}))
    RETURNING *
    """)
    @ResultMap("catMapping")
    Category createCategory(@Param("category") Category category);
}
