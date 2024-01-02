package com.ackerman_levi.blogAPIs.controllers;

import com.ackerman_levi.blogAPIs.config.AppConstant;
import com.ackerman_levi.blogAPIs.payloads.ApiResponse;
import com.ackerman_levi.blogAPIs.payloads.PostDto;
import com.ackerman_levi.blogAPIs.payloads.PostResponse;
import com.ackerman_levi.blogAPIs.services.FileService;
import com.ackerman_levi.blogAPIs.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @PostMapping("/createPost/{categoryID}/{userID}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int categoryID, @PathVariable int userID){
        PostDto savedPostDto = this.postService.createPost(postDto, categoryID, userID);
        return new ResponseEntity<PostDto>(savedPostDto, HttpStatus.CREATED);
    }

    @PutMapping("/updatePost/{postID}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postID){
        PostDto updatedPostDto = this.postService.updatePost(postDto, postID);
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
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        PostResponse postResponses = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
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

    @GetMapping("/searchPosts/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
        List<PostDto> postDto = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(postDto, HttpStatus.FOUND);
    }

    @PostMapping("/image/upload/{postID}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable int postID
    ) throws IOException {
        PostDto postDto = this.postService.getPostByID(postID);

        String fileName = this.fileService.uploadFile(AppConstant.POST_FOLDER,image);

        postDto.setImageName(fileName);
        PostDto updatedPostDto = this.postService.updatePost(postDto,postID);

        return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
    }

    @GetMapping(value = "/image/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.downloadFile(AppConstant.POST_FOLDER, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }
}
