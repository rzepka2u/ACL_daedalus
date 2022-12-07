package model.cases;

import model.tresors.Arme;
import model.tresors.Potion;
import model.tresors.Tresor;

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
     *
     * @param id    id
     * @param coord coordonnee de la case
     * @param t     le contenu du trésor (Potion, Arme ou PieceArmure)
     */
    public CaseTresor(int id, Coordonnee coord, Tresor t) {

        // Appel au constructeur de la classe mère
        super(coord.getX(), coord.getY());

        // Initialisation des attributs
        this.id = id;
        ouvert = false;
        contenu = t;

        // Si le trésor contient une arme, le caractère est A
        if (t instanceof Arme) this.c = 'A';

        // Sinon, si le trésor contient une potion, le caractère est P
        else if (t instanceof Potion) this.c = 'P';

        // Sinon, le caractère est W (pièce d'armure)
        else this.c = 'W';
    }

    /**
     * methode get id
     *
     * @return this.id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * methode set id
     *
     * @param id id voulu
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * methode get type (ici CaseTresor)
     * @return "CaseTresor"
     */
    @Override
    public String getType() {
        return "CaseTresor";
    }

    /**
     * Getter sur l'attribut ouvert
     *
     * @return la valeur booléene de l'attribut ouvert
     */
    public boolean getOuvert() {
        return ouvert;
    }

    /**
     * Getter sur l'attribut contenu
     *
     * @return la valeur de l'attribut contenu (type: Tresor)
     */
    public Tresor getContenu() {
        return contenu;
    }

    /**
     * Méthode qui permet d'ouvrir un coffre à trésor
     * @return true
     */
    public void ouvrirTresor() {
        this.ouvert = true;
    }

    /**
     * Méthode qui défini si la case est traversable pour le joueur
     *
     * @return false ou true suivant si le coffre est ouvert ou non
     */
    public boolean estTraversable() {
        return ouvert;
    }

    /**
     * Setter sur l'attribut contenu
     * @param contenu Le nouvel objet Tresor que contiendra la case
     */
    public void setContenu(Tresor contenu) {
        this.contenu = contenu;
    }

    /**
     * methode pour changer provisoirement le caractère associé à la case (pour la génération aléatoire)
     *
     * @param a charactere
     */
    @Override
    public void setChar(char a) {
        this.c = a;
    }

    /**
     * methode get charactere
     * @return this.c
     */
    @Override
    public char getChar() {
        return this.c;
    }
}
