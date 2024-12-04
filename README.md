Courier Tracking Service
========================

📖 Overview
-----------

The **Courier Tracking Service** is a Spring Boot application designed to track and manage couriers' travel logs, distances, and locations relative to stores. The service calculates total travel distance, logs couriers' locations when they are within proximity to a store, and retrieves detailed travel logs for a courier.

⚙️ Tools and Technologies Used
------------------------------

*   **Java 17**

*   **Spring Boot**

    *   Spring Web

    *   Spring Data JPA

    *   Spring Cache

*   **H2 Database** (In-memory database)

*   **Lombok**

*   **MediatR Pattern**

*   **CQRS Pattern** for implementing Command-Query Responsibility Segregation

*   **Distance Calculator based on Distance Type** (Strategy Pattern)

*   **OpenAPI/Swagger** for API documentation

*   **Mockito & JUnit** for unit testing

*   **Docker & Docker Compose** for containerization


🌐 Endpoints
------------

### 1\. **Get Total Travel Distance**

*   **URL:** /couriers/{courierId}/total-distance

*   **Method:** GET

*   **Description:** Returns the total distance traveled by a courier, calculated based on logged locations.

*   **Responses:**

    *   **200 OK:** Returns the total distance in kilometers.

    *   **404 NOT FOUND:** If the courier's logs are not found.

*  ```json
   {
     "totalDistance": "31,17 km"
   }

### 2\. **Update Courier Location**

*   **URL:** /couriers/update-location

*   **Method:** PUT

*   **Description:** Updates a courier's location and logs it if they are within 100 meters of a store. Prevents duplicate entries within 1 minute for the same store.

*   **Request Body**:

*   ```json
    { 
     "courierId": 1,
     "latitude": 40.741895,
     "longitude": -73.989308,
     "timestamp": 1691234567890
    }

*   **Responses:**

    *   **201 CREATED:** Location logged successfully.

    *   **400 BAD REQUEST:** Invalid data or the courier is not near any store.

    *   **409 CONFLICT:** Duplicate entry for the store within the last minute.


### 3\. **Get Travels of Courier**

*   **URL:** /couriers/{courierId}/travels

*   **Method:** GET

*   **Description:** Returns all travel logs of a courier, ordered by timestamp.

*   **Responses:**

    *   **200 OK:** Returns a list of travel logs.

    *   **404 NOT FOUND:** If no logs are found for the courier.

  *   ```json
      [
       {
        "courierId": 1,
        "storeName": "Migros Store A",
        "latitude": 40.741895,
        "longitude": -73.989308,
        "timestamp": 1691234567890
       },
       {
        "courierId": 1,
        "storeName": "Migros Store B",
        "latitude": 40.742895,
        "longitude": -73.988308,
        "timestamp": 1681235567890
       }
      ]
🚀 How to Run Locally
---------------------

### Prerequisites

*   Java 21

*   Docker and Docker Compose (optional for containerized execution)


### Running Locally (Without Docker)

1. Clone the repository.

2. Navigate to the project directory
    ```bash
     cd couriertracking

3. Run the application
   ```bash
     ./mvnw spring-boot:run

4.  Access the Swagger UI for API testing at: http://localhost:8080/swagger-ui.html


### Running with Docker

1.  Build and start the application:
    ```bash
    docker-compose up --build

2.  Access the Swagger UI for API testing at: http://localhost:8080/swagger-ui.html


📦 H2 Database Console
----------------------

*   **URL:** http://localhost:8080/h2-console

*   **JDBC URL:** jdbc:h2:mem:couriertracking

*   **Username:** sa

*   **Password:** (leave blank)
