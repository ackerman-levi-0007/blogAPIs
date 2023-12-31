package com.ackerman_levi.blogAPIs.payloads;

import com.ackerman_levi.blogAPIs.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private int id;
    private String title;
    private String content;
    private String imageName = "default.png";
    private CategoryDto category;
    private UserDto user;
    private Date createdOn;
}
