package co.edu.uptc.models;

public class UFO {
    
    private int coordenateX;
    private int coordenateY;

    public UFO(){
        coordenateX = (int)(Math.random() * 800);
        coordenateY = (int)(Math.random() * 600);
    }

    public void move(){
        coordenateX += (Math.random() * 10 - 5);
        coordenateY += (Math.random() * 10 - 5);
    }

    public int getCoordenateX() {
        return coordenateX;
    }

    public int getCoordenateY() {
        return coordenateY;
    }

}
