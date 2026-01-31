/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.bingogame;

import java.util.*;

public class Sorteador {

    private final List<Integer> ditadosDisponiveis;
    private final Set<Integer> ditadosSorteados;
    private Integer ultimoSorteado;
    private final Random random;
    private final int totalDitados;

    public Sorteador(int totalDitados) {
        this.totalDitados = totalDitados;
        this.ditadosDisponiveis = new ArrayList<>();
        this.ditadosSorteados = new HashSet<>();
        this.random = new Random();
        this.ultimoSorteado = null;
        
        for (int i = 0; i < totalDitados; i++) {
            ditadosDisponiveis.add(i);
        }
        Collections.shuffle(ditadosDisponiveis); 
    }
    
    public void resetar() {
        ditadosDisponiveis.clear();
        for (int i = 0; i < totalDitados; i++) {
            ditadosDisponiveis.add(i);
        }
        ditadosSorteados.clear();
        ultimoSorteado = null;
        Collections.shuffle(ditadosDisponiveis);
    }

    public Integer sortearProximo() {
        if (ditadosDisponiveis.isEmpty()) {
            ultimoSorteado = null;
            return null;
        }

        Integer sorteado = ditadosDisponiveis.remove(0); 
        ditadosSorteados.add(sorteado);
        ultimoSorteado = sorteado;

        return sorteado;
    }

    public Set<Integer> getDitadosSorteados() {
        return Collections.unmodifiableSet(ditadosSorteados);
    }

    public Integer getUltimoSorteado() {
        return ultimoSorteado;
    }
}