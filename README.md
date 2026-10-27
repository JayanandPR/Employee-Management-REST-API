# Employee Management REST API

A RESTful Employee Management API built with Java, Spring Boot, Spring Data JPA, MySQL, and Lombok.

## Features

- Create an employee
- Retrieve all employees with pagination and sorting
- Retrieve an employee by ID
- Update employee details
- Delete an employee
- Request validation
- Duplicate email prevention
- Centralized exception handling
- Layered architecture using controllers, services, repositories, entities, DTOs, and exceptions

## Technology Stack

- Java 25
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Jakarta Bean Validation
- Lombok
- Maven

## Prerequisites

- A compatible JDK
- MySQL Server
- Maven, or the included Maven Wrapper

## Setup Instructions

1. Clone the repository.
2. Create the database:

   ```sql
   CREATE DATABASE employee_management;
   ```

3. Configure the database credentials using environment variables.

   On Windows PowerShell:

   ```powershell
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="your_mysql_password"
   ```

4. Start the application using the Maven Wrapper:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   On macOS/Linux:

   ```bash
   ./mvnw spring-boot:run
   ```

5. The application runs on `http://localhost:8080` by default.

Hibernate creates or updates the database tables during development.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/employees` | Create an employee |
| GET | `/api/employees` | Retrieve employees with pagination |
| GET | `/api/employees/{id}` | Retrieve an employee by ID |
| PUT | `/api/employees/{id}` | Update an employee |
| DELETE | `/api/employees/{id}` | Delete an employee |

## Sample Request

`POST /api/employees`

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "department": "Engineering",
  "salary": 45000.00
}
```

## Sample Response

HTTP `201 Created`

```json
{
  "id": 1,
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "department": "Engineering",
  "salary": 45000.00
}
```

## Pagination and Sorting

Example:

`GET /api/employees?page=0&size=5&sortBy=name&direction=asc`

- `page`: Zero-based page number; defaults to `0`.
- `size`: Number of records per page; defaults to `10`, with a maximum of `100`.
- `sortBy`: Field to sort by; defaults to `id`.
- `direction`: `asc` or `desc`; defaults to `asc`.

The paginated response includes employee records and metadata such as the total number of elements and pages.

## Error Handling

- `400 Bad Request`: Invalid request data or pagination parameters.
- `404 Not Found`: Employee does not exist.
- `409 Conflict`: Duplicate email or database integrity conflict.

## Testing

Run the automated tests using:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Postman Collection

A Postman collection is included in the `postman/` folder to help you test the Employee Management REST API endpoints.

**Collection file:** `postman/Employee-Management-API.postman_collection.json`

### How to Import

1. Open Postman.
2. Click **Import**.
3. Select the `Employee-Management-API.postman_collection.json` file from the project's `postman/` folder.
4. Start the Spring Boot application and ensure your MySQL database is running.
5. Run the requests to test the API endpoints.

The collection includes requests for creating, retrieving, updating, and deleting employees, along with pagination, sorting, and error-handling scenarios.

