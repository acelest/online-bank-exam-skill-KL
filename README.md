# Online Banking System

Une application bancaire en ligne développée avec Spring Boot qui permet aux utilisateurs de gérer leurs comptes, effectuer des transactions et suivre leurs finances.

## Fonctionnalités

- Inscription et authentification des utilisateurs avec JWT
- Gestion de comptes bancaires
- Transferts entre comptes
- Historique des transactions
- Gestion de profil utilisateur

## Captures d'écran

Voici quelques captures d'écran de l'application :

![Capture d'écran 1](Screenshot%20from%202025-03-29%2003-30-35.png)
_Vue principale du dashboard utilisateur_

![Capture d'écran 2](Screenshot%20from%202025-03-29%2008-36-44.png)
_Interface de gestion des comptes_

![Capture d'écran 3](Screenshot%20from%202025-03-29%2012-22-04.png)
_Écran de transactions_

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

Er diagram

![er diagram](https://user-images.githubusercontent.com/34470526/37703339-8e85fcae-2d1f-11e8-900f-94cb2046d97f.png)

Online banking system detail diagram

![online banking system detail diagram](https://user-images.githubusercontent.com/34470526/37703353-999023fe-2d1f-11e8-96f6-db40724c5d14.png)

## Tests

Pour exécuter les tests :

```
mvn test
```

## Contribution

Les contributions sont les bienvenues! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.

## Licence

Ce projet est sous licence MIT.
