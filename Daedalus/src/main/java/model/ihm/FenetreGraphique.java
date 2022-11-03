package model.ihm;

import model.objets.Jeu;
import model.objets.Commande;
import model.threads.ThreadAffichage;
import model.enums.Direction;
import model.enums.Ordre;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;


/**
 * Classe FenetreGraphique représentant la fenetre principale d'interface graphique du projet
 */
public class FenetreGraphique extends JFrame {
    
    private JMenuBar menuBar; // La barre de menu située en haut
    private JPanel contentPane; // Le panel affichée dans la fenetre
    private ThreadAffichage thread; // Le thread pour rafraîchir l'affichage
    private Jeu jeu; // Le moteur du jeu

    /**
     * Constructeur par défaut de la classe FenetreGraphique
     */
    public FenetreGraphique(){

        // Appel du constructeur de la classe JFRAME + passage titre fenêtre en paramètre
        super("Daedalus");
        
        // Réalisation des réglages graphiques de la fenêtre
        this.setVisible(true); // Rendre la fenêtre visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminer le programme lorsque l'on ferme la fenêtre
        this.setSize(new Dimension(600, 600)); // Modification des dimension de départ de la fenêtre
        this.setLocationRelativeTo(null); // Mise de la fenêtre au milieu de l'écran

        menuBar = new Menu(); // Initialisation de la barre de menu
        this.setJMenuBar(menuBar); // Ajout de la barre de menu précédament créé

        // Mise des attributs inutiles (pour le moment) à null
        jeu = null;
        thread = null;

        // Initialisation d'un nouveau panel d'acceuil, et ajout de celui-ci dans la fenêtre
        contentPane = new PanelAccueil(this);
        this.setContentPane(contentPane);
    }


    /**
     * Getter sur l'attribut jeu
     * @return la valeur de l'attribut jeu (type: Jeu)
     */
    public Jeu getJeu(){
        return this.jeu;
    }

    /**
     * Méthode qui permet de créer et d'afficher le panel de partie
     * @param nbNiveau Le nombre Maximum de niveau pour finir la partie
     */
    public void afficherPartie(int nbNiveau){

        // Création et intialisation du moteur de la partie, l'attribut jeu
        this.jeu = new Jeu(this, nbNiveau);

        // Initialisation d'un nouveau panel de partie, et ajout de celui-ci dans la fenêtre
        contentPane = new PanelPartie(this);
        this.setContentPane(contentPane);

        // Permettre à la fenêtre de pouvoir obtenir le focus et le demander
        this.setFocusable(true);
        this.requestFocus();

        // Ajout d'un écouteur sur les touches préssées
        this.addKeyListener(new KeyAdapter(){
            /**
             * Méthode appeler lorsque l'utilisateur enfonce une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyPressed(KeyEvent e){

                Commande c;
                if(e.getKeyCode() == KeyEvent.VK_UP){ // Si la touche est la flèche haut
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.HAUT); // Déplacer le joueur en haut
                    jeu.controles(c);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN){ // Si la touche est la flèche bas
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.BAS); // Déplacer le joueur en bas
                    jeu.controles(c);
                } else if(e.getKeyCode() == KeyEvent.VK_LEFT){ // Si la touche est la flèche gauche
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.GAUCHE); // Déplacer le joueur à gauche
                    jeu.controles(c);
                } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){ // Si la touche est la flèche droite
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.DROITE); // Déplacer le joueur à droite
                    jeu.controles(c);
                } else if(e.getKeyCode() == KeyEvent.VK_SPACE){ // Si la touche est la barre espace
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.ATTAQUE, null); // Attaque de la part du joueur 
                    jeu.controles(c);
                } else if(e.getKeyCode() == KeyEvent.VK_R){ // Si la touche est R 
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.RAMASSER, null); // Ramasser un trésor à proximité
                    jeu.controles(c);
                } else if (e.getKeyCode() == KeyEvent.VK_O){ // Si la touche est O
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.OUVRIR, null); // Ouvrir un trésor à proximité
                    jeu.controles(c);
                } else if(e.getKeyCode() >= KeyEvent.VK_NUMPAD1 && e.getKeyCode() <= KeyEvent.VK_NUMPAD5){ 
                    // Si la touche est entre 1 et 5 inclus du clavier numérique
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.BOIRE, e.getKeyCode()-KeyEvent.VK_NUMPAD1);
                    jeu.controles(c);
                }
            }
        });

        // Création et démarrage du thread qui s'occupe de rafraîchir l'affichage
        thread = new ThreadAffichage(this);
        thread.start();

        // Mise à jour de la fenêtre
        this.validate();
    }

    /**
     * Méthode qui sert à mettre à jour le panel de partie
     */
    public void raffraichirPartie(){

        // Création d'un nouveau panel de partie et écrassement de l'ancien
        contentPane = new PanelPartie(this, ((PanelPartie) contentPane).getDescriptionPotion());
        this.setContentPane(contentPane);

        // Mise à jour de la fenêtre
        this.validate();
    }

    /**
     * Méthode qui sert à afficher le panel de fin de partie
     * @param result le résultat de la partie: true = victoire, false = defaite
     */
    public void afficherVueFin(boolean result){

        // TO DO: ARRET ThreadPartie

        // Création d'un panel de fin de partie, et remplacement du panel de partie
        contentPane = new PanelFinJeu(result);
        this.setContentPane(contentPane);

        // Mise à jour de la fenêtre
        this.validate();
    }

    // Fonction de test pour la phase de développement de l'IHM  (à retirer au final)
    public static void main(String[] args){

        // SET LOOK AN FEEL NIMBUS
        try{
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch( Exception exception ) { 
            exception.printStackTrace(); 
        }

        // CREATE NEW FENETRE 
        new FenetreGraphique();
    }

}
