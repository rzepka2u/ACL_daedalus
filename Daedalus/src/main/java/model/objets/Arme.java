package model.objets;

import model.enums.ZoneAttaque;

public class Arme implements Tresor {

    private String nom;
    private int degats;
    private int portee;
    private ZoneAttaque zone;

    public Arme(String nom, int degats, int portee, ZoneAttaque zone){
        this.nom = nom;
        this.degats = degats;
        this.portee = portee;
        this.zone = zone;
    }

    public String getNom(){
        return nom;
    }

}
