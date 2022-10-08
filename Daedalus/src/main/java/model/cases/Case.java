package model.cases;

/**
 * Represente une case dans le labyrinthe
 */
public interface Case {

        /**
        * Définit si une case est traversable ou non
        * @return true si la case est traversable, false sinon
        */
        public boolean estTraversable();

        public String toString();
}