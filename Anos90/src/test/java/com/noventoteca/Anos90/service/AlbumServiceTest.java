package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// Classe de teste para o serviço de állbuns
// Usa JUnit 5 e Mockito para simular o comportamento do repositórios
class AlbumServiceTest {

    private AlbumRepository albumRepository; // Mock do repositório
    private AlbumService albumService; // Classe sob teste


    //Configura o ambiente antes de cada teste
    @BeforeEach
    void setUp() {
        albumRepository = mock(AlbumRepository.class);
        albumService = new AlbumService(albumRepository); 
    }

    // Testa se a listagem de álbuns funciona corretamente
    /** Configura o mock para retornar uma lista com 1 album
     *  Verifica de o serviço retorna a lista com esse álbum
     *  Verifica se o método de repositório foi chamado exatamente uma vez
     */
    @Test
    void deveListarAlbuns() {
        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");
        when(albumRepository.listarAlbuns()).thenReturn(List.of(album));

        List<Album> resultado = albumService.listarAlbuns();

        assertEquals(1, resultado.size());
        assertEquals("Ok Computer", resultado.get(0).getTitulo());
        verify(albumRepository, times(1)).listarAlbuns();
    }

    //Testa se a busca por código retorna corretamente um álbum
    /** Configura o mock para retornar um álbum quando buscado pelo código 
     *  Chama o serviço e verifica os dados retornados
     */
    @Test
    void deveBuscarPorCodigo() {
        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");
        when(albumRepository.buscarPorCodigo("A001")).thenReturn(album);

        Album resultado = albumService.buscarPorCodigo("A001"); 

        assertNotNull(resultado);
        assertEquals("Radiohead", resultado.getArtista());
        verify(albumRepository, times(1)).buscarPorCodigo("A001");
    }
}
