package com.mycompany.bingogame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

public class JanelaPrincipal extends javax.swing.JFrame {

    private final BingoGame controle = new BingoGame();
    private final int totalDitados;

    private JLabel[] labelsDitados;
    private JLabel[] labelsOrdem;

    private DefaultTableModel modeloAcompanhamento;

    private static final Color COR_FUNDO = new Color(244, 247, 250);
    private static final Color COR_FAIXA = new Color(21, 66, 115);
    private static final Color COR_NAO_SORTEADO = Color.WHITE;
    private static final Color COR_JA_SORTEADO = new Color(198, 240, 205);
    private static final Color COR_ULTIMO_SORTEADO = new Color(255, 196, 0);

    public JanelaPrincipal() {
        this.totalDitados = controle.getTotalDitados();
        initComponents();
        configurarCartelaoVisual();
        configurarAtalhosDeTeclado();

        lblNumeroSorteado.setText("--");
        lblDitadoCompleto.setText("Pressione \u201cNovo Jogo\u201d para começar a partida.");
        atualizarStatus();

        this.setTitle("Bingo dos Ditados Populares");
        this.setMinimumSize(new Dimension(1300, 780));
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
    }

    // ------------------------------------------------------------------
    // Montagem do cartelão (tela grande com as primeiras metades dos ditados)
    // ------------------------------------------------------------------

    private void configurarCartelaoVisual() {
        labelsDitados = new JLabel[totalDitados];
        labelsOrdem = new JLabel[totalDitados];

        pnlCartelao.setLayout(new GridLayout(3, 10, 6, 6));
        pnlCartelao.setBackground(COR_FUNDO);

        for (int i = 0; i < totalDitados; i++) {
            JPanel celula = new JPanel(new BorderLayout());
            celula.setBackground(COR_NAO_SORTEADO);
            celula.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));

            JLabel ordem = new JLabel(" ", SwingConstants.RIGHT);
            ordem.setFont(new Font("Arial", Font.BOLD, 15));
            ordem.setForeground(new Color(120, 120, 120));
            ordem.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 4));

            JLabel texto = new JLabel("", SwingConstants.CENTER);
            texto.setFont(new Font("Arial", Font.BOLD, 27));
            texto.setVerticalAlignment(SwingConstants.CENTER);

            celula.add(ordem, BorderLayout.NORTH);
            celula.add(texto, BorderLayout.CENTER);

            pnlCartelao.add(celula);
            labelsDitados[i] = texto;
            labelsOrdem[i] = ordem;
        }
    }

    private void atualizarCartelaoVisual() {
        Set<Integer> sorteados = controle.getNumerosSorteados();
        Integer ultimo = controle.getUltimoSorteado();

        for (int i = 0; i < totalDitados; i++) {
            JLabel texto = labelsDitados[i];
            JLabel ordem = labelsOrdem[i];
            JPanel celula = (JPanel) texto.getParent();

            if (sorteados.contains(i)) {
                texto.setText("<html><center>" + controle.getDitadoPrimeiraParte(i) + "</center></html>");
                ordem.setText(String.valueOf(controle.getOrdemDoSorteio(i)) + "º");

                if (ultimo != null && i == ultimo) {
                    celula.setBackground(COR_ULTIMO_SORTEADO);
                    celula.setBorder(BorderFactory.createLineBorder(new Color(200, 90, 0), 3));
                    texto.setForeground(Color.BLACK);
                } else {
                    celula.setBackground(COR_JA_SORTEADO);
                    celula.setBorder(BorderFactory.createLineBorder(new Color(120, 180, 130)));
                    texto.setForeground(Color.BLACK);
                }
            } else {
                texto.setText("");
                ordem.setText(" ");
                celula.setBackground(COR_NAO_SORTEADO);
                celula.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            }
        }

        atualizarAcompanhamento();
        atualizarStatus();

        // Pequeno delay para a interface gráfica respirar antes do popup
        
        SwingUtilities.invokeLater(() -> {
            List<String> vencedores = controle.verificarVencedores();
            if (!vencedores.isEmpty()) {
                btnSortear.setEnabled(false);
                SomUtil.tocarBingo();
                String msg = (vencedores.size() > 1) ? "TEMOS UM EMPATE! GANHADORES:" : "O GANHADOR É:";
                mostrarDialogoVitoria(msg, vencedores);
            }
        });
    }

    private void mostrarDialogoVitoria(String titulo, List<String> vencedores) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblEmoji = new JLabel(" BINGO! ", SwingConstants.CENTER);
        lblEmoji.setFont(new Font("Arial", Font.BOLD, 42));
        lblEmoji.setForeground(new Color(200, 90, 0));
        lblEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        painel.add(lblEmoji);
        painel.add(lblTitulo);
        for (String nome : vencedores) {
            JLabel lblNome = new JLabel(nome, SwingConstants.CENTER);
            lblNome.setFont(new Font("Arial", Font.BOLD, 30));
            lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(lblNome);
        }

        JOptionPane.showMessageDialog(this, painel, "FIM DE JOGO", JOptionPane.PLAIN_MESSAGE);
    }

    // ------------------------------------------------------------------
    // Painel de acompanhamento dos jogadores (quem está mais perto de ganhar)
    // ------------------------------------------------------------------

    private void atualizarAcompanhamento() {
        modeloAcompanhamento.setRowCount(0);
        List<Cartela> cartelas = controle.getCartelas();

        List<Cartela> ordenadas = new java.util.ArrayList<>(cartelas);
        ordenadas.sort((a, b) -> b.getTotalMarcados() - a.getTotalMarcados());

        for (Cartela c : ordenadas) {
            modeloAcompanhamento.addRow(new Object[]{
                "Nº " + c.getNumeroCartela(),
                c.getNomeJogador(),
                new ProgressoCelula(c.getTotalMarcados(), c.getTotalNumeros())
            });
        }
    }

    private void limparAcompanhamento() {
        modeloAcompanhamento.setRowCount(0);
    }

    private void atualizarStatus() {
        lblStatus.setText("Sorteados: " + controle.getTotalSorteados() + " / " + totalDitados
                + "      Restam: " + controle.getRestantes());
    }

    /** Pequeno "carregador" de dados para a coluna de progresso da tabela. */
    private static class ProgressoCelula {
        final int marcados;
        final int total;

        ProgressoCelula(int marcados, int total) {
            this.marcados = marcados;
            this.total = total;
        }
    }

    /** Renderiza a coluna de progresso como uma barrinha colorida, com cor que muda perto do fim. */
    private static class ProgressoRenderer extends JProgressBar implements TableCellRenderer {
        ProgressoRenderer() {
            super(0, 1);
            setStringPainted(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (value instanceof ProgressoCelula p) {
                setMaximum(Math.max(1, p.total));
                setValue(p.marcados);
                setString(p.marcados + " / " + p.total);
                if (p.marcados >= p.total) {
                    setForeground(new Color(0, 150, 0));
                } else if (p.marcados >= p.total - 1) {
                    setForeground(new Color(230, 140, 0));
                } else {
                    setForeground(new Color(70, 130, 180));
                }
            }
            return this;
        }
    }

    // ------------------------------------------------------------------
    // Ações dos botões
    // ------------------------------------------------------------------

    private void btnSortearActionPerformed(java.awt.event.ActionEvent evt) {
        Integer numeroSorteado = controle.sortearProximo();

        if (numeroSorteado != null) {
            SomUtil.tocarSorteio();
            lblNumeroSorteado.setText(String.valueOf(controle.getTotalSorteados()));
            lblDitadoCompleto.setText(controle.getDitadoPrimeiraParte(numeroSorteado));
            atualizarCartelaoVisual();
        } else {
            lblNumeroSorteado.setText("FIM");
            lblDitadoCompleto.setText("Todos os ditados já foram sorteados.");
            btnSortear.setEnabled(false);
        }
    }

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {
        Integer numJogadores = perguntarNumeroDeJogadores();
        if (numJogadores == null) {
            return;
        }

        ConfiguracaoJogoDialog dialogo = new ConfiguracaoJogoDialog(this, numJogadores);
        dialogo.setVisible(true);
        if (!dialogo.isConfirmado()) {
            return;
        }

        controle.iniciarNovoJogo(numJogadores);
        List<String> nomes = dialogo.getNomes();
        for (int i = 0; i < nomes.size(); i++) {
            controle.setPlayerName(i, nomes.get(i));
        }

        lblNumeroSorteado.setText("0");
        lblDitadoCompleto.setText("Jogo iniciado! Boa sorte a todos.");
        btnSortear.setEnabled(true);
        limparAcompanhamento();
        atualizarCartelaoVisual();
    }

    private Integer perguntarNumeroDeJogadores() {
        SpinnerNumberModel modelo = new SpinnerNumberModel(1, 1, controle.getMaxCartelas(), 1);
        JSpinner spinner = new JSpinner(modelo);
        spinner.setFont(new Font("Arial", Font.BOLD, 22));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFont(new Font("Arial", Font.BOLD, 22));

        JLabel lbl = new JLabel("Quantidade de jogadores (1 a " + controle.getMaxCartelas() + "):");
        lbl.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        painel.add(lbl, BorderLayout.NORTH);
        painel.add(spinner, BorderLayout.CENTER);

        int opcao = JOptionPane.showConfirmDialog(this, painel, "Novo Jogo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcao != JOptionPane.OK_OPTION) {
            return null;
        }
        return (Integer) spinner.getValue();
    }

    private void btnTelaCheiaActionPerformed(java.awt.event.ActionEvent evt) {
        alternarTelaCheia();
    }

    private void alternarTelaCheia() {
        if (getExtendedState() == javax.swing.JFrame.MAXIMIZED_BOTH) {
            setExtendedState(javax.swing.JFrame.NORMAL);
        } else {
            setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        }
    }

    private void configurarAtalhosDeTeclado() {
        // Barra de espaço sorteia o próximo ditado - mais fácil de usar ao vivo do que mirar no botão.
        getRootPane().registerKeyboardAction(
                e -> { if (btnSortear.isEnabled()) btnSortear.doClick(); },
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        // F11 alterna o modo tela cheia, para exibir num telão/TV durante o evento.
        getRootPane().registerKeyboardAction(
                e -> alternarTelaCheia(),
                KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    // <editor-fold defaultstate="collapsed" desc="Construção da interface">
    private void initComponents() {
        getContentPane().setBackground(COR_FUNDO);

        // ---- Faixa de título ----
        JPanel pnlTitulo = new JPanel(new BorderLayout());
        pnlTitulo.setBackground(COR_FAIXA);
        pnlTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel("BINGO DOS DITADOS POPULARES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        pnlTitulo.add(lblTitulo, BorderLayout.CENTER);

        // ---- Painel de controles (sorteio atual + botões) ----
        pnlControles = new JPanel();
        pnlControles.setBackground(COR_FUNDO);
        pnlControles.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        pnlControles.setLayout(new BorderLayout(15, 0));

        lblNumeroSorteado = new JLabel("00", SwingConstants.CENTER);
        lblNumeroSorteado.setFont(new Font("Arial", Font.BOLD, 62));
        lblNumeroSorteado.setForeground(COR_FAIXA);
        lblNumeroSorteado.setPreferredSize(new Dimension(130, 95));
        lblNumeroSorteado.setToolTipText("Número da rodada de sorteio");

        lblDitadoCompleto = new JLabel("", SwingConstants.CENTER);
        lblDitadoCompleto.setFont(new Font("Arial", Font.BOLD, 30));
        lblDitadoCompleto.setForeground(new Color(40, 40, 40));

        JPanel pnlBotoes = new JPanel(new GridLayout(3, 1, 0, 6));
        pnlBotoes.setBackground(COR_FUNDO);

        btnSortear = new JButton("SORTEAR (espaço)");
        estilizarBotao(btnSortear, new Color(40, 130, 80));
        btnSortear.addActionListener(this::btnSortearActionPerformed);
        btnSortear.setEnabled(false);

        btnNovoJogo = new JButton("NOVO JOGO");
        estilizarBotao(btnNovoJogo, COR_FAIXA);
        btnNovoJogo.addActionListener(this::btnNovoJogoActionPerformed);

        JButton btnTelaCheia = new JButton("TELA CHEIA (F11)");
        estilizarBotao(btnTelaCheia, new Color(100, 100, 100));
        btnTelaCheia.addActionListener(this::btnTelaCheiaActionPerformed);

        pnlBotoes.add(btnSortear);
        pnlBotoes.add(btnNovoJogo);
        pnlBotoes.add(btnTelaCheia);

        pnlControles.add(lblNumeroSorteado, BorderLayout.WEST);
        pnlControles.add(lblDitadoCompleto, BorderLayout.CENTER);
        pnlControles.add(pnlBotoes, BorderLayout.EAST);

        // ---- Cartelão (tela grande) ----
        pnlCartelao = new JPanel();
        pnlCartelao.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 8));
        pnlCartelao.setBackground(COR_FUNDO);

        // ---- Painel de acompanhamento dos jogadores ----
        JPanel pnlAcompanhamento = new JPanel(new BorderLayout(0, 5));
        pnlAcompanhamento.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 15));
        pnlAcompanhamento.setBackground(COR_FUNDO);
        pnlAcompanhamento.setPreferredSize(new Dimension(300, 0));

        JLabel lblAcompanhamento = new JLabel("Acompanhamento dos Jogadores");
        lblAcompanhamento.setFont(new Font("Arial", Font.BOLD, 18));

        modeloAcompanhamento = new DefaultTableModel(new Object[]{"Cartela", "Jogador", "Progresso"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabela = new JTable(modeloAcompanhamento);
        tabela.setFont(new Font("Arial", Font.BOLD, 16));
        tabela.setRowHeight(32);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabela.getColumnModel().getColumn(2).setCellRenderer(new ProgressoRenderer());
        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);

        JScrollPane scrollTabela = new JScrollPane(tabela);

        pnlAcompanhamento.add(lblAcompanhamento, BorderLayout.NORTH);
        pnlAcompanhamento.add(scrollTabela, BorderLayout.CENTER);

        // ---- Barra de status ----
        lblStatus = new JLabel("Sorteados: 0 / " + totalDitados);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 17));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(4, 15, 8, 15));
        lblStatus.setForeground(new Color(80, 80, 80));

        // ---- Montagem final ----
        JPanel pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.setBackground(COR_FUNDO);
        pnlCentro.add(pnlControles, BorderLayout.NORTH);
        JScrollPane scrollCartelao = new JScrollPane(pnlCartelao);
        scrollCartelao.setBorder(BorderFactory.createEmptyBorder());
        pnlCentro.add(scrollCartelao, BorderLayout.CENTER);
        pnlCentro.add(pnlAcompanhamento, BorderLayout.EAST);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlTitulo, BorderLayout.NORTH);
        getContentPane().add(pnlCentro, BorderLayout.CENTER);
        getContentPane().add(lblStatus, BorderLayout.SOUTH);
    }

    private void estilizarBotao(JButton botao, Color cor) {
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
    }
    // </editor-fold>

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }

    private JButton btnNovoJogo;
    private JButton btnSortear;
    private JLabel lblDitadoCompleto;
    private JLabel lblNumeroSorteado;
    private JLabel lblStatus;
    private JPanel pnlCartelao;
    private JPanel pnlControles;
}
