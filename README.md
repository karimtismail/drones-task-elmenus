# Drones

---

## Table of Contents

- [Introduction](#introduction)
- [Task Description](#task-description)
- [Requirements](#requirements)
  - [Functional Requirements](#functional-requirements)
  - [Non-functional Requirements](#non-functional-requirements)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Building and Running](#building-and-running)
- [Development](#development)
  - [Database](#database)
  - [Swagger API Documentation](#periodic-task)

---

## Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include the delivery of small items that are (urgently) needed in locations with difficult access.

---

## Task Description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore, and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communication with the drone is outside the scope of this task.

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

---

## Requirements

While implementing your solution **please take care of the following requirements**:

### Functional Requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight than it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones' battery levels and create history/audit event log for this.

### Non-functional Requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable
- Advice: Show us how you work through your commit history.

---

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySql

### Building and Running

Clone the repository:

```bash
git clone https://github.com/your-username/drones.git
```

### Build the project
```bash
mvn clean install package
```

### Run the application:
```bash
java -jar target/drone.jar
```

### Java Documentation
  
Java documentation for this project is available in the `docs` folder. Also, you can generate the Java documentation using the following Maven command:

```bash
mvn javadoc:javadoc
```

## Development

### Database Configuration
By default, the application uses an H2 database. If you want to use MySQL, follow these steps:

1. Open the `application.properties` file located in the `resources` package.

2. Update the `spring.datasource.username` and `spring.datasource.password` properties with your MySQL username and password.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### Swagger API Documentation
The Swagger API documentation for this project is accessible at [Swagger API Documentation](http://localhost:8080/swagger-ui/index.html#/). Please refer to this documentation for information on available API endpoints and how to interact with them.
