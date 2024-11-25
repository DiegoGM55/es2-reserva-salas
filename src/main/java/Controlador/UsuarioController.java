/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Modelo.UsuarioSerializable;

public class UsuarioController {
    private final UsuarioSerializable serializable = new UsuarioSerializable();
    private Usuario usuarioLogado; // Variável para armazenar o usuário logado


    public Usuario registrarUsuario(String nome, String email, String telefone, String ra, String cpf, String senha) {
        return serializable.registrarUsuario(nome, email, telefone, ra, cpf, senha);
    }

    public Usuario atualizarUsuario(int usuarioID, String nome, String email, String telefone, String ra, String cpf, String senha) {
        return serializable.atualizarUsuario(usuarioID, nome, email, telefone, ra, cpf, senha);
    }

    public void deletarUsuario(String email) {
        serializable.deletarUsuario(email);
    }
    
    public void deletarUsuarioPorID(int id) {
        serializable.deletarUsuarioPorID(id);
    }

    public Usuario buscarUsuarioPorID(int usuarioID) {
        return serializable.buscarUsuarioPorID(usuarioID);
    }
    
    public Usuario buscarUsuarioPorEmail(String email) {
        return serializable.buscarUsuarioPorEmail(email);
    }

    public boolean login(String email, String senha) {
        boolean isAuthenticated = serializable.loginUser(email, senha);
        if (isAuthenticated) {
            this.usuarioLogado = buscarUsuarioPorEmail(email); // Atualiza o usuário logado
        }
        return isAuthenticated;
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
