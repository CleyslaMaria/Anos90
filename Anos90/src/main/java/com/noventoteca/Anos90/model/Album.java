package com.noventoteca.Anos90.model;

/**
 * Representa a entidade Album, que herda da classe abstrata Midia.
 * Esta classe contém informações específicas de um álbum musical.
 */
public class Album extends Midia {
    private String codigo; /** Campo para armazenar o código de identificação único do álbum8*/

    /**Construtor padrão sem argumentos,  
     * Pois é necessário para que o Spring e o Thymeleaf possam criar instâncias da classe
     * */ 
    public Album(){
        super();
    }

    /** Contrutor parametrizado 
     * Cria um album com valores iniciais
    */
    public Album(String codigo, String titulo, String artista, int ano, String genero, String link, String descricao){
        super(titulo, artista, ano, genero, link, descricao);
        this.codigo = codigo;
    }

    /** Método que retorna o código do álbum */
    public String getCodigo(){
        return codigo;
    }

    /** Método que define o código do álbum */
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    /** Sobrescreve o método padrão para fornecer uma representação em texto do objeto Album*/
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
