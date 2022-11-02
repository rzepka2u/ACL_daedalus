package model.objets;

import model.cases.Case;
import model.cases.CaseSortie;

import model.enums.Direction;
import model.enums.Ordre;
import model.threads.ThreadMonstre;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/** 
* La classe qui représente le moteur du jeu
*/

public class Jeu{
    
    private Labyrinthe labyrinthe; // Le labyrinthe en cours
    private ArrayList<Entite> entites;
    private ArrayList<Object> verrousEntites;
    private final int nbMaxNiveau;
    private int nbNiveau;
    private ArrayList<ThreadMonstre> threads;

    /**
     * Constructeur par défaut d'un objet Jeu avec un labyrinthe par défaut
     */
    public Jeu(int nbMax){

        int[] positionDepart; 
        this.nbMaxNiveau = nbMax;
        this.nbNiveau = 0;

        //Initialisation du labyrinthe avec le labyrinthe par défaut
        this.labyrinthe = new Labyrinthe();

        //Récupération de la position de départ
        positionDepart = determinerDepart(this.labyrinthe);

        this.entites = new ArrayList<Entite>();
        this.verrousEntites = new ArrayList<Object>();

        //Initialisation du joueur à la postion de départ
		this.entites.add(new Joueur(positionDepart[0], positionDepart[1]));
        this.verrousEntites.add(new Object());

        // TO DO: CREATION DES MONSTRES (Object Monstre + ThreadMonstres)

    }

    /** 
    * Constructeur par initialisation d'un objet Jeu avec un labyrinthe contenu dans un fichier
    * @param path Le chemin relatif ou absolu du fichier contenant le labyrinthe
    */
    public Jeu(String path, int nbMax) throws FileNotFoundException {

        int[] positionDepart;
        this.nbNiveau = 0;
        this.nbMaxNiveau = nbMax;

        //Initialisation du labyrinthe via fichier texte 
		this.labyrinthe = new Labyrinthe(path);

        //Récupération de la position de départ
        positionDepart = determinerDepart(this.labyrinthe);

        this.entites = new ArrayList<Entite>();
        this.verrousEntites = new ArrayList<Object>();

        //Initialisation du joueur à la position de départ
		this.entites.add(new Joueur(positionDepart[0], positionDepart[1]));
        this.verrousEntites.add(new Object());

        // TO DO: CREATION DES MONSTRES (Object Monstre + ThreadMonstres)
    }
    
    /**
     * getteur Labyrinthe
     * @return renvoie le labyrinthe du jeu
     */
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthe;
    }

    /**
     * getteur Joueur
     * @return renvoie le joueur du jeu
     */
    public Joueur getJoueur() {
        return (Joueur) this.entites.get(0);
    }

    /**
     * getteur pour les entités du Jeu
     * @return ArrayList<Entite> les entites
     */
    public ArrayList<Entite> getEntites(){
        return this.entites;
    }

    /**
     * Détermine la case de départ du joueur aléatoirement
     * @param l le labyrithne sur lequel va se dérouler la partie
     * @return un tableau d'entier de deux cases ou la case 0 est la position x du joueur, et la case 1 la position y du joueur
     */

    private int[] determinerDepart(Labyrinthe l){

        // Déclaration des variables nécessaires
        int[] positionDepart = new int[2]; // Tableau de deux cases pour les positions x et y du joueur
        int i;
        Case c;

        // Initialisation des variables
        positionDepart[0] = 0;
        positionDepart[1] = 0;
        c = l.getCase(positionDepart[0], positionDepart[1]);

        // Tant que la case n'est pas traversable ou que la case est la sortie
        while(!c.estTraversable() || c instanceof CaseSortie){

            // Tirage au sort d'une ligne du labyrinthe
            positionDepart[0] = (int) (Math.random() * l.getHauteur());
            
            // Tant que la case n'est pas traversable ou que la case est la sortie et que nous avons pas déjà essayé à trois reprise
            for(i=0; i<3 && (!c.estTraversable() || c instanceof CaseSortie); i++){

                //Tirage au sort d'une case parmi la ligne tirée au sort précédamment
                positionDepart[1] = (int) (Math.random() * l.getLargeur());
                c = l.getCase(positionDepart[0], positionDepart[1]);
            }

        }

        return positionDepart;
        
    }

    /**
     * permet de placer sur joueur sur la case (x,y) en outrepassant son test de direction
     * @param x coordonnee x
     * @param y coordonnee y
     */
    public void placerJoueurSurCase(int x, int y) {
        ((Joueur)this.entites.get(0)).seDeplacer(x,y);
    }

    /**
    *   Déplace un joueur dans une direction souhaitée
    *   @param direction La direction dans laquelle on veut déplacer le joueur (gauche/droite/haut/bas)
    *   @return Un entier qui indique le type de déplacement (0 : déplacement valide, 1 : collision, 2 : déplacement sur la sortie)
    */
    public int deplacerJoueur(Direction sens){
        int px, py;
        int res = 1;

        // Récupération des coordonnées du joueur
        px = this.entites.get(0).getX();
        py = this.entites.get(0).getY();

        // Modification de ses coordonnées suivant la direction
        switch (sens) {
            case GAUCHE -> py -= 1;
            case DROITE -> py += 1;
            case HAUT -> px -= 1;
            case BAS -> px += 1;
        }

        //Si la la case sur laquelle veut aller le joueur est valide alors le déplacement est effectué
        if(validerDeplacement(px, py)){
            this.entites.get(0).seDeplacer(px,py);
            ((Joueur) this.entites.get(0)).setRegard(sens);
            res = 0;
            if (etreSurSortie(px,py)) res = 2;
        }

        return res;
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

    /**
     * Détermine si le joueur est sur la sortie ou non
     * @param px la nouvelle position horizontale du joueur
     * @param py la nouvelle position verticale du joueur
     * @return un boolean à true si la case est la sortie, false sinon
     */
    public boolean etreSurSortie(int px, int py) {
        // Récupération de la case sur laquelle sera le joueur après son déplacement
        Case c = this.labyrinthe.getCase(px,py);
        // true si c'est la sortie, false sinon
        return c instanceof CaseSortie;
    }

    // Méthode appeler a chaque evenement de controle 
    // TO DO: REMONTER ORDRE UTILISER POTION DEPUIS LA FENETRE
    public void controles(Commande cmd){

        if(cmd.getOrdre() == Ordre.DEPLACEMENT){
            if(deplacerJoueur(cmd.getDirection()) == 2){
                changerNiveau();
            }
        } else if(cmd.getOrdre() == Ordre.ATTAQUE){ // cmd.getOrdre() == Ordre.ATTAQUE 
            // TO DO: ATTAQUE DU JOUEUR
        } else if(cmd.getOrdre() == Ordre.OUVRIR){
            // TO DO: OUVERTURE D'UN COFFRE A PROXIMITE
        } else if(cmd.getOrdre() == Ordre.RAMASSER){
            // TO DO: RAMMASER UN TRESOR A PROXIMITE
        }
    }

    // Methode appelée lorsque qu'un niveau est fini
    public void changerNiveau(){
        // TO DO CREATION NOUVEAU LABYRINTHE
    }

    @Override
    public String toString(){

        // Initialisation et déclaration des variables
        StringBuilder res = new StringBuilder();
        int[] positionJoueur = new int[2];
        int i = 0; // Compteur sur les lignes du labyrinthe
        int j = 0; // Compteur sur les cases de la ligne

        // Récupération de la position du joueur
        positionJoueur[0] = this.entites.get(0).getX();
        positionJoueur[1] = this.entites.get(0).getY();

        for (ArrayList<Case> aCase : this.labyrinthe.getCases()) { // Boucle sur les lignes du labyrinthe
            for (Case value : aCase) {  // Boucle sur les colonnes du labyrinthe

                // Si c'est la position du joueur, on l'affiche, sinon on affiche la case
                if(i == positionJoueur[0] && j == positionJoueur[1]){
                    res.append(this.entites.get(0));
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