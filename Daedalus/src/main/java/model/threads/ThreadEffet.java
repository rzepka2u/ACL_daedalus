package model.threads;

import model.objets.Jeu;
import model.objets.Joueur;

import java.lang.Thread;

public class ThreadEffet extends Thread {

    private Jeu jeu;
    private int augmentation;
    private int diminution;
    private int nb_coups;

    public ThreadEffet(Jeu j, int aug, int dim, int nbc){
        super();
        jeu = j;
        augmentation = aug;
        diminution = dim;
        nb_coups = nbc;
    }

    public void run(){
        for(int i = 0; i <= nb_coups-1; i++) {

            synchronized(jeu.getVerrousEntites().get(0)){
                Joueur j = jeu.getJoueur();
                j.modifierPV(augmentation);
                j.modifierPV(-diminution);
            }

            try{
                sleep(300);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }
        }
    }
    
}
