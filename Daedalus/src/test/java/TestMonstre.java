import model.enums.Direction;
import model.enums.Ordre;
import model.objets.Commande;
import model.objets.Entite;
import model.objets.Jeu;

import java.io.FileNotFoundException;

import model.objets.Joueur;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Classe de test des fonctions du joueur
 */
public class TestMonstre extends TestCase {

    /**
     * Test qui vérifie que le déplacement du monstre s'effectue bien
     */
    @Test
    public void test_deplacementMonstre() throws FileNotFoundException {

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauVide.txt", 1, false);

        synchronized(j.getVerrousEntites().get(1)){
            // Récupération du Monstre
            Entite e = j.getEntites().get(1);

            // on déplace le monstre
            e.seDeplacer(2, 2);

            
            assertTrue("Le monstre devrait avoir effectué son déplacement en y", 2 == e.getY() && 2 == e.getX());
        }

        j.getThreads().get(0).interrupt();
        j.getThreads().get(1).interrupt();

    }


    /**
     * Test qui vérifie que le déplacement du monstre s'effectue bien
     * @throws FileNotFoundException
     *//*/
    @Test
    public void test_priseDegatsMonstre() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauVide.txt", 1, false, true);
        int pa;
        Entite e;
        
        synchronized(j.getVerrousEntites().get(1)){
            synchronized(j.getVerrousEntites().get(0)){

                // Récupération du Joueur
                Joueur jo = j.getJoueur();

                // on fait regarde le Joueur vers le
                jo.setRegard(Direction.DROITE);
            

                // Récupération du Monstre
                e = j.getEntites().get(1);

                e.setRegard(Direction.GAUCHE);
                // on déplace le monstre à sa droite
                e.seDeplacer(jo.getX(), jo.getY()+1);

                // on stocke les points d'armure avant l'attaque
                pa = e.getPointsArmure();
            }
        }

        Commande c = new Commande(Ordre.ATTAQUE, null);
        // attaque
        j.controles(c);

        synchronized(j.getVerrousEntites().get(1)){
            // Test
            assertTrue("Le monstre devrait avoir reçu un coup"+e.getPointsArmure()+","+e.getPointsVie(), e.getPointsArmure() < pa);
        }  

        j.getThreads().get(0).interrupt();
        j.getThreads().get(1).interrupt();
    }*/

}


