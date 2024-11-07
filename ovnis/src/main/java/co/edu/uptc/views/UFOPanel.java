package co.edu.uptc.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import co.edu.uptc.models.UFO;

public class UFOPanel extends JPanel{
    
    private ArrayList<UFO> ufos;
    private Image ufoImage;
   // private BufferedImage ufoImage;


    public UFOPanel(ArrayList<UFO> ufos){
        this.ufos = ufos;
        setPreferredSize(new Dimension(800, 600));
    }

    /* 
    public void setUfoImage(BufferedImage ufoImage){
        this.ufoImage = ufoImage;
    }
    */

    public void updateUFOS(ArrayList<UFO> ufos){
        this.ufos = ufos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (UFO ufo : ufos) {
            Image image = ufo.getImage();
            if (image != null) {
                g.drawImage(image, ufo.getCoordenateX(), ufo.getCoordenateY(), this);
            }else {
                g.fillOval(ufo.getCoordenateX(), ufo.getCoordenateY(), 20, 20);
            }
        }
    }
        

    public void getImage(){
        try {
            ufoImage = ImageIO.read(new File("Images/Ovni1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/* 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (ufoImage != null) {
            int width = 40;
            int height = 30;
            g.drawImage(ufoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 10, 8, this);
        }else {
            System.out.println("weeey no carga :'v");
        }
    }
        */
}
