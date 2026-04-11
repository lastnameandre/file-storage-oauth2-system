# File Storage Service (OAuth2) – Java Spring Boot

📁 **File Storage Service** is a full-featured web application for managing files, designed to demonstrate modern **OAuth2 authentication and authorization with JWT**.

## 🚀 Technologies
- Java 17, Spring Boot 3
- Spring Security, OAuth2, JWT
- Thymeleaf (server-side UI)
- REST API
- MySQL / H2 Database
- SpringDoc OpenAPI (Swagger UI)

## 🏗 Architecture

[OAuth2 Authorization Server] <--JWT--> [Resource Server API] <--REST--> [Thymeleaf Client]


## ✨ Features

### 🔹 Frontend (Thymeleaf)
- OAuth2 login (Authorization Code flow)
- Upload files
- View personal file list
- Download and delete files

### 🔹 Authorization Server
- Custom OAuth2 Authorization Server
- Authorization Code + Refresh Token support
- OpenID Connect support
- In-memory users for demo

### 🔹 Resource Server
- REST API `/api/files`
- JWT-based authentication
- Scope-based authorization:
  - `files.read` → GET requests
  - `files.write` → POST / DELETE

### 🔹 API Documentation (Swagger)
- Auto-generated OpenAPI docs
- Swagger UI interface
- **Authorize button for JWT testing**
- Full CRUD testing via UI

## ⚙️ Getting Started

1. Run Authorization Server → http://localhost:9000  
2. Run Resource Server → http://localhost:8081  
3. Run Client App → http://localhost:8080  
4. Open Swagger UI → http://localhost:8081/swagger-ui.html  

## 📌 Usage

1. Login via OAuth2
2. Upload a file
3. View your files
4. Download or delete files
5. Test API via Swagger UI

## 📊 Architecture Diagram


+----------------+ +-------------------+ +-----------------+
| Thymeleaf UI | ---> | Resource Server | <---> | Authorization |
| (Client App) | | REST API + JWT | | Server OAuth2 |
+----------------+ +-------------------+ +-----------------+


## 💡 Use Cases
- Learning OAuth2 and JWT in real projects
- Backend system for file management
- Integration with mobile or SPA frontend
- Demo project for portfolio / Fiverr / freelance

## 🔐 Environment Variables

DB_PASSWORD=your_password
