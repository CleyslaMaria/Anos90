package com.noventoteca.Anos90.model;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlbumRepository {
    private final String arquivoAlbuns;

    public AlbumRepository() {
        // Cria a pasta "data" se não existir
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        arquivoAlbuns = "data/albuns.txt";
    }

    /** Lê todos os álbuns do arquivo */
    public List<Album> listarAlbuns() {
        List<Album> albuns = new ArrayList<>();
        File file = new File(arquivoAlbuns);

        if (!file.exists()) return albuns; // Retorna lista vazia se arquivo não existir

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Ignora linhas vazias
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

    /** Adiciona um álbum ao arquivo */
    public void salvarAlbum(Album album) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoAlbuns, true), StandardCharsets.UTF_8))) {
            bw.write(toTxt(album));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar álbum: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Regrava todos os álbuns no arquivo */
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

    /** Busca um álbum pelo código */
    public Album buscarPorCodigo(String codigo) {
        return listarAlbuns().stream()
                .filter(a -> a.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    /** Remove um álbum pelo código */
    public void removerAlbum(String codigo) {
        List<Album> albuns = listarAlbuns();
        albuns.removeIf(a -> a.getCodigo().equalsIgnoreCase(codigo));
        atualizaAlbuns(albuns);
    }

    /** Atualiza os dados de um álbum existente */
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

    /** Converte uma linha do TXT para um objeto Album */
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

    /** Converte um objeto Album para formato TXT */
    public String toTxt(Album a) {
        String titulo = a.getTitulo() != null ? a.getTitulo() : "";
        String artista = a.getArtista() != null ? a.getArtista() : "";
        String genero = a.getGenero() != null ? a.getGenero() : "";
        String link = a.getLink() != null ? a.getLink() : "";
        String descricao = a.getDescricao() != null ? a.getDescricao() : "";

        return a.getCodigo() + ";" + titulo + ";" + artista + ";" + a.getAno() + ";" + genero + ";" + link + ";" + descricao;
    }
}
