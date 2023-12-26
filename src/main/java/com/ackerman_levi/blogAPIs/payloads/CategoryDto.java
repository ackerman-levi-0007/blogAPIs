package com.ackerman_levi.blogAPIs.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    public int id;

    @NotEmpty
    @Size(max = 50, message = "Title cannot be of length more than 50 !!!")
    public String title;

    @NotEmpty
    @Size(max = 250, message = "Description cannot be of length more than 250 !!!")
    public String description;
}
