package com.reviews.Directory.service;

import com.reviews.Directory.dto.UserDto;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    //CREATE - POST
     public User saveUser(UserDto user) {return repository.save(user.dtoToUser());}


    //READ - GET

    public Optional<User> findByEmail(String email){
         try{
             return repository.findFirstByUserEmail(email);
         }catch (Exception e){
            log.error("{} found while getting user by email : {}",e.getClass().getSimpleName(),e.getMessage());
            return Optional.empty();
         }
    }

    public List<User> getUsers() {return repository.findAll();}

    public User getUserById(Long id) {return repository.findById(id).orElse(null);}

    //DELETE

    public String deleteUser(long id){
        repository.deleteAllById(Collections.singleton(id));
        // deleteAllById(id)
        return "User removed with ID number: "+ id;
    }

    //UPDATE

    public User updateUser(UserDto user) {
        User existingUser = repository.findById(user.getId()).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMobile(user.getMobile());
        existingUser.setUserEmail(user.getUserEmail());

//        existingUser.setPassword(user.getPassword());
//        existingUser.setProfilePicUrl(user.getProfilePic());

        existingUser.setUpdatedAt(new Date());

        return repository.save(existingUser);
    }


}
