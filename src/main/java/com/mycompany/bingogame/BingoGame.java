package com.mycompany.bingogame;

import java.util.*;

public class BingoGame {

    private List<Cartela> cartelas;
    private final Sorteador sorteador;
    private final Map<Integer, String[]> ditadosMap;

    // Conferidas uma a uma contra as 20 cartelas impressas em PDF (Nº 1 a Nº 20).
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

    public int getMaxCartelas() {
        return MAX_CARTELAS;
    }

    public int getTotalDitados() {
        return ditadosMap.size();
    }

    public void iniciarNovoJogo(int numJogadores) {
        sorteador.resetar();
        iniciarCadastroComCartelasSalvas(numJogadores);
    }

    private void iniciarCadastroComCartelasSalvas(int numJogadores) {
        int count = Math.min(numJogadores, MAX_CARTELAS);
        cartelas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // O número da cartela (i + 1) corresponde exatamente ao "Nº" impresso no PDF.
            String nomeInicial = "Cartela Nº " + (i + 1);
            Cartela novaCartela = new Cartela(i + 1, nomeInicial, CARTELAS_SALVAS[i]);
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
        // Adicionando ditados (Mantive sua lista original)
        ditados.put(0, new String[]{"DE GRÃO EM GRÃO,", "A GALINHA ENCHE O PAPO."});
        ditados.put(1, new String[]{"ÁGUA MOLE EM PEDRA DURA,", "TANTO BATE ATÉ QUE FURA."});
        ditados.put(2, new String[]{"CÃO QUE LATE,", "NÃO MORDE."});
        ditados.put(3, new String[]{"QUEM COM FERRO FERE,", "COM FERRO SERÁ FERIDO."});
        ditados.put(4, new String[]{"A PRESSA É", "INIMIGA DA PERFEIÇÃO."});
        ditados.put(5, new String[]{"MAIS VALE UM PÁSSARO NA MÃO", "DO QUE DOIS VOANDO."});
        ditados.put(6, new String[]{"EM BOCA FECHADA,", "NÃO ENTRA MOSQUITO."});
        ditados.put(7, new String[]{"QUEM NÃO TEM CÃO,", "CAÇA COM GATO."});
        ditados.put(8, new String[]{"ANTES TARDE", "DO QUE NUNCA."});
        ditados.put(9, new String[]{"O SEGURO", "MORREU DE VELHO."});
        ditados.put(10, new String[]{"FILHO DE PEIXE,", "PEIXINHO É."});
        ditados.put(11, new String[]{"LADRÃO QUE ROUBA LADRÃO,", "TEM CEM ANOS DE PERDÃO."});
        ditados.put(12, new String[]{"CADA MACACO", "NO SEU GALHO."});
        ditados.put(13, new String[]{"QUEM ESPERA,", "SEMPRE ALCANÇA."});
        ditados.put(14, new String[]{"PARA BOM ENTENDEDOR,", "MEIA PALAVRA BASTA."});
        ditados.put(15, new String[]{"DEVAGAR SE VAI", "AO LONGE."});
        ditados.put(16, new String[]{"DIGA-ME COM QUEM ANDAS,", "QUE TE DIREI QUEM ÉS."});
        ditados.put(17, new String[]{"TUDO O QUE VAI,", "VOLTA."});
        ditados.put(18, new String[]{"QUEM RI POR ÚLTIMO,", "RI MELHOR."});
        ditados.put(19, new String[]{"A MENTIRA TEM PERNA", "CURTA."});
        ditados.put(20, new String[]{"ONDE HÁ FUMAÇA,", "HÁ FOGO."});
        ditados.put(21, new String[]{"CAVALO DADO,", "NÃO SE OLHA OS DENTES."});
        ditados.put(22, new String[]{"É DANDO QUE", "SE RECEBE."});
        ditados.put(23, new String[]{"GATO ESCALDADO", "TEM MEDO DE ÁGUA FRIA."});
        ditados.put(24, new String[]{"SACO VAZIO", "NÃO PARA EM PÉ."});
        ditados.put(25, new String[]{"A NECESSIDADE", "É A MÃE DA INVENÇÃO."});
        ditados.put(26, new String[]{"QUEM NÃO ARRISCA,", "NÃO PETISCA."});
        ditados.put(27, new String[]{"DEPOIS DA TEMPESTADE,", "VEM A BONANÇA."});
        ditados.put(28, new String[]{"UM É POUCO,", "DOIS É BOM, TRÊS É DEMAIS."});
        ditados.put(29, new String[]{"QUEM CEDO MADRUGA,", "DEUS AJUDA."});

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
            }
        }
        return vencedores; // Retorna todos os vencedores encontrados
    }

    public List<Cartela> getCartelas() {
        return cartelas == null ? Collections.emptyList() : Collections.unmodifiableList(cartelas);
    }

    public Set<Integer> getNumerosSorteados() {
        return sorteador.getDitadosSorteados();
    }

    public Integer getUltimoSorteado() {
        return sorteador.getUltimoSorteado();
    }

    public int getTotalSorteados() {
        return sorteador.getTotalSorteados();
    }

    public int getRestantes() {
        return sorteador.getRestantes();
    }

    public int getOrdemDoSorteio(int ditadoId) {
        return sorteador.getOrdemDoSorteio(ditadoId);
    }

    public String getDitadoPrimeiraParte(int id) {
        if (ditadosMap.containsKey(id)) {
            return ditadosMap.get(id)[0];
        }
        return "ID INVÁLIDO";
    }

    public String getDitadoSegundaParte(int id) {
        if (ditadosMap.containsKey(id)) {
            return ditadosMap.get(id)[1];
        }
        return "";
    }
}
