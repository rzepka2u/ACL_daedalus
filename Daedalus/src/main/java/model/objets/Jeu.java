package model.objets;

import java.io.IOException;
import model.objets.*;

/** 
* La classe qui représente le moteur du jeu
*/

public class Jeu{
    
    private Labyrinthe labyrinthe; // Le labyrinthe en cours
    private Joueur joueur; // Liste des entites du jeu (dont le joueur)

    public Jeu(){
        //Initialisation du labyrinthe via fichier texte 
        try {
			this.labyrinthe = new Labyrinthe("src/main/ressources/niveaux/niveau0.txt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        //Initialisation du joueur au centre du Labyrinthe 
		this.joueur = new Joueur(this.labyrinthe.getXMilieu(), this.labyrinthe.getYMilieu());
    }

    /**
    *   Déplace un joueur dans une direction souhaitée
    *   @param direction La direction dans laquelle on veut déplacer le joueur (gauche/droite/haut/bas)
    */
    public void deplacerJoueur(String direction){
        int px, py;
        px = 0;
        py = 0;

        switch(direction){
            case "gauche":
                px -= 1;
                break;
            case "droite":
                px += 1;
                break;
            case "haut":
                py -= 1;
                break;
            case "bas":
                py += 1;
                break;
        }
        //Si la la case sur laquelle veut aller le joueur est valide alors le déplacement est effectué
        if(validerDeplacement(px, py)){
            joueur.seDeplacer(px, py);
        }
    }

    /**
    *   Détermine si un déplacement est réalisable ou non
    *   @param px la nouvelle position horizontale du joueur
    *   @param py la nouvelle position verticale du joueur
    *   @return un boolean à true si le déplacement est réalisable, false sinon
    */
    public boolean validerDeplacement(int px, int py){
        return labyrinthe.getCase(px,py).estTraversable();
    }

}