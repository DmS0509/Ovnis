package co.edu.uptc.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import co.edu.uptc.models.UFO;

public class UFOPanel extends JPanel {

    private ArrayList<UFO> ufos;
    private Image backgroundImage;
    private UFO selectedUFO;
    private ArrayList<Point> trajectory;
    private boolean showTrajectory;
    // private BufferedImage ufoImage;

    public UFOPanel(ArrayList<UFO> ufos, String backgroundImagepath) {
        this.ufos = ufos;
        this.trajectory = new ArrayList<>();
        this.showTrajectory = true;
        loadBackgroundImage(backgroundImagepath);
        setPreferredSize(new Dimension(1000, 750));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (UFO ufo : ufos) {
                        Image image = ufo.getImage();
                        int imageWidth = image.getWidth(UFOPanel.this);
                        int imageHeight = image.getHeight(UFOPanel.this);
                        Rectangle ufoBounds = new Rectangle(ufo.getCoordenateX(), ufo.getCoordenateY(), imageWidth,
                                imageHeight);
                        if (ufoBounds.contains(e.getPoint())) {
                            selectedUFO = ufo;
                            trajectory.clear();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && selectedUFO != null) {
                    trajectory.add(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && selectedUFO != null) {
                    selectedUFO.setTrajectory(trajectory);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if (SwingUtilities.isRightMouseButton(e) && selectedUFO != null) {
                    trajectory.add(e.getPoint());
                    repaint();
                }
            }
        });
    }

    private void loadBackgroundImage(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        if (icon.getImage() != null) {
            backgroundImage = icon.getImage();
        } else {
            System.err.println("no se puedo cargar la imagen " + path);
        }
    }

    public void updateUFOS(ArrayList<UFO> ufos) {
        this.ufos = ufos;
        repaint();
    }

    public void setShowTrajectory(boolean showTrajectory){
        this.showTrajectory = showTrajectory;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        for (UFO ufo : new ArrayList<>(ufos)) {
            Image image = ufo.getImage();
            if (image != null) {
                g.drawImage(image, ufo.getCoordenateX(), ufo.getCoordenateY(), this);
            } else {
                g.fillOval(ufo.getCoordenateX(), ufo.getCoordenateY(), 20, 20);
            }
        }
        if (showTrajectory && selectedUFO != null && !trajectory.isEmpty()) {
            g.setColor(Color.RED);
            for (int i = 0; i < trajectory.size() - 1; i++) {
                Point p1 = trajectory.get(i);
                Point p2 = trajectory.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}
