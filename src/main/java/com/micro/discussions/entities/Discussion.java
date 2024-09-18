package com.micro.discussions.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "discussion", schema = "public")
@Getter
@Setter
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long author;
    private long advertisement;
    private String name;
    @Column(columnDefinition = "text", length = 1000, nullable = false)
    private String text;
    @OneToMany(mappedBy = "discussion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> comments;
}
