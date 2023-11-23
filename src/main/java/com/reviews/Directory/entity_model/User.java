package com.reviews.Directory.entity_model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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


     private String profilePicUrl;
//     private MultipartFile profilePicFile;

//     private String profilePic;

     @Column(length = 64)
     private String profilePicName;

     @CreationTimestamp
     private Date createdAt  = new Date();
     @UpdateTimestamp
     private Date updatedAt = null;

     @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //FetchType.Lazy | CascadeType.Merge, Detach, Remove ++
     @JoinTable(name = "user_images",
          joinColumns = {
             @JoinColumn(name = "id")
          },
             inverseJoinColumns = {
             @JoinColumn(name = "imageId")
             }
     )

     private Set<ImageModel> userImage;

}
