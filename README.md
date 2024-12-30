# CSV File Upload and Retrieval Application

## Overview
This is a Spring Boot application built with Java. It allows users to upload, retrieve, and manage data from a CSV file via RESTful endpoints. The application uses an in-memory database for storage, ensuring quick setup and easy management.

## Features
- **Upload CSV Data**: Upload the contents of a CSV file to the in-memory database.
- **Fetch All Data**: Retrieve all records stored in the database.
- **Fetch Data by Code**: Retrieve a specific record using the unique `code` field.
- **Delete All Data**: Remove all records from the database.

## Architecture and Design

The application is built using the **Ports and Adapters** (Hexagonal) architecture.

### Key Concepts:
- **Port**: Defines the interfaces (e.g., REST endpoints or service contracts) through which the application interacts with the outside world.
- **Adapter**: Implements the ports, handling input (e.g., controllers for REST APIs) and output (e.g., database repositories).
- **Core Application**: Contains business logic, isolated from external systems. This ensures testability and flexibility for future changes.

### Benefits:
- Clear separation of concerns between business logic and external systems.
- Increased testability due to loosely coupled components.
- Easier maintenance and adaptability to changing requirements.

## Application Configuration

### Spring Boot
The application is built using Spring Boot, providing a robust framework for rapid application development.

### Database
The in-memory H2 database is used for data storage with the following configuration:
- **JDBC URL**: `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- **Driver Class Name**: `org.h2.Driver`
- **Username**: `sa`
- **Password**: *(none)*
- **Hibernate Dialect**: `org.hibernate.dialect.H2Dialect`
- **DDL Auto**: `update` (auto-updates the schema based on entity changes)
- **SQL Logging**: Enabled to display executed SQL statements.

### Server
The application runs on port `5050`.

### H2 Console
The H2 database console is enabled for debugging and can be accessed at: http://localhost:5050/h2-console

### Swagger
The application includes API documentation powered by Springdoc OpenAPI.
- Swagger UI: Available at `/swagger-ui`.
- API Docs: Available at `/v3/api-docs`.

## Build Tool

The project is built using **Maven**, a popular build automation tool for Java projects.  
To use Maven, download the latest version from the [official Maven website](https://maven.apache.org/download.cgi).

### Prerequisite Maven Version
Ensure you have Maven **3.8.8** (or later) installed. Follow the [installation guide](https://maven.apache.org/install.html) if needed.

To verify Maven installation:
```bash
mvn -v
