import model.cases.*;
import model.enums.Direction;
import model.enums.Ordre;
import model.objets.Commande;
import model.objets.Jeu;

import java.io.File;
import java.io.FileNotFoundException;

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
        Jeu j = new Jeu(null, 1, true);

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

    }

    /**
     * Test vérifant que le joueur est bien positionné sur la case de départ lors du lancement du jeu
     */
    @Test
    public void test_initialisationJoueurSurCaseDepart(){

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, 1, true);

        //Récupération des coordonnées de l'objet Joueur dans le labyrinthe
        int x = j.getJoueur().getX();
        int y = j.getJoueur().getY();

        //Récupération de la case sur laquelle se trouve l'objet Joueur
        Case c = j.getLabyrinthe().getCase(x, y);

        assertTrue("Le joueur doit être sur la case de départ", c instanceof CaseDepart);

    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers le bas du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurBas() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true);

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 1);

        //Réalisation de l'instruction de déplacement "bas"
        j.deplacerJoueur(Direction.BAS);

        assertSame("Le joueur doit être sur la case en dessous de la case (1,1)", 2, j.getJoueur().getX());
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers le haut du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurHaut() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true);

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(2, 1);

        //Réalisation de l'instruction de déplacement "haut"
        j.deplacerJoueur(Direction.HAUT);

        assertSame("Le joueur doit être sur la case au dessus de la case (1,2)", j.getJoueur().getX(), 1);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers la droite du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurDroite() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true);

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 1);

        //Réalisation de l'instruction de déplacement "droite"
        j.deplacerJoueur(Direction.DROITE);

        assertSame("Le joueur doit être sur la case à droite de la case (1,2)", j.getJoueur().getY(), 2);
    
    }

    /**
     * Test pour vérifier le bon fonctionnement du déplacement vers la gauche du joueur
     * @throws FileNotFoundException
     */
    @Test
    public void test_deplacementJoueurGauche() throws FileNotFoundException{

        //Création d'un nouveau objet Jeu avec le labyrinthe par défaut
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true);

        //Position sur la case dans le coin haut gauche pour être sûr qu'on essaye pas de se déplacer sur un mur
        j.placerJoueurSurCase(1, 2);

        //Réalisation de l'instruction de déplacement "gauche"
        j.deplacerJoueur(Direction.GAUCHE);

        assertSame("Le joueur doit être sur la case à gauche de la case (1,1)", j.getJoueur().getY(), 1);
    
    }

    /**
     * Test verifiant qu'un joueur ne peut pas se deplacer lorsqu'un mur le bloque
     */
    @Test
    public void test_collisionMur() {
        // preparation des donnees
        Jeu j = new Jeu(null, 1, true);// jeu avec labyrinthe par défaut

        int ancienx = j.getJoueur().getX();
        int ancieny = j.getJoueur().getY();

        // methodes testées
        j.deplacerJoueur(Direction.GAUCHE);

        int posx = j.getJoueur().getX();
        int posy = j.getJoueur().getY();

        // verifications des donnees
        assertEquals("Le joueur ne devrait pas avoir bougé de la position de départ", ancienx, posx);
        assertEquals("Le joueur ne devrait pas avoir bougé de la position de départ", ancieny, posy);
    }


    /**
     * Test verifiant qu'un joueur peut se deplacer lorsqu'il n'y a pas de mur pour le bloquer
     * @throws FileNotFoundException
     */
    @Test
    public void test_nonCollisionMur() throws FileNotFoundException {
        // preparation des donnees
        Jeu j = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true);// jeu avec labyrinthe par défaut
        j.placerJoueurSurCase(2,1);

        // methodes testées
        j.deplacerJoueur(Direction.HAUT);
        j.deplacerJoueur(Direction.DROITE);
        int posx = j.getJoueur().getX();
        int posy = j.getJoueur().getY();

        // verifications des donnees
        assertEquals("Le joueur devrait avoir bougé en X = 1", 1, posx);
        assertEquals("Le joueur devrait avoir bougé en Y = 2", 2, posy);
    }

    /**
     * Test vérifiant que le joueur peux bien boire une potion
     */
    @Test 
    public void testBoirePotion(){
        Jeu j = new Jeu(null, 1, true);

        j.getJoueur().setPointsVie(10);
        j.getJoueur().ajouterPotion();// Ajoute une potion dans l'inventaire de 10pv
        j.controles(new Commande(Ordre.BOIRE, 0));// Boire la potion

        assertTrue("Le nombre de points de vie doit être de 20.", j.getJoueur().getPointsVie() == 20);
    }


    /**
     * Test vérifiant la présence d'un trésor dans les labyrinthes
     */
    @Test
    public void testTresor(){

        Jeu j = new Jeu(null, 2, true);

        assertTrue("Le premier labyrinthe doit contenir un trésor",  j.toString().contains("C"));

        j.changerNiveau();

        assertTrue("Le second labyrinthe doit contenir un trésor",  j.toString().contains("T"));

    }


}
