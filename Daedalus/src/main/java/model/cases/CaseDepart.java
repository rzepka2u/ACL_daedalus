package model.cases;

/**
 * Représente la case de départ dans le labyrinthe
 */
public class CaseDepart extends Case {

    /**
     * id de la case vide (utile pour la génération aléatoire)
     */
    private int id;

    /**
     * charactere qui représente la case
     */
    private char c;

    /**
     * constructeur de CaseDepart
     *
     * @param id    id
     * @param coord coordonnee de la case
     */
    public CaseDepart(int id, Coordonnee coord) {

        // Appelle du constructeur de la classe mère
        super(coord.getX(), coord.getY());

        // Initialisation des attributs
        this.id = id;
        this.c = 'D';
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
     * methode get type (ici CaseDepart)
     * @return "CaseDepart"
     */
    @Override
    public String getType() {
        return "CaseDepart";
    }

    /**
     * Définit les cases départ comme traversable
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
     * methode get caractere
     * @return this.c
     */
    @Override
    public char getChar() {
        return this.c;
    }
}