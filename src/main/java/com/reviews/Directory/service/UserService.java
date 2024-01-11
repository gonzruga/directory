package com.reviews.Directory.service;

import com.reviews.Directory.dto.UserDto;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository repository;


    //CREATE - POST

    // Zaaim: https://www.youtube.com/watch?v=oTJ89wcz5Ec&t=1138s

    public User saveUser(UserDto user) {return repository.save(user.dtoToUser());}


    // READ - GET

    public List<User> getUsers() {return repository.findAll();}

    public User getUserById(Long id) {return repository.findById(id).orElse(null);}

    public Optional<User> findByEmail(String email){
        try{
            return repository.findFirstByUserEmail(email);
        }catch (Exception e){
            log.error("{} found while getting user by email : {}",e.getClass().getSimpleName(),e.getMessage());
            return Optional.empty();
        }
    }

    // DELETE

    public String deleteUser(long id){
//        repository.deleteAllById(Collections.singleton(id));
//        TODO: Revise/review logs
        log.info("Removing user with ID number: " + id);
        repository.deleteById(id);
         log.info("User removed with ID number: " + id);
        return "User removed with ID number: " + id;
    }

    // UPDATE - PUT

    public User updateUser(UserDto user) {
        User existingUser = repository.findById(user.getId()).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMobile(user.getMobile());
        existingUser.setUserEmail(user.getUserEmail());

//        existingUser.setPassword(user.getPassword());
//        existingUser.setProfilePicUrl(user.getProfilePic());
//        existingUser.setImage(user.getImage());

        existingUser.setUpdatedAt(new Date());

        return repository.save(existingUser);
    }


}
