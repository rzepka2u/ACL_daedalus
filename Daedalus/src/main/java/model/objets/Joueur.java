package model.objets;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;

import model.enums.Direction;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur extends Entite {

    // indique où regarde le joueur (vers le haut, bas,..)
    private ArrayList<Potion> inventaire;

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
        this.inventaire = new ArrayList<Potion>();
        this.competences = new ArrayList<Competence>();
        this.setArme(new Arme());
    }

    public ArrayList<Potion> getInventaire(){
        return inventaire;
    }

    public void ajouterPotion() {
        if(this.inventaire.size() < 5) {
            this.inventaire.add(new Potion(10));
        }
    }

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
                                    if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i) {
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
                                    else if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i) {
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
                            if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i) {
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

    public void lancerCompetence(int num) {
        // TODO : ajouter if sur le rang
        if(this.competences.get(num).isActivable() && num < this.competences.size()) {
            switch(this.competences.get(num).getType()) {
                case BERSERKER:
                    int dgts = this.getArme().getDegats();
                    this.prendreDegat(20);
                    this.getArme().setDegats(dgts + dgts/2);
                    this.competences.get(num).setActivable(false);
                    Timer t = new Timer();
                    t.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    getArme().setDegats(dgts);
                                    competences.get(num).setActivable(true);
                                    // close the thread
                                    t.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                    break;

                case BOUCLIER_MAGIQUE :
                    this.setPointsArmure(this.getPointsArmure()+25);
                    this.competences.get(num).setActivable(false);
                    break;

                case DRAIN_VIE:
                    this.drain = true;
                    this.competences.get(num).setActivable(false);
                    Timer t1 = new Timer();
                    t1.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    drain = false;
                                    competences.get(num).setActivable(true);
                                    // close the thread
                                    t1.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                    break;

                case REVENANT:
                    this.revenant = true;
                    this.competences.get(num).setActivable(false);
                    break;
            }
        }
    }

    public ArrayList<Competence> getCompetences() {
        return competences;
    }

    public void ajouterCompetence(Competence c) {
        this.competences.add(c);
    }

    public boolean isRevenant() {
        return revenant;
    }

    public void setRevenant(boolean revenant) {
        this.revenant = revenant;
    }

    public void seSoigner(int pv) {
        if(this.getPointsVie()+pv > NB_PV_START) {
            this.setPointsVie(NB_PV_START);
        } else {
            this.setPointsVie(this.getPointsVie()+pv);
        }
    }
    @Override
    public String toString(){
        return "J";
    }
}