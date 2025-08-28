package com.noventoteca.Anos90.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//* Este controlador gerencia a requisição para a página inicial do site */

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home"; // Página inicial dedicada
    }
    
    @GetMapping("/albuns")
    public String albuns() {
        return "redirect:/albuns/";
    }
}