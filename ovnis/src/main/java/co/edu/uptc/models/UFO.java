package co.edu.uptc.models;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class UFO extends Thread{
    
    private int coordenateX, coordenateY;
    private int maxCoordenateX, maxCoordenateY;
    private int deltaX, deltaY;
    private boolean running;
    private int speed;
    private Image image;

    public UFO(int maxCoordenateX, int maxCoordenateY, String imagePath){
        this.maxCoordenateX = maxCoordenateX;
        this.maxCoordenateY = maxCoordenateY;
        this.image = loadImage(imagePath, 50, 50);
        this.speed = 50;
        spawn();
        setRandomDirection();
    }

    @Override
    public void run(){
        running = true;
        while (running) {
            move();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void move(){
        coordenateX += deltaX;
        coordenateY += deltaY;

        if (coordenateX < 0) {
            coordenateX = 0;
            deltaX =- deltaX;
        }
        if (coordenateX > maxCoordenateX){ 
            coordenateX = maxCoordenateX;
            deltaX =- deltaX;
        }
        if (coordenateY < 0) {
            coordenateY = 0;
            deltaY =- deltaY;
        }
        if (coordenateY > maxCoordenateY) {
            coordenateY = maxCoordenateY;
            deltaY =- deltaY;
        }
        if (Math.random() < 0.05) {
            setRandomDirection();
        }

    }

    private Image loadImage(String path, int width, int height){
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource != null) {
            ImageIcon icon = new ImageIcon(resource);
            Image originalImage = icon.getImage();
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return resizedImage;
        }else {
            System.err.println("no hay imagen wey ; ;: " + path);
            return null;
        }
    }

    public void spawn(){
        coordenateX = (int)(Math.random() * maxCoordenateX);
        coordenateY = (int)(Math.random() * maxCoordenateY);
    }

    private void setRandomDirection(){
        deltaX = (int) (Math.random() * 4 - 2);
        deltaY = (int) (Math.random() * 4 - 2);
    }

    public int getCoordenateX() {
        return coordenateX;
    }

    public int getCoordenateY() {
        return coordenateY;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void stopMoving(){
        running = false;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(String imagePath){
        this.image = loadImage(imagePath, 50, 50);
    }

}
