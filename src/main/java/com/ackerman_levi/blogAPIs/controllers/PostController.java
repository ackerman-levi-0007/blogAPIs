package com.ackerman_levi.blogAPIs.controllers;

import com.ackerman_levi.blogAPIs.payloads.ApiResponse;
import com.ackerman_levi.blogAPIs.payloads.PostDto;
import com.ackerman_levi.blogAPIs.payloads.PostResponse;
import com.ackerman_levi.blogAPIs.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/createPost/{categoryID}/{userID}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int categoryID, @PathVariable int userID){
        PostDto savedPostDto = this.postService.createPost(postDto, categoryID, userID);
        return new ResponseEntity<PostDto>(savedPostDto, HttpStatus.CREATED);
    }

    @PutMapping("/updatePost/{postID}/{categoryID}/{userID}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postID, @PathVariable int categoryID, @PathVariable int userID){
        PostDto updatedPostDto = this.postService.updatePost(postDto, postID, categoryID, userID);
        return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePost/{postID}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postID){
        this.postService.deletePost(postID);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("/getPostByID/{postID}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable int postID){
        PostDto postDto = this.postService.getPostByID(postID);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.FOUND);
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<PostResponse> getPostByID(
            @RequestParam(value = "pageNumber", defaultValue = "1" , required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        PostResponse postResponses = this.postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponses, HttpStatus.FOUND);
    }

    @GetMapping("/getPostsByCategoryID/{categoryID}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryID(@PathVariable int categoryID){
        List<PostDto> postDto = this.postService.getPostsByCategory(categoryID);
        return new ResponseEntity<List<PostDto>>(postDto, HttpStatus.FOUND);
    }

    @GetMapping("/getPostsByUserID/{userID}")
    public ResponseEntity<List<PostDto>> getPostsByUserID(@PathVariable int userID){
        List<PostDto> postDto = this.postService.getPostByUser(userID);
        return new ResponseEntity<List<PostDto>>(postDto, HttpStatus.FOUND);
    }
}
