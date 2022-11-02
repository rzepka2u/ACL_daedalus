package model.cases;

import model.objets.Tresor;

/**
 * Classe CaseTresor qui représente une case contenant un trésor
 */
public class CaseTresor implements Case {
    
    private boolean ouvert; // Booléen pour savoir si le trésor est ouvert ou non
    private Tresor contenu; // Le contenu du trésor (Potion, Arme ou PieceArmure)

    /**
     * Unique constructeur de la classe CaseTresor 
     * @param t le contenu du trésor (Potion, Arme ou PieceArmure)
     */
    public CaseTresor(Tresor t){
        ouvert = false;
        contenu = t;
    }

    /**
     * Getter sur l'attribut ouvert
     * @return la valeur booléene de l'attribut ouvert
     */
    public boolean getOuvert(){
        return ouvert;
    }

    /** 
     * Getter sur l'attribut contenu
     * @return la valeur de l'attribut contenu (type: Tresor)
     */
    public Tresor getContenu(){
        return contenu;
    }

    /**
     * Méthode qui défini si la case est traversable pour le joueur
     * @return false ou true suivant si le coffre est ouvert ou non 
     */
    public boolean estTraversable(){
        if(ouvert){
            return true;
        } else {
            return false;
        }
    }
}
