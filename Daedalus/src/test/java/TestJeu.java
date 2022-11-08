import model.cases.Case;
import model.cases.CaseEffet;
import model.enums.Direction;
import model.objets.Jeu;
import model.objets.Labyrinthe;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;

public class TestJeu extends TestCase{

    /**
     * Test qui vérifie que le joueur est bien affiché dans le jeu
     * @throws FileNotFoundException
     */
    @Test
    public void test_affichageJoueurDansJeu() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true);

        //Récupération du toString du Jeu (méthode appeler lorsque l'on veut l'afficher)
        String chaine = j.toString();

        assertTrue("Le toString du jeu doit contenir le joueur", chaine.contains("J"));
    }

    /**
     * Test qui vérifie que le déplacement du joueur est bien prit en compte dans l'affichage
     * @throws FileNotFoundExeception
     */
    @Test
    public void test_raffraichissmentAffichageLorsqueDeplacementJoueur() throws FileNotFoundException {

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true);

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 1);
        //Récupération du toString du Jeu (méthode appeler lorsque l'on veut l'afficher) et récupération de la position du joueur (le symbole J)
        String chaine = j.toString();
        int positionJ = chaine.indexOf("J");

        //Réalisation de l'instruction de déplacement "bas"
        j.deplacerJoueur(Direction.DROITE);

        //Nouvelle récupération du toString du Jeu après le déplacmeent et récupération de la position du joueur (le symbole J)
        String chaine2 = j.toString();
        int newPositionJ = chaine2.indexOf("J");

        assertNotSame("La position du jouer doit être différente après un déplacement", positionJ, newPositionJ);

    }

    @Test
    public void test_incrementationDifficulteJeu() throws FileNotFoundException {
         //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
         Jeu j = new Jeu(null, 1, true);

        Labyrinthe l = j.getLabyrinthe();
        ArrayList<ArrayList<Case>> lab = l.getCases();
        int nbcasesEffet1 = 0;
        for(int i = 0; i < lab.size(); i++) {
            int s = lab.get(i).size();
            for(int k = 0; k < s; k++) {
                if(lab.get(i).get(k) instanceof CaseEffet) nbcasesEffet1++;
            }
        }
        int nbMonstres1 = j.getEntites().size() - 1;

        j.changerNiveau();

        l = j.getLabyrinthe();
        lab = l.getCases();
        int nbcasesEffet2 = 0;
        for(int i = 0; i < lab.size(); i++) {
            int s = lab.get(i).size();
            for(int k = 0; k < s; k++) {
                if(lab.get(i).get(k) instanceof CaseEffet) nbcasesEffet2++;
            }
        }
        int nbMonstres2 = j.getEntites().size() - 1;

        assertNotSame("Le nombre de monstre devrait avoir augmenté avec la difficulté du niveau", nbMonstres1, nbMonstres2);
        assertNotSame("Le nombre de cases à effets devrait avoir augmenté avec la difficulté du niveau", nbcasesEffet1, nbcasesEffet2);
    }

    
    @Test
    public void test_nombreMaximumNiveauxJeu() {
        Jeu j = new Jeu(null, 1, true);

        j.changerNiveau();
        j.changerNiveau();

        int nbniveau = j.getNbNiveau();

        assertEquals("Le numéro de niveau ne devrait pas dépasser le nombre maximum de niveau du jeu", 1, nbniveau);
    }
    
}
