package model.cases;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represente une case dans le labyrinthe
 */
public abstract class Case implements Serializable {

    /**
     * coordonnee de la case
     */
    private Coordonnee coord;

    /**
     * constructeur de case
     *
     * @param x abscisse x de la case
     * @param y ordonnee y de la case
     */
    public Case(int x, int y) {
        this.coord = new Coordonnee(x, y);
    }

    /**
     * methode donne id
     *
     * @return id
     */
    public abstract int getId();

    /**
     * methode change id
     *
     * @param a id
     */
    public abstract void setId(int a);

    /**
     * methode donne type
     *
     * @return type
     */
    public abstract String getType();

    /**
     * methode donne abscisse X
     *
     * @return X
     */
    public int getX() {
        return coord.getX();
    }

    /**
     * methode donne ordonnee Y
     *
     * @return Y
     */
    public int getY() {
        return coord.getY();
    }

    /**
     * methode donne charactere
     *
     * @return charactere
     */
    public abstract char getChar();

    /**
     * methode change charactere
     *
     * @param a charactere
     */
    public abstract void setChar(char a);

    /**
     * Définit si une case est traversable ou non
     *
     * @return true si la case est traversable, false sinon
     */
    public abstract boolean estTraversable();


    /**
     * méthode equals
     *
     * @param o object à vérifier
     * @return true si c'est égal
     */
    @Override
    public boolean equals(Object o) {
        // Si la référence est identique, les objets sont égaux
        if (this == o) return true;
        
        // Sinon, si o est null, ou que la classe de this est différente de la classe de o, les objets ne sont pas égaux.
        if (o == null || getClass() != o.getClass()) return false;

        // On convertit l'objet o en Case
        Case aCase = (Case) o;

        // Les objets sont égaux seulement si leurs coordonées sont égales.
        return Objects.equals(coord, aCase.coord);
    }

    /**
     * methode hasCode
     *
     * @return le hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(coord);
    }
}