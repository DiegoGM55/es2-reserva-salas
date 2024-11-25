package Interface;

import Controlador.ReservaController;
import Modelo.Reserva;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class UIAprovaReserva extends javax.swing.JFrame {
    private ReservaController reservaController;
    private int reservaId;
    private Reserva reserva;
    private Runnable updateTableCallback; // Callback para atualizar a tabela

    public UIAprovaReserva(ReservaController reservaController, int reservaId, Runnable updateTableCallback) {
        this.reservaController = reservaController;
        this.reservaId = reservaId;
        this.updateTableCallback = updateTableCallback;
        System.out.println("INICIANDO APROVACAO: " + this.reservaId);
        this.reserva = reservaController.buscarReservaPorID(reservaId); // Obtém a reserva pelo ID
        initComponents();
    }

    private void initComponents() {
        // Formatação de datas e horários
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Criando os componentes para exibir os detalhes da reserva
        JLabel labelTitulo = new JLabel("Detalhes da Reserva");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel labelId = new JLabel("ID: " + reserva.getId());
        JLabel labelEspaco = new JLabel("Espaço: " + (reserva.getEspacoFisico() != null ? reserva.getEspacoFisico().getNome() : "Não especificado"));
        JLabel labelUsuario = new JLabel("Usuário: " + (reserva.getUsuario() != null ? reserva.getUsuario().getNome() : "Não especificado"));

        JLabel labelDataInicio = new JLabel("Data de Início: " +
                (reserva.getDataReservaInicio() != null ? reserva.getDataReservaInicio().format(dateFormatter) : "Não especificada"));

        JLabel labelDataFim = new JLabel("Data de Fim: " +
                (reserva.getDataReservaFim() != null ? reserva.getDataReservaFim().format(dateFormatter) : "Não especificada"));

        JLabel labelHoraInicio = new JLabel("Hora de Início: " +
                (reserva.getHorarioInicio() != null ? reserva.getHorarioInicio().format(timeFormatter) : "Não especificada"));

        JLabel labelDuracao = new JLabel("Duração: " +
                (reserva.getDuracao() != null ? reserva.getDuracao().toHours() + " horas" : "Não especificada"));

        JLabel labelStatus = new JLabel("Status: " + getStatusDescricao(reserva.getStatus()));

        JButton bttnAprovar = new JButton("Aprovar");
        JButton bttnRecusar = new JButton("Recusar");

        // Configurar ações dos botões
        bttnAprovar.addActionListener(evt -> {
            reservaController.atualizaReserva(reservaId, reserva.getDataReservaInicio(), reserva.getUsuario(),
                    reserva.getEspacoFisico(), reserva.getHorarioInicio(), reserva.getDuracao(), 1); // Status: Aprovada
            JOptionPane.showMessageDialog(this, "Reserva aprovada com sucesso!");
            if (updateTableCallback != null) updateTableCallback.run(); // Atualiza a tabela
            this.dispose(); // Fecha a janela
        });

        bttnRecusar.addActionListener(evt -> {
            reservaController.atualizaReserva(reservaId, reserva.getDataReservaInicio(), reserva.getUsuario(),
                    reserva.getEspacoFisico(), reserva.getHorarioInicio(), reserva.getDuracao(), 2); // Status: Recusada
            JOptionPane.showMessageDialog(this, "Reserva recusada com sucesso!");
            if (updateTableCallback != null) updateTableCallback.run(); // Atualiza a tabela
            this.dispose(); // Fecha a janela
        });

        // Layout básico
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(labelTitulo)
                        .addComponent(labelId)
                        .addComponent(labelEspaco)
                        .addComponent(labelUsuario)
                        .addComponent(labelDataInicio)
                        .addComponent(labelDataFim)
                        .addComponent(labelHoraInicio)
                        .addComponent(labelDuracao)
                        .addComponent(labelStatus)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(bttnAprovar)
                                .addGap(10)
                                .addComponent(bttnRecusar))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(labelTitulo)
                        .addGap(10)
                        .addComponent(labelId)
                        .addComponent(labelEspaco)
                        .addComponent(labelUsuario)
                        .addComponent(labelDataInicio)
                        .addComponent(labelDataFim)
                        .addComponent(labelHoraInicio)
                        .addComponent(labelDuracao)
                        .addComponent(labelStatus)
                        .addGap(10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(bttnAprovar)
                                .addComponent(bttnRecusar))
        );

        pack();
        setLocationRelativeTo(null); // Centraliza a janela
    }

    // Método para traduzir o status numérico para uma descrição 
    private String getStatusDescricao(int status) {
        return switch (status) {
            case 0 -> "Pendente";
            case 1 -> "Aprovada";
            case 2 -> "Recusada";
            default -> "Desconhecido";
        };
    }
}
