# CodeCraftHub Learning Management System

A simple REST API for tracking learning goals and courses, built with Java and Spring Boot.

## Features

- Complete CRUD operations for course management
- JSON file-based data storage
- No database required
- RESTful API design
- Automatic ID generation
- Automatic `created_at` timestamp generation
- Input validation
- Status validation
- Error handling for missing courses and invalid requests
- Automatic creation of `courses.json` if the file does not exist

## Technologies

- Java 21
- Spring Boot
- Maven
- Jackson
- Apache Tomcat
- JSON

## Installation

### Prerequisites

Make sure the following are installed:

- Java JDK 21 or higher
- Maven, or use the included Maven Wrapper

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

If Maven is not installed globally, use the Maven Wrapper included with the project.

## Running the Application

### Using Maven Wrapper

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

### Using Maven

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

## Building the Application

To create a production build:

```powershell
.\mvnw.cmd clean package
```

If the build is successful, Maven creates the application JAR file in the `target` directory.

The project has been successfully built using Java 21 and the Maven Wrapper.

## Data Storage

Course data is stored in:

```text
courses.json
```

The application automatically creates this file if it does not already exist.

No external database is required.

## API Endpoints

Base URL:

```text
http://localhost:8080/api
```

### 1. Add a Course

**POST**

```text
/api/courses
```

Request body:

```json
{
  "name": "Python Basics",
  "description": "Learn Python fundamentals",
  "target_date": "2025-12-31",
  "status": "Not Started"
}
```

Example using curl:

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Python Basics\",\"description\":\"Learn Python fundamentals\",\"target_date\":\"2025-12-31\",\"status\":\"Not Started\"}"
```

The `id` and `created_at` fields are generated automatically.

### 2. Get All Courses

**GET**

```text
/api/courses
```

Example:

```bash
curl http://localhost:8080/api/courses
```

The response contains all courses stored in `courses.json`.

### 3. Get a Specific Course

**GET**

```text
/api/courses/{id}
```

Example:

```bash
curl http://localhost:8080/api/courses/1
```

If the course does not exist, the API returns:

```text
404 NOT_FOUND
```

### 4. Update a Course

**PUT**

```text
/api/courses/{id}
```

All fields are optional when updating a course.

Example request:

```json
{
  "status": "In Progress"
}
```

Example using curl:

```bash
curl -X PUT http://localhost:8080/api/courses/1 \
  -H "Content-Type: application/json" \
  -d "{\"status\":\"In Progress\"}"
```

Supported status values are:

```text
Not Started
In Progress
Completed
```

### 5. Delete a Course

**DELETE**

```text
/api/courses/{id}
```

Example:

```bash
curl -X DELETE http://localhost:8080/api/courses/1
```

If the course does not exist, the API returns:

```text
404 NOT_FOUND
```

## Course Data Structure

Each course contains the following fields:

| Field | Type | Required | Description |
|---|---|---|---|
| `id` | Integer | Automatically generated | Unique course identifier |
| `name` | String | Yes | Course name |
| `description` | String | Yes | Course description |
| `target_date` | String | Yes | Target date in `YYYY-MM-DD` format |
| `status` | String | Yes | Course status |
| `created_at` | String | Automatically generated | Creation timestamp |

### Valid Status Values

Only these status values are accepted:

```text
Not Started
In Progress
Completed
```

## Validation and Error Handling

The API provides error handling for:

- Missing required fields
- Invalid status values
- Course not found
- File read/write errors
- Invalid requests
- Internal server errors

### Missing Required Field

For example:

```json
{
  "name": "",
  "description": "Learn Java",
  "target_date": "2026-12-31",
  "status": "Not Started"
}
```

The API returns:

```text
400 BAD_REQUEST
```

### Invalid Status

For example:

```json
{
  "status": "Finished"
}
```

`Finished` is not a valid status.

The API returns:

```text
400 BAD_REQUEST
```

### Course Not Found

Requesting a course that does not exist:

```text
GET /api/courses/999
```

returns:

```text
404 NOT_FOUND
```

## Testing

The API can be tested using:

- IntelliJ IDEA
- Postman
- curl
- Browser for GET requests

The following operations should be tested:

1. Create a course
2. Get all courses
3. Get a specific course
4. Update a course
5. Delete a course
6. Test missing required fields
7. Test an invalid status
8. Test a non-existing course ID

The CRUD operations and validation scenarios have been tested during development.

## Troubleshooting

### Problem: `JAVA_HOME not set`

Make sure the `JAVA_HOME` environment variable points to your JDK installation.

Example:

```text
C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot
```

### Problem: Port 8080 is already in use

Stop the application currently using port 8080, or configure another port in:

```text
src/main/resources/application.properties
```

For example:

```properties
server.port=8081
```

### Problem: Maven command is not recognized

If Maven is not installed globally, use the Maven Wrapper included with the project:

```powershell
.\mvnw.cmd clean package
```

or:

```powershell
.\mvnw.cmd spring-boot:run
```

## Project Structure

```text
codecraft-hub/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── codecrafthub/
│       │           ├── CodecraftHubApplication.java
│       │           ├── CourseController.java
│       │           ├── Course.java
│       │           └── CourseService.java
│       └── resources/
├── courses.json
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Example Course

```json
{
  "id": 1,
  "name": "Advanced Java",
  "description": "Learn advanced Java programming",
  "status": "Completed",
  "target_date": "2027-01-15",
  "created_at": "2026-10-14 08:39:06"
}
```

## API Response Examples

### Successful Response

```json
{
  "success": true,
  "course": {
    "id": 1,
    "name": "Advanced Java",
    "description": "Learn advanced Java programming",
    "status": "Completed",
    "target_date": "2027-01-15",
    "created_at": "2026-10-14 08:39:06"
  }
}
```

### Error Response

```json
{
  "success": false,
  "error": "Course with ID 999 not found"
}
```

## Build Verification

The project can be verified with:

```powershell
.\mvnw.cmd clean package
```

A successful build should end with:

```text
BUILD SUCCESS
```

## License

This project was created as part of the CodeCraftHub learning project.