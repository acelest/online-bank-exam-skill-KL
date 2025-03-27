
# Online Banking System

## 1. Project Requirements and Modules

### User Modules
- **ADMINISTRATOR**: User management and global operations
- **BANK**: Adding, managing banks and managers
- **CLIENT**: Managing bank clients, activation/deactivation, etc.

### Functional Modules
- **Authentication**: Registration and login (Spring Security + JWT)
- **Account Management**: Creating, locking, viewing accounts
- **Bank Transactions**: Deposit, withdrawal, transfer, viewing history
- **OTP**: Validation during sensitive transactions via email (Mailtrap/Testmail)
- **Swagger**: REST API documentation to facilitate development and validation

## 2. Database Design and Planning

### Main Entities
- **User**: For users (admin, bank, client)
- **Bank**: To represent banks
- **BankAccount**: To manage clients' bank accounts
- **Transaction**: For deposit, withdrawal, transfer operations
- **OTP (optional)**: To store or track sent OTPs (depending on your implementation logic)

### Entity Relationships
- A **User** can have multiple **BankAccounts**
- A **Bank** can have multiple **BankAccounts** and **BankManagers**
- A **BankAccount** will have multiple **Transactions**

### Prompt Copilot Suggested:
"Write a prompt to generate a simple JPA entity for the user with fields email, password, and role."

## 3. Initial Configuration and Environment

### MySQL Connection Configuration
- Check the `application.properties` file for MySQL connection
- Define the Hibernate dialect (MySQLDialect recommended)
- Enable auto-creation or update of tables (DDL auto = update)

### Prompt Copilot Suggested:
"Generate the Spring Boot configuration to connect to MySQL in the `application.properties` file."

## 4. Creating Entities and Repositories

### For Each Identified Entity:
- Create the model class (User, Bank, BankAccount, Transaction)
- Add necessary JPA annotations (`@Entity`, `@Table`, etc.)
- Create relationships between entities (`OneToMany`, `ManyToOne`, etc.)
- Create a repository for each entity with Spring Data JPA

### Prompt Copilot Suggested:
"Create a JPA entity for BankAccount with fields id, accountNumber, balance, and a ManyToOne relationship with User."

## 5. Security and Authentication Setup

### Spring Security and JWT Configuration
- Define a configuration class for Spring Security
- Create an authentication service (`UserDetailsService`)
- Generate JWT tokens during login
- Protect endpoints via filters and roles

### Prompt Copilot Suggested:
"Generate an example of Spring Security configuration for a REST application using JWT for authentication."

## 6. OTP Integration via Email

### For Sensitive Transactions or Registration:
- Configure `JavaMailSender` with Mailtrap/Testmail parameters
- Create a service method to generate a simple OTP and send it via email
- Add a table or logic to verify OTP validity

### Prompt Copilot Suggested:
"Write a prompt to generate a Java service that sends an OTP via email using `JavaMailSender`."

## 7. Creating REST APIs

### Define Endpoints for Each Functionality
- **Authentication**: `/auth/register`, `/auth/login`
- **Bank Management**: `/banks`, `/banks/{id}`
- **Client Management**: `/customers`, `/customers/{id}`
- **Account Management**: `/accounts`, `/accounts/{id}`
- **Transactions**: `/transactions`, `/transactions/{id}`

### Integrate Swagger for Documentation
- Add Swagger dependency (or Springdoc OpenAPI)
- Configure Swagger interface to test and document your APIs

### Prompt Copilot Suggested:
"Generate the Swagger configuration for a Spring Boot application to document REST APIs."

## 8. Testing and Validation

### Test the Application
- Use Postman to send requests to endpoints
- Verify security functionality (JWT and OTP)
- Test data persistence in MySQL via phpMyAdmin

## 9. Dockerization (Optional but Recommended)

### Create a Dockerfile for Your Spring Boot Application
- Configure a container for your Java app
- Create a `docker-compose.yml` if you want to orchestrate MySQL and your application together

### Prompt Copilot Suggested:
"Generate a Dockerfile for a Spring Boot application and a `docker-compose.yml` file to include MySQL."

## 10. Documentation and Finalization

### Document the Project (README.md, Swagger)
- Ensure all project rules are followed
- Clean up code and add comments
- Prepare the project presentation
