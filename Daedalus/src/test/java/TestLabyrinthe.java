import model.cases.Case;
import model.cases.CaseMur;
import model.objets.Jeu;

import model.objets.Labyrinthe;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
        Jeu j = new Jeu(null, 1, true);
        Labyrinthe l = j.getLabyrinthe();

        // récupération des données à tester
        int h = l.getHauteur();
        int la = l.getLargeur();
        int dim;

        if(j.DIMENSION_LABYRINTHE%2==0){
            dim = j.DIMENSION_LABYRINTHE+1;
        } else {
            dim = j.DIMENSION_LABYRINTHE;
        }
        
        // verifications des données
        // test sur la hauteur
        assertEquals("La hauteur devrait être de "+dim, dim, h);
        // test sur la largeur
        assertEquals("La largeur devrait être de "+dim, dim, la);
    }


    /**
     * Test verifiant que le labyrinthe de base généré est bien celui souhaité
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
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true);

        // Création du labyrinthe
        Labyrinthe l = new Labyrinthe();

        // Génération du labyrinthe à partir du tableau créé précédemment
        l.genererDepuisEntiers(casesTest);

        String s1 = l.toString();
        String s2 = j.getLabyrinthe().toString();

        // On compare si on obtient la même chose après le toString des deux labyrinthes
        boolean b = s1.equals(s2);
        // verifications des donnees
        assertTrue(s1, b);
    }

    */

    /**
     * Test verifiant que la création du labyrinthe à partir d'un fichier va renvoyer une erreur si le fichier n'existe pas
     */
    @Test (expected = FileNotFoundException.class)
    public void test_generationLabyrintheAvecFichierInexistant() throws FileNotFoundException {
        // methodes testée
        // Ici on doit avoir une exception FileNotFound en créant le jeu
        Jeu j = new Jeu(null, "fichierInexistant.txt", 1, true);

    }

    @Test
    public void test_labyrintheEntoureMur() {
        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, 1, true);

        Labyrinthe l = j.getLabyrinthe();
        boolean entoure = true;
        ArrayList<ArrayList<Case>> lab = l.getCases();
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
    

}
