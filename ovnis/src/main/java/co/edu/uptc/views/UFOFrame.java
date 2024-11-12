package co.edu.uptc.views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.models.UFO;

public class UFOFrame extends JFrame implements ViewInterface{

    private UFOPanel ufoPanel;
    private UFO selectedUFO;
    private UFOControlPanel controlPanel;
    private ArrayList<Point> trajectory;
    private ArrayList<UFO> ufos;
    private PresenterInterface presenter;

    public UFOFrame(ArrayList<UFO> ufos, ActionListener updateListener, ActionListener spawListener,
            ActionListener speedListener, ActionListener imagListener) {
        this.ufos = ufos;
        ufoPanel = new UFOPanel(ufos, "/Images/background.jpg");
        trajectory = new ArrayList<>();
        setLayout(new BorderLayout());
        add(ufoPanel, BorderLayout.CENTER);

        controlPanel = new UFOControlPanel(updateListener, spawListener, speedListener, imagListener, e -> updateView(), ufos);
        add(controlPanel, BorderLayout.EAST);

        ufoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (UFO ufo : ufos) {
                        Image image = ufo.getImage();
                        int imageWidth = image.getWidth(ufoPanel);
                        int imageHeight = image.getHeight(ufoPanel);
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
                    trajectory.clear();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && selectedUFO != null) {
                    selectedUFO.setTrajectory(trajectory);
                }
            }
        });

        ufoPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && selectedUFO != null) {
                    trajectory.add(e.getPoint());
                    ufoPanel.repaint();
                }
            }
        });

        setTitle("UFO SIMULATION");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void updateUfos(ArrayList<UFO> ufos) {
        this.ufos = ufos;
        repaint();
    }

    public int getNumberOfUfos() {
        return controlPanel.getNumOfUFOs();
    }

    public int getSpawnTime() {
        return controlPanel.getSpawnTime();
    }

    public int getSpeed() {
        return controlPanel.getSpeed();
    }

    public UFO getSelecteUFO() {
        return selectedUFO;
    }

    public void setUFOsInScreenCount(int count) {
        controlPanel.setUFOsInScreenCount(count);
    }

    public void setUFOsCrashedCount(int count) {
        controlPanel.setUFOsCrashedCount(count);
    }

    private void updateView() {
        setUFOsInScreenCount(ufos.size());
        setUFOsCrashedCount(ufos.stream().filter(UFO::isCrashed).mapToInt(UFO -> 1).sum());
    }

    @Override
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }
}
