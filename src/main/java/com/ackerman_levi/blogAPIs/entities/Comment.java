package com.ackerman_levi.blogAPIs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @ManyToOne
    private Post post;

    @OneToOne
    private User user;

    @CreationTimestamp
    @Column(name = "comment_createdOn")
    private Date createdOn;
}
