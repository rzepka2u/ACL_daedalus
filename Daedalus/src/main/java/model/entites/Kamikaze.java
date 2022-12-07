package model.entites;

import model.enums.Direction;
import model.enums.ZoneAttaque;
import model.tresors.Arme;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Représente un monstre de type kamikaze dans le jeu
 */
public class Kamikaze extends Entite{


    /**
     * Constructeur par défaut de la classe Kamikaze
     * @param x la position de spawn du kamikaze (en ordonnées)
     * @param y la position de spawn du kamikaze (en abscisses)
     */
    public Kamikaze(int x, int y){

        // Appel au constructeur de la classe mère
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);

        // Initialisation de l'attribut arme
        setArme(new Arme("ceinture d'explosifs", 20, 2, ZoneAttaque.EN_CARRE));
    }
    
    /**
     * Constructeur par intialisation de la classe Kamikaze
     * @param x la position de spawn du kamikaze (en ordonnées)
     * @param y la position de spawn du kamikaze (en abscisses)
     * @param pv le nombre de points de vie de départ à attribuer
     * @param pa le nombre de points d'armure de départ à attribuer
     */
    public Kamikaze(int x, int y, int pv, int pa){

        // Appel au constructeur de la classe mère
        super(x,y,pv,pa, Direction.BAS);

        // Initialisation de l'attribut arme
        setArme(new Arme("ceinture d'explosifs", 20, 2, ZoneAttaque.EN_CARRE));
    }

    /**
     * Méthode qui permet de déplacer le Kamikaze (set attributs hérités X et Y)
     * @param px la nouvelle position du Kamikaze (en ordonnées)
     * @param py la nouvelle position du Kamikaze (en abscisses)
     */
    public void seDeplacer(int px, int py){
        this.setX(px);
        this.setY(py);
    }

    /**
     * Méthode qui permet de savoir si le joueur est à portée d'attaque
     * @param entites objet ArrayList<Entite> contenant les entites du jeu
     * @param verrous objet ArrayList<Object> contenant les verrous intrasectes pour les entites du jeu
     * @return ArrayList<Entite> de taille 0 ou de taille 1 (contenant le joueur)
     */
    @Override
    public ArrayList<Entite> attaquer(ArrayList<Entite> entites, ArrayList<Object> verrous) {


        // Déclaration et initialisation de la liste de retour
        ArrayList<Entite> entitesTouchees = new ArrayList<Entite>();
        
        // Récupération des données nécessaires
        int portee = this.getArme().getPortee();
        Joueur j = (Joueur) entites.get(0);

        // Boucle sur la portée de l'arme
        for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
            
            // l'entité se situe dans les cases au dessus du joueur
            if(j.getX() == this.getX()-i && j.getY() == this.getY()) {
                entitesTouchees.add(j);
            }

            // l'entité se situe dans les cases en dessous du joueur
            if(j.getX() == this.getX()+i && j.getY() == this.getY()) {
                entitesTouchees.add(j);
            }

            // l'entité se situe dans les cases à gauche du joueur
            if(j.getX() == this.getX() && j.getY() == this.getY()-i) {
                entitesTouchees.add(j);
            }

            // l'entité se situe dans les cases à droite du joueur
            if(j.getX() == this.getX() && j.getY() == this.getY()+i) {
                entitesTouchees.add(j);
            }

            // l'entité se situe dans les cases en haut à droite du joueur
            if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                entitesTouchees.add(j);
            }

            // l'entité se situe dans les cases en bas à droite du joueur
            if(j.getX() == this.getX()+i && j.getY() == this.getY()+i) {
                entitesTouchees.add(j);
            }

            // l'entité se situe dans les cases en haut à gauche du joueur
            if(j.getX() == this.getX()-i && j.getY() == this.getY()-i) {
                entitesTouchees.add(j);
            }
                    
            // l'entité se situe dans les cases en bas à gauche du joueur
            if(j.getX() == this.getX()-i && j.getY() == this.getY()+i) {
                entitesTouchees.add(j);
            }
                
        }

        // Si le joueur est à portée d'attaque
        if(entitesTouchees.size()>0){

            // Lecture et déclenchement du son de l'attaque (bruit de bombes)
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/bombe.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception excep) { System.out.println(excep.getMessage()); }
        }

        return entitesTouchees;
        
    }
}