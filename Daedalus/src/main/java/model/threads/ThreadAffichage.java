package model.threads;

import model.ihm.FenetreGraphique;

public class ThreadAffichage extends Thread{
    
    private FenetreGraphique fenetre;

    public ThreadAffichage(FenetreGraphique f){
        fenetre = f;
    }

    @Override
    public void run(){
        while(true){
            try{
                sleep(300);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }
            
            fenetre.raffraichirPartie();
        }
    }
}
