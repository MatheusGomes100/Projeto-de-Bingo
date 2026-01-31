/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.bingogame;

import java.util.HashSet;
import java.util.Set;

public class Cartela {
    
    private String nomeJogador; 
    private final Set<Integer> numerosDaCartela;
    private final Set<Integer> numerosMarcados;

    public Cartela(String nome, int[] numeros) {
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
    
    public String getNomeJogador() {
        return nomeJogador;
    }
    
    public void setNomeJogador(String nome) {
        this.nomeJogador = nome;
    }

    public Set<Integer> getNumerosDaCartela() {
        return numerosDaCartela;
    }
}