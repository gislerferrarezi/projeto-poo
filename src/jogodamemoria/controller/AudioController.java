package jogodamemoria.controller;

import javax.sound.sampled.*;
import java.net.URL;

public class AudioController {

    private static boolean somAtivado = true;
    private static Clip clipMusica;

    //  GERENCIAMENTO DA MÚSICA DE FUNDO 
    public static void tocarMusicaFundo(String caminhoArquivo) {
        if (clipMusica != null && clipMusica.isRunning()) {
            return; 
        }

        new Thread(() -> {
            try {
                URL url = AudioController.class.getResource(caminhoArquivo);
                if (url != null) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                    clipMusica = AudioSystem.getClip();
                    clipMusica.open(audioStream);

                    // Reduz o volume da música para ficar suave (-15 dB)
                    setVolumeClip(clipMusica, -15.0f);

                    // Configura para tocar em loop infinito
                    clipMusica.loop(Clip.LOOP_CONTINUOUSLY);

                    if (!somAtivado) {
                        clipMusica.stop();
                    } else {
                        clipMusica.start();
                    }
                } else {
                    System.err.println("Música de fundo não encontrada: " + caminhoArquivo);
                }
            } catch (Exception e) {
                System.err.println("Erro ao reproduzir música de fundo: " + e.getMessage());
            }
        }).start();
    }

    public static void pararMusicaFundo() {
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
        }
    }

    //  CONTROLE GERAL DE SOM 
    public static void alternarSom() {
        somAtivado = !somAtivado;

        if (clipMusica != null) {
            if (somAtivado) {
                clipMusica.start();
                clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clipMusica.stop();
            }
        }
    }

    public static boolean isSomAtivado() {
        return somAtivado;
    }

    public static void setSomAtivado(boolean ativado) {
        somAtivado = ativado;
        if (clipMusica != null) {
            if (somAtivado) {
                clipMusica.start();
                clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clipMusica.stop();
            }
        }
    }

    //  EFEITOS SONOROS (CLIQUES / NAVEGAÇÃO) 
    public static void tocarEfeito(String caminhoArquivo) {
        if (!somAtivado)
            return;

        new Thread(() -> {
            try {
                URL url = AudioController.class.getResource(caminhoArquivo);
                if (url != null) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                            try {
                                audioStream.close();
                            } catch (Exception e) {
                                System.err.println("Erro ao fechar AudioInputStream: " + e.getMessage());
                            }
                        }
                    });

                    clip.start();
                } else {
                    System.err.println("Efeito de áudio não encontrado: " + caminhoArquivo);
                }
            } catch (Exception e) {
                System.err.println("Erro ao reproduzir efeito sonoro: " + e.getMessage());
            }
        }).start();
    }

    // Método auxiliar para ajustar volume do áudio (em Decibéis)
    private static void setVolumeClip(Clip clip, float decibeis) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(decibeis);
        }
    }
}