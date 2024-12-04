package Modelo;

import java.io.*;
import java.util.ArrayList;

public class EspacoFisicoSerializable implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<EspacoFisico> espacosFisicos = new ArrayList<>();
    private int nextId = 1; // Gerador de IDs

    private static final String FILE_PATH = "espacos_fisicos.dat";

    public EspacoFisicoSerializable() {
        carregarDados();
    }

    public EspacoFisico registraEspacoFisico(String nome, String endereco, int capacidadeMaxima, String observacao) {
        EspacoFisico espaco = new EspacoFisico(nextId++, nome, endereco, capacidadeMaxima, observacao);
        espacosFisicos.add(espaco);
        salvarDados();
        return espaco;
    }

    public EspacoFisico atualizaEspacoFisico(int id, String nome, String endereco, int capacidadeMaxima, String observacao) {
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getId() == id) {
                espaco.setNome(nome);
                espaco.setEndereco(endereco);
                espaco.setCapacidadeMaxima(capacidadeMaxima);
                espaco.setObservacao(observacao);
                salvarDados();
                return espaco;
            }
        }
        return null;
    }

    public void excluiEspacoFisico(int id) {
        espacosFisicos.removeIf(espaco -> espaco.getId() == id);
        salvarDados();
    }

    public EspacoFisico buscaEspacoFisico(String nome) {
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getNome().equalsIgnoreCase(nome)) {
                return espaco;
            }
        }
        return null;
    }

    public EspacoFisico buscaEspacoFisicoById(int id) {
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getId() == id) {
                return espaco;
            }
        }
        return null;
    }

    public ArrayList<EspacoFisico> buscaEspacoFisicoPorCapacidade(int capacidadeMaxima) {
        ArrayList<EspacoFisico> resultado = new ArrayList<>();
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getCapacidadeMaxima() >= capacidadeMaxima) {
                resultado.add(espaco);
            }
        }
        return resultado;
    }

    public ArrayList<EspacoFisico> listaEspacosFisicos() {
        return new ArrayList<>(espacosFisicos);
    }

    // Métodos de persistência
    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(espacosFisicos);
            oos.writeInt(nextId);
            System.out.println("Dados salvos");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de espaços físicos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            ArrayList<EspacoFisico> carregados = (ArrayList<EspacoFisico>) ois.readObject();
            espacosFisicos.addAll(carregados);
            nextId = ois.readInt();
            System.out.println("Dados Carregados");
        } catch (FileNotFoundException e) {
            // Arquivo não existe ainda, será criado ao salvar
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados de espaços físicos: " + e.getMessage());
        }
    }
}
