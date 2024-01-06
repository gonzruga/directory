package com.reviews.Directory.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurity {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/userCreateDB/**","/userSubmitDB/**", "/saveUser/**", "/file/**", "/css/**", "/js/**","/home/**", "/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
//                .anyRequest().permitAll()  //Restrict or allow all requests
                .and()
                .formLogin()
                .and()


                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/login")
                .and().build();
    }
}
