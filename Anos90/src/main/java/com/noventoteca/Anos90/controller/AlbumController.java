package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumController {
    private final List<Album> albuns = new ArrayList<>();

    public AlbumController() {
        albuns.add(new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "Descrição do álbum"));
        albuns.add(new Album("A002", "Homogenic", "Bjork", 1997, "Art Pop", "link2", "Descrição do álbum"));
        albuns.add(new Album("A003", "Dots and Loops", "Stereolab", 1997, "Indietronica", "link3", "Descrição do álbum"));
    }

    public List<Album> listarTodos() {
        return albuns;
    }

    public List<Album> buscarPorTitulo(String titulo) {
        return albuns.stream()
                .filter(a -> a.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Album> buscarPorGenero(String genero) {
        return albuns.stream()
                .filter(a -> a.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    public List<Album> buscarPorArtista(String artista) {
        return albuns.stream()
                .filter(a -> a.getArtista().toLowerCase().contains(artista.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Album adicionarAlbum(String codigo, String titulo, String genero, String artista, int ano, String link, String descricao) {
        Album novo = new Album(codigo, titulo, artista, ano, genero, link, descricao);
        albuns.add(novo);
        return novo;
    }

    public boolean removerAlbum(String codigo) {
        return albuns.removeIf(a -> a.getCodigo().equalsIgnoreCase(codigo));
    }
}
