package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.service.AlbumService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador responsável por gerenciar as operações relacionadas aos álbuns

@Controller
@RequestMapping("/albuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * Página inicial que lista todos os álbuns disponíveis
     * model - Modelo para passar dados para a view
     * Retorna o nome da página de template
     */
    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Album> albuns = albumService.listarAlbuns();
        model.addAttribute("albums", albuns);
        model.addAttribute("albumsCount", albuns.size());
        return "index";
    }

    /**
     * Página de busca de álbuns com filtros
     * q - Texto para busca
     * searchType - Tipo de busca (title, artist, all)
     * genre - Gênero musical
     * model - Modelo para passar dados para a view
     * Retorna o nome da página de template
     */
    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "searchType", required = false, defaultValue = "all") String searchType,
                         @RequestParam(value = "genre", required = false, defaultValue = "all") String genre,
                         Model model) {
        List<Album> resultados = (q != null && !q.isEmpty()) ? albumService.buscarPorFiltros(q, searchType, genre) : albumService.listarAlbuns();
        model.addAttribute("albums", resultados);
        model.addAttribute("albumsCount", resultados.size());
        model.addAttribute("query", q != null ? q : "");
        return "search";
    }

    /**
     * Página de detalhes de um álbum específico
     * codigo - Código do álbum
     * model - Modelo para passar dados para a view
     * Retorna o nome da página de template
     */
    @GetMapping("/{codigo}")
    public String detalhes(@PathVariable String codigo, Model model) {
        Album album = albumService.buscarPorCodigo(codigo);
        if (album == null) {
            model.addAttribute("erro", "Álbum não encontrado!");
            return "error";
        }
        model.addAttribute("album", album);
        return "album-details";
    }

    /**
     * Formulário para adicionar um novo álbum
     * model - Modelo para passar dados para a view
     * Retorna o nome da página de template
     */
    @GetMapping("/adicionar")
    public String formAdicionar(Model model) {
        model.addAttribute("album", new Album());
        return "album-form";
    }

    /**
     * Recebe os dados do formulário e salva um novo álbum
     * album - Álbum a ser adicionado
     * Redirecionamento para a página principal
     */
    @PostMapping("/adicionar")
    public String adicionar(@ModelAttribute Album album) {
        albumService.adicionarAlbum(album);
        return "redirect:/albuns/";
    }

    /**
     * Remove um álbum da coleção
     * codigo - Código do álbum a ser removido
     * Redirecionamento para a página principal
     */
    @GetMapping("/remover/{codigo}")
    public String remover(@PathVariable String codigo) {
        albumService.removerAlbum(codigo);
        return "redirect:/albuns/";
    }

    /**
     * Sorteia um álbum aleatório da coleção
     * model - Modelo para passar dados para a view
     * Retorna o nome da página de template
     */
    @GetMapping("/random")
    public String random(Model model) {
        Album sorteado = albumService.sortearAlbum();
        if (sorteado == null) {
            model.addAttribute("erro", "Nenhum álbum disponível para sortear.");
            return "error";
        }
        model.addAttribute("album", sorteado);
        return "album-details";
    }
}
