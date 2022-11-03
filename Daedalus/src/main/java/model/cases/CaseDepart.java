package model.cases;

/**
 * Represente la case de départ dans la labyrinthe
 */
public class CaseDepart implements Case {


    /**
    * Définit les cases départ comme traversable
    * @return un boolean toujours égal à true
    */
    public boolean estTraversable(){
        return true;
    }

    @Override
    public String toString(){
        return "D";
    }
}