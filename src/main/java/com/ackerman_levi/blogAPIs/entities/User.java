package com.ackerman_levi.blogAPIs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private int id;
    @Column(name="user_name", nullable = false, length = 100)
    private String name;
    @Column(name="user_email", nullable = false, length = 40)
    private String email;
    @Column(name="user_password", nullable = false, length = 100)
    private String password;
    @Column(name="user_about", nullable = false)
    private String about;
}
