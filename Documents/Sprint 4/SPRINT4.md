# SPRINT 4  

## Sprint 4 - Objectifs  

### 1. Actions du joueur :  
- **Animation d’attaque :**  
  - [x] Implémenter une animation d’attaque lorsque le joueur appuie sur une touche d’action (coup d'épée et fire ball avec les touches espace pour epée et E pour fire ball). Hedi(3 jours)

### 2. Attaques des monstres :  
- **Comportements spécifiques :**  
  - [x] Développer des patterns d’attaque pour les monstres par des armes. Amina (2 jours)  
- **Résultat attendu :**  
  - Offrir des affrontements captivants où le joueur doit anticiper les attaques ennemies et adapter ses mouvements et actions.  

### 3. Gestion du changement de niveau :  
- **Points de sortie et conditions d’accès :**  
  - [x] Accéder aux portes uniquement après avoir récupéré deux clés pour passer d'un niveau à un autre. Emna (2 jours)
- **Chargement du prochain niveau :**  
  - [x] Intégrer une fonctionnalité de chargement dynamique pour de nouvelles cartes, ennemis, et défis. Hedi (3 jours)
  
- **Résultat attendu :**  
  - Une expérience enrichie où chaque niveau propose un environnement unique.
  

### 4. Gestion des vies du héros :  
  - [x] Décrémenter les vies du héros en cas de collision avec un monstre.  
- **Problèmes identifiés :**  
  - Synchronisation entre la détection de collision et la réduction des vies.  
  - Affichage du message "Game Over" dans l’interface (actuellement limité au terminal) et arrêt correct du jeu.  

### 5. Ajout d'un diagramme de séquence :
  - [x] Ajouter un diagramme de séquence. Rayen (1 jour)

---

## Sprint 4 - Rétrospective :  

### Ce qui s'est bien passé :  
- Les animations et les mécaniques d’attaque offrent un gameplay plus immersif.  
- Les cycles d’agression des monstres apportent une nouvelle dimension stratégique.  
- La mécanique de changement de niveau enrichit l’expérience de progression.  

### Problèmes rencontrés :  
- Défis techniques liés à la synchronisation des actions (collisions, gestion des vies).  
- Problèmes dans la gestion des transitions entre les niveaux (décalage graphique).  
- Complexité accrue dans l'organisation des classes et des comportements.  

### Solutions adoptées :  
- Révision du système de détection de collision pour synchroniser les actions et éviter les incohérences.  
- Tests approfondis pour identifier et corriger les erreurs dans la logique de transition.  

### Actions à entreprendre :  
- Améliorer l'architecture des classes pour simplifier l'ajout de comportements futurs.  
- Réaliser des tests unitaires pour garantir le bon fonctionnement des nouvelles fonctionnalités.  
- Planifier des sessions de synchronisation régulières pour renforcer la collaboration dans l'équipe.  

---

## Sprint 4 - Review :  
Au cours de ce sprint, l'équipe a réussi à atteindre les jalons suivants :  
- Implémentation de mécaniques d'attaque pour le héros et les monstres (épées et fire balls).  
- Ajout d’une logique de changement de niveau avec des transitions visuelles.  
- Création de combats stratégiques et immersifs, enrichissant l'expérience globale.  
