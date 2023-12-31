package com.ackerman_levi.blogAPIs.services;

import com.ackerman_levi.blogAPIs.entities.Post;
import com.ackerman_levi.blogAPIs.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, int categoryID, int userID);
    PostDto updatePost(PostDto postDto, int postID, int categoryID, int userID);
    void deletePost(int postID);
    PostDto getPostByID(int postID);
    List<PostDto> getAllPosts(int pageNumber,int pageSize);
    List<PostDto> getPostsByCategory(int categoryID);
    List<PostDto> getPostByUser(int userID);
    List<PostDto> searchPosts(String keyword);
}
