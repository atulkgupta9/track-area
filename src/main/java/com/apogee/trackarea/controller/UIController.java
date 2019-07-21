package com.apogee.trackarea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {
    @GetMapping("/ui/index")
    public String greeting() {
        return "index.html";
    }
    @GetMapping("/ui/signin")
    public String signin() {
        return "user-signin.html";
    }
}
