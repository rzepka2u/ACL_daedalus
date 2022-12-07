package model.cases;

/**
 * Represente une simple case sortie dans lr labyrinthe
 */
public class CaseSortie extends Case {

    /**
     * id de la case (utile pour la génération aléatoire)
     */
    private int id;

    /**
     * charactere qui représente la case
     */
    private char c;

    /**
     * constructeur de CaseSortie
     *
     * @param id    id
     * @param coord coordonnee de la case
     */
    public CaseSortie(int id, Coordonnee coord) {

        // Appel du constructeur de la classe mère
        super(coord.getX(), coord.getY());

        // Initialisation des attributs
        this.id = id;
        this.c = 'S';

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
     * methode get type (ici CaseSortie)
     * @return "CaseSortie"
     */
    @Override
    public String getType() {
        return "CaseSortie";
    }

    /**
     * Définit les cases sortie comme traversable
     *
     * @return un boolean toujours égal à true
     */
    public boolean estTraversable() {
        return true;
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
