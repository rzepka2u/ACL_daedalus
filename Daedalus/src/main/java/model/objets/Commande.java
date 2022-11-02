package model.objets;

import model.enums.Ordre;
import model.enums.Direction;

public class Commande {

    private Ordre ordre;
    private Direction sens;

    public Commande(Ordre ordre, Direction sens){
        this.ordre = ordre;
        this.sens = sens;
    }

    public Ordre getOrdre(){
        return ordre;
    }

    public Direction getDirection(){
        return sens;
    }
    
}
