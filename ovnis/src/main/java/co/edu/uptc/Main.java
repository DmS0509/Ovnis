package co.edu.uptc;

import javax.swing.SwingUtilities;

import co.edu.uptc.presenters.UFOApp;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UFOApp::new);    
    }
}