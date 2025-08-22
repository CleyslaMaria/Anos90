package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumController {
    private List<Album> albuns = new ArrayList<>();

    public AlbumController() {
        albuns.add(new Album(1L, "Ok Computer", "Alternative Rock", "Radiohead", 1997));
        albuns.add(new Album(2L, "Homogenic", "Art Pop", "Bjork", 1997));
        albuns.add(new Album(3L, "Dots and Loops", "Indietronica", "Stereolab", 1997));
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

    public Album adicionarAlbum(String titulo, String genero, String artista, int ano) {
        Album novo = new Album((long) (albuns.size() + 1), titulo, genero, artista, ano);
        albuns.add(novo);
        return novo;
    }

    public boolean removerAlbum(Long id) {
        return albuns.removeIf(a -> a.getId().equals(id));
    }
}

        // Busca por gênero
        System.out.println("=== Busca por gênero: Pop ===");
        view.exibirAlbuns(controller.buscarPorGenero("Pop"));
    }
}
}
