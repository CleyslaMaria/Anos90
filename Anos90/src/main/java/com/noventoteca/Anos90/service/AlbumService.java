package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.List;

@Service 
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(){
        this.albumRepository = new AlbumRepository();
    }

    public List<Album> listarAlbuns(){
        return albumRepository.listarAlbuns();
    }

    public void adicionarAlbum(Album album){
        Album existe = albumRepository.buscarPorCodigo(album.getCodigo());
        if( existe == null){
            albumRepository.salvarAlbum(album);
        }else{
            System.out.println("Ja existe um album com o codigo " + album.getCodigo());
        }
    }

    public Album burcarPorCodigo(String codigo){
        return albumRepository.buscarPorCodigo(codigo);
    }

    public List<Album> burcarPorTitulo(String titulo){
        return albumRepository.buscarPorTitulo(titulo);
    }

    public List<Album> burcarPorGenero(String genero){
        return albumRepository.buscarPorGenero(genero);
    }

    public List<Album> burcarPorArtista(String artista){
        return albumRepository.buscarPorArtista(artista);
    }

    public void removerAlbum(String codigo) {
        albumRepository.removerAlbum(codigo);
    }

    public void atualizarAlbum(Album album) {
        albumRepository.atualizaDadosA(album);
    }

    public Album sortearAlbum(){
        List<Album> albuns = albumRepository.listarAlbuns();
        if(albuns.isEmpty()) return null;
        Random r = new Random();
        return albuns.get(r.nextInt(albuns.size()));
    }
}
