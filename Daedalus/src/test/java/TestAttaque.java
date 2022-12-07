import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;

import model.entites.Gobelin;
import model.entites.Joueur;
import model.enums.Direction;
import model.enums.Ordre;
import model.enums.ZoneAttaque;
import model.objets.Commande;
import model.objets.Jeu;
import model.tresors.Arme;

import org.junit.Test;

public class TestAttaque {
    

    private static Jeu jeu;

    @BeforeClass
    public static void constructionJeuPourTests() throws FileNotFoundException{
        jeu = new Jeu(null, "src/main/resources/niveaux/niveauVide.txt", 1, true, true, false);
    }

    @Test
    public void testPortee(){
        int ancienneVie;
        Gobelin g;
        Joueur j = jeu.getJoueur();

        j.setArme(new Arme("Test", 2, 3, ZoneAttaque.CASE_DEVANT));
        j.setRegard(Direction.HAUT);

        g = new Gobelin(j.getX()-3, j.getY());
        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        jeu.getEntites().add(g);
        jeu.getVerrousEntites().add(new Object());
        
        jeu.controles(new Commande(Ordre.ATTAQUE, null));

        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

    }

    @Test
    public void testZoneCaseDevant(){
        int ancienneVie;
        Gobelin g;
        Joueur j = jeu.getJoueur();

        j.setArme(new Arme("Test", 2, 1, ZoneAttaque.CASE_DEVANT));
        j.setRegard(Direction.HAUT);

        g = new Gobelin(j.getX()-1, j.getY());
        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        jeu.getEntites().add(g);
        jeu.getVerrousEntites().add(new Object());
        
        jeu.controles(new Commande(Ordre.ATTAQUE, null));

        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());
    }


    @Test
    public void testZoneArcDeCercle(){
        int ancienneVie;
        Gobelin g;
        Joueur j = jeu.getJoueur();

        j.setArme(new Arme("Test", 2, 3, ZoneAttaque.ARC_DE_CERCLE));
        j.setRegard(Direction.BAS);
        j.seDeplacer(2, 2);

        g = new Gobelin(j.getX()+1, j.getY());
        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        jeu.getEntites().add(g);
        jeu.getVerrousEntites().add(new Object());
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        g.seDeplacer(j.getX()+1, j.getY()-1);
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        g.seDeplacer(j.getX()+1, j.getY()+1);
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

    }

    @Test
    public void testZoneEnCarre(){
        int ancienneVie;
        Gobelin g;
        Joueur j = jeu.getJoueur();

        j.setArme(new Arme("Test", 2, 3, ZoneAttaque.EN_CARRE));
        j.setRegard(Direction.BAS);
        j.seDeplacer(4, 4);

        g = new Gobelin(j.getX()+2, j.getY());
        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        jeu.getEntites().add(g);
        jeu.getVerrousEntites().add(new Object());
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        g.seDeplacer(j.getX()-2, j.getY());
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        g.seDeplacer(j.getX(), j.getY()+2);
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());

        ancienneVie = g.getPointsArmure() + g.getPointsVie();
        g.seDeplacer(j.getX(), j.getY()-2);
        jeu.controles(new Commande(Ordre.ATTAQUE, null));
        assertTrue("Le monstre devrait avoir reçu un coup !", ancienneVie > g.getPointsArmure()+g.getPointsVie());


    }
}
