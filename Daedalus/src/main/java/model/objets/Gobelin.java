package model.objets;

import model.enums.Direction;
import java.util.ArrayList;

public class Gobelin extends Entite{

    public Gobelin(int x, int y){
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);
        setArme(new Arme());
    }
    
    public Gobelin(int x, int y, int pv, int pa){
        super(x,y,pv,pa, Direction.BAS);
        setArme(new Arme());
    }

    public void seDeplacer(int px, int py){
        this.setX(px);
        this.setY(py);
    }

    @Override
    public ArrayList<Entite> attaquer(ArrayList<Entite> entites, ArrayList<Object> verrous) {

        ArrayList<Entite> entitesTouchees = new ArrayList<Entite>();
        int portee = this.getArme().getPortee();
        Joueur j = (Joueur) entites.get(0);

        // on traite chaque cas de zone d'attaque
        switch (this.getArme().getZone()) {
            case CASE_DEVANT :
                switch (this.getRegard()) {
                    case HAUT :
                      
                        // on teste si l'entité se situe sur les cases au dessus du joueur qui sont dans l'attaque
                        for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                            if(j.getX() == this.getX()-i && j.getY() == this.getY()) {
                                    // si oui on l'ajoute aux entités touchées
                                    entitesTouchees.add(j);
                            }
                        }
                        break;

                    case BAS:
                        // on teste si l'entité se situe sur les cases en dessous du joueur qui sont dans l'attaque
                        for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                            if(j.getX() == this.getX()+i && j.getY() == this.getY()) {
                                // si oui on l'ajoute aux entités touchées
                                entitesTouchees.add(j);
                            }
                        }
                    
                        break;

                    case DROITE:
                        // on teste si l'entité se situe sur les cases à droite du joueur qui sont dans l'attaque
                        for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                            if(j.getX() == this.getX() && j.getY() == this.getY()+i) {
                                // si oui on l'ajoute aux entités touchées
                                entitesTouchees.add(j);
                            }
                        }
                    
                        break;

                    case GAUCHE:
                            
                        // on teste si l'entité se situe sur les cases à gauche du joueur qui sont dans l'attaque
                        for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                            if(j.getX() == this.getX() && j.getY() == this.getY()-i) {
                                // si oui on l'ajoute aux entités touchées
                                entitesTouchees.add(j);
                            }
                        }
                    
                        break;
                }
                break;

            case ARC_DE_CERCLE:
                switch (this.getRegard()) {
                    case HAUT :
                            for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                                // l'entité se situe dans les cases en haut à gauche du joueur
                                if(j.getX() == this.getX()-i && j.getY() == this.getY()-i) {
                                    entitesTouchees.add(j);
                                }

                                // l'entité se situe dans les cases au dessus du joueur
                                else if(j.getX() == this.getX()-i && j.getY() == this.getY()) {
                                    entitesTouchees.add(j);
                                }

                                // l'entité se situe dans les cases en haut à droite du joueur
                                else if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                                    entitesTouchees.add(j);
                                }
                            }
                        break;

                    case BAS:
                
                        for (int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                            // l'entité se situe dans les cases en bas à gauche du joueur
                            if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                                entitesTouchees.add(j);
                            }

                            // l'entité se situe dans les cases en dessous du joueur
                            else if(j.getX() == this.getX()+i && j.getY() == this.getY()) {
                                entitesTouchees.add(j);
                            }

                            // l'entité se situe dans les cases en bas à droite du joueur
                            else if(j.getX() == this.getX()+i && j.getY() == this.getY()+i) {
                                entitesTouchees.add(j);
                            }
                        }
        
                        break;

                    case DROITE:
                        for (int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                            // l'entité se situe dans les cases en haut à gauche du joueur
                            if(j.getX() == this.getX()-i && j.getY() == this.getY()-i) {
                                entitesTouchees.add(j);
                            }

                            // l'entité se situe dans les cases à gauche du joueur
                            else if(j.getX() == this.getX() && j.getY() == this.getY()-i) {
                                entitesTouchees.add(j);
                            }

                            // l'entité se situe dans les cases en bas à gauche du joueur
                            else if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                                entitesTouchees.add(j);
                            }
                        }
                        break;

                    case GAUCHE:
                   
                            for (int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                                // l'entité se situe dans les cases en haut à droite du joueur
                                if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                                    entitesTouchees.add(j);
                                }

                                // l'entité se situe dans les cases à droite du joueur
                                else if(j.getX() == this.getX() && j.getY() == this.getY()+i) {
                                    entitesTouchees.add(j);
                                }

                                // l'entité se situe dans les cases en bas à droite du joueur
                                else if(j.getX() == this.getX()+i && j.getY() == this.getY()+i) {
                                    entitesTouchees.add(j);
                                }
                            }
                        break;
                }
                break;

            case EN_CARRE:

                for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                    // l'entité se situe dans les cases au dessus du joueur
                    if(j.getX() == this.getX()-i && j.getY() == this.getY()) {
                        entitesTouchees.add(j);
                    }

                    // l'entité se situe dans les cases en dessous du joueur
                    if(j.getX() == this.getX()+i && j.getY() == this.getY()) {
                        entitesTouchees.add(j);
                    }

                    // l'entité se situe dans les cases à gauche du joueur
                    if(j.getX() == this.getX() && j.getY() == this.getY()-i) {
                        entitesTouchees.add(j);
                    }

                    // l'entité se situe dans les cases à droite du joueur
                    if(j.getX() == this.getX() && j.getY() == this.getY()+i) {
                        entitesTouchees.add(j);
                    }

                    // l'entité se situe dans les cases en haut à droite du joueur
                    if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                        entitesTouchees.add(j);
                    }

                    // l'entité se situe dans les cases en bas à droite du joueur
                    if(j.getX() == this.getX()+i && j.getY() == this.getY()+i) {
                        entitesTouchees.add(j);
                    }

                    // l'entité se situe dans les cases en haut à gauche du joueur
                    if(j.getX() == this.getX()-i && j.getY() == this.getY()-i) {
                        entitesTouchees.add(j);
                    }
                    
                    // l'entité se situe dans les cases en bas à gauche du joueur
                    if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                        entitesTouchees.add(j);
                    }
                }
                break;
        }
        return entitesTouchees;
        
    }
}