# GitHub Copilot Rules pour le projet Online Banking avec OTP Security

## Règles générales de code

- Écrire le code et les commentaires en français
- Favoriser des solutions simples et efficaces
- Éviter les abstractions excessives ou non nécessaires
- Suivre les principes SOLID mais sans complexité inutile

## Architecture et structure

- Respecter la structure MVC: Controller, Service, Repository
- Utiliser les packages suivants:
  - `com.banque.controller` - Pour les contrôleurs REST
  - `com.banque.service` - Pour la logique métier
  - `com.banque.repository` - Pour l'accès aux données
  - `com.banque.model` - Pour les entités JPA
  - `com.banque.config` - Pour les configurations
  - `com.banque.dto` - Pour les objets de transfert de données
  - `com.banque.security` - Pour les classes liées à la sécurité
  - `com.banque.exception` - Pour la gestion des exceptions

## Sécurité

- Utiliser Spring Security basique (pas de JWT)
- Endpoints d'inscription et connexion ouverts
- Endpoints de transactions protégés par authentification
- Implémenter OTP pour les opérations sensibles
- Utiliser BCrypt pour le hashage des mots de passe

## Base de données

- Utiliser MySQL exclusivement
- Nommer les tables en minuscules et au pluriel
- Définir les deux types de comptes: COURANT, EPARGNE
- Utiliser des clés étrangères pour les relations entre entités

## OTP

- Générer des codes à 6 chiffres pour l'OTP
- Définir une expiration de 5 minutes pour les OTP
- Envoyer les OTP par email (pas de SMS)
- Stocker les OTP de manière sécurisée

## API REST

- Suivre les conventions REST (GET, POST, PUT, DELETE)
- Routes d'API à mettre en français
- Formatage de réponse standard:

  ```json
  {
    "statut": "SUCCES/ERREUR",
    "message": "Description",
    "data": {}
  }
  ```

## Style de code

- Utiliser Lombok pour réduire le boilerplate
- Préférer les classes finales quand c'est possible
- Utiliser des DTOs pour les transferts de données
- Utiliser des validations pour les entrées utilisateur

## Ne pas implémenter

- Pas de JWT ni d'OAuth
- Pas de sessions complexes
- Pas de solutions payantes pour l'OTP
- Pas de tests unitaires pour le MVP initial

## Fonctionnalités principales

- Authentification utilisateur
- Gestion des comptes (courant, épargne)
- Transactions bancaires avec OTP
- Envoi d'emails pour les OTP
- Consultation d'historique des transactions
