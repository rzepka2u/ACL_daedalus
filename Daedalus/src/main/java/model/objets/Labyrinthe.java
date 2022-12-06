package model.objets;

import model.cases.*;
import model.cases.Coordonnee;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe qui représente le labyrinthe du jeu
 */
public class Labyrinthe implements Serializable {

    /**
     * Cases du labyrinthe
     */
    private ArrayList<ArrayList<Case>> cases;
    private transient ArrayList<ArrayList<Object>> verrousCases;

    /**
     * Grille représentant le labyrinthe
     * Utile seulement pour la génération d'un labyrinthe aléatoire
     */
    private Case[][] grilleAlea;

    /**
     * Dimensions du labyrinthe (la taille est seulement utilisée pour la génération aléatoire du labyrinthe)
     */
    private int hauteur, largeur, taille;

    /**
     * Constructeur par defaut du labyrinthe
     * Initialise le labyrinthe avec un plateau par défaut
     */
    public Labyrinthe() {
        /*
         * LEGENDE :
         * - 0 = case vide
         * - 1 = mur
         * - 2 = piege
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
     * Constructeur du labyrinthe généré automatiquement
     *
     * @param dimension taille du côté du labyrinthe
     */
    public Labyrinthe(int dimension) {
        if (dimension % 2 != 0) dimension--; // Le labyrinthe sera carré de côté impair
        this.taille = dimension;
        this.initialiserGrillePourLabyrintheAleatoire();
        this.creerCheminLabyrintheAleatoire();
        this.cases = new ArrayList<>();
        int[][] tab = convertirFormatAleaEnInt();
        this.genererDepuisEntiers(tab);
    }

    /**
     * Constructeur par du labyrinthe par fichier
     * Initialise le labyrinthe selon un fichier
     *
     * @param path Chemin vers le fichier de génération du labyrinthe
     */
    public Labyrinthe(String path) throws FileNotFoundException {
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

        
        this.taille = nbLignes> nbColonnes? nbColonnes: nbLignes;
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
     * Méthode pour générer les cases du labyrinthe sous formes d'objets depuis un tableau d'entiers
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
                    //case 2 -> this.cases.get(i).add(new CaseEffet(-1, new Coordonnee(i,j))); // TODO

                    case 3 -> this.cases.get(i).add(new CaseSortie(-1, new Coordonnee(i, j)));
                    default -> this.cases.get(i).add(new CaseVide(-1, new Coordonnee(i, j)));
                }
            }
        }
        this.largeur = this.cases.get(0).size();
        this.hauteur = this.cases.size();

        this.cases.get(this.hauteur - 2).set(1, new CaseDepart(-1, new Coordonnee(hauteur - 1, 0)));
        this.cases.get(1).set(this.largeur - 2, new CaseSortie(-1, new Coordonnee(0, hauteur - 1)));
    }

    /**
     * Initialise le labyrinthe pour la génération aléatoire
     * Algorithme inspiré de <a href="https://fr.m.wikipedia.org/wiki/Modélisation_mathématique_d%27un_labyrinthe#Fusion_aléatoire_de_chemins">Article Modélisation Mathématique d'un Labyrinthe sur Wikipédia</a>
     */
    private void initialiserGrillePourLabyrintheAleatoire() {
        this.grilleAlea = new Case[this.taille + 2][this.taille + 2];

        // Dans un premier temps, l'algorithme ajoute des murs sur le tour de la grille
        for (int i = 0; i < this.taille + 2; i++) {
            this.grilleAlea[i][0] = new CaseMur(-1, new Coordonnee(i, 0));
            this.grilleAlea[0][i] = new CaseMur(-1, new Coordonnee(0, i));
            this.grilleAlea[this.taille][i] = new CaseMur(-1, new Coordonnee(this.taille, i));
            this.grilleAlea[i][this.taille] = new CaseMur(-1, new Coordonnee(i, this.taille));
        }

        // Dans un second temps, l'algorithme associe une valeur unique à chaque cellule et part d'un labyrinthe où tous les murs sont fermés.
        // Chaque case vide sera donc isolée
        int nbPassages = 0;
        int nbIterations = 0;

        for (int i = 1; i < this.taille; i++) {
            for (int j = 1; j < this.taille; j++) {
                nbPassages++;
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        nbIterations++;
                        this.grilleAlea[j][i] = new CaseVide(nbIterations, new Coordonnee(j, i));
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
    private void creerCheminLabyrintheAleatoire() {
        // Liste de coordonnées qui va à plusieurs reprises être utilisée puis clear. (elle va contenir des groupes de cases vides que l'algo va utiliser pour faire les couloirs)
        ArrayList<Coordonnee> coordonnees;
        Coordonnee coord;

        // À chaque itération, on choisit un mur à ouvrir de manière aléatoire, par conséquent on doit avoir une variable qui sera aléatoire.
        int nombreAleatoire, tmp, tmp2;

        // nombre de découpes (nombre de murs ouverts)
        int decoupes = 0;

        // Le chemin obtenu est unique lorsque le nombre de murs ouverts est égal au carré de la moitié de la taille du côté, moins 1.
        while (decoupes < ((taille / 2) * (taille / 2)) - 1) {
            nombreAleatoire = (int) (Math.random() * ((((taille / 2) * (taille / 2)) * 2) - ((taille / 2) * 2))) + 1;

            // On récupère les coordonnées d'une CaseMure ayant comme identifiant nombreAléatoire
            coord = rechercherCoordonnee("CaseMur", nombreAleatoire);

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
                        for (int i = 0; i < coordonnees.size(); i++) {
                            this.grilleAlea[coordonnees.get(i).getX()][coordonnees.get(i).getY()].setId(this.grilleAlea[coord.getX()][tmp].getId());
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
                        for (int i = 0; i < coordonnees.size(); i++) {
                            this.grilleAlea[coordonnees.get(i).getX()][coordonnees.get(i).getY()].setId(this.grilleAlea[tmp][coord.getY()].getId());
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
     * Méthode qui permet de récupérer les coordonnées d'une case d'un type particulier dans la grille des cases
     * (pour la création d'un Labyrinthe aléatoire)
     *
     * @param type le type de case à chercher (CaseMur ou CaseVide)
     * @param id   l'ID de la case
     * @return les coordonnées de la case recherchée
     */
    private Coordonnee rechercherCoordonnee(String type, int id) {
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
     * Méthode qui permet de regrouper toutes les CasesVides qui ont le meme ID (pour étape de Fusion de l'algorithme)
     * (pour la création d'un Labyrinthe aléatoire)
     *
     * @param id l'identifiant du groupe de cases souhaitées
     * @return retourne une ArrayList de coordonnées qui possèdent l'ID demandé
     */
    private ArrayList<Coordonnee> obtenirGroupeDeCasesVides(int id) {
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
     * (pour la création d'un Labyrinthe aléatoire) (Méthode de retro-compatibilité)
     *
     * @return un tableau d'entier à double entrée représentant la grille du labyrinthe sous forme d'entiers.
     */
    private int[][] convertirFormatAleaEnInt() {
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

    /**
     * Méthode get Cases
     *
     * @return l'arraylist des cases
     */
    public ArrayList<ArrayList<Case>> getCases() {
        return this.cases;
    }

    /**
     * Permet de définir la case X, Y à une autre Case
     *
     * @param x position X de la case à remplacer
     * @param y position Y de la case à remplacer
     * @param c nouvelle Case
     */
    public void setCaseXY(int x, int y, Case c) {
        this.cases.get(y).set(x, c);
    }

    /**
     * Méthode get VerrousCases
     *
     * @return l'arraylist des verrous des cases
     */
    public ArrayList<ArrayList<Object>> getVerrousCases() {
        return this.verrousCases;
    }

    /**
     * Retourne la largeur du labyrinthe
     *
     * @return largeur du labyrinthe
     */
    public int getLargeur() {
        return this.largeur;
    }

    /**
     * Retourne la hauteur du labyrinthe
     *
     * @return hauteur du labyrinthe
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Representation du labyrinthe sous forme de chaine de caractères
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

    /**
     * Méthode qui permet de déterminer quelles sont les cases qui seront transformées en trésor ou cases à effet
     *
     * @param estDetypeEffet true si on veut l'algo de placement des cases à effet, false si on désire celui pour les cases trésor
     * @param nbAAjouter     nombre de cases souhaitées
     * @return une liste de cases sélectionnées, compatibles avec les critères sélectionnés
     */
    public ArrayList<Case> calculerCasesSpeciales(boolean estDetypeEffet, int nbAAjouter) {
        ArrayList<Case> casesSpeciales = new ArrayList<>();
        int nbAjoutes = 0;
        int xAlea, yAlea;
        boolean pasDeCasesIdentiquesAutour;

        // Tant que toutes les cases spéciales n'ont pas été placées on continue à essayer
        while (nbAjoutes < nbAAjouter) {

            // Cas où on souhaite placer des CASES A EFFET
            if (estDetypeEffet) {
                // On détermine aléatoirement si on tente de mettre la case Effet dans la partie en BAS à GAUCHE ou en HAUT à DROITE. (une chance sur 2)
                if ((int) (Math.random() * 2) == 0) {
                    // On tire des coordonnées aléatoires dans la partie en BAS à GAUCHE
                    xAlea = (int) (Math.random() * (this.hauteur / 2)) + this.hauteur / 2;
                    yAlea = (int) (Math.random() * (this.largeur / 2));

                } else {
                    // On tire des coordonnées aléatoires dans la partie en HAUT à DROITE
                    xAlea = (int) (Math.random() * (this.hauteur / 2));
                    yAlea = (int) (Math.random() * (this.largeur / 2)) + this.largeur / 2;
                }
            } else { // Cas où on souhaite placer des CASES TRÉSOR
                // On détermine aléatoirement si on tente de mettre le trésor dans la partie en HAUT à GAUCHE ou en BAS à DROITE. (une chance sur 2)
                if ((int) (Math.random() * 2) == 0) {
                    // On tire des coordonnées aléatoires dans la partie en HAUT à GAUCHE, en évitant le centre du Labyrinthe
                    xAlea = (int) (Math.random() * (this.hauteur / 2 - 1));
                    yAlea = (int) (Math.random() * (this.largeur / 2 - 1));

                } else {
                    // On tire des coordonnées aléatoires dans la partie en BAS à DROITE, en évitant le centre du Labyrinthe
                    xAlea = (int) (Math.random() * (this.hauteur / 2) + 1) + this.hauteur / 2-1;
                    yAlea = (int) (Math.random() * (this.largeur / 2) + 1) + this.largeur / 2-1;
                }
            }

            // Il faut maintenant s'assurer que la case est libre
            if (this.cases.get(xAlea).get(yAlea) instanceof CaseVide) {
                // Il faut aussi que ça soit une case qui n'est pas déjà prévue pour être une case spéciale
                if (!casesSpeciales.contains(this.cases.get(xAlea).get(yAlea))) {
                    // Il faut enfin s'assurer qu'il n'y ait pas déjà une case spéciale sur les cases adjacentes.
                    pasDeCasesIdentiquesAutour = true;
                    for (Case c : casesSpeciales) {
                        if (xAlea == c.getX() + 1 || xAlea == c.getX() - 1 || yAlea == c.getY() + 1 || yAlea == c.getY() - 1) {
                            pasDeCasesIdentiquesAutour = false;
                        }
                    }
                    // Si toutes les conditions sont réunies, alors on ajoute
                    if (pasDeCasesIdentiquesAutour) {
                        casesSpeciales.add(this.cases.get(xAlea).get(yAlea));
                        nbAjoutes++;
                    }
                }
            }
        }
        return casesSpeciales;
    }

    /**
     * Méthode qui ajoute les cases à effet dans le labyrinthe, de manière pseudo-aléatoire favorisant un placement proche du chemin que le joueur va emprunter
     *
     * @param noNiveau numéro du niveau
     */
    public void ajouterCasesEffet(int noNiveau) {
        if (noNiveau == 0) noNiveau = 1;
        int casesInitialementVides = this.compterCasesVides();
        int nbAAjouter = 2 + (int) (Math.random() * ((casesInitialementVides * 0.8) * noNiveau) / 100);
        // Tout d'abord il faut limiter le nombre de cases spéciales sur le labyrinthe, pour ne pas surcharger l'algorithme
        if (nbAAjouter > casesInitialementVides / 20) nbAAjouter = casesInitialementVides / 20;

        // Il faut ensuite déterminer quelles seront les cases à changer
        ArrayList<Case> casesConcernees = calculerCasesSpeciales(true, nbAAjouter);

        int pvA, pvD;
        for (Case c : casesConcernees) {
            if ((int) (Math.random() * 2) == 0) {
                pvA = (int) (Math.random() * 10)+1;
                pvD = 0;
            } else {
                pvA = 0;
                pvD = (int) (Math.random() * 10)+1;
            }

            this.cases.get(c.getX()).set(c.getY(), new CaseEffet(0, new Coordonnee(c.getX(), c.getY()), pvA, pvD, ((int) (Math.random() * 2) == 0)));

        }
    }

    /**
     * Méthode qui ajoute les trésors dans le labyrinthe, de manière pseudo-aléatoire favorisant un placement éloigné du chemin que le joueur va emprunter
     *
     * @param noNiveau nombre de trésors à ajouter
     */
    public void ajouterCasesTresor(int noNiveau) {
        if (noNiveau == 0) noNiveau = 1;
        int casesInitialementVides = this.compterCasesVides();
        int nbAAjouter = 1 + (int) (Math.random() * casesInitialementVides * noNiveau * 0.009);
        // Tout d'abord il faut limiter le nombre de cases spéciales sur le labyrinthe, pour ne pas surcharger l'algorithme
        if (nbAAjouter > casesInitialementVides / 25) nbAAjouter = casesInitialementVides / 25;

        // Il faut ensuite déterminer quelles seront les cases à changer
        ArrayList<Case> casesConcernees = calculerCasesSpeciales(false, nbAAjouter);

        // Il faut maintenant déterminer quel sera le type de trésor, puis l'ajouter
        for (Case c : casesConcernees) {
            Tresor tresor;
            // On détermine aléatoirement s'il s'agira d'une Potion ou d'une Arme (une chance sur 3 que ça soit une arme).
            int nb = (int) (Math.random() * 3);
            switch (nb) {
                case 1 -> tresor = Arme.armeAlea();
                case 2 -> tresor = new PieceArmure((int) (Math.random() * 5)+1);
                default -> tresor = new Potion((int) (Math.random() * 10)+1);
            }
            this.cases.get(c.getX()).set(c.getY(), new CaseTresor(0, new Coordonnee(c.getX(), c.getY()), tresor));

        }
    }

    /**
     * Méthode qui permet de compter le nombre de cases vides
     *
     * @return le nombre de cases vides
     */
    public int compterCasesVides() {
        int compteur = 0;
        for (ArrayList<Case> ligne : this.cases) {
            for (Case c : ligne) {
                if (c instanceof CaseVide) compteur++;
            }
        }
        return compteur;
    }

    /**
     * Méthode qui permet de compter le nombre de cases trésor
     *
     * @return le nombre de cases tresor
     */
    public int compterCasesTresor() {
        int compteur = 0;
        for (ArrayList<Case> ligne : this.cases) {
            for (Case c : ligne) {
                if (c instanceof CaseTresor) compteur++;
            }
        }
        return compteur;
    }

    /**
     * Méthode qui permet de compter le nombre de cases a effet
     *
     * @return le nombre de cases effet
     */
    public int compterCasesEffet() {
        int compteur = 0;
        for (ArrayList<Case> ligne : this.cases) {
            for (Case c : ligne) {
                if (c instanceof CaseEffet) compteur++;
            }
        }
        return compteur;
    }

    public void setVerrousCases(ArrayList<ArrayList<Object>> verrousCases) {
        this.verrousCases = verrousCases;
    }

    public static void main(String[] args) {
        Labyrinthe l = new Labyrinthe(20);
        //System.out.println(l);

        System.out.println("Il y a " + l.compterCasesVides() + " cases vides");

        l.ajouterCasesEffet(5);
        l.ajouterCasesTresor(5);

        //System.out.println(l);

        System.out.println("Il y a " + l.compterCasesVides() + " cases vides");
        System.out.println("Il y a " + l.compterCasesEffet() + " cases effet");
        System.out.println("Il y a " + l.compterCasesTresor() + " cases trésor");
    }

}
