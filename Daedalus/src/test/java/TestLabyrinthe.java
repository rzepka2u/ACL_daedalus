import model.objets.Jeu;

import model.objets.Labyrinthe;
import org.junit.Test;

import java.io.FileNotFoundException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Classe de test du Labyrinthe
 */
public class TestLabyrinthe {

    /**
     * Test verifiant que le labyrinthe de base a les bonnes dimensions
     * Celui ci est de taille 15x15
     */
    @Test
    public void test_respectDimension() {
        // preparation des donnees
        Jeu j = new Jeu();
        Labyrinthe l = j.getLabyrinthe();

        // récupération des données à tester
        int h = l.getHauteur();
        int la = l.getLargeur();

        // verifications des données
        // test sur la hauteur
        assertEquals("La hauteur devrait être de 15", 15, h);
        // test sur la largeur
        assertEquals("La largeur devrait être de 15", 15, la);
    }


    /**
     * Test verifiant que le labyrinthe de base généré est bien celui souhaité
     */
    @Test
    public void test_generationLabyrintheAvecFichierValide() throws FileNotFoundException {
        // preparation des donnees
        int[][] casesTest = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,1,1,0,0,1,0,0,0,0,1,0,1},
                {1,1,2,1,0,1,0,0,0,0,1,0,0,3,1},
                {1,0,1,1,0,0,0,1,1,1,1,0,0,1,1},
                {1,0,1,1,1,0,0,1,0,0,0,0,0,0,1},
                {1,0,0,0,0,1,0,0,0,0,1,0,1,0,1},
                {1,0,1,1,0,1,0,1,1,0,1,2,1,0,1},
                {1,0,0,1,0,0,0,0,1,0,0,0,0,1,1},
                {1,0,0,0,1,1,0,0,0,0,1,0,0,0,1},
                {1,0,1,1,0,0,1,1,1,0,1,1,0,0,1},
                {1,0,0,0,0,0,1,0,1,0,1,0,0,0,1},
                {1,0,1,1,0,2,0,0,1,0,0,0,1,1,1},
                {1,0,1,0,0,1,0,0,1,0,0,0,1,0,1},
                {1,0,1,1,1,1,0,1,0,0,1,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

        // Initialisation du jeu pour créer le labyrinthe à partir du fichier niveauTestLabyrinthe
        Jeu j = new Jeu("src/main/ressources/niveaux/niveauTestLabyrinthe.txt");

        // Création du labyrinthe
        Labyrinthe l = new Labyrinthe();

        // Génération du labyrinthe à partir du tableau créé précédemment
        l.generer(casesTest);

        String s1 = l.getCases().toString();
        String s2 = j.getLabyrinthe().getCases().toString();

        // On compare si on obtient la même chose après le toString des deux labyrinthes
        boolean b = s1.equals(s2);
        // verifications des donnees
        assertTrue("Les deux labyrinthes devraient être égaux ", b);
    }

    /**
     * Test verifiant que la création du labyrinthe à partir d'un fichier va renvoyer une erreur si le fichier n'existe pas
     */
    @Test (expected = FileNotFoundException.class)
    public void test_generationLabyrintheAvecFichierInexistant() throws FileNotFoundException {
        // methodes testée
        // Ici on doit avoir une exception FileNotFound en créant le jeu
        Jeu j = new Jeu("fichierInexistant.txt");

    }

}
