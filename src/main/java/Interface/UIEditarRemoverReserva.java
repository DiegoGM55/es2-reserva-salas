package Interface;

import Controlador.ReservaController;
import Modelo.Reserva;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UIEditarRemoverReserva extends JFrame {

    private ReservaController reservaController;
    private Reserva reserva;
    private Runnable callback;

    public UIEditarRemoverReserva(ReservaController reservaController, Reserva reserva, Runnable callback) {
        this.reservaController = reservaController;
        this.reserva = reserva;
        this.callback = callback;
        initComponents();
    }

    private void initComponents() {
        // Formatação
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Componentes
        JLabel labelTitulo = new JLabel("Editar/Remover Reserva");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelEspaco = new JLabel("Espaço:");
        JTextField inputEspaco = new JTextField(reserva.getEspacoFisico().getNome());
        inputEspaco.setEditable(false); // O espaço não pode ser alterado

        JLabel labelDataInicio = new JLabel("Data de Início:");
        JTextField inputDataInicio = new JTextField(reserva.getDataReservaInicio().toLocalDate().format(dateFormatter));

        JLabel labelHoraInicio = new JLabel("Hora de Início:");
        JTextField inputHoraInicio = new JTextField(reserva.getHorarioInicio().format(timeFormatter));

        JLabel labelDuracao = new JLabel("Duração (em horas):");
        JTextField inputDuracao = new JTextField(String.valueOf(reserva.getDuracao().toHours()));

        JButton bttnSalvar = new JButton("Salvar Alterações");
        JButton bttnRemover = new JButton("Remover Reserva");
        JButton bttnCancelar = new JButton("Cancelar");

        // Ação para salvar alterações
        bttnSalvar.addActionListener(evt -> {
            String dataInicioStr = inputDataInicio.getText().trim();
            String horaInicioStr = inputHoraInicio.getText().trim();
            String duracaoStr = inputDuracao.getText().trim();

            if (dataInicioStr.isEmpty() || horaInicioStr.isEmpty() || duracaoStr.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Todos os campos devem ser preenchidos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                LocalDate dataInicio = LocalDate.parse(dataInicioStr, dateFormatter);
                LocalTime horaInicio = LocalTime.parse(horaInicioStr, timeFormatter);
                int duracaoHoras = Integer.parseInt(duracaoStr);

                // Atualiza a reserva
                reservaController.atualizaReserva(
                        reserva.getId(),
                        dataInicio.atTime(horaInicio).atZone(java.time.ZoneId.systemDefault()),
                        reserva.getUsuario(),
                        reserva.getEspacoFisico(),
                        horaInicio,
                        java.time.Duration.ofHours(duracaoHoras),
                        reserva.getStatus()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Reserva atualizada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (callback != null) {
                    callback.run(); // Atualiza as tabelas na tela principal
                }
                this.dispose(); // Fecha a janela
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro nos dados inseridos. Por favor, verifique os campos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Ação para remover reserva
        bttnRemover.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja remover esta reserva?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                reservaController.deleteReserva(reserva.getId());
                JOptionPane.showMessageDialog(
                        this,
                        "Reserva removida com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (callback != null) {
                    callback.run(); // Atualiza as tabelas na tela principal
                }
                this.dispose(); // Fecha a janela
            }
        });

        // Ação para cancelar
        bttnCancelar.addActionListener(evt -> this.dispose());

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(labelTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(labelEspaco, gbc);

        gbc.gridx = 1;
        panel.add(inputEspaco, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelDataInicio, gbc);

        gbc.gridx = 1;
        panel.add(inputDataInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelHoraInicio, gbc);

        gbc.gridx = 1;
        panel.add(inputHoraInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelDuracao, gbc);

        gbc.gridx = 1;
        panel.add(inputDuracao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bttnSalvar, gbc);

        gbc.gridy++;
        panel.add(bttnRemover, gbc);

        gbc.gridy++;
        panel.add(bttnCancelar, gbc);

        // Configuração da janela
        this.setTitle("Editar/Remover Reserva");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
    }
}
