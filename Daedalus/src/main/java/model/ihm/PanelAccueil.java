package model.ihm;

import model.objets.Jeu;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Classe PanelAccueil représentant le panel qui est affiché dans la fenêtre lorsque l'on démarre le projet
 */
public class PanelAccueil extends JPanel {
    
    private FenetreGraphique fenetre; // La fenetre graphique dans laquelle sera le panel
    private JPanel centerPanel; // Le panel de formulaire au centre de la stratégie de positionnement BorderLayout
    private JLabel topLabel; // Le texte en haut de la startégie de positionnement BorderLayout
    private JLabel bottomLabel; // Le texte en bas de la stratégie de positionnement BorderLayout
    private JLabel nbNiveauLabel; // L'étiquette pour le champ nombre max de niveaux
    private JTextField nbNiveauField; // Le champ pour saisir le nombre max de niveaux 
    private JCheckBox checkBox; // La case à cochée pour sélectionner le mode "répertoire de fichiers labyrinthe"
    private JLabel fichierLabel; // L'étiquette comportant le lien du dossier selectionné
    private JButton fichierButton; // Le button permettant de selectionner un dossier
    private JButton startButton; // Le button permettant de lancer la partie
    private JButton lireSaveButton; // Le button permettant de lire un fichier de sauvegarde


    /**
     * Constructeur par défaut de la classe PanelAccueil
     * @param f l'objet FenetreGraphique dans lequel sera inclus le nouveau panel
     */
    public PanelAccueil(FenetreGraphique f){

        // Appel du constructeur de JPanel + set stratégie positionnement = BorderLayout
        super(new BorderLayout()); 

        // Changement de la couleur de fond du panel
        this.setBackground(new Color(162,158,150));

        // stockage du paramètre f dans l'attribut fenetre
        fenetre = f;

        // Création et initialisation du text en haut de la stratégie de positionnement BorderLayout
        topLabel = createtopLabel();
        this.add(topLabel, BorderLayout.NORTH); // Ajout du text dans le haut du panel d'accueil

        // Création et initialisation du text en bas de la stratégie de positionnement BorderLayout
        bottomLabel = createTextBottom();
        this.add(bottomLabel, BorderLayout.SOUTH); // Ajout du text dans le haut du panel d'accueil

        // Création et initialisation du panel de formulaire au centre de la stratégie de positionnement BorderLayout
        centerPanel = createCenterPanel();
        this.add(centerPanel, BorderLayout.CENTER); // Ajout du nouveau panel dans le centre du panel d'accueil
    }

    /**
     * Méthode qui crée et initialise le panel de formulaire au centre de la statégie de positionnement BorderLayout
     * @return objet JPanel représentant le panel
     */
    private JPanel createCenterPanel(){

        // Création du panel
        JPanel panel = new JPanel(new GridBagLayout()); // Ajout de la statégie de positionnement GridBagLayout
        panel.setBackground(new Color(33,32,30)); // ajout d'une couleur de fond
        
        // Création d'un nouvel objet de contraintes pour la stratégie GridBagLayout
        GridBagConstraints gc = new GridBagConstraints(); 


        // Initialisation des contraintes pour l'étiquette du nombre de niveaux
        gc.insets = new Insets(12,12,12,12); // Les marges entres chaques cases
        gc.gridx = 1; // Sa position x dans le tableau (abscisses)
        gc.gridy = 1; // Sa position y dans le tableau (ordonnées)
        gc.fill = GridBagConstraints.HORIZONTAL; // N'essaye pas de remplire la case
        gc.anchor = GridBagConstraints.CENTER; // Ancre au milieu à droite
        gc.gridwidth = 2; // Le composant prend 2 cases en abscisses

        // Création et initialisation de l'attribut nbNiveauLabel
        nbNiveauLabel = createNbNiveauLabel();
        panel.add(nbNiveauLabel, gc); // Ajout de l'attribut dans le panel avec les contraintes

        // Initialisation des contraintes pour le champ du nombre de niveaux
        gc.gridx = 3; // Sa position x dans le tableau (abscisses)
        gc.anchor = GridBagConstraints.LINE_START; // Ancre au milieu à gauche
        gc.fill = GridBagConstraints.NONE; // N'essaye pas de remplire la case
        gc.gridwidth = 1; // Le composant prend 1 cases en abscisses

        // Création et initialisation de l'attribut nbNiveauField
        nbNiveauField = createNbNiveau();
        panel.add(nbNiveauField, gc); // Ajout de l'attribut dans le panel avec les contraintes

        // Initialisation des contraintes pour la checBox des fichiers
        gc.gridy = 2; // Sa position y dans le tableau (ordonnées)
        gc.gridx = 0; // Sa position x dans le tableau (abcisses)
        gc.anchor = GridBagConstraints.CENTER; // Ancre au centre de la case
        gc.fill = GridBagConstraints.NONE; // N'essaye pas de remplire la case

        // Création et initialisation de l'attribut checkBox
        checkBox = createCheckBox();
        panel.add(checkBox, gc); // Ajout de l'attribut dans le panel avec les contraintes

        // Initialisation des contraintes pour le texte du chemin du dossier
        gc.gridx = 1; // Sa position x dans le tableau (abscisses)
        gc.anchor = GridBagConstraints.CENTER; // Ancre au centre de la case
        gc.fill = GridBagConstraints.HORIZONTAL; // Le contenu de la case doit occuper toute la case horizontalement
        gc.gridwidth = 2; // Le composant prend 2 cases en abscisses

        // Création et initialisation de l'attribut fichierLabel
        fichierLabel = createLabelFichier();
        panel.add(fichierLabel, gc); // Ajout de l'attribut dans le panel avec les contraintes

        // Initialisation des contraintes pour le button de sélection de dossier
        gc.gridx=3;// Sa position x dans le tableau (abscisses)
        gc.anchor = GridBagConstraints.LINE_START; // Ancre au milieu à gauche
        gc.fill = GridBagConstraints.HORIZONTAL; // Le contenu de la case doit occuper toute la case horizontalement 
        gc.gridwidth = 1; // Le composant prend 1 cases en abscisses

        // Création et initialisation de l'attribut fichierButton
        fichierButton = createButtonFichier();
        panel.add(fichierButton, gc); // Ajout de l'attribut dans le panel avec les contraintes
        
        // Initialisation des contraintes pour le button de lancement de partie
        gc.gridx = 0; // Sa position x dans le tableau (abscisses)
        gc.gridy = 3; // Sa position y dans le tableau (ordonnées)
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.CENTER; // Ancre au centre de la case
        gc.fill = GridBagConstraints.HORIZONTAL; // Le contenu de la case doit occuper toute la case horizontalement
        gc.gridwidth = 2; // Le composant prend 2 cases en abscisses


        // Création et initialisation de l'attribut startButton
        lireSaveButton = createLireSaveButton();
        panel.add(lireSaveButton, gc); // Ajout de l'attribut dans le panel avec les contraintes


        // Initialisation des contraintes pour le button de lancement de partie
        gc.gridx = 2; // Sa position x dans le tableau (abscisses)

         // Création et initialisation de l'attribut startButton
         startButton = createstartButton();
         panel.add(startButton, gc); // Ajout de l'attribut dans le panel avec les contraintes        

        return panel;

    }
    

    /**
     * Méthode qui crée et initialise le text en haut de la statégie BorderLayout du pannel d'acceuil
     * @return objet JLabel représant le text en question
     */
    private JLabel createtopLabel(){

        JLabel label = new JLabel(); // Création d'un nouveau JLabel
        label.setText("<html><br><i><b>DAEDALUS</b></i><br><br></html>");// Ajout du texte de bienvenue
        label.setIcon(new ImageIcon(getClass().getResource("/assets/laby.png"))); // Ajout de l'icône prévue au texte
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centrer le texte horizontalement dans le JLabel
        label.setForeground(new Color(0,0,0)); // Changement de la couleur d'écriture
        // Ajout d'une bordure de 10 pixels avec la même couleur de fond (pour créer des marges internes)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(162,158,150)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise l'étiquette du champ du nombre de niveaux
     * @return objet JLabel représentant l'étiquette
     */
    private JLabel createNbNiveauLabel(){

        JLabel label = new JLabel("  Choissiez un nombre de niveau:  "); // Création d'un nouveau JLabel avec le texte
        
        //Ajout d'une bordure blanche (pour créer une marge intérieure)
        label.setBorder(BorderFactory.createMatteBorder(7,  10,  7,  10, new Color(255,255,255)));

        label.setIcon(new ImageIcon(getClass().getResource("/assets/niveaux.png"))); // Ajout de l'icône prévue à l'étiquette
        label.setOpaque(true); // Rend le JLabel opaque
        label.setBackground(new Color(255,255,255)); // Change la couleur de fond du JLabel en blanc

        return label;
    }

    /**
     * Méthode qui crée et initialise le champ de text du nombre de niveau
     * @return objet JTextField représentant le champ de texte éditable
     */
    private JTextField createNbNiveau(){

        JTextField textField = new JTextField(); // Création d'un nouveau champs éditable
        textField.setColumns(2); // Définir sa largeur à 3 caractères

        // Ajout d'un écouteur sur les touches relachées
        textField.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                char c = e.getKeyChar(); // Recupère le caractère qui a déclenché l'évenement
                if(c >= 48 && c >= 57) { // Si il ne se situe pas ente 1 et 9 inclus (code ASCII)
                    // Remplacement de toutes les occurences du nouveau caractère par la chaîne vide
                    textField.setText(textField.getText().replaceAll(""+c, ""));
                }
            }
        });

        return textField;
    }

    /**
     * Méthode qui crée et initialise la case à cocher de la selection de dossier
     * @return objet JCheckBox représentant la case à cocher
     */
    private JCheckBox createCheckBox(){

        // Création d'une nouvelle case a cocher
        JCheckBox checkBox = new JCheckBox();
        
        return checkBox;
    }

    /**
     * Méthode qui crée et initialise le texte comportant le chemin dans la selction de dossier 
     * @return objet JLabel représentant le chemin sous la forme d'un texte
     */
    private JLabel createLabelFichier(){

        JLabel label = new JLabel("  Chossisez un fichier.."); // Création d'un nouveau JLabel avec le texte
        // Ajout d'une bordure blanche (pour créer une marge intérieure)
        label.setBorder(BorderFactory.createMatteBorder(7,  10,  7,  10, new Color(255,255,255)));

        label.setIcon(new ImageIcon(getClass().getResource("/assets/dossier.png"))); // Ajout de l'icône prévue au texte 
        label.setOpaque(true); // Défini le texte comme opaque
        label.setBackground(new Color(255,255,255)); // Modifie la couleur de fond du texte en blanc

        label.addMouseListener(new MouseAdapter() {
        
            @Override
            public void mouseClicked(MouseEvent e){
                if(!checkBox.isSelected()){
                    checkBox.setSelected(true);
                }
            }
            
        });

        return label;
    }

    /** 
     * Méthode qui crée et initialise le bouton de la selction de dossier
     * @return objet JButton qui représente le bouton ".." (convention pour ouverture selecteur de fichiers)
     */
    private JButton createButtonFichier(){
        
        // Création d'un nouveau bouton avec le texte ".."
        JButton button = new JButton("..");
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                if(!checkBox.isSelected()){
                    checkBox.setSelected(true);
                }
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Choissiez le dossier contenant vos fichiers de labyrinthe: ");
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnValue = jfc.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (jfc.getSelectedFile().isDirectory()) {
                       fichierLabel.setText(jfc.getSelectedFile().toString());
                    }
                }
            }
            
            
        });
        return button;
    }

    /**
     * Méthode qui crée et initialise le bouton pour le lancement de la partie
     * @return objet JButton qui représente le bouton "Démarrer la partie"
     */
    private JButton createstartButton(){

        JButton button = new JButton("Démarrer la partie"); // Création d'un nouveau bouton avec le texte
        button.setBackground(new Color(24,34,95)); // Change sa couleur de fond
        button.setForeground(new Color(234,161,19)); // Change sa couleur de texte
        
        // Ajout d'un écouter sur le button
        button.addActionListener(new ActionListener(){
            /**
             * Méthode appeler lorsque l'utilisateur clique sur le bouton
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void actionPerformed(ActionEvent e){
                // Si j'ai bien un nombre de niveau renseigné dans le champ prévu et qu'il est > 0
                if(nbNiveauField.getText().length() != 0 && Integer.valueOf(nbNiveauField.getText()) > 0){ 

                    if(checkBox.isSelected()){
                        if(!fichierLabel.getText().equals("") &&  !fichierLabel.getText().equals("Choissisez un fichier..")){
                            fenetre.afficherChoixCompetances(Integer.valueOf(nbNiveauField.getText()), fichierLabel.getText());
                        }
                    } else {
                       
                        fenetre.afficherChoixCompetances(Integer.valueOf(nbNiveauField.getText()), null);
                    }
                } else {
                    JLabel label = new JLabel("Vous devez entrer au minimum un nombre de niveau.");
                    label.setOpaque(true);
                    label.setForeground(new Color(255,255,255));
                    label.setBackground(new Color(209,43,26));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setPreferredSize(new Dimension(20,35));

                    GridBagConstraints gc = new GridBagConstraints();
                    gc.gridx=0;
                    gc.gridy=0;
                    gc.gridwidth=4;
                    gc.insets = new Insets(0,0,10,0);
                    gc.fill = GridBagConstraints.BOTH;
                    gc.anchor = GridBagConstraints.CENTER;  

                    centerPanel.add(label, gc);
                    fenetre.validate();
                }
            }

        });
        
        return button;
    }

    private JButton createLireSaveButton(){
        
        JButton button = new JButton("Lire une sauvegarde"); // Création d'un nouveau bouton avec le texte
        button.setBackground(new Color(234,161,19)); // Change sa couleur de fond
        button.setForeground(new Color(24,34,95)); // Change sa couleur de texte
        button.setPreferredSize(new Dimension(260,30));


        // Ajout d'un écouter sur le button
        button.addActionListener(new ActionListener(){
            /**
             * Méthode appeler lorsque l'utilisateur clique sur le bouton
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void actionPerformed(ActionEvent e){
                
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Choissiez le fichier de sauvegarde:");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
                jfc.addChoosableFileFilter(filter);

                int returnValue = jfc.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    if(jfc.getSelectedFile().exists()){
                   
                        // TO DO: LIRE UNE SAVE

                        // objet File = jfc.getSelectedFile();

                        // + fenetre.afficherPartie(jeuLu)
                        // (Créer une méthode afficherPartie à partir d'un jeu => idem l'autre mais jeu pas à créer car en params)
                        try {
                            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(jfc.getSelectedFile())) ;
                            Jeu jeuLu = (Jeu) ois.readObject() ;
                            fenetre.afficherPartie(jeuLu);
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }

        });
        
        return button;
    }

    /**
     * Méthode qui crée et initialise le text en bas de la statégie BorderLayout du pannel d'acceuil
     * @return objet JLabel représant le text en question
     */
    private JLabel createTextBottom(){
        
        JLabel label = new JLabel(); // Création d'un nouveau JLabel
        // Ajout du texte (A MODFIEIR)
        label.setText("<html><br>COPYRIGHT Daedalus 2022 - Projet Université de Lorraine<br><br></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centrer le texte horizontalement dans le JLabel
        label.setForeground(new Color(0,0,0)); // Change la couleur de fond du JLabel en noir
        
        return label;
    }

}
