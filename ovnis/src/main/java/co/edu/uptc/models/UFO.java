package co.edu.uptc.models;

public class UFO extends Thread{
    
    private int coordenateX;
    private int coordenateY;
    private int maxCoordenateX;
    private int maxCoordenateY;
    private boolean running;

    public UFO(int maxCoordenateX, int maxCoordenateY){
        this.maxCoordenateX = maxCoordenateX;
        this.maxCoordenateY = maxCoordenateY;
        spawn();
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
        int deltaX = (int) (Math.random() * 10 - 5);
        int deltaY = (int) (Math.random() * 10 - 5);

        coordenateX += deltaX;
        coordenateY += deltaY;
        
        if(coordenateX < 0) coordenateX = 0;
        if(coordenateX > maxCoordenateX) coordenateX = maxCoordenateX;
        if(coordenateY < 0) coordenateY = 0;
        if(coordenateY > maxCoordenateY) coordenateY = maxCoordenateY;

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

    public void stopMoving(){
        running = false;
    }

}
