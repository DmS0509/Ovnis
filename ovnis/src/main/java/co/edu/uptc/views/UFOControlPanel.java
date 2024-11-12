package co.edu.uptc.views;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.models.UFO;

public class UFOControlPanel extends JPanel {

    private JTextField ufoCountField;
    private JTextField spawnTimeField;
    private JTextField speedField;
    private JButton imageButton;
    private JButton setDestinationButton;
    private JCheckBox showTrajectoryCheckBox;
    private JLabel ufosInScreenLabel;
    private JLabel ufosCrashedLabel;

    public UFOControlPanel(ActionListener updateListener, ActionListener spawnListener, ActionListener speedListener,
            ActionListener imageListener, ItemListener showTrajectoryListener, ArrayList<UFO> ufos) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Numero de Ovnis"));
        ufoCountField = new JTextField("10", 2);
        add(ufoCountField);

        add(Box.createVerticalStrut(10));

        add(new JLabel("Tiempo de aparicion(ms): "));
        spawnTimeField = new JTextField("1000", 2);
        add(spawnTimeField);

        add(Box.createVerticalStrut(10));

        add(new JLabel("Velocidad(ms): "));
        speedField = new JTextField("30", 2);
        add(speedField);

        add(Box.createVerticalStrut(10));

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(updateListener);
        add(updateButton);

        add(Box.createVerticalStrut(10));

        JButton spawnButton = new JButton("Configurar Tiempo");
        spawnButton.addActionListener(spawnListener);
        add(spawnButton);

        add(Box.createVerticalStrut(10));

        JButton speedButton = new JButton("Configurar Velocidad");
        speedButton.addActionListener(speedListener);
        add(speedButton);

        add(Box.createVerticalStrut(10));

        imageButton = new JButton("Seleccionar Imagen");
        imageButton.addActionListener(imageListener);
        add(imageButton);

        add(Box.createVerticalStrut(10));

        setDestinationButton = new JButton("Establecer Destino");
        setDestinationButton.addActionListener(e -> {
            int destX = Integer.parseInt(JOptionPane.showInputDialog(this, "ingresa la coordenada en x del destino"));
            int destY = Integer.parseInt(JOptionPane.showInputDialog(this, "ingresa la coordenada en y del destino"));
            for (UFO ufo : ufos) {
                ufo.setDestination(destX, destY);
            }
        });
        add(setDestinationButton);

        add(Box.createVerticalStrut(10));

        showTrajectoryCheckBox = new JCheckBox("Mostrar Trayectoria");
        showTrajectoryCheckBox.setSelected(true);
        showTrajectoryCheckBox.addItemListener(showTrajectoryListener);
        add(showTrajectoryCheckBox);

        add(Box.createVerticalStrut(10));

        ufosInScreenLabel = new JLabel("Ovnis en pantalla: 0");
        add(ufosInScreenLabel);

        ufosCrashedLabel = new JLabel("ovnis estrellados: 0");
        add(ufosCrashedLabel);
    }

    public int getNumOfUFOs() {
        try {
            return Integer.parseInt(ufoCountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ingresa un numero valido");
            return -1;
        }
    }

    public int getSpawnTime() {
        try {
            return Integer.parseInt(spawnTimeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un tiempo válido.");
            return 1000;
        }
    }

    public int getSpeed() {
        try {
            return Integer.parseInt(speedField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa una velocidad válida.");
            return 30;
        }
    }

    public void setUFOsInScreenCount(int count) {
        ufosInScreenLabel.setText("OVNIs en pantalla: " + count);
    }

    public void setUFOsCrashedCount(int count) {
        ufosCrashedLabel.setText("OVNIs estrellados: " + count);
    }
}
