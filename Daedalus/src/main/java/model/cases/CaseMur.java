package model.cases;

/**
 * Represente un mur dans le labyrinthe
 */
public class CaseMur implements Case {

    /**
    * Définit les cases mur comme non traversable
    * @return un boolean toujours égal à false
    */
    public boolean estTraversable(){
        return false;
    }

    @Override
    public String toString(){
        return "#";
    }
}
