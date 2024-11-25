/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Administrador;
import Modelo.AdministradorSerializable;

public class AdministradorController {
    private final AdministradorSerializable serializable = new AdministradorSerializable();

    public Administrador registrarAdmin(String nome, String cpf, String email, String senha, String cep) {
        return serializable.registrarAdmin(nome, cpf, email, senha, cep);
    }

    public Administrador atualizarAdmin(int id, String nome, String cpf, String email, String senha, String cep) {
        return serializable.atualizarAdmin(id, nome, cpf, email, senha, cep);
    }
    
    public void excluirAdmin(String email) {
        serializable.excluirAdmin(email);
    }

    public boolean login(String email, String senha) {
        // Lógica de login pode ser implementada aqui
        return serializable.loginAdmin(email,senha);
    }
}
