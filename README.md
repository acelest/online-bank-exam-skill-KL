# Online Banking System

Une application bancaire en ligne développée avec Spring Boot qui permet aux utilisateurs de gérer leurs comptes, effectuer des transactions et suivre leurs finances.

## Fonctionnalités

- Inscription et authentification des utilisateurs avec JWT
- Gestion de comptes bancaires
- Transferts entre comptes
- Historique des transactions
- Gestion de profil utilisateur

## Prérequis

- Java 8 ou supérieur
- Maven
- MySQL

## Installation

1. Clonez le dépôt :

```
git clone https://github.com/votre-username/online-banking.git
cd online-banking
```

2. Configurez la base de données MySQL dans `src/main/resources/application.properties`

3. Compilez et installez le projet avec Maven :

```
mvn clean install
```

## Lancement de l'application

### Développement

Pour lancer l'application en mode développement :

```
mvn spring-boot:run
```

### Production

Pour générer le fichier JAR et lancer l'application :

```
mvn package
java -jar target/userFront-0.0.1-SNAPSHOT.jar
```

L'application sera accessible à l'adresse `http://localhost:8080`.

## Structure du projet

```
online-banking/
├── src/
│   ├── main/
│   │   ├── java/com/userfront/
│   │   │   ├── controller/     # Contrôleurs REST
│   │   │   ├── dao/            # Interfaces DAO (Data Access Object)
│   │   │   ├── domain/         # Entités/modèles
│   │   │   ├── security/       # Configuration de sécurité JWT
│   │   │   ├── service/        # Services métier
│   │   │   └── UserFrontApplication.java  # Point d'entrée
│   │   └── resources/
│   │       ├── static/         # Ressources statiques (CSS, JS)
│   │       ├── templates/      # Templates Thymeleaf
│   │       └── application.properties  # Configuration
│   └── test/                  # Tests unitaires et d'intégration
├── pom.xml                    # Dépendances et configuration Maven
└── README.md
```

## Configuration

Les principales configurations se trouvent dans le fichier `src/main/resources/application.properties` :

- Configuration de la base de données
- Propriétés JWT pour l'authentification
- Configuration CORS
- Port du serveur (8080 par défaut)

## API Documentation

L'API est documentée avec Swagger et accessible à l'adresse suivante après le démarrage de l'application :

```
http://localhost:8080/swagger-ui.html
```

### Endpoints principaux

- **/signup** - Inscription d'un nouvel utilisateur
- **/api/login** - Authentification d'un utilisateur (JWT)
- **/userFront** - Dashboard utilisateur

## Technologies utilisées

- Spring Boot 2.5.0
- Spring Security avec JWT
- Spring Data JPA
- Hibernate
- MySQL
- Thymeleaf
- Lombok
- Swagger pour la documentation API

## Tests

Pour exécuter les tests :

```
mvn test
```

## Contribution

Les contributions sont les bienvenues! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.

## Licence

Ce projet est sous licence MIT.
