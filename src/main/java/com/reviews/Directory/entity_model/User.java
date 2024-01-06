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
@Table(name = "user")
@RequiredArgsConstructor
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id", nullable = false)
     private Long id;

     private String firstName;
     private String lastName;
     private String mobile;

//     TODO: Make emails unique. Was giving error maybe because of multiple saving events during image upload
//     @Column(unique = true)
     private String userEmail;
     private String password;

     // This variable is used when storing images to MySql database
     @Lob
     @Column(columnDefinition = "MEDIUMBLOB")
     private String profilePicture;

     // This variable is used when storing images to AWS and Fasthub CDN.Utils
     private String profilePicUrl;

     @CreationTimestamp
     private Date createdAt  = new Date();
     @UpdateTimestamp
     private Date updatedAt = null;


}
