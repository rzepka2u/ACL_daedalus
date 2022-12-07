package model.ihm;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;


/**
 * Classe qui représente les fenêtre d'affichage d'informations dans l'application (règles et copyright)
 */
public class FenetreInformation extends JFrame {

    /**
     * Le titre de la fenêtre
     */
    private JLabel titreLabel;

    /**
     * Le texte contenu de la fenêtre en HTML 
     */
    private JLabel textHTMLabel;

    /**
     * Le panel qui contient les éléments de la fenêtre
     */
    private JPanel contentPane;
    
    // Constantes de la classe contenant les images utilisées dans les instances de cette classe
    // (pour éviter la relecture à chaque mise à jour de l'affichage)
    private static final ImageIcon imgRegles = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/regles.png"));
    private static final ImageIcon imgCopyright = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/copyright.png"));

    /**
     * Constructeur par défaut de la classe FenetreInformations
     * @param isRegles true si fenêtre de règle, false si fenêtre de copyright
     */
    public FenetreInformation(boolean isRegles){

        // Appel au constructeur de la classe mère
        super("Informations");

        this.setVisible(true); // Rendre la fenêtre visible
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Terminer le programme lorsque l'on ferme la fenêtre
        this.setSize(new Dimension(750, 500)); // Modification des dimension de départ de la fenêtre
        this.setLocationRelativeTo(null); // Mise de la fenêtre au milieu de l'écran
        
        // Initialisation de l'attribut contentPane 
        this.contentPane = (JPanel) this.getContentPane();

        // Création du texte du titre de la page
        titreLabel = createTitreLabel(isRegles);
        
        // Ajout du texte contenant le titre de la page au nord du panel de la fenêtre
        contentPane.add(titreLabel, BorderLayout.NORTH);

        // Modification de la couleur de fond
        this.contentPane.setBackground(new Color(33,32,30));

        // Création d'un nouveau panel avec la stratégie de positionnement GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        // Création d'un nouveau panel "scrollable" qui contient le panel GridBagLayout
        JScrollPane scrol = new JScrollPane(panel);

        // Ajout du panel "scrollable" au centre du panel de la fenetre
        contentPane.add(scrol, BorderLayout.CENTER);
        
        // Création d'un nouvelle objet pour les contraintes de positionnement
        GridBagConstraints gc = new GridBagConstraints();


        // Initialisation des contraintes pour le prochain élément à insérer dans la grille
        gc.gridx = 0; // Sa position x dans le tableau (abscisses)
        gc.gridy = 0; // Sa position y dans le tableau (ordonnées)
        gc.weightx = 1; // Son poids dans la taille totale en abscisses
        gc.weighty = 1; // Son poids dans la taille totale en ordonnées
        gc.anchor = GridBagConstraints.CENTER; // On l'ancre au centre de la case
        gc.fill = GridBagConstraints.NONE; // L'élément n'essaye pas de remplir la place inoccupé
        
        // On crée le texte du contenu de la page et on l'ajoute au panel GridBagLayout avec les contraintes associées
        textHTMLabel = createTextHTMLLabel(isRegles);
        panel.add(textHTMLabel, gc);
    }

    /**
     * Méthode qui permet de créer le texte du titre de la fenetre
     * @param isRegles true si fenêtre de règle, false si fenêtre de copyright
     * @return le texte correctement instancié et contenant le titre de la fenêtre
     */
    private JLabel createTitreLabel(boolean isRegles){

        // Création d'un nouveau texte vide
        JLabel label = new JLabel();

        // Si c'est la page de règle
        if(isRegles){
            // Ajout du titre et de l'icon des règles
            label.setText("<html><span style='font-size:18px;'>LES REGLES DU JEU</span></html>");
            label.setIcon(imgRegles);
        } else { // Sinon c'est la page de copyright
            // Ajout du titre et de l'icon du copyright
            label.setText("<html><span style='font-size:18px;'>COPYRIGHT DE L'APPLICATION</span></html>");
            label.setIcon(imgCopyright);
        }

        
        // Change la couleur du texte
        label.setForeground(new Color(255,255,255));
        // On centre le texte horizontalement dans la zone de texte
        label.setHorizontalAlignment(SwingConstants.CENTER);
        // On centre le texte verticalement dans la zone de texte
        label.setVerticalAlignment(SwingConstants.CENTER);
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(33,32,30)));

        return label;
    }

    /**
     * Méthode qui permet de créer le texte du contenu de la fenetre
     * @param isRegles true si fenêtre de règle, false si fenêtre de copyright
     * @return le texte correctement instancié et contenant les informations de la fenêtre
     */
    private JLabel createTextHTMLLabel(boolean isRegles){

        // Création d'un nouveau texte vide
        JLabel label = new JLabel();
        

        // Si c'est la page des règles
        if(isRegles){
            // Ajout du texte des règles et d'une taille préférentielle
            label.setText(textRegles());
            label.setPreferredSize(new Dimension(640,800));
        } else { // Sinon, c'est la page du copyright
            // Ajout du texte du copyright et d'une taille préférentielle
            label.setText(textCopyright());
            label.setPreferredSize(new Dimension(640,350));
        }

        // On centre le texte horizontalement dans la zone de texte
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    /**
     * Méthode qui permet de récupérer le texte des informations de la page des règles en format HTML  
     * @return l'objet String contenant le HTML
     */
    private String textRegles(){

        return 
        
        "<html><br><br>"
            +"<p style='font-size:10px;'>"
                +"Le jeu <b>Daedalus</b> est un jeu qui consiste à traverser des labyrinthes successivement jusqu'à gagner la partie."
                +" Le joueur choisit au départ un nombre de niveaux maximum (= à finir pour gagner), puis s'il souhaite jouer avec ses propres labyrinthes prédéfinit ou les labyrinthes généré aléatoirement."
                +" Le premier labyrinthe est ensuite créer, et le joueur se retrouve inclus à l'entrée du labyrinthe, en compagnie de plusieurs types de montres."
                +" On retrouve également dans les labyrinthes des cases à effets spéciaux (négatif ou positif pour le joueur), ainsi que des trésors pouvant contenir des potions, des armes ou des pièces d'armures."
                +"<br><br> Le seul but de ce jeu est de terminer le nombre de niveau choisi au départ afin de gagner la partie. Pour se faire, vous devez"
                +" atteindre la case de sortie de chaque labyrinthe, en attaquant les monstres pour vous défendre mais aussi en esquivant leurs attaques, ou encore faire attention et esquiver (si possible)"
                +" les cases à effets négatifs. Nous vous conseillons également de fouiller le maximum de trésors possible afin de récupérer des objets qui vous aideront beaucoup.."
                +"La partie est considéré comme perdue seulement lorsque le joueur est mort.<br><br>"
                +"Au niveau des armes, il existe plusieurs armes avec des nombres de dégats, des portée, mais aussi des zones d'attaques différentes. Il est possible de ramasser une arme sur dans le labyrinthe en échangeant avec celle que vous aviez avant.<br><br>"
                +"Pour ce qui est des potions, lorsque vous en ramassez une, elle se place dans votre inventaire (s'il n'est pas plein), pour pouvoir ensuite la consommer quand vous le souhaitez.<br><br>"
                +"Il existe également un système de rang et d'expérience, qui s'incrémentent lorsque l'on fini un labyrinthe ou lorsque l'on tue un monstre."
                +"Ce système de rang permets d'augmenter les points de vie et points d'armures maximums, la place dans l'inventaire de potion, ainsi que les compétences disponibles."
                +"<br><br>Les compétences sont au nombre de quatre et sont obtenues automatiquement lorsque votre rang le permet. Voici un descriptif des compétences:"
            +"</p>"
            +"<ul>"
                +"<li>"
                    +"BERSERKER : Sacrifier 20 points de vie contre 50% de dégâts en plus pendant 10 secondes."
                +"</li>"
                +"<li>"
                    +"BOUCLIER MAGIQUE : Gagner 25 points d'armures temporaire (Deux niveaux de temps de recharge)."
                +"</li>"
                +"<li>"
                    +"DRAIN DE VIE : Vous ajoutez à vos points de vies chacun des points de vie infligés à vos ennemis pendant 10 secondes"
                +"</li>"
                +"<li>"
                    +"REVENANT : Lorsque vous mourrez, vous ressuscitez avec 20 points de vie (utilisable qu'une fois par partie)"
                +"</li>"
                +"<li>"
                    +"BLOCAGE : Permet de prendre 25% de dégâts en moins pendant 10 secondes (Un niveau de temps de recharge)"
                +"</li>"
                +"<li>"
                    +"EPINES : Permet de renvoyer 25% des dégâts subis au Monstre qui vous a attaqué pendant 10 secondes (Un niveau de temps de recharge)"
                +"</li>"
                +"<li>"
                    +"ANGUILLE : Permet d'avoir 10% de chance d'esquiver une attaque subie (Compétence passive)"
                +"</li>"
                +"<li>"
                    +"TELEPORTATION : Permet de se déplacer de deux cases dans la direction où vous regardez (Un niveau de temps de recharge)"
                +"</li>"
            +"</ul>"
        +"</html>";

    }

    /**
     * Méthode qui permet de récupérer le texte des informations de la page du copyritght en format HTML  
     * @return l'objet String contenant le HTML
     */
    private String textCopyright(){
        return 
        
        "<html><br>"
            +"<p style='font-size:15px;'>"
                +"L'application du jeu <b>Daedalus</b> a été conçue dans le cadre d'un projet universitaire pour le module <b>Analyse et Conception De Logiciel</b> en M1 INFORMATIQUE (2022),"
                +" au sein de l'<b>Université de Lorraine</b>. <br>Tous droits réservés à cette université ainsi qu'à ses créateurs.<br><br>"
            +"</p>"
            +"<p style='font-size:15px;'>"
                +"Les personnes ayant travaillé sur ce projet sont:"
            +"</p>"
            +"<ul>"
                +"<li>"
                    +"<span style='font-size:13px;'><i>M. RZEPKA Thomas</i></span>"
                +"</li>"
                +"<li>"
                    +"<span style='font-size:13px;'><i>M. POIREL Jérémy</i></span>"
                +"</li>"
                +"<li>"
                    +"<span style='font-size:13px;'><i>M. NOEL Victor</i></span>"
                +"</li>"
                +"<li>"
                    +"<span style='font-size:13px;'><i>M. RACOILLET Maxime</i></span>"
                +"</li>"
            +"</ul>"
        +"</html>";
    }

}
