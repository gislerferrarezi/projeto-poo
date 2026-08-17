package jogodamemoria.controller;

import javax.sound.sampled.*;
import java.net.URL;

public class AudioController {
    private static boolean somAtivado = true;

    public static void alternarSom() {
        somAtivado = !somAtivado;
    }

    public static boolean isSomAtivado() {
        return somAtivado;
    }

    // Toca efeitos curtos (clique/navegação, acerto, vitória) de forma assíncrona
    public static void tocarEfeito(String caminhoArquivo) {
        if (!somAtivado)
            return;

        // Roda em uma thread separada para não travar a interface (EDT)
        new Thread(() -> {
            try {
                URL url = AudioController.class.getResource(caminhoArquivo);
                if (url != null) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } else {
                    System.err.println("Áudio não encontrado: " + caminhoArquivo);
                }
            } catch (Exception e) {
                System.err.println("Erro ao reproduzir efeito sonoro: " + e.getMessage());
            }
        }).start();
    }
}