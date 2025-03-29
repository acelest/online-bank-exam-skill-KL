# Online Banking System - Project Structure

## 1. Main Components

```
src/
│
├── main/
│   ├── java/
│   │   ├── com/userfront/
│   │   │   ├── controller/      # Handles HTTP requests
│   │   │   ├── dao/             # Data Access Objects
│   │   │   ├── domain/          # Entity classes
│   │   │   ├── service/         # Business logic
│   │   │   ├── security/        # Authentication & Authorization
│   │   │   └── config/          # App configurations
│   │   │
│   │   └── com/onlinebanking/   # Additional features
│   │
│   └── resources/
│       ├── templates/           # Thymeleaf HTML templates
│       ├── static/              # JS, CSS, images
│       └── application.properties # App settings
└── test/                        # Unit and integration tests
```

## 2. Key Features

1. **User Management**

   - Registration
   - Authentication
   - Profile management

2. **Account Management**

   - Primary Account
   - Savings Account
   - Balance display

3. **Transaction Services**

   - Deposits
   - Withdrawals
   - Transfer between accounts
   - Transfer to other users

4. **Recipient Management**

   - Add/edit recipients
   - View recipients list

5. **Appointment System**
   - Schedule appointments
   - Manage appointments

## 3. Database Structure

```
Tables:
- user                  # User information
- primary_account       # Primary account details
- savings_account       # Savings account details
- primary_transaction   # Transactions for primary accounts
- savings_transaction   # Transactions for savings accounts
- recipient             # Recipient information
- appointment           # Appointment scheduling
- user_role             # User roles for security
- role                  # Available roles
```

## 4. Main Workflows

### Account Creation

1. User submits registration form
2. System validates user input
3. System creates user record
4. System creates primary and savings accounts
5. User is redirected to dashboard

### Fund Transfer

1. User selects transfer type (between accounts/to someone else)
2. User enters transfer details
3. System validates transfer possibility
4. System creates transaction records
5. System updates account balances
6. User receives confirmation

## 5. Technologies Used

- Spring Boot
- Spring Security
- Thymeleaf
- Spring Data JPA
- MySQL Database
- Tailwind CSS
- JavaScript/jQuery

```

```
