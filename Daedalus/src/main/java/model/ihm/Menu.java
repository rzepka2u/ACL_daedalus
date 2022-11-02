package model.ihm;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar{
    
    private JMenu control; // Le menu de contrôles
    private JMenu aide; // Le menu d'aide
    private JMenu informations; // Le menu d'informations
    private JMenuItem redemarrer; // L'item redémarrer du menu de contrôles
    private JMenuItem quitter; // L'item quitter du menu de contrôles
    private JMenuItem regles; // L'item règles du menu d'aide
    private JMenuItem commandes; // L'item commandes du menu d'aide
    private JMenuItem copyright; // L'item copyright du menu d'informations

    /**
     * Constructeur par défaut d'un objet Menu
     */
    public Menu(){

        // Appel au constructeur de JMenuBar
        super();

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
     * Méthode qui sert à créer l'item redémarrer du menu de contrôles
     * @return objet JMenuItem représentant l'item redémarrer
     */
    private JMenuItem createRedemarrer(){
        JMenuItem item = new JMenuItem("Redémarrer la partie");
        return item;
    }

    /**
     * Méthode qui sert à créer l'item quitter du menu de contrôles
     * @return objet JMenuItem représentant l'item quitter 
     */
    private JMenuItem createQuitter(){
        JMenuItem item = new JMenuItem("Quitter la partie");
        return item;
    }

    /**
     * Méthode qui sert à créer l'item règles du menu d'aide
     * @return objet JMenuItem représentant l'item règles 
     */
    private JMenuItem createRegles(){
        JMenuItem item = new JMenuItem("Règles du jeu");
        return item;
    }

    /**
     * Méthode qui sert à créer l'item commandes du menu d'aide
     * @return objet JMenuItem représentant l'item commandes 
     */
    private JMenuItem createCommandes(){
        JMenuItem item = new JMenuItem("Commandes du jeu");
        return item;
    }

    /**
     * Méthode qui sert à créer l'item copyright du menu d'informations
     * @return objet JMenuItem représentant l'item copyright 
     */
    private JMenuItem createCopyright(){
        JMenuItem item = new JMenuItem("Copyright");
        return item;
    }
}
