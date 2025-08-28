package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Este é um controlador REST
 * Ele recebe requisições da web e devolve dados
 * Ele lida com todas as ações relacionadas a usuários
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    /** O serviço de usuário é injetado aqui para possa ser 
     * usada a lógica de negócio do serviço pelo controlador */
    private final UsuarioService usuarioService;

    /** Construtor que recebe o serviço de usuário */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /** POST /usuarios - cria uma nova conta de usuário
     * @param usuario enviado no corpo da requisição
     * @return Resposta HTTP com uma mensgaem de sucesso
     * 
     * */ 
    @PostMapping
    public ResponseEntity<String> criarConta(@RequestBody Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok("Usuário criado com sucesso");
    }

    /** POST /usuarios/login - autentica um usuário
     * @param email do usuário para autenticação
     * @param senha do usuário para autenticação
     * @return Resposta HTTP de sucesso se o usúario for encontrado, 
     * ou erro 404 se as credenciais forem inválidas
     * */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        Usuario usuario = usuarioService.login(email, senha);
        if (usuario != null) {
            return ResponseEntity.ok("Usuário autenticado");
        } else {
            return ResponseEntity.status(404).body("Não Encontrado - Usuário ou senha inválidos");
        }
    }

    /**  POST /usuarios/{usuarioId}/favoritos/{albumId} - Adiciona um álbum aos favoritos
     * @param usuarioId ID do usuário.
     * @param albumId ID do álbum a ser favoritado.
     * @return Resposta HTTP com uma mensagem de sucesso, 
     * ou 404 se o usuário ou álbum não for encontrado.
     */
    @PostMapping("/{usuarioId}/favoritos/{albumId}")
    public ResponseEntity<String> favoritar(@PathVariable String usuarioId, @PathVariable String albumId) {
        boolean sucesso = usuarioService.favoritarAlbum(usuarioId, albumId);
        return sucesso ? ResponseEntity.ok("Álbum favoritado")
                    : ResponseEntity.status(404).body("Usuário ou álbum não encontrado");
    }

    /** DELETE /usuarios/{usuarioId}/favoritos/{albumId} - Remove um álbum dos favoritos de um usuário
     * @param usuarioId ID do usuário.
     * @param albumId ID do álbum a ser removido dos favoritos.
     * @return Resposta HTTP com uma mensagem de sucesso, ou 404 se o usuário ou álbum não for encontrado.
     */
    @DeleteMapping("/{usuarioId}/favoritos/{albumId}")
    public ResponseEntity<String> desfavoritar(@PathVariable String usuarioId, @PathVariable String albumId) {
        boolean sucesso = usuarioService.desfavoritarAlbum(usuarioId, albumId);
        return sucesso ? ResponseEntity.ok("Álbum removido dos favoritos")
                    : ResponseEntity.status(404).body("Usuário ou álbum não encontrado");
    }

    /** GET /usuarios/{usuarioId}/favoritos - lista todos os álbuns favoritados de um usuário
     * @param usuarioId ID do usuário.
     * @return Resposta HTTP com a lista de álbuns favoritos (se houver), ou 404 se a lista estiver vazia.
     */
    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<?> listarFavoritos(@PathVariable String usuarioId) {
        List<Album> favoritos = usuarioService.listarFavoritos(usuarioId);
        if (favoritos.isEmpty()) {
            return ResponseEntity.status(404).body("Nenhum favorito para este usuário");
        }
        return ResponseEntity.ok(favoritos);
    }
}