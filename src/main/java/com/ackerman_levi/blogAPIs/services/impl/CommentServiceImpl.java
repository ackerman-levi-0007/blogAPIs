package com.ackerman_levi.blogAPIs.services.impl;

import com.ackerman_levi.blogAPIs.entities.Comment;
import com.ackerman_levi.blogAPIs.entities.Post;
import com.ackerman_levi.blogAPIs.entities.User;
import com.ackerman_levi.blogAPIs.exceptions.ResourceNotFoundException;
import com.ackerman_levi.blogAPIs.payloads.CommentDto;
import com.ackerman_levi.blogAPIs.repositories.CommentRepo;
import com.ackerman_levi.blogAPIs.repositories.PostRepo;
import com.ackerman_levi.blogAPIs.repositories.UserRepo;
import com.ackerman_levi.blogAPIs.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, int postID, int userID) {
        Post post = this.postRepo.findById(postID).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postID));

        User user = this.userRepo.findById(userID).orElseThrow(()->
                new ResourceNotFoundException("User", "id", userID));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, int commentID, int postID, int userID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentID));

        Post post = this.postRepo.findById(postID).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postID));

        User user = this.userRepo.findById(userID).orElseThrow(()->
                new ResourceNotFoundException("User", "id", userID));

        Comment updatedComment = this.modelMapper.map(commentDto, Comment.class);

        comment.setContent(updatedComment.getContent());

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto getCommentByID(int commentID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentID));
        return this.modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public void deleteComment(int commentID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentID));
        this.commentRepo.delete(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = this.commentRepo.findAll();
        return comments.stream().map(x -> this.modelMapper.map(x, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByPost(int postID) {
        Post post = this.postRepo.findById(postID).orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postID));

        List<Comment> comments = this.commentRepo.findByPost(post);
        return comments.stream().map(x -> this.modelMapper.map(x, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByUser(int userID) {
        User user = this.userRepo.findById(userID).orElseThrow(()->
                new ResourceNotFoundException("User", "id", userID));

        List<Comment> comments = this.commentRepo.findByUser(user);
        return comments.stream().map(x -> this.modelMapper.map(x, CommentDto.class)).collect(Collectors.toList());
    }
}
