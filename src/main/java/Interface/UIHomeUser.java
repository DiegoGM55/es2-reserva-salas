package Interface;

import Controlador.AdministradorController;
import Controlador.EspacoFisicoController;
import Controlador.ReservaController;
import Controlador.UsuarioController;
import Modelo.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class UIHomeUser extends javax.swing.JFrame {

    private AdministradorController admController;
    private UsuarioController userController;
    private EspacoFisicoController espacoController;
    private ReservaController reservaController;

    public UIHomeUser(AdministradorController admController, UsuarioController userController, EspacoFisicoController espacoController, ReservaController reservaController) {
        this.admController = admController;
        this.userController = userController;
        this.espacoController = espacoController;
        this.reservaController = reservaController;
        initComponents();
        carregarReservasPendentes(); // Carregar reservas pendentes
        carregarReservasAprovadas(); // Carregar reservas aprovadas
        carregarReservasRecusadas(); // Carregar reservas recusadas
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jButtonNovaReserva = new javax.swing.JButton();
        jLabelPendentes = new javax.swing.JLabel();
        jLabelAprovadas = new javax.swing.JLabel();
        jLabelRecusadas = new javax.swing.JLabel();
        jScrollPanePendentes = new javax.swing.JScrollPane();
        jScrollPaneAprovadas = new javax.swing.JScrollPane();
        jScrollPaneRecusadas = new javax.swing.JScrollPane();
        jTablePendentes = new javax.swing.JTable();
        jTableAprovadas = new javax.swing.JTable();
        jTableRecusadas = new javax.swing.JTable();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenu();
        jMenuItemLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu.setText("Menu");
        jMenuItemLogout.setText("Logout");
        jMenuItemLogout.addActionListener(evt -> realizarLogout());
        jMenu.add(jMenuItemLogout);
        jMenuBar.add(jMenu);
        setJMenuBar(jMenuBar);

        jButtonNovaReserva.setText("Criar Nova Reserva");
        jButtonNovaReserva.addActionListener(evt -> abrirTelaCriarReserva());

        jLabelPendentes.setText("Reservas Aguardando Aprovação");
        jLabelAprovadas.setText("Reservas Aprovadas");
        jLabelRecusadas.setText("Reservas Recusadas");

        // Configuração da tabela de reservas pendentes
        jTablePendentes.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nome do Espaço", "Data e Hora de Início", "Ação"}
        ));
        jScrollPanePendentes.setViewportView(jTablePendentes);

        // Configuração da tabela de reservas aprovadas
        jTableAprovadas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nome do Espaço", "Data e Hora de Início", "Ação"}
        ));
        jScrollPaneAprovadas.setViewportView(jTableAprovadas);

        // Configuração da tabela de reservas recusadas
        jTableRecusadas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nome do Espaço", "Data e Hora de Início"}
        ));
        jScrollPaneRecusadas.setViewportView(jTableRecusadas);

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabelPendentes)
                                        .addComponent(jScrollPanePendentes, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelAprovadas)
                                        .addComponent(jScrollPaneAprovadas, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelRecusadas)
                                        .addComponent(jScrollPaneRecusadas, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonNovaReserva))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonNovaReserva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelPendentes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPanePendentes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelAprovadas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPaneAprovadas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelRecusadas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPaneRecusadas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }

    private void carregarReservasPendentes() {
        int usuarioId = userController.getUsuarioLogado().getId(); 
        ArrayList<Reserva> reservasPendentes = reservaController.getReservasPendentesPorUsuario(usuarioId);
        DefaultTableModel modelo = (DefaultTableModel) jTablePendentes.getModel();
        modelo.setRowCount(0);

        for (Reserva reserva : reservasPendentes) {
            modelo.addRow(new Object[]{
                reserva.getEspacoFisico().getNome(),
                reserva.getDataReservaInicio().toString(),
                "Editar/Remover"
            });
        }

        jTablePendentes.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        jTablePendentes.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JButton(), reservaController, this));
    }

    private void realizarLogout() {
        // Fecha a tela atual
        this.dispose();
        // Abre a tela de login (UIHome)
        new UIHome(admController,userController,espacoController,reservaController).setVisible(true);
    }

    private void carregarReservasAprovadas() {
        int usuarioId = userController.getUsuarioLogado().getId();        ArrayList<Reserva> reservasAprovadas = reservaController.getReservasAprovadasPorUsuario(usuarioId);
        DefaultTableModel modelo = (DefaultTableModel) jTableAprovadas.getModel();
        modelo.setRowCount(0);

        for (Reserva reserva : reservasAprovadas) {
            modelo.addRow(new Object[]{
                reserva.getEspacoFisico().getNome(),
                reserva.getDataReservaInicio().toString(),
                "Editar/Remover"
            });
        }

        jTableAprovadas.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        jTableAprovadas.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JButton(), reservaController, this));
    }

    private void carregarReservasRecusadas() {
        int usuarioId = userController.getUsuarioLogado().getId();        
        ArrayList<Reserva> reservasRecusadas = reservaController.getReservasRecusadasPorUsuario(usuarioId);
        DefaultTableModel modelo = (DefaultTableModel) jTableRecusadas.getModel();
        modelo.setRowCount(0);

        for (Reserva reserva : reservasRecusadas) {
            modelo.addRow(new Object[]{
                reserva.getEspacoFisico().getNome(),
                reserva.getDataReservaInicio().toString()
            });
        }
    }

    private void abrirTelaCriarReserva() {
        UICriarReserva novaTela = new UICriarReserva(reservaController, espacoController, userController, this::carregarReservasPendentes);
        novaTela.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            // Instâncias dos controladores
            AdministradorController admController = new AdministradorController();
            UsuarioController userController = new UsuarioController();
            EspacoFisicoController espacoController = new EspacoFisicoController();
            ReservaController reservaController = new ReservaController();

            // Inicializando a interface com os controladores
            new UIHomeUser(admController, userController, espacoController, reservaController).setVisible(true);
        });
    }

    // Classe interna para o botão "Editar/Remover"
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "Editar/Remover" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int reservaId;
        private ReservaController reservaController;
        private UIHomeUser parent;

        public ButtonEditor(JButton button, ReservaController reservaController, UIHomeUser parent) {
            this.button = button;
            this.reservaController = reservaController;
            this.parent = parent;
            this.button.addActionListener(evt -> {
                fireEditingStopped();
                abrirEditarRemoverReserva();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = value == null ? "Editar/Remover" : value.toString();
            reservaId = row; // Usando o índice da linha como ID da reserva
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        private void abrirEditarRemoverReserva() {
            Reserva reserva = reservaController.buscarReservaPorID(reservaId);
            if (reserva != null) {
                UIEditarRemoverReserva editarRemoverTela = new UIEditarRemoverReserva(reservaController, reserva, () -> {
                    carregarReservasPendentes();
                    carregarReservasAprovadas();
                    carregarReservasRecusadas();
                });
                editarRemoverTela.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(parent, "Reserva não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Variáveis da interface
    private javax.swing.JButton jButtonNovaReserva;
    private javax.swing.JLabel jLabelPendentes;
    private javax.swing.JLabel jLabelAprovadas;
    private javax.swing.JLabel jLabelRecusadas;
    private javax.swing.JScrollPane jScrollPanePendentes;
    private javax.swing.JScrollPane jScrollPaneAprovadas;
    private javax.swing.JScrollPane jScrollPaneRecusadas;
    private javax.swing.JTable jTablePendentes;
    private javax.swing.JTable jTableAprovadas;
    private javax.swing.JTable jTableRecusadas;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenuItem jMenuItemLogout;

}
