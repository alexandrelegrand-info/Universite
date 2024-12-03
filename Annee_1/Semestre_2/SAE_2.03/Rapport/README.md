## Rapport intermédiaire pour la SAE 2.03

> LEGRAND Alexandre

> ERTAM Ylies

> JACQUEMELLE Vincent

## Prérequis à la compilation 

Afin de compiler et de pouvoir lire correctement le rapport, il est demandé d'installer pandoc sur votre machine Linux.


## Pour compiler le Rapport au format MarkDown et l'exporter en .pdf et en .html :

- **En vous plaçant au préalable dans le dossier Rapport :**

   - Vous pouvez exécuter le compile.sh avec la commande suivante : `compile.sh`
   
   **OU**

   - Vous pouvez exécuter la commande suivante dans le terminal pour obtenir le format PDF : `pandoc --toc -N -V geometry:margin=1in -f markdown -t pdf res/meta.yaml Rapport.md res/Questions.md -o Rapport.pdf`


   - Vous pouvez exécuter la commande suivante dans le terminal pour obtenir le format HTML : `pandoc --toc -N -f markdown -t html5 -s res/meta.yaml Rapport.md res/Questions.md -c res/Rapport.css -o Rapport.html`

## Différentes options dans les commandes pandoc

Si vous souhaitez supprimer la numérotation automatique des sections sur certaines sections, vous pouvez ajouter `{-}` à la fin pour éviter cette numérotation.

**--toc** : Génère la table des matières.
**-N** : Utilise la numérotation automatique des sections.
**-V geometry:margin=1in** : Change la géométrie du PDF afin qu'il soit moins centré.
**-s** : Permet de mettre toutes les données dans le HTML.
**-c** : Permet l'utilisation d'un fichier .css.