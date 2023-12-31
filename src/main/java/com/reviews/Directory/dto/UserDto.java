package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDto {

    private Long id;

    private String firstName;
    private String lastName;
    private String mobile;

    private String userEmail;
    private String password;

    private String profilePicture;

    private String profilePicUrl;

    private Date createdAt  = new Date();
    private Date updatedAt = null;

    public User dtoToUser() {

        final User user = new User();

        user.setId(id);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMobile(mobile);

        user.setUserEmail(userEmail);
        user.setPassword(password);

        // When storing images to MySql
        user.setProfilePicture(profilePicture);

        // When storing images to Fasthub CDN.Utils & Amazon AWS
        user.setProfilePicUrl(profilePicUrl);


        return user;

    }

}