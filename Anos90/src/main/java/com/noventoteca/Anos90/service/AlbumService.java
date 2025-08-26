package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.List;
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
     * Busca álbuns com base em filtros: query, tipo de busca e gênero
     * query - Texto para busca
     * searchType - Tipo de busca (titulo, artista)
     * genre Gênero musical
     * Retorna a lista de álbuns que correspondem aos critérios
     */
    public List<Album> buscarPorFiltros(String query, String searchType, String genre) {
        // Se não há query, retorna todos os álbuns filtrados apenas por gênero
        if (query == null || query.trim().isEmpty()) {
            if (genre == null || genre.equals("all")) {
                return repository.listarAlbuns();
            } else {
                return repository.listarAlbuns().stream()
                    .filter(album -> album.getGenero().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());
            }
        }

        String qLower = query.toLowerCase().trim();

        return repository.listarAlbuns().stream()
                .filter(album -> {
                    boolean matchesQuery = switch (searchType) {
                        case "title" -> album.getTitulo().toLowerCase().contains(qLower);
                        case "artist" -> album.getArtista().toLowerCase().contains(qLower);
                        default -> album.getTitulo().toLowerCase().contains(qLower)
                                || album.getArtista().toLowerCase().contains(qLower)
                                || (album.getGenero() != null && album.getGenero().toLowerCase().contains(qLower));
                    };

                    boolean matchesGenre = genre.equals("all") || album.getGenero().equalsIgnoreCase(genre);

                    return matchesQuery && matchesGenre;
                })
                .collect(Collectors.toList());
    }

    /**
     * Adiciona um novo álbum à coleção
     * album - Álbum a ser adicionado
     */
    public void adicionarAlbum(Album album) {
        repository.salvarAlbum(album);
    }

    /**
     * Busca um álbum específico pelo seu código
     * codigo - Código do álbum
     * Retorna o álbum encontrado ou null se não encontrado
     */
    public Album buscarPorCodigo(String codigo){
        return repository.buscarPorCodigo(codigo);
    }

    /**
     * Remove um álbum da coleção pelo seu código
     * codigo - Código do álbum a ser removido
     */
    public void removerAlbum(String codigo) {
        repository.removerAlbum(codigo);
    }

    /**
     * Atualiza os dados de um álbum existente
     * album - Álbum com os dados atualizados
     */
    public void atualizarAlbum(Album album) {
        repository.atualizaDadosA(album);
    }

    /**
     * Seleciona um álbum aleatório da coleção
     * Retorna álbum sorteado ou null se não houver álbuns
     */
    public Album sortearAlbum() {
        List<Album> albuns = repository.listarAlbuns();
        if (albuns.isEmpty()) return null;
        int index = new Random().nextInt(albuns.size());
        return albuns.get(index);
    }
}
