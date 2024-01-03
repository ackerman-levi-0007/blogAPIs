package com.ackerman_levi.blogAPIs.services;

import com.ackerman_levi.blogAPIs.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, int postID, int userID);
    CommentDto updateComment(CommentDto commentDto, int commentID, int postID, int userID);
    CommentDto getCommentByID(int commentID);
    void deleteComment(int commentID);
    List<CommentDto> getAllComments();
    List<CommentDto> getAllCommentsByPost(int postID);
    List<CommentDto> getAllCommentsByUser(int userID);
}
