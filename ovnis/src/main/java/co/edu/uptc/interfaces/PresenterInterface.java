package co.edu.uptc.interfaces;

public interface PresenterInterface {
    
    void setView(ViewInterface view);
    void setModel(ModelInterface model);

    void spawnUFO();
    void checkCollisions();
    void updateUFOS();

}
