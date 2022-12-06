package model.entites;

import model.enums.Direction;
import model.enums.ZoneAttaque;
import model.tresors.Arme;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Archer extends Entite{

    public Archer(int x, int y){
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);
        setArme(new Arme("arc en bois", 8, 5, ZoneAttaque.CASE_DEVANT));
    }
    
    public Archer(int x, int y, int pv, int pa){
        super(x,y,pv,pa, Direction.BAS);
        setArme(new Arme("arc en bois", 8, 5, ZoneAttaque.CASE_DEVANT));
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

        if(entitesTouchees.size()>0){
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/arc.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception excep) { System.out.println(excep.getMessage()); }
        }

        return entitesTouchees;
        
    }
}