package co.edu.uptc.interfaces;

import java.util.ArrayList;

import co.edu.uptc.models.UFO;

public interface ViewInterface {

    void setPresenter(PresenterInterface presenter);

    void updateUfos(ArrayList<UFO> ufos);
    
}
