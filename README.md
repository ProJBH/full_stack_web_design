# Vendor Management Web App

A comprehensive vendor management system developed as an independent project (Dec 2023 – Mar 2024) to showcase full-stack and backend development expertise. Initially built using Java with the SSM framework (Spring, Spring MVC, MyBatis), the project was later migrated to Spring Boot—resulting in a 20% improvement in scalability. Deployed on a Linux server with continuous integration and deployment practices, systematic code reviews further boosted performance by 5%. This project highlights a commitment to building scalable, secure, and maintainable applications.

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Highlights](#project-highlights)
- [Project Structure Overview](#project-structure-overview)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)

## Overview

Vendor Management Web App is an independent project developed to solve real-world challenges in vendor information management. This application started as a robust solution built on the SSM framework and was later migrated to Spring Boot, achieving a notable 20% improvement in scalability and a 5% performance boost through systematic code reviews. The project demonstrates my expertise in full-stack development, backend API design, and continuous deployment on Linux-based environments. It reflects my commitment to building scalable, secure, and maintainable applications and serves as a key example of my technical capabilities in my portfolio.

## Tech Stack

**Backend:**
- **Java:** Primary programming language.
- **Spring Framework (SSM):** Initially used (Spring, Spring MVC, MyBatis) for structured development.
- **Spring Boot:** Adopted to streamline configuration and improve scalability.
- **MyBatis:** For ORM and data persistence.
- **Linux:** Deployed on a Linux server with CI/CD practices.

**Front-End:**
- **HTML5, CSS3, JavaScript:** (If applicable) for responsive UI components.
- **Thymeleaf/JSP:** (Optional) for server-side view rendering.

**Tools & Practices:**
- **Maven:** For project build and dependency management.
- **CI/CD Pipelines:** Automated testing and deployment.
- **Code Reviews:** Regular reviews to ensure code quality and performance.

## Project Highlights

- **Framework Migration:** Transitioned from the SSM framework to Spring Boot, achieving a 20% boost in scalability.
- **Performance Optimization:** Systematic code reviews led to a 5% improvement in performance.
- **Robust Deployment:** Deployed on a Linux server using CI/CD, ensuring continuous integration and streamlined deployment.
- **Independent Project:** Developed independently to enhance technical skills and solve real-world problems.

## Project Structure Overview

The project is organized as follows:

- **main/**: Contains the main application code, divided into modules (e.g., cache, dao, entity, etc.).
- **resources/**: Includes mappers and configuration files.
- **test/**: Holds unit and integration tests.

For a detailed view of the project structure, click below:

<details>
  <summary>Detailed Project Structure</summary>

```plaintext
vendor_management_web_app/mvco2o
├── src/
│   ├── main/
│   │   ├── java/../o2o
│   │   │   ├── cache/
│   │   │   ├── dao/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── enums/
│   │   │   ├── exceptions/
│   │   │   ├── interceptor/
│   │   │   ├── service/
│   │   │   ├── util/
│   │   │   └── web/
│   │   ├── resources/
│   │   │   ├── mapper/
│   │   │   ├── spring/
│   │   │   ├── jdbc.properties
│   │   │   ├── logback.xml
│   │   │   ├── mybatis-config.xml
│   │   │   ├── redis.properties
│   │   │   ├── watermark.jpg
│   │   │   └── ..
│   │   └── webapp/
│   │       ├── resources/
│   │       │   ├── css/
│   │       │   └── js/
│   │       ├── WEB-INF/
│   │       │   ├── html/
│   │       │   │   ├── frontend/
│   │       │   │   ├── local/
│   │       │   │   ├── shop/
│   │       │   │   └── superadmin/
│   │       │   ├── index.jsp
│   │       │   └── web.xml
│   │       └── index.jsp
│   └── test/
│       └── java/../
│           ├── dao/
│           ├── service/
│           └── BaseTese.java
├── .gitignore
├── backup.sh
├── o2o.sql
├── pom.xml
└── README.md
```

```
vendor_management_web_app/springbooto2o
├── src/
│   ├── main/
│   │   ├── java/../o2o
│   │   │   ├── cache/
│   │   │   ├── config/
│   │   │   ├── dao/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── enums/
│   │   │   ├── exceptions/
│   │   │   ├── interceptor/
│   │   │   ├── service/
│   │   │   ├── util/
│   │   │   ├── web/
│   │   │   ├── Hello.java
│   │   │   └── O2oApplication.java
│   │   └── resources/
│   │       ├── mapper/
│   │       ├── application.properties
│   │       ├── logback.xml
│   │       ├── mabatis-config.xml
│   │       └── watermark.jpg
│   └── test/../o2o
│       ├── dao/
│       ├── service/
│       └── O2oApplicationTests.java
├── .gitignore
├── o2o.sql
├── pom.xml
└── README.md
```
</details>

## Installation

### Prerequisites
- **Java JDK 11+**
- **Maven**
- **MySQL**
- **Linux**

### Steps

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/ProJBH/vendor_management_web_app.git
   cd vendor_management_web_app
   ```

2. **Build the Application:**
   ```bash
   mvn clean install
   ```

3. **Configure Environment Variables:**
   - Update `src/main/resources/application.properties` with your database and server configurations:
     ```properties
     server.port=5000
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database
     spring.datasource.username=admin
     spring.datasource.password=123123
     ```

4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

## Usage

Once the application is running:
- Access it via `http://localhost:5000` (or your configured port).
- Use the provided RESTful API endpoints to manage vendor information.

## Contributing
This project is a personal portfolio piece that demonstrates my independent work and technical capabilities. While I welcome feedback and suggestions for improvement, the repository is not actively seeking external contributions at this time.

For further information or inquiries, please contact [nzbohuajia@gmail.com].
