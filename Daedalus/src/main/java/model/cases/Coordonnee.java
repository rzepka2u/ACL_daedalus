package model.cases;

import java.io.Serializable;


/**
 * Represente les coordonnées d'une case
 */
public class Coordonnee implements Serializable {

    /**
     * Abscisse et ordonnée de la coordonnée
     */
    private int x, y;

    /**
     * Constructeur de coordonnée
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public Coordonnee(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * methode get x
     *
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * methode get y
     *
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * methode set x
     *
     * @param x x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * methode set y
     *
     * @param y y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * methode affichage de la coordonnée
     * @return Objet String représentant l'objet Coordonnee
     */
    public String toString() {
        return ("Coordonnée : (" + this.x + ", " + this.y + ").");
    }
}
