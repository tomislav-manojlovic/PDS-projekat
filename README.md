# Mini E-commerce Microservices Project

## Modules

* **EurekaServer** – Service registry (port 8761)
* **APIGateway** – Routes requests to microservices (port 8080)
* **UsersService** – CRUD operations for users (port 8081)
* **OrdersService** – Manages orders + purchases (port 8082)

---

## Start Order

1. **EurekaServer**
2. **UsersService**
3. **OrdersService**
4. **APIGateway**

---

## Endpoints via Gateway

* `GET /api/users` → Users
* `GET /api/orders` → Orders
* `GET /api/purchases` → Purchases

---

## Features

* **Service Discovery:** Eureka
* **Routing:** Spring Cloud Gateway
* **Feign Communication:** OrdersService calls UsersService
* **Resilience:** Circuit Breaker + Retry (Resilience4j)
* **Swagger UI:** UsersService & OrdersService endpoints
* **H2 Console:** In-memory DB for Users & Orders
