# ACL_daedalus
Ce projet de jeu avec labyrinthe est réalisé dans le cadre du module Analyse et Conception de Logiciels.

Les membres de l'équipe sont: M. RZEPKA Thomas, M. NOEL Victor, M. POIREL Jérémy et M. RACOILLET Maxime)

Le dernier sprint effecté est le sprint numéro 2.

Pour utiliser ce projet, il est recommandé d'utiliser JAVA SE version 17.0.1 ainsi que MAVEN version 3.8.6. 

Pour déployer et exécuté le jeu, vous devez:

    1- vous placer dans le dossier Daedalus avec votre terminal
    2- Utiliser la commande "mvn compile" pour générer les fichiers .class
    3- Utiliser la commande "mvn package" pour générer l'archive du projet

    4- Une fois ceci réalisé, vous devez utiliser la commande ci-dessous pour exécuter le jeu ci-dessous:
        
        java -cp target/Daedalus-3.0.jar start.Main 

Pour exécuter les fichiers de test, vous devez entrer, dans l'ordre, les commandes:

    - mvn compile
    - mvn test
