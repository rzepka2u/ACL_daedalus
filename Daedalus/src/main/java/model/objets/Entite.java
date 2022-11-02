package model.objets;

public abstract class Entite {
    
    private int x;
    private int y;
    private int pointsVie;
    private int pointsArmures;
    private Arme arme;

    public Entite(int x, int y, int pv, int pa){
        this.x = x;
        this.y = y;
        this.pointsVie = pv;
        this.pointsArmures = pa;
        this.arme = null;
    }
    
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getPointsVie(){
        return this.pointsVie;
    }

    public int getPointsArmures(){
        return this.pointsArmures;
    }

    public Arme getArme(){
        return this.arme;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public abstract void seDeplacer(int px, int py);
}
