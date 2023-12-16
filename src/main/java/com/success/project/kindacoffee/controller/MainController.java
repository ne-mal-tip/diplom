package com.success.project.kindacoffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/home"})
public class MainController {


    @GetMapping
    public String getMainPage() {
        return "redirect:/products/all";
    }
}
