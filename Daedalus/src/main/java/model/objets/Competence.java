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

    private int dureeNiveauDeBase;

    private int dureeNiveau = dureeNiveauDeBase;

    public Competence(TypeCompetence t, int rm, int tr, int dn) {
        this.type = t;
        this.rangMin = rm;
        this.tempsRecharge = tr;
        this.dureeNiveauDeBase = dn;
    }

    public TypeCompetence getType() {
        return type;
    }

    public int getRangMin() {
        return rangMin;
    }

    public boolean isActivable() {
        return activable;
    }

    public int getTempsRecharge() {
        return tempsRecharge;
    }

    public void setActivable(boolean activable) {
        this.activable = activable;
    }

    public int getDureeNiveauDeBase() {
        return dureeNiveauDeBase;
    }

    public void setDureeNiveau(int dureeNiveau) {
        this.dureeNiveau = dureeNiveau;
    }

    public int getDureeNiveau() {
        return dureeNiveau;
    }

}
