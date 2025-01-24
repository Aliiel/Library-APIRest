# Library-APIRest
# 📝 TP : Gestion d'une Bibliothèque en Spring Boot

---

## 🎯 1. Objectifs pédagogiques

L'objectif de ce TP est de permettre aux étudiants de mettre en pratique l'ensemble des couches d'une application **Spring Boot**, incluant :

- ✅ La modélisation des entités JPA et la gestion des relations.
- ✅ La création des repositories avec Spring Data JPA.
- ✅ L'implémentation des services pour gérer la logique métier.
- ✅ La création des controllers REST pour exposer des endpoints API.
- ✅ La validation des données et les tests via Postman.
- ✅ La gestion des exceptions personnalisées.

---

## 📌 2. Cahier des charges

Nous allons développer une application de gestion d'une bibliothèque en ligne permettant :

### 📚 Gestion des utilisateurs
- Ajouter un utilisateur.
- Lister tous les utilisateurs.

### 📖 Gestion des livres
- Ajouter, modifier, supprimer un livre.
- Lister les livres disponibles ou empruntés.

### 🔄 Gestion des emprunts
- Un utilisateur peut emprunter un livre.
- Un utilisateur peut rendre un livre.
- Afficher les emprunts en cours pour un utilisateur.
- Un utilisateur ne peut pas emprunter plus de 3 livres en même temps.

---

## 🛠️ 3. Modèle de données

Vous devez créer les entités suivantes :

### 🧑‍💻 Entité `User`
- `id` (Long, clé primaire)
- `name` (String)
- `email` (String, unique)

### 📗 Entité `Book`
- `id` (Long, clé primaire)
- `title` (String)
- `author` (String)
- `isAvailable` (boolean, par défaut à true)

### 🔄 Entité `Borrowing`
- `id` (Long, clé primaire)
- `user` (Relation ManyToOne avec `User`)
- `book` (Relation ManyToOne avec `Book`)
- `borrowDate` (LocalDate)
- `returnDate` (LocalDate, nullable)

---

## 🚀 4. Travail demandé

1. 🔹 **Étape 1** : Création des entités JPA avec leurs annotations et relations.
2. 🔹 **Étape 2** : Création des repositories pour chaque entité en utilisant **JpaRepository**.
3. 🔹 **Étape 3** : Implémentation des services pour gérer les opérations métier (ajout, suppression, récupération, règles d'emprunt).
4. 🔹 **Étape 4** : Création des controllers REST permettant d'exposer les endpoints nécessaires.
5. 🔹 **Étape 5** : Gestion des exceptions avec des messages d'erreur clairs.
6. 🔹 **Étape 6** : Test des endpoints avec **Postman** en effectuant des emprunts et retours de livres.
7. 🔹 **Étape 7** : Ajout des validations pour éviter les erreurs d'entrée utilisateur.

---

## ⚠️ 5. Gestion des exceptions

Vous devez gérer les erreurs courantes avec des exceptions personnalisées :

- `UserNotFoundException` : Si l'utilisateur n'existe pas.
- `BookNotFoundException` : Si le livre n'existe pas.
- `BookUnavailableException` : Si un utilisateur tente d'emprunter un livre déjà emprunté.
- `BorrowingLimitExceededException` : Si un utilisateur essaie d'emprunter plus de 3 livres.

### Exemple d'exception personnalisée :

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException { 
    public UserNotFoundException(String message) { 
        super(message); 
    } 
}
```

---

## 🚀 6. Instructions supplémentaires
- ✔️ Respectez les bonnes pratiques de développement Spring Boot.
- ✔️ Utilisez @Service pour la logique métier et @RestController pour les API.
- ✔️ Implémentez des contrôleurs REST pour gérer les requêtes HTTP.
- ✔️ Ajoutez des exceptions personnalisées pour gérer les erreurs courantes.
- ✔️ Testez vos endpoints avec des requêtes POST, GET et DELETE dans Postman.

---
