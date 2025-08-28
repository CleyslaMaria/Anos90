package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * Esta classe é o controlador de páginas (HTML) da aplicação.
 * Ela lida com as requisições que retornam views (páginas .html),
 * como login, registro e a página de favoritos.
 */
@Controller
public class PageController {

    /** O serviço se usuário é injetado aqui para que possa ser
     *  usada as regras de negócio */
    private final UsuarioService usuarioService;
    
    /** Construtor que recebe o serviçoo de usuário automaticamente */
    public PageController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /** Exibe a página de login */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /** Processa o formulário de login 
     * Se o login for válido, o usuário é salvo na sessão para mantê-lo logado
     * Se o login falhar, adiciona uma mensagem de erro ao modelo e reorna para a página de login*/
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

    /** Exibe a página de favoritos
     * Requer que o usuário esteja logado, então verifica a sessão
     * Se não houver usúario na sessão, redireciona para a página de login
     * Se estiver logado, pega a lista de favoritos do usuário logado e a envia para a página
     */
    @GetMapping("/favorites")
    public String favorites(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        
        List<Album> favoritos = usuarioService.listarFavoritos(usuario.getId());
        model.addAttribute("favoritos", favoritos);
        
        return "favorites";
    }

    /** Encerra a sessão do usuário 
     * Invalida a sessão, removendo o usuário logado e redireciona para a página inicial
    */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    /** Exibe a página de registro de novo usúario */
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    /** Processa o formullário de registro
     * Verifica se as senhas digitadas são iguais
     * Retorna para a página de registro com erro se não forem igual
     */
    @PostMapping("/register")
    public String registerSubmit(@RequestParam String nome, @RequestParam String email, @RequestParam String senha, @RequestParam String confirmarSenha, Model model) {
        // Check if passwords match
        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("erro", "As senhas não coincidem");
            return "register";
        }
        
       /** Cria um novo usuário com os dados do formulário. */
        String id = "U" + System.currentTimeMillis(); 
        Usuario usuario = new Usuario(id, nome, email, senha);
        
        /** Chama o serviço para salvar o novo usúario */
        usuarioService.criarUsuario(usuario);
        
        /** Redireciona para a página de login após o cadastro */
        return "redirect:/login";
    }
    
    //** Exibe a página de busca */
    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }
}