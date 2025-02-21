# Vendor Management Web App

A comprehensive vendor management system developed as an independent project (Dec 2023 – Mar 2024) to showcase full-stack and backend development expertise. Initially built using Java with the SSM framework (Spring, Spring MVC, MyBatis), the project was later migrated to Spring Boot—resulting in a 20% improvement in scalability. Deployed on a Linux server with continuous integration and deployment practices, systematic code reviews further boosted performance by 5%. This project highlights a commitment to building scalable, secure, and maintainable applications.

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Highlights](#project-highlights)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Vendor Management Web App is an independent project designed to manage vendor information efficiently. Leveraging modern Spring technologies, the system started with a robust SSM framework and evolved into a Spring Boot application to better meet scalability and performance demands. This project demonstrates advanced backend development using Java, along with solid API design and database management practices.

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

## Project Structure

```
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
│   │   ├── webapp/
│   │   │   ├── resources/
│   │   │   │   ├── css/
│   │   │   │   └── js/
│   │   │   ├── WEB-INF/
│   │   │   │   ├── html/
│   │   │   │   │   ├── frontend/
│   │   │   │   │   ├── local/
│   │   │   │   │   ├── shop/
│   │   │   │   │   └── superadmin/
│   │   │   │   ├── index.jsp
│   │   │   │   └── web.xml
│   │   │   └── index.jsp
│   └── test/
│       ├── java/../
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
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
   - Or run the packaged jar:
     ```bash
     java -jar target/vendor_management_web_app.jar
     ```

## Usage

Once the application is running:
- Access it via `http://localhost:5000` (or your configured port).
- Use the provided RESTful API endpoints to manage vendor information.


For further information or inquiries, please contact [nzbohuajia@gmail.com].
