/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

public class EspacoFisicoSerializable {
    private final ArrayList<EspacoFisico> espacosFisicos = new ArrayList<>();
    private int nextId = 0; // Gerador de IDs

    public EspacoFisico registraEspacoFisico(String nome, String endereco, int capacidadeMaxima, String observacao) {
        EspacoFisico espaco = new EspacoFisico(nextId++, nome, endereco, capacidadeMaxima, observacao);
        espacosFisicos.add(espaco);
        return espaco;
    }

    public EspacoFisico atualizaEspacoFisico(int id, String nome, String endereco, int capacidadeMaxima, String observacao) {
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getId() == id) {
                espaco.setNome(nome);
                espaco.setEndereco(endereco);
                espaco.setCapacidadeMaxima(capacidadeMaxima);
                espaco.setObservacao(observacao);
                return espaco;
            }
        }
        return null; // Espaço físico não encontrado
    }

    public void excluiEspacoFisico(int id) {
        espacosFisicos.removeIf(espaco -> espaco.getId() == id);
    }

    public EspacoFisico buscaEspacoFisico(String nome) {
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getNome().equalsIgnoreCase(nome)) {
                return espaco;
            }
        }
        return null; // Espaço físico não encontrado
    }

    public ArrayList<EspacoFisico> buscaEspacoFisicoPorCapacidade(int capacidadeMaxima) {
        ArrayList<EspacoFisico> espacosEncontrados = new ArrayList<>();
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getCapacidadeMaxima() <= capacidadeMaxima) {
                espacosEncontrados.add(espaco);
            }
        }
        return espacosEncontrados;
    }

    public ArrayList<EspacoFisico> listaEspacosFisicos() {
        return espacosFisicos;
    }

    public EspacoFisico buscaEspacoFisicoById(int id) {
        for (EspacoFisico espaco : espacosFisicos) {
            if (espaco.getId() == id) {
                return espaco;
            }
        }
        return null; // Espaço físico não encontrado
    }
}
