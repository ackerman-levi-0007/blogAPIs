package com.ackerman_levi.blogAPIs.controllers;

import com.ackerman_levi.blogAPIs.payloads.ApiResponse;
import com.ackerman_levi.blogAPIs.payloads.UserDto;
import com.ackerman_levi.blogAPIs.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int id){
        UserDto updatedUser = this.userService.updateUser(userDto, id);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/getUserByID/{id}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable int id){
        return new ResponseEntity<UserDto>(this.userService.getUserByID(id), HttpStatus.FOUND);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(), HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }
}
