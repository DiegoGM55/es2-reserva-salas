package Interface;

import Controlador.EspacoFisicoController;
import Controlador.ReservaController;
import Modelo.EspacoFisico;
import Modelo.Reserva;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIEditarRemoverEspacoFisico extends JFrame {

    private EspacoFisicoController espacoController;
    private EspacoFisico espacoFisico;
    private ReservaController reservaController;
    private Utilidades util;
    
    public UIEditarRemoverEspacoFisico(EspacoFisicoController espacoController, EspacoFisico espacoFisico, ReservaController reservaController) {
        this.espacoController = espacoController;
        this.espacoFisico = espacoFisico;
        this.reservaController = reservaController;
        this.util = new Utilidades();
        initComponents();
    }

    private void initComponents() {
        // Componentes da tela
        JLabel labelTitulo = new JLabel("Editar ou Remover Espaço Físico");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelNome = new JLabel("Nome:");
        JTextField inputNome = new JTextField(20);
        inputNome.setText(espacoFisico.getNome());

        JLabel labelEndereco = new JLabel("Endereço:");
        JTextField inputEndereco = new JTextField(20);
        inputEndereco.setText(espacoFisico.getEndereco());

        JLabel labelCapacidade = new JLabel("Capacidade Máxima:");
        JTextField inputCapacidade = new JTextField(5);
        inputCapacidade.setText(String.valueOf(espacoFisico.getCapacidadeMaxima()));

        JButton bttnSalvar = new JButton("Salvar Alterações");
        JButton bttnExcluir = new JButton("Excluir Espaço");

        // Ação para salvar alterações
        bttnSalvar.addActionListener(evt -> {
            String nome = inputNome.getText().trim();
            String endereco = inputEndereco.getText().trim();
            String capacidadeStr = inputCapacidade.getText().trim();

            // Validação de entrada
            if (nome.isEmpty() || endereco.isEmpty() || capacidadeStr.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Todos os campos devem ser preenchidos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            if (!util.validarInt(capacidadeStr)){
                JOptionPane.showMessageDialog(
                        this,
                        "Capacidade tem que ser um número inteiro",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            try {
                int capacidade = Integer.parseInt(capacidadeStr);

                // Atualiza os dados do espaço físico
                espacoController.atualizaEspacoFisico(espacoFisico.getId(), nome, endereco, capacidade, espacoFisico.getObservacao());

                JOptionPane.showMessageDialog(
                        this,
                        "Espaço Físico atualizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.dispose(); // Fecha a janela
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Capacidade máxima deve ser um número inteiro.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Ação para excluir o espaço físico
        bttnExcluir.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza de que deseja excluir este espaço físico?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                espacoController.excluiEspacoFisico(espacoFisico.getId());
                ArrayList<Reserva> reservasPorEspaco = reservaController.buscarReservasPorEspaco(espacoFisico);
                for (Reserva reserva : reservasPorEspaco) {
                    reservaController.deleteReserva(reserva.getId());
                }
                
                JOptionPane.showMessageDialog(
                        this,
                        "Espaço Físico excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.dispose(); // Fecha a janela
            }
        });

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Adicionando os componentes ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(labelTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(labelNome, gbc);

        gbc.gridx = 1;
        panel.add(inputNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelEndereco, gbc);

        gbc.gridx = 1;
        panel.add(inputEndereco, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelCapacidade, gbc);

        gbc.gridx = 1;
        panel.add(inputCapacidade, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bttnSalvar, gbc);

        gbc.gridy++;
        panel.add(bttnExcluir, gbc);

        // Configurações da janela
        this.setTitle("Editar/Remover Espaço Físico");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
    }
}
