package co.edu.uptc.views;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.models.UFO;

public class UFOFrame extends JFrame{
    
    private UFOPanel ufoPanel;
    private JTextField ufoCount;
    private JTextField ufoSpawnTime;
    private JTextField speedField;
    //private JComboBox<String> ufoSelector;
    private UFO selectedUFO;

    public UFOFrame(ArrayList<UFO> ufos, ActionListener updateListener, ActionListener spawListener, ActionListener speedListener, ActionListener imagListener){
        ufoPanel = new UFOPanel(ufos);
        setLayout(new BorderLayout());
        add(ufoPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Numero de ovnis"));
        ufoCount = new JTextField("10", 5);
        controlPanel.add(ufoCount);

        JButton uptadeButton = new JButton("actualizar");
        uptadeButton.addActionListener(updateListener);
        controlPanel.add(uptadeButton);

        controlPanel.add(new JLabel("tiempo de aparicion (ms): "));
        ufoSpawnTime = new JTextField("1000", 5);
        controlPanel.add(ufoSpawnTime);

        JButton spawnTimeButton = new JButton("Configurar tiempo");
        spawnTimeButton.addActionListener(updateListener);
        controlPanel.add(spawnTimeButton);

        controlPanel.add(new JLabel("Velocidad (ms): "));
        speedField = new JTextField("50", 5);
        controlPanel.add(speedField);

        JButton speedButton = new JButton("Configurar velocidad");
        speedButton.addActionListener(speedListener);
        controlPanel.add(speedButton);

        JButton selectImageButton = new JButton("seleccionar Ovni");
        selectImageButton.addActionListener(imagListener);
        controlPanel.add(selectImageButton);

        add(controlPanel, BorderLayout.SOUTH);

        ufoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                for (UFO ufo : ufos) {
                    if (new Rectangle(ufo.getCoordenateX(), ufo.getCoordenateY(), 20, 20).contains(e.getPoint())) {
                        selectedUFO = ufo;
                        break;
                    }
                }
            }
        });

        /* 
        ufoSelector = new JComboBox<>();
        for (int i = 0; i < ufos.size(); i++) {
            ufoSelector.addItem("UFO" + (i + 1));
        }
        controlPanel.add(ufoSelector);
        */
        setTitle("UFO SIMULATION");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void updateUfos(ArrayList<UFO> ufos){
        ufoPanel.updateUFOS(ufos);
    }

    public int getNumberOfUfos(){
        try {
            return Integer.parseInt(ufoCount.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "por favor ingresa un numero valido");
            return -1;
        }
    }

    public int getSpawnTime(){
        try {
            return Integer.parseInt(ufoSpawnTime.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "por favor ingresa un numero valido");
            return -1;
        }
    }

    public int getSpeed(){
        try {
            return Integer.parseInt(speedField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ingresa un numero valido");
            return 50;
        }
    }
    /* 
    public int getSelectedUfoIndex(){
        return ufoSelector.getSelectedIndex();
    }
    */

    public UFO getSelecteUFO(){
        return selectedUFO;
    }

    /* 
    private void selectImage(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage ufoImage = ImageIO.read(selectedFile);
                ufoPanel.setUfoImage(ufoImage);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "error al cargar");
            }
        }
    }
    */
}
