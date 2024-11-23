package main;

import Controlador.Controlador;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author Gaspar
 */
public class main { // Corrigido o nome da classe para começar com maiúscula

    public static void main(String[] args) { // Adicionado o método principal
        // Inicializar o controlador
        Controlador controller = new Controlador();

        controller.addEspacoFisico(1, "Nome 1", "Rua 1");
        controller.addEspacoFisico(2, "Nome 2", "Rua 2");
        controller.addEspacoFisico(3, "Nome 3", "Rua 3");

        controller.addUsuario("user1", "user1", "user1", "user1", "user1", "user1", 0);
        controller.autenticarUsuario("user1", "user1");

        ZonedDateTime dataInicio = ZonedDateTime.of(2025, 11, 17, 10, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        Duration duracao = Duration.ofHours(4);
        // Adiciona a reserva
        boolean resultado = controller.addReserva(
                1, // ID do espaço físico
                dataInicio, // Data e hora de início com fuso horário
                duracao // Duração de 4 horas
        );
        
        dataInicio = ZonedDateTime.of(2023, 11, 20, 12, 0, 0, 0, ZoneId.of("America/Sao_Paulo"));
        duracao = Duration.ofHours(4);
        // Adiciona a reserva
        resultado = controller.addReserva(
                1, // ID do espaço físico
                dataInicio, // Data e hora de início com fuso horário
                duracao // Duração de 4 horas
        );

    }
}
