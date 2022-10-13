import model.objets.Jeu;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test des fonctions du joueur
 */
public class TestJoueur {



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
