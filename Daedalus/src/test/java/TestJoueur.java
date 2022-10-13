import model.cases.*;
import model.objets.Jeu;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Classe de test des fonctions du joueur
 */
public class TestJoueur extends TestCase {

    /**
     * Test qui vérifie que l'initialisation d'un Joueur le positionne bien à l'intérieur du labyrinthe
     */
    @Test
    public void test_initialisationJoueurDansLabyrinthe(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu();

        //Récupération des coordonnées de l'objet Joueur créé par l'objet Jeu
        int x = j.getJoueur().getX();
        int y = j.getJoueur().getY();

        //Récupération de la taille (hauteur et largeur) de l'objet Labyrinthe crée par l'objet Jeu
        int hauteur = j.getLabyrinthe().getHauteur();
        int largeur = j.getLabyrinthe().getLargeur();

        assertTrue("Le joueur ne doit pas être au dessus du labyrinthe", x>-1);
        assertTrue("Le joueur ne doit pas être en dessous du labyrinthe", x<hauteur);
        assertTrue("Le joueur ne doit pas être à gauche du labyrinthe", y>-1);
        assertTrue("Le joueur ne doit pas être à droite du labyrinthe", y<largeur);
        assertTrue("Le joueur doit être sur une case vide", j.getLabyrinthe().getCase(x, y) instanceof CaseVide);


    }

    /**
     * Test vérifant que le joueur est bien positionné sur une case vide lors du lancement du jeu
     */
    @Test
    public void test_initialisationJoueurSurCaseVide(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu();

        //Récupération des coordonnées de l'objet Joueur dans le labyrinthe
        int x = j.getJoueur().getX();
        int y = j.getJoueur().getY();

        //Récupération de la case sur laquelle se trouve l'objet Joueur
        Case c = j.getLabyrinthe().getCase(x, y);

        assertTrue("Le joueur doit être sur une case vide", c instanceof CaseVide);

    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers le bas du joueur
     */
    @Test
    public void test_deplacementJoueurBas(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu();

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 1);

        //Réalisation de l'instruction de déplacement "bas"
        j.deplacerJoueur("bas");

        assertSame("Le joueur doit être sur la case en dessous de la case (1,1)", j.getJoueur().getX(), 2);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers le haut du joueur
     */
    @Test
    public void test_deplacementJoueurHaut(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu();

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 2);

        //Réalisation de l'instruction de déplacement "haut"
        j.deplacerJoueur("haut");

        assertSame("Le joueur doit être sur la case au dessus de la case (1,2)", j.getJoueur().getX(), 1);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers la droite du joueur
     */
    @Test
    public void test_deplacementJoueurDroite(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu();

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 1);

        //Réalisation de l'instruction de déplacement "droite"
        j.deplacerJoueur("droite");

        assertSame("Le joueur doit être sur la case à droite de la case (1,1)", j.getJoueur().getY(), 2);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers la gauche du joueur
     */
    @Test
    public void test_deplacementJoueurGauche(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu();

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 2);

        //Réalisation de l'instruction de déplacement "gauche"
        j.deplacerJoueur("gauche");

        assertSame("Le joueur doit être sur la case à gauche de la case (1,2)", j.getJoueur().getY(), 1);
    
    }

    /**
     * Test verifiant qu'un joueur ne peut pas se deplacer lorsqu'un mur le bloque
     */
    @Test
    public void test_collisionMur() {
        // preparation des donnees
        Jeu j = new Jeu();// jeu avec labyrinthe par défaut
        j.placerJoueurSurCase(1,1);

        // methodes testées
        j.deplacerJoueur("haut");
        int posx = j.getJoueur().getX();
        int posy = j.getJoueur().getY();

        // verifications des donnees
        assertEquals("Le joueur ne devrait pas avoir bougé de X = 1", 1, posx);
        assertEquals("Le joueur ne devrait pas avoir bougé de Y = 1 ", 1, posy);
    }


    /**
     * Test verifiant qu'un joueur peut se deplacer lorsqu'il n'y a pas de mur pour le bloquer
     */
    @Test
    public void test_nonCollisionMur() {
        // preparation des donnees
        Jeu j = new Jeu();// jeu avec labyrinthe par défaut
        j.placerJoueurSurCase(1,1);

        // methodes testées
        j.deplacerJoueur("bas");
        j.deplacerJoueur("droite");
        int posx = j.getJoueur().getX();
        int posy = j.getJoueur().getY();

        // verifications des donnees
        assertEquals("Le joueur devrait avoir bougé en X = 2", 2, posx);
        assertEquals("Le joueur devrait avoir bougé en Y = 2", 2, posy);
    }

}
