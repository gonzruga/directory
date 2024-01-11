package com.reviews.Directory.entity_model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String businessName;
    private String email;
    private String mobile;

    private String physicalAddress;
    private String location;

    private String category;
    private String tinNumber;
    private String link;

    private String logoUrl;

    @CreationTimestamp
    private Date createdAt = new Date();
    @UpdateTimestamp
    private Date updatedAt = null;

    @OneToMany(mappedBy = "productSubject", fetch = FetchType.EAGER)
    private Set<Product> products;

    @OneToMany(mappedBy = "reviewSubject", fetch = FetchType.EAGER)
    private Set<Review> reviews;
    // Array is not good because fetching them can cause errors. Instead I use Set


}
