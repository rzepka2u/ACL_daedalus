package start;

import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;
import model.objets.Labyrinthe;

import java.io.IOException;


/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		/*
		// creation du jeu particulier et de son afficheur
		PacmanGame game = new PacmanGame("helpFilePacman.txt");
		PacmanPainter painter = new PacmanPainter();
		PacmanController controller = new PacmanController();

		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
		*/
		Labyrinthe l = null;
		try {
			l = new Labyrinthe("src/main/ressources/niveaux/niveau0.txt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println(l.toString());

	}

}
