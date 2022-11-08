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

            px = 0;
            py = 0;
            m =  jeu.getEntites().get(positionInList+1);
            random = r.nextInt(4);
            random = random+1;

            switch(random) {
                case 1:
                    px = 1;
                    py = 0;
                break;
                case 2:
                    px = 0;
                    py = 1;
                break;
                case 3:
                    px = -1;
                    py = 0;
                break;
                case 4:
                    px = 0;
                    py = -1;
                break;
            }

            if(m instanceof Fantome) {
                if(m.getX() >= 0 && m.getY() >= 0 && m.getX() <= hauteur-1 && m.getY() <= largeur-1){
                    m.seDeplacer(m.getX() + px, m.getY() + py);
                }
            } else {
                if(jeu.validerDeplacement(m.getX() + px, m.getY() + py)) {
                    m.seDeplacer(m.getX() + px, m.getY() + py);
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