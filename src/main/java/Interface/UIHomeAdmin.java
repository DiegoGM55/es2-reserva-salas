package Interface;

import Controlador.AdministradorController;
import Controlador.EspacoFisicoController;
import Controlador.UsuarioController;
import Controlador.ReservaController;
import Modelo.EspacoFisico;
import Modelo.Reserva;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class UIHomeAdmin extends javax.swing.JFrame {

    private AdministradorController admController;
    private UsuarioController userController;
    private EspacoFisicoController espacoController;
    private ReservaController reservaController;

    public UIHomeAdmin(AdministradorController admController, UsuarioController userController, EspacoFisicoController espacoController, ReservaController reservaController) {
        this.admController = admController;
        this.userController = userController;
        this.espacoController = espacoController;
        this.reservaController = reservaController;
        initComponents();
        carregarReservasPendentes(); // Carregar reservas pendentes ao iniciar
        carregarReservasAprovadas(); // Carregar reservas aprovadas ao iniciar
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTable2 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuUsuario = new javax.swing.JMenu(); // Novo menu Usuário
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItemUsuarioCriar = new javax.swing.JMenuItem(); // Novo item para criar usuário
        jMenuItemUsuarioEditar = new javax.swing.JMenuItem(); // Novo item para editar/remover usuário
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        JMenuItem jMenuItemLogout = new javax.swing.JMenuItem(); // Inicializando o item de menu

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Aguardando Aprovação");

        jButton1.setText("Filtrar");
        jButton1.addActionListener(evt -> carregarReservasPendentes()); // Atualiza a tabela ao clicar

        jLabel2.setText("Próximas Reservas Confirmadas");

        // Configuração da tabela de reservas pendentes
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Espaço", "Usuário", "Ação"}
        ));
        jScrollPane3.setViewportView(jTable1);

        // Configuração da tabela de reservas aprovadas
        jTable2.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Espaço", "Data e Hora de Início", "Usuário"}
        ));
        jScrollPane2.setViewportView(jTable2);

        jMenu1.setText("Espaço Físico");

        jMenuItem1.setText("Editar/Remover");
        jMenuItem1.addActionListener(evt -> abrirTelaEditarRemoverEspacoFisico());
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Adicionar");
        jMenuItem2.addActionListener(evt -> abrirTelaCriarEspacoFisico());
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        // Novo Menu "Usuário"
        jMenuUsuario.setText("Usuário");

        jMenuItemUsuarioCriar.setText("Criar");
        jMenuItemUsuarioCriar.addActionListener(evt -> abrirTelaCriarUsuario());
        jMenuUsuario.add(jMenuItemUsuarioCriar);

        jMenuItemUsuarioEditar.setText("Editar/Remover");
        jMenuItemUsuarioEditar.addActionListener(evt -> abrirTelaEditarRemoverUsuario());
        jMenuUsuario.add(jMenuItemUsuarioEditar);

        jMenuBar1.add(jMenuUsuario); // Adiciona o menu "Usuário" à barra de menus

        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        jMenuItemLogout.setText("Logout"); // Definindo o texto do item
        jMenuItemLogout.addActionListener(evt -> realizarLogout()); // Definindo a ação ao clicar no item
        jMenuBar1.add(jMenuItemLogout); // Adicionando o item de logout à barra de menus

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(28, 28, 28)
                                                .addComponent(jButton1))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jButton1)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2))
                                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }

    private void realizarLogout() {
        // Fecha a tela atual
        this.dispose();
        // Abre a tela de login (UIHome)
        new UIHome(admController, userController, espacoController, reservaController).setVisible(true);
    }

    private void carregarReservasPendentes() {
        ArrayList<Reserva> reservasPendentes = reservaController.getReservasPendentes(); // Obtém reservas pendentes
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); // Limpar a tabela

        for (Reserva reserva : reservasPendentes) {
            modelo.addRow(new Object[]{
                reserva.getId(),
                reserva.getEspacoFisico().getNome(),
                reserva.getUsuario().getNome(),
                "Abrir" // Representa o botão
            });
        }

        jTable1.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        jTable1.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JButton(), reservaController, this));
    }

    private void abrirTelaCriarEspacoFisico() {
        UICriarEspacoFisico novaTela = new UICriarEspacoFisico(espacoController);
        novaTela.setVisible(true);
    }

    private void abrirTelaEditarRemoverEspacoFisico() {
        // Simule a seleção do espaço físico por ID
        String idStr = JOptionPane.showInputDialog(
                this,
                "Digite o ID do Espaço Físico para Editar:",
                "Editar Espaço Físico",
                JOptionPane.QUESTION_MESSAGE
        );

        if (idStr == null || idStr.trim().isEmpty()) {
            return; // Cancelado ou vazio
        }

        try {
            int id = Integer.parseInt(idStr);

            // Obtém o espaço físico pelo ID
            EspacoFisico espacoFisico = espacoController.buscaEspacoFisicoById(id); // Supondo que o método exista
            if (espacoFisico == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Espaço Físico com ID " + id + " não encontrado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Abre a tela de edição e remoção
            UIEditarRemoverEspacoFisico editarTela = new UIEditarRemoverEspacoFisico(espacoController, espacoFisico, reservaController);
            editarTela.setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido. Por favor, insira um número inteiro.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void abrirTelaCriarUsuario() {
        UICriarUsuario novaTela = new UICriarUsuario(userController);
        novaTela.setVisible(true);
    }

    private void abrirTelaEditarRemoverUsuario() {
        String email = JOptionPane.showInputDialog(
                this,
                "Digite o e-mail do usuário para editar ou remover:",
                "Editar/Remover Usuário",
                JOptionPane.QUESTION_MESSAGE
        );

        if (email == null || email.trim().isEmpty()) {
            return; // Cancelado ou vazio
        }

        try {
            var usuario = userController.buscarUsuarioPorEmail(email);
            if (usuario == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuário com e-mail " + email + " não encontrado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            UIEditarRemoverUsuario editarTela = new UIEditarRemoverUsuario(userController, usuario);
            editarTela.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar usuário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void carregarReservasAprovadas() {
        ArrayList<Reserva> reservasAprovadas = reservaController.getReservasAprovadas(); // Supondo que o método existe
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.setRowCount(0); // Limpar a tabela

        for (Reserva reserva : reservasAprovadas) {
            modelo.addRow(new Object[]{
                reserva.getEspacoFisico().getNome(),
                reserva.getDataReservaInicio().toString(), // Converte a data e hora para String
                reserva.getUsuario().getNome()
            });
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            AdministradorController admController = new AdministradorController();
            UsuarioController userController = new UsuarioController();
            EspacoFisicoController espacoController = new EspacoFisicoController();
            ReservaController reservaController = new ReservaController();

            new UIHomeAdmin(admController, userController, espacoController, reservaController).setVisible(true);
        });
    }

    // Classes internas para o botão na tabela pendente
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "Abrir" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int reservaId;
        private ReservaController reservaController;
        private UIHomeAdmin parent;

        public ButtonEditor(JButton button, ReservaController reservaController, UIHomeAdmin parent) {
            this.button = button;
            this.reservaController = reservaController;
            this.parent = parent;
            this.button.addActionListener(evt -> {
                fireEditingStopped();
                abrirUIAprovaReserva();
            });
        }

        private void abrirUIAprovaReserva() {
            // Abrir a tela para aprovar reserva com base no ID
            Reserva reserva = reservaController.buscarReservaPorID(reservaId);
            if (reserva != null) {
                UIAprovaReserva novaTela = new UIAprovaReserva(reservaController, reservaId, () -> {
                    parent.carregarReservasPendentes();
                    parent.carregarReservasAprovadas();
                });
                novaTela.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(parent, "Reserva não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public Object getCellEditorValue() {
            return label; // Retorna o texto atual do botão
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            reservaId = (int) table.getValueAt(row, 0); // Assume que a coluna 0 contém o ID da reserva
            label = value != null ? value.toString() : "Abrir";
            button.setText(label); // Define o texto do botão
            clicked = true;
            return button; // Retorna o botão como editor
        }
    }

    // Variáveis da interface
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenuUsuario; // Novo menu "Usuário"
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemUsuarioCriar; // Item de menu para criar usuário
    private javax.swing.JMenuItem jMenuItemUsuarioEditar; // Item de menu para editar/remover usuário
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
}
