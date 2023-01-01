# diamant_cpoo_dactylogame

## Compilation et execution
- 'mvn clean javafx:run' dans le dossier dactylogame.

## Utilisation du programme
Après avoir utilisé la commande ci-dessus, une fenêtre s'ouvre.
Plusieurs choix s'offre a nous : 

* Choisir entre 2 modes de jeu: Solo (Normal) et Solo (Jeu). Le multijoueur n'est pas implementé donc toutes les options liés a ce mode de jeu ne sont pas utilisables.
* On peut modifier les options par défaut :
    * Pour le __mode Solo (Normal)__ : Le choix du temps disponible et nombre de mots dans la file.
    * Pour le __mode Solo (Jeu)__ : Le choix du nombre de PV et de la longueur de la file avant auto-validation du mot (et donc perte de PV).

Il suffira ensuite de cliquer sur le bouton "__Jouer__" pour lancer le mode de jeu.
La fenêtre principale du jeu s'ouvre alors.

Le __mode solo (Normal)__ est le mode de jeu par défaut.
Le joueur peut alors appuyer sur le bouton (label) au milieu de l'ecran pour commencer a taper.
Il aura alors le temps choisi dans les options (60 secondes par défaut) pour taper le plus de mots possible.
Lorsque le temps est écoulé, le score est affiché et le joueur peut alors choisir de rejouer, de retourner au menu ou de quitter le jeu.

Le __mode solo (Jeu)__ est un mode de jeu ou le joueur doit taper le plus de mots possible avant de perdre tous ses PV.
Le joueur a 50 PV par défaut, et perd 1 PV a chaque erreur de frappe dans le mot qu'il a validé.
Si le joueur valide un mot bonus sans erreur, il gagne le nombre de PV correspondant a la longueur du mot bonus.
Le niveau de difficulté augmente a chaque fois que le joueur tape 100 mots correctement. La vitesse d'arrivée des mots dans la file augmentera alors.
Lorsque le joueur perd tous ses PV, le score est affiché et le joueur peut alors choisir de rejouer, de retourner au menu ou de quitter le jeu.

Les mots s'affiche en vert tant que le joueur n'a pas fait de faute de frappe. En cas d'erreur, le mot s'affiche en rouge.
Les mots bonus dans le mode solo (Jeu) sont affichés en bleu.
Le caractère a taper est affiché grâce a un soulignement en jaune en dessous du mot.

##### Dependances utilisées
- JavaFX : pour l'interface graphique
- RichTextFX : pour utiliser des styles CSS dans les labels
- JUnit : pour les tests unitaires
- LoremIpsum : pour générer des mots aléatoires en lorem ipsum.

## Utilisation des tests

- 'mvn clean test' dans le dossier dactylogame.
- Les tests ne sont pas complets, le seul test unitaire est celui de la classe 'App'.