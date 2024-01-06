package com.reviews.Directory.entity_model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    private String tags;  //Not to be comma separated. Todo: Tags Class & table
    private String brand;
    private Double price;

    private String description;

    // This variable is used when storing images to MySql database
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String productImageOne;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String productImageTwo;

    @ManyToOne
    @JoinColumn(name = "product_business_id")
    private Business productSubject;

    @CreationTimestamp
    private Date createdAt  = new Date();
    @UpdateTimestamp
    private Date updatedAt = null;


}
