/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class Reserva {

    private int id;
    private EspacoFisico espaco;
    private Usuario usuario;
    private ZonedDateTime dataInicioReserva;
    private ZonedDateTime dataFimReserva;
    private LocalTime horaInicio;
    private Duration duracao;
    private int status;

    public Reserva(int id, EspacoFisico espaco, Usuario usuario, ZonedDateTime dataInicioReserva, ZonedDateTime dataFimReserva, int status) {
        this.id = id;
        this.espaco = espaco;
        this.usuario = usuario;
        this.dataInicioReserva = dataInicioReserva;
        this.dataFimReserva = dataFimReserva;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reserva{"
                + "id=" + id
                + ",\nespaco=" + (espaco != null ? espaco.getNome() : "N/A")
                + ",\nusuario=" + (usuario != null ? usuario.getNome() : "N/A")
                + ",\ndataInicioReserva=" + dataInicioReserva
                + ",\ndataFimReserva=" + dataFimReserva
                + ",\nhoraInicio=" + (horaInicio != null ? horaInicio : "N/A")
                + ",\nduracao=" + (duracao != null ? duracao.toHours() + " horas" : "N/A")
                + ",\nstatus=" + status
                + '}';
    }

    
    public int getEspacoId(){
        return this.espaco.getId();
    }
    
    public int getUsuarioId(){
        return this.usuario.getId();
    }
    
    public ZonedDateTime getDataInicioReserva() {
        return dataInicioReserva;
    }

    public void setDataInicioReserva(ZonedDateTime dataInicioReserva) {
        this.dataInicioReserva = dataInicioReserva;
    }

    public ZonedDateTime getDataFimReserva() {
        return dataFimReserva;
    }

    public void setDataFimReserva(ZonedDateTime dataFimReserva) {
        this.dataFimReserva = dataFimReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EspacoFisico getEspaco() {
        return espaco;
    }

    public void setEspaco(EspacoFisico espaco) {
        this.espaco = espaco;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
