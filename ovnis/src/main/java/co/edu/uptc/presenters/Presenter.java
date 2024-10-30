package co.edu.uptc.presenters;
import javax.swing.*;

import co.edu.uptc.models.UFO;
import co.edu.uptc.views.MainUFOWindow;

import java.util.ArrayList;

public class Presenter extends JFrame {
    private ArrayList<UFO> ufos;
    private MainUFOWindow view;

    public Presenter(int numOfUFOs) {
        ufos = new ArrayList<>();
        for (int i = 0; i < numOfUFOs; i++) {
            ufos.add(new UFO());
        }

        view = new MainUFOWindow(ufos);
        add(view);

        setTitle("UFO Simulation");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        new Timer(10, e -> {
            for (UFO ufo : ufos) {
                ufo.move();
            }
            view.repaint();
        }).start();
    }
}

