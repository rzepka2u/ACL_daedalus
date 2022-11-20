package model.ihm;

import model.objets.Jeu;
import model.objets.Commande;
import model.threads.ThreadMonstre;
import model.threads.ThreadAffichage;
import model.enums.Direction;
import model.enums.Ordre;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.Dimension;


/**
 * Classe FenetreGraphique représentant la fenetre principale d'interface graphique du projet
 */
public class FenetreGraphique extends JFrame {
    
    private JMenuBar menuBar; // La barre de menu située en haut
    private JPanel contentPane; // Le panel affichée dans la fenetre
    private Object verrouContent;
    private ThreadAffichage thread; // Le thread pour rafraîchir l'affichage
    private Jeu jeu; // Le moteur du jeu
    private int[] commandes_deplacement;
    private int commande_attaquer;
    private int commande_ouvrir;
    private int commande_ramasser;
    private int[] commandes_boire;
    private int[] commandes_competence;

    /**
     * Constructeur par défaut de la classe FenetreGraphique
     */
    public FenetreGraphique(){

        // Appel du constructeur de la classe JFRAME + passage titre fenêtre en paramètre
        super("Daedalus");
        
        // Réalisation des réglages graphiques de la fenêtre
        this.setVisible(true); // Rendre la fenêtre visible
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Terminer le programme lorsque l'on ferme la fenêtre
        this.setSize(new Dimension(700, 700)); // Modification des dimension de départ de la fenêtre
        this.setLocationRelativeTo(null); // Mise de la fenêtre au milieu de l'écran

        FenetreGraphique f = this;

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                int retour = JOptionPane.showConfirmDialog((JFrame)f, 
                "Êtes-vous sûr de vouloir quitter le jeu?", 
                "Attention",
                JOptionPane.YES_NO_OPTION);

                if(retour == JOptionPane.YES_OPTION){
                    f.dispose();
                }
            }
        }); 

        menuBar = new Menu(this); // Initialisation de la barre de menu
        this.setJMenuBar(menuBar); // Ajout de la barre de menu précédament créé

        // Mise des attributs inutiles (pour le moment) à null
        jeu = null;
        thread = null;

        // Initialisation d'un nouveau panel d'acceuil, et ajout de celui-ci dans la fenêtre
        contentPane = new PanelAccueil(this);
        this.setContentPane(contentPane);

        initialiserCommandes();

    }

    private void initialiserCommandes(){

        commandes_deplacement = new int[4];

        commandes_deplacement[0] = KeyEvent.VK_UP;
        commandes_deplacement[1] = KeyEvent.VK_DOWN;
        commandes_deplacement[2] = KeyEvent.VK_LEFT;
        commandes_deplacement[3] = KeyEvent.VK_RIGHT;

        commande_attaquer = KeyEvent.VK_SPACE;
        commande_ramasser = KeyEvent.VK_R;
        commande_ouvrir = KeyEvent.VK_O;

        commandes_boire = new int[5];
        commandes_boire[0] = KeyEvent.VK_NUMPAD1;
        commandes_boire[1] = KeyEvent.VK_NUMPAD2;
        commandes_boire[2] = KeyEvent.VK_NUMPAD3;
        commandes_boire[3] = KeyEvent.VK_NUMPAD4;
        commandes_boire[4] = KeyEvent.VK_NUMPAD5;

        commandes_competence = new int[4];
        commandes_competence[0] = KeyEvent.VK_1;
        commandes_competence[1] = KeyEvent.VK_2;
        commandes_competence[2] = KeyEvent.VK_3;
        commandes_competence[3] = KeyEvent.VK_4;

    }


    /**
     * Getter sur l'attribut jeu
     * @return la valeur de l'attribut jeu (type: Jeu)
     */
    public Jeu getJeu(){
        return this.jeu;
    }

    public Object getVerrouContent(){
        return this.verrouContent;
    }

    public int[] getCommandesDeplacements(){
        return this.commandes_deplacement;
    }

    public int getCommandeAttaquer(){
        return this.commande_attaquer;
    }

    public int getCommandeOuvrir(){
        return this.commande_ouvrir;
    }

    public int getCommandeRamasser(){
        return this.commande_ramasser;
    }

    public int[] getCommandesBoirePotions(){
        return this.commandes_boire;
    }

    public int[] getCommandesCompetences(){
        return this.commandes_competence;
    }

    public void setCmdDeplacementHaut(int c){
        this.commandes_deplacement[0] = c;
    }

    public void setCmdDeplacementBas(int c){
        this.commandes_deplacement[1] = c; 
    }

    public void setCmdDeplacementGauche(int c){
        this.commandes_deplacement[2] = c; 
    }

    public void setCmdDeplacementDroite(int c){
        this.commandes_deplacement[3] = c; 
    }

    public void setCmdAttaquer(int c){
        this.commande_attaquer = c; 
    }

    public void setCmdOuvrir(int c){
        this.commande_ouvrir = c; 
    }

    public void setCmdRamasser(int c){
        this.commande_ramasser = c; 
    }

    public void setCmdBoirePotions(int p1, int p2, int p3, int p4, int p5){
        this.commandes_boire[0] = p1;
        this.commandes_boire[1] = p2;
        this.commandes_boire[2] = p3;
        this.commandes_boire[3] = p4;
        this.commandes_boire[4] = p5;
    }

    public void setCmdCompetences(int c1, int c2, int c3, int c4){
        this.commandes_competence[0] = c1;
        this.commandes_competence[1] = c2;
        this.commandes_competence[2] = c3;
        this.commandes_competence[3] = c4;
    }

    public void afficherPartie(int nbNiveau){
        try{
            afficherPartie(nbNiveau, null);
        } catch(FileNotFoundException e){}
    }

    /**
     * Méthode qui permet de créer et d'afficher le panel de partie
     * @param nbNiveau Le nombre Maximum de niveau pour finir la partie
     */
    public void afficherPartie(int nbNiveau, String path) throws FileNotFoundException{

        if(path == null){
            // Création et intialisation du moteur de la partie, l'attribut jeu
            this.jeu = new Jeu(this, nbNiveau);
        } else {
            this.jeu = new Jeu(null, path, nbNiveau);
        }


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
                if(e.getKeyCode() == commandes_deplacement[0]){ // Si la touche est la flèche haut
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.HAUT); // Déplacer le joueur en haut
                    jeu.controles(c);
                } else if(e.getKeyCode() == commandes_deplacement[1]){ // Si la touche est la flèche bas
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.BAS); // Déplacer le joueur en bas
                    jeu.controles(c);
                } else if(e.getKeyCode() == commandes_deplacement[2]){ // Si la touche est la flèche gauche
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.GAUCHE); // Déplacer le joueur à gauche
                    jeu.controles(c);
                } else if(e.getKeyCode() == commandes_deplacement[3]){ // Si la touche est la flèche droite
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.DEPLACEMENT, Direction.DROITE); // Déplacer le joueur à droite
                    jeu.controles(c);
                } else if(e.getKeyCode() == commande_attaquer){ // Si la touche est la barre espace
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.ATTAQUE, null); // Attaque de la part du joueur 
                    jeu.controles(c);
                } else if(e.getKeyCode() == commande_ramasser){ // Si la touche est R 
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.RAMASSER, null); // Ramasser un trésor à proximité
                    jeu.controles(c);
                } else if (e.getKeyCode() == commande_ouvrir){ // Si la touche est O
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.OUVRIR, null); // Ouvrir un trésor à proximité
                    jeu.controles(c);
                } else if(e.getKeyCode() == commandes_boire[0] || e.getKeyCode() == commandes_boire[1]
                    || e.getKeyCode() == commandes_boire[2] || e.getKeyCode() == commandes_boire[3]
                    || e.getKeyCode() == commandes_boire[4]){ 
                    // Si la touche est entre 1 et 5 inclus du clavier numérique
                    // Création de la nouvelle commande associée puis réalisation de celle-ci
                    c = new Commande(Ordre.BOIRE, e.getKeyCode()-KeyEvent.VK_NUMPAD1);
                    jeu.controles(c);
                } else if(e.getKeyCode() == commandes_competence[0] || e.getKeyCode() == commandes_competence[1]
                || e.getKeyCode() == commandes_competence[2] || e.getKeyCode() == commandes_competence[3]){ 
                    c = new Commande(Ordre.COMPETENCE, e.getKeyCode()-KeyEvent.VK_1);
                    jeu.controles(c);
                }
            }
        });

        verrouContent = new Object();

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

        if(contentPane instanceof PanelPartie){
            // Création d'un nouveau panel de partie et écrassement de l'ancien
            contentPane = new PanelPartie(this, ((PanelPartie) contentPane).getDescriptionPotion());
            this.setContentPane(contentPane);

            // Mise à jour de la fenêtre
            this.validate();
        }
    }

    /**
     * Méthode qui sert à afficher le panel de fin de partie
     * @param result le résultat de la partie: true = victoire, false = defaite
     */
    public void afficherVueFin(boolean result){

        this.thread.arret();
        // Attente de la fin du thread d'affichage
        this.thread.interrupt();
        this.thread = null;

        synchronized(verrouContent){
            // Création d'un panel de fin de partie, et remplacement du panel de partie
            contentPane = new PanelFinJeu(this,result);
            this.setContentPane(contentPane);
        }

        // Mise à jour de la fenêtre
        this.validate();
    }

    public void quitterPartie(){

        if(thread != null){
            thread.arret();
            thread.interrupt();
            thread = null;
        }

        ArrayList<ThreadMonstre> monstres = jeu.getThreads();

        while(monstres.size()>0){
            monstres.get(0).arret();
            monstres.get(0).interrupt();
            monstres.remove(monstres.get(0));
        }

        this.dispose();
        new FenetreGraphique();

    }

    public void redemarrerPartie(){

        if(thread != null){
            thread.arret();
            thread.interrupt();
            thread = null;
        }

        ArrayList<ThreadMonstre> monstres = jeu.getThreads();

        while(monstres.size()>0){
            monstres.get(0).arret();
            monstres.get(0).interrupt();
            monstres.remove(monstres.get(0));
        }

        int nbMaxNiveau = jeu.getNbMaxNiveau();
        String path = jeu.getPath();
        try{
            afficherPartie(nbMaxNiveau, path);
        } catch(FileNotFoundException e){
            System.out.println("Le fichier voulu n'existe pas!");
        }
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
