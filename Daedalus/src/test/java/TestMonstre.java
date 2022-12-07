import model.entites.Entite;
import model.entites.Joueur;
import model.enums.Direction;
import model.enums.Ordre;
import model.objets.Commande;
import model.objets.Jeu;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
        jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, false, true, false);
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
                jo.setRegard(Direction.DROITE);
            

                // Récupération du Monstre
                e = jeu.getEntites().get(1);

                // on déplace le monstre à droite du joueur
                e.seDeplacer(jo.getX(), jo.getY()+1);

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

    /**
     * Test qui vérifie que le déplacement du monstre s'effectue bien
     * @throws FileNotFoundException
     */
    @Test
    public void test_attaqueMonstre(){

        int pa;
        Entite e;

        synchronized(jeu.getVerrousEntites().get(1)){
            synchronized(jeu.getVerrousEntites().get(0)){

                // Récupération du Joueur
                Joueur jo = jeu.getJoueur();

                // Récupération du Monstre
                e = jeu.getEntites().get(1);

                e.setRegard(Direction.GAUCHE);

                // on déplace le monstre à sa droite
                e.seDeplacer(jo.getX(), jo.getY()+1);

                // on stocke les points d'armure avant l'attaque
                pa = jo.getPointsArmure();
            }
        }

        // attaque
        ArrayList<Entite> res = e.attaquer(jeu.getEntites(), jeu.getVerrousEntites());

        synchronized(jeu.getVerrousEntites().get(0)){
            // Test
            assertTrue("Le joueur devrait être dans les entités touchées", res.size() > 0);
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

