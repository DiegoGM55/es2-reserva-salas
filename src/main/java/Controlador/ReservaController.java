/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.EspacoFisico;
import Modelo.Reserva;
import Modelo.ReservaSerializable;
import Modelo.Usuario;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservaController {
    private final ReservaSerializable serializable = new ReservaSerializable();
    
    public Reserva registraReserva(ZonedDateTime dataReserva, Usuario usuario, EspacoFisico espacoFisico, LocalTime horarioInicio, Duration duracao, int status) {
        return serializable.registraReserva(dataReserva, usuario, espacoFisico, horarioInicio, duracao, status);
    }

    public Reserva atualizaReserva(int reservaID, ZonedDateTime dataReserva, Usuario usuario, EspacoFisico espacoFisico, LocalTime horarioInicio, Duration duracao, int status) {
        return serializable.atualizaReserva(reservaID, dataReserva, usuario, espacoFisico, horarioInicio, duracao, status);
    }

    public void deleteReserva(int reservaID) {
        serializable.deleteReserva(reservaID);
    }

    public Reserva buscarReservaPorID(int reservaID) {
        return serializable.buscarReservaPorID(reservaID);
    }
    
    public ArrayList<Reserva> buscarReservasPorEspaco(EspacoFisico espaçoFisico) {
        return serializable.buscarReservasPorEspaco(espaçoFisico);
    }
    
    public ArrayList<Reserva> getReservasPendentes() {
        return serializable.getReservasPendentes();
    }

    public ArrayList<Reserva> getReservasAprovadas() {
        return serializable.getReservasAprovadas();
    }

    public ArrayList<Reserva> getReservasRecusadas() {
        return serializable.getReservasRecusadas();
    }

    public ArrayList<Reserva> getReservasPendentesPorUsuario(int usuarioID) {
        return serializable.getReservasPendentesPorUsuario(usuarioID);
    }

    public ArrayList<Reserva> getReservasAprovadasPorUsuario(int usuarioID) {
        return serializable.getReservasAprovadasPorUsuario(usuarioID);
    }

    public ArrayList<Reserva> getReservasRecusadasPorUsuario(int usuarioID) {
        return serializable.getReservasRecusadasPorUsuario(usuarioID);
    }
}
