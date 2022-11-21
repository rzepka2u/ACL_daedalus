package model.threads;

import model.objets.Entite;
import model.objets.Fantome;
import model.objets.Gobelin;
import model.objets.Jeu;
import model.objets.Joueur;

import model.enums.Direction;

import java.lang.Thread;
import java.util.Random;

public class ThreadMonstre extends Thread {

    private int positionInList;
    private Jeu jeu;
    private boolean stop;

    public ThreadMonstre(Jeu j, int pos){
        super();
        jeu = j;
        positionInList = pos;
        stop = false;
    }

    public void run(){

        int px,py, pv, random;
        Direction sens;
        Entite m;
        Joueur j;
        Random r = new Random();
        int hauteur = jeu.getLabyrinthe().getHauteur();
        int largeur = jeu.getLabyrinthe().getLargeur();

        do {

            try{
                sleep(1000);
            } catch (InterruptedException e){
                System.out.println("SLEEP INTERROMPU");
            }

            if(!stop){
                synchronized(jeu.getVerrousEntites().get(0)){
                    synchronized(jeu.getVerrousEntites().get(positionInList+1)){
                        m = jeu.getEntites().get(positionInList+1);
                        pv = m.getPointsVie();
                        j = jeu.getJoueur();
                        
                        if(m.attaquer(jeu.getEntites(), null).size() == 0){
                            
                            px = m.getX();
                            py = m.getY();
                            random = r.nextInt(4);
                            random = random+1;

                            switch(random) {
                                case 1:
                                    px += 1;
                                    sens = Direction.BAS;
                                break;
                                case 2:
                                    py += 1;
                                    sens = Direction.DROITE;
                                break;
                                case 3:
                                    px += -1;
                                    sens = Direction.HAUT;
                                break;
                                case 4:
                                    py += -1;
                                    sens = Direction.GAUCHE;
                                break;
                                default :
                                    sens = Direction.BAS;
                            }

                            if(m instanceof Fantome) {
                                if(px >= 0 && py >= 0 && px <= hauteur-1 && py <= largeur-1){
                                    m.seDeplacer(px, py);
                                    m.setRegard(sens);
                                }
                            } else {
                                if(jeu.validerDeplacement(px, py)) {
                                    m.seDeplacer(px, py);
                                }
                                m.setRegard(sens);
                            }

                        } else {

                            int dgts = m.getArme().getDegats();
                            if(j.prendreDegat(dgts)){
                                if(j.isRevenant()) {
                                    j.setPointsVie(20);
                                } else{
                                    j.setPointsVie(0);
                                    this.jeu.mortJoueur(positionInList);
                                }
                            }
                            
                            synchronized(jeu.getVerrouInformations()){
                                jeu.ajouterInfos("Le "+ (m instanceof Gobelin? "Gobelin" : "Fatôme")+ " position ("+m.getX()+","+m.getY()+") vous a attribué "+dgts+" de dégats!" );
                            }
                        }
                    }
                }
            }

        } while(!stop);

        System.out.println("Fin monstre");

        synchronized(jeu.getVerrousEntites().get(positionInList)){
            synchronized(jeu.getVerrouInformations()){
                Entite e = jeu.getEntites().get(positionInList);
                jeu.ajouterInfos("Le "+ (e instanceof Gobelin? "Gobelin" : "Fatôme")+ " position ("+e.getX()+","+e.getY()+") est mort!" );
                jeu.getJoueur().gagnerExperience(2000);
            }
        }
    }

    public void arret(){
        stop=true;
    }
}