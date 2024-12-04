package Interface;

import Controlador.UsuarioController;

import javax.swing.*;
import java.awt.*;

public class UICriarUsuario extends JFrame {

    private UsuarioController usuarioController;
    private final Utilidades util;
    
    public UICriarUsuario(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        this.util = new Utilidades();
        initComponents();
    }

    private void initComponents() {
        // Componentes da tela
        JLabel labelTitulo = new JLabel("Criar Novo Usuário");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelNome = new JLabel("Nome:");
        JTextField inputNome = new JTextField(20);

        JLabel labelTelefone = new JLabel("Telefone:");
        JTextField inputTelefone = new JTextField(15);

        JLabel labelEmail = new JLabel("E-mail:");
        JTextField inputEmail = new JTextField(20);

        JLabel labelCpf = new JLabel("CPF:");
        JTextField inputCpf = new JTextField(15);

        JLabel labelRa = new JLabel("RA:");
        JTextField inputRa = new JTextField(10);

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField inputSenha = new JPasswordField(20);

        JButton bttnSalvar = new JButton("Salvar");
        JButton bttnCancelar = new JButton("Cancelar");

        // Ações dos botões
        bttnSalvar.addActionListener(evt -> {
            String nome = inputNome.getText().trim();
            String telefone = inputTelefone.getText().trim();
            String email = inputEmail.getText().trim();
            String cpf = inputCpf.getText().trim();
            String ra = inputRa.getText().trim();
            String senha = new String(inputSenha.getPassword()).trim();

            // Validação de entrada
            if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || cpf.isEmpty() || ra.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Todos os campos devem ser preenchidos.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            if (!util.validarEmail(email)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Precisa ser um e-mail válido",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            if (!util.validarCPF(cpf)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Precisa ser um CPF válido",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            // Salvar usuário
            try {
                usuarioController.registrarUsuario(nome, telefone, email, ra, cpf, senha);

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário criado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.dispose(); // Fecha a tela
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao salvar o usuário: " + e.getMessage(),
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
        panel.add(labelTelefone, gbc);

        gbc.gridx = 1;
        panel.add(inputTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelEmail, gbc);

        gbc.gridx = 1;
        panel.add(inputEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelCpf, gbc);

        gbc.gridx = 1;
        panel.add(inputCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelRa, gbc);

        gbc.gridx = 1;
        panel.add(inputRa, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(labelSenha, gbc);

        gbc.gridx = 1;
        panel.add(inputSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bttnSalvar, gbc);

        gbc.gridy++;
        panel.add(bttnCancelar, gbc);

        // Configurações da janela
        this.setTitle("Criar Usuário");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
    }
}
