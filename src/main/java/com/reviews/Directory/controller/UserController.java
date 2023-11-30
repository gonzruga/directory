package com.reviews.Directory.controller;


import com.reviews.Directory.dto.UserDto;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.service.StorageService;
import com.reviews.Directory.service.UserService;
import com.reviews.Directory.utils.CdnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Slf4j
@Controller
//@RestController // Use with Postman
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private StorageService storageService;

    @GetMapping("/userFormCDN")
    public String userFormCDN(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-create-form-CDN";
    }

    @PostMapping("/userSubmitCDN")
    public String saveUserCDN(@ModelAttribute UserDto user, Model model, @RequestParam("imageFile") MultipartFile multipartFile) {
        model.addAttribute("user", user);
        Optional<String> optionalUrl = CdnUtils.uploadFile(multipartFile);
        optionalUrl.ifPresent(user::setProfilePicUrl);
        service.saveUserCDN(user);
        return "user-create-submit-CDN";
    }

    @GetMapping("/userLogin")
    public String userLoginForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-login";
    }

    @GetMapping("/userFormDB")
    public String userForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-create-form-DB";
    }

    @PostMapping("/userSubmitDB")
    public String saveUser(@ModelAttribute UserDto user, Model model, @RequestParam("imageFile") MultipartFile file)
    {
        model.addAttribute("user", user);
        service.saveUserDB(user, file);
        return "user-create-submit-DB";
    }

    @GetMapping("/userFormAWS")
    public String userFormAWS(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-create-form-AWS";
    }
    @PostMapping("/userSubmitAWS")
    public String saveUserAWS(@ModelAttribute UserDto user, Model model, @RequestParam("imageFile") MultipartFile multipartFile) {
        model.addAttribute("user", user);
        user.setProfilePicUrl(storageService.uploadFile(multipartFile));
        service.saveUserAWS(user);
        return "user-create-submit-CDN";
    }


//READ - GET

    @GetMapping("/userList")
    public String findAllUsers(Model model) {
        model.addAttribute("user", service.getUsers());
        return "user-list";
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable long id) {
        return service.getUserById(id);
    }

    @GetMapping("/userPage/{id}")
    public String userPage(@PathVariable long id, Model model) {
        User userById = service.getUserById(id);
        model.addAttribute("user", userById);
        log.info("User : {}", userById.getProfilePicUrl());
        return "user-page";
    }

// UPDATE - PUT

    @GetMapping("/userEdit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        User user = new User();
        user = service.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/userUpdate/")
    public String updateUser(@ModelAttribute UserDto user) {
        service.updateUser(user);
        return "redirect:/userPage/{id}";
    }

// DELETE

    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable long id) {
        service.deleteUser(id);
        return "redirect:/userList";
    }

}
