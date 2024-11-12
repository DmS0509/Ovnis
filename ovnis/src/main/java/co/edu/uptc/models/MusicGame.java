package co.edu.uptc.models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MusicGame {
    
    private Clip clip;

    public void play(String filePath){
        try {
            URL resource = getClass().getClassLoader().getResource(filePath);
            if (resource == null) {
                System.err.println("No se pudo encontrar el archivo de música: " + filePath);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}


