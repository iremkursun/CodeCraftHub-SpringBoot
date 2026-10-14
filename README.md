# CodeCraftHub – Course Management REST API

A RESTful Course Management API built with Java 21, Spring Boot, and Maven as part of the CodeCraftHub learning project.

The application provides CRUD operations for managing courses, input validation, status management, error handling, and JSON-based data persistence without requiring an external database.

---

## 🚀 Features

- Create a new course
- Retrieve all courses
- Retrieve a course by ID
- Update an existing course
- Delete a course
- Automatic course ID generation
- Automatic created_at timestamp generation
- Input validation
- Course status validation
- Error handling
- Custom exception handling
- JSON-based data persistence
- Automatic creation of the data file when required
- RESTful API architecture
- Maven-based build system

---

## 🛠️ Technologies

- Java 21
- Spring Boot
- Spring Web
- Maven
- Jackson
- Apache Tomcat
- JSON
- REST API

---

## 📋 Prerequisites

Make sure the following are installed:

- Java JDK 21 or higher
- Maven

The project also includes the Maven Wrapper, so Maven does not need to be installed globally.

Check Java:

    java -version

Check Maven:

    mvn -version

If Maven is not installed globally, you can use the Maven Wrapper included with the project.

---

## 📂 Project Structure

    codecraft-hub/
    ├── .mvn/
    │   └── wrapper/
    │       ├── maven-wrapper.jar
    │       └── maven-wrapper.properties
    │
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── codecrafthub/
    │   │   │           ├── model/
    │   │   │           │   └── Course.java
    │   │   │           │
    │   │   │           ├── service/
    │   │   │           │   ├── CourseService.java
    │   │   │           │   └── StatusService.java
    │   │   │           │
    │   │   │           ├── ApiStatus.java
    │   │   │           ├── CodecraftHubApplication.java
    │   │   │           ├── CourseController.java
    │   │   │           ├── CourseNotFoundException.java
    │   │   │           ├── GlobalExceptionHandler.java
    │   │   │           ├── HelloController.java
    │   │   │           └── StatusController.java
    │   │   │
    │   │   └── resources/
    │   │       └── application.properties
    │   │
    │   └── test/
    │       └── java/
    │           └── com/
    │               └── codecrafthub/
    │                   └── CodecraftHubApplicationTests.java
    │
    ├── courses.json
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    ├── .gitignore
    └── README.md

---

# ▶️ Running the Application

## Using Maven Wrapper

On Windows PowerShell:

    .\mvnw.cmd spring-boot:run

## Using Maven

    mvn spring-boot:run

The application starts on:

    http://localhost:8080

---

# 🔨 Building the Project

To build the project:

    .\mvnw.cmd clean package

A successful build ends with:

    BUILD SUCCESS

The generated JAR file is created inside:

    target/

---

# 📡 API Endpoints

Base URL:

    http://localhost:8080/api

---

## 1. Create a Course

### POST

    /api/courses

### Request Body

    {
      "name": "Python Basics",
      "description": "Learn Python fundamentals",
      "target_date": "2026-12-31",
      "status": "Not Started"
    }

The id and created_at fields are generated automatically.

### Example using curl

    curl -X POST http://localhost:8080/api/courses \
      -H "Content-Type: application/json" \
      -d "{\"name\":\"Python Basics\",\"description\":\"Learn Python fundamentals\",\"target_date\":\"2026-12-31\",\"status\":\"Not Started\"}"

---

## 2. Get All Courses

### GET

    /api/courses

Example:

    curl http://localhost:8080/api/courses

Returns all available courses.

---

## 3. Get a Course by ID

### GET

    /api/courses/{id}

Example:

    curl http://localhost:8080/api/courses/1

If the requested course does not exist, the API returns:

    404 NOT_FOUND

---

## 4. Update a Course

### PUT

    /api/courses/{id}

Example request:

    {
      "status": "In Progress"
    }

Example using curl:

    curl -X PUT http://localhost:8080/api/courses/1 \
      -H "Content-Type: application/json" \
      -d "{\"status\":\"In Progress\"}"

The course can be updated according to the validation rules implemented by the API.

---

## 5. Delete a Course

### DELETE

    /api/courses/{id}

Example:

    curl -X DELETE http://localhost:8080/api/courses/1

If the course does not exist:

    404 NOT_FOUND

---

# 📚 Course Model

Each course contains the following fields:

| Field | Type | Required | Description |
|---|---|---|---|
| id | Integer | Automatically generated | Unique course identifier |
| name | String | Yes | Course name |
| description | String | Yes | Course description |
| target_date | String | Yes | Target date |
| status | String | Yes | Current course status |
| created_at | String | Automatically generated | Creation timestamp |

---

# 🔄 Course Status

The following status values are supported:

    Not Started
    In Progress
    Completed

An invalid status results in:

    400 BAD_REQUEST

---

# ⚠️ Validation & Error Handling

The application handles several error scenarios, including:

- Missing required fields
- Invalid course status
- Course not found
- Invalid request data
- File read/write errors
- Internal server errors

---

## Missing Required Field

Example:

    {
      "name": "",
      "description": "Learn Java",
      "target_date": "2026-12-31",
      "status": "Not Started"
    }

The API returns:

    400 BAD_REQUEST

---

## Invalid Status

Example:

    {
      "name": "Java Basics",
      "description": "Learn Java fundamentals",
      "target_date": "2026-12-31",
      "status": "Finished"
    }

Finished is not a valid course status.

The API returns:

    400 BAD_REQUEST

---

## Course Not Found

Request:

    GET /api/courses/999

Example response:

    {
      "success": false,
      "error": "Course with ID 999 not found"
    }

HTTP status:

    404 NOT_FOUND

---

# 💾 Data Storage

Course information is stored in:

    courses.json

The application uses JSON-based persistence and does not require an external database.

The application can automatically create the data file when it does not exist.

This makes the project lightweight and easy to run locally.

---

# 🧪 Testing

The API can be tested using:

- IntelliJ IDEA
- Postman
- curl
- Browser for GET requests

The following scenarios should be tested:

1. Create a course
2. Retrieve all courses
3. Retrieve a course by ID
4. Update a course
5. Delete a course
6. Submit missing required fields
7. Submit an invalid status
8. Request a non-existing course ID

The CRUD operations and validation scenarios have been tested during development.

---

# 🔧 Troubleshooting

## Maven Command Is Not Recognized

If Maven is not installed globally, use the Maven Wrapper included with the project:

    .\mvnw.cmd clean package

or:

    .\mvnw.cmd spring-boot:run

---

## JAVA_HOME Is Not Configured

Make sure the JAVA_HOME environment variable points to your JDK installation.

Example:

    C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot

You can verify Java with:

    java -version

---

## Port 8080 Is Already in Use

Stop the application currently using port 8080, or configure another port in:

    src/main/resources/application.properties

For example:

    server.port=8081

---

# ✅ Build Verification

The project can be verified with:

    .\mvnw.cmd clean package

Expected result:

    BUILD SUCCESS

The project has been successfully built using:

- Java 21
- Spring Boot
- Maven
- Maven Wrapper

---

# 📌 Project Status

Status: Completed

The project implements the core functionality required for the CodeCraftHub Course Management API, including:

- CRUD operations
- Input validation
- Status validation
- Exception handling
- JSON-based persistence
- RESTful API design
- Maven-based build and execution

---

# 🎓 Learning Objectives

This project demonstrates practical experience with:

- Java programming
- Spring Boot application development
- REST API design
- HTTP methods and status codes
- CRUD operations
- JSON serialization and deserialization
- Input validation
- Exception handling
- Service and controller separation
- Maven project management
- Git and GitHub
- API testing

---

# 📄 License

This project was created as part of the CodeCraftHub learning project.
