package Interface;

import Controlador.UsuarioController;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class UIEditarRemoverUsuario extends JFrame {

    private UsuarioController usuarioController;
    private Usuario usuario;
    private final Utilidades util;
    
    public UIEditarRemoverUsuario(UsuarioController usuarioController, Usuario usuario) {
        this.usuarioController = usuarioController;
        this.usuario = usuario;
        this.util = new Utilidades();
        initComponents();
    }

    private void initComponents() {
        // Componentes da tela
        JLabel labelTitulo = new JLabel("Editar ou Remover Usuário");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelNome = new JLabel("Nome:");
        JTextField inputNome = new JTextField(20);
        inputNome.setText(usuario.getNome());

        JLabel labelTelefone = new JLabel("Telefone:");
        JTextField inputTelefone = new JTextField(15);
        inputTelefone.setText(usuario.getTelefone());

        JLabel labelEmail = new JLabel("E-mail:");
        JTextField inputEmail = new JTextField(20);
        inputEmail.setText(usuario.getEmail());

        JLabel labelCpf = new JLabel("CPF:");
        JTextField inputCpf = new JTextField(15);
        inputCpf.setText(usuario.getCpf());

        JLabel labelRa = new JLabel("RA:");
        JTextField inputRa = new JTextField(10);
        inputRa.setText(usuario.getRa());

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField inputSenha = new JPasswordField(20);
        inputSenha.setText(usuario.getSenha());

        JButton bttnSalvar = new JButton("Salvar Alterações");
        JButton bttnExcluir = new JButton("Excluir Usuário");

        // Ação para salvar alterações
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
            
            try {
                // Atualiza os dados do usuário
                usuarioController.atualizarUsuario(usuario.getId(), nome, telefone, email, cpf, ra, senha);

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário atualizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.dispose(); // Fecha a janela
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao atualizar o usuário: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Ação para excluir o usuário
        bttnExcluir.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza de que deseja excluir este usuário?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                usuarioController.deletarUsuarioPorID(usuario.getId());
                JOptionPane.showMessageDialog(
                        this,
                        "Usuário excluído com sucesso!",
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
        panel.add(bttnExcluir, gbc);

        // Configurações da janela
        this.setTitle("Editar/Remover Usuário");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
    }
}
