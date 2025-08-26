package com.noventoteca.Anos90.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        
        return "index"; // Mostra o template index.html
    }
    
    @GetMapping("/albuns")
    public String albuns() {
        return "redirect:/albuns/";
    }
}