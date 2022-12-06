import model.cases.Case;
import model.cases.CaseEffet;
import model.enums.Direction;
import model.objets.Jeu;
import model.objets.Labyrinthe;
import model.threads.ThreadMonstre;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;

public class TestJeu{


    private static Jeu jeu;

    @BeforeClass
    public static void constructionJeuPourTests() throws FileNotFoundException{
        jeu = new Jeu(null, "src/main/resources/niveaux/niveauGrand.txt", 4, false, false, false);
    }

    /**
     * Test qui vérifie que le joueur est bien affiché dans le jeu
     * @throws FileNotFoundException
     */
    @Test
    public void test_affichageJoueurDansJeu(){
        //Récupération du toString du Jeu (méthode appeler lorsque l'on veut l'afficher)
        String chaine = jeu.toString();
        assertTrue("Le toString du jeu doit contenir le joueur", chaine.contains("J"));
    }

    /**
     * Test qui vérifie que le déplacement du joueur est bien prit en compte dans l'affichage
     */
    @Test
    public void test_raffraichissmentAffichageLorsqueDeplacementJoueur() {

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        jeu.placerJoueurSurCase(1, 1);
        //Récupération du toString du Jeu (méthode appeler lorsque l'on veut l'afficher) et récupération de la position du joueur (le symbole J)
        String chaine = jeu.toString();
        int positionJ = chaine.indexOf("J");

        //Réalisation de l'instruction de déplacement "bas"
        jeu.deplacerJoueur(Direction.DROITE);

        //Nouvelle récupération du toString du Jeu après le déplacmeent et récupération de la position du joueur (le symbole J)
        String chaine2 = jeu.toString();
        int newPositionJ = chaine2.indexOf("J");

        assertNotSame("La position du jouer doit être différente après un déplacement", positionJ, newPositionJ);

    }

    @Test
    public void test_incrementationDifficulteJeu() {
        

        Labyrinthe l = jeu.getLabyrinthe();
        ArrayList<ArrayList<Case>> lab = l.getCases();
        int nbcasesEffet1 = 0;

        for(int i = 0; i < lab.size(); i++) {
            int s = lab.get(i).size();
            for(int k = 0; k < s; k++) {
                if(lab.get(i).get(k) instanceof CaseEffet) nbcasesEffet1++;
            }
        }
        int nbMonstres1 = jeu.getEntites().size() - 1;

        jeu.setNbNiveau(2);
        jeu.changerNiveau();

        l = jeu.getLabyrinthe();
        lab = l.getCases();
        int nbcasesEffet2 = 0;
        for(int i = 0; i < lab.size(); i++) {
            int s = lab.get(i).size();
            for(int k = 0; k < s; k++) {
                if(lab.get(i).get(k) instanceof CaseEffet) nbcasesEffet2++;
            }
        }
        int nbMonstres2 = jeu.getEntites().size() - 1;

        assertNotSame("Le nombre de monstre devrait avoir augmenté avec la difficulté du niveau", nbMonstres1, nbMonstres2);
        assertNotSame("Le nombre de cases à effets devrait avoir augmenté avec la difficulté du niveau", nbcasesEffet1, nbcasesEffet2);
        
    }
    
    @Test
    public void test_nombreMaximumNiveauxJeu() {

        while(jeu.getNbNiveau()<3){
            jeu.changerNiveau();
        }

        jeu.changerNiveau();
        
        int nbniveau = jeu.getNbNiveau();

        assertEquals("Le numéro de niveau ne devrait pas dépasser le nombre maximum de niveau du jeu", 3, nbniveau);
    }

    @AfterClass public static void interruption_threads(){

        ArrayList<ThreadMonstre> threads = jeu.getThreads();
        int i=0;

        for(i=0; i<threads.size(); i++){
            threads.get(i).arret();
            threads.get(i).interrupt();
        }

    }
    
}
