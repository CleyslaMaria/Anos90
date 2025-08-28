package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private final AlbumRepository repository;

    public AlbumService(AlbumRepository repository){
        this.repository = repository;
    }

    //Lista todos os álbuns disponíveis
    public List<Album> listarAlbuns(){
        return repository.listarAlbuns();
    }

    /**
     * Busca álbuns com base em filtros: query, tipo de busca, gênero e ano
     * Retorna a lista de álbuns que correspondem aos critérios
     */
    public List<Album> buscarPorFiltros(String query, String searchType, String genre, String year) {
        if (query == null || query.trim().isEmpty()) {
            return repository.listarAlbuns().stream()
                .filter(album -> {
                    boolean genreMatch = (genre == null || genre.equals("all") || album.getGenero().equalsIgnoreCase(genre));
                    boolean yearMatch = (year == null || year.equals("all") || String.valueOf(album.getAno()).equals(year));
                    return genreMatch && yearMatch;
                })
                .collect(Collectors.toList());
        }

        // Converte a query para lowercase para busca case-insensitive
        String qLower = query.toLowerCase();

        return repository.listarAlbuns().stream()
                .filter(album -> {
                    boolean matchesQuery = switch (searchType) {
                        case "title" -> album.getTitulo().toLowerCase().contains(qLower);
                        case "artist" -> album.getArtista().toLowerCase().contains(qLower);
                        default -> album.getTitulo().toLowerCase().contains(qLower)
                                || album.getArtista().toLowerCase().contains(qLower)
                                || (album.getGenero() != null && album.getGenero().toLowerCase().contains(qLower));
                    };

                    // O filtro de gênero é aplicado se o valor não for "all" ou nulo
                    boolean genreMatches = genre.equals("all") || album.getGenero().equalsIgnoreCase(genre);

                    // O filtro de ano é aplicado se o valor não for "all" ou nulo
                    boolean yearMatches = year.equals("all") || String.valueOf(album.getAno()).equals(year);

                    return matchesQuery && genreMatches && yearMatches;
                })
                .collect(Collectors.toList());
    }

    /**
     * Adiciona um novo álbum à coleção
     * @param Álbum a ser adicionado
     */
    public void adicionarAlbum(Album album) {
        repository.salvarAlbum(album);
    }

    /**
     * Busca um álbum específico pelo seu código
     * @param Código do álbum
     * @return o álbum encontrado ou null se não encontrado
     */
    public Album buscarPorCodigo(String codigo){
        return repository.buscarPorCodigo(codigo);
    }

    /**
     * Remove um álbum da coleção pelo seu código
     * @param Código do álbum a ser removido
     */
    public void removerAlbum(String codigo) {
        repository.removerAlbum(codigo);
    }

    /**
     * Atualiza os dados de um álbum existente
     * @param Álbum com os dados atualizados
     */
    public void atualizarAlbum(Album album) {
        repository.atualizaDadosA(album);
    }

        /**
     * Retorna um conjunto com todos os anos de lançamento únicos dos álbuns
     * @return Set de inteiros com os anos únicos
     */
    public Set<Integer> listarAnosUnicos() {
        return repository.listarAlbuns().stream()
                .map(Album::getAno) 
                .collect(Collectors.toSet());
    }

    /**
     * Retorna um conjunto com todos os gêneros únicos dos álbuns
     * @return Set de strings com os gêneros únicos
     */
    public Set<String> listarGenerosUnicos() {
        return repository.listarAlbuns().stream()
                .map(Album::getGenero) 
                .collect(Collectors.toSet());
    }

}

