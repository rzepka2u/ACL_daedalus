package model.cases;

import model.objets.Coordonnee;

/**
 * Represente un mur dans le labyrinthe
 */
public class CaseMur extends Case {

    /**
     * id de la case (utile pour la génération aléatoire)
     */
    private int id;

    /**
     * charactere qui représente la case
     */
    private char c;

    /**
     * constructeur de CaseMur
     * @param id id
     * @param coord coordonnee de la case
     */
    public CaseMur(int id, Coordonnee coord) {
        super(coord.getX(), coord.getY());
        this.id = id;
        this.c = '#';

    }

    /**
     * methode get id
     * @return this.id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * methode set id
     * @param id id voulu
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }


    /**
     * methode get type (ici CaseMur)
     */
    @Override
    public String getType() {
        return "CaseMur";
    }

    /**
    * Définit les cases mur comme non traversable
    * @return un boolean toujours égal à false
    */
    public boolean estTraversable(){
        return false;
    }

    /**
     * methode pour changer provisoirement le caractère associé à la case (pour la génération aléatoire)
     * @param a charactere
     */
    @Override
    public void setChar(char a) {
        this.c = a;
    }

    /**
     * methode get charactere
     */
    @Override
    public char getChar() {
        return this.c;
    }

}
