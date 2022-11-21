import model.cases.*;
import model.enums.Direction;
import model.enums.Ordre;
import model.enums.ZoneAttaque;
import model.objets.Arme;
import model.objets.Commande;
import model.objets.Jeu;
import model.objets.Joueur;
import model.objets.PieceArmure;
import model.objets.Potion;

import java.io.FileNotFoundException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.BeforeClass;


/**
 * Classe de test des fonctions du joueur
 */
public class TestJoueur {

    private static Jeu jeu;

    @BeforeClass
    public static void constructionJeuPourTests() throws FileNotFoundException{
        jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 4, true, true, false);
    }

    /**
     * Test qui vérifie que l'initialisation d'un Joueur le positionne bien à l'intérieur du labyrinthe
     */
    @Test
    public void test_initialisationJoueurDansLabyrinthe(){

        //Récupération des coordonnées de l'objet Joueur créé par l'objet Jeu
        int x = jeu.getJoueur().getX();
        int y = jeu.getJoueur().getY();

        //Récupération de la taille (hauteur et largeur) de l'objet Labyrinthe crée par l'objet Jeu
        int hauteur = jeu.getLabyrinthe().getHauteur();
        int largeur = jeu.getLabyrinthe().getLargeur();

        assertTrue("Le joueur ne doit pas être au dessus du labyrinthe", x>-1);
        assertTrue("Le joueur ne doit pas être en dessous du labyrinthe", x<hauteur);
        assertTrue("Le joueur ne doit pas être à gauche du labyrinthe", y>-1);
        assertTrue("Le joueur ne doit pas être à droite du labyrinthe", y<largeur);

    }

    /**
     * Test vérifant que le joueur est bien positionné sur la case de départ lors du lancement du jeu
     */
    @Test
    public void test_initialisationJoueurSurCaseDepart(){

        //Récupération des coordonnées de l'objet Joueur dans le labyrinthe
        int x = jeu.getJoueur().getX();
        int y = jeu.getJoueur().getY();

        //Récupération de la case sur laquelle se trouve l'objet Joueur
        Case c = jeu.getLabyrinthe().getCase(x, y);

        assertTrue("Le joueur doit être sur la case de départ", c instanceof CaseDepart);

    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers le bas du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurBas(){

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        jeu.placerJoueurSurCase(1, 1);

        //Réalisation de l'instruction de déplacement "bas"
        jeu.deplacerJoueur(Direction.BAS);

        assertSame("Le joueur doit être sur la case en dessous de la case (1,1)", 2, jeu.getJoueur().getX());
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers le haut du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurHaut(){

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        jeu.placerJoueurSurCase(2, 1);

        //Réalisation de l'instruction de déplacement "haut"
        jeu.deplacerJoueur(Direction.HAUT);

        assertSame("Le joueur doit être sur la case au dessus de la case (1,2)", jeu.getJoueur().getX(), 1);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers la droite du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurDroite(){

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        jeu.placerJoueurSurCase(1, 1);

        //Réalisation de l'instruction de déplacement "droite"
        jeu.deplacerJoueur(Direction.DROITE);

        assertSame("Le joueur doit être sur la case à droite de la case (1,2)", jeu.getJoueur().getY(), 2);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers la gauche du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurGauche(){

        jeu.placerJoueurSurCase(1, 2);

        //Réalisation de l'instruction de déplacement "gauche"
        jeu.deplacerJoueur(Direction.GAUCHE);

        assertSame("Le joueur doit être sur la case à gauche de la case (1,1)", jeu.getJoueur().getY(), 1);
    
    }

    /**
     * Test verifiant qu'un joueur ne peut pas se deplacer lorsqu'un mur le bloque
     */
    @Test
    public void test_collisionMur() {
        
        jeu.placerJoueurSurCase(1, 1);

        // methodes testées
        jeu.deplacerJoueur(Direction.GAUCHE);

        int posx = jeu.getJoueur().getX();
        int posy = jeu.getJoueur().getY();

        // verifications des donnees
        assertEquals("Le joueur ne devrait pas avoir bougé de la position de départ", 1, posx);
        assertEquals("Le joueur ne devrait pas avoir bougé de la position de départ", 1, posy);
    }


    /**
     * Test verifiant qu'un joueur peut se deplacer lorsqu'il n'y a pas de mur pour le bloquer
     * @throws FileNotFoundException
     */
    @Test
    public void test_nonCollisionMur() throws FileNotFoundException {
        // preparation des donnees
        jeu.placerJoueurSurCase(2,1);

        // methodes testées
        jeu.deplacerJoueur(Direction.HAUT);
        jeu.deplacerJoueur(Direction.DROITE);
        int posx = jeu.getJoueur().getX();
        int posy = jeu.getJoueur().getY();

        // verifications des donnees
        assertEquals("Le joueur devrait avoir bougé en X = 1", 1, posx);
        assertEquals("Le joueur devrait avoir bougé en Y = 2", 2, posy);
    }

    /**
     * Test vérifiant que le joueur peux bien boire une potion
     */
    @Test 
    public void testBoirePotion(){
        
        jeu.getJoueur().setPointsVie(10);
        jeu.getJoueur().ajouterPotion(new Potion(10));// Ajoute une potion dans l'inventaire de 10pv
        jeu.controles(new Commande(Ordre.BOIRE, 0));// Boire la potion

        assertTrue("Le nombre de points de vie doit être de 20."+jeu.getJoueur().getPointsVie(), jeu.getJoueur().getPointsVie() == 20);
    }


}
