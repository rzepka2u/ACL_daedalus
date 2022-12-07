package model.cases;

/**
 * Classe CaseEffet qui représente une case à effet
 */
public class CaseEffet extends Case {

    /**
     * Le nombre de points de vie à augmenter
     */
    private final int augmentationPV;
    /**
     * Le nombre de points de vie à diminuer
     */
    private final int diminutionPV;

    /**
     * Si la diminution / augmentation est progressive ou instantanée
     */
    private final boolean progressif;

    /**
     * id de la case (utile pour la génération aléatoire)
     */
    private int id;

    /**
     * caractere qui représente la case
     */
    private char c;

    /**
     * Constructeur par initialisation d'un objet CaseEffet
     *
     * @param id    id
     * @param coord coordonnee de la case
     * @param a     le nombre de points de vie à augmenter
     * @param d     le nombre de points de vie à diminuer
     * @param p     le boolean indiquant si l'effet est progressif ou non
     */
    public CaseEffet(int id, Coordonnee coord, int a, int d, boolean p) {

        // Appel du constructeur de la classe mère
        super(coord.getX(), coord.getY());

        // Initialisation des attributs
        this.id = id;
        this.augmentationPV = a;
        this.diminutionPV = d;
        this.c = '£';
        this.progressif = p;
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
     * methode get type (ici CaseEffet)
     * @return "CaseEffet"
     */
    @Override
    public String getType() {
        return "CaseEffet";
    }


    /**
     * Getter sur l'attribut augmentationPV
     *
     * @return la valeur entière de l'attribut augmentationPV
     */
    public int getAugmentation() {
        return augmentationPV;
    }

    /**
     * Getter sur l'attribut diminutionPV
     *
     * @return la valeur entière de l'attribut diminutionPV
     */
    public int getDiminutionPV() {
        return diminutionPV;
    }

    /**
     * Getter sur l'attribut progressif
     *
     * @return la valeur booléenne de l'attribut progressif
     */
    public boolean getProgressif() {
        return this.progressif;
    }

    /**
     * Méthode qui défini si la case est traversable pour le joueur
     *
     * @return toujours true
     */
    @Override
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
