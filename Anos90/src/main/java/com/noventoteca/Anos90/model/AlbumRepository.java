package com.noventoteca.Anos90.model;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AlbumRepository {
    private final String arquivoAlbuns = "albuns.txt";

    public List<Album> listarAlbuns(){
        List<Album> albuns = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(arquivoAlbuns))){
            String linha;
            while ((linha = br.readLine()) != null){
                albuns.add(fromTxt(linha));
            }
        } catch(IOException e ){
            System.out.println("Erro ao ler: " + e.getMessage());
        }
        return albuns;
    }

    
    public void salvarAlbum(Album album){
        try(BufferedWriter bw = new BufferedWriter((new FileWriter(arquivoAlbuns, true)))){
            bw.write(toTxt(album));
            bw.newLine();
        }catch(IOException e){
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void atualizaAlbuns(List<Album> albuns){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoAlbuns))){
            for (Album a : albuns){
                bw.write(toTxt(a));
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public Album buscarPorCodigo(String codigo){
        return listarAlbuns().stream()
        .filter(a -> a.getCodigo().equalsIgnoreCase(codigo))
        .findFirst()
        .orElse(null);
    }


    public List<Album> buscarPorTitulo(String titulo){
        return listarAlbuns().stream()
        .filter(a -> a.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
        .collect(Collectors.toList());

    }

    public List<Album> buscarPorArtista(String artista){
        return listarAlbuns().stream()
        .filter(a -> a.getArtista().toLowerCase().contains(artista.toLowerCase()))
        .collect(Collectors.toList());

    }


    public List<Album> buscarPorGenero(String genero){
        return listarAlbuns().stream()
        .filter(a -> a.getGenero().equalsIgnoreCase(genero))
        .collect(Collectors.toList());
 
    }

    
    public void removerAlbum(String codigo){
        List<Album> albuns = listarAlbuns();
        albuns.removeIf(a -> a.getCodigo().equalsIgnoreCase(codigo));
        atualizaAlbuns(albuns);
    }

    public void atualizaDadosA(Album albumAtualizado){
        List<Album> albuns = listarAlbuns();
        for(int i = 0; i < albuns.size(); i++){
            if(albuns.get(i).getCodigo().equalsIgnoreCase(albumAtualizado.getCodigo())){
                albuns.set(i, albumAtualizado);
                break;
            }

        }
        atualizaAlbuns(albuns);
    }


    public static Album fromTxt(String registro){
        String[] partes = registro.split(";");
        if (partes.length < 7){
        throw new IllegalArgumentException("Registro inválido: " + registro);
        }
        return new Album(partes[0], partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], partes[5], partes[6]);
    }

    public String toTxt(Album a){
        return a.getCodigo() + ";" + a.getTitulo() + ";" + a.getArtista() + ";" + a.getAno() + ";" + a.getGenero() + ";" + a.getLink() + ";" + a.getDescricao();
    }
}
