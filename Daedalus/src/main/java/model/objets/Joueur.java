package model.objets;

import java.lang.ProcessBuilder.Redirect.Type;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;

import model.enums.Direction;
import model.enums.TypeCompetence;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur extends Entite {

    // Nombre de pa maximum du joueur évoluant au fil de la partie et des rangs gagnés
    protected static int NB_PA_MAX = 50;
    // Nombre d'objets maximum que l'inventaire peut contenir 
    protected static int TAILLE_INVENTAIRE = 5;

    // indique où regarde le joueur (vers le haut, bas,..)
    private ArrayList<Potion> inventaire;

    // points d'expériences du joueur 
    private double experience;
    // niveau du joueur : augmente avec l'expérience
    private int rang;

    // Liste des compétences du personnage
    private ArrayList<Competence> competences;

    private boolean drain = false;

    private boolean revenant = false;

    /**
     * Constructeur de la classe Joueur 
     * @param px coordonnées en x
     * @param py coordonnées en y 
     * @return l'objet Joueur correctement instancié
     */
    public Joueur(int px, int py) {
        super(px, py, NB_PV_START, NB_PA_START, Direction.BAS);
        inventaire = new ArrayList<Potion>();
        competences = new ArrayList<Competence>();
        this.setArme(new Arme());
    }

    public ArrayList<Potion> getInventaire(){
        return inventaire;
    }

    public double getExperience(){
        return experience;
    }

    public int getRang(){
        return rang;
    }

    public int getNB_PV_MAX(){
        return NB_PV_MAX;
    }

    /**
     * Méthode permattant d'ajouter une potion dans l'inventaire du joueur en respectant la taille limite de son inventaire
     */
    public void ajouterPotion() {
        if(this.inventaire.size() < TAILLE_INVENTAIRE) {
            this.inventaire.add(new Potion(10));
        }
    }

    /**
     * Méthode permettant au joueur de boire une potion et de récupérer un certain montant de points de vie 
     */
    public void boirePotion() {
        if(!this.inventaire.isEmpty()) {
            this.seSoigner(this.inventaire.get(0).getAugmentation());
            this.inventaire.remove(0);
        }
    }

    /**
     * Modifie les coordonnees du joueur en tenant des comptes des parametres
     * 
     * @param px deplacement en x
     * @param py deplacement en y
     */
    public void seDeplacer(int px, int py) {
      this.setX(px);
      this.setY(py);
    }

    /**
     * Méthode permettant de calculer la quantité d'expérience nécessaire pour passer au rang suivant
     * en partant de 0 (n'est pas pris en compte le montant actuel d'expérience du joueur)
     * @return la quantité d'expérience nécessaire 
     */
    public double calculerRangSuivant() {
        double xp = 10000;
        for(int i = 1; i < this.rang; i++) {
            xp = xp*1.5;
        }
        return xp;
    }

    /**
     * Méthode permettant de faire gagner une certaine quantité d'expérience au joueur et lui 
     * faire éventuellement passer au rang suivant si le montant d'expérience est suffisant
     * Permet également au joueur d'acquérir les améliorations liées au passage au rang suivant 
     * @param xp : montant d'expérience gagné par le joueur
     */
    public void gagnerExperience(double xp) {
        this.experience += xp;
        if(this.experience >= calculerRangSuivant()) {
            this.experience = (this.experience + xp) - calculerRangSuivant();
            this.rang++;
            NB_PV_MAX += 10;
            this.seSoigner(10);
            NB_PA_MAX += 10;
            //TAILLE_INVENTAIRE += 1; 
            if(this.rang == 2) ajouterCompetence(new Competence(TypeCompetence.BERSERKER, 2, 10, 0));
            if(this.rang == 3) ajouterCompetence(new Competence(TypeCompetence.BOUCLIER_MAGIQUE, 3, 0, 2));
            if(this.rang == 4) ajouterCompetence(new Competence(TypeCompetence.DRAIN_VIE, 4, 10, 1));
            if(this.rang == 5) ajouterCompetence(new Competence(TypeCompetence.REVENANT, 5, 0, 0));
        }
    }

    @Override
    public ArrayList<Entite> attaquer(ArrayList<Entite> entites, ArrayList<Object> verrous) {
        ArrayList<Entite> entitesTouchees = new ArrayList<Entite>();
        int portee = this.getArme().getPortee();
        // on traite chaque cas de zone d'attaque
        switch (this.getArme().getZone()) {
            case CASE_DEVANT :
                switch (this.getRegard()) {
                    
                    case HAUT :
                        for(Entite ent : entites) {
                            // on teste si l'entité se situe sur les cases au dessus du joueur qui sont dans l'attaque
                            synchronized(verrous.get(entites.indexOf(ent)+1)){
                                for(int i = 1; i <= portee; i++) {
                                    
                                    if(ent.getX() == this.getX()-i && ent.getY() == this.getY()) {
                                        // si oui on l'ajoute aux entités touchées
                                        entitesTouchees.add(ent);
                                    }
                                }
                            }
                        }
                        break;

                    case BAS:
                        for(Entite ent : entites) {
                            synchronized(verrous.get(entites.indexOf(ent)+1)){
                                // on teste si l'entité se situe sur les cases en dessous du joueur qui sont dans l'attaque
                                for(int i = 1; i <= portee; i++) {
                                   
                                    if(ent.getX() == this.getX()+i && ent.getY() == this.getY()) {
                                        // si oui on l'ajoute aux entités touchées
                                        entitesTouchees.add(ent);
                                  
                                    }
                                }
                            }
                        }
                        break;

                    case DROITE:
                        for(Entite ent : entites) {
                            synchronized(verrous.get(entites.indexOf(ent)+1)){
                                // on teste si l'entité se situe sur les cases à droite du joueur qui sont dans l'attaque
                                for(int i = 1; i < portee; i++) {
                                   
                                    if(ent.getX() == this.getX() && ent.getY() == this.getY()+i) {  
                                        // si oui on l'ajoute aux entités touchées
                                        entitesTouchees.add(ent);
                             
                                    }
                                }
                            }
                        }
                        break;

                    case GAUCHE:
                        for(Entite ent : entites) {
                            synchronized(verrous.get(entites.indexOf(ent)+1)){
                                // on teste si l'entité se situe sur les cases à gauche du joueur qui sont dans l'attaque
                                for(int i = 1; i <= portee; i++) {
                             
                                    if(ent.getX() == this.getX() && ent.getY() == this.getY()-i) {
                                        // si oui on l'ajoute aux entités touchées
                                        entitesTouchees.add(ent);
                                    }
                                }
                            }
                        }
                        break;
                }
                break;

            case ARC_DE_CERCLE:
                switch (this.getRegard()) {
                    case HAUT :
                        for(Entite ent: entites) {
                            synchronized(verrous.get(entites.indexOf(ent))){
                                for(int i = 1; i <= portee; i++) {
                                    // l'entité se situe dans les cases en haut à gauche du joueur
                                    if(ent.getX() == this.getX()-i && ent.getY() == this.getY()-i) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases au dessus du joueur
                                    else if(ent.getX() == this.getX()-i && ent.getY() == this.getY()) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases en haut à droite du joueur
                                    else if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i) {
                                        entitesTouchees.add(ent);
                                    }
                                }
                            }
                        }
                        break;

                    case BAS:
                        for(Entite ent: entites) {
                            synchronized(verrous.get(entites.indexOf(ent))){
                                for (int i = 1; i <= portee; i++) {
                                    // l'entité se situe dans les cases en bas à gauche du joueur
                                    if(ent.getX() == this.getX()+i && ent.getY() == this.getY()-i) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases en dessous du joueur
                                    else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases en bas à droite du joueur
                                    else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()+i) {
                                        entitesTouchees.add(ent);
                                    }
                                }
                            }
                        }
                        break;

                    case DROITE:
                        for(Entite ent: entites) {
                            synchronized(verrous.get(entites.indexOf(ent))){
                                for (int i = 1; i <= portee; i++) {
                                    // l'entité se situe dans les cases en haut à gauche du joueur
                                    if(ent.getX() == this.getX()-i && ent.getY() == this.getY()-i) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases à gauche du joueur
                                    else if(ent.getX() == this.getX() && ent.getY() == this.getY()-i) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases en bas à gauche du joueur
                                    else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()-i) {
                                        entitesTouchees.add(ent);
                                    }
                                }
                            }
                        }
                        break;

                    case GAUCHE:
                        for(Entite ent: entites) {
                            synchronized(verrous.get(entites.indexOf(ent))){
                                for (int i = 1; i <= portee; i++) {
                                    // l'entité se situe dans les cases en haut à droite du joueur
                                    if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases à droite du joueur
                                    else if(ent.getX() == this.getX() && ent.getY() == this.getY()+i) {
                                        entitesTouchees.add(ent);
                                    }

                                    // l'entité se situe dans les cases en bas à droite du joueur
                                    else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()+i) {
                                        entitesTouchees.add(ent);
                                    }
                                }
                            
                            }
                        }
                        break;
                }
                break;

            case EN_CARRE:
                for(Entite ent : entites) {
                    synchronized(verrous.get(entites.indexOf(ent))){
                        for(int i = 1; i <= portee; i++) {
                            // l'entité se situe dans les cases au dessus du joueur
                            if(ent.getX() == this.getX()-i && ent.getY() == this.getY()) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases en dessous du joueur
                            if(ent.getX() == this.getX()+i && ent.getY() == this.getY()) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases à gauche du joueur
                            if(ent.getX() == this.getX() && ent.getY() == this.getY()-i) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases à droite du joueur
                            if(ent.getX() == this.getX() && ent.getY() == this.getY()+i) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases en haut à droite du joueur
                            if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases en bas à droite du joueur
                            if(ent.getX() == this.getX()+i && ent.getY() == this.getY()+i) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases en haut à gauche du joueur
                            if(ent.getX() == this.getX()-i && ent.getY() == this.getY()-i) {
                                entitesTouchees.add(ent);
                            }

                            // l'entité se situe dans les cases en bas à gauche du joueur
                            if(ent.getX() == this.getX()+i && ent.getY() == this.getY()-i) {
                                entitesTouchees.add(ent);
                            }
                        }
                    }
                }
                break;
        }
        // si la compétence de drain de vie est activée on récupère des pv pour chaque attaque réussie
        if(!entitesTouchees.isEmpty() && this.drain) {
            for(Entite e : entitesTouchees) {
                this.seSoigner(this.getArme().getDegats());
            }
        }
        return entitesTouchees;
    }

    /**
     * Lance la compétence numéro num du joueur
     * @param num numéro de la compétence à effectuer
     */
    public void lancerCompetence(int num) {
        // TODO : ajouter if sur le rang
        // si la compétence est activable et si la compétence num est dans la liste des compétences débloquées
        if(this.competences.get(num).isActivable() && num < this.competences.size()) {
            // on regarde quelle est la compétence
            switch(this.competences.get(num).getType()) {
                // La compétence BERSERKER va infliger 20 PV au joueur et augmenter les dégâts de son arme de 50% pendant 10s
                case BERSERKER:
                    // On récupère les dégâts actuels de l'arme du joueur
                    int dgts = this.getArme().getDegats();
                    // On inflige 20 PV au joueur
                    this.prendreDegat(20);
                    // On augmente les dégâts de son arme de 50%
                    this.getArme().setDegats(dgts + dgts/2);
                    // La compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    Timer t = new Timer();
                    t.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Au bout de 10s on rétablit les dégâts initiaux de l'arme
                                    // et la compétence est de nouveau disponible
                                    getArme().setDegats(dgts);
                                    competences.get(num).setActivable(true);
                                    // On coupe le thread du timer
                                    t.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                    break;

                // La compétence BOUCLIER MAGIQUE octroie 25 points d'armure supplémentaires au joueur
                // cependant ils ne pourront pas être récupérés après avoir subi des dégâts
                // La compétence peut être réactivées après deux niveaux du labyrinthe effectués
                case BOUCLIER_MAGIQUE :
                    // On augmente l'armure de 25 PA
                    this.setPointsArmure(this.getPointsArmure()+25);
                    // La compétence n'est plus disponible
                    this.competences.get(num).setActivable(false);
                    break;

                // La compétence DRAIN DE VIE va soigner le joueur le montant de dégâts qu'il a infligé pendant une période de 10s
                // Cette compétence peut s'activer une seule fois par niveau
                case DRAIN_VIE:
                    // On indique que le drain est activé ce qui va être testé dans attaquer()
                    this.drain = true;
                    // La compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    Timer t1 = new Timer();
                    t1.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Au bout de 10s, le drain n'est plus activé
                                    drain = false;
                                    // On ferme le thread du timer
                                    t1.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                    break;

                // La compétence REVENANT permet de revenir à la vie avec 20PV, cette compétence n'est utilisable qu'une fois par partie
                case REVENANT:
                    // On indique que la compétence est active pour ensuite la tester lors des scénarios de mort du joueur
                    this.revenant = true;
                    // la compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    break;
            }
        }
    }

    /**
     * Renvoie la liste des compétences débloquées par le joueur
     * @return l'attribut competences
     */
    public ArrayList<Competence> getCompetences() {
        return competences;
    }

    /**
     * Ajoute la compétence c à la liste des compétences débloquées par le joueur
     * @param c compétence qui vient d'être débloquée par le joueur
     */
    public void ajouterCompetence(Competence c) {
        this.competences.add(c);
    }

    /**
     * Renvoie si la compétence REVENANT est active ou non
     * @return l'attribut revenant
     */
    public boolean isRevenant() {
        return revenant;
    }

    /**
     * Définit si la compétence REVENANT est active ou non
     * @param revenant
     */
    public void setRevenant(boolean revenant) {
        this.revenant = revenant;
    }

    /**
     * Méthode qui soigne le joueur à hauteur de pv
     * @param pv le montant de soin à attribuer
     */
    public void seSoigner(int pv) {
        // Si l'ajout va dépasser le nombre max de PV
        if(this.getPointsVie()+pv > NB_PV_START) {
            this.setPointsVie(NB_PV_START);
        } else {
            this.setPointsVie(this.getPointsVie()+pv);
        }
    }


    /**
     * Méthode permettant au joueur de récupérer des points d'armure sans dépasser la limite de points d'armure
     * @param pa : montant de points d'armure à récupérer
     */
    public void recupererPA(int pa) {
        if(this.getPointsArmure()+pa > NB_PA_MAX) {
            this.setPointsArmure(NB_PA_MAX);
        } else {
            this.setPointsArmure(this.getPointsArmure()+pa);
        }
    }


    @Override
    public String toString(){
        return "J";
    }
}