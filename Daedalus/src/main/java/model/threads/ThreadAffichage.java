package model.threads;

import model.ihm.FenetreGraphique;

/**
 * Classe représentant un thread dont le but est de rafraîchir régulièrement l'affichage de la partie
 */

public class ThreadAffichage extends Thread{

    // La fenètre graphique sur laquelle il doit rafraîchir l'information
    private FenetreGraphique fenetre;
    private boolean stop;

    /**
     * Constructeur par initialisation 
     * @param f objet FenetreGraphique qui doit être stoqué dans l'attribut
     */
    public ThreadAffichage(FenetreGraphique f){
        super();
        fenetre = f;
        stop = false;
    }

    /**
     * Méthode contenant le code d'exécution du thread
     */
    @Override
    public void run(){
        
        while(!Thread.interrupted()){
            
            // Attente de 300 milisecondes
            try{
                sleep(300);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }

            synchronized(fenetre.getVerrouContent()){
                // Rafraîchissent du panel de partie dans la fenêtre
                fenetre.raffraichirPartie();
            }
            
        }
    }

    public void arret(){
        stop=true;
    }
}
