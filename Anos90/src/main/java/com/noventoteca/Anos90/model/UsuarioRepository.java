package com.noventoteca.Anos90.model;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    private final String arquivoUsuarios;

    public UsuarioRepository() {
        // Define caminho para salvar os dados fora do classpath
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs(); // cria a pasta caso não exista
        }

        arquivoUsuarios = "data/usuarios.txt";
    }

    /**
     * Lista todos os usuários do arquivo
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(arquivoUsuarios);

        if (!file.exists()) {
            System.out.println("Arquivo de usuários não encontrado: " + arquivoUsuarios);
            return usuarios;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), java.nio.charset.StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) { // Ignora linhas vazias
                    usuarios.add(fromTxt(linha));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de usuários: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao processar registro de usuário: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Salva um usuário no arquivo (acrescenta no final)
     */
    public void salvarUsuario(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivoUsuarios, true),
                        java.nio.charset.StandardCharsets.UTF_8))) {
            bw.write(toTxt(usuario));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Atualiza os dados de um usuário existente
     */
    public void atualizaUsuario(Usuario usuario) {
        List<Usuario> usuarios = listarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        salvarTodos(usuarios);
    }

    public Usuario buscarPorId(String id) {
        return listarUsuarios().stream()
                .filter(u -> u.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorEmail(String email) {
        return listarUsuarios().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public void removerUsuario(String id) {
        List<Usuario> usuarios = listarUsuarios();
        usuarios.removeIf(u -> u.getId().equalsIgnoreCase(id));
        salvarTodos(usuarios);
    }

    /**
     * Salva todos os usuários no arquivo (reescreve o arquivo)
     */
    public void salvarTodos(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivoUsuarios, false),
                        java.nio.charset.StandardCharsets.UTF_8))) {
            for (Usuario u : usuarios) {
                bw.write(toTxt(u));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar todos os usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converte um usuário em linha de texto
     */
    public String toTxt(Usuario u) {
        String favs = "";
        if (u.getFavoritos() != null) {
            favs = String.join(",", u.getFavoritos());
        }
        String nome = u.getNome() != null ? u.getNome() : "";
        String email = u.getEmail() != null ? u.getEmail() : "";
        String senha = u.getSenha() != null ? u.getSenha() : "";

        return u.getId() + ";" + nome + ";" + email + ";" + senha + ";" + favs;
    }

    /**
     * Constrói um usuário a partir de uma linha de texto
     */
    public static Usuario fromTxt(String registro) {
        String[] partes = registro.split(";");
        if (partes.length < 4) {
            throw new IllegalArgumentException("Registro inválido: " + registro);
        }

        Usuario usuario = new Usuario(partes[0], partes[1], partes[2], partes[3]);

        // Processa favoritos (se houver)
        if (partes.length >= 5 && partes[4] != null && !partes[4].isEmpty()) {
            String[] favs = partes[4].split(",");
            for (String f : favs) {
                if (f != null && !f.trim().isEmpty()) {
                    usuario.adicionarFavorito(f.trim());
                }
            }
        }
        return usuario;
    }
}
