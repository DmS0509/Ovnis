package co.edu.uptc.models;

public class UFO {
    
    private int coordenateX;
    private int coordenateY;
    private int maxCoordenateX;
    private int maxCoordenateY;

    public UFO(int maxCoordenateX, int maxCoordenateY){
        coordenateX = (int)(Math.random() * 800);
        coordenateY = (int)(Math.random() * 600);
    }

    public void move(){
        coordenateX += (Math.random() * 10 - 5);
        coordenateY += (Math.random() * 10 - 5);
    }

    public void spawn(){
        coordenateX = (int)(Math.random() * maxCoordenateX);
        coordenateY = (int)(Math.random() * maxCoordenateY);
    }

    public int getCoordenateX() {
        return coordenateX;
    }

    public int getCoordenateY() {
        return coordenateY;
    }

}
