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
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, false);

        // Récupération du Monstre
        Entite e = j.getEntites().get(1);

        // on déplace le monstre
        e.seDeplacer(j.getJoueur().getX(), j.getJoueur().getY()-1);


        assertTrue("Le monstre devrait avoir effectué son déplacement en y", j.getJoueur().getY()-1 == e.getY());

    }


    /**
     * Test qui vérifie que le déplacement du monstre s'effectue bien
     * @throws FileNotFoundException
     */
    /*
    @Test
    public void test_priseDegatsMonstre() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, false);

        // Récupération du Monstre
        Entite e = j.getEntites().get(1);

        // Récupération du Joueur
        Joueur jo = j.getJoueur();

        // on fait regarde le Joueur vers la droite
        jo.setRegard(Direction.DROITE);

        // on déplace le monstre à sa droite
        e.seDeplacer(jo.getX(), jo.getY()-1);

        // on stocke les points d'armure avant l'attaque
        int pa = e.getPointsArmure();

        Commande c = new Commande(Ordre.ATTAQUE, null);
        // attaque
        j.controles(c);

        // Test
        assertTrue("Le monstre devrait avoir reçu un coup", e.getPointsArmure() < pa);

    }
    */

}


