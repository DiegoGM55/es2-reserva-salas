/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.LocalTime;

import java.io.Serializable;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L; // Controle de versão para serialização
    
    private int id;
    private ZonedDateTime dataReservaInicio;
    private ZonedDateTime dataReservaFim;
    private Usuario usuario;
    private EspacoFisico espacoFisico;
    private LocalTime horarioInicio;
    private Duration duracao;
    private int status; // 0 = Pendente, 1 = Aprovada, 2 = Recusada

    // Construtor
    public Reserva(int id, ZonedDateTime dataReservaInicio, Usuario usuario, EspacoFisico espacoFisico, LocalTime horarioInicio, Duration duracao, int status) {
        this.id = id;
        this.dataReservaInicio = dataReservaInicio;
        //this.dataReservaFim = data
        this.usuario = usuario;
        this.espacoFisico = espacoFisico;
        this.horarioInicio = horarioInicio;
        this.duracao = duracao;
        this.status = status;
    }

    public ZonedDateTime getDataReservaInicio() {
        return dataReservaInicio;
    }

    public void setDataReservaInicio(ZonedDateTime dataReservaInicio) {
        this.dataReservaInicio = dataReservaInicio;
    }

    // Getters e Setters
    public ZonedDateTime getDataReservaFim() {
        return dataReservaFim;
    }

    public void setDataReservaFim(ZonedDateTime dataReservaFim) {
        this.dataReservaFim = dataReservaFim;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EspacoFisico getEspacoFisico() {
        return espacoFisico;
    }

    public void setEspacoFisico(EspacoFisico espacoFisico) {
        this.espacoFisico = espacoFisico;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
