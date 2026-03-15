## Spring AOP Demo – Book Service

This repository contains a small Spring Boot application that demonstrates how to use **Spring AOP (Aspect Oriented Programming)** to implement cross‑cutting concerns such as logging and error handling around typical CRUD operations.

The domain is intentionally simple – a small book catalog – so that the focus stays on how AOP is applied around service-layer methods.

---

## Tech Stack

- **Language**: Java 17  
- **Framework**: Spring Boot 3 (Web, Data JPA, AOP)  
- **Database**: H2 (in-memory)  
- **Build Tool**: Maven  
- **Testing**: JUnit 5, Spring Boot Test, MockMvc  
- **Lombok** for boilerplate reduction

---

## Architectural Overview

The project follows a classic layered architecture:

- **`controller` layer**  
  - `BookController` exposes REST endpoints under `/api/books` for:
    - Getting all books
    - Getting a book by title
    - Adding a new book
  - All responses are wrapped into a `CustomResponse<T>` object.

- **`service` layer**  
  - `BookService` contains the business logic:
    - Interacts with the repository
    - Logs operations
    - Produces `CustomResponse<Book>` with appropriate `CustomStatus` (`SUCCESS`, `NOT_FOUND`, `EXCEPTION`).

- **`repository` layer**  
  - `BookRepository` is a Spring Data JPA repository for the `Book` entity (H2 in-memory database).

- **`entity` layer**  
  - `Book` is a simple JPA entity with `id`, `title`, and `author`.

- **`aop` layer**  
  - `Pointcuts` defines reusable pointcuts for:
    - All `get*` methods in `BookService`
    - All `add*` methods in `BookService`
  - `MyAspect` uses these pointcuts with `@Around` advice to:
    - Log method invocations and arguments (book title, etc.)
    - Measure execution time
    - Wrap unexpected exceptions into a `CustomResponse` with status `EXCEPTION`.

- **`util` layer**  
  - `CustomResponse<T>` – generic wrapper for API responses (`code`, `message`, `responseList`).
  - `CustomStatus` – enum with standard status codes and messages.

On startup, `CourseApplication` populates the in-memory database with a couple of predefined books so the API is ready to use immediately.

---

## REST API

Base path: `/api`

- **GET `/api/books`**  
  - Returns all books wrapped in `CustomResponse<Book>`.

- **GET `/api/books/{title}`**  
  - Returns a single book (as a one-element list) wrapped in `CustomResponse<Book>`.  
  - If a book with the given title does not exist, returns:
    - `code = 1`
    - `message = "Not found"`
    - `responseList = []`

- **POST `/api/books`**  
  - Creates a new book.  
  - Request body (JSON), for example:
    ```json
    {
      "title": "War and Peace",
      "author": "Leo Tolstoy"
    }
    ```
  - Returns the created book in `CustomResponse<Book>` with `code = 0`, `message = "Success"`.

All controller methods are intercepted by AOP advice for logging and error wrapping.

---

## How to Run the Application

### Prerequisites

- JDK 17 installed and `JAVA_HOME` configured  
- Maven 3.x installed (`mvn` available in your PATH)

### Build & Run

From the project root:

```bash
mvn clean package
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

Example requests (once the app is running):

- Get all books:

```bash
curl -X GET http://localhost:8080/api/books
```

- Get book by title:

```bash
curl -X GET http://localhost:8080/api/books/Война%20и%20Мир
```

- Add new book:

```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\"}"
```

---

## Running Integration Tests

The project contains an integration test that boots the full Spring context and exercises the HTTP layer using MockMvc.

Run all tests with:

```bash
mvn test
```

The main integration test verifies that:

- The application context starts correctly  
- `GET /api/books` returns a `CustomResponse` JSON structure with:
  - `code = 0`
  - `message = "Success"`
  - `responseList` as an array

---

## AOP Focus

This repository is meant to serve as a compact but realistic example of Spring AOP usage in a REST application:

- Cross-cutting concerns (logging, error wrapping, timing) are implemented in the `aop` package.  
- Business logic in `BookService` stays focused on working with entities and repositories.  
- The response contract is centralized via `CustomResponse` and `CustomStatus`, which are reused across the controller and the aspect.

There are two aspects to clearly demonstrate different AOP use cases:

- `MyAspect`  
  - Uses `@Around` advice on `BookService` methods whose names start with `get*` and `add*`.  
  - Measures execution time and converts unexpected exceptions into a `CustomResponse` with status `EXCEPTION`.  
  - Shows how to work with `ProceedingJoinPoint`, arguments and method signatures.

- `LoggingAspect`  
  - Uses `@Before` advice to log service-layer method invocations and arguments.  
  - Uses `@AfterReturning` advice to log successful method results.  
  - Uses `@AfterThrowing` advice on controller methods to log any unhandled exceptions.

Together these aspects make the AOP behavior visible in the logs and illustrate the main advice types available in Spring AOP.
