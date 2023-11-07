package com.reviews.Directory.controller;


import com.reviews.Directory.dto.UserDto;
import com.reviews.Directory.entity_model.User;
import com.reviews.Directory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

//FORMS

    @GetMapping("/userLogin")
    public String userLoginForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "user-login";
    }

    // Form to Create User
    @GetMapping("/userCreateForm")
    public String userForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "user-create-form";
    }

    // Form to display User details after User creation
    @PostMapping("/userSubmit")
    public String userSubmit(@ModelAttribute UserDto user, Model model) {
        model.addAttribute("user", user);
        service.saveUser(user);
        return "redirect:/user-list";
    }


//CREATE - POST

    @PostMapping("/addUser")
    public User addUser(@RequestBody UserDto user) {return service.saveUser(user);}


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
