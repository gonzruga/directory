package com.reviews.Directory.controller;


import com.reviews.Directory.dto.UserDto;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.service.UserService;
import com.reviews.Directory.utils.CdnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

//    temporary
    @PostMapping("/userSubmitTemp")
    public String saveUserAndImage(@RequestParam("file") MultipartFile file,
                                   @RequestParam("firstName") String firstName)
    {
        service.saveUserAndImageToDB(file, firstName);
        return "user-create-submit-dto";
    }


    @Autowired
    private UserService service;
    @GetMapping("/userLogin")
    public String userLoginForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-login";
    }

    @GetMapping("/userCreateForm")
    public String userForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-create-form";
    }

    @PostMapping("/userSubmit")
    public String saveUser(@ModelAttribute UserDto user, Model model, @RequestParam("file") MultipartFile file)
    {
        model.addAttribute("user", user);
        service.saveUser(user, file);
        return "user-create-submit";
    }

    @PostMapping("/userSubmitCDN")
    public String saveUserCdn(@ModelAttribute UserDto user, Model model, @RequestPart MultipartFile multipartFile) {

        model.addAttribute("user", user);
        CdnUtils.uploadFile(multipartFile);
        user.setProfilePicFile(multipartFile);

        service.saveUserCDN(user);
        return "user-create-submit";
    }


    /*

    @PostMapping("/saveUserDto")
    public String saveUser(UserDto user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());  // Error: required ObjectNonNull
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setProfilePicName(fileName);
            User savedUser = service.saveUserDto(user);
            String upload = "src/main/resources/static/images/users/" + savedUser.getId() + "/";

            FileUploadUtil.saveFile(upload, fileName, multipartFile); // Exception added upon safeFile

        } else {
            if (user.getProfilePicName().isEmpty()) {
                user.setProfilePicName(null);
                service.saveUserDto(user);
            }
        }
        service.saveUserDto(user);

        return "redirect:/userList";
    }



    @PostMapping("/saveUser")
    public String saveUser(User user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());  // Error: required ObjectNonNull
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setProfilePicName(fileName);
            User savedUser = service.saveUser(user);
            String upload = "src/main/resources/static/images/users/" + savedUser.getId() + "/";

            FileUploadUtil.saveFile(upload, fileName, multipartFile); // Exception added upon safeFile

        } else {
            if (user.getProfilePicName().isEmpty()) {
                user.setProfilePicName(null);
                service.saveUser(user);
            }
        }
        service.saveUser(user);

        return "redirect:/userList";
    }

    //    Learn Yourself https://www.youtube.com/watch?v=deYVx0qF5EY
    @PostMapping(value = {"/saveUser2Dto"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public User saveUser2(@RequestPart("user") UserDto user,
                          @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<ImageModel> images = uploadImage(file);
            user.setUserImage(images);
            // Save to Database
            return service.saveUserDto(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //    Learn Yourself https://www.youtube.com/watch?v=deYVx0qF5EY   RequestPart("user")
    @PostMapping(value = {"/saveUser2"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String saveUser2(User user,
                            @RequestPart("image") MultipartFile[] file) {
        try {
            Set<ImageModel> images = uploadImage(file);
            user.setUserImage(images);
            // Save to Database
            service.saveUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return "redirect:/userList";
    }

    //Method to process image file then back to addUser to call it and save image and add to database
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
            // Create object of imageModel with parameterized constructor. Can also use without i.e. new ImageModel()
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()   //Could be chance of IOExcetion but will be handled in saveUser so here use 'throws'
            );
            // Add imageModel to set of ImageModels
            imageModels.add(imageModel);
        }

        return imageModels;
    }

     */

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
