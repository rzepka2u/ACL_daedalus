package model.threads;

import model.objets.Entite;
import model.objets.Fantome;
import model.objets.Gobelin;
import model.objets.Jeu;
import model.objets.Joueur;
import model.objets.Kamikaze;
import model.cases.Case;
import model.cases.CaseMur;
import model.enums.Direction;

import java.io.Serializable;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;

public class ThreadMonstre extends Thread implements Serializable {

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

                            if(!(m instanceof Fantome)){
                                sens = determinerDeplacement(px, py, m);
                            } else {
                                sens = determinerDeplacementFantome(px, py, m, j, hauteur, largeur);
                            }

                            switch(sens){
                                case HAUT -> px -= 1;
                                case BAS -> px += 1;
                                case GAUCHE -> py -= 1;
                                case DROITE -> py += 1;
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

                            if(j.isEpines()) {
                                if(m.prendreDegat(dgts/10)) this.arret();
                            }

                            if(j.isBlocage()) dgts -= dgts/4;

                            boolean esquive = false;

                            if(j.isAnguille()) {
                                int nb = r.nextInt(100+1)+1;
                                if(nb >= 1 && nb <= 10) {
                                    esquive = true;
                                }
                            }
                            
                            if(!esquive) {
                                if(j.prendreDegat(dgts)){
                                    if(j.isRevenant()) {
                                        j.setPointsVie(20);
                                    } else{
                                        j.setPointsVie(0);
                                        this.jeu.mortJoueur(positionInList);
                                    }
                                }
                            }
                            
                            synchronized(jeu.getVerrouInformations()){
                                jeu.ajouterInfos("Le "+ (m instanceof Gobelin? "Gobelin" : "Fantôme")+ " position ("+m.getX()+","+m.getY()+") vous a attribué "+dgts+" de dégats!" );
                            }

                            if(m instanceof Kamikaze) {
                                m.setPointsVie(0);
                                this.arret();
                            }
                        }
                    }
                }
            }

        } while(!stop);

        synchronized(jeu.getVerrousEntites().get(positionInList+1)){
            synchronized(jeu.getVerrouInformations()){
                Entite e = jeu.getEntites().get(positionInList+1);
                jeu.ajouterInfos("Le "+ (e instanceof Gobelin? "Gobelin" : "Fantôme")+ " position ("+e.getX()+","+e.getY()+") est mort!" );
            }
        }

        synchronized (jeu.getVerrousEntites().get(0)) {
            jeu.getJoueur().gagnerExperience(2000);
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

    public int getPositionInList() {
        return positionInList;
    }

    private Direction determinerDeplacement(int px, int py, Entite m){
        
        boolean haut = false, bas = false, gauche = false, droite = false;
        Direction resultat;

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
            resultat = Direction.HAUT;
        } else if(bas){
            resultat = Direction.BAS;
        }else if(gauche){
            resultat = Direction.GAUCHE;
        } else if(droite){
            resultat = Direction.DROITE;
        } else {
            resultat = determinerDirectionAlea(m, px, py);
        }

        return resultat;

    }

    private Direction determinerDeplacementFantome(int px, int py, Entite m, Entite j, int hauteur, int largeur){
        
        int i, cas = -1;
        boolean cond = true;
        Direction resultat;

        for(i=0;i<3 && cond && px-i > 0 && py-i > 0;i++){
            if(j.getX() == m.getX()-i && j.getY() == m.getY()-i){
                cond = false;
                cas = 1;
            }
        }

        for(i=0;i<5 && cond && px-i > 0; i++){
            if(j.getX() == m.getX()-i && j.getY() == m.getY()){
                cond = false;
                cas = 2;
            }
        }

        for(i=0;i<3 && cond && px-i > 0 && py+i < largeur; i++){
            if(j.getX() == m.getX()-i && j.getY() == m.getY()+i){
                cond = false;
                cas = 3;
            }
        }

        for(i=0;i<3 && cond && px+i < hauteur && py-i > 0;i++){
            if(j.getX() == m.getX()+i && j.getY() == m.getY()-i){
                cond = false;
                cas = 4;
            }
        }

        for(i=0;i<5 && cond && px+i < hauteur;i++){
            if(j.getX() == m.getX()+i && j.getY() == m.getY()){
                cond = false;
                cas = 5;
            }
        }

        for(i=0;i<3 && cond && px+i < hauteur && py+i < largeur; i++){
            if(j.getX() == m.getX()+i && j.getY() == m.getY()+i){
                cond = false;
                cas = 6;
            }
        }

        for(i=0;i<5 && cond && py-i > 0;i++){
            if(j.getX() == m.getX() && j.getY() == m.getY()-i){
                cond = false;
                cas = 7;
            }
        }

        for(i=0;i<5 && cond && py+1 < largeur;i++){
            if(j.getX() == m.getX() && j.getY() == m.getY()+i){
                cond = false;
                cas = 8;
            }
        }

        if(cas == -1){
            
            resultat = determinerDirectionAlea(m, px, py);
    
        } else {
                                    
            switch(cas){
                case 1 -> resultat = Direction.HAUT;
                case 2 -> resultat = Direction.HAUT;
                case 3 -> resultat = Direction.HAUT;
                case 4 -> resultat = Direction.BAS;
                case 5 -> resultat = Direction.BAS;
                case 6 -> resultat = Direction.BAS;
                case 7 -> resultat = Direction.GAUCHE;
                case 8 -> resultat = Direction.DROITE;
                default -> resultat = Direction.BAS;
            }
        }

        return resultat;
    }
}