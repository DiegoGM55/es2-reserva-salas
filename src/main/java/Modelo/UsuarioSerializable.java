/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

public class UsuarioSerializable {
    private final ArrayList<Usuario> usuarios = new ArrayList<>();
    private int nextId = 0; // Gerador de IDs

    public Usuario registrarUsuario(String nome, String email, String telefone, String ra, String cpf, String senha) {
        Usuario usuario = new Usuario(nextId++, nome, telefone, email, cpf, ra, senha);
        usuarios.add(usuario);
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
                return usuario;
            }
        }
        return null; // Usuário não encontrado
    }

    public void deletarUsuario(String email) {
        usuarios.removeIf(usuario -> usuario.getEmail().equals(email));
    }
    
    public void deletarUsuarioPorID(int id) {
        usuarios.removeIf(usuario -> usuario.getId() == id);
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
}
