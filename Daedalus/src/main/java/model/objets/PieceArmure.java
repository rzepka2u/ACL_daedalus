package model.objets;

public class PieceArmure implements Tresor {


    /**
     * la protection qu'elle offre
     */
    private int pointsArmure;

    /**
     * Constructeur principal
     * @param pointsArmure le nombre le nombre points d'armure qui seront ajoutés par cette armure
     */
    public PieceArmure(int pointsArmure) {
        this.pointsArmure = pointsArmure;
    }

    /**
     * getteur pointsArmure
     * @return le nombre le nombre points d'armure ajoutés
     */
    public int getPointsArmure() {
        return this.pointsArmure;
    }

    /**
     * methode toString
     * @return string
     */
    @Override
    public String toString() {
        return "W";
    }
}
