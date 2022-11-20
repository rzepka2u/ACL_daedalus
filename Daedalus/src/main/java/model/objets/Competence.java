package model.objets;

import model.enums.TypeCompetence;

public class Competence {
    // Type de compétence
    private TypeCompetence type;

    // Le rang nécessaire pour utiliser la compétence
    private int rangMin;

    // indique si la compétence est activable ou en recharge
    private boolean activable = true;

    // Le temps de recharge de la compétence
    private int tempsRecharge;

    // Indique le nombre de niveaux nécessaires avant de pouvoir réactiver la compétence
    private int dureeNiveauDeBase;

    // Indique le nombre de niveaux restants avant de pouvoir réactiver la compétence
    private int dureeNiveau = dureeNiveauDeBase;

    /**
     * Constructeur de Compétence
     * @param t le type de la compétence
     * @param rm le rang minimum de lvl pour pouvoir l'utiliser
     * @param tr le temps de recharge de la compétence
     * @param dn le nombre de niveaux nécessaires avant de pouvoir réactiver la compétence
     */
    public Competence(TypeCompetence t, int rm, int tr, int dn) {
        this.type = t;
        this.rangMin = rm;
        this.tempsRecharge = tr;
        this.dureeNiveauDeBase = dn;
    }

    /**
     * Renvoie le type de la compétence
     * @return l'attribut type
     */
    public TypeCompetence getType() {
        return type;
    }

    /**
     * Renvoie le rang nécessaire pour utiliser la compétence
     * @return l'attribut rangMin
     */
    public int getRangMin() {
        return rangMin;
    }

    /**
     * Renvoie si la compétence est activable ou non
     * @return l'attribut activable
     */
    public boolean isActivable() {
        return activable;
    }

    /**
     * Renvoie le temps de recharge de la compétence
     * @return l'attribut tempsRecharge
     */
    public int getTempsRecharge() {
        return tempsRecharge;
    }

    /**
     * Définit l'attribut activable
     * @param activable
     */
    public void setActivable(boolean activable) {
        this.activable = activable;
    }

    /**
     * Renvoie le nombre de niveaux nécessaires pour réactiver la compétence
     * @return
     */
    public int getDureeNiveauDeBase() {
        return dureeNiveauDeBase;
    }

    /**
     * Définit la durée de niveaux actuelle avant de réactiver la compétence
     * @param dureeNiveau
     */
    public void setDureeNiveau(int dureeNiveau) {
        this.dureeNiveau = dureeNiveau;
    }

    /**
     * Renvoie la durée de niveaux actuelle avant de réactiver la compétence
     * @return l'attribut dureeNiveau
     */
    public int getDureeNiveau() {
        return dureeNiveau;
    }

}
