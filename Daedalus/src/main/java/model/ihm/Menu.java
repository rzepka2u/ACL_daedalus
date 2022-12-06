package model.ihm;

import javax.swing.JMenuBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.threads.ThreadAffichage;
import model.threads.ThreadMonstre;

public class Menu extends JMenuBar{
    
    private FenetreGraphique fenetre;
    private JMenu control; // Le menu de contrôles
    private JMenu aide; // Le menu d'aide
    private JMenu informations; // Le menu d'informations
    private JMenuItem sauvegarder; // L'item sauvegarder du menu de contrôles
    private JMenuItem redemarrer; // L'item redémarrer du menu de contrôles
    private JMenuItem quitter; // L'item quitter du menu de contrôles
    private JMenuItem regles; // L'item règles du menu d'aide
    private JMenuItem commandes; // L'item commandes du menu d'aide
    private JMenuItem copyright; // L'item copyright du menu d'informations

    /**
     * Constructeur par défaut d'un objet Menu
     */
    public Menu(FenetreGraphique f){

        // Appel au constructeur de JMenuBar
        super();

        fenetre = f;

        // Création et initialisation du menu de contrôles
        control = createControl();
        super.add(control); // Affectation du menu à la barre

        // Création et intialisation du menu d'aide
        aide = createAide();
        super.add(aide); // Affectation du menu à la barre

        // Création et initialisation du menu d'informations
        informations = createInformations();
        super.add(informations); // Affectation du menu à la barre

    }

    /**
     * Méthode qui crée et initialise le menu de contrôles
     * @return objet JMenu représentant le menu de contrôles
     */
    private JMenu createControl(){
        
        // Création du menu de contrôle
        JMenu menu = new JMenu("Contrôles");
        
        // Création de l'item sauvegarder du menu
        sauvegarder = createSauvegarder();

        // Affectation de l'item sauvegarder au menu de contrôles
        menu.add(sauvegarder); 

        // Création de l'item redémarrer du menu
        redemarrer = createRedemarrer();

        // Affectation de l'item rédemarrer au menu de contrôles
        menu.add(redemarrer);

        // Création de l'item quitter du menu
        quitter = createQuitter();

        // Affectation de l'item quitter au menu de contrôles
        menu.add(quitter);

        return menu;
    }

    /**
     * Méthode qui crée et initialise le menu d'aide
     * @return objet JMenu représentant le menu d'aide
     */
    private JMenu createAide(){
        
        // Création du menu d'aide
        JMenu menu = new JMenu("Aide");

        // Création de l'item règles du menu
        regles = createRegles();

        // Affectation de l'item règles au menu d'aide
        menu.add(regles);

        // Création de l'item commandes du menu
        commandes = createCommandes();

        // Affectation de l'item commandes au menu d'aide
        menu.add(commandes);

        return menu;
    }

    /**
     * Méthode qui crée et initialise le menu d'informations
     * @return objet JMenu représentant le menu d'informationss
     */
    private JMenu createInformations(){
        
        // Création du menu d'informations
        JMenu menu = new JMenu("Informations");

        // Création de l'item copyright du menu
        copyright = createCopyright();

        // Affectation de l'item copyright au menu d'informations
        menu.add(copyright);

        return menu;
    }

    /**
     * Méthode qui sert à sauvegarder la partie
     * @return objet JMenuItem
     */
    private JMenuItem createSauvegarder(){
        JMenuItem item = new JMenuItem("Sauvegarder la partie");
        item.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                if(fenetre.getJeu() == null){
                    JOptionPane.showInternalMessageDialog(null, "Vous n'avez pas encore débuté la partie.",
                    "information",
                    JOptionPane.INFORMATION_MESSAGE);
                } else {

                    int i;

                    fenetre.getThread().arret();
                    fenetre.getThread().interrupt();

                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                        fenetre.getJeu().getThreads().get(i).arret(true);
                        fenetre.getJeu().getThreads().get(i).interrupt();
                    }
                    
                    JFileChooser jfc = new JFileChooser();
                    jfc.setDialogTitle("Choissiez l'endroit de la sauvegarde:");
                    jfc.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
                    jfc.addChoosableFileFilter(filter);

                    int returnValue = jfc.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {

                        // object File = jfc.getSelectedFile();
                        try {
                            ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(jfc.getSelectedFile()+".bin"));
                            oos.writeObject(fenetre.getJeu());
                            System.out.println("Ecriture dans le fichier : " + jfc.getSelectedFile());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    fenetre.setThread(new ThreadAffichage(fenetre));
                    fenetre.getThread().start();
                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                        fenetre.getJeu().getThreads().set(i, new ThreadMonstre(fenetre.getJeu(), i));
                        fenetre.getJeu().getThreads().get(i).start();
                    }
                }
            }
            
        });
        return item;
    }

    /**
     * Méthode qui sert à créer l'item redémarrer du menu de contrôles
     * @return objet JMenuItem représentant l'item redémarrer
     */
    private JMenuItem createRedemarrer(){
        JMenuItem item = new JMenuItem("Redémarrer la partie");
        item.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                if(fenetre.getJeu() == null){
                    JOptionPane.showInternalMessageDialog(null, "Vous n'avez pas encore débuté la partie.",
                    "information",
                    JOptionPane.INFORMATION_MESSAGE);
                } else {

                    int i;

                    fenetre.getThread().arret();
                    fenetre.getThread().interrupt();

                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                        fenetre.getJeu().getThreads().get(i).arret(true);
                        fenetre.getJeu().getThreads().get(i).interrupt();
                    }
                    
                    int retour = JOptionPane.showConfirmDialog(fenetre, 
                    "Êtes-vous sûr de recommencer la partie?", 
                    "Attention",
                    JOptionPane.YES_NO_OPTION);

                    if(retour == JOptionPane.YES_OPTION){
                        fenetre.redemarrerPartie();
                    }  else {

                        fenetre.setThread(new ThreadAffichage(fenetre));
                        fenetre.getThread().start();
                        for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                            fenetre.getJeu().getThreads().set(i, new ThreadMonstre(fenetre.getJeu(), i));
                            fenetre.getJeu().getThreads().get(i).start();
                        }
                    }
                }
            }
            
        });
        return item;
    }

    /**
     * Méthode qui sert à créer l'item quitter du menu de contrôles
     * @return objet JMenuItem représentant l'item quitter 
     */
    private JMenuItem createQuitter(){
        JMenuItem item = new JMenuItem("Quitter la partie");
        item.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                if(fenetre.getJeu() == null){
                    JOptionPane.showInternalMessageDialog(null, "Vous n'avez pas encore débuté la partie.",
                    "information",
                    JOptionPane.INFORMATION_MESSAGE);
                } else {

                    int i;

                    fenetre.getThread().arret();
                    fenetre.getThread().interrupt();

                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                        fenetre.getJeu().getThreads().get(i).arret(true);
                        fenetre.getJeu().getThreads().get(i).interrupt();
                    }

                    int retour = JOptionPane.showConfirmDialog(fenetre, 
                    "Êtes-vous sûr de vouloir quitter la partie?", 
                    "Attention",
                    JOptionPane.YES_NO_OPTION);

                    if(retour == JOptionPane.YES_OPTION){
                        fenetre.quitterPartie();
                    } else {
                        fenetre.setThread(new ThreadAffichage(fenetre));
                        fenetre.getThread().start();
                        for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                            fenetre.getJeu().getThreads().set(i, new ThreadMonstre(fenetre.getJeu(), i));
                            fenetre.getJeu().getThreads().get(i).start();
                        }
                    }
                }
            }
            
        });
        return item;
    }

    /**
     * Méthode qui sert à créer l'item règles du menu d'aide
     * @return objet JMenuItem représentant l'item règles 
     */
    private JMenuItem createRegles(){
        JMenuItem item = new JMenuItem("Règles du jeu");
        item.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new FenetreInformation(true);
            }
        });
        return item;
    }

    /**
     * Méthode qui sert à créer l'item commandes du menu d'aide
     * @return objet JMenuItem représentant l'item commandes 
     */
    private JMenuItem createCommandes(){
        JMenuItem item = new JMenuItem("Commandes du jeu");
        item.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new FenetreCommandes(fenetre);
            }
        });
        return item;
    }

    /**
     * Méthode qui sert à créer l'item copyright du menu d'informations
     * @return objet JMenuItem représentant l'item copyright 
     */
    private JMenuItem createCopyright(){
        JMenuItem item = new JMenuItem("Copyright");
        item.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new FenetreInformation(false);
            }
        });
        return item;
    }
}
