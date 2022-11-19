package model.ihm;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Classe PanelFinJeu représentant le panel qui sera affiché dans la fenêtre lorsque l'on termine une partie
 */
public class PanelFinJeu extends JPanel {

    // Déclaration des attributs
    private FenetreGraphique fenetre;
    private boolean type; // Le type de la fenêtre (true = victoire, false = défaite)
    private JLabel icone; // L'image de la fenêtre
    private JLabel texte; // Le texte de la fenêtre
    private JButton boutton; // Le boutton pour lancer une nouvelle partie

    /**
     * Unique constructeur de la classe PanelFinJeu 
     * @param result true si gagner, false sinon
     */
    public PanelFinJeu(FenetreGraphique f, boolean result){

        // Appel du constructeur de la classe JPanel et définition stratégie de positionnement GridBagLayout
        super(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints(); // Nouvel objet pour les contraintes de chaques composants
        this.setBackground(new Color(45,78, 95)); // Définition de la couleur de fond du panel
        
        // Association du paramètre reçu à l'attribut prévu à cet effet
        type = result;
        fenetre = f;

        // Initialisation des contraintes pour l'ajout de l'image
        gc.gridx = 0; // Sa position x dans le tableau (abscisses)
        gc.gridy = 0; // Sa position y dans le tableau (ordonnées)
        gc.weightx = 0.88; // Le poid de la case dans la taille horizontale totale
        gc.weighty = 0.4; // Le poid de la case dans la taille verticale totale
        gc.anchor = GridBagConstraints.CENTER; // Ancre au centre de la case
        gc.fill = GridBagConstraints.NONE; // Le composant n'essaye pas de remplir plus l'espace

        // Initialisation et création de l'attribut icône pour l'image du panel
        icone = createIcone();
        this.add(icone, gc); // On ajout l'attribut au panel de fin de jeu avec les contraintes

        gc.gridy++; // On se déplace à la ligne du dessous
        gc.anchor = GridBagConstraints.PAGE_START; // Ancre du composant en haut au centre de la case 
        gc.weighty = 0.4; // Le poid de la case dans la taille verticale totale

        // Initialisation et création de l'attribut texte pour le texte du panel
        texte = createTexte();
        this.add(texte, gc); // On ajout l'attribut au panel de fin de jeu avec les contraintes

        gc.gridy++; // On se déplace à la ligne du dessous
        gc.anchor = GridBagConstraints.PAGE_START; // Ancre du composant en haut au centre de la case 

        // Initialisation et création de l'attribut boutton pour le boutton de relancement de partie
        boutton = createBoutton();
        this.add(boutton, gc); // On ajout l'attribut au panel de fin de jeu avec les contraintes
    }

    /**
     * Méthode qui crée et initialise l'image du panel via un objet JLabel
     * @return objet JLabel représentant l'image a afficher dans le panel de fin de partie
     */
    private JLabel createIcone(){
        
        // Création d'un nouveau label
        JLabel label = new JLabel();

        if(type){ // Si c'est un panel de victoire
            // Ajout de l'icône de victoire prévu à cet effet dans le label
            label.setIcon(new ImageIcon(getClass().getResource("/assets/victoire.png")));
        } else { // Si c'est un panel de défaite
            // Ajout de l'icône de défaite prévu à cet effet dans le label
            label.setIcon(new ImageIcon(getClass().getResource("/assets/defaite.png")));
        }

        return label;
    }

    /**
     * Méthode qui crée et initialise le texte du panel via un objet JLabel
     * @return objet JLabel représentant le texte à afficher dans le panel de fin de partie
     */
    private JLabel createTexte(){

        // Création d'un nouveau label
        JLabel label = new JLabel();
        
        if(type){ // Si le panel est un panel de victoire
            // Ajout du texte commentant la victoire dans le label
            label.setText("<html><i><b>Vous avez gagner la partie !</b></i></html>");
            label.setBackground(new Color(31,141,26)); // Change la couleur de fond à vert
        } else { // Si le panel est un panel de défaite
            // Ajout du texte commentant la défaite dans le label
            label.setText("<html><i><b>Vous êtes mort !</b></i></html>");
            label.setBackground(new Color(216,31,32)); // Change la couleur de fond à rouge
        }

        // Définition d'une taille préferée de 250x50 pixels pour le label
        label.setPreferredSize(new Dimension(250,50));
        // On centre le texte horizontalement dans le label
        label.setHorizontalAlignment(SwingConstants.CENTER);
        // On centre le texte verticalement dans le label
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(true); // Rend le label opaque
        label.setForeground(new Color(255,255,255)); // Change la couleur du texte à blanc
        // Ajout d'une bordure de deux pixels de couleur blanche
        label.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(255,255,255)));

        return label;
    }

    /**
     * Méthode qui crée et initialise le boutton pour rédemarrer une partie du panel de fin de jeu
     * @return objet JButton représentant le boutton à afficher dans le panel
     */
    private JButton createBoutton(){
        
        // Création d'un nouveau boutton avec le texte à inclure dedans
        JButton btn = new JButton("Démarrer une nouvelle partie");

        // Ajout d'une taille préférée de 250x35 pixels pour le bouton
        btn.setPreferredSize(new Dimension(250,35)); 
        btn.setBackground(new Color(33,32,30)); // Changement de la couleur de fond
        btn.setForeground(new Color(234,161,19)); // Changement de la couleur du texte

        // Ajout d'un écouter sur le button
        btn.addActionListener(new ActionListener(){
            /**
             * Méthode appeler lorsque l'utilisateur clique sur le bouton
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void actionPerformed(ActionEvent e){
                fenetre.dispose();
                new FenetreGraphique();
            }

        });
        
        return btn;
    }
}
