package com.noventoteca.Anos90.model;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é o repositório de álbuns, responsável por lidar
 * com a persistência de dados em um arquivo de texto.
 */
@Repository
public class AlbumRepository {

    /** Caminho do arquivo onde os álbuns serão salvos
     * O arquivo está em uma pasta "dados" na raiz do projeto, para que seja editável
     */
    private final String arquivoAlbuns = "dados/albuns.txt"; 

    /** Construtor do repositório
     * Ele garante que o arquivo de dados exista ao iniciar a aplicação */
    public AlbumRepository() {
        inicializarArquivoSeNecessario();
    }

    /** Verifica se o arquivo de dados externo existe. Se não,
     * ele é criado. Se houver um arquivo inicial em resources,
     * ele é copiado para o novo arquivo.
     */
    private void inicializarArquivoSeNecessario() {
        File file = new File(arquivoAlbuns);

        if (!file.exists()) {
            file.getParentFile().mkdirs(); 
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("dados/albuns_iniciais.txt")) {
                if (is != null) {
                    Files.copy(is, file.toPath());
                    System.out.println("Arquivo albuns.txt criado com dados iniciais.");
                } else {
                    file.createNewFile();
                    System.out.println("Arquivo albuns.txt criado vazio.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lê todos os álbuns do arquivo de texto e os retorna como uma lista.
     * @return Uma lista de objetos Album.
     */
    public List<Album> listarAlbuns() {
        List<Album> albuns = new ArrayList<>();
        File file = new File(arquivoAlbuns);

        if (!file.exists()) return albuns; 

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    albuns.add(fromTxt(linha));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de álbuns: " + e.getMessage());
            e.printStackTrace();
        }

        return albuns;
    }

    /**
     * Adiciona um álbum ao final do arquivo. Este método é usado para novos álbuns.
     * @param album objeto Album a ser salvo.
     */
    public void salvarAlbum(Album album) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoAlbuns, true), StandardCharsets.UTF_8))) {
            bw.write(toTxt(album));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar álbum: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Regrava todos os álbuns no arquivo. Este método é usado para atualizações ou remoções.
     * @param albuns lista completa e atualizada de álbuns.
     */
    public void atualizaAlbuns(List<Album> albuns) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoAlbuns, false), StandardCharsets.UTF_8))) {
            for (Album a : albuns) {
                bw.write(toTxt(a));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao atualizar álbuns: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca um álbum na lista pelo seu código de identificação.
     * @param codigo código do álbum a ser buscado.
     * @return objeto Album encontrado, ou null se não for encontrado.
     */
    public Album buscarPorCodigo(String codigo) {
        return listarAlbuns().stream()
                .filter(a -> a.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Remove um álbum da coleção pelo seu código.
     * @param codigo código do álbum a ser removido.
     */
    public void removerAlbum(String codigo) {
        List<Album> albuns = listarAlbuns();
        albuns.removeIf(a -> a.getCodigo().equalsIgnoreCase(codigo));
        atualizaAlbuns(albuns);
    }

    /**
     * Atualiza os dados de um álbum existente na coleção.
     * @param albumAtualizado objeto Album com os novos dados.
     */
    public void atualizaDadosA(Album albumAtualizado) {
        List<Album> albuns = listarAlbuns();
        for (int i = 0; i < albuns.size(); i++) {
            if (albuns.get(i).getCodigo().equalsIgnoreCase(albumAtualizado.getCodigo())) {
                albuns.set(i, albumAtualizado);
                break;
            }
        }
        atualizaAlbuns(albuns);
    }

    /**
     * Converte uma linha de texto do arquivo em um objeto Album.
     * @param registro  linha de texto a ser processada.
     * @return Um objeto Album criado a partir da linha.
     * @throws IllegalArgumentException se o registro for inválido.
     */
    public static Album fromTxt(String registro) {
        String[] partes = registro.split(";");
        if (partes.length < 7) {
            throw new IllegalArgumentException("Registro inválido: " + registro);
        }
        try {
            return new Album(
                    partes[0], // código
                    partes[1], // título
                    partes[2], // artista
                    Integer.parseInt(partes[3]), // ano
                    partes[4], // gênero
                    partes[5], // link
                    partes[6]  // descrição
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ano inválido no registro: " + registro, e);
        }
    }

    /**
     * Converte um objeto Album em uma linha de texto para ser salva no arquivo.
     * @param a O objeto Album a ser convertido.
     * @return Uma String formatada para o arquivo de texto.
     */
    public String toTxt(Album a) {
        String titulo = a.getTitulo() != null ? a.getTitulo() : "";
        String artista = a.getArtista() != null ? a.getArtista() : "";
        String genero = a.getGenero() != null ? a.getGenero() : "";
        String link = a.getLink() != null ? a.getLink() : "";
        String descricao = a.getDescricao() != null ? a.getDescricao() : "";

        return a.getCodigo() + ";" + titulo + ";" + artista + ";" + a.getAno() + ";" + genero + ";" + link + ";" + descricao;
    }
}