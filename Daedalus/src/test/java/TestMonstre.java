import model.enums.Direction;
import model.enums.Ordre;
import model.objets.Commande;
import model.objets.Entite;
import model.objets.Jeu;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.objets.Joueur;
import model.threads.ThreadMonstre;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Classe de test des fonctions du joueur
 */
public class TestMonstre {

    private static Jeu jeu;

    @BeforeClass
    public static void constructionJeuPourTests() throws FileNotFoundException{
        jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, false, true);
    }

    /**
     * Test qui vérifie que le déplacement du monstre s'effectue bien
     */
    @Test
    public void test_deplacementMonstre() {

        synchronized(jeu.getVerrousEntites().get(1)){
            // Récupération du Monstre
            Entite e = jeu.getEntites().get(1);
            // on déplace le monstre
            e.seDeplacer(2, 2);
            assertTrue("Le monstre devrait avoir effectué son déplacement en (2,2)", 2 == e.getY() && 2 == e.getX());
        }
    }

    /**
     * Test qui vérifie que le déplacement du monstre s'effectue bien
     * @throws FileNotFoundException
     */
    @Test
    public void test_priseDegatsMonstre(){

        int pa;
        Entite e;
        
        synchronized(jeu.getVerrousEntites().get(1)){
            synchronized(jeu.getVerrousEntites().get(0)){

                // Récupération du Joueur
                Joueur jo = jeu.getJoueur();

                // on fait regarde le Joueur vers le
                jo.setRegard(Direction.HAUT);
            

                // Récupération du Monstre
                e = jeu.getEntites().get(1);

                e.setRegard(Direction.BAS);
                // on déplace le monstre à sa droite
                e.seDeplacer(jo.getX()-1, jo.getY());

                // on stocke les points d'armure avant l'attaque
                pa = e.getPointsArmure();
            }
        }

        Commande c = new Commande(Ordre.ATTAQUE, null);
        // attaque
        jeu.controles(c);

        synchronized(jeu.getVerrousEntites().get(1)){
            // Test
            assertTrue("Le monstre devrait avoir reçu un coup"+e.getPointsArmure()+","+e.getPointsVie(), e.getPointsArmure() < pa);
        } 
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


