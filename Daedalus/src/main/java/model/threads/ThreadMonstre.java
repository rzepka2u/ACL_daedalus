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

                            //On vérifie si on a repéré le joueur au cours du jeu
                            //Si on l'a trouvé alors on le suit
                            if(!joueurtrouve) {
                                //On cherche le joueur autour du monstre
                                int[] pos = this.jeu.chercherJoueur(m);
                                //Si le joueur ne se trouve pas à proximité du monstre et dans son champ de vision
                                //alors on se déplace aléatoirement
                                //Si le joueur se trouve à proximité alors trouvejoueur passe a true pour undiquer que
                                //dorénavent, on va suivre le joueur 
                                if(pos[0] == -1 && pos[1] == -1) {
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
                                            px += 1;
                                            sens = Direction.BAS;
                                    }

                                    //Récupération de la case vers laquelle va se diriger le monstre
                                    Case c = jeu.getLabyrinthe().getCase(px, py);

                                    //Si le monstre n'est pas un fantôle et qu'il se déplace vers une case 
                                    //qui n'est pas traversable alors on cherche de nouveau une case vers 
                                    //laquelle se déplacer et on refait le test
                                    if(!(m instanceof Fantome)) {
                                        while(!c.estTraversable()) {
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
                                            c = jeu.getLabyrinthe().getCase(px, py);
                                        }
                                    }

                                    //Si le monstre est un fantôme alors on se déplace en faisant uniquement
                                    //attention à ne pas sortir du labyrinthe
                                    //Sinon alors on vérifie que le déplacement est valide puis on se déplace
                                    if(m instanceof Fantome) {
                                        if(px >= 0 && py >= 0 && px <= hauteur-1 && py <= largeur-1){
                                            m.seDeplacer(px, py);
                                            m.setRegard(sens);
                                        }
                                    } else {
                                        if(jeu.validerDeplacement(px, py) && !jeu.emplacementOccupe(px, py)) {
                                            m.seDeplacer(px, py);
                                        }
                                        m.setRegard(sens);
                                    }
                                } else {
                                    //pos n'est [-1, -1] donc le joueur se trouve a proximité du monstre :
                                    //le joueur a été trouvé 
                                    this.joueurtrouve = true;
                                }
                            } else {
                                int j_x = this.jeu.getJoueur().getX();
                                int j_y = this.jeu.getJoueur().getY();
                                int dx = Math.abs(j_x - m.getX());
                                int dy = Math.abs(j_y - m.getY()); 
                                sens = Direction.BAS;
                                //Si le monstre est un fantôme, on se déplace librement
                                //Sinon alors on fait attention à ce que les cases soient traversables
                                if(m instanceof Fantome) { 
                                    //Si le est plus loin en x qu'en y    
                                    if(dx >= dy) {
                                        if(j_x > m.getX()) {
                                            px += 1;
                                            sens = Direction.BAS;
                                        } else {
                                            px += -1;
                                            sens = Direction.HAUT;
                                        }
                                    //Si le joueur est plus loin en y qu'en x
                                    } else {
                                        if(j_y > m.getY()) {
                                            py += 1;
                                            sens = Direction.DROITE;
                                        } else {
                                            py += -1;
                                            sens = Direction.GAUCHE;
                                        }
                                    }   
                                } else {
                                    boolean testx = false;
                                    boolean testy = false;
                                    boolean deplacement = false;
                                    //Tant qu'on a pas trouvé un déplacement possible à réaliser
                                    //Alors on cherche 
                                    while(!deplacement) {
                                        //Si le joueur est plus lion en x qu'en y
                                        if(dx >= dy) {
                                            if(j_x > m.getX() && jeu.validerDeplacement(px + 1, py)) {
                                                px += 1;
                                                sens = Direction.BAS;
                                                deplacement = true;
                                            } else if(j_x > m.getX() && jeu.validerDeplacement(px - 1, py)) {
                                                px += -1;
                                                sens = Direction.HAUT;
                                                deplacement = true;
                                            } else {
                                                testx = true;
                                            }     
                                        //Si le joueur est plus loin en y qu'en x OU que le déplacement en x
                                        //était impossible car chemin non empruntable                                
                                        } else if (dy > dx || testx){
                                            if(j_y > m.getY() && jeu.validerDeplacement(px, py + 1)) {
                                                py += 1;
                                                sens = Direction.DROITE;
                                                deplacement = true;
                                            } else if (jeu.validerDeplacement(px, py - 1)) {
                                                py += -1;
                                                sens = Direction.GAUCHE;
                                                deplacement = true;
                                            } else {
                                                testy = true;
                                            }
                                        //Si dy > dx mais que le déplacement en y était impossible
                                        }  else if (testy) {
                                            if(j_x > m.getX()) {
                                                px += 1;
                                                sens = Direction.BAS;
                                                deplacement = true;
                                            } else if(j_x > m.getX()) {
                                                px += -1;
                                                sens = Direction.HAUT;
                                                deplacement = true;
                                            }
                                        }
                                    }
                                }
                                if(m instanceof Fantome) {
                                    if(px >= 0 && py >= 0 && px <= hauteur-1 && py <= largeur-1){
                                        m.seDeplacer(px, py);
                                        m.setRegard(sens);
                                    }
                                } else {
                                    if(jeu.validerDeplacement(px, py) && !jeu.emplacementOccupe(px, py)) {
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

        System.out.println("Fin monstre");

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
}