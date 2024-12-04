package Interface;

import Controlador.ReservaController;
import Controlador.UsuarioController;
import Modelo.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UICalendarioReservas extends JFrame {
    private ReservaController reservaController;
    private UsuarioController usuarioController;
    private LocalDate dataAtual; // Mantém o mês exibido atualmente
    private JTable tabelaCalendario;
    private JLabel labelMesAno;

    public UICalendarioReservas(ReservaController reservaController, UsuarioController usuarioController) {
        this.reservaController = reservaController;
        this.usuarioController = usuarioController;
        this.dataAtual = LocalDate.now().withDayOfMonth(1); // Começa no mês atual
        initComponents();
    }

    private void initComponents() {
        // Título e cabeçalho
        labelMesAno = new JLabel(getTituloMesAno());
        labelMesAno.setFont(new Font("Arial", Font.BOLD, 18));
        labelMesAno.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnAnterior = new JButton("◀ Mês Anterior");
        JButton btnProximo = new JButton("Próximo Mês ▶");

        btnAnterior.addActionListener(evt -> alterarMes(-1)); // Retrocede um mês
        btnProximo.addActionListener(evt -> alterarMes(1));  // Avança um mês

        JPanel header = new JPanel(new BorderLayout());
        header.add(btnAnterior, BorderLayout.WEST);
        header.add(labelMesAno, BorderLayout.CENTER);
        header.add(btnProximo, BorderLayout.EAST);

        // Tabela de calendário
        tabelaCalendario = new JTable();
        tabelaCalendario.setRowHeight(80); // Aumenta o tamanho das células
        tabelaCalendario.setFont(new Font("Arial", Font.PLAIN, 14)); // Aumenta a fonte
        JScrollPane scrollPane = new JScrollPane(tabelaCalendario);

        tabelaCalendario.setModel(new DefaultTableModel(
                new Object[6][7], // 6 semanas e 7 dias da semana
                new String[]{"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"}
        ));

        carregarCalendario(); // Carrega o mês atual

        JButton bttnFechar = new JButton("Fechar");
        bttnFechar.addActionListener(evt -> this.dispose());

        JPanel footer = new JPanel();
        footer.add(bttnFechar);

        // Layout principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        this.setTitle("Calendário de Reservas");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(panel);
        this.setSize(1000, 600); // Aumenta o tamanho da janela
        this.setLocationRelativeTo(null);
    }

    private void carregarCalendario() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaCalendario.getModel();
        modelo.setRowCount(6); // Máximo de semanas em um mês
        modelo.setColumnCount(7); // Dias da semana

        LocalDate primeiroDiaMes = dataAtual;
        int primeiroDiaSemana = primeiroDiaMes.getDayOfWeek().getValue() % 7; // Ajusta para começar no domingo
        int diasNoMes = primeiroDiaMes.lengthOfMonth();

        // Lista de reservas do usuário
        int usuarioId = usuarioController.getUsuarioLogado().getId();
        ArrayList<Reserva> reservas = reservaController.getReservasPendentesPorUsuario(usuarioId);

        // Preenche os dias do mês
        int diaAtual = 1;
        boolean preenchendo = false;
        for (int linha = 0; linha < 6; linha++) {
            for (int coluna = 0; coluna < 7; coluna++) {
                if (linha == 0 && coluna == primeiroDiaSemana) {
                    preenchendo = true;
                }
                if (preenchendo && diaAtual <= diasNoMes) {
                    String conteudo = "<html><b>" + diaAtual + "</b>";

                    // Adiciona reservas ao dia
                    LocalDate dataDia = primeiroDiaMes.plusDays(diaAtual - 1);
                    List<String> detalhesReservas = new ArrayList<>();
                    for (Reserva reserva : reservas) {
                        if (reserva.getDataReservaInicio().toLocalDate().isEqual(dataDia)) {
                            detalhesReservas.add(
                                reserva.getEspacoFisico().getNome() + " - " +
                                reserva.getHorarioInicio().format(DateTimeFormatter.ofPattern("HH:mm"))
                            );
                        }
                    }
                    if (!detalhesReservas.isEmpty()) {
                        conteudo += "<br>" + String.join("<br>", detalhesReservas);
                    }

                    conteudo += "</html>";
                    modelo.setValueAt(conteudo, linha, coluna);
                    diaAtual++;
                } else {
                    modelo.setValueAt("", linha, coluna); // Limpa células vazias
                }
            }
        }

        labelMesAno.setText(getTituloMesAno()); // Atualiza o título
    }

    private void alterarMes(int deslocamento) {
        dataAtual = dataAtual.plusMonths(deslocamento); // Altera o mês
        carregarCalendario(); // Recarrega o calendário com o novo mês
    }

    private String getTituloMesAno() {
        return dataAtual.format(DateTimeFormatter.ofPattern("MMMM yyyy")); // Exemplo: "Novembro 2024"
    }
}
