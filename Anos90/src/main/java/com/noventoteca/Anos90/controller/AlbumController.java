package com.noventoteca.Anos90.controller;

public class AlbumController {
   private Long id;
    private String titulo;
    private String genero;
    private String artista;
    private int anoLancamento;

    public Album() {}

    public Album(Long id, String titulo, String genero, String artista, int anoLancamento) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.artista = artista;
        this.anoLancamento = anoLancamento;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public int getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(int anoLancamento) { this.anoLancamento = anoLancamento; }
}

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


import java.util.List;

public class AlbumView {
    private AlbumController controller;

    public AlbumView(AlbumController controller) {
        this.controller = controller;
    }

    public void exibirAlbuns(List<Album> albuns) {
        System.out.println("<html><body>");
        System.out.println("<h1>Lista de Álbuns</h1>");
        System.out.println("<ul>");
        for (Album a : albuns) {
            System.out.printf("<li>%s - %s [%s] (%d)</li>%n",
                    a.getTitulo(), a.getArtista(), a.getGenero(), a.getAnoLancamento());
        }
        System.out.println("</ul>");
        System.out.println("</body></html>");
    }
}

public class Main {
    public static void main(String[] args) {
        AlbumController controller = new AlbumController();
        AlbumView view = new AlbumView(controller);

        // Lista inicial
        view.exibirAlbuns(controller.listarTodos());

        // Adiciona novo álbum
        controller.adicionarAlbum("Ok Computer", "Alternative Rock", "Radiohead", 1997);

        // Lista atualizada
        view.exibirAlbuns(controller.listarTodos());

        // Busca por gênero
        System.out.println("=== Busca por gênero: Pop ===");
        view.exibirAlbuns(controller.buscarPorGenero("Pop"));
    }
}
}
