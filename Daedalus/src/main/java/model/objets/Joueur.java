package model.objets;

import java.util.ArrayList;

import model.enums.Direction;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur extends Entite {

    private static final int NB_PV_START = 100;
    private static final int NB_PA_START = 50;

    // indique où regarde le joueur (vers le haut, bas,..)
    private Direction regard;
    private ArrayList<Potion> inventaire;

    /**
     * Constructeur de la classe Joueur 
     * @param px coordonnées en x
     * @param py coordonnées en y 
     * @return l'objet Joueur correctement instancié
     */
    public Joueur(int px, int py) {
        super(px, py, NB_PV_START, NB_PA_START);
        inventaire = new ArrayList<Potion>();
        regard = Direction.BAS;
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
            if(this.getPointsVie()+this.inventaire.get(0).getAugmentation() > NB_PV_START) {
                this.setPointsVie(NB_PV_START);
            } else {
                this.setPointsVie(this.getPointsVie()+this.inventaire.get(0).getAugmentation());
            }
            this.inventaire.remove(0);
        }
    }

    public Direction getRegard(){
        return regard;
    }

    public void setRegard(Direction sens){
        regard = sens;
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
    public ArrayList<Entite> attaquer(ArrayList<Entite> entites) {
        ArrayList<Entite> entitesTouchees = new ArrayList<Entite>();
        int portee = this.getArme().getPortee();
        // on traite chaque cas de zone d'attaque
        switch (this.getArme().getZone()) {
            case CASE_DEVANT :
                switch (this.regard) {
                    case HAUT :
                        for(Entite ent : entites) {
                            // on teste si l'entité se situe sur les cases au dessus du joueur qui sont dans l'attaque
                            for(int i = 1; i <= portee; i++) {
                                if(ent.getX() == this.getX()-i && ent.getY() == this.getY()) {
                                    // si oui on l'ajoute aux entités touchées
                                    entitesTouchees.add(ent);
                                }
                            }
                        }
                        break;

                    case BAS:
                        for(Entite ent : entites) {
                            // on teste si l'entité se situe sur les cases en dessous du joueur qui sont dans l'attaque
                            for(int i = 1; i <= portee; i++) {
                                if(ent.getX() == this.getX()+i && ent.getY() == this.getY()) {
                                    // si oui on l'ajoute aux entités touchées
                                    entitesTouchees.add(ent);
                                }
                            }
                        }
                        break;

                    case DROITE:
                        for(Entite ent : entites) {
                            // on teste si l'entité se situe sur les cases à droite du joueur qui sont dans l'attaque
                            for(int i = 1; i <= portee; i++) {
                                if(ent.getX() == this.getX() && ent.getY() == this.getY()-i) {
                                    // si oui on l'ajoute aux entités touchées
                                    entitesTouchees.add(ent);
                                }
                            }
                        }
                        break;

                    case GAUCHE:
                        for(Entite ent : entites) {
                            // on teste si l'entité se situe sur les cases à gauche du joueur qui sont dans l'attaque
                            for(int i = 1; i <= portee; i++) {
                                if(ent.getX() == this.getX() && ent.getY() == this.getY()+i) {
                                    // si oui on l'ajoute aux entités touchées
                                    entitesTouchees.add(ent);
                                }
                            }
                        }
                        break;
                }
                break;

            case ARC_DE_CERCLE:
                switch (this.regard) {
                    case HAUT :
                        for(Entite ent: entites) {
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
                        break;

                    case BAS:
                        for(Entite ent: entites) {
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
                        break;

                    case DROITE:
                        for(Entite ent: entites) {
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
                        break;

                    case GAUCHE:
                        for(Entite ent: entites) {
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
                        break;
                }
                break;

            case EN_CARRE:
                for(Entite ent : entites) {
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
                break;
        }
        return entitesTouchees;
    }


    @Override
    public String toString(){
        return "J";
    }
}