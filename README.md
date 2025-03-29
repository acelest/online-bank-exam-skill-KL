# Online Banking System

Une application bancaire en ligne développée avec Node.js et Express qui permet aux utilisateurs de gérer leurs comptes, effectuer des transactions et suivre leurs finances.

## Fonctionnalités

- Inscription et authentification des utilisateurs
- Gestion de comptes bancaires
- Transferts entre comptes
- Historique des transactions
- Gestion de profil utilisateur

## Prérequis

- Node.js (v14.0.0 ou supérieur)
- MongoDB
- NPM

## Installation

1. Clonez le dépôt :

```
git clone https://github.com/votre-username/online-banking.git
cd online-banking
```

2. Installez les dépendances :

```
npm install
```

3. Créez un fichier `.env` à la racine du projet avec les variables suivantes :

```
PORT=3000
MONGODB_URI=mongodb://localhost:27017/online-banking
JWT_SECRET=votre_secret_jwt
```

## Lancement de l'application

### Développement

Pour lancer l'application en mode développement avec rechargement automatique :

```
npm run dev
```

### Production

Pour lancer l'application en mode production :

```
npm start
```

L'application sera accessible à l'adresse `http://localhost:3000` (ou le port défini dans votre fichier .env).

## Structure du projet

```
online-banking/
├── config/           # Configuration de l'application
├── controllers/      # Contrôleurs pour gérer les requêtes
├── middleware/       # Middleware personnalisé (authentification, etc.)
├── models/           # Modèles de données MongoDB
├── routes/           # Routes de l'API
├── services/         # Services métier
├── utils/            # Utilitaires
├── .env              # Variables d'environnement
├── app.js            # Point d'entrée de l'application
└── package.json      # Dépendances et scripts
```

## API Documentation

### Endpoints principaux

- **POST /api/auth/register** - Inscription d'un nouvel utilisateur
- **POST /api/auth/login** - Connexion utilisateur
- **GET /api/accounts** - Récupérer les comptes de l'utilisateur
- **POST /api/transactions** - Créer une nouvelle transaction
- **GET /api/transactions** - Récupérer l'historique des transactions

## Tests

Pour exécuter les tests :

```
npm test
```

## Contribution

Les contributions sont les bienvenues! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.

## Licence

Ce projet est sous licence MIT.
