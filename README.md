# File Storage Service (OAuth2)

Secure microservice-based file storage platform built with Java Spring Boot.

### 🚀 Tech Stack
Java 17 • Spring Boot 3 • OAuth2 • JWT • MySQL • Swagger • Thymeleaf

### 📌 Features
✔ OAuth2 Authorization Server  
✔ JWT-secured Resource Server  
✔ File Upload / Download / Delete  
✔ Swagger UI API Docs  
✔ Scope-based Access Control

## 🏗 Architecture
                ┌──────────────────────────┐
                │ OAuth2 Authorization     │
                │ Server (9000)            │
                │ - Login                  │
                │ - Consent                │
                │ - JWT Tokens             │
                └──────────┬───────────────┘
                           │
                           │ Access Token
                           ▼

┌──────────────────────┐        REST API        ┌──────────────────────┐
│ Client App           │ ─────────────────────▶ │ Resource Server      │
│ Thymeleaf (8080)     │                        │ File Storage API     │
│ - Login              │ ◀───────────────────── │ - Upload             │
│ - File Dashboard     │        JSON/Data       │ - Download           │
└──────────────────────┘                        │ - Delete             │
                                                │ - JWT Validation     │
                                                └──────────────────────┘

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

+-------------------+       +------------------------+       +----------------------+
| Client App        | ----> | Authorization Server   | ----> | JWT Access Token     |
| Thymeleaf (8080)  |       | OAuth2 / OIDC (9000)   |       | issued to client     |
+-------------------+       +------------------------+       +----------------------+
        |
        | Bearer Token + REST Calls
        v
+------------------------+
| Resource Server        |
| File API (8081)        |
| Upload / Download      |
| Delete / Validate JWT  |
+------------------------+


## 💡 Use Cases
- Learning OAuth2 and JWT in real projects
- Backend system for file management
- Integration with mobile or SPA frontend
- Demo project for portfolio / Fiverr / freelance

## 🔐 Environment Variables

DB_PASSWORD=your_password

## Demo Login

username: user1  
password: password

How to Run

1. Start auth-server
2. Start resource-server
3. Start client
4. Open localhost:8080
