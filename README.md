# Java Microservices Architecture with Spring Boot and Java 21

[![License](https://img.shields.io/badge/License-Unlicense-blue.svg)](https://unlicense.org/)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)

## Overview

This repository demonstrates a modern **Java Microservices Architecture** built with **Spring Boot 3.x** and **Java 21**, leveraging cutting-edge technologies and best practices for scalable, resilient, and maintainable microservices. The project includes multiple services, each with its own database, orchestrated via Docker, and integrated with service discovery, circuit breakers, and API documentation.

### Key Features
- **Service Discovery**: Eureka for dynamic service registration and discovery.
- **Resilience**: Circuit breakers with Resilience4j to handle failures gracefully.
- **Data Persistence**:
  - **MongoDB** for flexible NoSQL storage (Product Service).
  - **MySQL** with Spring Data JPA and Flyway migrations (Order Service).
- **API Documentation**: Swagger/OpenAPI for interactive endpoint exploration.
- **Containerization**: Docker and Testcontainers for development and testing.
- **Frontend**: Angular-based UI for a complete full-stack experience.
- **Modern Java**: Uses Java 21 Records, Lombok for boilerplate reduction, and modular design.

## Architecture Overview

The system follows a **gateway-patterned microservices architecture**:

```
[Client/UI] --> [API Gateway (Spring Cloud Gateway + Resilience4j)]
                |
                +--> [Product Service] <--> [MongoDB]
                |
                +--> [Order Service] <--> [MySQL (Flyway)]
                |
                +--> [Inventory Service] <--> [Persistent Storage]
```

- **API Gateway**: Routes requests to microservices, enforces circuit breaking, and exposes Swagger docs.
- **Services**: Independent, loosely coupled, each with dedicated persistence.
- **Eureka**: Central registry for service discovery.
- **Frontends**: Angular-based UI consuming REST APIs.

## API Endpoints

All services expose RESTful APIs with JSON payloads, documented via Swagger. Below is a summary of key endpoints:

### API Gateway
- **Base URL**: `http://localhost:8080`
- **Key Endpoint**:
  - `GET /fallbackRoute` → Returns 503 "Service Unavailable" on circuit breaker trip.
- **Routing**: Forwards `/api/product/**`, `/api/order/**`, `/api/inventory/**` to respective services.

### Product Service
- **Base URL**: `/api/product`
- **Endpoints**:
  - `POST /api/product` → Create a product (`ProductRequest` DTO → `ProductResponse` DTO).
  - `GET /api/product` → List all products (returns `[ProductResponse]` DTOs).
- **Backend**: MongoDB, Spring Data MongoDB, Lombok.

### Order Service
- **Base URL**: `/api/order`
- **Endpoints**:
  - `POST /api/order` → Place an order (`OrderRequest` DTO → "Order Placed Successfully").
- **Backend**: MySQL, Spring Data JPA, Flyway migrations, Resilience4j.

### Inventory Service
- **Base URL**: `/api/inventory`
- **Endpoints**:
  - `GET /api/inventory` → List inventory items.
  - `POST /api/inventory` → Add/update inventory (`InventoryRequest` DTO → `InventoryResponse` DTO).
- **Backend**: Flexible storage (MongoDB/SQL), integrated with Order Service.

### Swagger Documentation
- Access via gateway: `http://localhost:8080/<service>/v3/api-docs` (e.g., `/product-service/v3/api-docs`).
- Interactive UI: `http://localhost:8080/<service>/swagger-ui.html`.
- Auto-generates schemas, supports request/response examples.

## Docker and Containerization

Each microservice is containerized with **Docker** and orchestrated via **docker-compose**. **Testcontainers** are used for integration testing.

### Docker Setup
- **Base Images**: `eclipse-temurin:21-jdk-alpine` (build), `eclipse-temurin:21-jre-alpine` (runtime).
- **Dockerfiles**: Multi-stage builds for slim images (~150MB).
- **docker-compose.yml**: Defines services, networks, and dependencies (e.g., MongoDB, MySQL).
- **Example (Product Service)**:
```yaml
version: '3.8'
services:
  product-service:
    build: .
    ports:
      - "8081:8080"
    depends_on:
      - mongo
  mongo:
    image: mongo:7
    ports:
      - "27017:27017"
```

### Key Features
- **Health Checks**: Exposed via `/actuator/health`.
- **Volumes**: Persistent data for MongoDB/MySQL.
- **Testcontainers**: Auto-spins DBs for tests (e.g., `MongoDBContainer`).

## Frontend

- **angular-frontend**:
  - Built with **Angular 18 CLI**.
  - Run: `cd angular-frontend && ng serve` (serves at `http://localhost:4200`).
  - Proxies API calls to the gateway.
- **microservices-shop-frontend**:
  - Secondary UI (check sub-README for specifics, likely React/Vue).
  - Similar setup for static serving via Nginx.

## Technologies

| Category          | Technologies                          |
|-------------------|---------------------------------------|
| Backend          | Java 21, Spring Boot, Spring Data JPA/MongoDB, Lombok, Records |
| Databases        | MongoDB, MySQL (Flyway)              |
| Resilience       | Eureka, Resilience4j                 |
| API Docs         | Swagger/OpenAPI (Springdoc)          |
| Containerization | Docker, Testcontainers, docker-compose |
| Frontend         | Angular 18                           |
| Build Tools      | Maven                                |

## Getting Started

### Prerequisites
- **Java 21** (e.g., OpenJDK)
- **Docker** and **docker-compose**
- **Node.js** (for Angular frontend)
- **Maven**

### Running the Project
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/aswinandro/microservices-java21.git
   cd microservices-java21
   ```
2. **Start Services**:
   - Per service: `cd <service> && docker-compose up` (e.g., `cd product-service`).
   - All services: Run root-level `docker-compose up` (if available).
3. **Access**:
   - API Gateway: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/<service>/swagger-ui.html`
   - Frontend: `cd angular-frontend && ng serve` → `http://localhost:4200`
4. **Testing**:
   - Run unit/integration tests: `cd <service> && ./mvnw test`
   - Testcontainers auto-start DBs for tests.

### Production Deployment
- Use Kubernetes/Helm for scaling (adapt docker-compose to manifests).
- Configure environment variables (e.g., `SPRING_DATASOURCE_URL`, `SPRING_DATA_MONGODB_URI`).

## License

This project is licensed under the [Unlicense](https://unlicense.org/) – free to use, modify, and distribute without restrictions. See [LICENSE](./LICENSE) for details.

## Contributing

Contributions are welcome! Please:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature`).
3. Commit changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## Contact

For issues or questions, open a GitHub issue or reach out via the repository's [discussions](https://github.com/aswinandro/microservices-java21/discussions).

---

*Last updated: October 04, 2025*