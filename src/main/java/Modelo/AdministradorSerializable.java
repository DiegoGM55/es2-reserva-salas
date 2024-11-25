/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

public class AdministradorSerializable {
    private final ArrayList<Administrador> administradores = new ArrayList<>();
    private int nextId = 1; // Gerador de IDs

    public Administrador registrarAdmin(String nome, String email, String cpf, String senha, String cfep) {
        Administrador admin = new Administrador(nextId++, nome, email, cpf, senha, cfep);
        administradores.add(admin);
        return admin;
    }

    public Administrador atualizarAdmin(int id, String nome, String email, String cpf, String senha, String cfep) {
        for (Administrador admin : administradores) {
            if (admin.getId() == id) {
                admin.setNome(nome);
                admin.setEmail(email);
                admin.setCpf(cpf);
                admin.setSenha(senha);
                admin.setCfep(cfep);
                return admin;
            }
        }
        return null; // Administrador não encontrado
    }

    public void excluirAdmin(String email) {
        administradores.removeIf(admin -> admin.getEmail().equals(email));
    }

    public Administrador buscarAdminPorID(int id) {
        for (Administrador admin : administradores) {
            if (admin.getId() == id) {
                return admin;
            }
        }
        return null; // Administrador não encontrado
    }

    public boolean loginAdmin(String email, String senha) {
    for (Administrador admin : administradores) {
        if (admin.getEmail().equals(email) && admin.getSenha().equals(senha)) {
            return true; // Login bem-sucedido
        }
    }
    return false; // Email ou senha incorretos
}

}
