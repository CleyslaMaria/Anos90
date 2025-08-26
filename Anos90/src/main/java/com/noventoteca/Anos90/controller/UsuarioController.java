package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /usuarios - criar conta
    @PostMapping
    public ResponseEntity<String> criarConta(@RequestBody Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    // POST /usuarios/login - autentica usuário
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        Usuario usuario = usuarioService.login(email, senha);
        if (usuario != null) {
            return ResponseEntity.ok("Usuário autenticado");
        } else {
            return ResponseEntity.status(404).body("Não Encontrado - Usuário ou senha inválidos");
        }
    }

    // POST /usuarios/{usuarioId}/favoritos/{albumId} - favoritar
    @PostMapping("/{usuarioId}/favoritos/{albumId}")
    public ResponseEntity<String> favoritar(@PathVariable String usuarioId, @PathVariable String albumId) {
        boolean sucesso = usuarioService.favoritarAlbum(usuarioId, albumId);
        return sucesso ? ResponseEntity.ok("Álbum favoritado")
                    : ResponseEntity.status(404).body("Usuário ou álbum não encontrado");
    }

    // DELETE /usuarios/{usuarioId}/favoritos/{albumId} - desfavoritar
    @DeleteMapping("/{usuarioId}/favoritos/{albumId}")
    public ResponseEntity<String> desfavoritar(@PathVariable String usuarioId, @PathVariable String albumId) {
        boolean sucesso = usuarioService.desfavoritarAlbum(usuarioId, albumId);
        return sucesso ? ResponseEntity.ok("Álbum removido dos favoritos")
                    : ResponseEntity.status(404).body("Usuário ou álbum não encontrado");
    }

    // GET /usuarios/{usuarioId}/favoritos - lista favoritos
    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<?> listarFavoritos(@PathVariable String usuarioId) {
        List<Album> favoritos = usuarioService.listarFavoritos(usuarioId);
        if (favoritos.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum favorito para este usuário");
        }
        return ResponseEntity.ok(favoritos);
    }
}