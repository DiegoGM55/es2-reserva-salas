/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.EspacoFisico;
import Modelo.EspacoFisicoSerializable;
import java.util.ArrayList;

public class EspacoFisicoController {
    private final EspacoFisicoSerializable serializable = new EspacoFisicoSerializable();

    public EspacoFisico registraEspacoFisico(String nome, String endereco, int capacidadeMaxima, String observacao) {
        return serializable.registraEspacoFisico(nome, endereco, capacidadeMaxima, observacao);
    }

    public EspacoFisico atualizaEspacoFisico(int id, String nome, String endereco, int capacidadeMaxima, String observacao) {
        return serializable.atualizaEspacoFisico(id, nome, endereco, capacidadeMaxima, observacao);
    }

    public void excluiEspacoFisico(int id) {
        serializable.excluiEspacoFisico(id);
    }

    public EspacoFisico buscaEspacoFisico(String nome) {
        return serializable.buscaEspacoFisico(nome);
    }
    
    public EspacoFisico buscaEspacoFisicoById(int id) {
        return serializable.buscaEspacoFisicoById(id);
    }

    public ArrayList<EspacoFisico> buscaEspacoFisicoPorCapacidade(int capacidadeMaxima) {
        return serializable.buscaEspacoFisicoPorCapacidade(capacidadeMaxima);
    }

    public ArrayList<EspacoFisico> listaEspacosFisicos() {
        return serializable.listaEspacosFisicos();
    }
}

