package model.threads;

import model.objets.Entite;
import model.objets.Fantome;
import model.objets.Jeu;


import java.lang.Thread;
import java.util.Random;

public class ThreadMonstre extends Thread {

    private int positionInList;
    private Jeu jeu;

    public ThreadMonstre(Jeu j, int pos){
        super();
        jeu = j;
        positionInList = pos;
    }

    public void run(){

        int px,py,random;
        Entite m;
        Random r = new Random();
        int hauteur = jeu.getLabyrinthe().getHauteur();
        int largeur = jeu.getLabyrinthe().getLargeur();

        while(true){

            m =  jeu.getEntites().get(positionInList+1);
            px = m.getX();
            py = m.getY();
            random = r.nextInt(4);
            random = random+1;

            switch(random) {
                case 1:
                    px += 1;
                break;
                case 2:
                    py += 1;
                break;
                case 3:
                    px += -1;
                break;
                case 4:
                    py += -1;
                break;
            }

            if(m instanceof Fantome) {
                if(px >= 0 && py >= 0 && px <= hauteur-1 && py <= largeur-1){
                    m.seDeplacer(px, py);
                }
            } else {
                if(jeu.validerDeplacement(px, py)) {
                    m.seDeplacer(px, py);
                }
            }

            try{
                sleep(1000);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }
        }
    }
}