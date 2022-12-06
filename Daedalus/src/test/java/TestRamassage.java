import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import model.objets.Commande;
import model.objets.Jeu;
import model.tresors.Arme;
import model.tresors.PieceArmure;
import model.tresors.Potion;
import model.cases.CaseTresor;
import model.cases.CaseVide;
import model.cases.Coordonnee;
import model.entites.Joueur;
import model.enums.Direction;
import model.enums.Ordre;
import model.enums.ZoneAttaque;

public class TestRamassage {

    @Test 
    public void ouvrirCoffre() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        j.setRegard(Direction.DROITE);
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 2), new Potion(5));

        
        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY()+1, c);

        jeu.controles(new Commande(Ordre.OUVRIR, Direction.DROITE));

        assertTrue("Le trésor devrait être ouvert", c.getOuvert());

    }

    @Test 
    public void ouvrirCoffreEloignee() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        j.setRegard(Direction.DROITE);
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 3), new Potion(5));

        
        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY()+2, c);

        jeu.controles(new Commande(Ordre.OUVRIR, Direction.DROITE));

        assertTrue("Le trésor devrait être fermée", !c.getOuvert());

    }

    @Test
    public void ramasserPotion() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 1), new Potion(5));
        c.ouvrirTresor();

        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY(), c);

        jeu.controles(new Commande(Ordre.RAMASSER, null));
        
        assertTrue("Le joueur devrait avoir une potion dans son inventaire!", j.getInventaire().size()==1);
        assertTrue("La potion doit ne plus être sur la case", jeu.getLabyrinthe().getCase(1, 1) instanceof CaseVide);
    }

    @Test
    public void ramasserPotionQuandInventairePlein() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        j.ajouterPotion(new Potion(1));
        j.ajouterPotion(new Potion(1));
        j.ajouterPotion(new Potion(1));
        j.ajouterPotion(new Potion(1));
        j.ajouterPotion(new Potion(1));
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 1), new Potion(5));
        c.ouvrirTresor();

        j.setRegard(Direction.DROITE);
        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY(), c);

        jeu.controles(new Commande(Ordre.RAMASSER, null));
        
        assertTrue("Le joueur devrait avoir toujours 5 potions dans son inventaire!", j.getInventaire().size()==5);
        assertTrue("La potion doit toujours être sur la case", jeu.getLabyrinthe().getCase(1, 1) instanceof CaseTresor);
    }

    @Test 
    public void ramasserArmure() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        j.setPointsArmure(0);
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 1), new PieceArmure(10));
        c.ouvrirTresor();

        j.setRegard(Direction.DROITE);
        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY(), c);

        jeu.controles(new Commande(Ordre.RAMASSER, null));
        
        assertTrue("Le joueur devrait avoir 10 points d'armure!", j.getPointsArmure()==10);
        assertTrue("La pièce d'amure ne doit plus être sur la case", jeu.getLabyrinthe().getCase(1, 1) instanceof CaseVide);

    }

    @Test 
    public void ramasserArmureQuandPointsArmureDejaMax() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        j.setPointsArmure(j.getNB_PA_MAX());
        int pa = j.getPointsArmure();
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 1), new PieceArmure(10));
        c.ouvrirTresor();

        j.setRegard(Direction.DROITE);
        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY(), c);

        jeu.controles(new Commande(Ordre.RAMASSER, null));
        
        assertTrue("Les points d'armure du joueur devrait ne pas avoir bougé!", pa==j.getPointsArmure());
        assertTrue("La pièce d'amure doit toujours être sur la case", jeu.getLabyrinthe().getCase(1, 1) instanceof CaseTresor);

    }

    

    @Test
    public void ramasserArme() throws FileNotFoundException{
        Jeu jeu = new Jeu(null, "src/main/resources/niveaux/niveauSimple.txt", 1, true, true, false);
        jeu.placerJoueurSurCase(1, 1);

        Joueur j = jeu.getJoueur();
        CaseTresor c = new CaseTresor(-1, new Coordonnee(1, 1), new Arme("Arc", 15, 3, ZoneAttaque.CASE_DEVANT));
        c.ouvrirTresor();

        j.setRegard(Direction.DROITE);
        jeu.getLabyrinthe().getCases().get(j.getX()).set(j.getY(), c);

        jeu.controles(new Commande(Ordre.RAMASSER, null));
        
        assertTrue("Le joueur devrait avoir l'arme nommée 'Arc' en main!", j.getArme().getNom().equals("Arc"));
        assertTrue("La case où se situe l'arme devrait contenir l'arme 'Epée en bois'!", ((Arme)c.getContenu()).getNom().equals("Epée en bois"));
    }
}
