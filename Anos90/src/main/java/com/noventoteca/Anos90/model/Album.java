package com.noventoteca.Anos90.model;


public class Album extends Midia {
    private String codigo;

    public Album(String codigo, String titulo, String artista, int ano, String genero, String link, String descricao){
        super(titulo, artista, ano, genero, link, descricao);
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
}
