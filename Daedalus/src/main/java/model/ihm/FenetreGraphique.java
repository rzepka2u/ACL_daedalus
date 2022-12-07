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
import javax.swing.JLabel;

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
    
    /**
     *  La barre de menu située en haut
     */
    private JMenuBar menuBar;

    /**
     * Le panel affichée dans la fenetre
     */
    private JPanel contentPane;

    /**
     * Le verrou associé au panel de la fenêtre
     */
    private Object verrouContent;

    /**
     * Le thread pour rafraîchir l'affichage
     */
    private ThreadAffichage thread; 

    /**
     * Le moteur du jeu
     */
    private Jeu jeu; 

    /**
     * Les codes des touches utilisées pour se déplacer
     */
    private int[] commandes_deplacement;

    /**
     * Le code de la touche utilisée pour attaquer
     */
    private int commande_attaquer;

    /**
     * Le code de la touche utilisée pour ouvrir
     */
    private int commande_ouvrir;

    /**
     * Le code de la toouche utilisée pour ramasser
     */
    private int commande_ramasser;

    /**
     * Les codes des touches utilisées pour boire les potions
     */
    private int[] commandes_boire;

    /**
     * Les codes des touches utilisées pour déclencher les compétences
     */
    private int[] commandes_competence;

    /**
     * Booléen qui sert à savoir si l'écouteur sur les touches saisies est déjà ajouté à la fenêtre
     */
    private boolean ecouteursAdd;

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
        this.verrouContent = new Object(); // Création d'un nouvel objet qui servira de verrou pour la panel de la fenêtre
        
        // Déclaration d'un variable pour pouvoir utiliser this dans la méthode windowClosing
        FenetreGraphique f = this;

        // Ajout d'un écouteur sur la fermeture de la fenêtre
        this.addWindowListener(new WindowAdapter(){
            /**
             * Méthode qui sera appelée lorsque l'on clique sur la croix pour fermer la fenêtre
             * @param e objet représentant l'événement qui vient de déclencher la méthode
             */
            @Override
            public void windowClosing(WindowEvent e){

                // On affiche un dialog à confirmer qui demande si on veut bien quitter le jeu
                int retour = JOptionPane.showConfirmDialog((JFrame)f, 
                "Êtes-vous sûr de vouloir quitter le jeu?", 
                "Attention",
                JOptionPane.YES_NO_OPTION);

                // Si l'utilisateur clique sur oui
                if(retour == JOptionPane.YES_OPTION){

                    int i;

                    // Section critique, on vérouille le vérrou associé au panel de fenêyre
                    synchronized(verrouContent){

                        // Si le panel est une instance de la classe PanelPartie
                        if(f.contentPane instanceof PanelPartie){

                            // On arrête le thread pour le rafraichissement de l'affichage
                            thread.arret();
                            thread.interrupt();

                            // On arrête tous les threads pour la gestion des monstres
                            for(i=0; i<f.getJeu().getEntites().size()-1; i++){
                                f.getJeu().getThreads().get(i).arret();
                                f.getJeu().getThreads().get(i).interrupt();
                            }      
                        }
                    }

                    // On libère les ressources de la fenêtre
                    f.dispose();
                }   
            }

        }); 

        menuBar = new Menu(this); // Initialisation de la barre de menu
        this.setJMenuBar(menuBar); // Ajout de la barre de menu précédament créé

        // Mise des attributs inutiles (pour le moment) à null
        jeu = null;
        thread = null;

        // L'écouteur pour les touches de la partie n'est pas encore ajouté 
        this.ecouteursAdd = false;

        // Initialisation d'un nouveau panel d'acceuil, et ajout de celui-ci dans la fenêtre
        contentPane = new PanelAccueil(this);
        this.setContentPane(contentPane);

        // Initialisation de tous les attributs de code des touches
        initialiserCommandes();

    }

    /**
     * Méthode qui permet de mettre tous les attributs concernant les codes des touches à leurs valeurs de base
     */
    public void initialiserCommandes(){

        // Création d'un tableau de quatre entiers pour les codes de déplacement dans l'attribut
        commandes_deplacement = new int[4];

        // Ajout des valeurs des codes dans le tableau
        commandes_deplacement[0] = KeyEvent.VK_UP;
        commandes_deplacement[1] = KeyEvent.VK_DOWN;
        commandes_deplacement[2] = KeyEvent.VK_LEFT;
        commandes_deplacement[3] = KeyEvent.VK_RIGHT;

        // Ajout du code pour l'attaque dans l'attribut associé
        commande_attaquer = KeyEvent.VK_SPACE;

        // Ajout du code pour le ramassage de trésor dans l'attribut associé
        commande_ramasser = KeyEvent.VK_R;

        // Ajout du code pour l'ouverture de coffre dans l'attribut associé
        commande_ouvrir = KeyEvent.VK_O;


        // Création d'un tableau de cinq entiers pour les codes des touches utilisées pour boire les potions
        commandes_boire = new int[5];

        // Ajout des valeurs des codes dans le tableau
        commandes_boire[0] = KeyEvent.VK_NUMPAD1;
        commandes_boire[1] = KeyEvent.VK_NUMPAD2;
        commandes_boire[2] = KeyEvent.VK_NUMPAD3;
        commandes_boire[3] = KeyEvent.VK_NUMPAD4;
        commandes_boire[4] = KeyEvent.VK_NUMPAD5;

        // Création d'un tableau de quatre entiers pour les codes des touches utilisées pour les compétences 
        commandes_competence = new int[4];

        // Ajout des valeurs des codes dans le tableau
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

    /**
     * Getter sur l'attribut thread
     * @return this.thread
     */
    public ThreadAffichage getThread(){
        return this.thread;
    }

    /** 
     * Setter sur l'attribut thread
     * @param t la nouvelle valeur de l'attribut thread 
     */
    public void setThread(ThreadAffichage t){
        this.thread = t;
    }

    /**
     * Getter sur l'attribut verrouContent
     * @return this.verrouContent
     */
    public Object getVerrouContent(){
        return this.verrouContent;
    }

    /**
     * Getter sur l'attribut commandes_deplacement
     * @return this.commandes_deplacement
     */
    public int[] getCommandesDeplacements(){
        return this.commandes_deplacement;
    }

    /**
     * Getter sur l'attribut commande_attaquer
     * @return this.commande_attaquer
     */
    public int getCommandeAttaquer(){
        return this.commande_attaquer;
    }

    /**
     * Getter sur l'attribut commande_ouvrir
     * @return this.commande_ouvrir
     */
    public int getCommandeOuvrir(){
        return this.commande_ouvrir;
    }

    /**
     * Getter sur l'attribut commande_ramasser
     * @return this.commande_ramasser
     */
    public int getCommandeRamasser(){
        return this.commande_ramasser;
    }

    /**
     * Getter sur l'attribut commandes_boire
     * @return this.commandes_boire
     */
    public int[] getCommandesBoirePotions(){
        return this.commandes_boire;
    }

    /**
     * Getter sur l'attribut commandes_competence
     * @return this.commandes_competence
     */
    public int[] getCommandesCompetences(){
        return this.commandes_competence;
    }

    /**
     * Modifie le code de la touche de déplacement vers le haut
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdDeplacementHaut(int c){
        this.commandes_deplacement[0] = c;
    }

    /**
     * Modifie le code de la touche de déplacement vers le bas
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdDeplacementBas(int c){
        this.commandes_deplacement[1] = c; 
    }

    /**
     * Modifie le code de la touche de déplacement vers la gauche
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdDeplacementGauche(int c){
        this.commandes_deplacement[2] = c; 
    }

    /**
     * Modifie le code de la touche de déplacement vers la droite
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdDeplacementDroite(int c){
        this.commandes_deplacement[3] = c; 
    }

    /**
     * Setter sur l'attribut commande_attaquer
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdAttaquer(int c){
        this.commande_attaquer = c; 
    }

    /**
     * Setter sur l'attribut commande_ouvrir
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdOuvrir(int c){
        this.commande_ouvrir = c; 
    }
    
    /**
     * Setter sur l'attribut commande_ramasser
     * @param c Le code de la nouvelle touche utilisée
     */
    public void setCmdRamasser(int c){
        this.commande_ramasser = c; 
    }

    /**
     * Modifie les codes des touches utilisées pour boire les potions
     * @param p1 Le code de la nouvelle touche utilisée pour boire la potion position 1 dans l'inventaire
     * @param p2 Le code de la nouvelle touche utilisée pour boire la potion position 2 dans l'inventaire
     * @param p3 Le code de la nouvelle touche utilisée pour boire la potion position 3 dans l'inventaire
     * @param p4 Le code de la nouvelle touche utilisée pour boire la potion position 4 dans l'inventaire
     * @param p5 Le code de la nouvelle touche utilisée pour boire la potion position 5 dans l'inventaire
     */
    public void setCmdBoirePotions(int p1, int p2, int p3, int p4, int p5){
        this.commandes_boire[0] = p1;
        this.commandes_boire[1] = p2;
        this.commandes_boire[2] = p3;
        this.commandes_boire[3] = p4;
        this.commandes_boire[4] = p5;
    }

    /**
     * Modifie les codes des touches utilisées pour déclencher les compétences
     * @param c1 Le code de la nouvelle touche utilisée pour déclencher la compétences position 1 dans la liste des compétences
     * @param c2 Le code de la nouvelle touche utilisée pour déclencher la compétences position 2 dans la liste des compétences
     * @param c3 Le code de la nouvelle touche utilisée pour déclencher la compétences position 3 dans la liste des compétences
     * @param c4 Le code de la nouvelle touche utilisée pour déclencher la compétences position 4 dans la liste des compétences
     */
    public void setCmdCompetences(int c1, int c2, int c3, int c4){
        this.commandes_competence[0] = c1;
        this.commandes_competence[1] = c2;
        this.commandes_competence[2] = c3;
        this.commandes_competence[3] = c4;
    }

    /**
     * Méthode qui permet de passer du panel d'accueil au panel de choix des compétences 
     * @param nbNiveau Le nombre de niveau choisit dans le formulaire du panel d'accueil
     * @param path Le chemin vers le dossier de fichiers labyrinthes choisit dans le formulaire du panel d'acceuil
     */
    public void afficherChoixCompetances(int nbNiveau, String path){
        
        // Initialisation d'un nouveau panel de choix de compétences et ajout de celui-ci dans la fenêtre
        contentPane = new PanelChoixCompetences(this, nbNiveau, path);
        this.setContentPane(contentPane);

        // Mise à jour de l'affichage de la fenêtre
        this.validate();
    }


    /**
     * Méthode qui permet de passer du panel de choix des compétences au panel de partie (et donc de démarrer la partie..) 
     * @param nbNiveau Le nombre de niveau choisit dans le formulaire du panel d'accueil
     * @param competences Les compétences choisit dans le formulaire du panel de choix des compétences
     */
    public void afficherPartie(int nbNiveau, int[] competences){

        // Appel de la surcharge de la méthode avec un dossier de fichier == null
        // (cas des labyrinthes générés aléatoirement)
        try{
            afficherPartie(nbNiveau, competences, null);
        } catch(FileNotFoundException e){}
    }

    /**
     * Méthode qui permet de passer du panel de choix de compétences au panel de partie (et donc de démarrer la partie..)
     * @param nbNiveau Le nombre de niveau choisit dans le formulaire du panl d'accueil
     * @param competences Les compétences choisit dans le formulaire du panel de choix des compétences
     * @param path Le chemin vers le dossier de fichiers labyrinthes choisit dans le formulaire du panel d'accueil
     */
    public void afficherPartie(int nbNiveau, int[] competences, String path) throws FileNotFoundException{

        // Si le dossier de fichier est null
        if(path == null){
            // Création et intialisation du moteur du jeu, avec les labyrinthes générés aléatoirement
            this.jeu = new Jeu(this, nbNiveau);
        } else { // Sinon
            // Création et initialisation du moteur du jeu, avec les labyrinthes générés par fichiers
            this.jeu = new Jeu(null, path, nbNiveau);
        }

        // On ajoute les compétences choisit dans les compétences sélectionnées par le joueur
        jeu.getJoueur().setCompetencesSelect(competences);

        // Initialisation d'un nouveau panel de partie, et ajout de celui-ci dans la fenêtre
        contentPane = new PanelPartie(this);
        this.setContentPane(contentPane);


        // Permettre à la fenêtre de pouvoir obtenir le focus et le demander
        this.setFocusable(true);
        this.requestFocus();

        // Si l'écouteur des touches de la partie n'est pas déjà ajouté
        if(!ecouteursAdd){

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
                        c = new Commande(Ordre.BOIRE, e.getKeyCode()-KeyEvent.VK_NUMPAD1); // Boire la potion
                        jeu.controles(c);
                    } else if(e.getKeyCode() == commandes_competence[0] || e.getKeyCode() == commandes_competence[1]
                    || e.getKeyCode() == commandes_competence[2] || e.getKeyCode() == commandes_competence[3]){ 
                        // Création de la nouvelle commande associée puis réalisation de celle-ci
                        // Si la touche est entre 1 et 4 inclus des chiffres du clavier simple (pas numérique)
                        c = new Commande(Ordre.COMPETENCE, e.getKeyCode()-KeyEvent.VK_1); // activer la compétence
                        jeu.controles(c);
                    }
                }
            });

            // On passe le booléen à true pour ne pas recréer d'écouteur par la suite si on redémarre la partie
            ecouteursAdd = true;
        }

        // Création et démarrage du thread qui s'occupe de rafraîchir l'affichage
        thread = new ThreadAffichage(this);
        thread.start();

        // Mise à jour de la fenêtre
        this.validate();
    }

    /**
     * Méthode qui permet de créer le panel de partie (et donc de lancer le jeu..) dans le contexte d'une sauvegarde
     * @param j Le jeu récupérer de la sauvegarde
     */
    public void afficherPartie(Jeu j) throws FileNotFoundException{

        // Changement de la valeur de l'attribut jeu
        this.jeu = j;

        // On ajoute la nouvelle fenêtre du jeu
        j.setFenetre(this);

        // On créer la nouvelle liste des vérrous pour les entités
        ArrayList<Object> verrousEnt = new ArrayList<>();

        // Pour chacune des entités de la liste d'entités du jeu
        for (int i = 0; i < j.getEntites().size(); i++) {
            // On ajoute un vérrou à la liste des verrous
            verrousEnt.add(new Object());
        }

        // La nouvelle liste des vérrous associés aux entités devient celle du jeu
        j.setVerrousEntites(verrousEnt);

        // On créer un nouveau verrou pour la liste des informations
        j.setVerrouInformations(new Object());

        // On créer une nouvelle matrice de vérrous pour les cases du labyrinthe
        ArrayList<ArrayList<Object>> verrousCases = new ArrayList<>();

        // Pour toutes les lignes du labyrinthe
        for (int i = 0; i < j.getLabyrinthe().getHauteur(); i++) {
            // On ajoute un nouveux tableau à la matrice des vérrous
            verrousCases.add(new ArrayList<>());
            //Pour toutes les colonnes du labyrinthe
            for (int k = 0; k < j.getLabyrinthe().getLargeur(); k++) {
                // On ajoute un nouveau vérrou à la matrice
                verrousCases.get(i).add(new Object());
            }
        }

        // On défini la nouvelle matrice de vérrous obtenue comme étant celle des cases du labyrinthe du jeu.
        j.getLabyrinthe().setVerrousCases(verrousCases);

        // Pour chacun des threads d'entités du jeu
        for (ThreadMonstre tm : j.getThreads()) {

            // Récupération de sa position dans la liste du jeu
            int pos = tm.getPositionInList()+1;

            // Si le monstre n'était pas mort
            if (jeu.getEntites().get(pos).getPointsVie() > 0) {

                // On redémarre le thread
                tm.start();
            }
        }

        // Initialisation d'un nouveau panel de partie, et ajout de celui-ci dans la fenêtre
        contentPane = new PanelPartie(this);
        this.setContentPane(contentPane);


        // Permettre à la fenêtre de pouvoir obtenir le focus et le demander
        this.setFocusable(true);
        this.requestFocus();

        // Si l'écouteur des touches de la partie n'est pas déjà ajouté
        if(!ecouteursAdd){
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
                        c = new Commande(Ordre.BOIRE, e.getKeyCode()-KeyEvent.VK_NUMPAD1); // Boire la potion
                        jeu.controles(c);
                    } else if(e.getKeyCode() == commandes_competence[0] || e.getKeyCode() == commandes_competence[1]
                            || e.getKeyCode() == commandes_competence[2] || e.getKeyCode() == commandes_competence[3]){
                        // Création de la nouvelle commande associée puis réalisation de celle-ci
                        // Si la touche est entre 1 et 4 inclus des chiffres du clavier simple (pas numérique)
                        c = new Commande(Ordre.COMPETENCE, e.getKeyCode()-KeyEvent.VK_1); // activer la compétence
                        jeu.controles(c);
                    }
                }
            });

            // On passe le booléen à true pour ne pas recréer d'écouteur par la suite si on redémarre la partie
            ecouteursAdd = true;
        }

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

        JLabel descrPotion;

        // Section critique, vérouillage du vérrou pour le panel de la fenêtre
        synchronized(verrouContent){

            // Si le panel est une instance de la classe PanelPartie
            if(contentPane instanceof PanelPartie){
                    
                // On récupère le texte contenant la description de la potion dans le panel
                descrPotion = ((PanelPartie) contentPane).getDescriptionPotion();

                // On créer un nouveau panel de partie sur l'état actuel du jeu
                PanelPartie panel = new PanelPartie(this, descrPotion);
            
                // Création d'un nouveau panel de partie et écrassement de l'ancien
                contentPane = panel;
                this.setContentPane(contentPane);

                // Mise à jour de la fenêtre
                this.validate();
            }
        }
        
    }

    /**
     * Méthode qui sert à afficher le panel de fin de partie
     * @param result le résultat de la partie: true = victoire, false = defaite
     */
    public void afficherVueFin(boolean result){
        
        // Si le thread affichage n'est pas null
        if(thread != null){

            // Arret du thread
            this.thread.arret();
            this.thread = null;
        }

        // Création du nouveau panel de fin de jeu
        PanelFinJeu panel = new PanelFinJeu(this,result);

        // Section critique, vérouillage du verrou associé au panel de la fenêtre
        synchronized(verrouContent){

            // Remplacement de l'ancien panel par le panel de fin de partie
            contentPane = panel;
            this.setContentPane(contentPane);
        }

        // Mise à jour de la fenêtre
        this.validate();
    }

    /**
     * Méthode qui sert à quitter la partie (bouton menu)
     */
    public void quitterPartie(){

        // Si le thread pour l'affichage n'est pas null
        if(thread != null){

            // Arrêt du thread
            thread.arret();
            thread.interrupt();
            thread = null;
        }

        // On récupère les threads associés aux monstres du jeu
        ArrayList<ThreadMonstre> monstres = jeu.getThreads();


        // Tant qu'il reste des threads 
        while(monstres.size()>0){
            // On arrête le premier thread de la liste
            monstres.get(0).arret();
            monstres.get(0).interrupt();
            monstres.remove(monstres.get(0));
        }

        // On libère les ressouces de la fenêtre
        this.dispose();

        // On créer une nouvelle fenêtre graphique
        new FenetreGraphique();

    }

    /**
     * Méthode qui sert à redémarrer la partie (bouton menu)
     */
    public void redemarrerPartie(){

        // Récupération des données nécessaires
        int nbMaxNiveau = jeu.getNbMaxNiveau();
        String path = jeu.getPath();

        // Si le thread pour l'affichage n'est pas null
        if(thread != null){

            // Arrêt du thread
            thread.arret();
            thread.interrupt();
            thread = null;
        }

        // On récupère les threads associés aux monstres du jeu
        ArrayList<ThreadMonstre> monstres = jeu.getThreads();


        // Tant qu'il reste des threads 
        while(monstres.size()>0){
            // On arrête le premier thread de la liste
            monstres.get(0).arret();
            monstres.get(0).interrupt();
            monstres.remove(monstres.get(0));
        }


        // Affichage d'un nouveau Panel de partie et création d'une nouvelle instance de jeu
        try{
            afficherPartie(nbMaxNiveau, jeu.getJoueur().getCompetencesSelect(), path);
        } catch(FileNotFoundException e){
            System.out.println("Le fichier voulu n'existe pas!");
        }
    }

}
