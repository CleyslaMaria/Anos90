package com.noventoteca.Anos90.model;

 /**Classe abstrata que representa um tipo genérico de mídia*/
public abstract class Midia {
     /** Atributos privados que representam as informações comuns a todas as mídias */
    private String titulo;
    private String artista;
    private int ano;
    private String genero;
    private String link;
    private String descricao;

    /**
     * Construtor padrão sem argumentos
     * Necessário para a deserialização de objetos
     */
    public Midia(){
        
    }

    /**
     * Construtor com todos os argumentos para inicializar uma nova instância de Midia
     * @param titulo título da mídia
     * @param artista artista ou banda
     * @param ano ano de lançamento
     * @param genero gênero musical
     * @param link link da imagem de capa
     * @param descricao descrição da mídia
     */
    public Midia(String titulo, String artista, int ano, String genero, String link, String descricao){
        this.titulo = titulo;
        this.artista = artista;
        this.ano = ano;
        this.genero = genero;
        this.link = link;
        this.descricao = descricao;
    }

    // Métodos getters e setters para cada atributo, seguindo o padrão de encapsulamento
    /** Retorna o título da mídia*/
    public String getTitulo(){
        return titulo;
    }
        
    /**Define o título da mídia*/
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    /** Retorna o artista da mídia*/
    public String getArtista(){
        return artista;
    }
     /**Define o artista da mídia*/
    public void setArtista(String artista){
        this.artista = artista;
    }
    /** Retorna o ano da mídia*/
    public int getAno(){
        return ano;
    }
     /**Define o ano da mídia*/
    public void setAno(int ano){
        this.ano = ano;
    }
    /** Retorna o genero da mídia*/
    public String getGenero(){
        return genero;
    }
     /**Define o genero da mídia*/
    public void setGenero(String genero){
        this.genero = genero;
    }
    /** Retorna o link da mídia*/
    public String getLink(){
        return link;
    }
     /**Define o link da mídia*/
    public void setLink(String link){
        this.link = link;
    }
    /** Retorna a descrição da mídia*/
    public String getDescricao(){
        return descricao;
    }
     /**Define a descrição da mídia*/
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    
    /**Converte o objeto Midia em uma linha de texto formatada.*/
    public String toTxt(){
        return titulo + ";" + artista + ";" + ano + ";" + genero + ";" + link + ";" + descricao;
    }
}