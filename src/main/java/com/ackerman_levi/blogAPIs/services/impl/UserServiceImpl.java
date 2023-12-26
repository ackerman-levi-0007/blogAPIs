package com.ackerman_levi.blogAPIs.services.impl;

import com.ackerman_levi.blogAPIs.entities.User;
import com.ackerman_levi.blogAPIs.exceptions.ResourceNotFoundException;
import com.ackerman_levi.blogAPIs.payloads.UserDto;
import com.ackerman_levi.blogAPIs.repositories.UserRepo;
import com.ackerman_levi.blogAPIs.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);

        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserByID(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(user);
    }

    public UserDto userToUserDto (User user){
        return this.modelMapper.map(user, UserDto.class);
    }

    public User userDtoToUser (UserDto userDto){
        return this.modelMapper.map(userDto, User.class);
    }
}
