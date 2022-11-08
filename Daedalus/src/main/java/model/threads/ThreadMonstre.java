package model.threads;

import model.objets.Entite;
import model.objets.Fantome;
import model.objets.Gobelin;
import model.objets.Jeu;
import model.objets.Labyrinthe;

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

    /*public void run(){
        int px,py;
        px = 0;
        py = 0;
        Entite m =  jeu.getEntites().get(positionInList+1);
        Random r = new Random();
        int random = r.nextInt(4);
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
            m.seDeplacer(m.getX() + px, m.getY() + py);
        } else {
            if(jeu.validerDeplacement(m.getX() + px, m.getY() + py)) {
                m.seDeplacer(m.getX() + px, m.getY() + py);
            }
        }

        try{
            sleep(750);
        } catch (InterruptedException e){
            System.out.println("SLEEP INTERROMPU");
        }
    }*/
    
}
