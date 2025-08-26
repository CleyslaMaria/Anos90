package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class PageController {

    private final UsuarioService usuarioService;
    
    public PageController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email, @RequestParam String senha, Model model, HttpSession session) {
        Usuario usuario = usuarioService.login(email, senha);
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            return "redirect:/albuns/";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/favorites")
    public String favorites(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        
        // Get user's favorite albums
        List<Album> favoritos = usuarioService.listarFavoritos(usuario.getId());
        model.addAttribute("favoritos", favoritos);
        
        return "favorites";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerSubmit(@RequestParam String nome, @RequestParam String email, @RequestParam String senha, @RequestParam String confirmarSenha, Model model) {
        // Check if passwords match
        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("erro", "As senhas não coincidem");
            return "register";
        }
        
        // Create a new user with a generated ID
        String id = "U" + System.currentTimeMillis(); // Simple ID generation
        Usuario usuario = new Usuario(id, nome, email, senha);
        
        // Try to create the user
        usuarioService.criarUsuario(usuario);
        
        // Redirect to login page with success message
        return "redirect:/login";
    }
    
    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }
}