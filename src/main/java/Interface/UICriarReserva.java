package Interface;

import Controlador.ReservaController;
import Controlador.EspacoFisicoController;
import Controlador.UsuarioController;
import Modelo.EspacoFisico;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UICriarReserva extends JFrame {

    private ReservaController reservaController;
    private EspacoFisicoController espacoController;
    private UsuarioController usuarioController;
    private Runnable callback;

    public UICriarReserva(ReservaController reservaController, EspacoFisicoController espacoController, UsuarioController usuarioController, Runnable callback) {
        this.reservaController = reservaController;
        this.espacoController = espacoController;
        this.usuarioController = usuarioController;
        this.callback = callback;
        initComponents();
    }

    private void initComponents() {
        // Componentes
        JLabel labelTitulo = new JLabel("Criar Nova Reserva");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelEspaco = new JLabel("Espaço:");
        JComboBox<String> comboEspacos = new JComboBox<>();
        ArrayList<EspacoFisico> espacos = espacoController.listaEspacosFisicos(); // Obtém os espaços físicos
        for (EspacoFisico espaco : espacos) {
            comboEspacos.addItem(espaco.getId() + " - " + espaco.getNome());
        }

        JLabel labelDataInicio = new JLabel("Data de Início:");
        JTextField inputDataInicio = new JTextField(10);
        inputDataInicio.setToolTipText("Formato: dd/MM/yyyy");

        JLabel labelHoraInicio = new JLabel("Hora de Início:");
        JTextField inputHoraInicio = new JTextField(5);
        inputHoraInicio.setToolTipText("Formato: HH:mm");

        JLabel labelDuracao = new JLabel("Duração (em horas):");
        JTextField inputDuracao = new JTextField(5);

        JButton bttnSalvar = new JButton("Salvar Reserva");
        JButton bttnCancelar = new JButton("Cancelar");

        // Ação para salvar reserva
        bttnSalvar.addActionListener(evt -> {
            String espacoSelecionado = (String) comboEspacos.getSelectedItem();
            if (espacoSelecionado == null || espacoSelecionado.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, selecione um espaço.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String dataInicioStr = inputDataInicio.getText().trim();
            String horaInicioStr = inputHoraInicio.getText().trim();
            String duracaoStr = inputDuracao.getText().trim();
            Usuario usuario = usuarioController.getUsuarioLogado();
                    
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
                System.out.println("usuario: " + usuario);
                int espacoId = Integer.parseInt(espacoSelecionado.split(" - ")[0]);
                System.out.println("EspaçoID" + espacoId);
                LocalDate dataInicio = LocalDate.parse(dataInicioStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                System.out.println("DataInicio:" + dataInicio);
                LocalTime horaInicio = LocalTime.parse(horaInicioStr, DateTimeFormatter.ofPattern("HH:mm"));
                System.out.println("horaInicio:" + horaInicio);
                int duracaoHoras = Integer.parseInt(duracaoStr);
                System.out.println("duracaoHoras:" + duracaoHoras);
                EspacoFisico espaco = espacoController.buscaEspacoFisicoById(espacoId);
                if (espaco == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Espaço físico não encontrado.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                reservaController.registraReserva(
                        dataInicio.atTime(horaInicio).atZone(java.time.ZoneId.systemDefault()),
                        usuario, // Usuário deve ser configurado conforme necessário
                        espaco,
                        horaInicio,
                        java.time.Duration.ofHours(duracaoHoras),
                        0 // Status inicial: pendente
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Reserva criada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (callback != null) {
                    callback.run(); // Atualiza a tabela de reservas pendentes
                }
                this.dispose(); // Fecha a janela
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro nos dados inseridos. Por favor, verifique os campos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                System.out.println("erro" + e);
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
        panel.add(comboEspacos, gbc);

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
        panel.add(bttnCancelar, gbc);

        // Configuração da janela
        this.setTitle("Criar Nova Reserva");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
    }
}
