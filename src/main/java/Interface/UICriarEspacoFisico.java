package Interface;

import Controlador.EspacoFisicoController;

import javax.swing.*;
import java.awt.*;

public class UICriarEspacoFisico extends JFrame {

    private EspacoFisicoController espacoController;
    private Utilidades util;
    
    public UICriarEspacoFisico(EspacoFisicoController espacoController) {
        this.espacoController = espacoController;
        this.util = new Utilidades();
        initComponents();
    }

    private void initComponents() {
        // Componentes da tela
        JLabel labelTitulo = new JLabel("Criar Novo Espaço Físico");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelNome = new JLabel("Nome:");
        JTextField inputNome = new JTextField(20);

        JLabel labelEndereco = new JLabel("Endereço:");
        JTextField inputEndereco = new JTextField(20);

        JLabel labelCapacidade = new JLabel("Capacidade Máxima:");
        JTextField inputCapacidade = new JTextField(5);

        JLabel labelObservacao = new JLabel("Observação:");
        JTextField inputObservacao = new JTextField(20);

        JButton bttnSalvar = new JButton("Salvar");
        JButton bttnCancelar = new JButton("Cancelar");

        // Ações dos botões
        bttnSalvar.addActionListener(evt -> {
            String nome = inputNome.getText().trim();
            String endereco = inputEndereco.getText().trim();
            String capacidadeStr = inputCapacidade.getText().trim();
            String observacao = inputObservacao.getText().trim();

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

                // Adiciona o espaço físico
                espacoController.registraEspacoFisico(nome, endereco, capacidade, observacao);

                // Exibe mensagem de sucesso
                JOptionPane.showMessageDialog(
                        this,
                        "Espaço Físico criado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.dispose(); // Fecha a tela
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Capacidade máxima deve ser um número inteiro.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        bttnCancelar.addActionListener(evt -> this.dispose()); // Fecha a tela

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
        panel.add(labelObservacao, gbc);

        gbc.gridx = 1;
        panel.add(inputObservacao, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bttnSalvar, gbc);

        gbc.gridy++;
        panel.add(bttnCancelar, gbc);

        // Configurações da janela
        this.setTitle("Criar Espaço Físico");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
    }
}
