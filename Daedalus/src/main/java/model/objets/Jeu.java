package model.objets;

import java.io.IOException;
import model.objets.*;
import model.cases.Case;
import java.util.ArrayList;

/** 
* La classe qui représente le moteur du jeu
*/

public class Jeu{
    
    private Labyrinthe labyrinthe; // Le labyrinthe en cours
    private Joueur joueur; // Liste des entites du jeu (dont le joueur)

    public Jeu(String path){
        //Initialisation du labyrinthe via fichier texte 
        try {
			this.labyrinthe = new Labyrinthe(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        //Initialisation du joueur au centre du Labyrinthe 
		this.joueur = new Joueur(this.labyrinthe.getXMilieu(), this.labyrinthe.getYMilieu());
    }

    /**
    *   Déplace un joueur dans une direction souhaitée
    *   @param direction La direction dans laquelle on veut déplacer le joueur (gauche/droite/haut/bas)
    *   @return Un booléen qui indique si le déplacement a était effectuée ou non (si une collision est survenue ou pas)
    */
    public boolean deplacerJoueur(String direction){
        int px, py;

        // Récupération des coordonnées du joueur
        px = this.joueur.getX();
        py = this.joueur.getY();

        // Modification de ses coordonnées suivant la direction
        switch(direction){
            case "gauche":
                py -= 1;
                break;
            case "droite":
                py += 1;
                break;
            case "haut":
                px -= 1;
                break;
            case "bas":
                px += 1;
                break;
        }

        //Si la la case sur laquelle veut aller le joueur est valide alors le déplacement est effectué
        if(validerDeplacement(px, py)){
            joueur.seDeplacer(px, py);
            return true;
        }

        return false;
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

    
    @Override
    public String toString(){

        // Initialisation et déclaration des variables
        StringBuilder res = new StringBuilder();
        int[] positionJoueur = new int[2];
        int i = 0; // Compteur sur les lignes du labyrinthe
        int j = 0; // Compteur sur les cases de la ligne

        // Récupération de la position du joueur
        positionJoueur[0] = this.joueur.getX();
        positionJoueur[1] = this.joueur.getY();

        for (ArrayList<Case> aCase : this.labyrinthe.getCases()) { // Boucle sur les lignes du labyrinthe
            for (Case value : aCase) {  // Boucle sur les colonnes du labyrinthe

                // Si c'est la position du joueur, on l'affiche, sinon on affiche la case
                if(i == positionJoueur[0] && j == positionJoueur[1]){
                    res.append(this.joueur);
                } else {
                    res.append(value);
                }


                res.append(" "); // Espace entre les cases pour une meilleure visibilité
                j++;
            }
            res.append("\n");
            i++;
            j=0;
        }
        
        return res.toString();
    }
}