# Java Project Structure

## Project Description
This repository contains a Java-based application with a layered architecture. The project is organized into the following layers:
- **Controller Layer:** Handles HTTP requests.
- **Service Layer:** Contains business logic.
- **TalonOne Client Layer:** Manages external API interactions.
- **Model Layer:** Defines data structures.
- **Repository Layer:** Handles database operations.
- **Config Layer:** Contains configuration files.
- **Resource Layer:** Includes application properties.

## Directory Structure
```
src/
├── main/java/com/app/
│   ├── AppApplication.java
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── OrderController.java
│   │   └── RewardsController.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── OrderService.java
│   │   └── RewardsService.java
│   ├── talonone/
│   │   └── TalonOneClient.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Order.java
│   │   ├── Item.java
│   │   ├── OrderRequest.java
│   │   ├── CartRequest.java
│   │   ├── RewardsResponse.java
│   │   ├── ProfileDTO.java
│   │   └── SessionDTO.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── OrderRepository.java
│   └── config/
│       └── RestTemplateConfig.java
└── resources/
    └── application.properties
```

## How to Build and Run
1. Clone the repository: `git clone https://github.com/Thapaswi31/talon-poc-5401.git`
2. Navigate to the project directory: `cd talon-poc-5401`
3. Build the project using Maven: `mvn clean install`
4. Run the application: `java -jar target/<application_name>.jar`
