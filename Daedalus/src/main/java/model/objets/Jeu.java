package model.objets;

import model.objets.*;

/** 
* La classe qui représente le moteur du jeu
*/

public class Jeu{
    
    private Labyrinthe labyrinthe; // Le labyrinthe en cours
    private Joueur joueur; // Le joueur à l'intérieur du labyrinthe

    public Jeu(){
        // TO DO
    }

    /**
    *   Déplace un joueur dans une direction souhaitée
    *   @param direction La direction dans laquelle on veut déplacer le joueur (gauche/droite/haut/bas)
    */
    public void deplacerJoueur(String direction){
        int px, py;
        px = joueur.getX();
        py = joueur.getY();

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

        if(validerDeplacement(px, py)){
            // TO DO
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