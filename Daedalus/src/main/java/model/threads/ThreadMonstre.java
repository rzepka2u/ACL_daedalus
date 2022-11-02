package model.threads;

import model.objets.Jeu;
import java.lang.Thread;

public class ThreadMonstre extends Thread {

    private int positionInList;
    private Jeu jeu;

    public ThreadMonstre(Jeu j, int pos){
        super();
        jeu = j;
        positionInList = pos;
    }

    public void run(){
        // TO DO: MAIN DU MONSTRE
    }
    
}
