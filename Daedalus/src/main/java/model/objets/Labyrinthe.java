package model.objets;

import model.cases.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
* La classe qui représente le labyrinthe du jeu
*/

public class Labyrinthe {

    /**
     * Cases du labyrinthe
     */
    private ArrayList<ArrayList<Case>> cases;

    private int hauteur, largeur;

    /**
     * Constructeur par defaut du labyrinthe
     */
    public Labyrinthe() {
        this.initialiserParDefaut();
    }

    /**
     * Constructeur par defaut du labyrinthe
     */
    public Labyrinthe(String path) throws IOException {
        this.initialiserParFichier(path);
    }

    /**
     * Constructeur du labyrinthe généré automatiquement
     * TODO sprint suivant
     * @param largeur largeur du labyrinthe
     * @param hauteur largeur du labyrinthe
     */
    public Labyrinthe(int largeur, int hauteur) {
        this.cases = new ArrayList<ArrayList<Case>>();
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.initialiserParGenerationAutomatique();
    }

    /**
     * Initialise le labyrinthe
     */
    public void initialiserParDefaut() {
        /*
         * LEGENDE :
         * - 0 = case vide
         * - 1 = mur
         * - 2 = piege dans le futur, Case vide pout le moment TODO
         * - 3 = sortie
         */
        int[][] casesTemplate = {
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 },
                { 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0 ,0 ,1 ,0 ,1 },
                { 1, 0, 2, 1, 0, 1, 0, 0, 0, 0, 1 ,0 ,0 ,3 ,1 },
                { 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1 ,0 ,0 ,1 ,1 },
                { 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0 ,0 ,0 ,0 ,1 },
                { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 ,0 ,1 ,0 ,1 },
                { 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1 ,2 ,1 ,0 ,1 },
                { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 ,0 ,0 ,1 ,1 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 ,0 ,0 ,0 ,1 },
                { 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1 ,1 ,0 ,0 ,1 },
                { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 ,0 ,0 ,0 ,1 },
                { 1, 0, 1, 1, 0, 2, 0, 0, 1, 0, 0 ,0 ,1 ,1 ,1 },
                { 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 ,0 ,1 ,0 ,1 },
                { 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1 ,0 ,0 ,0 ,1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 }, };

        this.generer(casesTemplate);
    }

    /**
     * Initialise le labyrinthe selon un fichier
     * @param path Chemin vers le fichier de génération du labyrinthe
     */
    public void initialiserParFichier(String path) throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(path));
        String fileLine = "";

        // definition des dimensions du labyrinthe
        int nbColonnes = 0;
        int nbLignes = 0;

        while ((fileLine = buffer.readLine()) != null) {
            nbColonnes = fileLine.length();
            nbLignes++;
        }
        // initialisation du tableau de cases
        int[][] casesTemplate = new int[nbLignes][nbColonnes];

        // lecture du contenu du fichier
        buffer = new BufferedReader(new FileReader(path));

        // transposition de la lecture du fichier labyrinthe dans le tableau
        int ligne = 0;
        while ((fileLine = buffer.readLine()) != null) {
            for (int colonne = 0; colonne < fileLine.length(); colonne++) {
                casesTemplate[ligne][colonne] = Character.getNumericValue(fileLine.charAt(colonne));
            }
            ligne++;
        }
        buffer.close();

        this.generer(casesTemplate);
    }

    public void generer(int[][] casesTemplate) {
        this.cases = new ArrayList<ArrayList<Case>>();
        for (int i = 0; i < casesTemplate.length; i++) {
            this.cases.add(new ArrayList<Case>());

            for (int j = 0; j < casesTemplate[i].length; j++)  {
                switch (casesTemplate[i][j]) {
                    case 1 -> this.cases.get(i).add(new CaseMur());
                    case 2 -> this.cases.get(i).add(new CaseVide()); // TODO
                    case 3 -> this.cases.get(i).add(new CaseSortie());
                    default -> this.cases.get(i).add(new CaseVide());
                }
            }
        }
        this.largeur = this.cases.get(0).size();
        this.hauteur = this.cases.size();
    }

    /**
     * Initialise le labyrinthe selon le niveau
     */
    public void initialiserParGenerationAutomatique() {
        // TODO
    }

    /**
     * Recupere la case aux coordonnes donnees
     *
     * @param x position x
     * @param y position y
     * @return case correspondante ou null si pas de case
     */
    public Case getCase(int x, int y) {
        if ((x >= 0 && x <= this.largeur) && (y >= 0 && y <= this.hauteur)) {
            return this.cases.get(x).get(y);
        }
        return null;
    }

    public ArrayList<ArrayList<Case>> getCases(){
        return this.cases;
    }

    /**
     * Recupere la largeur du labyrinthe
     *
     * @return largeur du labyrinthe
     */
    public int getLargeur() {
        return this.largeur;
    }

    /**
     * Recupere la hauteur du labyrinthe
     *
     * @return hauteur du labyrinthe
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Coordonnees X du milieu
     *
     * @return x du milieu
     */
    public int getXMilieu() {
        return (int) Math.floor(this.getLargeur() / 2.0);

    }

    /**
     * Coordonnees Y du milieu
     *
     * @return y du milieu
     */
    public int getYMilieu() {
        return (int) Math.floor(this.getHauteur() / 2.0);
    }

    /**
     * Representation du labyrinthe sous forme de chaine de caracteres
     *
     * @return labyrinthe sous forme de chaine de caracteres
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for (ArrayList<Case> aCase : this.cases) {
            for (Case value : aCase) {
                res.append(value);
                res.append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }

}
