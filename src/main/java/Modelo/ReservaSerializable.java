package Modelo;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ReservaSerializable implements Serializable {
    private static final long serialVersionUID = 1L; // Controle de versão para serialização
    private final ArrayList<Reserva> reservas = new ArrayList<>();
    private int nextId = 0; // Gerador de IDs

    private static final String FILE_PATH = "reservas.dat";

    public ReservaSerializable() {
        carregarDados();
    }

    public Reserva registraReserva(ZonedDateTime dataReserva, Usuario usuario, EspacoFisico espacoFisico,
                                   LocalTime horarioInicio, Duration duracao, int status) {
        Reserva reserva = new Reserva(nextId++, dataReserva, usuario, espacoFisico, horarioInicio, duracao, status);
        System.out.println("Reserva iniciada: " + reserva.getId() + " nome local: " + espacoFisico.getNome());
        reservas.add(reserva);
        salvarDados();
        return reserva;
    }

    public Reserva atualizaReserva(int reservaID, ZonedDateTime dataReserva, Usuario usuario, EspacoFisico espacoFisico,
                                   LocalTime horarioInicio, Duration duracao, int status) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == reservaID) {
                reserva.setDataReservaInicio(dataReserva);
                reserva.setUsuario(usuario);
                reserva.setEspacoFisico(espacoFisico);
                reserva.setHorarioInicio(horarioInicio);
                reserva.setDuracao(duracao);
                reserva.setStatus(status);
                salvarDados();
                return reserva;
            }
        }
        return null; // Reserva não encontrada
    }

    public void deleteReserva(int reservaID) {
        if (reservas.removeIf(reserva -> reserva.getId() == reservaID)) {
            salvarDados(); // Salva as mudanças após remover
        }
    }

    public Reserva buscarReservaPorID(int reservaID) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == reservaID) {
                return reserva;
            }
        }
        return null; // Reserva não encontrada
    }

    public ArrayList<Reserva> buscarReservasPorEspaco(EspacoFisico espacoFisico) {
        ArrayList<Reserva> reservasPorEspaco = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getEspacoFisico().equals(espacoFisico)) {
                reservasPorEspaco.add(reserva);
            }
        }
        return reservasPorEspaco;
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

    // Métodos de persistência
    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(reservas);
            oos.writeInt(nextId);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de reservas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            ArrayList<Reserva> carregadas = (ArrayList<Reserva>) ois.readObject();
            reservas.addAll(carregadas);
            nextId = ois.readInt();
        } catch (FileNotFoundException e) {
            // Arquivo ainda não existe; será criado ao salvar
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados de reservas: " + e.getMessage());
        }
    }
}
