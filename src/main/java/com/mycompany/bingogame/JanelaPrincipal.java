/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.bingogame; 

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class JanelaPrincipal extends javax.swing.JFrame {

    // --- Variáveis de Controle ---
    private final BingoGame controle = new BingoGame(); 
    private JLabel[] labelsNumeros; 
    private final int TOTAL_DITADOS = 30;

    public JanelaPrincipal() {
        initComponents(); 
        configurarCartelaoVisual(); 
        
        lblNumeroSorteado.setText("Ag...");
        lblDitadoCompleto.setText("Pressione 'Novo Jogo' para configurar e iniciar.");
        this.setTitle("Bingo Caller - Painel de Controle");
        this.setLocationRelativeTo(null); 
    }

    private void configurarCartelaoVisual() {
        labelsNumeros = new JLabel[TOTAL_DITADOS]; 
        pnlCartelao.setLayout(new GridLayout(3, 10, 5, 5));
        
        for (int i = 0; i < TOTAL_DITADOS; i++) {
            JLabel label = new JLabel("", SwingConstants.CENTER); 
            // FONTE MAIOR E EM NEGRITO (Acessibilidade para idosos)
            label.setFont(new Font("Arial", Font.BOLD, 16)); 
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label.setOpaque(true);
            label.setBackground(Color.WHITE); 
            
            pnlCartelao.add(label);
            labelsNumeros[i] = label; 
        }
    }

    private void atualizarCartelaoVisual() {
        Set<Integer> sorteados = controle.getNumerosSorteados(); 
        Integer ultimo = controle.getUltimoSorteado();
        
        for (int i = 0; i < TOTAL_DITADOS; i++) {
            JLabel label = labelsNumeros[i];
            
            if (sorteados.contains(i)) {
                // Estado: SORTEADO
                String ditadoInicial = controle.getDitadoPrimeiraParte(i);
                label.setText("<html><center><b>" + ditadoInicial + "</b></center></html>"); 
                label.setFont(new Font("Arial", Font.BOLD, 16)); 

                if (i == ultimo && ultimo != null) {
                    label.setBackground(new Color(255, 102, 102)); // Vermelho
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(153, 255, 153)); // Verde Claro
                    label.setForeground(Color.BLACK);
                }
            } else {
                // Estado: NÃO SORTEADO (REMOÇÃO DO NÚMERO AQUI)
                label.setText(""); // A célula fica vazia
                label.setFont(new Font("Arial", Font.BOLD, 16));
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
        }
        
        // Verificação de Vencedores
        List<String> vencedores = controle.verificarVencedores();

        if (!vencedores.isEmpty()) {
            String listaNomes = String.join(", ", vencedores);
            // Mensagem ajustada para singular, conforme lógica de vencedor único
            String mensagem = "BINGO! O GANHADOR É: \n" + listaNomes;
            
            JOptionPane.showMessageDialog(this, mensagem, "BINGO ENCONTRADO!", JOptionPane.INFORMATION_MESSAGE);
            btnSortear.setEnabled(false);
        }
    }

    private void btnSortearActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Verifica se já há vencedor
        if (!controle.verificarVencedores().isEmpty()) {
            btnSortear.setEnabled(false);
            return;
        }

        Integer numeroSorteado = controle.sortearProximo(); 

        if (numeroSorteado != null) {
            lblNumeroSorteado.setText(String.valueOf(numeroSorteado));
            lblDitadoCompleto.setText(controle.getDitadoPrimeiraParte(numeroSorteado)); 
            
            atualizarCartelaoVisual(); 
        } else {
            lblNumeroSorteado.setText("FIM");
            lblDitadoCompleto.setText("Todos os 30 ditados foram sorteados!");
            btnSortear.setEnabled(false);
        }
    }                                          

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        int numJogadores = 0;
        boolean inputValido = false;

        // 1. Pede a quantidade de jogadores (Loop para garantir input válido)
        while (!inputValido) {
            String input = JOptionPane.showInputDialog(this, 
                    "Digite a quantidade de jogadores (1 a 20):", 
                    "Configuração do Jogo", 
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                // Usuário cancelou
                return; 
            }

            try {
                numJogadores = Integer.parseInt(input.trim());
                if (numJogadores >= 1 && numJogadores <= 20) {
                    inputValido = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor, insira um número entre 1 e 20.", "Erro de Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input inválido. Por favor, insira um número.", "Erro de Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // 2. Inicia o jogo com a quantidade selecionada (numJogadores > 0 garantido)
        controle.iniciarNovoJogo(numJogadores);
        
        // 3. Coleta os nomes dos jogadores para as cartelas criadas
        for (int i = 0; i < numJogadores; i++) {
            String input = JOptionPane.showInputDialog(this, 
                    "Cartela ID " + (i + 1) + " (Máx. Cartela: " + numJogadores + "): Digite o nome do jogador:", 
                    "Cadastro de Jogadores", 
                    JOptionPane.QUESTION_MESSAGE);
            
            String nomeFinal = (input != null && !input.trim().isEmpty()) ? input.trim() : "Jogador " + (i + 1);
            
            // 4. Atualiza o nome na lógica do jogo (BingoGame)
            controle.setPlayerName(i, nomeFinal); 
        }
        
        // 5. Reseta a UI para o estado inicial
        lblNumeroSorteado.setText("Aguardando...");
        lblDitadoCompleto.setText("Pressione 'Sortear Próximo' para começar."); 
        btnSortear.setEnabled(true);
        atualizarCartelaoVisual(); 
    }                                           

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        pnlControles = new javax.swing.JPanel();
        lblNumeroSorteado = new javax.swing.JLabel();
        btnSortear = new javax.swing.JButton();
        btnNovoJogo = new javax.swing.JButton();
        lblDitadoCompleto = new javax.swing.JLabel();
        pnlCartelao = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlControles.setBorder(javax.swing.BorderFactory.createTitledBorder("Controle do Sorteio"));

        lblNumeroSorteado.setFont(new java.awt.Font("Arial", 1, 64)); 
        lblNumeroSorteado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumeroSorteado.setText("00");

        btnSortear.setFont(new java.awt.Font("Arial", 1, 18)); 
        btnSortear.setText("Sortear Próximo");
        btnSortear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortearActionPerformed(evt);
            }
        });

        btnNovoJogo.setFont(new java.awt.Font("Arial", 1, 18));
        btnNovoJogo.setText("Novo Jogo / Resetar");
        btnNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoJogoActionPerformed(evt);
            }
        });

        lblDitadoCompleto.setFont(new java.awt.Font("Arial", 1, 18)); 
        lblDitadoCompleto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDitadoCompleto.setText("Aguardando...");

        javax.swing.GroupLayout pnlControlesLayout = new javax.swing.GroupLayout(pnlControles);
        pnlControles.setLayout(pnlControlesLayout);
        pnlControlesLayout.setHorizontalGroup(
            pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNumeroSorteado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDitadoCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlControlesLayout.createSequentialGroup()
                        .addComponent(btnSortear, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovoJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlControlesLayout.setVerticalGroup(
            pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNumeroSorteado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlControlesLayout.createSequentialGroup()
                        .addGroup(pnlControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSortear, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNovoJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDitadoCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCartelao.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Estado do Cartelão de Ditados",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Arial", Font.BOLD, 18) 
        ));
        
        javax.swing.GroupLayout pnlCartelaoLayout = new javax.swing.GroupLayout(pnlCartelao);
        pnlCartelao.setLayout(pnlCartelaoLayout);
        pnlCartelaoLayout.setHorizontalGroup(
            pnlCartelaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
        );
        pnlCartelaoLayout.setVerticalGroup(
            pnlCartelaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlControles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCartelao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCartelao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JanelaPrincipal janela = new JanelaPrincipal();
                janela.setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnNovoJogo;
    private javax.swing.JButton btnSortear;
    private javax.swing.JLabel lblDitadoCompleto;
    private javax.swing.JLabel lblNumeroSorteado;
    private javax.swing.JPanel pnlCartelao;
    private javax.swing.JPanel pnlControles;
}