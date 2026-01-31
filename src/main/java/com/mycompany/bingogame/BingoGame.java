/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bingogame;

import java.util.*;

public class BingoGame {
    
    private List<Cartela> cartelas;
    private final Sorteador sorteador;
    private final Map<Integer, String[]> ditadosMap;
    
    private static final int[][] CARTELAS_SALVAS = {
        {1, 3, 5, 7, 9, 11, 13, 15, 17, 19},
        {0, 2, 4, 6, 8, 10, 12, 14, 16, 18},
        {20, 21, 22, 23, 24, 25, 26, 27, 28, 29},
        {1, 2, 3, 4, 5, 26, 27, 28, 29, 0},
        {10, 11, 12, 13, 14, 15, 16, 17, 18, 19},
        {0, 8, 9, 1, 4, 11, 17, 20, 23, 26},
        {0, 4, 8, 12, 16, 20, 24, 28, 1, 5},
        {1, 6, 11, 16, 21, 26, 3, 8, 13, 18},
        {4, 5, 6, 7, 0, 14, 15, 16, 17, 18},
        {0, 3, 6, 9, 12, 15, 18, 21, 24, 27},
        {19, 17, 15, 13, 11, 9, 7, 5, 3, 1},
        {18, 16, 14, 12, 10, 8, 6, 4, 2, 0},
        {29, 28, 27, 26, 25, 24, 23, 22, 21, 20},
        {0, 29, 28, 27, 26, 5, 4, 3, 2, 1},
        {19, 18, 17, 16, 15, 14, 13, 12, 11, 10},
        {26, 23, 20, 17, 11, 4, 1, 9, 8, 0},
        {5, 1, 28, 24, 20, 16, 12, 8, 4, 0},
        {18, 13, 8, 3, 26, 21, 16, 11, 6, 1},
        {18, 17, 16, 15, 14, 0, 7, 6, 5, 4},
        {27, 24, 21, 18, 15, 12, 9, 6, 3, 0}
    };
    
    private final int MAX_CARTELAS = CARTELAS_SALVAS.length;

    public BingoGame() {
        this.ditadosMap = criarDitados();
        this.sorteador = new Sorteador(ditadosMap.size());
        iniciarNovoJogo(1);
    }
    
    public void iniciarNovoJogo(int numJogadores) {
        sorteador.resetar();
        iniciarCadastroComCartelasSalvas(numJogadores);
    }
    
    private void iniciarCadastroComCartelasSalvas(int numJogadores) {
        int count = Math.min(numJogadores, MAX_CARTELAS);
        
        cartelas = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            String nomeInicial = "CARTELA ID " + i;
            Cartela novaCartela = new Cartela(nomeInicial, CARTELAS_SALVAS[i]);
            cartelas.add(novaCartela);
        }
    }
    
    public void setPlayerName(int cartelaIndex, String nome) {
        if (cartelaIndex >= 0 && cartelaIndex < cartelas.size()) {
            cartelas.get(cartelaIndex).setNomeJogador(nome.toUpperCase());
        }
    }

    private Map<Integer, String[]> criarDitados() {
        Map<Integer, String[]> ditados = new HashMap<>();
        
        ditados.put(0, new String[]{"DE GRÃO EM GRÃO,".toUpperCase(), "A GALINHA ENCHE O PAPO.".toUpperCase()});
        ditados.put(1, new String[]{"ÁGUA MOLE EM PEDRA DURA,".toUpperCase(), "TANTO BATE ATÉ QUE FURA.".toUpperCase()});
        ditados.put(2, new String[]{"CÃO QUE LATE,".toUpperCase(), "NÃO MORDE.".toUpperCase()});
        ditados.put(3, new String[]{"QUEM COM FERRO FERE,".toUpperCase(), "COM FERRO SERÁ FERIDO.".toUpperCase()});
        ditados.put(4, new String[]{"A PRESSA É".toUpperCase(), "INIMIGA DA PERFEIÇÃO.".toUpperCase()});
        ditados.put(5, new String[]{"MAIS VALE UM PÁSSARO NA MÃO".toUpperCase(), "DO QUE DOIS VOANDO.".toUpperCase()});
        ditados.put(6, new String[]{"EM BOCA FECHADA,".toUpperCase(), "NÃO ENTRA MOSQUITO.".toUpperCase()});
        ditados.put(7, new String[]{"QUEM NÃO TEM CÃO,".toUpperCase(), "CAÇA COM GATO.".toUpperCase()});
        ditados.put(8, new String[]{"ANTES TARDE".toUpperCase(), "DO QUE NUNCA.".toUpperCase()});
        ditados.put(9, new String[]{"O SEGURO".toUpperCase(), "MORREU DE VELHO.".toUpperCase()});
        ditados.put(10, new String[]{"FILHO DE PEIXE,".toUpperCase(), "PEIXINHO É.".toUpperCase()});
        ditados.put(11, new String[]{"LADRÃO QUE ROUBA LADRÃO,".toUpperCase(), "TEM CEM ANOS DE PERDÃO.".toUpperCase()});
        ditados.put(12, new String[]{"CADA MACACO".toUpperCase(), "NO SEU GALHO.".toUpperCase()});
        ditados.put(13, new String[]{"QUEM ESPERA,".toUpperCase(), "SEMPRE ALCANÇA.".toUpperCase()});
        ditados.put(14, new String[]{"PARA BOM ENTENDEDOR,".toUpperCase(), "MEIA PALAVRA BASTA.".toUpperCase()});
        ditados.put(15, new String[]{"DEVAGAR SE VAI".toUpperCase(), "AO LONGE.".toUpperCase()});
        ditados.put(16, new String[]{"DIGA-ME COM QUEM ANDAS,".toUpperCase(), "QUE TE DIREI QUEM ÉS.".toUpperCase()});
        ditados.put(17, new String[]{"TUDO O QUE VAI,".toUpperCase(), "VOLTA.".toUpperCase()});
        ditados.put(18, new String[]{"QUEM RI POR ÚLTIMO,".toUpperCase(), "RI MELHOR.".toUpperCase()});
        ditados.put(19, new String[]{"A MENTIRA TEM PERNA".toUpperCase(), "CURTA.".toUpperCase()});
        ditados.put(20, new String[]{"ONDE HÁ FUMAÇA,".toUpperCase(), "HÁ FOGO.".toUpperCase()});
        ditados.put(21, new String[]{"CAVALO DADO,".toUpperCase(), "NÃO SE OLHA OS DENTES.".toUpperCase()});
        ditados.put(22, new String[]{"É DANDO QUE".toUpperCase(), "SE RECEBE.".toUpperCase()});
        ditados.put(23, new String[]{"GATO ESCALDADO".toUpperCase(), "TEM MEDO DE ÁGUA FRIA.".toUpperCase()});
        ditados.put(24, new String[]{"SACO VAZIO".toUpperCase(), "NÃO PARA EM PÉ.".toUpperCase()});
        ditados.put(25, new String[]{"A NECESSIDADE".toUpperCase(), "É A MÃE DA INVENÇÃO.".toUpperCase()});
        ditados.put(26, new String[]{"QUEM NÃO ARRISCA,".toUpperCase(), "NÃO PETISCA.".toUpperCase()});
        ditados.put(27, new String[]{"DEPOIS DA TEMPESTADE,".toUpperCase(), "VEM A BONANÇA.".toUpperCase()});
        ditados.put(28, new String[]{"UM É POUCO,".toUpperCase(), "DOIS É BOM, TRÊS É DEMAIS.".toUpperCase()});
        ditados.put(29, new String[]{"QUEM CEDO MADRUGA,".toUpperCase(), "DEUS AJUDA.".toUpperCase()});
        
        return ditados;
    }

    public Integer sortearProximo() {
        Integer sorteado = sorteador.sortearProximo();
        
        if (sorteado != null) {
            for (Cartela cartela : cartelas) {
                cartela.marcarDitado(sorteado);
            }
        }
        return sorteado;
    }
    
    public List<String> verificarVencedores() {
        List<String> vencedores = new ArrayList<>();
        
        for (Cartela cartela : cartelas) {
            if (cartela.isCompleta()) {
                vencedores.add(cartela.getNomeJogador());
                return vencedores;
            }
        }
        return vencedores;
    }
    
    public Set<Integer> getNumerosSorteados() {
        return sorteador.getDitadosSorteados();
    }

    public Integer getUltimoSorteado() {
        return sorteador.getUltimoSorteado();
    }

    public String getDitadoPrimeiraParte(int id) {
          if (ditadosMap.containsKey(id)) {
             return ditadosMap.get(id)[0];
        }
        return "ID INVÁLIDO";
    }
}