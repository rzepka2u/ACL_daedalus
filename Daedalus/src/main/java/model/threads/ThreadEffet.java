package model.threads;

import model.objets.Jeu;
import model.objets.Joueur;

import java.io.Serializable;
import java.lang.Thread;

public class ThreadEffet extends Thread  implements Serializable {

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
                if(j.getPointsVie() <= 0){
                    jeu.mortJoueur(-1);
                }
            }

            try{
                sleep(2000);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }
        }
    }
    
}
