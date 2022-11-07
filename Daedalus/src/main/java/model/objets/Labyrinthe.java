package model.objets;

import model.cases.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe qui représente le labyrinthe du jeu
 */

public class Labyrinthe {

    /**
     * Cases du labyrinthe
     */
    private ArrayList<ArrayList<Case>> cases;
    private ArrayList<ArrayList<Object>> verrousCases;

    private Case[][] grilleAlea;

    /**
     * Dimensions du labyrinthe (taille est utilisé pour la génération aléatoire du labyrinthe)
     */
    private int hauteur, largeur, taille;

    /**
     * Constructeur par defaut du labyrinthe
     */
    public Labyrinthe() {
        this.initialiserParDefaut();
    }

    /**
     * Constructeur par du labyrinthe par fichier
     *
     * @param path chemin vers le fichier de génération
     */
    public Labyrinthe(String path) throws FileNotFoundException {
        this.initialiserParFichier(path);
    }

    /**
     * Constructeur du labyrinthe généré automatiquement
     *
     * @param dimension taille du côté du labyrinthe
     */
    public Labyrinthe(int dimension) {
        if (dimension % 2 != 0) dimension++;
        this.cases = new ArrayList<>();
        this.taille = dimension;
        this.initialiserPourGenerationAleatoire();
        this.creerCheminLabyrintheAleatoire();
        int[][] tab = convertirFormatAleaEnInt();
        this.genererDepuisEntiers(tab);
    }

    /**
     * Initialise le labyrinthe
     */
    public void initialiserParDefaut() {
        /*
         * LEGENDE :
         * - 0 = case vide
         * - 1 = mur
         * - 2 = piege dans le futur
         * - 3 = sortie
         */
        int[][] casesTemplate = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 2, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 3, 1},
                {1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 2, 1, 0, 1},
                {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 2, 0, 0, 1, 0, 0, 0, 1, 1, 1},
                {1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};

        this.genererDepuisEntiers(casesTemplate);
    }

    /**
     * Initialise le labyrinthe selon un fichier
     *
     * @param path Chemin vers le fichier de génération du labyrinthe
     */
    public void initialiserParFichier(String path) throws FileNotFoundException {
        String fileLine = "";

        // definition des dimensions du labyrinthe
        int nbColonnes = 0;
        int nbLignes = 0;

        // Analyse de la taille du futur labyrinthe
        try {
            // Création d'un flux de caractère entrant provenant du fichier se trouvant au chemin path
            FileReader fr = new FileReader(path);

            // Création d'un tampon pour gérer ce flux et réduire le nombre d'opérations au niveau du système
            BufferedReader buffer = new BufferedReader(fr);

            // Calcul du nombre de lignes et du nombre de colonnes du futur labyrinthe en parcourant le fichier
            while ((fileLine = buffer.readLine()) != null) {
                if (nbColonnes == 0) nbColonnes = fileLine.length();
                nbLignes++;
            }

            // Fermeture du flux ainsi que du tampon
            fr.close();
            buffer.close();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
            /*System.err.println("Impossible de trouver le fichier \""+path+"\".");
            System.exit(-1);*/

        } catch (IOException e) {
            /*System.err.println("Erreur lors de la lecture du fichier du labyrinthe: "+ e);
            System.exit(-1);*/
        }

        // initialisation de la matrice de cases
        int[][] casesTemplate = new int[nbLignes][nbColonnes];

        // Analyse du contenu réel du fichier (pas seulement les lignes et les colonnes)
        try {
            // Création d'un flux de caractère entrant provenant du fichier se trouvant au chemin path
            FileReader fr = new FileReader(path);

            // Création d'un tampon pour gérer ce flux et reduire le nombre d'opérations au niveau du système
            BufferedReader buffer = new BufferedReader(fr);

            // Transposition des cases du fichier labyrinthe dans la matrice
            int ligne = 0;
            while ((fileLine = buffer.readLine()) != null) {
                for (int colonne = 0; colonne < fileLine.length(); colonne++) {
                    casesTemplate[ligne][colonne] = Character.getNumericValue(fileLine.charAt(colonne));
                }
                ligne++;
            }

            // Fermeture du flux ainsi que du tampon
            fr.close();
            buffer.close();

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
            /*System.err.println("Impossible de trouver le fichier \""+path+"\".");*/
            //System.exit(-1);

        } catch (IOException e) {

            System.err.println("Erreur lors de la lecture dans le fichier du labyrinthe: " + e);
            System.exit(-1);

        }
        this.genererDepuisEntiers(casesTemplate);
    }

    /**
     * Méthode pour générer les cases du labyrinthe sous formes d'objes depuis un tableau d'entiers
     *
     * @param casesTemplate tableau d'entiers à double dimension
     */
    public void genererDepuisEntiers(int[][] casesTemplate) {
        // On initialise la liste des cases
        this.cases = new ArrayList<>();
        this.verrousCases = new ArrayList<>();

        // On parcourt le tableau en hauteur
        for (int i = 0; i < casesTemplate.length; i++) {
            this.cases.add(new ArrayList<>());
            this.verrousCases.add(new ArrayList<>());

            // On parcourt le tableau en largeur
            for (int j = 0; j < casesTemplate[i].length; j++) {
                verrousCases.get(i).add(new Object());

                // Pour chaque entier, on ajoute dans la liste des cases un objet correspondant à l'entier
                switch (casesTemplate[i][j]) {
                    case 1 -> this.cases.get(i).add(new CaseMur(-1, new Coordonnee(i, j)));
                    //case 2 -> this.cases.get(i).add(new CaseVide(-1, new Coordonnee(i,j))); // TODO
                    case 3 -> this.cases.get(i).add(new CaseSortie(-1, new Coordonnee(i, j)));
                    default -> this.cases.get(i).add(new CaseVide(-1, new Coordonnee(i, j)));
                }
            }
        }
        this.largeur = this.cases.get(0).size();
        this.hauteur = this.cases.size();
    }

    /**
     * Initialise le labyrinthe pour la génération aléatoire
     */
    public void initialiserPourGenerationAleatoire() {
        this.grilleAlea = new Case[this.taille + 2][this.taille + 2];

        // L'algorithme associe une valeur unique à chaque cellule et part d'un labyrinthe où tous les murs sont fermés.
        for (int i = 0; i < this.taille + 2; i++) {
            this.grilleAlea[i][0] = new CaseMur(-1, new Coordonnee(i, 0));
            this.grilleAlea[0][i] = new CaseMur(-1, new Coordonnee(0, i));
            this.grilleAlea[this.taille][i] = new CaseMur(-1, new Coordonnee(this.taille, i));
            this.grilleAlea[i][this.taille] = new CaseMur(-1, new Coordonnee(i, this.taille));
        }

        int nbPassages = 0;
        int nbIteration = 0;

        for (int i = 1; i < this.taille; i++) {
            for (int j = 1; j < this.taille; j++) {
                nbPassages++;
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        nbIteration++;
                        this.grilleAlea[j][i] = new CaseVide(nbIteration, new Coordonnee(j, i));
                    } else {
                        this.grilleAlea[j][i] = new CaseMur(nbPassages / 2, new Coordonnee(j, i));
                    }
                } else {
                    if (j % 2 == 0) {
                        this.grilleAlea[j][i] = new CaseMur(-1, new Coordonnee(j, i));
                    } else {
                        this.grilleAlea[j][i] = new CaseMur(nbPassages / 2, new Coordonnee(j, i));
                    }
                }
            }
        }
    }

    /**
     * Méthode qui modifie la grille afin de créer un chemin complet
     * L'algorithme implémenté est celui de la "Fusion aléatoire de chemins"
     * (pour la création d'un Labyrinthe aléatoire)
     */
    public void creerCheminLabyrintheAleatoire() {
        // Liste de coordonnées qui va à plusieurs reprises être utilisée puis clear. (elle va contenir des groupes de cases vides que l'algo va utiliser pour faire les couloirs)
        ArrayList<Coordonnee> coordonnees;
        Coordonnee coord;

        // À chaque itération, on choisit un mur à ouvrir de manière aléatoire, par conséquent on doit avoir une variable qui sera aléatoire.
        int nombreAleatoire, tmp, tmp2;

        // nombre de découpes (nombre de murs ouverts)
        int decoupes = 0;

        // Le chemin obtenu est unique lorsque le nombre de murs ouverts est égal au carré de la moitié de la taille du côté, moins 1.
        while (decoupes < ((this.taille / 2) * (this.taille / 2)) - 1) {
            nombreAleatoire = (int) (Math.random() * ((((this.taille / 2) * (this.taille / 2)) * 2) - ((this.taille / 2) * 2))) + 1;

            // On récupère les coordonnées d'une CaseMure ayant comme identifiant nombreAléatoire
            coord = rechercherCoordonner("CaseMur", nombreAleatoire);

            // Si c'est bien un Objet CaseMur d'identifiant différent de -1 (par défaut), alors
            if ((this.grilleAlea[coord.getX()][coord.getY()] instanceof CaseMur) && (this.grilleAlea[coord.getX()][coord.getY()].getId() != -1)) {

                // On regarde si le modulo de l'ordonnée vaut 0.
                if (coord.getY() % 2 == 0) {

                    // On enregistre les cases au-dessus et en dessous.
                    tmp = coord.getY() + 1;
                    tmp2 = coord.getY() - 1;


                    if (this.grilleAlea[coord.getX()][tmp].getId() != this.grilleAlea[coord.getX()][tmp2].getId()) {
                        // Pour simplifier on modifie seulement le char associé à la case
                        this.grilleAlea[coord.getX()][coord.getY()].setChar('.');

                        // On récupère les coordonnées des CaseVides ayant comme identifiant celui de la case enregistrée dans tmp2.
                        coordonnees = obtenirGroupeDeCasesVides(this.grilleAlea[coord.getX()][tmp2].getId());

                        // On parcourt les cases obtenues
                        for (Coordonnee c : coordonnees) {
                            // On modifie l'identifiant des cases voisines
                            this.grilleAlea[c.getX()][c.getY()].setId(this.grilleAlea[c.getX()][tmp].getId());
                        }

                        // On incrémente le nombre de découpes faites jusqu'à maintenant et on remet à 0 la liste des coordonnées utilisées juste avant.
                        decoupes++;
                        coordonnees.clear();
                    }
                // Si le modulo de l'ordonnée ne vaut pas 0, on va faire les mêmes étapes, mais pour les cases dans l'autre direction.
                } else {
                    // On enregistre les cases au-dessus et en dessous.
                    tmp = coord.getX() + 1;
                    tmp2 = coord.getX() - 1;

                    if (this.grilleAlea[tmp][coord.getY()].getId() != this.grilleAlea[tmp2][coord.getY()].getId()) {

                        // Pour simplifier on modifie seulement le char associé à la case
                        this.grilleAlea[coord.getX()][coord.getY()].setChar('.');

                        // On récupère les coordonnées des CaseVides ayant comme identifiant celui de la case enregistrée dans tmp2.
                        coordonnees = obtenirGroupeDeCasesVides(this.grilleAlea[tmp2][coord.getY()].getId());

                        // On parcourt les cases obtenues
                        for (Coordonnee c : coordonnees) {
                            this.grilleAlea[c.getX()][c.getY()].setId(this.grilleAlea[tmp][c.getY()].getId());
                        }

                        // On incrémente le nombre de découpes faites jusqu'à maintenant et on remet à 0 la liste des coordonnées utilisées juste avant.
                        decoupes++;
                        coordonnees.clear();
                    }
                }
            }
        }
    }

    /**
     * Méthode qui permet de récupérer les coordonnées d'une case d'un type particulier dans la grille pour la création d'un Labyrinthe aléatoire
     *
     * @param type le type de case à chercher (CaseMur ou CaseVide)
     * @param id   l'ID de la case
     * @return les coordonnées de la case recherchée
     */

    public Coordonnee rechercherCoordonner(String type, int id) {
        // Coordonnee de la case trouvée (par défaut (-1;-1))
        Coordonnee coordonnee = new Coordonnee(-1, -1);

        // On parcourt la grille
        for (int i = 1; i < this.taille; i++) {
            for (int j = 1; j < this.taille; j++) {
                // Si la case en (j;i) est du type demandé et possède l'identifiant voulu, alors on récupère ses coordonnées.
                if ((Objects.equals(this.grilleAlea[j][i].getType(), type)) && (this.grilleAlea[j][i].getId() == id)) {
                    coordonnee = new Coordonnee(j, i);
                }
            }
        }
        return coordonnee;
    }

    /**
     * Méthode qui permet de regrouper toutes les CasesVides qui ont le meme ID (pour étape de Fusion de l'algorithme) (pour la création d'un Labyrinthe aléatoire)
     *
     * @param id l'ID du groupe de cases souhaitées
     * @return retourne une ArrayList de coordonnées qui possèdent l'ID demandé
     */
    public ArrayList<Coordonnee> obtenirGroupeDeCasesVides(int id) {
        // Initialisation de la liste
        ArrayList<Coordonnee> groupe = new ArrayList<>();
        // On parcourt la grille
        for (int i = 1; i <= this.taille; i++) {
            for (int j = 1; j <= this.taille; j++) {
                // Si la case en (j;i) est une CaseVide et possède l'identifiant voulu, alors on enregistre ses coordonnées.
                if ((Objects.equals(this.grilleAlea[j][i].getType(), "CaseVide")) && (this.grilleAlea[j][i].getId() == id)) {
                    groupe.add(new Coordonnee(j, i));
                }
            }
        }
        return groupe;
    }

    /**
     * Permet de convertir la grille du labyrinthe aléatoire dans la structure de donnée utilisée précédemment (avec des entiers)
     * (pour la création d'un Labyrinthe aléatoire)
     * @return un tableau d'entier à double entrée représentant la grille du labyrinthe sous forme d'entiers.
     */
    public int[][] convertirFormatAleaEnInt() {
        int[][] casesTemplate = new int[this.taille + 1][this.taille + 1];

        for (int i = 0; i <= this.taille; i++) {
            for (int j = 0; j <= this.taille; j++) {
                if (this.grilleAlea[j][i].getChar() == '.') casesTemplate[j][i] = 0;
                else casesTemplate[j][i] = 1;
            }
        }
        return casesTemplate;
    }

    /**
     * Récupère la case aux coordonnées données
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

    public ArrayList<ArrayList<Case>> getCases() {
        return this.cases;
    }

    public ArrayList<ArrayList<Object>> getVerrousCases() {
        return this.verrousCases;
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
     * Récupère la hauteur du labyrinthe
     *
     * @return hauteur du labyrinthe
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Representation du labyrinthe sous forme de chaine de caracteres
     *
     * @return labyrinthe sous forme de chaine de caractères
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for (ArrayList<Case> aCase : this.cases) {
            for (Case value : aCase) {
                res.append(value.getChar());
                res.append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }

    /**
     * Initialise un labyrinthe minimisé pour réaliser des tests
     */
    public void initialiserPourTest() {
        /*
         * LEGENDE :
         * - 0 = case vide
         * - 1 = mur
         * - 2 = piege dans le futur, Case vide pout le moment TODO
         * - 3 = sortie
         */
        int[][] casesTemplate = {
                {0, 0, 0},
                {1, 0, 3},
                {0, 0, 0}};
        this.genererDepuisEntiers(casesTemplate);
    }
}
