package com.ackerman_levi.blogAPIs.repositories;

import com.ackerman_levi.blogAPIs.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
