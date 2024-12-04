package Modelo;

import java.io.*;
import java.util.ArrayList;

public class AdministradorSerializable implements Serializable {
    private static final long serialVersionUID = 1L; // Para controle de versão da serialização
    private final ArrayList<Administrador> administradores = new ArrayList<>();
    private int nextId = 1; // Gerador de IDs

    private static final String FILE_PATH = "administradores.dat";

    public AdministradorSerializable() {
        carregarDados();
    }

    public Administrador registrarAdmin(String nome, String email, String cpf, String senha, String cfep) {
        Administrador admin = new Administrador(nextId++, nome, email, cpf, senha, cfep);
        administradores.add(admin);
        salvarDados();
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
                salvarDados();
                return admin;
            }
        }
        return null; // Administrador não encontrado
    }

    public void excluirAdmin(String email) {
        administradores.removeIf(admin -> admin.getEmail().equals(email));
        salvarDados();
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

    // Métodos de persistência
    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(administradores);
            oos.writeInt(nextId);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de administradores: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            ArrayList<Administrador> carregados = (ArrayList<Administrador>) ois.readObject();
            administradores.addAll(carregados);
            nextId = ois.readInt();
        } catch (FileNotFoundException e) {
            // Arquivo não existe ainda, será criado ao salvar
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados de administradores: " + e.getMessage());
        }
    }
}
