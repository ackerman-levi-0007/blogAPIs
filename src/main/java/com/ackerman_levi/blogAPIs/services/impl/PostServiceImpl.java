package com.ackerman_levi.blogAPIs.services.impl;

import com.ackerman_levi.blogAPIs.entities.Category;
import com.ackerman_levi.blogAPIs.entities.Post;
import com.ackerman_levi.blogAPIs.entities.User;
import com.ackerman_levi.blogAPIs.exceptions.ResourceNotFoundException;
import com.ackerman_levi.blogAPIs.payloads.CategoryDto;
import com.ackerman_levi.blogAPIs.payloads.PostDto;
import com.ackerman_levi.blogAPIs.payloads.UserDto;
import com.ackerman_levi.blogAPIs.repositories.CategoryRepo;
import com.ackerman_levi.blogAPIs.repositories.PostRepo;
import com.ackerman_levi.blogAPIs.repositories.UserRepo;
import com.ackerman_levi.blogAPIs.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;
    @Override
    public PostDto createPost(PostDto postDto, int categoryID, int userID) {
        Category category = this.categoryRepo.findById(categoryID).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryID)
        );

        User user = this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userID));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setUser(user);

        Post savedPost = this.postRepo.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postID, int categoryID, int userID) {
        Category category = this.categoryRepo.findById(categoryID).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryID)
        );

        User user = this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userID));

        Post post = this.postRepo.findById(postID).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postID));

        Post updatedPost = this.modelMapper.map(postDto, Post.class);

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setImageName(updatedPost.getImageName());
        post.setCategory(category);
        post.setUser(user);

        Post savedPost = this.postRepo.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public void deletePost(int postID) {
        Post post = this.postRepo.findById(postID).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postID));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostByID(int postID) {
        Post post = this.postRepo.findById(postID).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postID));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = this.postRepo.findAll();
        return posts.stream().map(x -> this.modelMapper.map(x,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryID) {
        Category category = this.categoryRepo.findById(categoryID).orElseThrow( () ->
                new ResourceNotFoundException("Category", "id", categoryID));
        List<Post> posts = this.postRepo.findByCategory(category);
        return posts.stream().map(x -> this.modelMapper.map(x,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUser(int userID) {
        User user = this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userID));
        List<Post> posts = this.postRepo.findByUser(user);
        return posts.stream().map(x -> this.modelMapper.map(x,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }
}
