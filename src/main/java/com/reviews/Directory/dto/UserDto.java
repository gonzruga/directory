package com.reviews.Directory.dto;

import com.reviews.Directory.entity_model.ImageModel;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.utils.CdnUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

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

    private String profilePicUrl;
    private MultipartFile profilePicFile;
    private Set<ImageModel> userImage;

    private String profilePicName;

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

        user.setProfilePicName(profilePicName);


        Optional<String> optionalUrl = CdnUtils.uploadFile(profilePicFile);

        optionalUrl.ifPresent(user::setProfilePicUrl);

        return user;

    }

}
