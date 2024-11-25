/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservaSerializable {
    private final ArrayList<Reserva> reservas = new ArrayList<>();
    private int nextId = 0; // Gerador de IDs

    public Reserva registraReserva(ZonedDateTime dataReserva, Usuario usuario, EspacoFisico espacoFisico, LocalTime horarioInicio, Duration duracao, int status) {
        Reserva reserva = new Reserva(nextId++, dataReserva, usuario, espacoFisico, horarioInicio, duracao, status);
        System.out.println("Reserva iniciada: " +  nextId + " nome local: " + espacoFisico.getNome());
        reservas.add(reserva);
        return reserva;
    }

    public Reserva atualizaReserva(int reservaID, ZonedDateTime dataReserva, Usuario usuario, EspacoFisico espacoFisico, LocalTime horarioInicio, Duration duracao, int status) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == reservaID) {
                reserva.setDataReservaInicio(dataReserva);
                reserva.setUsuario(usuario);
                reserva.setEspacoFisico(espacoFisico);
                reserva.setHorarioInicio(horarioInicio);
                reserva.setDuracao(duracao);
                reserva.setStatus(status);
                return reserva;
            }
        }
        return null; // Reserva não encontrada
    }

    public void deleteReserva(int reservaID) {
        reservas.removeIf(reserva -> reserva.getId() == reservaID);
        System.out.println("reservas:" + reservas);
    }

    public Reserva buscarReservaPorID(int reservaID) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == reservaID) {
                return reserva;
            }
        }
        return null; // Reserva não encontrada
    }

    public ArrayList<Reserva> buscarReservasPorEspaco(EspacoFisico espaçoFisico) {
        ArrayList<Reserva> reservasPorEspaco = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getEspacoFisico().equals(espaçoFisico)) {
                reservasPorEspaco.add(reserva);
            }
        }
        return reservasPorEspaco; // Reserva não encontrada
    }
    
    public ArrayList<Reserva> getReservasPendentes() {
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getStatus() == 0) { // Status 0 = Pendente
                pendentes.add(reserva);
            }
        }
        return pendentes;
    }

    public ArrayList<Reserva> getReservasAprovadas() {
        ArrayList<Reserva> aprovadas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getStatus() == 1) { // Status 1 = Aprovada
                aprovadas.add(reserva);
            }
        }
        return aprovadas;
    }

    public ArrayList<Reserva> getReservasRecusadas() {
        ArrayList<Reserva> recusadas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getStatus() == 2) { // Status 2 = Recusada
                recusadas.add(reserva);
            }
        }
        return recusadas;
    }

    public ArrayList<Reserva> getReservasPendentesPorUsuario(int usuarioID) {
        ArrayList<Reserva> pendentes = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getUsuario().getId() == usuarioID && reserva.getStatus() == 0) {
                pendentes.add(reserva);
            }
        }
        return pendentes;
    }

    public ArrayList<Reserva> getReservasAprovadasPorUsuario(int usuarioID) {
        ArrayList<Reserva> aprovadas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getUsuario().getId() == usuarioID && reserva.getStatus() == 1) {
                aprovadas.add(reserva);
            }
        }
        return aprovadas;
    }

    public ArrayList<Reserva> getReservasRecusadasPorUsuario(int usuarioID) {
        ArrayList<Reserva> recusadas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getUsuario().getId() == usuarioID && reserva.getStatus() == 2) {
                recusadas.add(reserva);
            }
        }
        return recusadas;
    }
}
