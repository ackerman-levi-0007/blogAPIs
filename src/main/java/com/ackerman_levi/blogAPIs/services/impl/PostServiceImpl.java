package com.ackerman_levi.blogAPIs.services.impl;

import com.ackerman_levi.blogAPIs.entities.Category;
import com.ackerman_levi.blogAPIs.entities.Post;
import com.ackerman_levi.blogAPIs.entities.User;
import com.ackerman_levi.blogAPIs.exceptions.ResourceNotFoundException;
import com.ackerman_levi.blogAPIs.payloads.PostDto;
import com.ackerman_levi.blogAPIs.payloads.PostResponse;
import com.ackerman_levi.blogAPIs.repositories.CategoryRepo;
import com.ackerman_levi.blogAPIs.repositories.PostRepo;
import com.ackerman_levi.blogAPIs.repositories.UserRepo;
import com.ackerman_levi.blogAPIs.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostDto updatePost(PostDto postDto, int postID) {
        Post post = this.postRepo.findById(postID).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postID));

        Post updatedPost = this.modelMapper.map(postDto, Post.class);

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setImageName(updatedPost.getImageName());

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
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort );
        Page<Post> pagePost = this.postRepo.findAll(p);

        List<PostDto> postDto = pagePost.getContent().stream().map(x -> this.modelMapper.map(x,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
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
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map(x -> this.modelMapper.map(x, PostDto.class)).collect(Collectors.toList());
    }
}
