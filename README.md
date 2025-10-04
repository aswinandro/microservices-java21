Java Microservices Architecture powered by Spring Boot, MongoDB, SQL, Eureka (Service Discovery), Circuit Breaker, Docker, Test Containerization, Spring Data JPA, Flyway Migration, Lombok, and Spring Web. This repo demonstrates best practices for modern Java microservices development with multiple services and resilient configuration.

Architecture Overview
API Gateway: Handles routing to internal microservices, circuit breaking (Resilience4J/Spring Cloud Gateway).

Product Service: Manages products, stores data in MongoDB.

Order Service: Handles order creation/processing, stores in SQL (JPA/Flyway).

Inventory Service: Manages product inventory, persistent storage.

Frontends: Multiple UI frontends built with Angular and/or other technologies.

Microservices & REST APIs
API Gateway
Handles routing to /api/product, /api/order, /api/inventory. Implements circuit breaker fallback.

Fallback route: GET /fallbackRoute
Responds: 503 Service Unavailable: "Service Unavailable"

Routes configured for each microservice with circuit breaker support.

Product Service
Base URL: /api/product

Method	Endpoint	Description	Request Body	Response
POST	/api/product	Create a new product	ProductRequest DTO	ProductResponse DTO
GET	/api/product	List all products	—	[ProductResponse] DTOs
MongoDB powered. Lombok/Spring Data used.

Order Service
Base URL: /api/order

Method	Endpoint	Description	Request Body	Response
POST	/api/order	Place a new order	OrderRequest DTO	"Order Placed Successfully"
Circuit breaker and fallback method implemented (Resilience4j). SQL, Flyway migrations, and JPA.

Inventory Service
Base URL: /api/inventory

(Typical endpoints follow product and order services patterns, e.g.)

GET /api/inventory – List all inventory items.

POST /api/inventory – Add/update inventory records.

See service code for additional specifics.

Frontends
angular-frontend
Built with Angular CLI 18.x.

Usage:

ng serve for development

http://localhost:4200/ for preview

See README inside angular-frontend for build/test instructions.

microservices-shop-frontend
Additional UI frontend, folder structure similar to Angular frontend.

Resilience & Service Discovery
Eureka: Central registry for service discovery.

Circuit Breaker: Spring Cloud Gateway and Resilience4j to prevent cascading failures (configured per service in API Gateway).

Swagger API Docs:
Exposed for each microservice per route (e.g. /product-service/v3/api-docs routed via gateway).

Running the Project
Services can be orchestrated using docker-compose.yml files in each microservice folder.
See each microservice’s README (or Dockerfile) for instructions.

Technologies Used
Java 21, Spring Boot, Spring Data JPA

MongoDB, MySQL/SQL

Eureka

Resilience4j

Docker

Angular (Frontend)

Lombok

Flyway

License
This project is licensed under the Unlicense. Feel free to use and modify.

For API models and additional endpoints, see each microservice’s source (controller and dto folders). Code is modular for easy extension.

github.com favicon
microservices-java21/inventory-service at main · aswinandro/microservices-java21 · GitHub


