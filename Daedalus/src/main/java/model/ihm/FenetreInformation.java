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

public class FenetreInformation extends JFrame {

    private JLabel titreLabel;
    private JLabel textHTMLabel;
    private JPanel contentPane;

    public FenetreInformation(boolean isRegles){
        super("Informations");

        this.setVisible(true); // Rendre la fenêtre visible
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Terminer le programme lorsque l'on ferme la fenêtre
        this.setSize(new Dimension(750, 500)); // Modification des dimension de départ de la fenêtre
        this.setLocationRelativeTo(null); // Mise de la fenêtre au milieu de l'écran
        
        this.contentPane = (JPanel) this.getContentPane();

        titreLabel = createTitreLabel(isRegles);
        contentPane.add(titreLabel, BorderLayout.NORTH);
        this.contentPane.setBackground(new Color(33,32,30));

        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane scrol = new JScrollPane(panel);
        contentPane.add(scrol, BorderLayout.CENTER);
        
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        
        textHTMLabel = createTextHTMLLabel(isRegles);
        panel.add(textHTMLabel, gc);
    }

    private JLabel createTitreLabel(boolean isRegles){

        JLabel label = new JLabel();

        if(isRegles){
            label.setText("<html><span style='font-size:18px;'>LES REGLES DU JEU</span></html>");
            label.setIcon(new ImageIcon(getClass().getResource("/assets/regles.png")));
        } else {
            label.setText("<html><span style='font-size:18px;'>COPYRIGHT DE L'APPLICATION</span></html>");
            label.setIcon(new ImageIcon(getClass().getResource("/assets/copyright.png")));
        }

        
        label.setForeground(new Color(255,255,255));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(33,32,30)));

        return label;
    }

    private JLabel createTextHTMLLabel(boolean isRegles){
        JLabel label = new JLabel();
        
        if(isRegles){
            label.setText(textRegles());
            label.setPreferredSize(new Dimension(640,610));
        } else {
            label.setText(textCopyright());
            label.setPreferredSize(new Dimension(640,350));
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

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
            +"</ul>"
        +"</html>";

    }

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
