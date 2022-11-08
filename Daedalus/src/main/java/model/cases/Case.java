package model.cases;

import model.objets.Coordonnee;

import java.util.Objects;

/**
 * Represente une case dans le labyrinthe
 */
public abstract class Case {

        /**
         * coordonnee de la case
         */
        private Coordonnee coord;

        /**
         * constructeur de case
         * @param x abscisse x de la case
         * @param y ordonnee y de la case
         */
        public Case(int x, int y) {
                this.coord = new Coordonnee(x, y);
        }

        /**
         * methode donne id
         * @return id
         */
        public abstract int getId();

        /**
         * methode change id
         * @param a id
         */
        public abstract void setId(int a);

        /**
         * methode donne type
         * @return type
         */
        public abstract String getType();

        /**
         * methode donne abscisse X
         * @return X
         */
        public int getX() {
                return coord.getX();
        }

        /**
         * methode donne ordonnee Y
         * @return Y
         */
        public int getY() {
                return coord.getY();
        }

        /**
         * methode donne charactere
         * @return charactere
         */
        public abstract char getChar();

        /**
         * methode change charactere
         * @param a charactere
         */
        public abstract void setChar(char a);

        /**
        * Définit si une case est traversable ou non
        * @return true si la case est traversable, false sinon
        */
        public abstract boolean estTraversable();


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Case aCase = (Case) o;
                return Objects.equals(coord, aCase.coord);
        }

        @Override
        public int hashCode() {
                return Objects.hash(coord);
        }
}