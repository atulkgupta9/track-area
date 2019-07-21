package com.apogee.trackarea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {
    @GetMapping("/index")
    public String greeting() {
        return "index.html";
    }
}
