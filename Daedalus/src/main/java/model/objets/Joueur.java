package model.objets;

import java.util.ArrayList;

import model.enums.Direction;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur extends Entite{

    private static final int NB_PV_START = 100;
    private static final int NB_PA_START = 50;

    private Direction regard;
    private ArrayList<Potion> inventaire;

    /**
     * Constructeur de la classe Joueur 
     * @param px coordonnées en x
     * @param py coordonnées en y 
     * @return l'objet Joueur correctement instancié
     */
    public Joueur(int px, int py) {
        super(px, py, NB_PV_START, NB_PA_START);
        inventaire = new ArrayList<Potion>();
        regard = Direction.BAS;
    }

    public ArrayList<Potion> getInventaire(){
        return inventaire;
    }

    public Direction getRegard(){
        return regard;
    }

    public void setRegard(Direction sens){
        regard = sens;
    }

    /**
     * Modifie les coordonnees du joueur en tenant des comptes des parametres
     * 
     * @param px deplacement en x
     * @param py deplacement en y
     */
    public void seDeplacer(int px, int py) {
      this.setX(px);
      this.setY(py);
    }

    @Override
    public String toString(){
        return "J";
    }
}