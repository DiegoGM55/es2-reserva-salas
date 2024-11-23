/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Controle;
import Modelo.Usuario;
import Modelo.EspacoFisico;
import Modelo.Reserva;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Controlador {

    private final Controle controle = new Controle();

    public void addEspacoFisico(int id, String nome, String endereco) {
        EspacoFisico v = new EspacoFisico(id, nome, endereco);
        controle.addEspacoFisico(v);
    }

    public String imprimirEspacoFisico() {
        return controle.imprimirEspacoFisico();
    }

    public void excluirEspacoFisico(int id) {
        controle.excluirEspacoFisico(id);
    }

    public void addUsuario(String nome, String telefone, String email, String cpf, String ra, String senha, int tipoUsuario) {
        // Criar o objeto Usuario com os parâmetros fornecidos
        Usuario usuario = new Usuario(controle.getNewIdEspacos(), nome, telefone, email, cpf, ra, senha, tipoUsuario);
        controle.addUsuario(usuario);
    }

    public int autenticarUsuario(String email, String senha) {
        return controle.autenticarUsuario(email, senha);
    }

    public boolean addReserva(int idEspaco, ZonedDateTime dataInicioReserva, Duration duracao) {
        System.out.println("INICIANDO RESERVA");
        Usuario user = controle.getUsuarioById(controle.getUsuarioLogado());
        EspacoFisico espaco = controle.getEspacoFisicoById(idEspaco);
        ZonedDateTime dataFimReserva = dataInicioReserva.plus(duracao);

        if (dataInicioReserva.isBefore(ZonedDateTime.now())) {
            System.out.println("Erro: A data de início da reserva não pode ser no passado.");
            return false;
        }

        if (controle.validaReserva(espaco, dataInicioReserva, dataFimReserva)) {
            Reserva novaReserva = new Reserva(
                    controle.getLenReserva() + 1,
                    espaco,
                    user,
                    dataInicioReserva,
                    dataFimReserva,
                    0
            );
            controle.addReserva(novaReserva);
            return true;
        }

        return false;
    }

    public ArrayList<Reserva> getReservasPendentes() {
        return controle.getReservasPendentes();
    }


}
