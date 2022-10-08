package model.cases;

/**
 * Represente une simple case vide dans la labyrinthe
 */
public class CaseVide implements Case {


    /**
    * Définit les cases vide comme traversable
    * @return un boolean toujours égal à true
    */
    public boolean estTraversable(){
        return true;
    }

    public String toString(){
        return ".";
    }
}
