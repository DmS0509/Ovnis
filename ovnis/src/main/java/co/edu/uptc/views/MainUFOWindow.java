package co.edu.uptc.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import co.edu.uptc.models.UFO;

import javax.swing.JPanel;

public class MainUFOWindow extends JPanel{
    
    private ArrayList<UFO> ufos;
    
    public MainUFOWindow(ArrayList<UFO> ufos) {
        this.ufos = ufos;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (UFO ufo : ufos) {
            g.fillOval(ufo.getCoordenateX(), ufo.getCoordenateY(), 20, 20);
        }
    }
}
