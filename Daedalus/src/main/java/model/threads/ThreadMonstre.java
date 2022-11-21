package model.threads;

import model.objets.Entite;
import model.objets.Fantome;
import model.objets.Gobelin;
import model.objets.Jeu;
import model.objets.Joueur;
import model.cases.Case;
import model.cases.CaseMur;
import model.enums.Direction;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;

public class ThreadMonstre extends Thread {

    private int positionInList;
    private Jeu jeu;
    private boolean stop;
    private boolean joueurtrouve;

    public ThreadMonstre(Jeu j, int pos){
        super();
        jeu = j;
        positionInList = pos;
        stop = false;
        joueurtrouve = false;
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
            } catch (InterruptedException e){}

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
                            boolean haut = false, bas =false, gauche =false, droite =false;

                            if(px-1 >= 0 && px-1 <jeu.getLabyrinthe().getHauteur()){
                                haut = jeu.chercherJoueur(px-1, py, 1, Direction.HAUT);
                            }

                            if(px+1 >= 0 && px+1 < jeu.getLabyrinthe().getHauteur()){
                                bas = jeu.chercherJoueur(px+1, py, 1, Direction.BAS);
                            }

                            if(py-1 >= 0 && py-1 < jeu.getLabyrinthe().getLargeur()){
                                gauche = jeu.chercherJoueur(px, py-1, 1, Direction.GAUCHE);
                            }

                            if(py+1 >= 0 && py+1 < jeu.getLabyrinthe().getLargeur()){
                                droite = jeu.chercherJoueur(px, py+1, 1, Direction.DROITE); 
                            }

                            if(haut){
                                px -= 1;
                                sens = Direction.HAUT;
                            } else if(bas){
                                sens = Direction.BAS;
                                px += 1;
                            }else if(gauche){
                                sens = Direction.GAUCHE;
                                py -= 1;
                            } else if(droite){
                                sens = Direction.DROITE;
                                py += 1;
                            } else {

                                sens = determinerDirectionAlea(m, px, py);
                                switch(sens){
                                    case HAUT:
                                        px -= 1; 
                                    break;
                                    case BAS:
                                        px += 1; 
                                    break;
                                    case GAUCHE:
                                        py -= 1; 
                                    break;
                                    case DROITE:
                                        py += 1; 
                                    break;
                                }
                            }


                            if(jeu.getJoueur().getX() == px && jeu.getJoueur().getY() == py){
                                m.setRegard(sens);
                            } else {

                                if(m instanceof Fantome) {
                                    
                                    m.seDeplacer(px, py);
                                    m.setRegard(sens);
                                    
                                } else {
                                    if(!jeu.emplacementOccupe(px, py)) {
                                        m.seDeplacer(px, py);
                                    }
                                    m.setRegard(sens);
                                }
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

        synchronized(jeu.getVerrousEntites().get(positionInList+1)){
            synchronized(jeu.getVerrouInformations()){
                Entite e = jeu.getEntites().get(positionInList+1);
                jeu.ajouterInfos("Le "+ (e instanceof Gobelin? "Gobelin" : "Fatôme")+ " position ("+e.getX()+","+e.getY()+") est mort!" );
                jeu.getJoueur().gagnerExperience(2000);
            }
        }
    }

    public void arret(){
        stop=true;
    }

    private Direction determinerDirectionAlea(Entite m, int x, int y){

        ArrayList<Direction> sensPossibles = new ArrayList<>(); 

        if(m instanceof Fantome){

            if(x > 0){
                sensPossibles.add(Direction.HAUT);
            }
            if(x < jeu.getLabyrinthe().getHauteur()-1){
                sensPossibles.add(Direction.BAS);
            } 
            if(y > 0){
                sensPossibles.add(Direction.GAUCHE);
            }
            if(y < jeu.getLabyrinthe().getLargeur()-1){
                sensPossibles.add(Direction.DROITE);
            }

        } else {
            
            synchronized(jeu.getLabyrinthe().getVerrousCases().get(x-1).get(y)){
                if(jeu.getLabyrinthe().getCases().get(x-1).get(y).estTraversable() && !jeu.emplacementOccupe(x-1, y)){
                    sensPossibles.add(Direction.HAUT);
                }
            }

            synchronized(jeu.getLabyrinthe().getVerrousCases().get(x+1).get(y)){
                if(jeu.getLabyrinthe().getCases().get(x+1).get(y).estTraversable() && !jeu.emplacementOccupe(x+1, y)){
                    sensPossibles.add(Direction.BAS);
                }
            }

            synchronized(jeu.getLabyrinthe().getVerrousCases().get(x).get(y-1)){
                if(jeu.getLabyrinthe().getCases().get(x).get(y-1).estTraversable() && !jeu.emplacementOccupe(x, y-1)){
                    sensPossibles.add(Direction.GAUCHE);
                }
            }

            synchronized(jeu.getLabyrinthe().getVerrousCases().get(x).get(y+1)){
                if(jeu.getLabyrinthe().getCases().get(x).get(y+1).estTraversable() && !jeu.emplacementOccupe(x, y+1)){
                    sensPossibles.add(Direction.DROITE);
                }
            }
        }

        int alea = (int) (Math.random()*sensPossibles.size());

        return sensPossibles.get(alea);
    }
}