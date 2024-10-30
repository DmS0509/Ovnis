package co.edu.uptc.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import co.edu.uptc.models.UFO;

public class UFOPanel extends JPanel{
    
    private ArrayList<UFO> ufos;

    public UFOPanel(ArrayList<UFO> ufos){
        this.ufos = ufos;
        setPreferredSize(new Dimension(800, 600));
    }

    public void updateUFOS(ArrayList<UFO> ufos){
        this.ufos = ufos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (UFO ufo : ufos) {
            g.fillOval(ufo.getCoordenateX(), ufo.getCoordenateY(), 20, 20);
        }
    }
}
