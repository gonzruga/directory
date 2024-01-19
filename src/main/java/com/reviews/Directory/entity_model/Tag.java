package com.reviews.Directory.entity_model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    // TODO: Make Tag Title unique
    @Column(unique = true)
    private String tagTitle;
    private String tagDescription;

    @CreationTimestamp
    private Date createdAt = new Date();
    @UpdateTimestamp
    private Date updatedAt = null;

    // Create for new instant in ProductController country list
    public Tag(String tagTitle, String tagDescription) {
        super();
        this.tagTitle = tagTitle;
        this.tagDescription = tagDescription;
    }
}
