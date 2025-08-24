package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.model.UsuarioRepository;
import com.noventoteca.Anos90.model.AlbumRepository;
import com.noventoteca.Anos90.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private AlbumRepository albumRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        albumRepository = mock(AlbumRepository.class);
        usuarioService = new UsuarioService(usuarioRepository, albumRepository);
    }

    @Test
    void deveAutenticarUsuario() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");
        when(usuarioRepository.buscarPorEmail("maria@gmail.com")).thenReturn(usuario);

        Usuario resultado = usuarioService.login("maria@gmail.com", "1234");

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
    }

    @Test
    void deveFalharLoginSenhaErrada() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");
        when(usuarioRepository.buscarPorEmail("maria@gmail.com")).thenReturn(usuario);

        Usuario resultado = usuarioService.login("maria@gmail.com", "0000");

        assertNull(resultado);
    }

    @Test
    void deveFavoritarAlbum() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");
        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");

        when(usuarioRepository.buscarPorId("U001")).thenReturn(usuario);
        when(albumRepository.buscarPorCodigo("A001")).thenReturn(album);

        boolean sucesso = usuarioService.favoritarAlbum("U001", "A001");

        assertTrue(sucesso);
        assertTrue(usuario.getFavoritos().contains("A001"));
        verify(usuarioRepository, times(1)).atualizaUsuario(usuario);
    }

    @Test
    void deveListarFavoritos() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");
        usuario.adicionarFavorito("A001");

        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");

        when(usuarioRepository.buscarPorId("U001")).thenReturn(usuario);
        when(albumRepository.buscarPorCodigo("A001")).thenReturn(album);

        List<Album> favoritos = usuarioService.listarFavoritos("U001");

        assertEquals(1, favoritos.size());
        assertEquals("Ok Computer", favoritos.get(0).getTitulo());
    }
}
