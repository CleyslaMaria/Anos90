package com.noventoteca.Anos90.controller;

import com.noventoteca.Anos90.model.Album;
import com.noventoteca.Anos90.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos álbuns
 */

@Controller
@RequestMapping("/albuns")
public class AlbumController {

    /** oO serviço de álbum é injetado aqui para que o controlador possa usar a 
     * lógica de negócio do serviço
    */
    private final AlbumService albumService;

    /** Construtor que recebe o serviço de álbum automaticamente  */
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * Página inicial que lista todos os álbuns disponíveis
     * @param model Modelo para passar dados para a view
     * @return Nome da página de template
     */
    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Album> albuns = albumService.listarAlbuns();
        model.addAttribute("albums", albuns);
        model.addAttribute("albumsCount", albuns.size());
        return "albums";
    }

    /**
     * Página de detalhes de um álbum específico
     * @param codigo Código do álbum
     * @param model Modelo para passar dados para a view
     * @return Nome da página de template
     */
    @GetMapping("/album/{codigo}")
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
     * Página de busca de álbuns com filtros
     * Recebe a query de busca, o tipo, o gênero e o ano como parâmetros da URL
     * @param model Modelo para passar dados para a view
     * @return Nome da página de template
     */
    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "searchType", required = false, defaultValue = "all") String searchType,
                         @RequestParam(value = "genre", required = false, defaultValue = "all") String genre,
                         @RequestParam(value = "year", required = false, defaultValue = "all") String year,
                         Model model) {
        List<Album> resultados = albumService.buscarPorFiltros(q, searchType, genre, year);
        model.addAttribute("albums", resultados);
        model.addAttribute("albumsCount", resultados.size());
        model.addAttribute("query", q != null ? q : "");
        model.addAttribute("searchType", searchType);
        model.addAttribute("genre", genre);
        model.addAttribute("year", year);
        return "search";
    }

    /**
     * Formulário para adicionar um novo álbum
     * @param model Modelo para passar dados para a view
     * @return Nome da página de template
     */
    @GetMapping("/adicionar")
    public String formAdicionar(Model model) {
        model.addAttribute("album", new Album());
        return "album-form";
    }

    /**
     * Recebe os dados do formulário e salva um novo álbum
     * @param album Álbum a ser adicionado
     * @return Redirecionamento para a página principal
     */
    @PostMapping("/adicionar")
    public String adicionar(@ModelAttribute Album album) {
        albumService.adicionarAlbum(album);
        return "redirect:/albuns/";
    }

    /**
     * Remove um álbum da coleção
     * @param codigo Código do álbum a ser removido
     * @return Redirecionamento para a página principal
     */
    @GetMapping("/remover/{codigo}")
    public String remover(@PathVariable String codigo) {
        albumService.removerAlbum(codigo);
        return "redirect:/albuns/";
    }



}