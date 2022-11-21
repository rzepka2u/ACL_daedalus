package model.objets;

import model.enums.ZoneAttaque;

public class Arme implements Tresor {

    // le nom de l'arme
    private String nom;
    // les dégâts qu'elle inflige
    private int degats;
    // la portée de son attaque
    private int portee;
    // la zone effective de son attaque
    private ZoneAttaque zone;

    /**
     * Constructeur avec paramètres de l'arme
     * @param nom de l'arme
     * @param degats de l'arme
     * @param portee de l'arme
     * @param zone d'attaque de l'arme
     */
    public Arme(String nom, int degats, int portee, ZoneAttaque zone){
        this.nom = nom;
        this.degats = degats;
        this.portee = portee;
        this.zone = zone;
    }

    /**
     * Constructeur vide d'une arme qui construit une arme de base
     */
    public Arme() {
        this.nom = "Epée en bois";
        this.degats = 10;
        this.portee = 1;
        this.zone = ZoneAttaque.CASE_DEVANT;
    }

    public static Arme armeAlea(){
        Arme arme;

        if( Math.random() > 0.5 ){
            arme = new Arme("Arc", 15, 4, ZoneAttaque.CASE_DEVANT);
        } else {    
            if( Math.random() > 0.5){
                arme = new Arme("Sabre", 20, 1, ZoneAttaque.ARC_DE_CERCLE); 
            } else {
                arme = new Arme("Bombe", 13, 2, ZoneAttaque.EN_CARRE);
            }
        }

        return arme;
    }

    /**
     * Renvoie le nom de l'arme
     * @return l'attribut nom
     */
    public String getNom(){
        return nom;
    }

    /**
     * Renvoie les dégâts de l'arme
     * @return l'attribut degats
     */
    public int getDegats() {
        return this.degats;
    }

    /**
     * Renvoie la portée de l'arme
     * @return l'attribut portee
     */
    public int getPortee() {
        return this.portee;
    }

    /**
     * Renvoie la zone d'attaque de l'arme
     * @return l'attribut zone
     */
    public ZoneAttaque getZone() {
        return zone;
    }

    public void setDegats(int i) {
        this.degats = i;
    }
}
