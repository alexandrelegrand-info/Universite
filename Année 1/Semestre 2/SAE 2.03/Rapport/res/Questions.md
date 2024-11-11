# Questions : {-}

**Que signifie “64-bit” dans “Debian 64-bit” ?**  

[Le 64-bit correspond à l'architecture du processeur, cela permet à ces systèmes d’utiliser plus de RAM et donc d’être plus performant](https://www.debian.org/ports/amd64/index.fr.html)


**Quel est le nom du fichier XML contenant la configuration de votre machine ?**  

Sur notre machine, le chemin vers le fichier .xml : 

`/usr/local/virtual_machine/infoetu/prenom.nom.etu/nomdelamachine.vbox-prev`


**Qu’est-ce qu’un fichier iso bootable ?**  

C'est un fichier sur lequel on met le système d’exploitation pour la machine.


**Qu’est-ce que MATE ? GNOME ?**  

Ce sont des [interfaces graphiques pour Debian](https://mate-desktop.org/fr/).


**Qu’est-ce qu’un serveur web ?**  

Un serveur web est un logiciel qui permet de traiter les requêtes http pour afficher des pages html.


**Qu’est-ce qu’un serveur ssh ?**  

Un serveur ssh est un logiciel qui permet de l’accès sécurisé à un système distant ou à une machine via [le protocole ssh](https://www.cloudflare.com/fr-fr/learning/access-management/what-is-ssh/).	


**Qu’est-ce qu’un serveur mandataire ?**  

[Un serveur mandataire ou un proxy](https://help.gnome.org/users/gnome-help/stable/net-proxy.html.fr), est un serveur intermédiaire qui agit comme un intermédiaire entre les utilisateurs et les serveurs auxquels ils accèdent. Il reçoit les requêtes du navigateur pour récupérer les pages Web demandées avec leurs éléments afin de décider s'il doit vous les transmettre ou non (selon les règles du pays par exemple).
	

**Quelle est la version du noyau Linux utilisé par votre VM ?**  

En tapant la commande uname -a dans la machine virtuel nous obtenons le noyau suivant: 6.5.0-18 generic.
	

**À quoi servent les suppléments invités ? Donner 2 principales raisons de les installer ?**  

Ils permettent une meilleure intégration du bureau et des meilleures performances graphiques par exemple.
	

**À quoi sert la commande mount (dans notre cas de figure et dans le cas général) ?**

Elle permet de rendre accessible un système de fichier à partir d’un emplacement particulier dans l’arborescence de fichier.


**Qu'est ce que le projet Debian ? D'où vient le nom Debian ?**

Le nom tire son origine des prénoms du créateur de Debian, Ian Murdock, et de son épouse, Debra comme expliqué [sur la page racontant l'histoire de Debian](https://www.debian.org/doc/manuals/project-history/intro.fr.html#:~:text=La%20prononciation%20officielle%20de%20Debian,et%20de%20son%20%C3%A9pouse%2C%20Debra)


**Il existe 3 durées de prise en charge (support) de ces versions : la durée minimale, la durée en support long terme (LTS) et la durée en support long terme étendue (ELTS). Quelles sont les durées de ces prises en charge ?**   

La version Stable : Il s'agit de la version principale de Debian, elle est stable et fiable, les mise à jours de celle-ci se poursuivront 5 ans après sa sortie.

La version Testing : Testing est une version intermédiaire qui contient des packages plus récents que la branche Stable. Cependant elle est beaucoup moins stable que la version Stable. Une fois les mise à jour de la version testing sont arrivées à échéance elle devient alors la version Stable.

La version Unstable : C’est la version de développement de Debian. Elle bénéficie des dernières versions des logiciels mais elle ne deviendra jamais la version stable.

[Toutes les explications sur la page Debian LTS](https://wiki.debian.org/fr/LTS)


**Pendant combien de temps les mises à jour de sécurité seront-elles fournies ?**  

Les mises à jour de sécurité sont disponibles 5 ans après la dernière version de la [version Stable](https://www.debian.org/releases/index.fr.html). 
	

**Combien de versions au minimum sont activement maintenues par Debian ? Donnez leur nom générique (= les types de distribution) ?**
	
La version stable, la version odlstable et la version testing sont les 3 versions maintenues par Debian comme on peut le voir [sur la page des releases Debian](https://www.debian.org/releases/index.fr.html).

**Chaque distribution majeure possède un nom de code différent. Par exemple, la version majeure actuelle (Debian 12) se nomme bookworm. D'où viennent les noms de code données aux distributions ?**

Le nom des distributions de Debian vient du nom de chaque personnage de Toy-Story comme on peut le voir [sur la page des releases avec le nom des versions](https://www.debian.org/releases/index.fr.html).


# Problèmes : {-}

## Problèmes d'installation manuelle :

Lors de la création d’une machine virtuelle, à la première page, il faut cocher une case car le fichier ISO possède une version déjà configurée par défaut et ne propose pas de modifier cette installation par défaut.


## Problèmes durant l'installation automatisé :

-  Difficultés à trouver l'endroit pour installer l'environnement mate, nous avons essayé avec la commande `log_command_in_target apt-get install mate-desktop` dans le `vboxpostinstall.sh` mais ça ne fonctionnait pas. Nous avons ensuite trouvé un forum expliquant qu'il fallait l'inscrire dans le `preseed-fr.cfg` avec la commande `tasksel tasksel/first mate-desktop`. Nous avons ensuite fait le lien entre cette commande et la commande déjà présente `tasksel tasksel/first multiselect standard ssh-server`.

## Problèmes durant l'installation et la mise en service de Gitea :

- Nous avons eu des difficultés à comprendre ce que faisaient les commandes présentes sur le site de Gitea pour l'installation. Par exemple, nous n'avions pas compris que ces commandes créaient un utilisateur Git.