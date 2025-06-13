# Inventory & Order Management System

A beginner-friendly **Java 21 Spring Boot** application that implements an Inventory and Order Management System with RESTful JSON APIs. This system supports multiple user roles (Customer, Seller, Admin) and provides essential features such as product management, order management, and inventory control.

---

## Setup & Installation

1. **Clone the productRepository**
   ```bash
   git clone https://github.com/duong-tranhai/spring-order-system.git
   cd spring-order-system
   ```
2. **Configure database**
   Update src/main/resources/application.properties or application.yml with your DB credentials:
   
    ```bash
    # PostgreSQL datasource settings
    spring.datasource.url=jdbc:postgresql://localhost:5432/inventorydb
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
    spring.datasource.driver-class-name=org.postgresql.Driver

    # JPA / Hibernate settings
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
   ```
3. **Build the project**
   Using Maven:
    ```bash
    mvn clean install
    ```
    Or using Gradle:
    ```bash 
    ./gradlew build
    ```

4. **Running the Application**
    ```bash 
    mvn spring-boot:run
    ```

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [System Architecture & Package Structure](#system-architecture--package-structure)
- [Technologies Used](#technologies-used)
- [Setup & Installation](#setup--installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [User Roles & Permissions](#user-roles--permissions)
- [Expandable Features Suggestions](#expandable-features-suggestions)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

This application is designed to manage products, customer orders, and inventory in a simple but scalable way. It implements a layered architecture separating concerns via packages like `controller`, `service`, `productRepository`, `entity`, `dto`, and `config`. It supports user authentication and role-based authorization via `security`.

---

## Features

### Product Management
- Add new products
- Update existing products
- Delete products
- List all products

### Order Management
- Customers can create orders
- View orders by users and admins
- Sellers handle and update order status

### Inventory Control
- Sellers update stock quantities
- Stock levels are adjusted automatically upon order creation

### User Roles
- **Customer**: Browse products, create orders
- **Seller**: Manage product stock, process orders, update order status
- **Admin**: Full control over products, orders, users, reports, and system settings

---

## System Architecture & Package Structure
```
src/main/java/com/example/inventorysystem/
│
├── config/ # Application and security configuration classes
├── controller/ # REST API controllers (endpoints)
├── dto/ # Data Transfer Objects for request/response payloads
├── entity/ # JPA entities representing database tables
├── productRepository/ # Spring Data JPA repositories (DAO layer)
├── service/ # Business logic services
├── security/ # Security configuration, JWT handlers, filters, user details
└── exception/ # Custom exceptions and handlers (optional)
```

---

## Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Web (REST APIs)
- Spring Data JPA
- Spring Security with JWT Authentication
- MySQL / PostgreSQL (configurable via application properties)
- Maven / Gradle (build tool)
- Lombok (optional, for reducing boilerplate code)
- MapStruct (optional, for DTO mapping)

---


