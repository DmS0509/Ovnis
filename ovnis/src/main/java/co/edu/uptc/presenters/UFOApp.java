package co.edu.uptc.presenters;

import javax.swing.*;
import java.util.ArrayList;

import co.edu.uptc.models.UFO;
//import co.edu.uptc.views.MainUFOWindow;
import co.edu.uptc.views.UFOFrame;

public class UFOApp {
    private ArrayList<UFO> ufos;
    private UFOFrame ufoView;

    public UFOApp(){
        ufos = new ArrayList<>();
        ufoView = new UFOFrame(ufos, e -> updateUFOs());
        
        for (int i = 0; i < 10; i++) {
            ufos.add(new UFO());
        }
        addTimer();
    }

    private void updateUFOs() {
        int numOfUFOs = ufoView.getNumberOfUfos();
        if (numOfUFOs < 0) return; // Manejo de error
        ufos.clear();
        for (int i = 0; i < numOfUFOs; i++) {
            ufos.add(new UFO());
        }
        ufoView.updateUfos(ufos);
    }

    public void addTimer(){
        new Timer(10, e -> {
            for (UFO ufo : ufos) {
                ufo.move();
            }
            ufoView.repaint();
        }).start();
        ufoView.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UFOApp::new);    
    }
}

