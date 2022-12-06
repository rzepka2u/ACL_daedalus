package model.ihm;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class PanelChoixCompetences extends JPanel {
    
    private FenetreGraphique fenetre;
    private JPanel centerPanel;
    private JLabel infosLabel;
    private JCheckBox[] competancesCheckBoxs;
    private JLabel[] competancesLabels;
    private JButton startButton;
    private int nbNiveau;
    private String path;

    private static final ImageIcon imgCompetence = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/competence.png"));
    private static final ImageIcon imgBerserker = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/berserker.png"));
    private static final ImageIcon imgBouclier_magique = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/bouclier_magique.png"));
    private static final ImageIcon imgDrain_vie = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/drain_vie.png"));
    private static final ImageIcon imgRevenant = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/revenant.png"));
    private static final ImageIcon imgBlocage = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/blocage.png"));
    private static final ImageIcon imgEpines = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/epines.png"));
    private static final ImageIcon imgAnguilles = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/anguilles.png"));
    private static final ImageIcon imgTeleportation = new ImageIcon(PanelChoixCompetences.class.getResource("/assets/teleportation.png"));

    public PanelChoixCompetences(FenetreGraphique f, int nb, String chemin){

        super(new BorderLayout());
        this.fenetre = f;
        this.nbNiveau = nb;
        this.path = chemin;

        infosLabel = createInfosLabel();
        this.add(infosLabel, BorderLayout.NORTH);
        
        int i;
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(33,32,30));
        GridBagConstraints gc = new GridBagConstraints();
        

        competancesCheckBoxs = createCompetancesCheckBoxs();
        competancesLabels = createCompetancesLabels();

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.BOTH;

        for(i=0; i<8; i++){
            JPanel competancePanel = new JPanel(new FlowLayout());
            competancePanel.setBackground(new Color(33,32,30));

            competancePanel.add(competancesCheckBoxs[i]);
            competancePanel.add(competancesLabels[i]);

            centerPanel.add(competancePanel, gc);

            if(i%2==0){
                gc.gridx++;
            } else {
                gc.gridx = 0;
                gc.gridy++;
            }
        }  

        startButton = createStartButton();
        gc.gridwidth = 3;
        gc.ipady = 8;
        gc.insets = new Insets(15,0,0,0);

        centerPanel.add(startButton, gc);

        this.add(centerPanel, BorderLayout.CENTER);

    }

    private JLabel createInfosLabel(){
        JLabel label = new JLabel("Veuillez sélectionner vos quatre compétences de départ");
        label.setIcon(imgCompetence);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, label.getBackground()));
        return label;
    }

    private JCheckBox[] createCompetancesCheckBoxs(){

        JCheckBox[] checks = new JCheckBox[8];
        int i;

        for(i=0; i<checks.length; i++){
            checks[i] = new JCheckBox();
            int x = i;
            checks[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){

                    try {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/checkbox.wav"));
                        clip.open(inputStream);
                        clip.start();
                    } catch (Exception excep) { System.out.println(excep.getMessage()); }

                    int cpt=0, j;
                    for(j=0; j<competancesCheckBoxs.length; j++){
                        if(competancesCheckBoxs[j].isSelected()){
                            cpt++;
                        }
                    }

                    if(cpt == 5){

                        boolean cond = true;
                        for(j=0; j<competancesCheckBoxs.length && cond; j++){
                            if(x!=j && competancesCheckBoxs[j].isSelected()){
                                competancesCheckBoxs[j].setSelected(false);
                                cond = false;
                            }
                        }

                    }
                }
            });
        }   

        return checks;
    }

    private JLabel[] createCompetancesLabels(){

        JLabel[] labels = new JLabel[8];

        int i;

        labels[0] = new JLabel("BERSERKER");
        labels[0].setIcon(imgBerserker);

        labels[1] = new JLabel("BOUCLIER MAGIQUE");
        labels[1].setIcon(imgBouclier_magique);

        labels[2] = new JLabel("DRAIN DE VIE");
        labels[2].setIcon(imgDrain_vie);

        labels[3] = new JLabel("REVENANT");
        labels[3].setIcon(imgRevenant);

        labels[4] = new JLabel("BLOCAGE");
        labels[4].setIcon(imgBlocage);

        labels[5] = new JLabel("EPINES");
        labels[5].setIcon(imgEpines);

        labels[6] = new JLabel("ANGUILLES");
        labels[6].setIcon(imgAnguilles);

        labels[7] = new JLabel("TELEPORTATION");
        labels[7].setIcon(imgTeleportation);

        for(i=0; i<labels.length; i++){
            labels[i].setForeground(new Color(255,255,255));
            labels[i].setPreferredSize(new Dimension(170,60));
            labels[i].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(33,32,30)));
            labels[i].setVerticalAlignment(SwingConstants.CENTER);
        }

        return labels;

    }

    private JButton createStartButton(){
        JButton bouton = new JButton("Démarrer la partie");
        bouton.setBackground(new Color(24,34,95)); // Change sa couleur de fond
        bouton.setForeground(new Color(234,161,19)); // Change sa couleur de texte
        bouton.addActionListener(new ActionListener(){
            /**
             * Méthode appeler lorsque l'utilisateur clique sur le bouton
             * @param e L'évenement capté par l'écouteur
             */
            @Override
            public void actionPerformed(ActionEvent e){
                int cpt=0, i;
                int[] competancesChoisies = new int[4];
                for(i=0; i<competancesCheckBoxs.length; i++){
                    if(competancesCheckBoxs[i].isSelected()){
                        competancesChoisies[cpt] = i;
                        cpt++;
                    }
                }

                if(cpt == 4){

                    try {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/bouton.wav"));
                        clip.open(inputStream);
                        clip.start();
                    } catch (Exception excep) { System.out.println(excep.getMessage()); }

                    try{
                        fenetre.afficherPartie(nbNiveau, competancesChoisies, path);
                    } catch(FileNotFoundException exception){
                        exception.printStackTrace();
                    }

                } else {
                    
                    JLabel label = new JLabel("Vous devez sélectionner au minimum quatre compétences.");
                    label.setOpaque(true);
                    label.setForeground(new Color(255,255,255));
                    label.setBackground(new Color(209,43,26));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setPreferredSize(new Dimension(20,40));

                    GridBagConstraints gc = new GridBagConstraints();
                    gc.gridx=0;
                    gc.gridy=0;
                    gc.gridwidth=3;
                    gc.fill = GridBagConstraints.BOTH;
                    gc.anchor = GridBagConstraints.CENTER;    
                    
                    centerPanel.add(label, gc);
                    fenetre.validate();
                    
                }
            }
        });

        return bouton;
    }


}
