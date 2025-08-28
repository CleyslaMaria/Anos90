package com.noventoteca.Anos90.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a entidade de um usuário na aplicação
 * Armazena as informações de login, dados pessoais e a lista de álbuns favoritos. */
public class Usuario {
    /** Atributos privados que representam os dados de um usuário*/
    private String id;
    private String nome;
    private String email;
    private String senha;
    private List<String> favoritos;

    /**
     * Construtor com argumentos para inicializar um novo usuário
     * @param id O identificador único do usuário
     * @param nome O nome completo do usuário
     * @param email O email do usuário, usado para login
     * @param senha A senha do usuário (em texto simples para este exemplo)
     */
    public Usuario(String id, String nome, String email, String senha){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.favoritos = new ArrayList<>();
    }
    
    // Métodos getters e setters para cada atributo, seguindo o padrão de encapsulamento
    /** Retorna o ID do usuário*/
    public String getId(){
        return id;
    }
    /**Define o ID do usuário*/
    public void setId(String id){
        this.id = id;
    }
    /** Retorna o nome do usuário*/
    public String getNome(){
        return nome;
    }
    /**Define o nome do usuário*/
    public void setNome(String nome){
        this.nome = nome;
    }
    /** Retorna o email do usuário*/
    public String getEmail(){
        return email;
    }
    /**Define o email do usuário*/
    public void setEmail(String email){
        this.email = email;
    }
    /** Retorna a senha do usuário*/
    public String getSenha(){
        return senha;
    }
    /**Define o senha do usuário*/
    public void setSenha(String senha){
        this.senha = senha;
    }

    /**
     * Retorna a lista de IDs de álbuns favoritos do usuário
     * @return Uma lista de Strings
     */
    public List<String> getFavoritos(){
        return favoritos;
    }

    
    /**
     * Define a lista de IDs de álbuns favoritos do usuário
     * @param favoritos A nova lista de favoritos
     */
    public void setFavoritos(List<String> favoritos){
        this.favoritos = favoritos;
    }

    /**
     * Adiciona o ID de um álbum à lista de favoritos, se ele ainda não estiver lá
     * @param idAlbum  ID do álbum a ser adicionado
     */
    public void adicionarFavorito(String idAlbum){
        if(!favoritos.contains(idAlbum)){
            favoritos.add(idAlbum);
        }
    }

    /**
     * Remove o ID de um álbum da lista de favoritos
     * @param idAlbum  ID do álbum a ser removido
     * */
    public void removerFavorito(String idAlbum){
        favoritos.remove(idAlbum);
    }


    /**
     * Retorna uma representação em String do objeto Usuario.
     * Útil para depuração.
     * @return Uma String formatada com os dados do usuário.
     */
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
