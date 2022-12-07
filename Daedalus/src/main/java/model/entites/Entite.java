package model.entites;

import java.io.Serializable;
import java.util.ArrayList;
import model.enums.Direction;
import model.tresors.Arme;


/**
 * Représente une entité dans le jeu
 */
public abstract class Entite implements Serializable {

    // Attributs constants de classe     
    protected static final int NB_PV_START = 100; // Nombre de points de vie de démarrage (si non renseigné)
    protected static final int NB_PA_START = 50; // Nombre de points d'armure de démarrage (si non renseigné)

    // Nombre de points de vie maximum du joueur évoluant au fil de la partie et des rangs gagnés
    protected static int NB_PV_MAX = 100; 
    
    /**
     * Coordonnee x de l'entite
     */
    private int x;

    /**
     * Coordonnee y de l'entite
     */
    private int y;

    /**
     * Points de vie de l'entite
     */
    private int pointsVie;

    /**
     * Points d'armure de l'entite
     */
    private int pointsArmure;

    /**
     * L'arme de l'entite
     */
    private Arme arme;

    /**
     * La direction du regard de l'entite
     */
    private Direction regard;


    /**
     * Constructeur de la classe Entite
     * @param x coordonnées en x
     * @param y coordonnées en y
     * @param pv points de vie
     * @param pa points d'armure
     * @return l'objet Entité correctement instancié
     */
    public Entite(int x, int y, int pv, int pa, Direction regard){
        this.x = x;
        this.y = y;
        this.pointsVie = pv;
        this.pointsArmure = pa;
        this.regard = regard;
        this.arme = null;
    }

    /**
     * Getter sur l'attribut regard
     * @return this.regard
     */
    public Direction getRegard(){
        return regard;
    }

    /**
     * Setter sur l'attribut regard
     * @param sens La nouvelle direction du regard
     */
    public void setRegard(Direction sens){
        regard = sens;
    }

    /**
     * Modifie les coordonnees de l'entité en tenant des comptes des parametres
     *
     * @param px deplacement en x
     * @param py deplacement en y
     */
    public abstract void seDeplacer(int px, int py);

    /**
     * Effectue la prise de dégats par l'entité
     * @param retrait le nombre de pv à retirer
     * @return true si l'entité meurt à la suite de l'attaque
     */
    public boolean prendreDegat(int retrait) {

        // Déclaration et initialisation d'un booléen pour tester la mort
        boolean mort = false;

        // si les dégats sont supérieurs au cumul d'armure et vie
        if(this.pointsVie + this.pointsArmure - retrait <= 0) {
            
            mort = true;
        
        } else { // si les dégats sont inférieurs au cumul d'armure et vie
            
            // S'il n'y a pas d'armure
            if(this.pointsArmure == 0) {

                // on retire des pv
                this.pointsVie -= retrait;

            } else { // S'il y a de l'armure
                
                // si l'armure peut encaisser tous les dégâts
                if(this.pointsArmure >= retrait) {
                    // on retire les points d'amures
                    this.pointsArmure -= retrait;
                } else { // Si l'armure ne peut pas encaisser tous les dégats 
                    
                    // on soustrait les pts d'armure au retrait
                    retrait -= pointsArmure;
                    // l'armure est brisée
                    this.pointsArmure = 0;
                    // on retire des pv avec le retrait restant
                    this.pointsVie -= retrait;
                }
            }

        }

        return mort;
    }

    /**
     * Méthode qui permet de savoir si le joueur est à portée d'attaque
     * @param entites liste des entités présentes dans le labyrinthe
     * @param verrous liste des verrous de synchronisation pour les entites 
     * @return la liste contenant le joueur s'il est touché par l'attaque, liste vide sinon.
     */
    public abstract ArrayList<Entite> attaquer(ArrayList<Entite> entites, ArrayList<Object> verrous);

    /**
     * Renvoie les coordonnees en x de l'entité
     * @return l'attribut x
     */
    public int getX(){
        return this.x;
    }

    /**
     * Renvoie les coordonnees en y de l'entité
     * @return l'attribut x
     */
    public int getY(){
        return this.y;
    }


    /**
     * Renvoie les points de vie de l'entité
     * @return l'attribut pointsVie
     */
    public int getPointsVie(){
        return this.pointsVie;
    }

    /**
     * Setter sur les points de vie de l'entité
     * @param pointsVie le nouveau nombre de points de vie
     */
    public void setPointsVie(int pointsVie) {
        this.pointsVie = pointsVie;
    }

    /**
     * Renvoie les points d'armure de l'entité
     * @return l'attribut pointsArmure
     */
    public int getPointsArmure(){
        return this.pointsArmure;
    }

    /**
     * Setter sur les points d'armure de l'entité
     * @param pointsArmure le nouveau nombre de points d'armure
     */
    public void setPointsArmure(int pointsArmure) {
        this.pointsArmure = pointsArmure;
    }

    /**
     * Renvoie l'arme de l'entité
     * @return l'attribut arme
     */
    public Arme getArme(){
        return this.arme;
    }

    /**
     * Défini l'arme de l'entité
     * @param a arme à ajouter
     */
    public void setArme(Arme a){
        this.arme = a;
    }

    /**
     * Défini les coordonnées en y de l'entité
     * @param y coordonnée en y
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Definit les coordonnées en x de l'entité
     * @param x coordonnée en x
     */
    public void setX(int x){
        this.x = x;
    }


    /**
     * Méthode qui permet d'ajouter des points de vie à l'entité
     * @param effet le nombre de points de vie à ajouter
     */
    public void modifierPV(int effet) {

        // On ajoute l'effet seulement si le résultat ne dépasse pas le nombre de points de vie max
        if(pointsVie + effet <= NB_PV_MAX)
            pointsVie += effet;
    }

    /**
     * Méthode qui permet de savoir si l'entité est morte
     * @return true si l'entité est morte, false sinon
     */
    public boolean etreMort() {
        return this.pointsVie > 0;
    }
}
