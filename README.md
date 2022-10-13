# ACL_daedalus
Projet ACL Labyrinthe (RZEPKA Thomas, NOEL Victor, POIREL Jérémy, RACOILLET Maxime)

Dernier sprint effecté : sprint 0

Pour utiliser le jeu, vous devez:

    1- Se placer dans le dossier Daedalus avec votre terminal
    2- Utiliser la commande mvn compile pour générer les fichiers .class
    3- Utiliser la commande mvn package pour générer l'archive du projet

    4- Une fois ceci réalisé, vous devez utiliser au choix une des deux commandes ci-dessous:

        - Pour générer un jeu avec un labyrinthe défini dans un fichier .txt: 
            
            java -cp target/Daedalus-1.0.jar start.Main [chaîne de caractère contenant le lien du fichier (relatif ou absolu)]

            Un fichier préfabriqué est déjà utilisable avec le lien "src/main/ressources/niveaux/niveau0.txt".

        - Pour générer un jeu avec un labyrinthe défini par défaut (toujours le même):

            java -cp target/Daedalus-1.0.jar start.Main 

Pour exécuter les fichiers de test, vous devez entrer, dans l'ordre, les commandes:

    - mvn compile
    - mvn test
