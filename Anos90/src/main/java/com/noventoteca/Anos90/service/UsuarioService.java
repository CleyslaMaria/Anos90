package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.model.AlbumRepository;
import com.noventoteca.Anos90.model.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AlbumRepository albumRepository;

    public UsuarioService(){
        this.usuarioRepository = new UsuarioRepository();
        this.albumRepository = new AlbumRepository();
    }

    public void criarUsuario(Usuario usuario){
        Usuario existe = usuarioRepository.buscarPorEmail(usuario.getEmail());
        if(existe == null){
            usuarioRepository.salvarUsuario(usuario);
        } else{
            System.out.println("Ja existe usuario com esse email");
        }
    }

    public Usuario login(String email, String senha){
        Usuario usuario = usuarioRepository.buscarPorEmail(email);
        if(usuario != null && usuario.getSenha().equals(senha)){
            return usuario;
        }
        return null;
    }

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

    public void atualizaDadosUsuario(Usuario usuario){
        usuarioRepository.atualizaUsuario(usuario);
    }

    public void removerDadosUsuario(String id){
        usuarioRepository.removerUsuario(id);
    }
}
