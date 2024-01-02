package com.ackerman_levi.blogAPIs.services;

import com.ackerman_levi.blogAPIs.entities.Post;
import com.ackerman_levi.blogAPIs.payloads.PostDto;
import com.ackerman_levi.blogAPIs.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, int categoryID, int userID);
    PostDto updatePost(PostDto postDto, int postID);
    void deletePost(int postID);
    PostDto getPostByID(int postID);
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
    List<PostDto> getPostsByCategory(int categoryID);
    List<PostDto> getPostByUser(int userID);
    List<PostDto> searchPosts(String keyword);
}
