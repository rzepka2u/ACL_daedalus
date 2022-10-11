package model.objets;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur {

    /**
     * Coordonnees du joueur
     */
    private int x, y;

    /**
     * Constructeur de la classe Joueur 
     * @param px coordonnées en x
     * @param py coordonnées en y 
     */
    public Joueur(int px, int py) {
      this.x = px;
      this.y = py;
    }

    /**
     * Renvoit les coordonnees en x du joueur 
     * 
     * @return l'attribut x
     */
    public int getX() {
      return this.x;
    }

    /**
     * Renvoit les coordonnees en y du joueur
     * 
     * @return l'attribut y
     */
    public int getY() {
      return this.y;
    }

    /**
     * Definit les coordonnees en x du joueur
     * 
     * @param nx coordonnee en x
     */
    public void setX(int nx) {
      this.x = nx;
    }

    /**
     * Definit les coordonnees en y du joueur
     * 
     * @param ny coordonnee en y
     */
    public void setY(int ny) {
      this.y = ny;
    }

    /**
     * Modifie les coordonnees du joueur en tenant des comptes des parametres
     * 
     * @param px deplacement en x
     * @param py deplacement en y
     */
    public void seDeplacer(int px, int py) {
      this.x = px;
      this.y = py;
    }

    @Override
    public String toString(){
        return "J";
    }
}