package com.reviews.Directory.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/home")
    private String test() {
        return "index";
    }

}
