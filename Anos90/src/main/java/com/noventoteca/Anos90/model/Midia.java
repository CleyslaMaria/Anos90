package com.noventoteca.Anos90.model;

public abstract class Midia {
    private String titulo;
    private String artista;
    private int ano;
    private String genero;
    private String link;
    private String descricao;

    public Midia(String titulo, String artista, int ano, String genero, String link, String descricao){
        this.titulo = titulo;
        this.artista = artista;
        this.ano = ano;
        this.genero = genero;
        this.link = link;
        this.descricao = descricao;
    }

    public String getTitulo(){
        return titulo;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getArtista(){
        return artista;
    }
    public void setArtista(String artista){
        this.artista = artista;
    }

    public int getAno(){
        return ano;
    }
    public void setAno(int ano){
        this.ano = ano;
    }

    public String getGenero(){
        return genero;
    }
    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    

    public String toTxt(){
        return titulo + ";" + artista + ";" + ano + ";" + genero + ";" + link + ";" + descricao;
    }
}
