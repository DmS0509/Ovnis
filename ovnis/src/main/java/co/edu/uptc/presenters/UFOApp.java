package co.edu.uptc.presenters;

import javax.swing.*;

//import java.awt.Image;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import co.edu.uptc.models.UFO;
import co.edu.uptc.views.UFOFrame;

public class UFOApp {
    private ArrayList<UFO> ufos;
    private UFOFrame ufoView;
    private Timer timer;
    private int spawnTime;
    private int maxCoordenateX = 800;
    private int maxCoordenateY = 600;
    private String ufoImagePath = "ovnis\\src\\main\\resources\\Ovni1.png";

    public UFOApp(){
        ufos = new ArrayList<>();
        ufoView = new UFOFrame(ufos, e -> updateUFOs(), e -> updateSpawnTime(), e -> updateSpeed(), e -> updateImage());
        
        for (int i = 0; i < 10; i++) {
            UFO ufo = new UFO(maxCoordenateX, maxCoordenateY, ufoImagePath);
            ufos.add(ufo);
            ufo.start();
        }
        spawnTime = 1000;
        addTimer();
    }

    private void updateUFOs() {
        int numOfUFOs = ufoView.getNumberOfUfos();
        if (numOfUFOs < 0) return; // Manejo de error
        for (UFO ufo : ufos) {
            ufo.stopMoving();
        }
        ufos.clear();
        for (int i = 0; i < numOfUFOs; i++) {
            UFO ufo = new UFO(maxCoordenateX, maxCoordenateY, ufoImagePath);
            ufos.add(ufo);
            ufo.start();
        }
        ufoView.updateUfos(ufos);
    }

    private void updateSpawnTime(){
        spawnTime = ufoView.getSpawnTime();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                for (UFO ufo : ufos) {
                    ufo.move();
                }
                ufoView.updateUfos(ufos);
            }

        }, 0, spawnTime);
    }

    public void addTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                for (UFO ufo : ufos) {
                    ufo.move();
                }
                ufoView.updateUfos(ufos);
            }
        }, 0, 10);
        ufoView.setVisible(true);
    }

    private void updateSpeed(){
        UFO selectedUFO = ufoView.getSelecteUFO();
        if (selectedUFO != null) {
            selectedUFO.setSpeed(ufoView.getSpeed());
        } else {
            int speed = ufoView.getSpeed();
            for (UFO ufo : ufos) {
                ufo.setSpeed(speed);
            }
        }
    }

    /*
    private void selectImage(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(ufoView);
        if (result == JFileChooser.APPROVE_OPTION) {
            ImageIcon icon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
            ufoImage = icon.getImage();
            for (UFO ufo : ufos) {
                ufo.setImage(ufoImage);    
            }
        }
    }
    */

    private void updateImage(){
        for (UFO ufo : ufos) {
            ufo.setImage(ufoImagePath);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UFOApp::new);    
    }
}

