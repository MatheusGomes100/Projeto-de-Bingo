/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.bingogame;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa a versão "digital" de uma cartela física impressa.
 * O número da cartela (numeroCartela) corresponde exatamente ao "Nº"
 * impresso no PDF (Nº 1, Nº 2, ...), para que a equipe nunca tenha dúvida
 * sobre qual cartela de papel pertence a qual registro no sistema.
 */
public class Cartela {

    private String nomeJogador;
    private final int numeroCartela;
    private final Set<Integer> numerosDaCartela;
    private final Set<Integer> numerosMarcados;

    public Cartela(int numeroCartela, String nome, int[] numeros) {
        this.numeroCartela = numeroCartela;
        this.nomeJogador = nome;
        this.numerosDaCartela = new HashSet<>();
        for (int num : numeros) {
            this.numerosDaCartela.add(num);
        }
        this.numerosMarcados = new HashSet<>();
    }

    public void marcarDitado(int ditadoId) {
        if (numerosDaCartela.contains(ditadoId)) {
            numerosMarcados.add(ditadoId);
        }
    }

    public boolean isCompleta() {
        return numerosMarcados.containsAll(numerosDaCartela);
    }

    // --- Getters e Setters ---

    public int getNumeroCartela() {
        return numeroCartela;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nome) {
        this.nomeJogador = nome;
    }

    public Set<Integer> getNumerosDaCartela() {
        return numerosDaCartela;
    }

    public int getTotalMarcados() {
        return numerosMarcados.size();
    }

    public int getTotalNumeros() {
        return numerosDaCartela.size();
    }
}
