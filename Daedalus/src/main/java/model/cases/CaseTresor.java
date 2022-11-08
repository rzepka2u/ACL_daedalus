package model.cases;

import model.objets.Coordonnee;
import model.objets.Potion;
import model.objets.Tresor;

/**
 * Classe CaseTresor qui représente une case contenant un trésor
 */
public class CaseTresor extends Case {
    
    private boolean ouvert; // Booléen pour savoir si le trésor est ouvert ou non
    private Tresor contenu; // Le contenu du trésor (Potion, Arme ou PieceArmure)

    /**
     * id de la case (utile pour la génération aléatoire)
     */
    private int id;

    /**
     * charactere qui représente la case
     */
    private char c;

    /**
     * Unique constructeur de la classe CaseTresor
     * @param id id
     * @param coord coordonnee de la case
     * @param t le contenu du trésor (Potion, Arme ou PieceArmure)
     */


    public CaseTresor(int id, Coordonnee coord, Tresor t) {
        super(coord.getX(), coord.getY());
        this.id = id;
        this.c = '?';
        ouvert = false;
        contenu = t;
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
     * methode get type (ici CaseTresor)
     */
    @Override
    public String getType() {
        return "CaseTresor";
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
     * Méthode qui permet d'ouvrir un coffre à trésor
     */
    public void ouvrirTresor() {
        this.ouvert = true;
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

    public void setContenu(Tresor contenu) {
        this.contenu = contenu;
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
