package start;


import model.ihm.FenetreGraphique;

/*
import model.PacmanPainter;
import engine.GameEngineGraphical;
import model.PacmanController;
import model.PacmanGame;
*/

import model.objets.Jeu;

import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Class permettant l'exécution du moteur de jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
		/* ANCIEN MAIN

		// DECLARATION ET INITIALISATION DES VARIABLES NECESSAIRES


		Scanner scan = new Scanner(System.in);
		Jeu jeu;
		String entree, direction;
		int deplacement = 0;

		if(args.length == 1){
			jeu =  new Jeu(args[0]);
		} else {
			jeu = new Jeu();
		}

		// BOUCLE DU JEU TANT QUE LE JOUEUR N'EST PAS SUR LA SORTIE
		while(deplacement != 2){

			System.out.println(jeu.toString()); // AFFICHAGE DE L'ETAT DU JEU

			// SI LE PRECEDENT DEPLACEMENT ETAIT UNE COLISION, AFFICHAGE D'UN MESSAGE D'INFORMATIONS
			if(deplacement == 1){
				System.out.println("Vous ne pouvez pas vous déplacer sur un mur!");
			}
			
			// BOUCLE DE SAISIE SECURISEE
			do{
				System.out.print("\nEntrez une direction pour déplacer le joueur (haut, bas, gauche ou droite):");
				entree = scan.nextLine(); // LECTURE DE LA SAISIE AU CLAVIER DE L'UTILISATEUR
				direction = entree.toLowerCase(); // MISE DE LA SAISIE EN MINUSCULE

				// AFFICHAGE D'UN MESSAGE D'ERREUR SI LA SAISIE EST INCORRECTE
				if(!direction.equals("haut") && !direction.equals("bas") && !direction.equals("gauche") && !direction.equals("droite")){
					System.out.println("Vous devez entrer une direction correcte.");
				}

			} while(!direction.equals("haut") && !direction.equals("bas") && !direction.equals("gauche") && !direction.equals("droite")); // TANT QUE SAISIE INCORRECTE

			deplacement = jeu.deplacerJoueur(direction); // REALISATION DU DEPLACEMENT


			// CLEAR DU TERMINAL
			System.out.print("\033[H\033[2J");
			System.out.flush();

			// AFFICHAGE MESSAGE FINAL SI LE JOUEUR EST SUR LA SORTIE
			if(deplacement == 2) {
				System.out.println("Bravo, vous avez atteint la sortie !");
				break;
			}

		}

		// Fermeture de l'objet Scanner
		scan.close(); 


		*/

		new FenetreGraphique();
	}
}
