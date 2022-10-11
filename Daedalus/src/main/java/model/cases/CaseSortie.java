package model.cases;

/**
 * Represente une simple case vide dans la labyrinthe
 */
public class CaseSortie implements Case {


    /**
    * Définit les cases sortie comme traversable
    * @return un boolean toujours égal à true
    */
    public boolean estTraversable(){
        return true;
    }

    @Override
    public String toString(){
        return "S";
    }
}
