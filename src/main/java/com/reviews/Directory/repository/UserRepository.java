package com.reviews.Directory.repository;

import com.reviews.Directory.entity_model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getUserById(Long id);

//    List<User> findAllByUserId(Long id);

    Optional<User>  findFirstByUserEmail(String userEmail);

}
