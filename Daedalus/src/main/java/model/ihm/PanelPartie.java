package model.ihm;

import java.util.ArrayList;

import model.cases.Case;
import model.cases.CaseMur;
import model.cases.CaseTresor;
import model.cases.CaseEffet;
import model.enums.Direction;
import model.objets.Entite;
import model.objets.Joueur;
import model.objets.Gobelin;
import model.objets.Potion;
import model.objets.Arme;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;


/**
 * Classe PanelPartie représentant le panel qui sera affiché dans la fenêtre pendant une partie
 */
public class PanelPartie extends JPanel{

    private FenetreGraphique fenetre; // La fenetre dans laquelle l'objet sera affiché
    private JPanel labyPanel; // Le panel qui contient le labyrinthe
    private JPanel hudPanel; // Le panel qui détermine le hud
    private JPanel infosPanel; // Le panel qui symbolise les informations
    private JLabel nameJeuLabel; // Le texte du logo du projet
    private ArrayList<ArrayList<JLabel>> caseLabels; // Les icônes des cases du labyrinthes
    private JLabel hudLabel; // Le texte du titre HUD
    private JLabel vieLabel; // Le texte et l'icône des points de vie
    private JLabel armureLabel; // Le texte et l'icône des points d'armure
    private JLabel armeLabel; // Le texte et l'icône de l'arme du joueur
    private JLabel potionTitreLabel; // Le texte et l'icône du titre de la partie potions
    private JLabel[] potionLabels; // Les cinqs icônes de l'inventaire des potions
    private JLabel infosTitreLabel; // Le titre et l'îcone de titre des informations
    private JLabel[] infosLabels; // Les cinqs derniers textes des informations

    /**
     * Unique constructeur de la classe PanelPartie
     * @para f l'objet FenetreGraphique dans lequelle sera affiché le panel
     */
    public PanelPartie(FenetreGraphique f){

        // Appel au constructeur de JPanel et défini BorderLayout en stratégie de positionnement
        super(new BorderLayout());
        
        int i; // Pour la boucle for
        fenetre = f; // Sauvegarde le paramètre f dans l'attribut fenetre

        // Crée une nouvelle collection pour les icônes des cases
        caseLabels = new ArrayList<ArrayList<JLabel>>();

        // Récupération des cases du labyrinthe du jeu
        ArrayList<ArrayList<Case>> casesLaby = f.getJeu().getLabyrinthe().getCases(); 

        // Pour chaque ligne de la matrice des cases du labyrinthe
        for(i=0; i<casesLaby.size(); i++){
            // Création d'une nouvelle collection dans la collections des îcones de cases
            caseLabels.add(new ArrayList<JLabel>()); 
        }

        // Création et initialisation de l'attribut labyPanel correspondant au panel du labyrinthe
        labyPanel = createLabyPanel(casesLaby);
        this.add(labyPanel, BorderLayout.CENTER); // Ajout du panel de labyrinthe dans le panel de partie au centre

        // Création et initialisation de l'attribut hudPanel correspondant au panel pour le hud
        hudPanel = createHudPanel();
        this.add(hudPanel, BorderLayout.EAST); // Ajout du panel pour le hud dans le panel de partie à droite

        // CRéation et initialisation de l'attribut infosPanel correspondant au panel des informations
        infosPanel = createInfosPanel();
        this.add(infosPanel, BorderLayout.SOUTH); // Ajout du panel des informations dans le panel de partie en bas

    }

    /**
     * Méthode qui crée et initialise le panel du labyrinthe
     * @param cases objet ArrayList<ArrayList<Case>> représentant les cases du labyrinthe
     * @return objet JPanel représentant le labyrinthe
     */
    private JPanel createLabyPanel( ArrayList<ArrayList<Case>> cases){
        
        // Déclaration des variables nécessaires pour les boucles
        int i, j;

        // Création du nouveau panel et définition de stratégie de positionnement GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(45,78,95)); // Modifie la couleur de fond
        
        // Création d'un object GridBagConstraints pour la gestion des contraintes de chacuns des composants
        GridBagConstraints gc = new GridBagConstraints();

        // Initialisation des contraintes pour les icônes de cases
        gc.insets = new Insets(0,0,0,0); // Pas de marges entre les cases
        gc.ipady = 0; // Pas de marges en ordonnée
        gc.ipadx = 0; // Pas de marges en abscisse
        gc.weightx = 1; // Poids de la case dans la taille final en abscisse
        gc.weighty = 1; // Poids de la case dans la taille final en ordonnée
        gc.fill = GridBagConstraints.HORIZONTAL; // Le composant doit remplire la case horizontalement
        gc.anchor = GridBagConstraints.FIRST_LINE_START; // Ancre en haut à gauche de la case
        gc.gridx=0; // Sa position x dans le tableau (abscisses)
        gc.gridy=0; // Sa position y dans le tableau (ordonnées)


        // Boucles sur les lignes des cases du labyrinthe
        for(i=0; i<cases.size(); i++){
            // Boucles sur les colonnes des cases du labyrinthe
            for(j=0; j<cases.get(i).size(); j++){

                JLabel tmp;
                // Si la case est un mur
                if(cases.get(i).get(j) instanceof CaseMur){
                    // Création d'une nouvelle icône mur
                    tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/mur.png")));
                } else if(cases.get(i).get(j) instanceof CaseTresor){ //Si la case est un trésor

                    // Si le trésor est fermé
                    if(((CaseTresor)cases.get(i).get(j)).getOuvert() == false){
                        // Création d'une nouvelle îcone trésor
                        tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/tresor.png")));
                    } else { // Si le trésor est ouvert

                        // Si le trésor est une potion
                        if(((CaseTresor)cases.get(i).get(j)).getContenu() instanceof Potion){
                            // Création d'une nouvelle icône potion
                            tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/potion.png")));
                        } else if(((CaseTresor)cases.get(i).get(j)).getContenu() instanceof Arme){ // Si le trésor est une Arme
                            // Création d'une nouvelle icône arme 
                            tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/arme.png")));
                        } else { // Si le trésor est une pièce d'armure
                            // Création d'une nouvelle icône armure 
                            tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/armure.png")));
                        }

                    }
                
                } else if(cases.get(i).get(j) instanceof CaseEffet){ // Si la case est une case à effet

                    if(((CaseEffet) cases.get(i).get(j)).getAugmentation() > 0){ // Si elle effectue une augmentation 
                        // Création d'une nouvelle icône soin
                        tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/soin.png")));
                    } else { // Si elle effectue une diminution
                        // Création d'une nouvelle icône poison
                        tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/poison.png")));
                    }

                } else { // La case est une case vide
                    // Création d'une nouvelle icône sol
                    tmp = new JLabel(new ImageIcon(getClass().getResource("/assets/sol.png")));
                }

                caseLabels.get(i).add(tmp); // Ajout de la nouvelle icônes dans l'attribut de la collection des icônes
                panel.add(tmp, gc); // Ajout de la nouvelle icône dans le panel avec les contraintes souhaitées
                gc.gridx++; // On décale la prochaine icône d'une case vers la droite
            }

            gc.gridx = 0; // On se remet en debut de ligne
            gc.gridy++; // On descent à la colonne du dessous
        }

        // Boucle sur les entites du jeu
        for(i=0; i<fenetre.getJeu().getEntites().size(); i++){

            // Définition des variables nécessaires
            Entite e = fenetre.getJeu().getEntites().get(i);
            ImageIcon image;

            // Si l'entitée est le joueur
            if(e instanceof Joueur){

                // Si il regarde vers le haut
                if(((Joueur) e).getRegard() == Direction.HAUT){
                    // Création d'une nouvelle icône joueur vers le haut
                    image = new ImageIcon(getClass().getResource("/assets/joueur_haut.png")); 
                } else if (((Joueur) e).getRegard() == Direction.BAS){ // Si il regarde vers le bas
                    // Création d'un nouvelle icône joueur vers le bas
                    image = new ImageIcon(getClass().getResource("/assets/joueur_bas.png")); 
                } else if (((Joueur) e).getRegard() == Direction.GAUCHE){ // SI il regarde vers la gauche
                    // Création d'une nouvelle icône joueur vers la gauche
                    image = new ImageIcon(getClass().getResource("/assets/joueur_gauche.png")); 
                } else { // Si il regarde vers la droite
                    // Création d'une nouvelle icône joueur vers la droite
                    image = new ImageIcon(getClass().getResource("/assets/joueur_droite.png")); 
                }

            } else if(e instanceof Gobelin){ // Si l'entitée est un Gobelin
                // Création d'une nouvelle icône gobelin
                image = new ImageIcon(getClass().getResource("/assets/goblin.png"));
            } else{ // Si l'enittée est un fantome
                // Création d'une nouvelle icône fantome
                image = new ImageIcon(getClass().getResource("/assets/fantome.png"));
            }

            // Ajout de l'icône dans un champ de texte (pour pouvoir l'afficher)
            JLabel label = new JLabel(image);
            
            gc.gridx = e.getY(); // La position de l'icône en ordonnée doit être celle de l'entitée
            gc.gridy = e.getX(); // La position de l'icône en abscisse doit être celle de l'entitée

            // On retire l'icône qui a était mise dans cette case lors de la boucle précédente
            panel.remove(caseLabels.get(e.getX()).get(e.getY()));
            caseLabels.get(e.getX()).set(e.getY(), label); // modification de l'icône dans la collections des icônes de cases
            panel.add(label, gc); // On ajoute la nouvelle icône dans le panel avec les contraintes
        }

        return panel;
    }


    /**
     * Méthode qui crée et inittialise le panel pour le hud
     * @return objet JPanel représentant le hud
     */
    private JPanel createHudPanel(){

        // Création d'un nouveau panel avec la stratégie de positionnement GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(33,32,30)); // Modifie la couleur de fond
        

        // Création d'un object GridBagConstraints pour la gestion des contraintes de chacuns des composants
        GridBagConstraints gc = new GridBagConstraints();

        // Initialisation des contraintes pour les composants du hud
        gc.gridx = 0; // Sa position x dans le tabeleau (abscisses)
        gc.gridy = 0; // Sa position y dans le tableau (ordonnées)
        gc.fill = GridBagConstraints.BOTH; // Le composant essaye de remplir toute la case
        gc.anchor = GridBagConstraints.LINE_START; // Ancre au milieu à gauche
        gc.weighty = 1; // Poid de la case dans la taille final en ordonnées
        gc.weightx = 1; // Poid de la case dans la taille final en abscisses
        gc.gridwidth = 5; // Le composant doit être sur 5 case de large 


        // Création et initialisation de l'attribut nameJeuLabel pour le logo du projet
        nameJeuLabel = createNameJeuLabel();
        panel.add(nameJeuLabel, gc); // Ajout de l'attribut dans le panel pour le hud avec les contraintes

        gc.gridy++; // On se déplace d'une ligne vers le bas

        // Création et initialisation de l'attribut hudLabel pour le titre de la partie HUD
        hudLabel = createHudLabel();
        panel.add(hudLabel, gc); // Ajout de l'attribut dans le panel pour le hud avec les contraintes

        gc.gridy++; // On se déplace d'une ligne vers le bas

        // Création et initialisation de l'attribut vieLabel pour afficher les points de vie
        vieLabel = createVieLabel();
        panel.add(vieLabel, gc); // Ajout de l'attribut dans le panel pour le hud avec les contraintes

        gc.gridy++; // On se déplace d'une ligne vers le bas

        // Création et initialisation de l'attribut armureLabel pour afficher les points d'armures
        armureLabel = createArmureLabel();  
        panel.add(armureLabel, gc); // Ajout de l'attribut dans le panel pour le hud avec les contraintes

        gc.gridy++; // On se déplace d'une ligne vers le bas

        // Création et initialisation de l'attribut armeLabel pour afficher le nom de l'arme que le joueur possède
        armeLabel = createArmeLabel();
        panel.add(armeLabel, gc); // Ajout de l'attribut dans le panel pour le hud avec les contraintes

        gc.gridy++; // On se déplace d'une ligne vers le bas

        // Création et initialisation de l'attribut potionTitreLabel pour afficher le titre du tableau de potions
        potionTitreLabel = createPotionTitreLabel();
        panel.add(potionTitreLabel, gc); // Ajout de l'attribut dans le panel pour le hud avec les contraintes


        gc.gridy++; // On se déplace d'une ligne vers le bas
        // On veut que le composant occupe tout l'espave horizontal de la case
        gc.fill = GridBagConstraints.HORIZONTAL; 
        gc.gridwidth = 1; // L'élement prend l'espace horizontale d'une seule case

        /* 
            Création et initialisation de l'attribut potionsLabels pour le titre du tableau de potions
            + Ajout de l'attribut dans le panel pour le hud avec les contraintes
        */
        potionLabels = createPotionLabels(panel, gc); 

        return panel;
    }


    /**
     * Méthode qui crée et initialise le panel pour afficher les informations
     * @return objet JPanel représentant les informations
     *  TO DO
     */
    private JPanel createInfosPanel(){
        
        // Déclaration de la variable nécessaire pour la boucle
        int i;

        // Création d'un nouveau panel avec la stratégie de positionnement GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        // Ajout d'une ligne blanche de deux pixels au dessus
        panel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(255,255,255)));
        panel.setBackground(new Color(33,32,30)); // Modifie la couleur de fond du panel

        // Création d'un object GridBagConstraints pour la gestion des contraintes de chacuns des composants
        GridBagConstraints gc = new GridBagConstraints();

        // Initialisation des contraintes pour le titre du panel d'informations
        gc.insets = new Insets(10,10,10,10); // Marges internes de 10 pixels entre les cases 
        gc.gridx = 0; // Sa position x dans le tableau (abscisses)
        gc.gridy = 0; // Sa position y dans le tableau (ordonnées)
        gc.weightx = 1; // Poid de la case dans la taille final en abscisses
        gc.weighty = 1; // Poid de la case dans la taille final en ordonnées
        gc.anchor = GridBagConstraints.CENTER; // Ancre au centre de la case
        gc.fill = GridBagConstraints.HORIZONTAL; // Le composant doit occuper tout l'espace horizontale de la case

        // Création et initialisation de l'attribut infosTitreLabel pour le titre du panel des informations
        infosTitreLabel = createInfosTitreLabel();
        panel.add(infosTitreLabel, gc); // Ajout de l'attribut dans le panel pour les informations avec les contraintes


        gc.anchor = GridBagConstraints.LINE_START; // Ancre au milieu à gauche de la case
        gc.fill = GridBagConstraints.HORIZONTAL; // Le composant doit occuper tout l'espace horizontalement

        // Créations et initialisation de l'attribut infosLabels (JLabel[]) pour le texte des cinqs dernières informations
        infosLabels = createInfosLabels();

        // Boucles sur les cinqs textes crées
        for(i=0; i<infosLabels.length; i++){
            gc.gridy++; // On se déplace à la ligne du dessous
            panel.add(infosLabels[i], gc); // On ajout le texte dans le panel des inforamtions
        }

        /*
            TO DO: REMONTER LES INFORMATIONS DU JEU     
       */

        return panel;

    }

    /** 
     * Méthode qui crée et initialise le logo du projet
     * @return objet JLabel représentant le logo du projet 
     */
    private JLabel createNameJeuLabel(){

        // Création d'un nouveau text comportant le nom du projet
        JLabel label = new JLabel("<html><br><b>DEADALUS</b><br><br></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER); // Le text doit être centré horizontalement dans le label
        label.setOpaque(true); // On rend le label opaque
        // Ajout d'une petite bordure noire de deux pixels à gauche
        label.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(33,32,30)));
        label.setBackground(new Color(143,151,150)); // Modification de la couleur de fond du label
        
        return label;
    }

    /** 
     * Méthode qui crée et initialise le titre de la partie HUD
     * @return objet JLabel représentant le titre en question
     */
    private JLabel createHudLabel(){

        // Création d'un nouveu texte contenant le mot HUD
        JLabel label = new JLabel("HUD");
        label.setForeground(new Color(255,255,255)); // Mets la couleur de texte à blanc
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte dans le label
        // Ajout d'une bordure de 2 pixels blanche en bas
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255,255,255)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise le texte et l'icône des points de vie dans le HUD
     * @return objet JLabel représentant le texte et l'icône des points de vie
     */
    private JLabel createVieLabel(){

        // Création d'un nouveau texte indiquant le nombre de points de vie du joueur
        JLabel label = new JLabel("  Points de vie: "+fenetre.getJeu().getJoueur().getPointsVie());
        
        // Ajout d'une icône prévu pour les points de vie au texte
        label.setIcon(new ImageIcon(getClass().getResource("/assets/pointsVie.png")));

        // Modification de la couleur du texte en blanc
        label.setForeground(new Color(255,255,255));
        
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(33,32,30)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise le texte et l'icône des points d'armures pour le HUD
     * @return objet JLabel représentant le texte et l'icône des points d'armures
     */
    private JLabel createArmureLabel(){

        // Création d'un nouveau texte indiquant le nombre de points d'armures du joueur
        JLabel label = new JLabel("  Points d'armure: "+fenetre.getJeu().getJoueur().getPointsArmures());

        // Ajout d'une icône prévu pour les points d'armure au texte
        label.setIcon(new ImageIcon(getClass().getResource("/assets/armure_hud.png")));
        
        // Modification de la couleur du texte en blanc 
        label.setForeground(new Color(255,255,255));
        
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(33,32,30)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise le texte et l'icône du nom de l'arme possédée dans le HUD
     * @return objet JLabel représentant le texte et l'icône du nom de l'arme
     */
    private JLabel createArmeLabel(){

        // Definition d'une nouvelle chaîne de caractères
        String nomArme;

        // Si le joueur n'a pas d'arme
        if(fenetre.getJeu().getJoueur().getArme() == null){
            nomArme = "vide"; // Le texte indique que l'emplacement pour l'arme est vide
        } else { // Si le joueur à une arme
            nomArme = fenetre.getJeu().getJoueur().getArme().getNom(); // Le texte indique son noms
        } 

        // Création d'un nouveau texte indiquant la chaîne de caractère obtenue
        JLabel label = new JLabel("  Arme: "+nomArme);

        // Ajout d'une icône prévue pour les armes au texte
        label.setIcon(new ImageIcon(getClass().getResource("/assets/arme_hud.png")));

        // Modification de la couleur du texte en blanc
        label.setForeground(new Color(255,255,255));

        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(33,32,30)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise le texte et l'icône du titre du tableau des potions pour le HUD
     * @return objet JLabel représentant le titre du tableau des potions
     */
    private JLabel createPotionTitreLabel(){

        // Création d'un nouveau texte indiquant le titre "Potions:"
        JLabel label = new JLabel("  Potions:");

        // Ajout d'une icône prévu pour les potions au texte
        label.setIcon(new ImageIcon(getClass().getResource("/assets/potion_hud.png")));

        // Modification de la couleur du texte en blanc
        label.setForeground(new Color(255,255,255));

        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 5, 10, new Color(33,32,30)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise les icônes du tableau des potions
     * @param panel le panel dans lequel on souhaite ajouter le tableau des potions
     * @param gc les contraintes souhaitées pour chacunes des cases du tableau
     * @return objet JLabel[] tableau contenant les 5 icônes du tableau des potions
     */
    private JLabel[] createPotionLabels(JPanel panel, GridBagConstraints gc){
        
        // Déclaration de la variable nécessaire au parcours de la collection
        int i; 

        // Déclaration et intialisation d'un tableau de 5 labels
        JLabel[] labels = new JLabel[5];

        // On récupère l'inventaire du joueur (son tableau de potion possédées)
        ArrayList<Potion> potions = fenetre.getJeu().getJoueur().getInventaire();

        // Pour chaque potions possédées par le joueur
        for(i=0; i<potions.size() && i<5; i++){

            // On crée une nouvelle icône symbolysant une potion contenu dans une case
            labels[i] = new JLabel(new ImageIcon(getClass().getResource("/assets/potion_plein.png")));
            labels[i].setHorizontalAlignment(SwingConstants.CENTER); // On centre l'îcone horizontalement dans le label
            
            panel.add(labels[i], gc); // on ajoute l'icône dans le panel avec les contraintes souhaitées
            gc.gridx++; // On se décalle d'une case vers la droite dans le tableau
        }

        // Si le joueur n'avais pas 5 potions, tant que nous avons pas 5 composants dans le tableau
        while(i<5){

            // Création d'une nouvlle icône symbolysant une case de potion vide
            labels[i] = new JLabel(new ImageIcon(getClass().getResource("/assets/potion_vide.png")));
            labels[i].setHorizontalAlignment(SwingConstants.CENTER); // On centre l'icône horizontalement dans le label
            // On ajout une bordure de 12 pixels à gauche et à droite (pour crée une marge horizontale interne)
            labels[i].setBorder(BorderFactory.createMatteBorder(0, 12, 0, 12, new Color(33,32,30)));
            
            panel.add(labels[i], gc); // on ajoute l'icône dans le panel avec les contraintes souhaitées
            gc.gridx++; // On se décale d'une case à droite dans le tableau
            i++; // On passe au composant suivant
        }

        return labels;
    }

    /**
     * Méthode qui crée et initialise le texte et l'icône du titre présent dans le panel des informations
     * @return objet JLabel représentant le texte et l'icône en question
     */
    private JLabel createInfosTitreLabel(){

        // Création d'un nouveau texte indiquant le titre "INFORMATIONS DE PARTIE:"
        JLabel label = new JLabel(" INFORMATIONS DE PARTIE:");
        
        // Ajout d'une icône prévu pour le titre des informations au texte
        label.setIcon(new ImageIcon(getClass().getResource("/assets/infos.png")));

        // Modification de la couleur d'écriture
        label.setForeground(new Color(224,226,225));

        // Center horizontalement le texte dans le label
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Définition de la taille préférée du label (48x45)
        label.setPreferredSize(new Dimension(48, 45));

        // Centrer verticalement le texte dans le label
        label.setVerticalAlignment(SwingConstants.CENTER);

        // Ajout d'une bordure blanche de 2 pixels en bas
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255,255,255)));
        
        return label;
    }

    /**
     * Méthode qui crée et initialise le textes des cinqs dernières informations
     * @return objet JLable[] tableau de cinqs cases contenant le texte des cinsq dernières informations
     * TO DO
     */
    private JLabel[] createInfosLabels(){

        // Déclaration et initialisation d'un tableau de cinqs cases pour les textes
        JLabel[] labels = new JLabel[5];

        // Déclaration de la varaibale nécessaire pour parcourir le tableau
        int i;

        //Pour chaque case du tableau
        for(i=0; i<labels.length; i++){
            
            // Création d'un nouveau texte (TO DO)
            labels[i] = new JLabel("INFOS"+i);

            // Changement de la couleur du texte
            labels[i].setForeground(new Color(224,226,225));

            // Ajout d'une bordure de 5 pixel à l'horizontale, et de 3 pixels en haut (pour créer des marges internes)
            labels[i].setBorder(BorderFactory.createMatteBorder(3, 5, 0, 5, new Color(33,32,30)));
        }

        return labels;
    }

}
