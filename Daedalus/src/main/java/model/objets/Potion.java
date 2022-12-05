package model.objets;

import java.io.Serializable;

public class Potion implements Tresor, Serializable {
    
    private int augmentationPV;

    public Potion(int aug){
        augmentationPV = aug;
    }

    public int getAugmentation(){
        return augmentationPV;
    }

    
}

