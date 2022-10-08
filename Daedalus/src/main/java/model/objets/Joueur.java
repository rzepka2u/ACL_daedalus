package model.objets;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur {

    private int x; // La position horizontale du joueur
    private int y; // La position verticale du joueur

    public Joueur(){
        // TO DO
    }


    /**
     * Getter sur la position horizontale du joueur
     * @return l'attribut x
     */
    public int getX(){
        return this.x;
    }


    /**
     * Getter sur la position vertical du joueur
     * @return l'attribut y
     */
    public int getY(){
        return this.y;
    }
}