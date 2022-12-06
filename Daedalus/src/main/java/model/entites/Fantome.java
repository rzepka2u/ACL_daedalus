package model.entites;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import model.enums.Direction;
import model.objets.Arme;

public class Fantome extends Entite {
    
    /**
     * @param x
     * @param y
     */
    public Fantome(int x, int y){
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);
        setArme(new Arme());
    }

    public Fantome(int x, int y, int pv, int pa){
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
        Joueur j = (Joueur) entites.get(0);

        switch (this.getRegard()) {
            case HAUT :      
                if(j.getX() == this.getX()-1 && j.getY() == this.getY()) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }
            break;

            case BAS:
                if(j.getX() == this.getX()+1 && j.getY() == this.getY()) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }    
            break;

            case DROITE:        
                if(j.getX() == this.getX() && j.getY() == this.getY()+1) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }   
            break;

            case GAUCHE:
                if(j.getX() == this.getX() && j.getY() == this.getY()-1) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }
            break;
            
        }

        if(entitesTouchees.size() > 0){
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/fantome.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception excep) { System.out.println(excep.getMessage()); }
        }

        return entitesTouchees;
    }
}
