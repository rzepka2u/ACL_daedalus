package model.threads;

import model.objets.Jeu;
import model.entites.Archer;
import model.entites.Entite;
import model.entites.Fantome;
import model.entites.Gobelin;
import model.entites.Joueur;
import model.entites.Kamikaze;
import model.enums.Direction;

import java.io.Serializable;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;

public class ThreadMonstre extends Thread implements Serializable {

    private int positionInList;
    private Jeu jeu;
    private boolean stop;
    private boolean pause;

    public ThreadMonstre(Jeu j, int pos){
        super();
        jeu = j;
        positionInList = pos;
        stop = false;
        pause = false;
    }

    public void run(){

        int px,py;
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
                        j = jeu.getJoueur();
                        
                        if(m.attaquer(jeu.getEntites(), null).size() == 0){

                            px = m.getX();
                            py = m.getY();

                            if(m instanceof Fantome){
                                sens = determinerDeplacementFantome(px, py, m, j, hauteur, largeur);
                            } else {

                                if(!jeu.getDossier()){
                                    sens = determinerDeplacement(px, py, m);
                                } else {
                                    sens = determinerDeplacementDossier(px, py, m, j, hauteur, largeur);
                                }
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
                                        j.setRevenant(false);
                                    } else{
                                        j.setPointsVie(0);
                                        this.jeu.mortJoueur(positionInList);
                                    }
                                }
                            }

                            String entite;

                            if(m instanceof Gobelin){
                                entite = "Le Gobelin";
                            } else if(m instanceof Fantome){
                                entite = "Le Fantome";
                            } else if(m instanceof Archer){
                                entite = "L'Archer";
                            } else {
                                entite = "Le Kamikaze";
                            }
                            
                            synchronized(jeu.getVerrouInformations()){
                                jeu.ajouterInfos(entite+ " position ("+m.getX()+","+m.getY()+") vous a attribué "+dgts+" de dégats!" );
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

        if(!pause){
            synchronized(jeu.getVerrousEntites().get(positionInList+1)){
                synchronized(jeu.getVerrouInformations()){
                    Entite e = jeu.getEntites().get(positionInList+1);
                    String entite;

                    if(e instanceof Gobelin){
                        entite = "Le Gobelin";
                    } else if(e instanceof Fantome){
                        entite = "Le Fantome";
                    } else if(e instanceof Archer){
                        entite = "L'Archer";
                    } else {
                        entite = "Le Kamikaze";
                    }

                    jeu.ajouterInfos(entite+ " position ("+e.getX()+","+e.getY()+") est mort!" );
                }
            }

            synchronized (jeu.getVerrousEntites().get(0)) {
                jeu.getJoueur().gagnerExperience(2000);
            }
        }
    }

    public void arret(){
        arret(false);
    }

    public void arret(boolean p){
        this.stop=true;
        this.pause = p;
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

    private int chercherJoueurSansMur(int px, int py, Entite m, Entite j, int hauteur, int largeur){
        
        int i, cas = -1;
        boolean cond = true;

        // HAUT GAUCHE

        if(j.getX() == m.getX()-4 && j.getY() == m.getY()-1){ 
            cond = false; 
            cas = 1;
        }

        if(cond && j.getX() == m.getX()-3 && (j.getY() == m.getY()-1 || j.getY() == m.getY()-2)){ 
            cond = false; 
            cas = 1;
        }

        if(cond && j.getX() == m.getX()-2 && (j.getY() == m.getY()-1 || j.getY() == m.getY()-2 || j.getY() == m.getY()-3)){ 
            cond = false; 
            cas = 1;
        }

        if(cond && j.getX() == m.getX()-1 && (j.getY() == m.getY()-1 || j.getY() == m.getY()-2 || j.getY() == m.getY()-3 || j.getY() == m.getY()-4 )){ 
            cond = false; 
            cas = 1;
        }

        // HAUT

        for(i=0;i<5 && cond && px-i > 0; i++){
            if(j.getX() == m.getX()-i && j.getY() == m.getY()){
                cond = false;
                cas = 2;
            }
        }

        // HAUT DROIT


        if(cond && j.getX() == m.getX()-4 && j.getY() == m.getY()+1){ 
            cond = false; 
            cas = 3;
        }

        if(cond && j.getX() == m.getX()-3 && (j.getY() == m.getY()+1 || j.getY() == m.getY()+2)){ 
            cond = false; 
            cas = 3;
        }

        if(cond && j.getX() == m.getX()-2 && (j.getY() == m.getY()+1 || j.getY() == m.getY()+2 || j.getY() == m.getY()-3)){ 
            cond = false; 
            cas = 3;
        }

        if(cond && j.getX() == m.getX()-1 && (j.getY() == m.getY()+1 || j.getY() == m.getY()-2 || j.getY() == m.getY()+3 || j.getY() == m.getY()+4 )){ 
            cond = false; 
            cas = 3;
        }

        // BAS GAUCHE

        if(cond && j.getX() == m.getX()+4 && j.getY() == m.getY()-1){ 
            cond = false; 
            cas = 4;
        }

        if(cond && j.getX() == m.getX()+3 && (j.getY() == m.getY()-1 || j.getY() == m.getY()-2)){ 
            cond = false; 
            cas = 4;
        }

        if(cond && j.getX() == m.getX()+2 && (j.getY() == m.getY()-1 || j.getY() == m.getY()-2 || j.getY() == m.getY()-3)){ 
            cond = false; 
            cas = 4;
        }

        if(cond && j.getX() == m.getX()+1 && (j.getY() == m.getY()-1 || j.getY() == m.getY()-2 || j.getY() == m.getY()-3 || j.getY() == m.getY()-4 )){ 
            cond = false; 
            cas = 4;
        }

        // BAS

        for(i=0;i<5 && cond && px+i < hauteur;i++){
            if(j.getX() == m.getX()+i && j.getY() == m.getY()){
                cond = false;
                cas = 5;
            }
        }

        // BAS DROITE

        if(cond && j.getX() == m.getX()+4 && j.getY() == m.getY()-1){ 
            cond = false; 
            cas = 6;
        }

        if(cond && j.getX() == m.getX()+3 && (j.getY() == m.getY()+1 || j.getY() == m.getY()+2)){ 
            cond = false; 
            cas = 6;
        }

        if(cond && j.getX() == m.getX()+2 && (j.getY() == m.getY()+1 || j.getY() == m.getY()+2 || j.getY() == m.getY()+3)){ 
            cond = false; 
            cas = 6;
        }

        if(cond && j.getX() == m.getX()+1 && (j.getY() == m.getY()+1 || j.getY() == m.getY()+2 || j.getY() == m.getY()+3 || j.getY() == m.getY()+4 )){ 
            cond = false; 
            cas = 6;
        }

        // GAUCHE /  DROITE

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

        return cas;
    }

    private Direction determinerDeplacementFantome(int px, int py, Entite m, Entite j, int hauteur, int largeur){
        
        int cas;
        Direction resultat;

        cas = chercherJoueurSansMur(px, py, m, j, hauteur, largeur);

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

    private Direction determinerDeplacementDossier(int px, int py, Entite m, Entite j, int hauteur, int largeur){
        
        int cas;
        Direction resultat;

        cas = chercherJoueurSansMur(px, py, m, j, hauteur, largeur);
        
        if(cas == -1){
            
            resultat = determinerDirectionAlea(m, px, py);
    
        } else {

            int pjx = j.getX();
            int pjy = j.getY();
            Direction sens;

            if(caseAccessibleSansMur(px, py, pjx, pjy, cas, 0)){
            
                switch(cas){
                    case 1: 

                        if(caseAccessibleSansMur(px-1, py, pjx, pjy, cas, 1)){
                            resultat = Direction.HAUT;
                        } else {
                            resultat = Direction.GAUCHE;
                        }
                       
                        break;

                    case 2:
                        resultat = Direction.HAUT;
                        break;
                    case 3:

                        if(caseAccessibleSansMur(px-1, py, pjx, pjy, cas, 1)){
                            resultat = Direction.HAUT;
                        } else {
                            resultat = Direction.DROITE;
                        }

                        break;
                    case 4:

                        if(caseAccessibleSansMur(px+1, py, pjx, pjy, cas, 1)){
                            resultat = Direction.BAS;
                        } else {
                            resultat = Direction.GAUCHE;
                        }

                        break;
                    case 5:
                        resultat = Direction.BAS;
                        break;
                    case 6:

                        if(caseAccessibleSansMur(px+1, py, pjx, pjy, cas, 1)){
                            resultat = Direction.BAS;
                        } else {
                            resultat = Direction.GAUCHE;
                        }

                        break;
                    case 7:
                        resultat = Direction.GAUCHE;
                        break;
                    case 8:
                        resultat = Direction.DROITE;
                        break;
                    default:
                        resultat = Direction.BAS;
                }
            } else {
                resultat = determinerDirectionAlea(m, px, py);
            }
        }   

        return resultat;
    }

    private boolean caseAccessibleSansMur(int px, int py, int pjx, int pjy, int cas, int cpt){

        if(px == pjx && py == pjy){
            return true;
        }

        if(cpt>5){
            return false;
        }

        if(!jeu.validerDeplacement(px, py)){
            return false;
        }

        if(cas == 1){
            return caseAccessibleSansMur(px-1, py, pjx, pjy, cas, cpt+1) || caseAccessibleSansMur(px, py-1, pjx, pjy, cas, cpt+1);
        } else if(cas == 2){
            return caseAccessibleSansMur(px-1, py, pjx, pjy, cas, cpt+1);
        } else if(cas == 3){
            return caseAccessibleSansMur(px-1, py, pjx, pjy, cas, cpt+1) || caseAccessibleSansMur(px, py+1, pjx, pjy, cas, cpt+1);      
        } else if(cas == 4){
            return caseAccessibleSansMur(px+1, py, pjx, pjy, cas, cpt+1) || caseAccessibleSansMur(px, py-1, pjx, pjy, cas, cpt+1);  
        } else if(cas == 5){
            return caseAccessibleSansMur(px+1, py, pjx, pjy, cas, cpt+1);  
        } else if(cas == 6){
            return caseAccessibleSansMur(px+1, py, pjx, pjy, cas, cpt+1) || caseAccessibleSansMur(px, py+1, pjx, pjy, cas, cpt+1);  
        } else if(cas == 7){
            return caseAccessibleSansMur(px, py-1, pjx, pjy, cas, cpt+1);  
        } else {
            return caseAccessibleSansMur(px, py+1, pjx, pjy, cas, cpt+1);  
        }

    }
}

//  C:\Users\33603\Desktop\COURS_MASTER\ACL\ACL_daedalus\Daedalus\src\main\resources\niveaux