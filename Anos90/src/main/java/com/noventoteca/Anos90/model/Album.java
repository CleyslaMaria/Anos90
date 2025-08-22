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

    public static Album fromTxt(String registro){
        String[] partes = registro.split(";");
        if (partes.length < 7){
        throw new IllegalArgumentException("Registro inválido: " + registro);
        }
        return new Album(
            partes[0],
            partes[1],
            partes[2],
            Integer.parseInt(partes[3]),
            partes[4],
            partes[5],
            partes[6]
        );
    }

    @Override
    public String toTxt(){
        return codigo + ";" + getTitulo() + ";" + getArtista() + ";" + getAno() + ";" + getGenero() + ";" + getLink() + ";" + getDescricao();
    }

    @Override
    public String toString(){
        return "Album{" +
        "codigo='" + codigo +'\'' +
        ", titulo='" + getTitulo() + '\'' +
        ", artista='" + getArtista() + '\'' +
        ", ano='" + getAno() + '\'' +
        ", genero='" + getGenero() + '\'' +
        ", link='" + getLink() + '\'' +
        ", descricao='" + getDescricao() + '\'' +
        '}';
    }
    
}
