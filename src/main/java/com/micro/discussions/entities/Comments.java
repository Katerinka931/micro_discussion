package com.micro.discussions.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String author;
    @Column
    private String text;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "discussion_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Discussion discussion;
}
