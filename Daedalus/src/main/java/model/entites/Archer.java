package model.entites;

import model.enums.Direction;
import model.enums.ZoneAttaque;
import model.tresors.Arme;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Représente un monstre de type Archer dans le jeu
 */
public class Archer extends Entite{

    /**
     * Constructeur par défaut de la classe Archer
     * @param x la position de spawn de l'archer (en ordonnées)
     * @param y la position de spawn de l'archer (en abscisses)
     */
    public Archer(int x, int y){

        // Appel au constructeur de la classe mère
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);
        
        // Initialisation de l'attribut arme avec l'arc de l'archer
        setArme(new Arme("arc en bois", 8, 5, ZoneAttaque.CASE_DEVANT));
    }

    /**
     * Constructeur par initialisation de la classe Archer
     * @param x la position de spawn de l'archer (en ordonnées)
     * @param y la position de spawn de l'archer (en abscisses)
     * @param pv le nombre de points de vie de départ ajoutés à l'archer
     * @param pa le nombre de points d'armure de départ ajoutés à l'archer
     */    
    public Archer(int x, int y, int pv, int pa){

        // Appel au constructeur de la classe mère
        super(x,y,pv,pa, Direction.BAS);

        // Initialisation de l'attribut arme avec l'arc de l'archer
        setArme(new Arme("arc en bois", 8, 5, ZoneAttaque.CASE_DEVANT));
    }


    /**
     * Méthode qui permet de déplacer l'Archer (set attributs hérités X et Y)
     * @param px la nouvelle position de l'archer (en ordonnées)
     * @param py la nouvelle position de l'archer (en abscisses)
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

        // Déclaration et intialisation de la liste de retour
        ArrayList<Entite> entitesTouchees = new ArrayList<Entite>();
        
        // Récupération des données nécessaires
        int portee = this.getArme().getPortee();
        Joueur j = (Joueur) entites.get(0);


        // On test le sens du regard de l'archer
        switch (this.getRegard()) {
            
            // S'il regarde en haut
            case HAUT :
                // on teste si le joueur se situe sur les cases au dessus de l'entité qui attaque
                for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                    if(j.getX() == this.getX()-i && j.getY() == this.getY()) {
                        // si oui on l'ajoute aux entités touchées
                        entitesTouchees.add(j);
                    }
                }
            break;

            // S'il regarde en bas 
            case BAS:
                // on teste si le joueur se situe sur les cases en dessous de l'entité qui attaque
                for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                    if(j.getX() == this.getX()+i && j.getY() == this.getY()) {
                        // si oui on l'ajoute aux entités touchées
                        entitesTouchees.add(j);
                    }
                }
                
            break;

            // S'il regarde à droite
            case DROITE:
                // on teste si le joueur se situe sur les cases à droite de l'entité qui attaque
                for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                    if(j.getX() == this.getX() && j.getY() == this.getY()+i) {
                        // si oui on l'ajoute aux entités touchées
                        entitesTouchees.add(j);
                    }
                }            
            break;

            // S'il regarde à gauche
            case GAUCHE:     
                // on teste si le joueur se situe sur les cases à gauche de l'entité qui attaque
                for(int i = 1; i <= portee && entitesTouchees.size() == 0; i++) {
                    if(j.getX() == this.getX() && j.getY() == this.getY()-i) {
                        // si oui on l'ajoute aux entités touchées
                        entitesTouchees.add(j);
                    }
                }
                
            break;
        }

        // Si le joueur est à portée d'attaque
        if(entitesTouchees.size()>0){

            // Déclenchement du son de l'attaque (bruit d'arc)
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/arc.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception excep) { System.out.println(excep.getMessage()); }
            
        }

        return entitesTouchees;
        
    }
}