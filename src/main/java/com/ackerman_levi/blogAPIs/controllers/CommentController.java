package com.ackerman_levi.blogAPIs.controllers;

import com.ackerman_levi.blogAPIs.payloads.ApiResponse;
import com.ackerman_levi.blogAPIs.payloads.CommentDto;
import com.ackerman_levi.blogAPIs.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment/{postID}/{userID}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable int postID,
            @PathVariable int userID
    ){
        CommentDto savedCommentDto = this.commentService.createComment(commentDto, postID, userID);
        return new ResponseEntity<CommentDto>(savedCommentDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateComment/{commentID}/{postID}/{userID}")
    public ResponseEntity<CommentDto> updateComment(
            @RequestBody CommentDto commentDto,
            @PathVariable int commentID,
            @PathVariable int postID,
            @PathVariable int userID
    ){
        CommentDto updatedCommentDto = this.commentService.updateComment(commentDto, commentID, postID, userID);
        return new ResponseEntity<CommentDto>(updatedCommentDto, HttpStatus.OK);
    }

    @GetMapping("/getCommentByID/{commentID}")
    public ResponseEntity<CommentDto> getCommentByID(@PathVariable int commentID){
        CommentDto commentDto = this.commentService.getCommentByID(commentID);
        return new ResponseEntity<CommentDto>(commentDto, HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteComment/{commentID}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentID){
        this.commentService.deleteComment(commentID);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtos = this.commentService.getAllComments();
        return new ResponseEntity<List<CommentDto>>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/getAllCommentsByPost/{postID}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable int postID){
        List<CommentDto> commentDtos = this.commentService.getAllCommentsByPost(postID);
        return new ResponseEntity<List<CommentDto>>(commentDtos, HttpStatus.FOUND);
    }

    @GetMapping("/getAllCommentsByUser/{userID}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@PathVariable int userID){
        List<CommentDto> commentDtos = this.commentService.getAllCommentsByUser(userID);
        return new ResponseEntity<List<CommentDto>>(commentDtos, HttpStatus.FOUND);
    }
}
