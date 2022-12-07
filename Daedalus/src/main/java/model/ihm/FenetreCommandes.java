package model.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;


/**
 * Classe qui représente la fenêtre des touches du jeu
 */
public class FenetreCommandes extends JFrame {

    /**
     * La fentre graphique principale du jeu
     */
    private FenetreGraphique fenetre;

    /**
     * Le panel qui contient tous ce que contient la fenêtre des touches
     */
    private JPanel contentPane;

    /** 
     * Le texte contenant le titre de la fenêtre 
     */
    private JLabel titreLabel;

    /**
     * Le texte contenant la description de la fenêtre
     */
    private JLabel descriptionLabel;

    /**
     * Les textes servant d'étiquettes aux champs de textes de chacunes des touches de déplacements
     */
    private JLabel[] deplacementsLabels;

    /**
     * Les champs de textes de chacunes des touches de déplacements
     */
    private JTextField[] deplacementsFields;

    /**
     * Le texte servant d'étiquette au champ de texte de la touche d'attaque
     */
    private JLabel attaquerLabel;

    /**
     * Le champ de texte de la touche d'attaque
     */
    private JTextField attaquerField;

    /**
     * Le texte servant d'étiquette au champ de texte de la touche pour ramasser
     */
    private JLabel ramasserLabel;

    /**
     * Le champ de texte de la touche pour ramasser
     */
    private JTextField ramasserField;

    /**
     * Le texte servant d'étiquette au champ de texte de la touche pour ouvrir
     */
    private JLabel ouvrirLabel;

    /**
     * Le champ de texte de la touche pour ouvrir
     */
    private JTextField ouvrirField;

    /**
     * Les textes servant d'étiquettes aux champs de textes pour chacunes des touches utilisées pour boire les potions
     */
    private JLabel[] boirePotionsLabels;

    /**
     *  Les champs de textes pour chacunes des touches utilisées pour boire les potions
     */
    private JTextField[] boirePotionsFields;

    /**
     * Les textes servant d'étiquettes aux champs de textes pour chacunes des touches utilisées pour déclencher les compétences
     */
    private JLabel[] competencesLabels;

    /**
     * Les champs de textes pour chacunes des touches utilisées pour déclencher les compétences
     */
    private JTextField[] competencesFields;

    /**
     * Le boutton servant à valider les données renseignées dans le formulaire
     */
    private JButton boutonValider;

    /**
     * Le bouton servant à reset les touches de base 
     */
    private JButton boutonReset;


    // Constantes de la classe contenant les images utilisées dans les instances de cette classe
    // (pour éviter la relecture à chaque mise à jour de l'affichage)
    private static final ImageIcon imgCommandes = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/commandes.png"));
    private static final ImageIcon imgDeplaceHaut = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/deplace_haut.png"));
    private static final ImageIcon imgDeplaceBas = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/deplace_bas.png"));
    private static final ImageIcon imgDeplaceGauche = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/deplace_gauche.png"));
    private static final ImageIcon imgDeplaceDroite = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/deplace_droite.png"));
    private static final ImageIcon imgAttaquer = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/attaquer.png"));
    private static final ImageIcon imgRamasser = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/ramasser.png"));
    private static final ImageIcon imgOuvrir = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/ouvrir.png"));
    private static final ImageIcon imgBoire = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/boire.png"));
    private static final ImageIcon imgCompetence = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/competence.png"));


    /**
     * Constructeur par défaut de la classe FenetreCommandes
     * @param f la fenêtre graphique principale du jeu
     */
    public FenetreCommandes(FenetreGraphique f){

        // Appel au constructeur de la classe mère
        super("Commandes");

        this.setVisible(true); // Rendre la fenêtre visible
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Terminer le programme lorsque l'on ferme la fenêtre
        this.setSize(new Dimension(600, 700)); // Modification des dimension de départ de la fenêtre
        this.setLocationRelativeTo(null); // Mise de la fenêtre au milieu de l'écran
        
        // Initialisation des attributs fentre et contentPane
        fenetre = f;
        contentPane = (JPanel) this.getContentPane();

        // Création du texte de titre de la page 
        titreLabel = createTitreLabel();
        // Ajout du titre au nord du panel de la fenêtre
        contentPane.add(titreLabel, BorderLayout.NORTH);

        // Création d'un nouveau panel avec la stratégie de positionnement GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        
        // Création d'un panel "scrollable" dans lequel on ajout le le panel GridBagLayout
        JScrollPane scrol = new JScrollPane(panel);

        // Ajout du panel "scrollable" au centre du panel de la fenêtre
        contentPane.add(scrol, BorderLayout.CENTER);

        // Modification de la couleur de fond du panel de la fenêtre
        panel.setBackground(new Color(33,32,30));

        // Création d'un nouvel objet de contraintes
        GridBagConstraints gc = new GridBagConstraints();

        // Initialisation des contraintes
        gc.gridx = 0; // Sa position x dans le tableau (abscisses)
        gc.gridy = 0; // Sa position y dans le tableau (ordonnées)
        gc.gridwidth = 2; // L'élément prend 2 cases en abscisses
        gc.insets = new Insets(20,20,20,20); // Ajout de padding dans la case de l'élément
        gc.fill = GridBagConstraints.VERTICAL; // L'élément prend toute la place disponible verticalement
        gc.anchor = GridBagConstraints.CENTER; // L'élément est ancré au centre de la case


        // Création du texte de description de la page
        descriptionLabel = createDescriptionLabel();
        // Ajout du texte de description dans le panel GridBagLayout, avec les contraintes associées
        panel.add(descriptionLabel, gc);

        // L'élément prend la taille d'une case en abscisses
        gc.gridwidth = 1;
        // On passe à la ligne d'en dessous
        gc.gridy++;

        // On crée les textes servant d'étiquettes aux champs de texte pour les déplacements
        deplacementsLabels = createDeplacementsLabels();

        // On crée les champs de texte pour les déplacements 
        deplacementsFields = createDeplacementsFields();

        // Pour chaque textes servant d'étiquettes aux champs de texte pour les déplacements
        for(int i=0; i<deplacementsLabels.length; i++){

            // On ajoute le texte servant d'étiquette au panel GridBagLayout, avec les contraintes associées
            panel.add(deplacementsLabels[i], gc);

            // On se décale d'une colone à droite
            gc.gridx++;

            //On ajoute le champ de texte qui correspond à l'étiquette au GridBagLayout, avec les contraintes associées
            panel.add(deplacementsFields[i], gc);

            // On revient à la première colonne
            gc.gridx=0;
            // On passe à la ligne d'en dessous
            gc.gridy++;
        }

        // Création du texte servant d'étiquette pour la touche d'attaque
        attaquerLabel = createAttaquerLabel();
        // Ajout du texte précédemment créé dans le panel GridBagLayout, avec les contraintes associées
        panel.add(attaquerLabel, gc);

        // On se décale d'une colonne vers la droite
        gc.gridx++;

        // Création du champ de texte pour la touche d'attaque
        attaquerField = createAttaquerField();
        // Ajout du champ de texte précédemment créé dans le panel GridBagLayout, avec les contraintes associées
        panel.add(attaquerField, gc);

        // On passe à la ligne d'en dessous
        gc.gridy++;
        // On revient à la première colonne
        gc.gridx = 0;

        // Création du texte servant d'étiquette pour la touche ramasser
        ramasserLabel = createRamasserLabel();
        // Ajout du texte précédemment créé dans le panel GridBagLayout, avec les containtes associées
        panel.add(ramasserLabel, gc);

        // On se décale d'une colone vers la gauche
        gc.gridx++;

        // Création du champ de texte pour la touche ramasser
        ramasserField = createRamasserField();
        // Ajout du champ de texte précédemment créé dans le panel GridBagLayout, avec les contraintes associées
        panel.add(ramasserField, gc);

        // On passe à la ligne d'en dessous
        gc.gridy++;
        // On revient à la première colonne
        gc.gridx = 0;

        // Création du texte servant d'étiquette pour la touche ouvrir trésor
        ouvrirLabel = createOuvrirLabel();
        // Ajout du texte précédémment créé dans le panel GridBagLayout, avec les contraintes associées
        panel.add(ouvrirLabel, gc);

        // On se décale d'une colonne à droite
        gc.gridx++;

        // Création du champ de texte pour la touche ouvrir trésor
        ouvrirField = createOuvrirField();
        // Ajout du champ de texte précédémment créé dans le panel GridBagLayout, avec les contraintes associées
        panel.add(ouvrirField, gc);

        // On passe à la ligne d'en dessous
        gc.gridy++;

        // On revient à la première colonne
        gc.gridx=0;

        // Créations des textes servant d'étiquettes pour les touches utilisées pour boire les potions
        boirePotionsLabels = createBoirePotionsLabels();

        // Créations des champs de textes pour les touches utilisées pour boire les potions
        boirePotionsFields = createBoirePotionsFields();

        // Pour chacuns des textes d'étiquettes servant pour les touches utilisées pour boire les potions
        for(int i=0; i<boirePotionsLabels.length; i++){

            // On ajoute le texte servant d'étiquette dans le panel GridBagLayout, avec les contraintes associées
            panel.add(boirePotionsLabels[i], gc);

            // On se décale d'une colonne vers la droite
            gc.gridx++;

            // On ajoute le champ de texte associé à l'étiquette dans le panel GridBagLayout, avec les contraintes associées
            panel.add(boirePotionsFields[i], gc);

            // On passe à la ligne d'en dessosu
            gc.gridy++;

            // On revient à la première colonne
            gc.gridx=0;
        }

        // Création des textes servant d'étiquettes pour les touches utilisées pour déclencher une compétence
        competencesLabels = createCompetencesLabels();

        // Création des champs de textes pour les touches utilisées pour déclencher une compétence
        competencesFields = createCompetencesFields();

        // Pour chacun des textes servant d'étiquettes pour les touches utilisées pour déclencher une compétence
        for(int i=0; i<competencesLabels.length; i++){

            // On ajoute le texte servant d'étiquette au panel GridBagLayout, avec les containtes associées
            panel.add(competencesLabels[i], gc);

            // On se décale d'une colonne vers la droite
            gc.gridx++;

            // On ajoute le champ de texte associé à l'étiquette au panl GridBagLayout, avec les contraintes associées
            panel.add(competencesFields[i], gc);

            // On passe à la ligne d'en dessous
            gc.gridy++;

            // On revient à la première colonne
            gc.gridx=0;
        }

        
        gc.gridy++; // On passe à la ligne d'en dessous
        gc.fill = GridBagConstraints.HORIZONTAL; // L'élément doit prendre toute la place disponible horizontalement

        // Création du bouton pour reset les commandes par défaut
        boutonReset = createBoutonReset(panel, gc);
        // Ajout du bouton reset dans le panel GridBagLayout, avec les contraintes associées
        panel.add(boutonReset, gc);

        // On se décale d'une colonne vers la droite
        gc.gridx++;

        // Création du bouton pour valider le changement des commandes
        boutonValider = createBoutonValider(panel, gc);
        // Ajout du bouton pour valider dans le panel GridBagLayout, avec les contraintes associées
        panel.add(boutonValider, gc);

    }

    /**
     * Méthode qui permet de créer le texte du titre de la fenêtre des touches
     * @return objet JLabel représentant le texte du titre
     */
    private JLabel createTitreLabel(){

        // Création d'un nouveau texte et initialisation de son contenu
        JLabel label = new JLabel("<html><span style='font-size:18px;'>  LES COMMANDES DU JEU</span></html>");
        label.setIcon(imgCommandes); // Ajout de l'images allant avec le titre
        label.setPreferredSize(new Dimension(400,90)); // Ajout d'une dimension préférentielle
        label.setForeground(new Color(33,32,30)); // Modifie la couleur du texte
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte horizontalement dans la zone de texte
        label.setVerticalAlignment(SwingConstants.CENTER); // Centre le texte verticalement dans la zone de texte
        
        return label;

    }

    /**
     * Méthode qui permet de créer le texte de la description de la fenêtre des touches
     * @return objet JLabel représentant la description de la fenêtre des touches
     */
    private JLabel createDescriptionLabel(){

        //Création d'un nouveau texte et initialisation de son contenu
        JLabel label = new JLabel("<html><p>Vous pouvez consulter ci-dessous les commandes du jeu.<br>Vous pouvez également les modifier comme vous le souhaitez.</p></html>");

        label.setForeground(new Color(255,255,255)); // Change la couleur du texte
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte horizontalement dans la zone de texte
        label.setVerticalAlignment(SwingConstants.CENTER); // Centre le texte verticalement dans la zone de texte
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(33,32,30)));

        return label;

    }

    /**
     * Méthode qui permet de transformer le code entier d'un caractère en une description sous forme de String
     * @param c le code à transformer
     * @return la chaîne de caractères obtenu après transformation
     */
    private String stringOfCode(int c){

        // Déclaration d'une variable pour le retour 
        String res = "";

        // Si la touche est la flèche du haut
        if(c == KeyEvent.VK_UP) { 
            res = "FLECHE HAUT";
        }

        // Si la touche est la flèche du bas
        if(c == KeyEvent.VK_DOWN) { 
            res = "FLECHE BAS";
        }

        // Si la touche est la flèche de gauche
        if(c == KeyEvent.VK_LEFT) { 
            res = "FLECHE GAUCHE";
        }

        // Si la touche est la flèche de droite
        if(c == KeyEvent.VK_RIGHT) { 
            res = "FLECHE DROITE";
        }

        // Si la touche est la barre d'espace
        if(c == KeyEvent.VK_SPACE){
            res = "ESPACE";
        }

        // Si la touche est le 0 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD0){
            res = "0 NUMPAD";
        }

        // Si la touche est le 1 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD1){
            res = "1 NUMPAD";
        }

        // Si la touche est le 2 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD2){
            res = "2 NUMPAD";
        }

        // Si la touche est le 3 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD3){
            res = "3 NUMPAD";
        }

        // Si la touche est le 4 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD4){
            res = "4 NUMPAD";
        }

        // Si la touche est le 5 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD5){
            res = "5 NUMPAD";
        }

        // Si la touche est 6 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD6){
            res = "6 NUMPAD";
        }

        // Si la touche est le 7 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD7){
            res = "7 NUMPAD";
        }

        // Si la touche est le 8 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD8){
            res = "8 NUMPAD";
        }

        // Si la touche est le 9 du pavé numérique
        if(c == KeyEvent.VK_NUMPAD9){
            res = "9 NUMPAD";
        }

        // Si la touche est la touche contrôle
        if(c == KeyEvent.VK_CONTROL){
            res = "Ctrl";
        }

        // Si la touche est la touche shift
        if(c == KeyEvent.VK_SHIFT){
            res = "Shift";
        }

        // Si la touche est la touche tabulation
        if(c == KeyEvent.VK_TAB){
            res = "Tabulation";
        }

        // Si la touch est la touche ALT
        if(c == KeyEvent.VK_ALT){
            res = "Alt";
        }

        // Si la touche est la touche ALT GR
        if(c == KeyEvent.VK_ALT_GRAPH){
            res = "AltGr";
        }

        // SI la touche est la touche entrée
        if(c == KeyEvent.VK_ENTER){
            res = "Entrer";
        }

        // Si la touche est la touche effacer
        if(c == KeyEvent.VK_BACK_SPACE){
            res = "Cancel";
        }

        // Si la touche est la touche pour vérouiller les majuscules
        if(c == KeyEvent.VK_CAPS_LOCK){
            res = "Verr. Maj";
        }

        // Si la touche est F1
        if(c == KeyEvent.VK_F1){
            res = "F1";
        }

        // Si la touche est F2
        if(c == KeyEvent.VK_F2){
            res = "F2";
        }

        // Si la touche est F3
        if(c == KeyEvent.VK_F3){
            res = "F3";
        }

        // Si la touche est F4
        if(c == KeyEvent.VK_F4){
            res = "F4";
        }

        // Si la touche est F5
        if(c == KeyEvent.VK_F5){
            res = "F5";
        }

        // Si la touche est F6
        if(c == KeyEvent.VK_F6){
            res = "F6";
        }

        // Si la touche est F7
        if(c == KeyEvent.VK_F7){
            res = "F7";
        }

        // Si la touche est F8
        if(c == KeyEvent.VK_F8){
            res = "F8";
        }

        // Si la touche est F9
        if(c == KeyEvent.VK_F9){
            res = "F9";
        }

        // Si la touche est F10
        if(c == KeyEvent.VK_F10){
            res = "F10";
        }

        // Si la touche est F11
        if(c == KeyEvent.VK_F11){
            res = "F11";
        }

        // Si la touche est F12
        if(c == KeyEvent.VK_F12){
            res = "F12";
        }

        // SI la touche est la touche échap
        if(c == KeyEvent.VK_ESCAPE){
            res = "Echap";
        }

        return res;
    }

    /**
     * Méthode qui permet de créer les textes servant d'étiquettes pour les touches de déplacements
     * @return Le tableau contenant les textes qui représente les étiquettes 
     */
    private JLabel[] createDeplacementsLabels(){

        // Création d'un nouveau tableau de quatre textes
        JLabel[] labels = new JLabel[4];

        // Création du texte pour le déplacement vers le haut 
        labels[0] = new JLabel("  Deplacement Haut:");
        // Ajout de son icon
        labels[0].setIcon(imgDeplaceHaut);

        // Création du texte pour le déplacement vers le bas
        labels[1] = new JLabel("  Deplacement Bas:");
        // Ajout de son icon
        labels[1].setIcon(imgDeplaceBas);

        // Création du texte pour le déplacement vers la gauche
        labels[2] = new JLabel("  Deplacement Gauche:");
        // Ajout de son icon
        labels[2].setIcon(imgDeplaceGauche);

        // Création du texte pour le déplacement vers la droite
        labels[3] = new JLabel("  Deplacement Droite:");
        // Ajout de son icon
        labels[3].setIcon(imgDeplaceDroite);

        // Pour chacuns des quatre textes
        for(int i=0; i<labels.length; i++){
            
            // On rend le fond visible
            labels[i].setOpaque(true);
            // On définit une taille de préférence
            labels[i].setPreferredSize(new Dimension(170,48));
            // On centre le texte verticalement dans la zone de texte
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            // On change la couleur de fond
            labels[i].setBackground(new Color(255,255,255));
            // ON ajoute un border (pour créer un effet de marge interne donc même couleur que le fond)
            labels[i].setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, new Color(255,255,255)));
        }

        return labels;

    }

    /**
     * Méthode qui permet de créer les champs de textes pour les touches de déplacements
     * @return le tableau contenant les quatre champs de textes
     */
    private JTextField[] createDeplacementsFields(){

        // Création d'un tableau de quatre champs de texte
        JTextField[] fields = new JTextField[4];

        // Récupération des codes des touches définies pour les déplacements du joueur
        int[] codes = fenetre.getCommandesDeplacements();

        // Pour chacun des quatre champs de textes
        for(int i=0; i<fields.length; i++){

            // On créer un nouveau champ de texte vide
            fields[i] = new JTextField();
            // On défini sa largeur à 12 caractères
            fields[i].setColumns(12);
            // On centre le texte horizontalement dans la zone de texte
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            // On récupère la description du code représentant la touche de la direction associé sous la forme d'un String
            String txt = stringOfCode(codes[i]);
            
            // Si la taille du texte récupéré est de 0, on récupère la valeur du code en caractère
            if(txt.length() == 0) txt = ""+(char) codes[i];
            // On ajoute cette valeur au champ de texte
            fields[i].setText(txt);
        }

        // Ajout d'un écouteur au premier champ de texte sur les touches relachées
        fields[0].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){

                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la descripton est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[0].setText(res);
            }
        });

        // Ajout d'un écouteur au deuxième champ de texte sur les touches relachées
        fields[1].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la descripton est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[1].setText(res);
                
            }
        });

        // Ajout d'un écouteur au troisème champ de texte sur les touches relachées
        fields[2].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la descripton est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[2].setText(res);
            }
        });

        // Ajout d'un écouteur au quatrième champ de texte sur les touches relachées
        fields[3].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la descripton est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[3].setText(res);
            }
        });

        return fields;

    }

    /**
     * Méthode qui permet de crée le texte servant d'étiquette pour la touche d'attaque
     * @return objet JLabel représentant le texte servant d'étiquette 
     */
    private JLabel createAttaquerLabel(){

        // Création d'un nouveau texte et initialisation de son contenu
        JLabel label = new JLabel("  Attaquer:");
        // Ajout de son icon
        label.setIcon(imgAttaquer);
        // Rend le fond visible
        label.setOpaque(true);
        // Ajout d'une taille préférentielle
        label.setPreferredSize(new Dimension(170,48));
        // On centre le texte verticalement dans la zone de texte
        label.setVerticalAlignment(SwingConstants.CENTER);
        // On modifie la couleur de fond
        label.setBackground(new Color(255,255,255));
        // On ajoute une bordure (de même couleur que le fond pour créer un effet de marge interne)
        label.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, new Color(255,255,255)));
        
        return label;
    }

    /**
     * Méthode qui permet de créer le champ de texte pour la touche d'attaque
     * @return le champ de texte correctement instancié
     */
    private JTextField createAttaquerField(){

        // Création d'un nouveau champ de texte vide
        JTextField field = new JTextField();
        // Modification de sa largeur à 12 caractères
        field.setColumns(12);
        // On centre le texte horizontalement dans la zone de texte
        field.setHorizontalAlignment(SwingConstants.CENTER);
        // On récupère le code associé à la touche pour attaquer
        int code = fenetre.getCommandeAttaquer();
        // On transforme le code en description sous la forme d'une chaîne de caractères
        String txt = stringOfCode(code);

        // Si la chaîne obtenu est de taille 0, la chaîne devient le code sous la forme d'un caractère
        if(txt.length() == 0) txt = ""+(char) code;

        // On ajoute le String obtenu au champ de texte
        field.setText(txt);

        // Ajout d'un écouteur au champ de texte sur les touches relachées
        field.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la descripton est vide                
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                field.setText(res);
            }
        });

        return field;

    }

    /**
     * Méthode qui permet de créer le texte servant d'étiquette pour la touche ramasser
     * @return objet JLabel représentant l'étiquette voulue
     */
    private JLabel createRamasserLabel(){

        // Création d'un nouveau texte et intialisation de son contenu
        JLabel label = new JLabel("  Ramasser trésor:");
        // Ajout de son icon
        label.setIcon(imgRamasser);
        // On rend le fond visible
        label.setOpaque(true);
        // On ajoute une taille préférentielle
        label.setPreferredSize(new Dimension(170,48));
        // On centre verticalement le texte dans la zone de texte
        label.setVerticalAlignment(SwingConstants.CENTER);
        // On change la couleur de fond
        label.setBackground(new Color(255,255,255));
        // On ajoute une bordure (de même couleur que le fond pour créer un effet de marge interne)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        
        return label;
    }

    /**
     * Méthode qui permet de créer le champ de texte pour la touche ramasser
     * @return le champ de texte correctement instancié
     */
    private JTextField createRamasserField(){

        // Création d'un nouveau champ de texte vide 
        JTextField field = new JTextField();
        // Modification de sa largeur à 12 caractères
        field.setColumns(12);
        // On centre le texte horizontalement dans la zone de texte
        field.setHorizontalAlignment(SwingConstants.CENTER);
        // On récupère le code associé à la touche pour ramasser
        int code = fenetre.getCommandeRamasser();
        // On transforme le code en description sous la forme d'une chaîne de caractères
        String txt = stringOfCode(code);

        // Si la chaîne obtenue est de taille 0, la chaîne devient le code sous la forme d'un caractère
        if(txt.length() == 0) txt = ""+(char) code;

        // On ajout le String obtenu dans le champ de textes
        field.setText(txt);

        // Ajout d'un écouteur au champ de texte sur les touches relachées
        field.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide 
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                field.setText(res);
            }
        });

        return field;
    }

    /**
     * Méthode qui permet de créer le texte servant d'étiquette pour la touche ouvrir trésor
     * @return le texte servant d'étiquette correctement instancié
     */
    private JLabel createOuvrirLabel(){

        // Création d'un nouveau texte et initialisation de son contenu
        JLabel label = new JLabel("  Ouvrir trésor:");
        // Ajout de son icon
        label.setIcon(imgOuvrir);
        // Ajout d'un taille préférentielle
        label.setPreferredSize(new Dimension(170,48));
        // On centre le texte verticalement dans la zone de texte
        label.setVerticalAlignment(SwingConstants.CENTER);
        // On rend le fond visible
        label.setOpaque(true);
        // On modifie la couleur de fond
        label.setBackground(new Color(255,255,255));
        // On ajoute une bordure (de même couleur que le fond pour créer un effet de marges internes)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
       
        return label;

    }

    /**
     * Méthode qui permet de créer le champ de texte pour la touche ouvrir trésor
     * @return le champ de texte voulue correctement instancié
     */
    private JTextField createOuvrirField(){

        // Création d'un nouveau champ de texte vide 
        JTextField field = new JTextField();
        // Modification de sa largeur à 12 caractères
        field.setColumns(12);
        // On centre le texte horizontalement dans la zone de texte
        field.setHorizontalAlignment(SwingConstants.CENTER);
        // On récupère le code associé à la touche pour ouvir un trésor
        int code = fenetre.getCommandeOuvrir();
        // On transforme le code en description sous la forme de chaîne de caractères
        String txt = stringOfCode(code);

        // Si la chaîne obtenue est de taille 0, la chaîne devient le code sous la forme d'un caractère
        if(txt.length() == 0) txt = ""+(char) code;

        // On ajoute le String obtenu dans le champ de texte
        field.setText(txt);

        // Ajout d'un écouteur au champ de texte sur les touches relachées
        field.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                field.setText(res);
            }
        });

        return field;
    }

    /**
     * Méthode qui permet de créer les textes servant d'étiquettes pour les touches utilisées pour boire les potions
     * @return un tableau contenant les cinq textes d'étiquettes correctement instanciés
     */
    private JLabel[] createBoirePotionsLabels(){

        // Création d'un nouveau tableau de cinq textes
        JLabel[] labels = new JLabel[5];

        // Initialisation du texte pour la touche de la place 1 de l'inventaire
        labels[0] = new JLabel("  Boire potion 1:");
        // Initialisation du texte pour la touche de la place 2 de l'inventaire
        labels[1] = new JLabel("  Boire potion 2:");
        // Initialisation du texte pour la touche de la place 3 de l'inventaire
        labels[2] = new JLabel("  Boire potion 3:");
        // Initialisation du texte pour la touche de la place 4 de l'inventaire
        labels[3] = new JLabel("  Boire potion 4:");
        // Initialisation du texte pour la touche de la place 5 de l'inventaire
        labels[4] = new JLabel("  Boire potion 5:");

        // Pour chacuns des cinq textes
        for(int i=0; i<labels.length; i++){
            // On rend le fond visible
            labels[i].setOpaque(true);
            // On ajoute l'icon de potion
            labels[i].setIcon(imgBoire);
            // On défini une taille préférentielle
            labels[i].setPreferredSize(new Dimension(170,48));
            // On centre le texte verticalement dans la zone de texte
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            // On modifie la couleur de fond
            labels[i].setBackground(new Color(255,255,255));
            // On ajoute une bordure (de même couleur que le fond pour créer un effet de marges internes)
            labels[i].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        }

        return labels;

    }

    /**
     * Méthode qui permet de créer les champs de textes pour les touches utilisées pour boire les potions
     * @return le tableau contenant les cinq champs de textes correctement instanciés
     */
    private JTextField[] createBoirePotionsFields(){

        // Création d'un nouveau tableau de cinq champs de textes
        JTextField[] fields = new JTextField[5];

        // Récupération des codes associés aux touches utilisées pour boire les potions
        int[] codes = fenetre.getCommandesBoirePotions();
        
        // Pour chacun des cinq champs de textes
        for(int i=0; i<fields.length; i++){

            // Création d'un nouveau champ de texte vide
            fields[i] = new JTextField();
            // On modifie sa largeur à 12 caractères
            fields[i].setColumns(12);
            // On centre le texte horizontalement dans la zone de texte
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            // On transforme le code correspondant à la touche du champ de texte en description sous la forme de chaîne de caractères
            String txt = stringOfCode(codes[i]);

            // Si la chaîne obtenue est de taille 0, la chaîne devient le code sous la forme d'un caractère
            if(txt.length() == 0) txt = ""+(char) codes[i];

            // On ajoute le String obtenu dans le champ de texte concerné
            fields[i].setText(txt);
        }

        // Ajout d'un écouteur pour le premier champ de texte sur les touches relachées
        fields[0].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar(); 
                }

                // On ajoute le String obtenu dans le champ de texte concerné
                fields[0].setText(res);
            }
        });

        // Ajout d'un écouteur pour le deuxième champ de texte sur les touches relachées
        fields[1].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte concerné
                fields[1].setText(res);
            }
        });

        // Ajout d'un écouteur pour le troisième champ de texte sur les touches relachées
        fields[2].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte concerné
                fields[2].setText(res);
            }
        });

        // Ajout d'un écouteur pour le quatrième champ de texte sur les touches relachées
        fields[3].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte concerné
                fields[3].setText(res);
            }
        });

        // Ajout d'un écouteur pour le cinquième champ de texte sur les touches relachées
        fields[4].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte concerné 
                fields[4].setText(res);
            }
        });

        return fields;

    }

    /**
     * Méthode qui permet de créer les textes servant d'étiquettes pour les touches utilisées pour déclencher les compétences
     * @return un tableau de quatre textes correctement instanciés et représentant les étiquettes voulues 
     */
    private JLabel[] createCompetencesLabels(){

        // Création d'un tableau de quatre textes 
        JLabel[] labels = new JLabel[4];

        // Initialisation du texte pour la touche de la première compétence du joueur
        labels[0] = new JLabel("  Utiliser compétence 1:");
        // Initialisation du texte pour la touche de la deuxième compétence du joueur
        labels[1] = new JLabel("  Utiliser compétence 2:");
        // Initialisation du texte pour la touche de la troisième compétence du joueur
        labels[2] = new JLabel("  Utiliser compétence 3:");
        // Initialisation du texte pour la touche de la quatrième compétence du joueur
        labels[3] = new JLabel("  Utiliser compétence 4:");

        // Pour chacun des quatre textes 
        for(int i=0; i<labels.length; i++){

            // On rend visible le fond
            labels[i].setOpaque(true);
            // On ajoute son icon
            labels[i].setIcon(imgCompetence);
            // On défini une taille préférentielle
            labels[i].setPreferredSize(new Dimension(170,48));
            // On centre verticalement le texte dans la zone de texte
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            // On modifie la couleur de fond
            labels[i].setBackground(new Color(255,255,255));
            // On ajoute une bordure (de même couleur que le fond pour créer un effet de marges internes)
            labels[i].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        }

        return labels;
    }

    /**
     * Méthode qui permet de créer les champs de textes pour les touches utilisées pour déclencher une compétence
     * @return un tableau contenant les quatre champs de textes correctement instanciés
     */
    private JTextField[] createCompetencesFields(){

        // Création d'un nouveau tableau de quatre champs de textes
        JTextField[] fields = new JTextField[4];

        // Récupération des codes associés aux touches utilisées pour délencher une compétence        
        int[] codes = fenetre.getCommandesCompetences();
        
        // Pour chacun des quatre champs de textes 
        for(int i=0; i<fields.length; i++){

            // Création d'un nouveau champ de texte vide
            fields[i] = new JTextField();
            // Définition de sa largeur à 12 caractères
            fields[i].setColumns(12);
            // On centre le texte horizontalement dans la zone de texte
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);

            // On transforme le code correspondant à la touche du champ de texte en description sous la forme de chaîne de caractères
            String txt = stringOfCode(codes[i]);

            // Si la chaîne obtenue est de taille 0, la chaîne devient le code sous la forme d'un caractère
            if(txt.length() == 0) txt = ""+(char) codes[i];

            // On ajoute le String obtenu dans le champ de texte concerné
            fields[i].setText(txt);
        }

        // Ajout d'un écouteur pour le premier champ de textesur les touches relachées
        fields[0].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[0].setText(res);
            }
        });

        // Ajout d'un écouteur pour le deuxieme champ de texte sur les touches relachées
        fields[1].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[1].setText(res);
            }

        });

        // Ajout d'un écouteur pour le troisième champ de texte sur les touches relachées
        fields[2].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte
                fields[2].setText(res);
            }
        });

        // Ajout d'un écouteur pour le quatrième champ de texte sur les touches relachées
        fields[3].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c); // Récupère la description sous la forme de String de ce caractère

                // Si la description est vide
                if(res.equals("")){
                    // La description devient le code du caractère qui déclenché l'événement sous la forme de caractère
                    res = ""+e.getKeyChar();
                }

                // On ajoute le String obtenu dans le champ de texte concerné
                fields[3].setText(res);
            }
        });

        return fields;
    }


    /**
     * Méthode qui permet de créer le bouton pour reset les touches par défaut
     * @param panel le panel contenu dans le centre du panel de la fenêtre
     * @param gc l'objet représentant les contraintes associés en cours
     * @return l'objet JButton crée et correctement instancié
     */
    private JButton createBoutonReset(JPanel panel, GridBagConstraints gc){
        
        // Création d'un nouveau bouton avec le texte "Reset"
        JButton bouton = new JButton("Reset");
        
        bouton.setBackground(new Color(234,161,19)); // Change sa couleur de fond
        bouton.setForeground(new Color(24,34,95)); // Change sa couleur de texte

        // Définition d'une variable pour pouvoir y accéder dans la méthode actionPerformed
        FenetreCommandes fc = this; 

        // Ajout d'un écouteur sur le déclenchement du bouton
        bouton.addActionListener(new ActionListener(){
            /**
             * Méthode qui sera appelé lorsque l'on clique sur le bouton
             * @param e objet représentant l'événement qui vient de se déclencher
             */
            @Override
            public void actionPerformed(ActionEvent e){
                
                // On réinitialise les commandes (comme au départ)
                fenetre.initialiserCommandes();

                // On lit les codes des touches de déplacements
                int[] cmds = fenetre.getCommandesDeplacements();

                // On les ajoute dans les champs de textes concernés
                deplacementsFields[0].setText(stringOfCode(cmds[0]));
                deplacementsFields[1].setText(stringOfCode(cmds[1]));
                deplacementsFields[2].setText(stringOfCode(cmds[2]));
                deplacementsFields[3].setText(stringOfCode(cmds[3]));

                // On lit le code de la touche pour attaquer et on l'ajoute dans le champ de texte concerné
                attaquerField.setText(stringOfCode(fenetre.getCommandeAttaquer()));

                // On lit le code de la touche pour ramasser et on l'ajoute dans le champ de texte concerné
                ramasserField.setText(""+(char)fenetre.getCommandeRamasser());

                // On lit le code de la touche pour ouvrir et on l'ajoute dans le champ de texte concerné
                ouvrirField.setText(""+(char)fenetre.getCommandeOuvrir());

                // On lit les codes des touches utillisées pour boire les potions
                cmds = fenetre.getCommandesBoirePotions();

                //On les ajoute dans les champs de textes concernés
                boirePotionsFields[0].setText(stringOfCode(cmds[0]));
                boirePotionsFields[1].setText(stringOfCode(cmds[1]));
                boirePotionsFields[2].setText(stringOfCode(cmds[2]));
                boirePotionsFields[3].setText(stringOfCode(cmds[3]));
                boirePotionsFields[4].setText(stringOfCode(cmds[4]));

                // On lit les codes des touches utilisées pour déclencher les compétences
                cmds = fenetre.getCommandesCompetences();

                // On les ajoute dans les champs de textes concernés
                competencesFields[0].setText(""+(char)cmds[0]);
                competencesFields[1].setText(""+(char)cmds[1]);
                competencesFields[2].setText(""+(char)cmds[2]);
                competencesFields[3].setText(""+(char)cmds[3]);

                // On crée un nouveau texte et on l'initialise
                JLabel label = new JLabel("Les changements ont bien été remit à zéro.");
                // On rend le fond visible
                label.setOpaque(true);
                // On passe sa couleur de fond en vert
                label.setBackground(new Color(90,209,26));
                // On passe la couleur du texte en blanc
                label.setForeground(new Color(255,255,255));
                // On centre le texte horizontalement dans la zone de texte
                label.setHorizontalAlignment(SwingConstants.CENTER);
                // On ajoute une bordure (de même couleur que le fond pour créer un effet de marges internes)
                label.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(90,209,26)));
                
                // On définit les contraintes associés au texte que l'on vient de créer
                gc.gridy = 17; // Sa position y dans le tableau (ordonnées)
                gc.gridwidth = 2; // Il doit prendre la taille de 2 cases en largeur
                gc.gridx = 0; // On revient à la première colonne
                
                // On ajout le texte créé avec ses contraintes associées dans le panel GridBagLayout
                panel.add(label, gc);

                // On remet à jour l'affichage de la fenêtre des touches
                fc.validate();
                    
            }

        });

        return bouton;
    }


    /**
     * Méthode qui permet de créer le bouton pour valider le changement des touches
     * @param panel le panel contenu dans le centre du panel de la fenêtre
     * @param gc l'objet représentant les contraintes associées en cours
     * @return l'objet JButton crée et correctement instancié
     */
    private JButton createBoutonValider(JPanel panel, GridBagConstraints gc){

        // Création d'un nouveau bouton avec le texte "Sauvegarder"
        JButton bouton = new JButton("Sauvegarder");
        bouton.setBackground(new Color(24,34,95)); // Change sa couleur de fond
        bouton.setForeground(new Color(234,161,19)); // Change sa couleur de texte
        
        // Définition d'une variable pour pouvoir y accéder dans la méthode actionPerformed
        FenetreCommandes fc = this; 

        // Ajout d'un écouteur sur le déclenchement du bouton
        bouton.addActionListener(new ActionListener(){
            /**
             * Méthode qui sera appelé lorsque l'on clique sur le bouton
             * @param e objet représentant l'événement qui vient de se déclencher
             */
            @Override
            public void actionPerformed(ActionEvent e){

                // Si tous les champs de textes contiennent bien une touche
                if(deplacementsFields[0].getText().length() > 0
                && deplacementsFields[1].getText().length() > 0
                && deplacementsFields[2].getText().length() > 0
                && deplacementsFields[3].getText().length() > 0
                && attaquerField.getText().length() > 0
                && ouvrirField.getText().length() > 0
                && ramasserField.getText().length() > 0
                && boirePotionsFields[0].getText().length() > 0
                && boirePotionsFields[1].getText().length() > 0
                && boirePotionsFields[2].getText().length() > 0
                && boirePotionsFields[3].getText().length() > 0
                && boirePotionsFields[4].getText().length() > 0
                && competencesFields[0].getText().length() > 0
                && competencesFields[1].getText().length() > 0
                && competencesFields[2].getText().length() > 0
                && competencesFields[3].getText().length() > 0){

                    // On modifie le code des touches des déplacements
                    fenetre.setCmdDeplacementHaut(codeOfString(deplacementsFields[0].getText()));
                    fenetre.setCmdDeplacementBas(codeOfString(deplacementsFields[1].getText()));
                    fenetre.setCmdDeplacementGauche(codeOfString(deplacementsFields[2].getText()));
                    fenetre.setCmdDeplacementDroite(codeOfString(deplacementsFields[3].getText()));

                    // On modifie le code la touche pour attaquer
                    fenetre.setCmdAttaquer(codeOfString(attaquerField.getText()));
                    // On modifie le code de la touche pour ouvrir un coffre
                    fenetre.setCmdOuvrir(codeOfString(ouvrirField.getText()));
                    // On modifie le code de la touche pour ramasser un trésor
                    fenetre.setCmdRamasser(codeOfString(ramasserField.getText()));

                    // On modifie le code des touches pour boire les potions
                    fenetre.setCmdBoirePotions(codeOfString(boirePotionsFields[0].getText()),
                    codeOfString(boirePotionsFields[1].getText()),
                    codeOfString(boirePotionsFields[2].getText()),
                    codeOfString(boirePotionsFields[3].getText()),
                    codeOfString(boirePotionsFields[4].getText()));

                    // On modifie le code des touches pour déclencher les compétences
                    fenetre.setCmdCompetences(codeOfString(competencesFields[0].getText()),
                    codeOfString(competencesFields[1].getText()),
                    codeOfString(competencesFields[2].getText()),
                    codeOfString(competencesFields[3].getText()));

                    // Création d'un nouveau texte pour la notification
                    JLabel label = new JLabel("Les changements ont bien été enregistrés.");
                    // On rend le fond du texte visible
                    label.setOpaque(true);
                    // On change la couleur de fond
                    label.setBackground(new Color(90,209,26));
                    // On change la couleur du texte
                    label.setForeground(new Color(255,255,255));
                    // On centre horizontalement le texte dans la zone de texte
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    // On ajoute une bordure (de la même couleur que le fond pour créer un effet de marges internes)
                    label.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(90,209,26)));
                    
                     // On définit les contraintes associés au texte que l'on vient de créer
                    gc.gridy = 17; // Sa position y dans le tableau (ordonnées)
                    gc.gridx = 0; // On revient à la première colonne
                    gc.gridwidth = 2; // Il doit prendre la taille de 2 cases en largeur

                    // On ajout le texte créé avec ses contraintes associées dans le panel GridBagLayout
                    panel.add(label, gc);

                    // On remet à jour l'affichage de la fenêtre des touches
                    fc.validate();
                    
                }
            }
        });

        return bouton;
    }

    /**
     * Méthode qui permet de transformer une description sous forme de String d'une touche en un code entier de caractère 
     * @param s ma description de la touche dont on souhaite obtenir le code
     * @return le code entier correspondant à la description de la touche
     */
    private int codeOfString(String s){
       
        // Déclaration et initalisation de la variable de retour
        int res = -1;
    
        // Si la description est "FLECHE HAUT"
        if(s.equals("FLECHE HAUT")) { 
            res = KeyEvent.VK_UP;
        }
    
        // Si la description est "FLECHE BAS"
        if(s.equals("FLECHE BAS")) { 
            res = KeyEvent.VK_DOWN;
        }
        
        // Si la description est "FLECHE GAUCHE"
        if(s.equals("FLECHE GAUCHE")) { 
            res = KeyEvent.VK_LEFT;
        }
    
        // Si la description est "FLECHE DROITE"
        if(s.equals("FLECHE DROITE")) { 
            res = KeyEvent.VK_RIGHT;
        }
    
        // Si la description est "ESPACE"
        if(s.equals("ESPACE")){
            res = KeyEvent.VK_SPACE;
        }
    
        // Si la description est "0 NUMPAD"
        if(s.equals("0 NUMPAD")){
            res = KeyEvent.VK_NUMPAD0;
        }
    
        // Si la description est "1 NUMPAD"
        if(s.equals("1 NUMPAD")){
            res = KeyEvent.VK_NUMPAD1;
        }
    
        // Si la description est "2 NUMPAD"
        if(s.equals("2 NUMPAD")){
            res = KeyEvent.VK_NUMPAD2;
        }
    
        // Si la description est "3 NUMPAD"
        if(s.equals("3 NUMPAD")){
            res = KeyEvent.VK_NUMPAD3;
        }
    
        // Si la description est "4 NUMPAD"
        if(s.equals("4 NUMPAD")){
            res = KeyEvent.VK_NUMPAD4;
        }
    
        // Si la description est "5 NUMPAD"
        if(s.equals("5 NUMPAD")){
            res = KeyEvent.VK_NUMPAD5;
        }
    
        // Si la description est "6 NUMPAD"
        if(s.equals("6 NUMPAD")){
            res = KeyEvent.VK_NUMPAD6;
        }
    
        // Si la description est "7 NUMPAD"
        if(s.equals("7 NUMPAD")){
            res = KeyEvent.VK_NUMPAD7;
        }
        
        // Si la description est "8 NUMPAD"
        if(s.equals("8 NUMPAD")){
            res = KeyEvent.VK_NUMPAD8;
        }
    
        // Si la description est "9 NUMPAD"
        if(s.equals("9 NUMPAD")){
            res = KeyEvent.VK_NUMPAD9;
        }
    
        // Si la description est "Ctrl"
        if(s.equals("Ctrl")){
            res = KeyEvent.VK_CONTROL;
        }
    
        // Si la description est "Shift"
        if(s.equals("Shift")){
            res = KeyEvent.VK_SHIFT;
        }
    
        // Si la description est "Tabulation"
        if(s.equals("Tabulation")){
            res = KeyEvent.VK_TAB;
        }
    
        // Si la description est "Alt"
        if(s.equals("Alt")){
            res = KeyEvent.VK_ALT;
        }
    
        // Si la description est "AltGr"
        if(s.equals("AltGr")){
            res = KeyEvent.VK_ALT_GRAPH;
        }
        
        // Si la description est "Entrer"
        if(s.equals("Entrer")){
            res = KeyEvent.VK_ENTER;
        }
    
        // Si la description est "Cancel"
        if(s.equals("Cancel")){
            res = KeyEvent.VK_BACK_SPACE;
        }
    
        // Si la description est "Verr.Maj"
        if(s.equals("Verr. Maj")){
            res = KeyEvent.VK_CAPS_LOCK;
        }
    
        // Si la description est "F1"
        if(s.equals("F1")){
            res = KeyEvent.VK_F1;
        }
        
        // Si la description est "F2"
        if(s.equals("F2")){
            res = KeyEvent.VK_F2;
        }
    
        // Si la description est "F3"
        if(s.equals("F3")){
            res = KeyEvent.VK_F3;
        }
    
        // Si la description est "F4"
        if(s.equals("F4")){
            res = KeyEvent.VK_F4;
        }
    
        // Si la description est "F5"
        if(s.equals("F5")){
            res = KeyEvent.VK_F5;
        }
    
        // Si la description est "F6"
        if(s.equals("F6")){
            res = KeyEvent.VK_F6;
        }
    
        // Si la description est "F7"
        if(s.equals("F7")){
            res = KeyEvent.VK_F7;
        }
    
        // Si la description est "F8"
        if(s.equals("F8")){
            res = KeyEvent.VK_F8;
        }
    
        // Si la description est "F9"
        if(s.equals("F9")){
            res = KeyEvent.VK_F9;
        }
    
        // Si la description est "F10"
        if(s.equals("F10")){
            res = KeyEvent.VK_F10;
        }
    
        // Si la description est "F11"
        if(s.equals("F11")){
            res = KeyEvent.VK_F11;
        }
    
        // Si la description est "F12"
        if(s.equals("F12")){
            res = KeyEvent.VK_F12;
        }
    
        // Si la description est "Echap"
        if(s.equals("Echap")){
            res = KeyEvent.VK_ESCAPE;
        }

        // Si la description n'est pas égale à l'une de toutes celles testées
        if(res == -1){
            // On récupère le code du caractère de la description
            res = KeyEvent.getExtendedKeyCodeForChar(s.charAt(0));
            
        }
    
        return res;
        
    }

}
