package model.objets;

import model.enums.Ordre;
import model.enums.Direction;

import java.io.Serializable;

public class Commande implements Serializable {

    private Ordre ordre;
    private Direction sens;
    private int indice; 

    public Commande(Ordre ordre, Direction sens){
        this.ordre = ordre;
        this.sens = sens;
        this.indice = -1;
    }

    public Commande(Ordre ordre, int indice){
        this.ordre = ordre;
        this.indice = indice;
        this.sens = null;
    }

    public Ordre getOrdre(){
        return ordre;
    }

    public Direction getDirection(){
        return sens;
    }

    public int getIndice(){
        return indice;
    }
    
}
