import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import model.entites.Gobelin;
import model.entites.Joueur;
import model.enums.Direction;
import model.enums.Ordre;
import model.enums.TypeCompetence;
import model.enums.ZoneAttaque;
import model.objets.Commande;
import model.objets.Competence;
import model.objets.Jeu;
import model.tresors.Arme;

public class TestCompetence {
    

    @Test
    public void test_competence_berserker() throws FileNotFoundException{

        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauGrand.txt", 4, false, false, false);
        int ancienneVieJ;
        Joueur j = jeu.getJoueur();

        ancienneVieJ = j.getPointsVie()+j.getPointsArmure();
        j.setArme(new Arme("Test", 10, 1, ZoneAttaque.CASE_DEVANT));
        j.setRegard(Direction.HAUT);

        j.getCompetences().add(new Competence(TypeCompetence.BERSERKER, 2, 10000, 0));
        j.lancerCompetence(0);

        
        assertTrue("Le joueur devrait avoir perdu 20 points de vies", ancienneVieJ-20 == j.getPointsVie()+j.getPointsArmure());
        assertTrue("Les dégats de l'arme devrait être de 15", j.getArme().getDegats() == 15);
        assertTrue("La compétence ne devrait pas être activable !", !j.getCompetences().get(0).isActivable());
    }

    @Test
    public void test_competence_bouclier() throws FileNotFoundException{

        Jeu  jeu = new Jeu(null, "src/main/resources/niveaux/niveauGrand.txt", 4, false, false, false);
        Joueur j = jeu.getJoueur();

        j.setArme(new Arme("Test", 10, 1, ZoneAttaque.CASE_DEVANT));
        j.setRegard(Direction.HAUT);
        j.setPointsArmure(0);

        j.getCompetences().add(new Competence(TypeCompetence.BOUCLIER_MAGIQUE, 3, 0, 2));
        j.lancerCompetence(0);

        
        assertTrue("Le joueur devrait avoir gagner 20 points de vies", 25 == j.getPointsArmure());
        assertTrue("La compétence devrait pas être activable", !j.getCompetences().get(0).isActivable());

        jeu.changerNiveau();
        jeu.changerNiveau();

        assertTrue("La compétence devrait être activable", j.getCompetences().get(0).isActivable());
    }

    @Test
    public void test_drain_vie() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauGrand.txt", 4, false, false, false);
        Joueur j = jeu.getJoueur();
        Gobelin g = new Gobelin(j.getX()-1, j.getY());
        jeu.getEntites().add(g);
        jeu.getVerrousEntites().add(new Object());

        j.setArme(new Arme("Test", 10, 1, ZoneAttaque.CASE_DEVANT));
        j.setRegard(Direction.HAUT);
        j.setPointsVie(10);

        j.getCompetences().add( new Competence(TypeCompetence.DRAIN_VIE, 4, 10000, 1));
        j.lancerCompetence(0);

        jeu.controles(new Commande(Ordre.ATTAQUE, null));

        assertTrue("Le joueur devrait avoir gagner 10 points de vies", 20 == j.getPointsVie());
        assertTrue("La compétence devrait pas être activable", !j.getCompetences().get(0).isActivable());

    }

}
