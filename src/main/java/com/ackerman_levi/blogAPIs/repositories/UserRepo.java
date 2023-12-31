package com.ackerman_levi.blogAPIs.repositories;

import com.ackerman_levi.blogAPIs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
