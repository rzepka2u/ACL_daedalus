package model.objets;

import java.util.ArrayList;

public class Gobelin extends Entite{

    public Gobelin(int x, int y){
        super(x, y, NB_PV_START, NB_PA_START);
    }
    
    public Gobelin(int x, int y, int pv, int pa){
        super(x,y,pv,pa);
    }

    public void seDeplacer(int px, int py){
        this.setX(px);
        this.setY(py);
    }

    @Override
    public ArrayList<Entite> attaquer(ArrayList<Entite> entites) {
        // TODO Auto-generated method stub
        return null;
    }
}