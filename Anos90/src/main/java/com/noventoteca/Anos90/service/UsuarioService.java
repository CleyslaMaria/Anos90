package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.model.AlbumRepository;
import com.noventoteca.Anos90.model.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Serviço responsável por gerenciar as operações relacionadas aos usuários
 */
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AlbumRepository albumRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, AlbumRepository albumRepository){
        this.usuarioRepository = usuarioRepository;
        this.albumRepository = albumRepository;
    }

    /**
     * Cria um novo usuário no sistema
     * usuario - Usuário a ser criado
     */
    public void criarUsuario(Usuario usuario){
        Usuario existe = usuarioRepository.buscarPorEmail(usuario.getEmail());
        if(existe == null){
            usuarioRepository.salvarUsuario(usuario);
        } else{
            System.out.println("Já existe usuário com esse email");
        }
    }

    /**
     * Realiza o login de um usuário
     * email - Email do usuário
     * senha - Senha do usuário
     * Retorna o usuário autenticado ou null se as credenciais forem inválidas
     */
    public Usuario login(String email, String senha) {
        return usuarioRepository.listarUsuarios().stream()
            .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
            .findFirst()
            .orElse(null);
    }

    /**
     * Adiciona um álbum aos favoritos do usuário
     * usuarioId - ID do usuário
     * albumCodigo - Código do álbum
     * Retorna true se o álbum foi adicionado aos favoritos, false caso contrário
     */
    public boolean favoritarAlbum(String usuarioId, String albumCodigo){
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        Album album = albumRepository.buscarPorCodigo(albumCodigo);

        if(usuario != null && album != null){
            List<String> favoritos = usuario.getFavoritos();
            if(!favoritos.contains(albumCodigo)){
                favoritos.add(albumCodigo);
                usuario.setFavoritos(favoritos);
                usuarioRepository.atualizaUsuario(usuario);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove um álbum dos favoritos do usuário
     * usuarioId - ID do usuário
     * albumCodigo - Código do álbum
     * Retorna true se o álbum foi removido dos favoritos, false caso contrário
     */
    public boolean desfavoritarAlbum(String usuarioId, String albumCodigo){
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        if(usuario != null){
            List<String> favoritos = usuario.getFavoritos();
            if(favoritos.contains(albumCodigo)){
                favoritos.remove(albumCodigo);
                usuario.setFavoritos(favoritos);
                usuarioRepository.atualizaUsuario(usuario);
                return true;
            }
        }
        return false;
    }

    /**
     * Lista os álbuns favoritos de um usuário
     * usuarioId - ID do usuário
     * Retorna a lista de álbuns favoritos
     */
    public List<Album> listarFavoritos(String usuarioId){
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        List<Album> favoritosAlbuns = new ArrayList<>();

        if(usuario != null){
            for(String id : usuario.getFavoritos()){
                Album album = albumRepository.buscarPorCodigo(id);
                if(album != null) favoritosAlbuns.add(album);
            }
        }
        return favoritosAlbuns;
    }

    /**
     * Atualiza os dados de um usuário
     * usuario - Usuário com os dados atualizados
     */
    public void atualizaDadosUsuario(Usuario usuario){
        usuarioRepository.atualizaUsuario(usuario);
    }

    /**
     * Remove um usuário do sistema
     * id - ID do usuário a ser removido
     */
    public void removerDadosUsuario(String id){
        usuarioRepository.removerUsuario(id);
    }
}
