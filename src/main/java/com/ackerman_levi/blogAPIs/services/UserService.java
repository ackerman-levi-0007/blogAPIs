package com.ackerman_levi.blogAPIs.services;

import com.ackerman_levi.blogAPIs.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, int userId);
    UserDto getUserByID(int userId);
    List<UserDto> getAllUsers();
    void deleteUser(int userId);
}
