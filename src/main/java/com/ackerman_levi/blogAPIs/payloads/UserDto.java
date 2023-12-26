package com.ackerman_levi.blogAPIs.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(max = 50, message = "Name should not be empty and should not be more than 30 characters !!")
    private String name;

    @Email
    private String email;

    @NotEmpty
    @Size(max = 10, message = "Password should not be empty and should not be more than 10 characters !!")
    private String password;

    @Size(max = 200, message = "About should not be more than 200 characters !!")
    private String about;
}
