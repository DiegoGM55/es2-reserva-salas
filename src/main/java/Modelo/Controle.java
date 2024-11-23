/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Controle {

    private int idUsuario = -1;
    private boolean autenticado = false;
    private boolean admin = false;
    private final static ArrayList<EspacoFisico> espacosFisicos = new ArrayList();
    private final static ArrayList<Usuario> usuarios = new ArrayList();
    private final static ArrayList<Reserva> reservas = new ArrayList();

    public Controle() {
    }

    // ESPAÇOS FISICOS
    public EspacoFisico getEspacoFisicoById(int id) {
        for (EspacoFisico espaco : Controle.espacosFisicos) { // Alterado de espacosFisicos para espaco
            if (espaco.getId() == id) {
                return espaco;
            }
        }
        return null;
    }

    public void addEspacoFisico(EspacoFisico l) {
        Controle.espacosFisicos.add(l);
    }

    public EspacoFisico recuperarEspacoFisico(int id) {
        // Procurar e remover o espaço físico pelo ID
        for (int i = 0; i < Controle.espacosFisicos.size(); i++) {
            EspacoFisico espaco = Controle.espacosFisicos.get(i);
            if (espaco.getId() == id) { // Verifica se o ID corresponde
                return espaco;
            }
        }
        return null;
    }

    public boolean excluirEspacoFisico(int id) {
        // Procurar e remover o espaço físico pelo ID
        for (int i = 0; i < Controle.espacosFisicos.size(); i++) {
            EspacoFisico espaco = Controle.espacosFisicos.get(i);
            if (espaco.getId() == id) { // Verifica se o ID corresponde
                Controle.espacosFisicos.remove(i); // Remove o item
                System.out.println("Espaço físico com ID " + id + " removido com sucesso.");
                return true; // Retorna true indicando sucesso
            }
        }
        System.out.println("Espaço físico com ID " + id + " não encontrado.");
        return false; // Retorna false caso não encontre
    }

    public String imprimirEspacoFisico() {
        System.out.println("Todos espaços cadastrados:");
        int contador = 1;
        String relatorio = "";

        for (EspacoFisico espaco : Controle.espacosFisicos) { // Alterado de espacosFisicos para espaco
            relatorio += "\n\n#" + contador;
            relatorio += espaco.imprimirEspacosFisicos(); // Agora acessa o objeto corretamente
            contador++;
        }
        if (relatorio.isEmpty()) {
            relatorio = "Nenhum espaço cadastrado";
        }
        return relatorio;
    }

    public int getNewIdEspacos() {

        return espacosFisicos.size() + 1;
    }

    // USUARIOS 
    public void addUsuario(Usuario v) {
        Controle.usuarios.add(v);
    }

    public int getUsuarioLogado() {
        return this.idUsuario;
    }

    public Usuario getUsuarioById(int id) {
        for (Usuario user : Controle.usuarios) { // Alterado de espacosFisicos para espaco
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public Usuario getUsuarioByEmail(String email) {
        for (Usuario user : Controle.usuarios) { // Alterado de espacosFisicos para espaco
            if (user.getEmail() == email) {
                return user;
            }
        }
        return null;
    }

    public int autenticarUsuario(String email, String senha) {
        for (Usuario user : Controle.usuarios) { // Alterado de espacosFisicos para espaco
            if (user.getEmail().equals(email)) {
                if (user.autenticar(email, senha)) {
                    this.autenticado = true;
                    this.idUsuario = user.getId();

                    System.out.println("USUARIO AUTENTICADO COM SUCESSO");

                    return user.getTipoUsuario();
                }
            }
        }
        return -1;
    }

    // RESERVA
    public boolean validaReserva(EspacoFisico espaco, ZonedDateTime dataInicioReserva, ZonedDateTime dataFimReserva) {
        for (Reserva reserva : Controle.reservas) {
            // Verifica se a reserva existente é para o mesmo espaço físico
            if (reserva.getEspaco().getId() == espaco.getId()) {
                // Obtém os horários de início e fim da reserva existente
                ZonedDateTime inicioExistente = reserva.getDataInicioReserva();
                ZonedDateTime fimExistente = reserva.getDataFimReserva();

                // Verifica se há sobreposição de horários
                boolean sobreposicao = !(dataFimReserva.isBefore(inicioExistente) || dataInicioReserva.isAfter(fimExistente));

                if (sobreposicao) {
                    System.out.println("Conflito de reserva encontrado com a reserva existente: ");
                    return false; // Reserva indisponível
                }
            }
        }

        // Se nenhuma sobreposição for encontrada
        System.out.println("Reserva disponível.");
        return true;
    }

    public int getLenReserva() {
        return this.reservas.size();
    }

    public void addReserva(Reserva v) {
        Controle.reservas.add(v);
    }

    public ArrayList<Reserva> getReservasPendentes() {
        ArrayList<Reserva> reservasPendentes = new ArrayList<>();
        for (Reserva reserva : Controle.reservas) {
            if (reserva.getStatus() == 0) { // Status 0 representa uma reserva pendente
                reservasPendentes.add(reserva);
            }
        }
        return reservasPendentes;
    }
    
    public ArrayList<Reserva> getReservasAprovadas() {
        ArrayList<Reserva> reservasAprovadas = new ArrayList<>();
        for (Reserva reserva : Controle.reservas) {
            if (reserva.getStatus() == 1) { // Status 0 representa uma reserva pendente
                reservasAprovadas.add(reserva);
            }
        }
        return reservasAprovadas;
    }

}
