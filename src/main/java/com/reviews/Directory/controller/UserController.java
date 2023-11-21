package com.reviews.Directory.controller;


import com.reviews.Directory.dto.UserDto;
import com.reviews.Directory.entity_model.ImageModel;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.service.UserService;
import com.reviews.Directory.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService service;

//FORMS

    @GetMapping("/userLogin")
    public String userLoginForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "user-login";
    }

    // Form to Create User
    @GetMapping("/userCreateDto")
    public String userFormDto(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "user-create-form";
    }

    @GetMapping("/userCreateForm")
    public String userForm(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "user-create-form";
    }

    // Form to display User details after User creation
    @PostMapping("/userSubmit")
    public String userSubmit(@ModelAttribute UserDto user, Model model) {
        model.addAttribute("user", user);
        service.saveUserDto(user);
        return "user-create-submit";
    }


//CREATE - POST

    @PostMapping("/addUser")
    public User addUser(@RequestBody UserDto user) {return service.saveUserDto(user);}

//    With Image upload

    @PostMapping("/saveUserDto")
    public String saveUser(UserDto user, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());  // Error: required ObjectNonNull
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setProfilePicName(fileName);
            User savedUser = service.saveUserDto(user);
            String upload = "src/main/resources/static/images/users/" + savedUser.getId() + "/";

            FileUploadUtil.saveFile(upload,fileName,multipartFile); // Exception added upon safeFile

        }else{
            if(user.getProfilePicName().isEmpty()){
                user.setProfilePicName(null);
                service.saveUserDto(user);
            }
        }
        service.saveUserDto(user);

        return "redirect:/userList";
    }


    @PostMapping("/saveUser")
    public String saveUser(User user, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());  // Error: required ObjectNonNull
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setProfilePicName(fileName);
            User savedUser = service.saveUser(user);
            String upload = "src/main/resources/static/images/users/" + savedUser.getId() + "/";

            FileUploadUtil.saveFile(upload,fileName,multipartFile); // Exception added upon safeFile

        }else{
            if(user.getProfilePicName().isEmpty()){
                user.setProfilePicName(null);
                service.saveUser(user);
            }
        }
        service.saveUser(user);

        return "redirect:/userList";
    }

    @PostMapping(value = {"/addUser2"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public User addUser(@RequestPart("user") UserDto user,
                        @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<ImageModel> images = uploadImage(file);
            user.setUserImage(images);
            // Save to Database
            return service.saveUserDto(user);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Method to process image file then back to addUser to call it and save image and add to database
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file: multipartFiles) {
            // Create object of imageModel with parameterized constructor. Can also use without i.e. new ImageModel()
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()   //Could be chance of IOExcetion
            );
            // Add imageModel to set of ImageModels
            imageModels.add(imageModel);
        }

        return imageModels;
    }

//READ - GET

    @GetMapping("/userList")
    public String findAllUsers(Model model){
        model.addAttribute("user", service.getUsers());
        return "user-list";
    }

    @GetMapping("/user/{id}")
public User findUserById(@PathVariable long id) { return service.getUserById(id); }

    @GetMapping("/userPage/{id}")
    public String userPage(@PathVariable long id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "user-page";
    }

// UPDATE - PUT

    @GetMapping("/userEdit/{id}")
    public String editUser(@PathVariable long id, Model model){
        User user = new User();
        user = service.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/userUpdate/{id}")
    public String updateUser(@ModelAttribute UserDto user, @PathVariable long id) {
        service.updateUser(user);
        return "redirect:/userPage/{id}";
    }

// DELETE

    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable long id){
        service.deleteUser(id);
        return "redirect:/userList";
    }
}
