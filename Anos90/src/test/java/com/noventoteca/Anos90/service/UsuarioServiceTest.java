package com.noventoteca.Anos90.service;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.model.Usuario;
import com.noventoteca.Anos90.model.UsuarioRepository;
import com.noventoteca.Anos90.model.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;


// Classe de teste para o serviço de usuário
// Usa JUnit 5 e Mockito para simular o comportamento do repositórios

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository; // Mock do repositório de usuários
    private AlbumRepository albumRepository; // Mock do repositório de álbun
    private UsuarioService usuarioService; // Classe sob teste

    @BeforeEach
    void setUp() {
        // Criando mocks para os repositórios
        usuarioRepository = mock(UsuarioRepository.class);
        albumRepository = mock(AlbumRepository.class);

        // Injetando os mocks no service
        usuarioService = new UsuarioService(usuarioRepository, albumRepository);
    }

    // Testa se o login funciona corretamente com email e senha corretas
    /** Configura o mock para retornar uma lista com 1 usuário
     *  Chama o serviço de login
     *  Verifica se o usuário é autenticado corretamente
     */
    @Test
    void deveAutenticarUsuario() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");

        when(usuarioRepository.listarUsuarios()).thenReturn(List.of(usuario));

        Usuario resultado = usuarioService.login("maria@gmail.com", "1234");

        assertNotNull(resultado, "O usuário deveria ser autenticado");
        assertEquals("Maria", resultado.getNome(), "O nome do usuário deve ser Maria");
    }

    // Testa se o login falha quando a senha está incorreta
    /** Configura o mock para retornar um usuário
     *  Chama o serviço de login com senha errada
     *  Verifica se o resultado é nulo
     */
    @Test
    void deveFalharLoginSenhaErrada() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");

        when(usuarioRepository.listarUsuarios()).thenReturn(List.of(usuario));

        Usuario resultado = usuarioService.login("maria@gmail.com", "0000");

        assertNull(resultado, "O login deve falhar com senha errada");
    }

    // Testa se é possível favoritar um álbum
    /** Configura o mock para retornar usuário e álbum
     *  Chama o serviço de favoritar álbum
     *  Verifica se o álbum foi adicionado aos favoritos do usuário
     *  Verifica se o repositório atualizou o usuário
     */
    @Test
    void deveFavoritarAlbum() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");
        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");

        when(usuarioRepository.buscarPorId("U001")).thenReturn(usuario);
        when(albumRepository.buscarPorCodigo("A001")).thenReturn(album);

        boolean sucesso = usuarioService.favoritarAlbum("U001", "A001");

        assertTrue(sucesso, "O álbum deveria ser adicionado aos favoritos");
        assertTrue(usuario.getFavoritos().contains("A001"), "O usuário deveria ter A001 nos favoritos");
        verify(usuarioRepository, times(1)).atualizaUsuario(usuario);
    }

    
    // Testa se a listagem de álbuns favoritos funciona corretamente
    /** Configura o mock para retornar usuário e álbum
     *  Adiciona um álbum aos favoritos do usuário
     *  Chama o serviço para listar favoritos
     *  Verifica se o álbum retornado está correto
     */
    @Test
    void deveListarFavoritos() {
        Usuario usuario = new Usuario("U001", "Maria", "maria@gmail.com", "1234");
        usuario.adicionarFavorito("A001");

        Album album = new Album("A001", "Ok Computer", "Radiohead", 1997, "Alternative Rock", "link1", "desc");

        when(usuarioRepository.buscarPorId("U001")).thenReturn(usuario);
        when(albumRepository.buscarPorCodigo("A001")).thenReturn(album);

        List<Album> favoritos = usuarioService.listarFavoritos("U001");

        assertEquals(1, favoritos.size(), "Deve existir 1 álbum favorito");
        assertEquals("Ok Computer", favoritos.get(0).getTitulo(), "O título do álbum favorito deve ser Ok Computer");
    }
}
