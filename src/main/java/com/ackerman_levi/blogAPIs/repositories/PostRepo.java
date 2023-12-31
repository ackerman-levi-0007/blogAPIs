package com.ackerman_levi.blogAPIs.repositories;

import com.ackerman_levi.blogAPIs.entities.Category;
import com.ackerman_levi.blogAPIs.entities.Post;
import com.ackerman_levi.blogAPIs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}
