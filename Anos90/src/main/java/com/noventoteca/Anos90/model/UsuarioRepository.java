package com.noventoteca.Anos90.model;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

/**
 * Repositório responsável por lidar com a persistência de dados dos usuários
 * em um arquivo de texto.
 */
@Repository
public class UsuarioRepository {
    /** Caminho do arquivo onde os usuários serão salvos. */
    private final String arquivoUsuarios = "dados/usuarios.txt";

    /**
     * Verifica se o arquivo de dados externo existe. Se não existir,
     * ele é criado. Caso exista um arquivo inicial em resources,
     * ele é copiado para o novo arquivo.
     */
    public UsuarioRepository() {
        inicializarSeNecessario();
    }

    private void inicializarSeNecessario() {
        File file = new File(arquivoUsuarios);

        if (!file.exists()) {
            file.getParentFile().mkdirs(); 
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("dados/usuarios_iniciais.txt")) {
                if (is != null) {
                    Files.copy(is, file.toPath());
                    System.out.println("Arquivo usuarios.txt criado com dados iniciais.");
                } else {
                    file.createNewFile(); 
                    System.out.println("Arquivo usuarios.txt criado vazio.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Lista todos os usuários do arquivo
     * @return Uma lista de objetos Usuario
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
                if (!linha.trim().isEmpty()) { 
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
     * Salva um usuário no arquivo, adicionando-o ao final
     * @param usuario objeto Usuario a ser salvo
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
     * @param usuario objeto Usuario com os dados atualizados
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


    /**
     * Busca um usuário pelo seu ID
     * @param id ID do usuário a ser buscado
     * @return objeto Usuario encontrado, ou null se não for encontrado
     */
    public Usuario buscarPorId(String id) {
        return listarUsuarios().stream()
                .filter(u -> u.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca um usuário pelo seu email
     * @param email O email do usuário a ser buscado
     * @return O objeto Usuario encontrado, ou null se não for encontrado
     */
    public Usuario buscarPorEmail(String email) {
        return listarUsuarios().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Remove um usuário da coleção pelo seu ID
     * @param id O ID do usuário a ser removido
     */
    public void removerUsuario(String id) {
        List<Usuario> usuarios = listarUsuarios();
        usuarios.removeIf(u -> u.getId().equalsIgnoreCase(id));
        salvarTodos(usuarios);
    }

    /**
     * Salva todos os usuários no arquivo, reescrevendo-o.
     * @param usuarios A lista completa e atualizada de usuários.
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
     * Converte um objeto Usuario em uma linha de texto para ser salva no arquivo
     * @param u O objeto Usuario a ser convertido
     * @return Uma String formatada para o arquivo de texto
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
     * Converte uma linha de texto do arquivo em um objeto Usuario
     * @param registro  linha de texto a ser processada
     * @return  objeto Usuario criado a partir da linha
     * @throws IllegalArgumentException se o registro for inválido
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