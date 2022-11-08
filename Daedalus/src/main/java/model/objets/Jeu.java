package model.objets;

import model.cases.Case;
import model.cases.CaseVide;
import model.cases.CaseEffet;
import model.cases.CaseSortie;

import model.cases.CaseTresor;
import model.enums.Direction;
import model.enums.Ordre;
import model.ihm.FenetreGraphique;
import model.threads.ThreadEffet;
import model.threads.ThreadMonstre;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/** 
* La classe qui représente le moteur du jeu
*/

public class Jeu{

    private final int nbMaxNiveau;
    private final int DIMENSION_LABYRINTHE = 15;

    private FenetreGraphique fenetre;
    private Labyrinthe labyrinthe; // Le labyrinthe en cours
    private ArrayList<Entite> entites;
    private ArrayList<Object> verrousEntites;
    private ArrayList<ThreadMonstre> threads;
    private ArrayList<ThreadEffet> threadsEffet;
    private int nbNiveau;
    private ArrayList<String> informations;
    private Object verrouInformations;


   /**
     * Constructeur par défaut d'un objet Jeu avec un labyrinthe par défaut
     */
    public Jeu(FenetreGraphique f, int nbMax){

        this.nbMaxNiveau = nbMax;
        this.nbNiveau = 0;
        this.fenetre = f;
        this.informations = new ArrayList<String>();
        this.verrouInformations = new Object();

        //Initialisation du labyrinthe avec le labyrinthe par défaut
        this.labyrinthe = new Labyrinthe(DIMENSION_LABYRINTHE);

        this.entites = new ArrayList<Entite>();
        this.verrousEntites = new ArrayList<Object>();

        //Initialisation du joueur à la postion de départ
		this.entites.add(new Joueur(labyrinthe.getHauteur()-2, 1));
        this.verrousEntites.add(new Object());

        this.threads = new ArrayList<ThreadMonstre>();

        // CREATION DES MONSTRES (Object Monstre + ThreadMonstres + start)
        createNewEntites();

    }

    /** 
    * Constructeur par initialisation d'un objet Jeu avec un labyrinthe contenu dans un fichier
    * @param path Le chemin relatif ou absolu du fichier contenant le labyrinthe
    */
    public Jeu(FenetreGraphique f, String path, int nbMax) throws FileNotFoundException {

        int[] positionDepart;
        this.nbNiveau = 0;
        this.nbMaxNiveau = nbMax;
        this.fenetre = f;

        this.informations = new ArrayList<String>();
        this.verrouInformations = new Object();

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
        synchronized(verrousEntites.get(0)){
            return (Joueur) this.entites.get(0);    
        }
    }

    /**
     * getteur pour les entités du Jeu
     * @return ArrayList<Entite> les entites
     */
    public ArrayList<Entite> getEntites(){
        return this.entites;
    }

    public ArrayList<Object> getVerrousEntites(){
        return this.verrousEntites;
    }

    public ArrayList<String> getInformations(){
        return this.informations;
    }

    public Object getVerrouInformations(){
        return this.verrouInformations;
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
        while(!c.estTraversable() ||  !(c instanceof CaseVide) || emplacementOccupe(positionDepart[0], positionDepart[1])){

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

    public boolean emplacementOccupe(int x, int y){
        
        boolean res = false;
        int i;
        
        if(this.entites != null){

            for(i=0; i<entites.size() && res == false; i++){
                if(entites.get(i).getX() == x){
                    if(entites.get(i).getY() == y){
                        res = true;
                    }
                }        
            }

        }

        return res;
    }

    /**
     * permet de placer sur joueur sur la case (x,y) en outrepassant son test de direction
     * @param x coordonnee x
     * @param y coordonnee y
     */
    public void placerJoueurSurCase(int x, int y) {
        synchronized(verrousEntites.get(0)){
            ((Joueur)this.entites.get(0)).seDeplacer(x,y);
        }
    }

    /**
    *   Déplace un joueur dans une direction souhaitée
    *   @param sens La direction dans laquelle on veut déplacer le joueur (gauche/droite/haut/bas)
    *   @return Un entier qui indique le type de déplacement (0 : déplacement valide, 1 : collision, 2 : déplacement sur la sortie)
    */
    public int deplacerJoueur(Direction sens){
        int px, py;
        int res = 1;

        synchronized(verrousEntites.get(0)){

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
                if (etreSurCaseEffet(px, py)) res = 3;
                if (etreSurSortie(px,py)) res = 2;
            }
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
        synchronized(labyrinthe.getVerrousCases().get(px).get(py)){
            return labyrinthe.getCase(px,py).estTraversable();
        }
    }

    /**
     * Détermine si le joueur est sur la sortie ou non
     * @param px la nouvelle position horizontale du joueur
     * @param py la nouvelle position verticale du joueur
     * @return un boolean à true si la case est la sortie, false sinon
     */
    public boolean etreSurSortie(int px, int py) {

        synchronized(labyrinthe.getVerrousCases().get(px).get(py)){
            // Récupération de la case sur laquelle sera le joueur après son déplacement
            Case c = this.labyrinthe.getCase(px,py);
            // true si c'est la sortie, false sinon
            return c instanceof CaseSortie;
        }
    }

     /**
     * Détermine si le joueur est sur une case à effet ou non 
     * @param px la nouvelle position horizontale du joueur
     * @param py la nouvelle position verticale du joueur
     * @return un boolean à true si la case est une case à effet, false sinon
     */
    public boolean etreSurCaseEffet(int px, int py) {
        synchronized(labyrinthe.getVerrousCases().get(px).get(py)){
            // Récupération de la case sur laquelle sera le joueur après son déplacement
            Case c = this.labyrinthe.getCase(px,py);
            // true si c'est une case à effet, false sinon
            return c instanceof CaseEffet;
        }
    }

    public void appliquerEffetCase(Direction sens) {
        int px, py;
        px = this.entites.get(0).getX();
        py = this.entites.get(0).getY();
        switch (sens) {
            case GAUCHE -> py -= 1;
            case DROITE -> py += 1;
            case HAUT -> px -= 1;
            case BAS -> px += 1;
        }
        CaseEffet ce = (CaseEffet) labyrinthe.getCase(px, py);
        boolean progressif = ce.getProgressif();
        if(progressif) {
            ThreadEffet te = new ThreadEffet(this, threadsEffet.size(), ce.getAugmentation(), ce.getDiminutionPV(), 10);
            threadsEffet.add(te);
            te.run();
        } else {
            this.getJoueur().modifierPV(ce.getAugmentation());
            this.getJoueur().modifierPV(- ce.getDiminutionPV());
        }
    }

    public int[] etreProcheCaseTresor() {
        int[] pos = {-1, -1};
        Case c;
        switch(getJoueur().getRegard()) {
            case HAUT : 
                c = this.labyrinthe.getCase(this.getJoueur().getX(), this.getJoueur().getY() + 1);
                if(c instanceof CaseTresor) {
                    pos[0] = this.getJoueur().getX();
                    pos[1] = this.getJoueur().getY() + 1;
                }
            break;
            case BAS : 
                c = this.labyrinthe.getCase(this.getJoueur().getX(), this.getJoueur().getY() - 1);
                if(c instanceof CaseTresor) {
                    pos[0] = this.getJoueur().getX();
                    pos[1] = this.getJoueur().getY() - 1;
                }
            break;
            case GAUCHE : 
                c = this.labyrinthe.getCase(this.getJoueur().getX() - 1, this.getJoueur().getY());
                if(c instanceof CaseTresor) {
                    pos[0] = this.getJoueur().getX() - 1;
                    pos[1] = this.getJoueur().getY() ;
                }
            break;
            case DROITE : 
                c = this.labyrinthe.getCase(this.getJoueur().getX() + 1, this.getJoueur().getY());
                if(c instanceof CaseTresor) {
                    pos[0] = this.getJoueur().getX() + 1;
                    pos[1] = this.getJoueur().getY();
                }
            break;
        }
        return pos;
    }

    // Méthode appelé a chaque evenement de controle 
    public void controles(Commande cmd){

        if(cmd.getOrdre() == Ordre.DEPLACEMENT){
            switch(deplacerJoueur(cmd.getDirection())){
                case 2: 
                    changerNiveau();
                    break;
                case 3:
                    appliquerEffetCase(cmd.getDirection());
                    break;
            }

        } else if(cmd.getOrdre() == Ordre.ATTAQUE){ // cmd.getOrdre() == Ordre.ATTAQUE 
            // TO DO: ATTAQUE DU JOUEUR
            ArrayList<Entite> lEntites = new ArrayList<Entite>(this.entites);
            lEntites.remove(this.getEntites().get(0));
            ArrayList<Entite> lAttaquees = this.getEntites().get(0).attaquer(lEntites);
            int dgts = this.getEntites().get(0).getArme().getDegats();
            for(Entite ent : lAttaquees) {
                if(ent.prendreDegat(dgts)) {
                    // kill thread
                }
            }
        } else if(cmd.getOrdre() == Ordre.OUVRIR){
            int[] poscoffre = etreProcheCaseTresor();
            if(poscoffre[0] != -1 && poscoffre[1] != -1) {
                CaseTresor ct = (CaseTresor) labyrinthe.getCase(poscoffre[0], poscoffre[1]);
                if(!ct.getOuvert()) ct.ouvrirTresor();
            }
        } else if(cmd.getOrdre() == Ordre.RAMASSER){
            // on teste si le joueur se trouve sur une case d'un trésor (et non d'un coffre)
            if(this.getLabyrinthe().getCase(this.getJoueur().getX(), this.getJoueur().getY()) instanceof CaseTresor) {
                CaseTresor ct = (CaseTresor) this.getLabyrinthe().getCase(this.getJoueur().getX(), this.getJoueur().getY());
                if(ct.getOuvert()) {
                    // on teste si ce trésor est une arme
                    if(ct.getContenu() instanceof Arme) {
                        Arme temp_a = this.getJoueur().getArme();
                        Arme nov_a = ((Arme) ct.getContenu());
                        // pose l'arme par terre
                        ct.setContenu(temp_a);

                        // switch avec la nouvelle
                        this.getJoueur().setArme(nov_a);
                    } else if(ct.getContenu() instanceof PieceArmure) {
                        // on ajoute de l'armure
                    } else if(ct.getContenu() instanceof Potion) {
                        this.getJoueur().ajouterPotion();
                    }
                }
            }
            // TO DO: RAMMASER UN TRESOR A PROXIMITE
        } else if(cmd.getOrdre() == Ordre.BOIRE){
            // TO DO: BOIRE LA POTION A L'indice cmd.getIndice() dans la collection du joueur
            this.getJoueur().boirePotion();
        }
    }

    // Methode appelée lorsque qu'un niveau est fini
    public void changerNiveau(){

        // - INTERRUPT LES THREADS MONSTRES
        int i;

        for(i=0; i<threads.size(); i++){
            threads.get(i).interrupt();
        }

        // - INTERUPT LES THREADS EFFETS PROGRESSIF
        for(i=0; i<threadsEffet.size(); i++){
            threads.get(i).interrupt();
        }

        if(nbNiveau < nbMaxNiveau){
            
            // 1- CREATION NOUVEAU LABYRINTHE 

            this.labyrinthe = new Labyrinthe(DIMENSION_LABYRINTHE);
            nbNiveau++;
            placerJoueurSurCase(labyrinthe.getHauteur()-2, i);

            // 2- CREATION NOUVELLES ENTITES (object + threads)
            
            createNewEntites();

        } else {
            fenetre.afficherVueFin(true);
        }
    }

    public void createNewEntites(){

        long nombreEntite =  Math.round(nbNiveau * 0.80 + 2);
        int i;


        if(threads != null){
            while(threads.size() > 0){
                threads.remove(0);
                verrousEntites.remove(1);
                entites.remove(1);
            }
        }


        for(i=0; i<nombreEntite; i++){
            
            int[] determinerDepart = determinerDepart(this.labyrinthe);

            if(i%2 == 0){
                entites.add(new Fantome(determinerDepart[0], determinerDepart[1]));
                verrousEntites.add(new Object());
            } else {
                entites.add(new Gobelin(determinerDepart[0], determinerDepart[1]));
                verrousEntites.add(new Object());
            }
        }

        for(i=0; i<nombreEntite; i++){
            threads.add(new ThreadMonstre(this, i));
            threads.get(i).start();
        }

    }

    // Méthode appelée lorsque le joueur est mort
    public void mortJoueur(){
        
        // - INTERRUPT LES THREADS MONSTRES
        int i;

        for(i=0; i<threads.size(); i++){
            threads.get(i).interrupt();
        }

        // - INTERUPT LES THREADS EFFETS PROGRESSIF
        // TO DO:

        fenetre.afficherVueFin(false);
    }

    // Méthode pour ajouter une informations dans le tableau des informations
    public void ajouterInfos(String infos){
        synchronized(verrouInformations){
            informations.add(infos);
        }
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