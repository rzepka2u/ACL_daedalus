package model.cases;

/**
 * Classe CaseEffet qui représente une case à éffet
 */
public class CaseEffet implements Case {

    private int augmentationPV; // Le nombre de points de vie à augmenter
    private int diminutionPV; // Le nombre de points de vie à diminuer

    /**
     * Constructeur par initialisation d'un objet CaseEffet
     * @param a le nombre de points de vie à augmenter
     * @param d le nombre de points de vie à diminuer
     */
    public CaseEffet(int a, int d){
        augmentationPV = a;
        diminutionPV = d;
    }

    /**
     * Getter sur l'attribut augmentationPV
     * @return la valeur entière de l'attribut augmentationPV
     */
    public int getAugmentation(){
        return augmentationPV;
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
