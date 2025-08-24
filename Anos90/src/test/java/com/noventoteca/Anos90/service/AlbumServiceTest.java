package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.AlbumRepository;
import com.noventoteca.Anos90.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class AlbumServiceTest {

    private AlbumRepository albumRepository;
    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        albumRepository = mock(AlbumRepository.class);
        albumService = new AlbumService(albumRepository);
    }

    @Test
    void deveListarAlbuns() {
        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");
        when(albumRepository.listarAlbuns()).thenReturn(List.of(album));

        List<Album> resultado = albumService.listarAlbuns();

        assertEquals(1, resultado.size());
        assertEquals("Ok Computer", resultado.get(0).getTitulo());
        verify(albumRepository, times(1)).listarAlbuns();
    }

    @Test
    void deveBuscarPorCodigo() {
        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");
        when(albumRepository.buscarPorCodigo("A001")).thenReturn(album);

        Album resultado = albumService.burcarPorCodigo("A001");

        assertNotNull(resultado);
        assertEquals("Radiohead", resultado.getArtista());
    }
}
