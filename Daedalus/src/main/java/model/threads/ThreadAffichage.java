package model.threads;

import javax.swing.SwingUtilities;

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
        
        while(stop == false){
            
            // Attente de 300 milisecondes
            try{
                sleep(150);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }

            
                // Rafraîchissent du panel de partie dans la fenêtre
                fenetre.raffraichirPartie();
            
            
        }

        System.out.println("FIN AFFICHAGE");
        System.out.flush();
    }

    public void arret(){
        stop=true;
    }
}
