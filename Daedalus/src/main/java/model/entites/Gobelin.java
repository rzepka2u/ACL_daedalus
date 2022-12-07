package model.entites;

import model.enums.Direction;
import model.tresors.Arme;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Représente un monstre de type gobelin dans le jeu
 */
public class Gobelin extends Entite{


    /**
     * Constructeur par défaut de la classe Gobelin
     * @param x la position de spawn du gobelin (en ordonnées)
     * @param y la position de spawn du gobelin (en abscisses)
     */
    public Gobelin(int x, int y){

        // Appel du constructeur de la classe mère
        super(x, y, NB_PV_START, NB_PA_START, Direction.BAS);

        // Initialisation de l'attribut Arme
        setArme(new Arme());
    }
    
    /**
     * Constructeur par initialisation de la classe Gobelin
     * @param x la position de spawn du gobelin (en ordonnées)
     * @param y la position de spawn du gobelin (en abscisses)
     * @param pv le nombre de points de vie de départ attribués
     * @param pa le nombre de points d'armure de départ attribués
     */
    public Gobelin(int x, int y, int pv, int pa){

        // Appel du constructeur de la classe mère
        super(x,y,pv,pa, Direction.BAS);

        // Initialisation de l'attribut Arme
        setArme(new Arme());
    }

    /**
     * Méthode qui permet de déplacer le gobelin (set attributs hérités X et Y)
     * @param px la nouvelle position du gobelin (en ordonnées)
     * @param py la nouvelle position du gobelin (en abscisses)
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

        // Récupération du joueur de la partie
        Joueur j = (Joueur) entites.get(0);


        // On étudie la direction du regard du gobelin
        switch (this.getRegard()) {
            
            // S'il regarde en haut
            case HAUT :    
                // On test si le joueur est sur la case du dessus 
                if(j.getX() == this.getX()-1 && j.getY() == this.getY()) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                } 
            break;
            
            // S'il regarde en bas
            case BAS:
                // On test si le joueur est sur la case du dessous
                if(j.getX() == this.getX()+1 && j.getY() == this.getY()) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }
            break;

            // S'il regarde à droite
            case DROITE:
                // On test si le joueur est sur la case de droite
                if(j.getX() == this.getX() && j.getY() == this.getY()+1) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }
            break;
            
            // S'il regarde à gauche
            case GAUCHE:
                // On test si le joueur est sur la case de gauche
                if(j.getX() == this.getX() && j.getY() == this.getY()-1) {
                    // si oui on l'ajoute aux entités touchées
                    entitesTouchees.add(j);
                }
            break;
        }       

        // Si le joueur est à portée d'attaque
        if(entitesTouchees.size()>0){

            // On déclenche le son de l'attaque (bruit de gobelin)
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/gobelin.wav"));
                clip.open(inputStream);
                clip.start();
            } catch (Exception excep) { System.out.println(excep.getMessage()); }
        }

        return entitesTouchees;
        
    }
}