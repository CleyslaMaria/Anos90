package com.noventoteca.Anos90.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private List<String> favoritos;

    public Usuario(String id, String nome, String email, String senha){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.favoritos = new ArrayList<>();
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

        public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public List<String> getFavoritos(){
        return favoritos;
    }

    public void setFavoritos(List<String> favoritos){
        this.favoritos = favoritos;
    }

    public void adicionarFavorito(String idAlbum){
        if(!favoritos.contains(idAlbum)){
            favoritos.add(idAlbum);
        }
    }

    public void remodeFavorito(String idAlbum){
        favoritos.remove(idAlbum);
    }

    public String toTxt(){
        String favs = String.join(",", favoritos);
        return id + ";" + nome + ";" + email + ";" + senha + ";" + favs;
    }

    public static Usuario fromTxt(String registro){
        String[] partes = registro.split(";");
        if (partes.length < 4){
            throw new IllegalArgumentException("Registro invalido: " + registro);

        }

        Usuario usuario = new Usuario(
            partes[0],
            partes[1],
            partes[2],
            partes[3]
        );

        if(partes.length == 5 && !partes[4].isEmpty()){
            String[] favs = partes[4].split(",");
            for(String f : favs){
                usuario.adicionarFavorito(f);
            }
        }
        return usuario;

    }

    @Override
    public String toString(){
        return "Usuario{" +
        "id='" + id +'\''+
        ", nome='" + nome + '\'' +
        ", email='" + email + '\'' +
        ", favoritos=" + favoritos +
        '}';
    }
}
