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

public class FenetreCommandes extends JFrame {
    private FenetreGraphique fenetre;
    private JPanel contentPane;
    private JLabel titreLabel;
    private JLabel descriptionLabel;
    private JLabel[] deplacementsLabels;
    private JTextField[] deplacementsFields;
    private JLabel attaquerLabel;
    private JTextField attaquerField;
    private JLabel ramasserLabel;
    private JTextField ramasserField;
    private JLabel ouvrirLabel;
    private JTextField ouvrirField;
    private JLabel[] boirePotionsLabels;
    private JTextField[] boirePotionsFields;
    private JLabel[] competencesLabels;
    private JTextField[] competencesFields;
    private JButton boutonValider;
    private JButton boutonReset;

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

    public FenetreCommandes(FenetreGraphique f){

        super("Commandes");

        this.setVisible(true); // Rendre la fenêtre visible
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Terminer le programme lorsque l'on ferme la fenêtre
        this.setSize(new Dimension(600, 700)); // Modification des dimension de départ de la fenêtre
        this.setLocationRelativeTo(null); // Mise de la fenêtre au milieu de l'écran
        
        fenetre = f;
        contentPane = (JPanel) this.getContentPane();

        titreLabel = createTitreLabel();
        contentPane.add(titreLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane scrol = new JScrollPane(panel);
        contentPane.add(scrol, BorderLayout.CENTER);
        panel.setBackground(new Color(33,32,30));

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.insets = new Insets(20,20,20,20);
        gc.fill = GridBagConstraints.VERTICAL;
        gc.anchor = GridBagConstraints.CENTER;

        descriptionLabel = createDescriptionLabel();
        panel.add(descriptionLabel, gc);

        gc.gridwidth = 1;
        gc.gridy++;

        deplacementsLabels = createDeplacementsLabels();
        deplacementsFields = createDeplacementsFields();

        for(int i=0; i<deplacementsLabels.length; i++){

            panel.add(deplacementsLabels[i], gc);

            gc.gridx++;

            panel.add(deplacementsFields[i], gc);

            gc.gridx=0;
            gc.gridy++;
        }

        attaquerLabel = createAttaquerLabel();
        panel.add(attaquerLabel, gc);

        gc.gridx++;

        attaquerField = createAttaquerField();
        panel.add(attaquerField, gc);

        gc.gridy++;
        gc.gridx = 0;

        ramasserLabel = createRamasserLabel();
        panel.add(ramasserLabel, gc);

        gc.gridx++;

        ramasserField = createRamasserField();
        panel.add(ramasserField, gc);

        gc.gridy++;
        gc.gridx = 0;

        ouvrirLabel = createOuvrirLabel();
        panel.add(ouvrirLabel, gc);

        gc.gridx++;

        ouvrirField = createOuvrirField();
        panel.add(ouvrirField, gc);

        gc.gridy++;
        gc.gridx=0;

        boirePotionsLabels = createBoirePotionsLabels();
        boirePotionsFields = createBoirePotionsFields();

        for(int i=0; i<boirePotionsLabels.length; i++){
            panel.add(boirePotionsLabels[i], gc);

            gc.gridx++;

            panel.add(boirePotionsFields[i], gc);

            gc.gridy++;
            gc.gridx=0;
        }

        competencesLabels = createCompetencesLabels();
        competencesFields = createCompetencesFields();

        for(int i=0; i<competencesLabels.length; i++){
            panel.add(competencesLabels[i], gc);

            gc.gridx++;

            panel.add(competencesFields[i], gc);

            gc.gridy++;
            gc.gridx=0;
        }

        gc.gridy++;
        gc.fill = GridBagConstraints.HORIZONTAL;

        boutonReset = createBoutonReset(panel, gc);
        panel.add(boutonReset, gc);

        gc.gridx++;

        boutonValider = createBoutonValider(panel, gc);
        panel.add(boutonValider, gc);

    }

    private JLabel createTitreLabel(){

        JLabel label = new JLabel("<html><span style='font-size:18px;'>  LES COMMANDES DU JEU</span></html>");
        label.setIcon(imgCommandes);
        label.setPreferredSize(new Dimension(400,90));
        label.setForeground(new Color(33,32,30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)

        return label;

    }

    private JLabel createDescriptionLabel(){

        JLabel label = new JLabel("<html><p>Vous pouvez consulter ci-dessous les commandes du jeu.<br>Vous pouvez également les modifier comme vous le souhaitez.</p></html>");

        label.setForeground(new Color(255,255,255));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        // Ajout d'une bordure de 10 pixels (pour créer une marge interne)
        label.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(33,32,30)));

        return label;

    }

    private String stringOfCode(int c){
        String res = "";

        if(c == KeyEvent.VK_UP) { 
            res = "FLECHE HAUT";
        }

        if(c == KeyEvent.VK_DOWN) { 
            res = "FLECHE BAS";
        }

        if(c == KeyEvent.VK_LEFT) { 
            res = "FLECHE GAUCHE";
        }

        if(c == KeyEvent.VK_RIGHT) { 
            res = "FLECHE DROITE";
        }

        if(c == KeyEvent.VK_SPACE){
            res = "ESPACE";
        }

        if(c == KeyEvent.VK_NUMPAD0){
            res = "0 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD1){
            res = "1 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD2){
            res = "2 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD3){
            res = "3 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD4){
            res = "4 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD5){
            res = "5 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD6){
            res = "6 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD7){
            res = "7 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD8){
            res = "8 NUMPAD";
        }

        if(c == KeyEvent.VK_NUMPAD9){
            res = "9 NUMPAD";
        }

        if(c == KeyEvent.VK_CONTROL){
            res = "Ctrl";
        }

        if(c == KeyEvent.VK_SHIFT){
            res = "Shift";
        }

        if(c == KeyEvent.VK_TAB){
            res = "Tabulation";
        }

        if(c == KeyEvent.VK_ALT){
            res = "Alt";
        }

        if(c == KeyEvent.VK_ALT_GRAPH){
            res = "AltGr";
        }

        if(c == KeyEvent.VK_ENTER){
            res = "Entrer";
        }

        if(c == KeyEvent.VK_BACK_SPACE){
            res = "Cancel";
        }

        if(c == KeyEvent.VK_CAPS_LOCK){
            res = "Verr. Maj";
        }

        if(c == KeyEvent.VK_F1){
            res = "F1";
        }

        if(c == KeyEvent.VK_F2){
            res = "F2";
        }

        if(c == KeyEvent.VK_F3){
            res = "F3";
        }

        if(c == KeyEvent.VK_F4){
            res = "F4";
        }

        if(c == KeyEvent.VK_F5){
            res = "F5";
        }

        if(c == KeyEvent.VK_F6){
            res = "F6";
        }

        if(c == KeyEvent.VK_F7){
            res = "F7";
        }

        if(c == KeyEvent.VK_F8){
            res = "F8";
        }

        if(c == KeyEvent.VK_F9){
            res = "F9";
        }

        if(c == KeyEvent.VK_F10){
            res = "F10";
        }

        if(c == KeyEvent.VK_F11){
            res = "F11";
        }

        if(c == KeyEvent.VK_F12){
            res = "F12";
        }

        if(c == KeyEvent.VK_ESCAPE){
            res = "Echap";
        }

        return res;
    }

    private JLabel[] createDeplacementsLabels(){

        JLabel[] labels = new JLabel[4];

        labels[0] = new JLabel("  Deplacement Haut:");
        labels[0].setIcon(imgDeplaceHaut);
        labels[1] = new JLabel("  Deplacement Bas:");
        labels[1].setIcon(imgDeplaceBas);
        labels[2] = new JLabel("  Deplacement Gauche:");
        labels[2].setIcon(imgDeplaceGauche);
        labels[3] = new JLabel("  Deplacement Droite:");
        labels[3].setIcon(imgDeplaceDroite);

        for(int i=0; i<labels.length; i++){
            labels[i].setOpaque(true);
            labels[i].setPreferredSize(new Dimension(170,48));
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            labels[i].setBackground(new Color(255,255,255));
            labels[i].setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, new Color(255,255,255)));
        }

        return labels;

    }

    private JTextField[] createDeplacementsFields(){

        JTextField[] fields = new JTextField[4];
        int[] codes = fenetre.getCommandesDeplacements();

        for(int i=0; i<fields.length; i++){
            fields[i] = new JTextField();
            fields[i].setColumns(12);
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            String txt = stringOfCode(codes[i]);
            if(txt.length() == 0) txt = ""+(char) codes[i];
            fields[i].setText(txt);
        }

        // Ajout d'un écouteur sur les touches relachées
        fields[0].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[0].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[1].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[1].setText(res);
                
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[2].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[2].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[3].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[3].setText(res);
            }
        });

        return fields;

    }

    private JLabel createAttaquerLabel(){
        JLabel label = new JLabel("  Attaquer:");
        label.setIcon(imgAttaquer);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(170,48));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBackground(new Color(255,255,255));
        label.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, new Color(255,255,255)));
        return label;
    }

    private JTextField createAttaquerField(){
        JTextField field = new JTextField();
        field.setColumns(12);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        int code = fenetre.getCommandeAttaquer();
        String txt = stringOfCode(code);
        if(txt.length() == 0) txt = ""+(char) code;
        field.setText(txt);

        // Ajout d'un écouteur sur les touches relachées
        field.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                field.setText(res);
            }
        });
        return field;

    }

    private JLabel createRamasserLabel(){
        JLabel label = new JLabel("  Ramasser trésor:");
        label.setIcon(imgRamasser);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(170,48));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBackground(new Color(255,255,255));
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        return label;
    }

    private JTextField createRamasserField(){
        JTextField field = new JTextField();
        field.setColumns(12);
        field.setHorizontalAlignment(SwingConstants.CENTER);

        int code = fenetre.getCommandeRamasser();
        String txt = stringOfCode(code);
        if(txt.length() == 0) txt = ""+(char) code;
        field.setText(txt);

        // Ajout d'un écouteur sur les touches relachées
        field.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                field.setText(res);
            }
        });

        return field;
    }

    private JLabel createOuvrirLabel(){
        JLabel label = new JLabel("  Ouvrir trésor:");
        label.setIcon(imgOuvrir);
        label.setPreferredSize(new Dimension(170,48));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(255,255,255));
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        return label;

    }

    private JTextField createOuvrirField(){
        JTextField field = new JTextField();
        field.setColumns(12);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        int code = fenetre.getCommandeOuvrir();
        String txt = stringOfCode(code);
        if(txt.length() == 0) txt = ""+(char) code;
        field.setText(txt);

        // Ajout d'un écouteur sur les touches relachées
        field.addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                field.setText(res);
            }
        });

        return field;
    }

    private JLabel[] createBoirePotionsLabels(){

        JLabel[] labels = new JLabel[5];
        labels[0] = new JLabel("  Boire potion 1:");
        labels[1] = new JLabel("  Boire potion 2:");
        labels[2] = new JLabel("  Boire potion 3:");
        labels[3] = new JLabel("  Boire potion 4:");
        labels[4] = new JLabel("  Boire potion 5:");

        for(int i=0; i<labels.length; i++){
            labels[i].setOpaque(true);
            labels[i].setIcon(imgBoire);
            labels[i].setPreferredSize(new Dimension(170,48));
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            labels[i].setBackground(new Color(255,255,255));
            labels[i].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        }

        return labels;

    }

    private JTextField[] createBoirePotionsFields(){

        JTextField[] fields = new JTextField[5];
        int[] codes = fenetre.getCommandesBoirePotions();
        
        for(int i=0; i<fields.length; i++){
            fields[i] = new JTextField();
            fields[i].setColumns(12);
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            String txt = stringOfCode(codes[i]);
            if(txt.length() == 0) txt = ""+(char) codes[i];
            fields[i].setText(txt);
        }

        // Ajout d'un écouteur sur les touches relachées
        fields[0].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[0].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[1].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[1].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[2].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[2].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[3].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[3].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[4].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[4].setText(res);
            }
        });
        return fields;

    }

    private JLabel[] createCompetencesLabels(){
        JLabel[] labels = new JLabel[4];
        labels[0] = new JLabel("  Utiliser compétence 1:");
        labels[1] = new JLabel("  Utiliser compétence 2:");
        labels[2] = new JLabel("  Utiliser compétence 3:");
        labels[3] = new JLabel("  Utiliser compétence 4:");

        for(int i=0; i<labels.length; i++){
            labels[i].setOpaque(true);
            labels[i].setIcon(imgCompetence);
            labels[i].setPreferredSize(new Dimension(170,48));
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
            labels[i].setBackground(new Color(255,255,255));
            labels[i].setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(255,255,255)));
        }

        return labels;
    }

    private JTextField[] createCompetencesFields(){
        JTextField[] fields = new JTextField[4];
        int codes[] = fenetre.getCommandesCompetences();
        
        for(int i=0; i<fields.length; i++){
            fields[i] = new JTextField();
            fields[i].setColumns(12);
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            String txt = stringOfCode(codes[i]);
            if(txt.length() == 0) txt = ""+(char) codes[i];
            fields[i].setText(txt);
        }

        // Ajout d'un écouteur sur les touches relachées
        fields[0].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[0].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[1].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[1].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[2].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[2].setText(res);
            }
        });

        // Ajout d'un écouteur sur les touches relachées
        fields[3].addKeyListener(new KeyAdapter(){ 
            /**
             * Méthode appeler lorsque l'utilisateur relâche une touche
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void keyReleased(KeyEvent e){
                int c = e.getKeyCode(); // Recupère le caractère qui a déclenché l'évenement
                String res = stringOfCode(c);

                if(res == ""){
                    res = ""+e.getKeyChar();
                }

                fields[3].setText(res);
            }
        });

        return fields;
    }


    private JButton createBoutonReset(JPanel panel, GridBagConstraints gc){
        JButton bouton = new JButton("Reset");
        bouton.setBackground(new Color(234,161,19)); // Change sa couleur de fond
        bouton.setForeground(new Color(24,34,95)); // Change sa couleur de texte
        FenetreCommandes fc = this; 
        bouton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                

                fenetre.initialiserCommandes();

                int[] cmds = fenetre.getCommandesDeplacements();
                deplacementsFields[0].setText(stringOfCode(cmds[0]));
                deplacementsFields[1].setText(stringOfCode(cmds[1]));
                deplacementsFields[2].setText(stringOfCode(cmds[2]));
                deplacementsFields[3].setText(stringOfCode(cmds[3]));

                attaquerField.setText(stringOfCode(fenetre.getCommandeAttaquer()));
                ramasserField.setText(""+(char)fenetre.getCommandeRamasser());
                ouvrirField.setText(""+(char)fenetre.getCommandeOuvrir());

                cmds = fenetre.getCommandesBoirePotions();

                boirePotionsFields[0].setText(stringOfCode(cmds[0]));
                boirePotionsFields[1].setText(stringOfCode(cmds[1]));
                boirePotionsFields[2].setText(stringOfCode(cmds[2]));
                boirePotionsFields[3].setText(stringOfCode(cmds[3]));
                boirePotionsFields[4].setText(stringOfCode(cmds[4]));

                cmds = fenetre.getCommandesCompetences();

                competencesFields[0].setText(""+(char)cmds[0]);
                competencesFields[1].setText(""+(char)cmds[1]);
                competencesFields[2].setText(""+(char)cmds[2]);
                competencesFields[3].setText(""+(char)cmds[3]);

                JLabel label = new JLabel("Les changements ont bien été remit à zéro.");
                label.setOpaque(true);
                label.setBackground(new Color(90,209,26));
                label.setForeground(new Color(255,255,255));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(90,209,26)));
                gc.gridy = 17; 
                gc.gridwidth = 2;
                gc.gridx = 0;
                panel.add(label, gc);
                fc.validate();
                    
                
            }
        });
        return bouton;
    }


    private JButton createBoutonValider(JPanel panel, GridBagConstraints gc){
        JButton bouton = new JButton("Sauvegarder");
        bouton.setBackground(new Color(24,34,95)); // Change sa couleur de fond
        bouton.setForeground(new Color(234,161,19)); // Change sa couleur de texte
        FenetreCommandes fc = this; 
        bouton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
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

                    fenetre.setCmdDeplacementHaut(codeOfString(deplacementsFields[0].getText()));
                    fenetre.setCmdDeplacementBas(codeOfString(deplacementsFields[1].getText()));
                    fenetre.setCmdDeplacementGauche(codeOfString(deplacementsFields[2].getText()));
                    fenetre.setCmdDeplacementDroite(codeOfString(deplacementsFields[3].getText()));

                    fenetre.setCmdAttaquer(codeOfString(attaquerField.getText()));
                    fenetre.setCmdOuvrir(codeOfString(ouvrirField.getText()));
                    fenetre.setCmdRamasser(codeOfString(ramasserField.getText()));

                    fenetre.setCmdBoirePotions(codeOfString(boirePotionsFields[0].getText()),
                    codeOfString(boirePotionsFields[1].getText()),
                    codeOfString(boirePotionsFields[2].getText()),
                    codeOfString(boirePotionsFields[3].getText()),
                    codeOfString(boirePotionsFields[4].getText()));

                    fenetre.setCmdCompetences(codeOfString(competencesFields[0].getText()),
                    codeOfString(competencesFields[1].getText()),
                    codeOfString(competencesFields[2].getText()),
                    codeOfString(competencesFields[3].getText()));


                    JLabel label = new JLabel("Les changements ont bien été enregistrés.");
                    label.setOpaque(true);
                    label.setBackground(new Color(90,209,26));
                    label.setForeground(new Color(255,255,255));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(90,209,26)));
                    gc.gridy = 17; 
                    gc.gridx = 0;
                    gc.gridwidth = 2;
                    panel.add(label, gc);
                    fc.validate();
                    
                }
            }
        });
        return bouton;
    }

    private int codeOfString(String s){
       
        int res = -1;
    
        if(s.equals("FLECHE HAUT")) { 
            res = KeyEvent.VK_UP;
        }
    
        if(s.equals("FLECHE BAS")) { 
            res = KeyEvent.VK_DOWN;
        }
    
        if(s.equals("FLECHE GAUCHE")) { 
            res = KeyEvent.VK_LEFT;
        }
    
        if(s.equals("FLECHE DROITE")) { 
            res = KeyEvent.VK_RIGHT;
        }
    
        if(s.equals("ESPACE")){
            res = KeyEvent.VK_SPACE;
        }
    
        if(s.equals("0 NUMPAD")){
            res = KeyEvent.VK_NUMPAD0;
        }
    
        if(s.equals("1 NUMPAD")){
            res = KeyEvent.VK_NUMPAD1;
        }
    
        if(s.equals("2 NUMPAD")){
            res = KeyEvent.VK_NUMPAD2;
        }
    
        if(s.equals("3 NUMPAD")){
            res = KeyEvent.VK_NUMPAD3;
        }
    
        if(s.equals("4 NUMPAD")){
            res = KeyEvent.VK_NUMPAD4;
        }
    
        if(s.equals("5 NUMPAD")){
            res = KeyEvent.VK_NUMPAD5;
        }
    
        if(s.equals("6 NUMPAD")){
            res = KeyEvent.VK_NUMPAD6;
        }
    
        if(s.equals("7 NUMPAD")){
            res = KeyEvent.VK_NUMPAD7;
        }
    
        if(s.equals("8 NUMPAD")){
            res = KeyEvent.VK_NUMPAD8;
        }
    
        if(s.equals("9 NUMPAD")){
            res = KeyEvent.VK_NUMPAD9;
        }
    
        if(s.equals("Ctrl")){
            res = KeyEvent.VK_CONTROL;
        }
    
        if(s.equals("Shift")){
            res = KeyEvent.VK_SHIFT;
        }
    
        if(s.equals("Tabulation")){
            res = KeyEvent.VK_TAB;
        }
    
        if(s.equals("Alt")){
            res = KeyEvent.VK_ALT;
        }
    
        if(s.equals("AltGr")){
            res = KeyEvent.VK_ALT_GRAPH;
        }
    
        if(s.equals("Entrer")){
            res = KeyEvent.VK_ENTER;
        }
    
        if(s.equals("Cancel")){
            res = KeyEvent.VK_BACK_SPACE;
        }
    
        if(s.equals("Verr. Maj")){
            res = KeyEvent.VK_CAPS_LOCK;
        }
    
        if(s.equals("F1")){
            res = KeyEvent.VK_F1;
        }
    
        if(s.equals("F2")){
            res = KeyEvent.VK_F2;
        }
    
        if(s.equals("F3")){
            res = KeyEvent.VK_F3;
        }
    
        if(s.equals("F4")){
            res = KeyEvent.VK_F4;
        }
    
        if(s.equals("F5")){
            res = KeyEvent.VK_F5;
        }
    
        if(s.equals("F6")){
            res = KeyEvent.VK_F6;
        }
    
        if(s.equals("F7")){
            res = KeyEvent.VK_F7;
        }
    
        if(s.equals("F8")){
            res = KeyEvent.VK_F8;
        }
    
        if(s.equals("F9")){
            res = KeyEvent.VK_F9;
        }
    
        if(s.equals("F10")){
            res = KeyEvent.VK_F10;
        }
    
        if(s.equals("F11")){
            res = KeyEvent.VK_F11;
        }
    
        if(s.equals("F12")){
            res = KeyEvent.VK_F12;
        }
    
        if(s.equals("Echap")){
            res = KeyEvent.VK_ESCAPE;
        }

        if(res == -1){
            res = KeyEvent.getExtendedKeyCodeForChar(s.charAt(0));
            
        }
    
        return res;
        
    }

}
