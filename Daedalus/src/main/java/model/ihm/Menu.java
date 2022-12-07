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
    
    /**
     * La fenetre graphique qui contient le menu
     */
    private FenetreGraphique fenetre;

    /**
     * Le menu de contrôles
     */
    private JMenu control;

    /**
     * Le menu d'aide
     */
    private JMenu aide;

    /**
     * Le menu d'informations
     */
    private JMenu informations;

    /**
     * L'item sauvegarder du menu de contrôles
     */
    private JMenuItem sauvegarder;

    /**
     * L'item redémarrer du menu de contrôles
     */
    private JMenuItem redemarrer;

    /**
     * L'item quitter du menu de contrôles
     */
    private JMenuItem quitter; 

    /**
     *  L'item règles du menu d'aide
     */
    private JMenuItem regles;

    /**
     *  L'item commandes du menu d'aide
     */
    private JMenuItem commandes; 

    /**
     * L'item copyright du menu d'informations
     */
    private JMenuItem copyright; 

    /**
     * Constructeur par défaut d'un objet Menu
     * @param f La fenetre graphique qui comportera le menu
     */
    public Menu(FenetreGraphique f){

        // Appel au constructeur de JMenuBar
        super();

        // Initialisation de l'attribut fenetre
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
     * Méthode qui créer et initisalise l'item pour sauvegarder la partie
     * @return objet JMenuItem
     */
    private JMenuItem createSauvegarder(){

        // Création d'un nouvel item
        JMenuItem item = new JMenuItem("Sauvegarder la partie");

        // Ajout d'un ecouteur sur l'action du bouton de l'item
        item.addActionListener(new ActionListener(){

            /**
             * Méthode qui sera appelée lorsque l'on clique sur le bouton de l'item
             * @parem e objet représentant l'événement qui a déclenché la méthode
             */
            @Override
            public void actionPerformed(ActionEvent e){

                // Si la fenetre n'as pas encore de jeu
                if(fenetre.getJeu() == null){

                    // Affichage d'un message expliquant qu'on ne peu pas encore save
                    JOptionPane.showInternalMessageDialog(null, "Vous n'avez pas encore débuté la partie.",
                    "information",
                    JOptionPane.INFORMATION_MESSAGE);

                } else { // Sinon si la fenetre a un jeu

                    int i;

                    // On arrete le thread de l'affichage pour la mise en pause
                    fenetre.getThread().arret();
                    fenetre.getThread().interrupt();

                    // Pour chacun des montres du jeu
                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){

                        // On arrête le thread associé pour la pause
                        fenetre.getJeu().getThreads().get(i).arret(true);
                        fenetre.getJeu().getThreads().get(i).interrupt();
                    }
                    

                    // Création d'un selecteur de fichier .bin
                    JFileChooser jfc = new JFileChooser();
                    jfc.setDialogTitle("Choisissez l'endroit de la sauvegarde :");
                    jfc.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
                    jfc.addChoosableFileFilter(filter);

                    // Déclenchement du selecteur de fichier
                    int returnValue = jfc.showSaveDialog(null);

                    // Si le fichier choisit est conforme aux type de fichier attendu
                    if (returnValue == JFileChooser.APPROVE_OPTION) {

                        // On séralise l'objet jeu dans le fichier.bin
                        try {
                            ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(jfc.getSelectedFile()+".bin"));
                            oos.writeObject(fenetre.getJeu());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    // On redémarre le thread pour l'affichage
                    fenetre.setThread(new ThreadAffichage(fenetre));
                    fenetre.getThread().start();

                    // On redémarre les threads pour les monstres
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

        // Création d'un nouvel item
        JMenuItem item = new JMenuItem("Redémarrer la partie");

        // Ajout d'un écouteur sur l'action du bouton de l'item
        item.addActionListener(new ActionListener(){

            /**
             * Méthode qui sera appelée lorsque l'on clique sur le bouton de l'item
             * @parem e objet représentant l'événement qui a déclenché la méthode
             */
            @Override
            public void actionPerformed(ActionEvent e){

                // Si la fenetre n'a pas encore de jeu
                if(fenetre.getJeu() == null){
                    // Affichage d'un message expliquant qu'on ne peu pas encore redémarrer
                    JOptionPane.showInternalMessageDialog(null, "Vous n'avez pas encore débuté la partie.",
                    "information",
                    JOptionPane.INFORMATION_MESSAGE);

                } else { // Sinon si la fenetre a un jeu

                    int i;

                    // On arrete le thread de l'affichage pour la mise en pause
                    fenetre.getThread().arret();
                    fenetre.getThread().interrupt();

                    // Pour chacun des monstres du jeu
                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                        // On arrête le thread associé pour la pause
                        fenetre.getJeu().getThreads().get(i).arret(true);
                        fenetre.getJeu().getThreads().get(i).interrupt();
                    }
                    
                    // affichage message de confirmation êtes vous sûr
                    int retour = JOptionPane.showConfirmDialog(fenetre, 
                    "Êtes-vous sûr de recommencer la partie?", 
                    "Attention",
                    JOptionPane.YES_NO_OPTION);


                    // Si l'utilisateur clique sur oui
                    if(retour == JOptionPane.YES_OPTION){
                        // On redémarre la partie
                        fenetre.redemarrerPartie();
                    }  else { // Sinon

                        // On recrée un nouveau thread pour l'affichage et on le démarre
                        fenetre.setThread(new ThreadAffichage(fenetre));
                        fenetre.getThread().start();

                        // On recrée un nouveau thread pour chacun des monstres et on le démarre
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

        // Création d'un nouvel item
        JMenuItem item = new JMenuItem("Quitter la partie");

        // Ajout d'un écouteur sur l'action du bouton de l'item
        item.addActionListener(new ActionListener(){

            /**
             * Méthode qui sera appelée lorsque l'on clique sur le bouton de l'item
             * @parem e objet représentant l'événement qui a déclenché la méthode
             */
            @Override
            public void actionPerformed(ActionEvent e){

                // Si la fenetre n'a pas encore de jeu
                if(fenetre.getJeu() == null){

                    // Affichage d'un message expliquant qu'on ne peu pas encore redémarrer
                    JOptionPane.showInternalMessageDialog(null, "Vous n'avez pas encore débuté la partie.",
                    "information",
                    JOptionPane.INFORMATION_MESSAGE);

                } else { // Sinon si la fenetre a un jeu

                    int i;

                    // On arrete le thread de l'affichage pour la mise en pause
                    fenetre.getThread().arret();
                    fenetre.getThread().interrupt();

                    // Pour chacun des monstres du jeu
                    for(i=0; i<fenetre.getJeu().getEntites().size()-1; i++){
                        // On arrête le thread associé pour la pause
                        fenetre.getJeu().getThreads().get(i).arret(true);
                        fenetre.getJeu().getThreads().get(i).interrupt();
                    }

                    // Affichage message de confirmation êtes-vous sûr
                    int retour = JOptionPane.showConfirmDialog(fenetre, 
                    "Êtes-vous sûr de vouloir quitter la partie?", 
                    "Attention",
                    JOptionPane.YES_NO_OPTION);

                    // Si l'utitlisateur clique sur oui
                    if(retour == JOptionPane.YES_OPTION){
                        // On quitte la partie
                        fenetre.quitterPartie();
                    } else { // Sinon

                        // On recrée un nouveau Thread pour l'affichage et on le démarre
                        fenetre.setThread(new ThreadAffichage(fenetre));
                        fenetre.getThread().start();

                        // On recrée un nouveau thread pour chacun des monstres et on le démarre
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

        // Création d'un nouvel item
        JMenuItem item = new JMenuItem("Règles du jeu");

        // Ajout d'un écouteur sur l'action du bouton de l'item
        item.addActionListener(new ActionListener(){
            /**
             * Méthode qui sera appelée lorsque l'on clique sur le bouton de l'item
             * @parem e objet représentant l'événement qui a déclenché la méthode
             */
            @Override
            public void actionPerformed(ActionEvent e){
                // Création et spawn de la nouvelle fenetre contenant les règles
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

        // Création d'un nouvel item
        JMenuItem item = new JMenuItem("Commandes du jeu");

        // Ajout d'un écouteur sur l'action du bouton de l'item
        item.addActionListener(new ActionListener(){
            /**
             * Méthode qui sera appelée lorsque l'on clique sur le bouton de l'item
             * @parem e objet représentant l'événement qui a déclenché la méthode
             */
            @Override
            public void actionPerformed(ActionEvent e){

                // Création et spawn de la fenetre pour la gestion des touches
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
        
        // Création d'un nouvel item
        JMenuItem item = new JMenuItem("Copyright");

        // Ajout d'un écouteur sur l'action du bouton de l'item
        item.addActionListener(new ActionListener(){
            /**
             * Méthode qui sera appelée lorsque l'on clique sur le bouton de l'item
             * @parem e objet représentant l'événement qui a déclenché la méthode
             */
            @Override
            public void actionPerformed(ActionEvent e){
                // Création et spawn de la fenetre contenant le copyright
                new FenetreInformation(false);
            }
        });
        return item;
    }
}
