package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

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
        usuarioService.criarConta(usuario);
        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    // POST /login - autentica usuário
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        Optional<Usuario> usuario = usuarioService.autenticar(email, senha);
        if (usuario.isPresent()) {
            return ResponseEntity.ok("Usuário autenticado");
        } else {
            return ResponseEntity.status(404).body("Não Encontrado - Usuário ou senha inválidos");
        }
    }

    // POST /usuarios/{usuarioId}/favoritos/{albumId} - favoritar
    @PostMapping("/{usuarioId}/favoritos/{albumId}")
    public ResponseEntity<String> favoritar(@PathVariable Long usuarioId, @PathVariable Long albumId) {
        return usuarioService.favoritar(usuarioId, albumId)
                .map(u -> ResponseEntity.ok("Álbum favoritado"))
                .orElse(ResponseEntity.status(404).body("Não Encontrado"));
    }

    // DELETE /usuarios/{usuarioId}/favoritos/{albumId} - desfavoritar
    @DeleteMapping("/{usuarioId}/favoritos/{albumId}")
    public ResponseEntity<String> desfavoritar(@PathVariable Long usuarioId, @PathVariable Long albumId) {
        return usuarioService.desfavoritar(usuarioId, albumId)
                .map(u -> ResponseEntity.ok("Álbum removido dos favoritos"))
                .orElse(ResponseEntity.status(404).body("Não Encontrado"));
    }

    // GET /usuarios/{usuarioId}/favoritos - lista favoritos
    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<?> listarFavoritos(@PathVariable Long usuarioId) {
        Set<Album> favoritos = usuarioService.listarFavoritos(usuarioId);
        if (favoritos.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum favorito para este usuário");
        }
        return ResponseEntity.ok(favoritos);
    }
}
