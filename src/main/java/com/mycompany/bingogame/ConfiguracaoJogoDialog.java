package com.mycompany.bingogame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Janela para configurar uma nova partida. Em vez de várias caixinhas de
 * diálogo separadas (uma por jogador), mostra tudo em uma única tela: o
 * número físico de cada cartela (que bate com o "Nº" impresso no PDF) ao
 * lado de um campo para digitar o nome de quem vai recebê-la. Isso evita
 * confusão na hora de entregar as cartelas de papel para os participantes.
 */
public class ConfiguracaoJogoDialog extends JDialog {

    private boolean confirmado = false;
    private JTextField[] camposNomes;

    public ConfiguracaoJogoDialog(Frame owner, int numJogadores) {
        super(owner, "Configurar Jogadores", true);
        montarInterface(numJogadores);
    }

    private void montarInterface(int numJogadores) {
        camposNomes = new JTextField[numJogadores];

        JPanel painelCampos = new JPanel(new GridLayout(numJogadores, 1, 0, 8));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (int i = 0; i < numJogadores; i++) {
            JPanel linha = new JPanel(new BorderLayout(10, 0));

            JLabel lbl = new JLabel("Cartela Nº " + (i + 1) + ":");
            lbl.setFont(new Font("Arial", Font.BOLD, 20));
            lbl.setPreferredSize(new Dimension(160, 38));

            JTextField campo = new JTextField();
            campo.setFont(new Font("Arial", Font.BOLD, 20));
            camposNomes[i] = campo;

            linha.add(lbl, BorderLayout.WEST);
            linha.add(campo, BorderLayout.CENTER);
            painelCampos.add(linha);
        }

        JLabel instrucao = new JLabel(
                "<html><center>Entregue as cartelas impressas seguindo a numeração abaixo<br>"
                + "e escreva o nome de cada participante (pode deixar em branco).</center></html>",
                SwingConstants.CENTER);
        instrucao.setFont(new Font("Arial", Font.BOLD, 17));
        instrucao.setBorder(BorderFactory.createEmptyBorder(12, 10, 5, 10));

        JScrollPane scroll = new JScrollPane(painelCampos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        int alturaVisivel = Math.min(560, 70 * numJogadores + 20);
        scroll.setPreferredSize(new Dimension(480, alturaVisivel));

        JButton btnConfirmar = new JButton("COMEÇAR JOGO");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 20));
        btnConfirmar.setBackground(new Color(46, 139, 87));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.addActionListener(e -> {
            confirmado = true;
            setVisible(false);
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCancelar.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        painelBotoes.add(btnConfirmar);
        painelBotoes.add(btnCancelar);

        setLayout(new BorderLayout());
        add(instrucao, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        getRootPane().registerKeyboardAction(e -> btnConfirmar.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        getRootPane().registerKeyboardAction(e -> btnCancelar.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        if (numJogadores > 0) {
            SwingUtilities.invokeLater(() -> camposNomes[0].requestFocusInWindow());
        }

        pack();
        setMinimumSize(new Dimension(480, 220));
        setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public List<String> getNomes() {
        List<String> nomes = new ArrayList<>();
        for (int i = 0; i < camposNomes.length; i++) {
            String texto = camposNomes[i].getText().trim();
            nomes.add(texto.isEmpty() ? ("Cartela " + (i + 1)) : texto);
        }
        return nomes;
    }
}
