package co.edu.uptc.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;

public class UFO extends Thread implements ModelInterface {

    private int coordenateX, coordenateY;
    private int maxCoordenateX, maxCoordenateY;
    private int destinationInX, destinationInY;
    private int dx, dy;
    private boolean running;
    private int speed;
    private Image image;
    private ArrayList<Point> trajectory;
    private int trajectoryIndex;
    private boolean crashed;
    private boolean reachedDestination;
    private String imagePath;

    private PresenterInterface presenter;

    public UFO(int maxCoordenateX, int maxCoordenateY, String imagePath, int destinationInX, int destinationInY) {
        this.maxCoordenateX = maxCoordenateX;
        this.maxCoordenateY = maxCoordenateY;
        this.destinationInX = destinationInX;
        this.destinationInY = destinationInY;
        this.image = loadImage(imagePath, 120, 80);
        this.speed = 25;
        this.trajectory = new ArrayList<>();
        this.trajectoryIndex = 0;
        this.crashed = false;
        this.imagePath = imagePath;
        spawn();
        setRandomDirection();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            move();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                running = false;
            }
            if (destinationInX != -1 && destinationInY != -1 && hasReachedDestination()) {
                running = false;
            }
        }
    }

    @Override
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    private boolean hasReachedDestination() {
        return Math.abs(coordenateX - destinationInX) < 5 && Math.abs(coordenateY - destinationInY) < 5;
    }

    public boolean hasReachedDefaultDestination(Point destination) {
        return Math.abs(coordenateX - destination.x) < 5 && Math.abs(coordenateY - destination.y) < 5;
    }

    public void move() {
        if (!crashed && !reachedDestination) {
            if (!trajectory.isEmpty()) {
                Point nextPoint = trajectory.get(trajectoryIndex);
                int deltaX = nextPoint.x - coordenateX;
                int deltaY = nextPoint.y - coordenateY;
                double fraction = 0.4;
                coordenateX += deltaX * fraction;
                coordenateY += deltaY * fraction;

                if (Math.abs(deltaX) < 5 && Math.abs(deltaY) < 5) {
                    trajectoryIndex++;
                    if (trajectoryIndex >= trajectory.size()) {
                        trajectory.clear();
                        setRandomDirection();
                    }
                }
            } else {

                if (destinationInX != -1 && destinationInY != -1) {
                    int deltaX = destinationInX - coordenateX;
                    int deltaY = destinationInY - coordenateY;
                    double fraction = 0.4;
                    coordenateX += deltaX * fraction;
                    coordenateY += deltaY * fraction;

                    if (Math.abs(deltaX) < 5 && Math.abs(deltaY) < 5) {
                        reachedDestination = true;
                        running = false;
                    }
                } else {
                    coordenateX += dx;
                    coordenateY += dy;

                    if (coordenateX < 0 || coordenateX > maxCoordenateX || coordenateY < 0
                            || coordenateY > maxCoordenateY) {
                        crashed = true;
                        running = false;
                    }
                    validateCoordenates();
                    if (Math.random() < 0.1) {
                        setRandomDirection();
                    }

                    if (hasReachedDefaultDestination(new Point(maxCoordenateX / 2, maxCoordenateY / 2))) {
                        stopMoving();
                    }
                }

            }
        }
    }

    private void validateCoordenates() {
        if (coordenateX < 0) {
            coordenateX = 0;
            dx = -dx;
        }
        if (coordenateX > maxCoordenateX) {
            coordenateX = maxCoordenateX;
            dx = -dx;
        }
        if (coordenateY < 0) {
            coordenateY = 0;
            dy = -dy;
        }
        if (coordenateY > maxCoordenateY) {
            coordenateY = maxCoordenateY;
            dy = -dy;
        }
    }

    private void setRandomDirection() {
        dx = (int) (Math.random() * 12 - 6);
        dy = (int) (Math.random() * 12 - 6);
    }

    public void setDestination(int destinationInX, int destinationInY) {
        this.destinationInX = destinationInX;
        this.destinationInY = destinationInY;
    }

    public void addTrajectoryPoint(int x, int y) {
        this.trajectory.add(new Point(x, y));
    }

    public void setTrajectory(ArrayList<Point> trajectory) {
        this.trajectory = new ArrayList<>(trajectory);
        this.trajectoryIndex = 0;
    }

    private Image loadImage(String path, int width, int height) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource != null) {
            ImageIcon icon = new ImageIcon(resource);
            Image originalImage = icon.getImage();
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return resizedImage;
        } else {
            System.err.println("no hay imagen wey ; ;: " + path);
            return createPlaceHolderImage(width, height);
        }
    }

    public void spawn() {
        coordenateX = (int) (Math.random() * maxCoordenateX);
        coordenateY = (int) (Math.random() * maxCoordenateY);
    }

    public int getCoordenateX() {
        return coordenateX;
    }

    public int getCoordenateY() {
        return coordenateY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void stopMoving() {
        running = false;
    }

    public Image getImage() {
        return image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImage(String imagePath) {
        Image img = loadImage(imagePath, 80, 80);
        if (img != null) {
            this.image = img;
        } else {
            System.err.println("usando marcador de posicion para la imagen: " + imagePath);
            this.image = createPlaceHolderImage(50, 50);
        }
    }

    private Image createPlaceHolderImage(int width, int height) {
        BufferedImage placeHolder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeHolder.createGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString("No Image", 10, 25);
        g2d.dispose();
        return placeHolder;
    }

    public void setImage(Image image2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setImage'");
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCollision(boolean crashed) {
        this.crashed = crashed;
    }

    public boolean checkCollision(UFO other) {
        Rectangle thisBounds = new Rectangle(coordenateX, coordenateY, image.getWidth(null), image.getHeight(null));
        Rectangle otherBounds = new Rectangle(other.getCoordenateX(), other.getCoordenateY(),
                other.getImage().getWidth(null), other.getImage().getHeight(null));
        return thisBounds.intersects(otherBounds);
    }

}
