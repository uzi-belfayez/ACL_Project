# SPRINT3

## Sprint 3 - Objectifs

- [x]  Gestion du map et ajout des obstacles : Rayen (3 jours)
L’objectif est d’ajouter des obstacles tels que des murs, des rochers et des buissons dans la carte du jeu. Ces éléments doivent influencer la navigation du joueur et des monstres en bloquant leur passage. Une mise à jour du système de collision est nécessaire pour que ces obstacles soient pris en compte dans les mouvements. Les obstacles seront placés de manière stratégique pour enrichir le gameplay et créer des environnements interactifs.
- [x]  Gestion des collisions entre monstres et personnage: Hedi (3 jours)
Cette tâche vise à gérer les interactions entre le joueur et les monstres lors des collisions. Si un monstre touche le joueur, ce dernier perd des points de vie, ce qui est reflété par une mise à jour visuelle des cœurs affichés. Cela nécessite une amélioration du moteur de collision pour détecter ces interactions et une méthode pour appliquer les dégâts au joueur.
- [x]  Gestion du changement de niveau : Emna et Amina (3 jours)
Le jeu introduira des mécanismes pour passer d’un niveau à l’autre. Les cartes comporteront des points de sortie, activés par la condition de la collecte de toutes les clés. Chaque niveau aura ses propres ennemis, obstacles et objets pour diversifier le gameplay.

## Sprint 3 - Rétrospective:
### Ce qui s'est bien passé:
- Implémentation réussie des obstacles interactifs sur la carte.
- Développement des collisions monstre-joueur et celles avec les clés.
- Affichage sur le terminale du jeu des bonnes réactions.
- On a un jeu fonctionnel a la fin de ce sprint même si on n'a pas tous fait. 
### Problèmes rencontrés:
- Traitement des ressources graphiques pour les adapter au jeu.
- On trouve des bugs dans le chargement des cartes.
- la collision avec les ghosts n'est pas détecté.

### solution adoptée au problème de la perte des commits :
- On a réussi à récuperer la dernière version fonctionnelle de notre projet.
### Actions à entreprendre:
- Mise en place d’un système de tests internes avec un feedback des membres de l’équipe.
- Debugging approfondi et utilisation d’une architecture modulaire pour le code des cartes.

# Sprint 3 - Review
Au cours du Sprint 3, l'équipe a réussi à atteindre les jalons suivants :
- Intégration réussie des obstacles et amélioration de la logique de collision pour les rendre interactifs.
- Ajouter la vie du héro dont la logique sera développée dans les sprints prochains.
- Implémentation partiellement réussie de la logique de détection de collision.