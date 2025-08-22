package com.noventoteca.Anos90.model;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    private final String arquivoUsuarios = "usuarios.txt";

    public UsuarioRepository(){
        File file = new File(arquivoUsuarios);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e) {
                System.err.println("Erro ao criar arquivo de usuarios: " + e.getMessage());
            }
        }
    }

    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                usuarios.add(fromTxt(linha));
            }
        } catch (IOException e) {

        }
        return usuarios;
    }

    public void salvarUsuario(Usuario usuario){
        try(BufferedWriter bw = new BufferedWriter((new FileWriter(arquivoUsuarios,true)))){
            bw.write(toTxt(usuario));
            bw.newLine();
        }catch(IOException e){
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void atualizaUsuario(Usuario usuario){
        List<Usuario> usuarios = listarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        salvarTodos(usuarios);
    }


    public Usuario buscarPorId(String id){
        return listarUsuarios().stream()
        .filter(u -> u.getId().equalsIgnoreCase(id))
        .findFirst()
        .orElse(null);
    }

    public Usuario buscaPorEmail(String email){
        return listarUsuarios().stream()
        .filter(u -> u.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElse(null);
    }

    public void removerUsuario(String id){
        List<Usuario> usuarios = listarUsuarios();
        usuarios.removeIf(u -> u.getId().equalsIgnoreCase(id));
        salvarTodos(usuarios);
    }

    public void atualizaDadosU(Usuario usuarioAtualizado){
        List<Usuario> usuarios = listarUsuarios();
        for(int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getId().equalsIgnoreCase(usuarioAtualizado.getId())){
                usuarios.set(i, usuarioAtualizado);
                break;
            }

        }
        salvarTodos(usuarios);
    }

    public void salvarTodos(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoUsuarios))) {
            for (Usuario u : usuarios) {
                bw.write(toTxt(u));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toTxt(Usuario u){
        String favs = String.join(",", u.getFavoritos());
        return u.getId() + ";" + u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + favs ;
    }

    public static Usuario fromTxt(String registro){
        String[] partes = registro.split(";");
        if (partes.length < 4){
            throw new IllegalArgumentException("Registro invalido: " + registro);

        }

        Usuario usuario = new Usuario(partes[0], partes[1], partes[2], partes[3]);

        if(partes.length == 5 && !partes[4].isEmpty()){
            String[] favs = partes[4].split(",");
            for(String f : favs){
                usuario.adicionarFavorito(f);
            }
        }
        return usuario;

    }
}
