/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.bingogame;

import java.util.*;

public class Sorteador {

    private final List<Integer> ditadosDisponiveis;
    private final Set<Integer> ditadosSorteados;
    private final List<Integer> ordemSorteio;
    private Integer ultimoSorteado;
    private final Random random;
    private final int totalDitados;

    public Sorteador(int totalDitados) {
        this.totalDitados = totalDitados;
        this.ditadosDisponiveis = new ArrayList<>();
        this.ditadosSorteados = new HashSet<>();
        this.ordemSorteio = new ArrayList<>();
        this.random = new Random();
        this.ultimoSorteado = null;

        for (int i = 0; i < totalDitados; i++) {
            ditadosDisponiveis.add(i);
        }
        Collections.shuffle(ditadosDisponiveis, random);
    }

    public void resetar() {
        ditadosDisponiveis.clear();
        for (int i = 0; i < totalDitados; i++) {
            ditadosDisponiveis.add(i);
        }
        ditadosSorteados.clear();
        ordemSorteio.clear();
        ultimoSorteado = null;
        Collections.shuffle(ditadosDisponiveis, random);
    }

    public Integer sortearProximo() {
        if (ditadosDisponiveis.isEmpty()) {
            ultimoSorteado = null;
            return null;
        }

        Integer sorteado = ditadosDisponiveis.remove(0);
        ditadosSorteados.add(sorteado);
        ordemSorteio.add(sorteado);
        ultimoSorteado = sorteado;

        return sorteado;
    }

    public Set<Integer> getDitadosSorteados() {
        return Collections.unmodifiableSet(ditadosSorteados);
    }

    public Integer getUltimoSorteado() {
        return ultimoSorteado;
    }

    /** Quantos ditados já foram sorteados nesta partida. */
    public int getTotalSorteados() {
        return ditadosSorteados.size();
    }

    /** Quantos ditados ainda restam por sortear. */
    public int getRestantes() {
        return ditadosDisponiveis.size();
    }

    /** Em que ordem (1º, 2º, 3º...) um ditado específico foi sorteado, ou -1 se ainda não saiu. */
    public int getOrdemDoSorteio(int ditadoId) {
        int posicao = ordemSorteio.indexOf(ditadoId);
        return posicao < 0 ? -1 : posicao + 1;
    }
}
