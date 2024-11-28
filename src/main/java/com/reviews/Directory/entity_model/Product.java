package com.reviews.Directory.entity_model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String productName;
    private String alternativeNames; // Todo: review approach. Array etc.
    // List
//    private String alternativeName2;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_tag",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagList;

    private String brand;
    private Double price;

    private String description;
    private String paymentLink;

    private String imageOneUrl;

    private String imageTwoUrl;

//    private boolean sponsored;
    private int sponsorLevel = 0;
    private Date paymentDate  = new Date();

    @ManyToOne
    @JoinColumn(name = "product_business_id")
    private Business productSubject;

    @CreationTimestamp
    private Date createdAt  = new Date();
    @UpdateTimestamp
    private Date updatedAt = null;

//    @PrePersist
//    void preinsert(){
//        this.reference = UUID.randomUUID().toString();
//    }

}
