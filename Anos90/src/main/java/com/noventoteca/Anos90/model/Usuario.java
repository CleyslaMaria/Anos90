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
