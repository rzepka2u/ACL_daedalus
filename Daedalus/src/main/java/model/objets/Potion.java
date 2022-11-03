package model.objets;

public class Potion implements Tresor{
    
    private int augmentationPV;

    public Potion(int aug){
        augmentationPV = aug;
    }

    public int getAugmentation(){
        return augmentationPV;
    }

    
}

