package co.edu.uptc.views;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.models.UFO;

public class UFOFrame extends JFrame{
    
    private UFOPanel ufoPanel;
    private JTextField ufoCount;

    public UFOFrame(ArrayList<UFO> ufos, ActionListener updateListener){
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

        add(controlPanel, BorderLayout.SOUTH);
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
}
