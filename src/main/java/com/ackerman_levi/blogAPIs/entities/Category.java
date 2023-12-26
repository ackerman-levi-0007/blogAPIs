package com.ackerman_levi.blogAPIs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    public int id;
    @Column(name = "category_title", nullable = false, length = 50)
    public String title;
    @Column(name = "category_description", nullable = false, length = 250)
    public  String description;
}
