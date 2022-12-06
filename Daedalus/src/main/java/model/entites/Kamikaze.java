package model.entites;

import model.enums.Direction;
import model.enums.ZoneAttaque;
import model.tresors.Arme;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Kamikaze extends Entite{

    public Kamikaze(int x, int y){
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);
        setArme(new Arme("ceinture d'explosifs", 20, 2, ZoneAttaque.EN_CARRE));
    }
    
    public Kamikaze(int x, int y, int pv, int pa){
        super(x,y,pv,pa, Direction.BAS);
        setArme(new Arme("ceinture d'explosifs", 20, 2, ZoneAttaque.EN_CARRE));
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

        if(entitesTouchees.size()>0){
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/bombe.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception excep) { System.out.println(excep.getMessage()); }
        }

        return entitesTouchees;
        
    }
}