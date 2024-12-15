# Gestion des Tâches

## Introduction

**Gestion des Tâches** est une application JavaFX permettant de gérer des tâches personnelles ou professionnelles. Cette application offre une interface intuitive pour ajouter, modifier, supprimer et filtrer les tâches selon différents critères (priorité, échéance, etc.).

L'application est accompagnée d'un fichier CSS pour une meilleure expérience utilisateur grâce à une interface visuellement agréable et bien structurée.

---

## Fonctionnalités

### **Gestion des tâches :**
1. **Ajouter une tâche :**
   - Saisie des informations nécessaires :
     - Nom de la tâche (*TextField*).
     - Description (*TextArea*).
     - Priorité (*ComboBox* : *Basse*, *Moyenne*, *Élevée*).
     - Échéance (*DatePicker*).
   - Validation des champs avant ajout.

2. **Modifier une tâche :**
   - Possibilité de sélectionner une tâche existante dans la liste pour mettre à jour ses informations.

3. **Supprimer une tâche :**
   - Suppression d'une tâche sélectionnée dans la liste.

### **Filtrage des tâches :**
- Affichage et recherche des tâches selon différents critères :
  - **Lister toutes les tâches.**
  - **Filtrer par priorité** (*Basse*, *Moyenne*, *Élevée*).
  - **Filtrer par échéance** (tâches dépassées).
  - **Recherche par nom ou description.**

---

## Structure de l'interface

L'interface principale de l'application est divisée en plusieurs sections :

1. **Formulaire de saisie :**
   - Contient les champs pour ajouter ou modifier une tâche.
   - Inclut un bouton pour chaque action (« Ajouter », « Supprimer », « Modifier », etc.).

2. **Liste des tâches :**
   - Affiche toutes les tâches enregistrées sous forme de liste.
   - Chaque tâche affiche :
     - Le nom.
     - La description.
     - La priorité.
     - L’échéance.

3. **Zone de filtrage :**
   - Permet de rechercher ou de filtrer les tâches par critères spécifiques.

---

## Prérequis

- **Java 17+** : Une version récente de Java est requise pour exécuter l'application.
- **JavaFX** : Assurez-vous que JavaFX est configuré correctement dans votre environnement.
- **IDE compatible** : Par exemple, IntelliJ IDEA ou Eclipse.

---

## Installation

1. Clonez ce dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/gestion-taches.git
   ```

2. Importez le projet dans votre IDE.

3. Assurez-vous que JavaFX est configuré correctement dans les dépendances du projet.

4. Lancez la classe principale : `ma.enset.javafx_basics.GestionTache`.

---

## Style CSS

Un fichier CSS personnalisé (« style.css ») est utilisé pour définir les styles visuels de l'application. Les priorités des tâches sont mises en valeur par des couleurs :

- **Basse** : Style spécifique avec une couleur représentant une priorité faible.
- **Moyenne** : Style neutre.
- **Élevée** : Couleur vive pour indiquer l'urgence.

---

## Auteur

**Hamza El Ghazouani**  
Application développée pour la gestion efficace des tâches en utilisant JavaFX.
