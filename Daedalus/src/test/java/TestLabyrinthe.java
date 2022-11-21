import model.cases.Case;
import model.cases.CaseMur;
import model.objets.Jeu;

import model.objets.Labyrinthe;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Classe de test du Labyrinthe
 */
public class TestLabyrinthe {


    private static Labyrinthe labyrinthe;

    @BeforeClass
    public static void constructionJeuPourTests() throws FileNotFoundException{
        labyrinthe = new Labyrinthe(9);
    }

    /**
     * Test verifiant que le labyrinthe de base généré est bien celui souhaité
     */
    @Test
    public void test_generationLabyrintheAvecFichierValide() throws FileNotFoundException {
        // preparation des donnees
        int[][] casesTest = {
                {1,1,1,1,1,1},
                {1,0,0,0,0,1},
                {1,0,0,1,0,1},
                {1,0,1,1,1,1},
                {1,0,1,1,0,1},
                {1,0,0,0,0,1},
                {1,1,1,1,1,1},
            };

        // Initialisation du jeu pour créer le labyrinthe à partir du fichier niveauTestLabyrinthe
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);

        // Création du labyrinthe
        Labyrinthe l = new Labyrinthe();

        // Génération du labyrinthe à partir du tableau créé précédemment
        l.genererDepuisEntiers(casesTest);

        String s1 = l.toString();
        String s2 = j.getLabyrinthe().toString();

        // On compare si on obtient la même chose après le toString des deux labyrinthes
        boolean b = s1.equals(s2);
        // verifications des donnees 
        assertTrue(s1+s2, b);
    }

    /**
     * Test verifiant que la création du labyrinthe à partir d'un fichier va renvoyer une erreur si le fichier n'existe pas
     */
    @Test (expected = FileNotFoundException.class)
    public void test_generationLabyrintheAvecFichierInexistant() throws FileNotFoundException {
        // methodes testée
        // Ici on doit avoir une exception FileNotFound en créant le jeu
        new Jeu(null, "fichierInexistant.txt", 1, true, false, false);

    }

    /**
     * Test verifiant que le labyrinthe de base a les bonnes dimensions
     * Celui ci est de taille 15x15
     */
    @Test
    public void test_respectDimension() {

        // récupération des données à tester
        int h = labyrinthe.getHauteur();
        int la = labyrinthe.getLargeur();
        
        // verifications des données
        // test sur la hauteur
        assertEquals("La hauteur devrait être de "+7, 9, h);
        // test sur la largeur
        assertEquals("La largeur devrait être de "+7, 9, la);
    }

    @Test
    public void test_labyrintheEntoureMur() {

        boolean entoure = true;
        ArrayList<ArrayList<Case>> lab = labyrinthe.getCases();
        for (int i = 0; i < lab.size(); i++) {
            for(int k = 0; k < lab.get(i).size(); k++) {
                if(i == 0 || i == lab.size()) {
                    if(!(lab.get(i).get(k) instanceof CaseMur)) entoure = false;
                }
                if(k == 0 || k == lab.get(i).size()) {
                    if(!(lab.get(i).get(k) instanceof CaseMur)) entoure = false;
                }
            }
        }

        assertEquals("Le labyrinthe devrait être entouré de mur", true, entoure);
    }

    /**
     * Test vérifiant la présence d'un trésor dans les labyrinthes
     */
    @Test
    public void testPresenceDesTresors() throws FileNotFoundException{

        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauVide.txt", 2, true, false, false);
        
        assertTrue("Le premier labyrinthe doit contenir un trésor"+j.getLabyrinthe().compterCasesTresor(),  j.getLabyrinthe().compterCasesTresor()>0);

        j.changerNiveau();

        assertTrue("Le second labyrinthe doit contenir un trésor",  j.getLabyrinthe().compterCasesTresor() >0);

    }
    

}
