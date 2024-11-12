package co.edu.uptc.presenters;

import javax.swing.*;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.models.MusicGame;
import co.edu.uptc.models.UFO;
import co.edu.uptc.views.UFOFrame;

public class UFOApp implements PresenterInterface{
    private ArrayList<UFO> ufos;
    private UFOFrame ufoView;
    private Timer timer;
    private int spawnTime;
    private int maxCoordenateX = 800;
    private int maxCoordenateY = 600;
    private String ufoImagePath = "Images/MainOvni.png";
    private int maxUFOs;
    private Point defaultDestination = new Point(100, 100);
    private MusicGame musicGame;
    private ViewInterface view;
    private ModelInterface model;
    //private int ufosCrashedCount;

    public UFOApp() {
        ufos = new ArrayList<>();
        ufoView = new UFOFrame(ufos, e -> updateUFOS(), e -> updateSpawnTime(), e -> updateSpeed(), e -> selectImage());
        maxUFOs = ufoView.getNumberOfUfos();
        musicGame = new MusicGame();

        spawnTime = 1000;

        testImageLoading();
        musicGame.play("sounds/musicInGame.wav");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ufoView.updateUfos(ufos);
                removeReachedOrCrashedUFOs();
                checkCollisions();
                //updateControlPanel();
            }
        }, 0, 10);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (ufos) {
                    int ufosToSpawn = maxUFOs - ufos.size();
                    for (int i = 0; i < ufosToSpawn && i < 1; i++) {
                        spawnUFO();
                    }
                }
            }
        }, 0, spawnTime);
        ufoView.setVisible(true);
    }

    @Override
    public void spawnUFO() {
        UFO ufo = new UFO(maxCoordenateX, maxCoordenateY, ufoImagePath, -1, -1);
        synchronized (ufos) {
            ufos.add(ufo);
        }
        ufo.start();
    }

    @Override
    public void checkCollisions() {
        synchronized (ufos) {
            for (int i = 0; i < ufos.size(); i++) {
                UFO ufo1 = ufos.get(i);
                Rectangle bounds1 = new Rectangle(ufo1.getCoordenateX(), ufo1.getCoordenateY(), 50, 50);

                if (ufo1.isCrashed())
                    continue;
                for (int j = i + 1; j < ufos.size(); j++) {
                    UFO ufo2 = ufos.get(j);
                    Rectangle bounds2 = new Rectangle(ufo2.getCoordenateX(), ufo2.getCoordenateY(), 50, 50);
                    if (bounds1.intersects(bounds2)) {
                        ufo1.setCollision(true);
                        ufo2.setCollision(true);
                        //ufosCrashedCount++;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void updateUFOS() {
        int numOfUFOs = ufoView.getNumberOfUfos();
        if (numOfUFOs < 0)
            return; 
        maxUFOs = numOfUFOs;
        synchronized (ufos) {
            for (UFO ufo : ufos) {
                ufo.stopMoving();
            }
            ufos.clear();
        }
    }

    private void removeReachedOrCrashedUFOs() {
        synchronized (ufos) {
            Iterator<UFO> iterator = ufos.iterator();
            while (iterator.hasNext()) {
                UFO ufo = iterator.next();
                if (!ufo.isAlive() || ufo.isCrashed() || ufo.hasReachedDefaultDestination(defaultDestination)) {
                    //ufosCrashedCount++;
                    iterator.remove();
                }
                //iterator.remove();
            }
        }
    }

    private void updateSpawnTime() {
        spawnTime = ufoView.getSpawnTime();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (ufos) {
                    int ufosToSpawn = maxUFOs - ufos.size();
                    for (int i = 0; i < ufosToSpawn && i < 5; i++) {
                        spawnUFO();
                    }
                }
            }
        }, 0, spawnTime);
    }

    private void updateSpeed() {
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
    private void updateControlPanel() {
        SwingUtilities.invokeLater(() -> {
            ufoView.setUFOsInScreenCount(ufos.size());
            ufoView.setUFOsCrashedCount(ufosCrashedCount);
        });
        
    }
    */
    private void selectImage() {
        String[] ufoImages = { "Images/MainUfo.png", "Images/Ovni1.png", "Images/Ovni2.png" };

        String selectedImage = (String) JOptionPane.showInputDialog(ufoView, 
        "Selecciona un ovni", 
        "selector",
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                ufoImages, 
                ufoImages[0]
        );

        if (selectedImage != null && !selectedImage.isEmpty()) {
            ufoImagePath = selectedImage;
            for (UFO ufo : ufos) {
                ufo.setImage(selectedImage);
            }
        }
    }

    private void testImageLoading() {
        Image testImage = new ImageIcon(getClass().getClassLoader().getResource("Images/MainOvni.png")).getImage();
        if (testImage == null) {
            System.err.println("No se pudo cargar la imagen de prueba.");
        } else {
            System.out.println("Imagen de prueba cargada exitosamente.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UFOApp::new);
    }

    @Override
    public void setView(ViewInterface view) {
      this.view = ufoView;
    }

    @Override
    public void setModel(ModelInterface model) {
      this.model = model;
    }
}
