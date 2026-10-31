package com.mycompany.bingogame;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
 * Efeitos sonoros simples, gerados na hora (sem depender de nenhum arquivo
 * de áudio externo). Deixa o jogo mais animado ao vivo, com um "toc" a cada
 * sorteio e uma pequena fanfarra quando alguém faz BINGO.
 *
 * Toda chamada roda em uma thread separada e falha em silêncio caso o
 * computador não tenha placa de som disponível, para nunca travar o jogo.
 */
public final class SomUtil {

    private static final float SAMPLE_RATE = 44100f;

    private SomUtil() {
    }

    public static void tocarSorteio() {
        new Thread(() -> tocarTom(880, 90), "som-sorteio").start();
    }

    public static void tocarBingo() {
        new Thread(() -> {
            int[] notas = {523, 659, 784, 1047}; // pequena fanfarra ascendente
            for (int nota : notas) {
                tocarTom(nota, 160);
            }
        }, "som-bingo").start();
    }

    private static void tocarTom(int frequenciaHz, int duracaoMs) {
        try {
            int amostras = (int) (SAMPLE_RATE * duracaoMs / 1000.0);
            byte[] buffer = new byte[amostras];
            for (int i = 0; i < amostras; i++) {
                double angulo = 2.0 * Math.PI * i * frequenciaHz / SAMPLE_RATE;
                // Envelope simples para evitar "cliques" no início/fim do som.
                double envelope = Math.min(1.0, Math.min(i, amostras - i) / 200.0);
                buffer[i] = (byte) (Math.sin(angulo) * 100 * envelope);
            }
            AudioFormat formato = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
            try (SourceDataLine linha = AudioSystem.getSourceDataLine(formato)) {
                linha.open(formato);
                linha.start();
                linha.write(buffer, 0, buffer.length);
                linha.drain();
            }
        } catch (Exception e) {
            // Sem placa de som disponível: ignora e segue o jogo normalmente.
        }
    }
}
