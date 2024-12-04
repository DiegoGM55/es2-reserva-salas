package Modelo;

import java.util.ArrayList;
import java.io.*;

public class UsuarioSerializable implements Serializable {
    private static final long serialVersionUID = 1L; // Controle de versão para serialização
    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    private int nextId = 0; // Gerador de IDs
    private static final String FILE_PATH = "usuarios.dat";

    public UsuarioSerializable() {
        carregarDados(); // Carrega os dados automaticamente ao instanciar a classe
    }

    public Usuario registrarUsuario(String nome, String telefone, String email, String ra, String cpf, String senha) {
        Usuario usuario = new Usuario(nextId++, nome, telefone, email, ra, cpf, senha);
        usuarios.add(usuario);
        salvarDados(); // Salva os dados após registrar um usuário
        return usuario;
    }

    public Usuario atualizarUsuario(int id, String nome, String email, String telefone, String ra, String cpf, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setTelefone(telefone);
                usuario.setRa(ra);
                usuario.setCpf(cpf);
                usuario.setSenha(senha);
                salvarDados(); // Salva os dados após atualizar um usuário
                return usuario;
            }
        }
        return null; // Usuário não encontrado
    }

    public void deletarUsuario(String email) {
        if (usuarios.removeIf(usuario -> usuario.getEmail().equals(email))) {
            salvarDados(); // Salva os dados após remover um usuário
        }
    }

    public void deletarUsuarioPorID(int id) {
        if (usuarios.removeIf(usuario -> usuario.getId() == id)) {
            salvarDados(); // Salva os dados após remover um usuário
        }
    }

    public Usuario buscarUsuarioPorID(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null; // Usuário não encontrado
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null; // Usuário não encontrado
    }

    public boolean loginUser(String email, String senha) {
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) {
                return true; // Login bem-sucedido
            }
        }
        return false; // Email ou senha incorretos
    }

    // Métodos de persistência
    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(usuarios);
            oos.writeInt(nextId);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de usuários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            ArrayList<Usuario> carregados = (ArrayList<Usuario>) ois.readObject();
            usuarios.addAll(carregados);
            nextId = ois.readInt();
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe; será criado ao salvar
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados de usuários: " + e.getMessage());
        }
    }
}
