package model.cases;

/**
 * Classe CaseEffet qui représente une case à éffet
 */
public class CaseEffet implements Case {

    private int augmentationPV; // Le nombre de points de vie à augmenter
    private int diminutionPV; // Le nombre de points de vie à diminuer
    private boolean progressif; // Si la diminution / augmentation est progressive ou instantanée 

    /**
     * Constructeur par initialisation d'un objet CaseEffet
     * @param a le nombre de points de vie à augmenter
     * @param d le nombre de points de vie à diminuer
     * @param p le boolean indiquant si l'effet est progressif ou non 
     */
    public CaseEffet(int a, int d, boolean p){
        augmentationPV = a;
        diminutionPV = d;
        progressif = p;
    }

    /**
     * Getter sur l'attribut augmentationPV
     * @return la valeur entière de l'attribut augmentationPV
     */
    public int getAugmentation(){
        return augmentationPV;
    }

    /**
     * Getter sur l'attribut diminutionPV
     * @return la valeur entière de l'attribut diminutionPV
     */
    public int getDiminutionPV() {
        return diminutionPV;
    }

    /**
     * Getter sur l'attribut progressif 
     * @return la valeur booléenne de l'attribut progressif 
     */
    public boolean getProgressif() {
        return this.progressif;
    }

    /**
     * Méthode qui défini si la case est traversable pour le joueur
     * @return toujours true 
     */
    @Override
    public boolean estTraversable(){
        return true;
    }
}
