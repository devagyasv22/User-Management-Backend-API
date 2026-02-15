# User Management Backend API

## Overview

This project is a secure and scalable Spring Boot backend application developed as part of a backend domain challenge.

It implements:

- JWT-based Authentication
- Role-Based Access Control (RBAC)
- Secure Password Management
- RESTful API Design
- MySQL Database Integration

The application follows clean architecture principles and industry-standard security practices.

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- MySQL
- Maven

---

## Features

### Authentication

- User Registration (Signup)
- User Login
- JWT Token Generation
- Secure Password Hashing using BCrypt

### Authorization

- Role-Based Access Control
- Admin-Only Endpoints
- Secured APIs using Spring Security

### Role Management

- Create New Roles
- Assign Roles to Users
- Many-to-Many User ↔ Role Mapping

### User Management

- Change Password
- Reset Password
- View Logged-in User Details
- Profile Management Support

---

## Database Schema

### Users Table

- id
- email (unique)
- password
- status

### Roles Table

- id
- name (ROLE_USER, ROLE_ADMIN)

### User_Roles (Join Table)

- user_id
- role_id

---

## Authentication Flow

1. User logs in using `/auth/login`
2. Server returns a JWT token
3. Client sends token in request header:

   Authorization: Bearer <JWT_TOKEN>

4. Spring Security validates token via `JwtAuthenticationFilter`
5. Access is granted based on user roles

---

## API Endpoints

### Public Endpoints

- POST /signup
- POST /auth/login
- POST /auth/reset-password

### Authenticated Endpoints

- GET /users/me
- POST /users/change-password

### Admin Endpoints

- POST /roles?name=ROLE_ADMIN
- POST /roles/assign?userId=1&role=ROLE_ADMIN
- GET /roles

---

## How to Run Locally

### 1. Clone Repository

git clone ... 

cd your-repository

### 2. Configure Database

Update `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

### 3. Run Application

mvn spring-boot:run

---

## Testing

APIs can be tested using:

- Postman
- cURL
- Any REST client

### Example Login Request

POST /auth/login

{
"email": "user@example.com
",
"password": "password123"
}

## Project Structure

src/main/java/com/example/demo
│
├── config
├── controller
├── service
├── repository
├── entity
└── security
## Security Highlights

- Stateless Authentication
- Password Encryption with BCrypt
- Custom JWT Filter
- Role-Based Endpoint Protection
- Clean Separation of Concerns
## Future Enhancements

- Refresh Token Implementation
- Email Verification
- Swagger API Documentation
- Unit & Integration Testing
- Docker Support
- Pagination & Sorting
## Author
Devagya Singh Vats  

