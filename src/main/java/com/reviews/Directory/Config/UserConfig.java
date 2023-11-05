package com.reviews.Directory.Config;

import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final UserService userService;

    @Bean
    UserDetailsService userDetailsService(){
        return  username -> {
            Optional<User> optionalUser = userService.findByEmail(username);
            if(!optionalUser.isPresent()) throw new UsernameNotFoundException("Invalid credentials");

            User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(
                    username,
                    user.getPassword(),
                    Collections.emptyList()
            );
        };
    }


    @Bean
    PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }

}


