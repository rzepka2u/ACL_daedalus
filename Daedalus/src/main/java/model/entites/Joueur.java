package model.entites;

import java.util.ArrayList;
import java.util.Timer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import model.enums.Direction;
import model.enums.TypeCompetence;
import model.objets.Competence;
import model.tresors.Arme;
import model.tresors.PieceArmure;
import model.tresors.Potion;

/**
* La classe qui représente le joueur dans le labyrinthe
*/

public class Joueur extends Entite {

    /**
     *  serialVersionUID pour la sauvegarde
     */
    private final long serialVersionUID = 2641088547673365950L;

    /**
     * Nombre de pa maximum des objets joueurs, évoluant au fil de la partie et des rangs gagnés
     */
    protected static int NB_PA_MAX = 50;

    /**
     *  Nombre d'objets maximum que les objets joueurs peuvent contenir dans l'inventaire 
     */
    protected static int TAILLE_INVENTAIRE = 5;

    /**
     *  L'inventaire de potions du joueur
     */
    private ArrayList<Potion> inventaire;

    /**
     *  Les points d'expériences du joueur 
     */
    private double experience;

    /**
     *  Le niveau du joueur: augmente avec l'expérience
     */
    private int rang;

    /**
     * Liste des compétences débloquées du joueur
     */
    private ArrayList<Competence> competences;

    /**
     * Les booléens qui déterminent si les compétences sont activées
     */
    private boolean drain, revenant, blocage, epines, anguille;

    /**
     * Les compétences choisies lors du panel de choix
     */
    private int[] competencesSelect;


    /**
     * Constructeur de la classe Joueur 
     * @param px coordonnées en x
     * @param py coordonnées en y 
     * @return l'objet Joueur correctement instancié
     */
    public Joueur(int px, int py) {

        // Appel du constructeur de la classe mère
        super(px, py, NB_PV_START, NB_PA_START, Direction.BAS);

        // Initialisation des attributs
        inventaire = new ArrayList<Potion>();
        rang = 1;
        competences = new ArrayList<Competence>();
        this.setArme(new Arme());

    }

    /**
     * Getter pour l'attribut inventaire
     * @return this.inventaire
     */
    public ArrayList<Potion> getInventaire(){
        return inventaire;
    }

    /**
     * Getter pour l'attribut experience
     * @return this.experience
     */
    public double getExperience(){
        return experience;
    }

    /**
     * Getter pour l'attribut rang
     * @return this.rang
     */
    public int getRang(){
        return rang;
    }

    /**
     * Getter pour l'attribut NB_PV_MAX
     * @return this.NB_PV_MAX
     */
    public int getNB_PV_MAX(){
        return NB_PV_MAX;
    }

    /**
     * Getter pour l'attribut NB_PA_MAX
     * @return this.NB_PA_MAX
     */
    public int getNB_PA_MAX(){
        return NB_PA_MAX;
    }

    /**
     * Getter sur l'attribut competencesSelect
     * @return this.competencesSelect
     */
    public int[] getCompetencesSelect(){
        return this.competencesSelect;
    }

    /**
     * Setter sur l'attribut competencesSelect
     * @param competences le nouveau tableau qui représente les compétences selectionnées
     */
    public void setCompetencesSelect(int[] competences){
        this.competencesSelect = competences;
    }

    /**
     * Méthode permattant d'ajouter une potion dans l'inventaire du joueur en respectant la taille limite de son inventaire
     * @param p la nouvelle potion à ajouter
     * @return true si la potion est bien ajoutée, false si inventaire plein
     */
    public boolean ajouterPotion(Potion p) {

        if(this.inventaire.size() < TAILLE_INVENTAIRE) {
            this.inventaire.add(p);
            return true;
        }
        
        return false;
    }

    /**
     * Méthode permettant de ramasser une pièce d'armure
     * @param p la pièce d'armure à ramasser
     * @return true si la pièce a bien été ramassée, false si les points d'amures déjà au max
     */
    public boolean ramasserArmure(PieceArmure p){

        // Si les points d'amures ne sont pas déjà au max
        if(this.getPointsArmure() < NB_PA_MAX){

            // On ajoute les points de la pièce d'armure à l'armure du joueur
            this.setPointsArmure(this.getPointsArmure()+p.getPointsArmure());

            // Si le nouveau nombre de points d'amure dépasse le maximum
            if(this.getPointsArmure() > NB_PA_MAX){
                // On mets le nombre de points d'armure au maximum
                this.setPointsArmure(NB_PA_MAX);
            }

            return true;
        }

        return false;
    }

    /**
     * Méthode permettant au joueur de boire une potion et de récupérer un certain montant de points de vie 
     * @param indice l'indice de l'emplacement de la position à boire dans la liste inventaire 
     * @return true une potion a bien été bu, false si pas de potion à cette emplacement de l'inventaire
     */
    public boolean boirePotion(int indice) {

        // Si l'indice correspond à un emplacement occupé de l'inventaire
        if(inventaire.size() > indice) {
            
            // On soigne le joueur du nombre de points de vie donné par la potion
            this.seSoigner(this.inventaire.get(indice).getAugmentation());
            // On retire la potion de l'inventaire
            this.inventaire.remove(indice);
            
            return true;
        }

        return false;
    }

    /**
     * Modifie les coordonnees du joueur en tenant des comptes des parametres
     * 
     * @param px deplacement en x
     * @param py deplacement en y
     */
    public void seDeplacer(int px, int py) {
      this.setX(px);
      this.setY(py);
    }

    /**
     * Méthode permettant de calculer la quantité d'expérience nécessaire pour passer au rang suivant
     * en partant de 0 (n'est pas pris en compte le montant actuel d'expérience du joueur)
     * @return la quantité d'expérience nécessaire 
     */
    public double calculerRangSuivant() {

        // Déclaration de la variable nécessaire
        double xp = 10000;

        // pour chanque rang jusqu'à celui actuel de joueur
        for(int i = 1; i < this.rang; i++) {
            // On ajoute 50% d'xp en plus
            xp = xp*1.5;
        }

        return xp;
    }

    /**
     * Méthode permettant de faire gagner une certaine quantité d'expérience au joueur et lui 
     * faire éventuellement passer au rang suivant si le montant d'expérience est suffisant
     * Permet également au joueur d'acquérir les améliorations liées au passage au rang suivant 
     * @param xp : montant d'expérience gagné par le joueur
     */
    public void gagnerExperience(double xp) {

        // On ajoute l'xp obtenu à l'attribut experience du joueur
        this.experience += xp;

        // Si l'experience dépasse celle requise pour incrémenter le niveau
        if(this.experience >= calculerRangSuivant()) {
            
            // On retire le nombre d'experience pour passer le niveau 
            this.experience = (this.experience + xp) - calculerRangSuivant();
            // On augmente le rang du joueur
            this.rang++;
            // On augmente le maximum de points de vie atteignable de 10
            NB_PV_MAX += 10;
            // Le joueur gagne 10 points de vie
            this.seSoigner(10);
            // On augmente le maximum de points d'armures atteignable de 10
            NB_PA_MAX += 10;

            // Si les compétences choisient avec le panel ne sont pas null (c'est le cas dans les tests)
            if(competencesSelect != null){
                
                // Ajout des quatres compétences choisient tous les deux niveaux
                if(this.rang == 2) ajouterCompetence(competencesSelect[0]);
                if(this.rang == 4) ajouterCompetence(competencesSelect[1]);
                if(this.rang == 6) ajouterCompetence(competencesSelect[2]);
                if(this.rang == 8) ajouterCompetence(competencesSelect[3]);
            }
        }
    }

    /**
     * Méthode qui permet de savoir si des monstres sont à portée d'attaque
     * @param entites objet ArrayList<Entite> contenant les entites du jeu
     * @param verrous objet ArrayList<Object> contenant les verrous intrasectes pour les entites du jeu
     * @return ArrayList<Entite> contenant les monstres touchées
     */
    @Override
    public ArrayList<Entite> attaquer(ArrayList<Entite> entites, ArrayList<Object> verrous) {

        // Déclaration et initialisation de la liste de retour
        ArrayList<Entite> entitesTouchees = new ArrayList<Entite>();

        // Section critique, on verrouille le verrou appartenant au joueur
        synchronized(verrous.get(0)){

            // On récupère la portée de l'arme du joueur
            int portee = this.getArme().getPortee();

            // on traite chaque cas de zone d'attaque
            switch (this.getArme().getZone()) {

                // Si l'arme a une zone d'attaque de type CASE_DEVANT
                case CASE_DEVANT :

                    // On étudie la direction du regard du joueur
                    switch (this.getRegard()) {
                        
                        // S'il regarde vers le haut
                        case HAUT :
                            // Pour toutes les entités du jeu
                            for(Entite ent : entites) {
                               // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent)+1)){
                                    // on teste si l'entité se situe sur les cases au dessus du joueur qui sont dans l'attaque
                                    for(int i = 1; i <= portee; i++) {             
                                        if(ent.getX() == this.getX()-i && ent.getY() == this.getY() && ent.getPointsVie() > 0) {
                                            // si oui on l'ajoute aux entités touchées
                                            entitesTouchees.add(ent);
                                        }
                                    }
                                }
                            }
                        break;
                        
                        // S'il regarde en bas
                        case BAS:
                            // Pour toutes les entités du jeu
                            for(Entite ent : entites) {
                                // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent)+1)){
                                    // on teste si l'entité se situe sur les cases en dessous du joueur qui sont dans l'attaque
                                    for(int i = 1; i <= portee; i++) {                        
                                        if(ent.getX() == this.getX()+i && ent.getY() == this.getY() && ent.getPointsVie() > 0) {
                                            // si oui on l'ajoute aux entités touchées
                                            entitesTouchees.add(ent);        
                                        }
                                    }
                                }
                            }    
                        break;
                        
                        // S'il regarde à droite
                        case DROITE:
                            // Pour toutes les entités du jeu
                            for(Entite ent : entites) {
                                // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent)+1)){
                                    // on teste si l'entité se situe sur les cases à droite du joueur qui sont dans l'attaque
                                    for(int i = 1; i <= portee; i++) {            
                                        if(ent.getX() == this.getX() && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {  
                                            // si oui on l'ajoute aux entités touchées
                                            entitesTouchees.add(ent);    
                                        }
                                    }
                                }
                            }    
                        break;

                        // S'il regarde à gauche
                        case GAUCHE:
                            // Pour toutes les entités du jeu
                            for(Entite ent : entites) {
                                // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent)+1)){
                                    // on teste si l'entité se situe sur les cases à gauche du joueur qui sont dans l'attaque
                                    for(int i = 1; i <= portee; i++) {      
                                        if(ent.getX() == this.getX() && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                            // si oui on l'ajoute aux entités touchées
                                            entitesTouchees.add(ent);
                                        }
                                    }
                                }
                            }
                        
                        break;
                    }
                break;
                
                // Si l'arme a une zone d'attaque de type ARC_DE_CERCLE
                case ARC_DE_CERCLE:

                    // On étudie le regard du joueur
                    switch (this.getRegard()) {

                        // S'il regarde vers le haut
                        case HAUT :
                            // Pour toutes les entités du jeus
                            for(Entite ent: entites) {

                                // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent))){
                                    // Boucle sur la portée de l'arme
                                    for(int i = 1; i <= portee; i++) {
                                        // l'entité se situe dans les cases en haut à gauche du joueur
                                        if(ent.getX() == this.getX()-i && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases au dessus du joueur
                                        else if(ent.getX() == this.getX()-i && ent.getY() == this.getY() && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases en haut à droite du joueur
                                        else if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i  && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }
                                    }
                                }
                            }
                        break;

                        // S'il regarde en bas
                        case BAS:
                            // Pour toutes les entités du jeu
                            for(Entite ent: entites) {
                                // Section critique, on verouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent))){
                                    // Boucle sur la portée de l'arme
                                    for (int i = 1; i <= portee; i++) {
                                        // l'entité se situe dans les cases en bas à gauche du joueur
                                        if(ent.getX() == this.getX()+i && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases en dessous du joueur
                                        else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()  && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases en bas à droite du joueur
                                        else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }
                                    }
                                }
                            }
                        break;
                        
                        // S'il regarde à droite
                        case DROITE:
                            // Pour toutes les entités du jeu
                            for(Entite ent: entites) {
                                // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent))){
                                    // Boucle sur la portée de l'arme
                                    for (int i = 1; i <= portee; i++) {
                                        // l'entité se situe dans les cases en haut à gauche du joueur
                                        if(ent.getX() == this.getX()-i && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases à gauche du joueur
                                        else if(ent.getX() == this.getX() && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases en bas à gauche du joueur
                                        else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }
                                    }
                                }
                            }
                        break;
                        
                        // S'il regarde à gauche
                        case GAUCHE:
                            // Pour toutes les entités du jeu
                            for(Entite ent: entites) {
                                // Section critique, on verrouille le verrou appartenant à l'entité
                                synchronized(verrous.get(entites.indexOf(ent))){
                                    // Boucle sur la portée de l'arme
                                    for (int i = 1; i <= portee; i++) {

                                        // l'entité se situe dans les cases en haut à droite du joueur
                                        if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }

                                        // l'entité se situe dans les cases à droite du joueur
                                        else if(ent.getX() == this.getX() && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }
                                        
                                        // l'entité se situe dans les cases en bas à droite du joueur
                                        else if(ent.getX() == this.getX()+i && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                            entitesTouchees.add(ent);
                                        }
                                    }
                                
                                }
                            }
                        break;
                    }
                break;

                // Si l'arme a une zone d'attaque de type EN_CARRE
                case EN_CARRE:
                    // Pour toutes les entités du jeu
                    for(Entite ent : entites) {
                        // Section critique, on vérouille le vérrou appartenant à l'entité
                        synchronized(verrous.get(entites.indexOf(ent))){
                            // Boucle sur la portée de l'arme
                            for(int i = 1; i <= portee; i++) {
                                // l'entité se situe dans les cases au dessus du joueur
                                if(ent.getX() == this.getX()-i && ent.getY() == this.getY() && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases en dessous du joueur
                                if(ent.getX() == this.getX()+i && ent.getY() == this.getY() && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases à gauche du joueur
                                if(ent.getX() == this.getX() && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases à droite du joueur
                                if(ent.getX() == this.getX() && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases en haut à droite du joueur
                                if(ent.getX() == this.getX()-i && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases en bas à droite du joueur
                                if(ent.getX() == this.getX()+i && ent.getY() == this.getY()+i && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases en haut à gauche du joueur
                                if(ent.getX() == this.getX()-i && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }

                                // l'entité se situe dans les cases en bas à gauche du joueur
                                if(ent.getX() == this.getX()+i && ent.getY() == this.getY()-i && ent.getPointsVie() > 0) {
                                    entitesTouchees.add(ent);
                                }
                            }
                        }
                    }
                break;
            }

            // si la compétence de drain de vie est activée on récupère des pv pour chaque attaque réussie
            if(!entitesTouchees.isEmpty() && this.drain) {
                for(Entite e : entitesTouchees) {
                    this.seSoigner(this.getArme().getDegats());
                }
            }

            // Déclaration d'une variable contenant le chemin vers le fichier son
            String sound;

            // Si l'arme est l'Arc
            if(this.getArme().getNom().equals("Arc")){
                sound = "/sounds/arc.wav"; // Sond prend la valeur du chemin vers le fichier son de l'arc
            } else if(this.getArme().getNom().equals("Sabre")){ // Si l'arme est le Sabre
                sound = "/sounds/sabre.wav"; // Sond prend la valeur du chemin vers le fichier son de du sabre
            } else if(this.getArme().getNom().equals("Bombes")){ // Si l'amre est les Bombes
                sound = "/sounds/bombe.wav"; // Sond prend la valeur du chemin vers le fichier son de la bombe
            } else { // Si l'arme est l'épée en bois
                sound = "/sounds/epee_bois.wav"; // Sond prend la valeur du chemin vers le fichier son de l'épée en bois
            }

            // On lit et déclenche le son qui se trouve au chemin contenu dans la variable sound
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(sound));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) { System.out.println(e.getMessage()); }

        }

        return entitesTouchees;
    }

    /**
     * Méthode qui lance une des compétences du joueur
     * @param num l'indice de la compétence à délcencher dans la liste de compétence du joueur
     * @return true si une compétence a bien été déclenchée, false sinon
     */
    public boolean lancerCompetence(int num) {
 
        // si la compétence est activable et si la compétence num est dans la liste des compétences débloquées
        if(num < this.competences.size() && this.competences.get(num).isActivable()) {
            // on regarde quelle est la compétence
            switch(this.competences.get(num).getType()) {
                // La compétence BERSERKER va infliger 20 PV au joueur et augmenter les dégâts de son arme de 50% pendant 10s
                case BERSERKER:
                    // On récupère les dégâts actuels de l'arme du joueur
                    int dgts = this.getArme().getDegats();
                    // On inflige 20 PV au joueur
                    this.prendreDegat(20);
                    // On augmente les dégâts de son arme de 50%
                    this.getArme().setDegats(dgts + dgts/2);
                    // La compétence n'est plus activable
                    this.competences.get(num).setActivable(false);

                    // Création d'un timer
                    Timer t = new Timer();

                    // Ajout d'une action au bout du temps de recharge (via le timer)
                    t.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Au bout de 10s on rétablit les dégâts initiaux de l'arme
                                    // et la compétence est de nouveau disponible
                                    getArme().setDegats(dgts);
                                    competences.get(num).setActivable(true);
                                    // On coupe le thread du timer
                                    t.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                break;

                // La compétence BOUCLIER MAGIQUE octroie 25 points d'armure supplémentaires au joueur
                // cependant ils ne pourront pas être récupérés après avoir subi des dégâts
                // La compétence peut être réactivées après deux niveaux du labyrinthe effectués
                case BOUCLIER_MAGIQUE :
                    // On augmente l'armure de 25 PA
                    this.setPointsArmure(this.getPointsArmure()+25);
                    // La compétence n'est plus disponible
                    this.competences.get(num).setActivable(false);
                break;

                // La compétence DRAIN DE VIE va soigner le joueur le montant de dégâts qu'il a infligé pendant une période de 10s
                // Cette compétence peut s'activer une seule fois par niveau
                case DRAIN_VIE:
                    // On indique que le drain est activé ce qui va être testé dans attaquer()
                    this.drain = true;
                    // La compétence n'est plus activable
                    this.competences.get(num).setActivable(false);

                    // Création d'un timer
                    Timer t1 = new Timer();

                    // Ajout d'un action au bout du temps de recharge (via le timer)
                    t1.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Au bout de 10s, le drain n'est plus activé
                                    drain = false;
                                    // On ferme le thread du timer
                                    t1.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                break;

                // La compétence REVENANT permet de revenir à la vie avec 20PV, cette compétence n'est utilisable qu'une fois par partie
                case REVENANT:
                    // On indique que la compétence est active pour ensuite la tester lors des scénarios de mort du joueur
                    this.revenant = true;
                    // la compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                break;

                // La compétence BLOCAGE va permettre au Joueur de subir un quart de dégâts en moins pendant 10s
                // Utilisable une seule fois par niveau
                case BLOCAGE:
                    
                    // On indique que la compétence est active pour pouvoir réduire les dégâts lors de l'attaque réussie d'un monstre
                    this.blocage = true;
                    // la compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    
                    // Création d'un timer
                    Timer t2 = new Timer();

                    // Ajout d'une action au bout du temps de recharge (via le timer)
                    t2.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Au bout de 10s, le drain n'est plus activé
                                    blocage = false;
                                    // On ferme le thread du timer
                                    t2.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                break;

                // La compétence EPINES va permettre d'infliger au Monstre qui attaque le joueur 25% des dégâts de l'attaque subie pendant 10s
                // Utilisable une seule fois par niveau
                case EPINES:
                    // On indique que la compétence est active pour pouvoir soigner le Joueur lors de l'attaque réussie d'un monstre
                    this.epines = true;
                    // la compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    
                    // Création d'un nouveau timer
                    Timer t3 = new Timer();

                    // Ajout d'une action au bout du temps de recharge (via le timer)
                    t3.schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    // Au bout de tempsRecharge, les épines ne sont plus actives
                                    epines = false;
                                    // On ferme le thread du timer
                                    t3.cancel();
                                }
                            },
                            competences.get(num).getTempsRecharge()
                    );
                break;

                // La compétence passive ANGUILLE permet d'octroyer au Joueur une chance de 10% d'esquiver une attaque subie
                case ANGUILLE:
                    // On indique que la compétence est active pour pouvoir faire le test lors de l'attaque réussie d'un monstre
                    this.anguille = true;
                    // la compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    break;

                // La compétence TELEPORTATION permet au Joueur de se déplacer de deux cases dans la direction où il regarde
                // Utilisable une fois par niveau
                case TELEPORTATION:
                    // la compétence n'est plus activable
                    this.competences.get(num).setActivable(false);
                    break;
            }

            return true;
        }

        return false;
    }

    /**
     * Renvoie la liste des compétences débloquées par le joueur
     * @return l'attribut competences
     */
    public ArrayList<Competence> getCompetences() {
        return competences;
    }

    /**
     * Ajoute une compétence à la liste des compétences débloquées par le joueur
     * @param nb compétence qui vient d'être débloquée par le joueur (1 à 8, dans l'ordre du panel de choix des compétences)
     */
    public void ajouterCompetence(int nb) {

        // Déclaration de la nouvelle compétence à créer
        Competence competence;

        // Création de la nouvelle compétence
        switch(nb){
            case 0:
                competence = new Competence(TypeCompetence.BERSERKER, 2, 10000, 0);
                break;
            case 1:
                competence = new Competence(TypeCompetence.BOUCLIER_MAGIQUE, 3, 0, 2);
                break;
            case 2:
                competence = new Competence(TypeCompetence.DRAIN_VIE, 4, 10000, 1);
                break;
            case 3:
                competence = new Competence(TypeCompetence.REVENANT, 5, 0, 0);
                break;
            case 4:
                competence = new Competence(TypeCompetence.BLOCAGE, 2, 10000, 1);
                break;
            case 5:
                competence = new Competence(TypeCompetence.EPINES, 3, 10000, 1);
                break;
            case 6: 
                competence = new Competence(TypeCompetence.ANGUILLE, 5, 0, 0);
                break;
            case 7:
                competence = new Competence(TypeCompetence.TELEPORTATION, 4, 0, 1);
                break;
            default:
                competence = null;
        }

        // On ajoute la nouvelle competence crée à la liste des competences du joueur
        this.competences.add(competence);
    }

    /**
     * Renvoie si la compétence REVENANT est active ou non
     * @return l'attribut revenant
     */
    public boolean isRevenant() {
        return revenant;
    }

    /**
     * Définit si la compétence REVENANT est active ou non
     * @param revenant
     */
    public void setRevenant(boolean revenant) {
        this.revenant = revenant;
    }

    /**
     * Getter sur l'attribut blocage du joueur
     * @return this.blocage
     */
    public boolean isBlocage() {
        return blocage;
    }

    /**
     * Méthode qui soigne le joueur à hauteur de pv
     * @param pv le montant de soin à attribuer
     */
    public void seSoigner(int pv) {

        // Si l'ajout va dépasser le nombre max de PV
        if(this.getPointsVie()+pv > NB_PV_MAX) {
            // On set les points de vie au nombre de points de vie max
            this.setPointsVie(NB_PV_MAX);
        } else { // Sinon
            // On set les points de vie avec la valeur des points de vie actuels + ceux que l'on doit attribuer
            this.setPointsVie(this.getPointsVie()+pv);
        }
    }


    /**
     * Méthode permettant au joueur de récupérer des points d'armure sans dépasser la limite de points d'armure
     * @param pa : montant de points d'armure à récupérer
     */
    public void recupererPA(int pa) {

        // Si l'ajout va dépasse le nombre max de PA
        if(this.getPointsArmure()+pa > NB_PA_MAX) {
            // On set les points d'armure au nombre de points d'armure max
            this.setPointsArmure(NB_PA_MAX);
        } else {// Sinon
            // on set les points d'armure avec la valeur des points d'armures actuels + ceux que l'on doit attribuer
            this.setPointsArmure(this.getPointsArmure()+pa);
        }
    }

    /**
     * Getter sur l'attribut epines du joueur
     * @return this.epines
     */
    public boolean isEpines() {
        return epines;
    }

    /**
     * Getter sur l'attribut anguille du joueur
     * @return this.anguille
     */
    public boolean isAnguille() {
        return anguille;
    }

    /**
     * Permet de représenter l'objet joueur sous la forme d'un objet String
     * @return "J"
     */
    @Override
    public String toString(){
        return "J";
    }
}