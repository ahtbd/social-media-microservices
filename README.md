# Social Media Mini Backend

## Microservices-Based Cloud Application Development

### 📌 Course Information
- **Course:** Software Service Engineering (YN3012180059)
- **Institution:** Yunnan University, School of Software and AI
- **Semester:** Spring 2026
- **Teacher:** Ahmed Zahir
- **Assignment:** Mid-term Project
- **Topic:** Option C - Social Media Mini Backend

---
### 👨‍💻 Student Information
- **Name:** Tuhin Md Abu Hamza
- **Student ID:** 20233120013
- **Major:** Software

## 🏗️ Architecture

```
                    ┌─────────────────────────────────┐
                    │     API Gateway (Port 8080)     │
                    │    Spring Cloud Gateway         │
                    └──────────┬──────────────────────┘
                               │
         ┌─────────────────────┼─────────────────────┐
         │                     │                     │
    ┌────▼──────┐    ┌────────▼─────┐    ┌──────────▼──┐    ┌──────────────┐
    │   User    │    │     Post     │    │   Comment   │    │     Like     │
    │  Service  │    │    Service   │    │   Service   │    │   Service    │
    │  :8081    │    │    :8082     │    │   :8083     │    │    :8084     │
    └────┬──────┘    └──────┬──────┘    └──────┬──────┘    └──────┬───────┘
         │                  │                  │                  │
    ┌────▼──────┐    ┌──────▼──────┐    ┌──────▼──────┐    ┌──────▼───────┐
    │ PostgreSQL │    │ PostgreSQL  │    │   MongoDB   │    │  PostgreSQL  │
    │  users_db  │    │  posts_db   │    │ comments_db │    │   likes_db   │
    └───────────┘    └─────────────┘    └─────────────┘    └──────────────┘
```

---

## 🛠️ Technology Stack

| Service | Language | Framework | Build Tool | Database |
|---------|----------|-----------|------------|----------|
| User Service | Java 17 | Spring Boot 3.2.5 | Gradle 8.5 | PostgreSQL |
| Post Service | Java 17 | Spring Boot 3.2.5 | Gradle 8.5 | PostgreSQL |
| Comment Service | Node.js | Express.js | npm | MongoDB |
| Like Service | Java 17 | Spring Boot 3.2.5 | Gradle 8.5 | PostgreSQL |
| API Gateway | Java 17 | Spring Cloud Gateway | Gradle 8.5 | - |

---

## 📁 Project Structure

```
social-media-microservices/
├── user-service/                  # Spring Boot - User Management
│   ├── build.gradle
│   ├── settings.gradle
│   ├── Dockerfile
│   └── src/
├── post-service/                  # Spring Boot - Post Management
│   ├── build.gradle
│   ├── settings.gradle
│   ├── Dockerfile
│   └── src/
├── comment-service/               # Node.js/Express - Comments
│   ├── package.json
│   ├── Dockerfile
│   └── src/
├── like-service/                  # Spring Boot - Like System
│   ├── build.gradle
│   ├── settings.gradle
│   ├── Dockerfile
│   └── src/
├── api-gateway/                   # Spring Cloud Gateway
│   ├── build.gradle
│   ├── settings.gradle
│   ├── Dockerfile
│   └── src/
├── docs/                          # Documentation & Screenshots
│   └── images/
├── postman/                       # Postman Collection
│   └── social-media-api.json
├── test-scripts/                  # Automated Test Script
│   └── test-all.sh
├── docker-compose.yml
└── README.md
```

---

## 🔌 API Endpoints

### User Service (`/api/users`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Register new user |
| GET | `/{id}` | Get user by ID |
| GET | `/search?username=` | Search user by username |
| DELETE | `/{id}` | Delete user |

### Post Service (`/api/posts`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create new post |
| GET | `/{id}` | Get post by ID |
| GET | `/user/{userId}` | Get user's posts |
| GET | `/` | Get all posts (timeline) |
| DELETE | `/{id}` | Delete post |

### Comment Service (`/api/comments`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Add comment |
| GET | `/post/{postId}` | Get post comments |
| DELETE | `/{id}` | Delete comment |

### Like Service (`/api/likes`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/toggle?userId=&postId=` | Toggle like/unlike |
| GET | `/count/{postId}` | Get like count |

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17 (for local development)
- Gradle 8.5 (for local development)
- Node.js 18+ (for comment service)

### 1. Clone Repository
```bash
git clone https://github.com/ahtbd/social-media-microservices.git
cd social-media-microservices
```

### 2. Start All Services (Docker)
```bash
docker compose up -d --build
```

### 3. Check Status
```bash
docker compose ps
```

All 9 containers should show `healthy` or `Up` status.

### 4. Run Tests
```bash
./test-scripts/test-all.sh
```

### 5. Stop All Services
```bash
docker compose down
```

### 6. Fresh Start (Remove volumes)
```bash
docker compose down -v
docker compose up -d --build
```

---

## 🧪 Testing

### Run Unit Tests
```bash
# User Service (5 tests)
cd user-service && ./gradlew test && cd ..

# Post Service (3 tests)
cd post-service && ./gradlew test && cd ..

# Like Service (3 tests)
cd like-service && ./gradlew test && cd ..
```

### Run Semi-Manual Test Script
```bash
./test-scripts/test-all.sh
```

### Test Results
| Test Type | Tests | Passed | Failed |
|-----------|-------|--------|--------|
| Unit Tests (JUnit) | 11 | 11 | 0 |
| Semi-Manual Script | 7 | 7 | 0 |
| **Total** | **18** | **18** | **0** |

---

## 🐳 Docker Commands

| Command | Description |
|---------|-------------|
| `docker compose up -d --build` | Build & start all services |
| `docker compose ps` | Check container status |
| `docker compose logs` | View all logs |
| `docker compose logs [service]` | View specific service logs |
| `docker compose restart` | Restart all services |
| `docker compose down` | Stop all services |
| `docker compose down -v` | Stop + remove volumes |

---

## 🗄️ Database Access

### PostgreSQL (users_db)
```bash
docker exec -it users-db psql -U admin -d users_db
```

### PostgreSQL (posts_db)
```bash
docker exec -it posts-db psql -U admin -d posts_db
```

### MongoDB (comments_db)
```bash
docker exec -it comments-db mongosh -u admin -p admin123
```

### PostgreSQL (likes_db)
```bash
docker exec -it likes-db psql -U admin -d likes_db
```

---

## 📊 Development Progress

| Step | Description | Status |
|------|-------------|--------|
| 1 | Project Structure + Docker Databases | ✅ |
| 2 | User Service (Spring Boot + PostgreSQL) | ✅ |
| 3 | Post Service (Spring Boot + PostgreSQL) | ✅ |
| 4 | Comment Service (Node.js + MongoDB) | ✅ |
| 5 | Like Service (Spring Boot + PostgreSQL) | ✅ |
| 6 | API Gateway (Spring Cloud Gateway) | ✅ |
| 7 | Dockerfiles + docker-compose | ✅ |
| 8 | Unit Tests (11 tests) | ✅ |
| 9 | Test Script + Postman Collection | ✅ |
| 10 | Gradle Migration (Maven → Gradle) | ✅ |

---

## ⭐ Features

- ✅ Microservices Architecture (4 independent services)
- ✅ RESTful API Design
- ✅ API Gateway (Single Entry Point - Port 8080)
- ✅ Database per Service Pattern
- ✅ Docker Containerization (One-command deploy)
- ✅ Unit Tests (JUnit 5 + Mockito)
- ✅ Automated Test Scripts (Bash)
- ✅ Postman Collection
- ✅ Gradle Build System
- ✅ Inter-service Communication
- ✅ Input Validation
- ✅ Error Handling

---

## 📄 Documentation Files

- `docs/images/` - Step-by-step screenshots
- `postman/social-media-api.json` - Postman API collection
- `test-scripts/test-all.sh` - Automated test script

---

## 🔧 Local Development

### Run User Service
```bash
cd user-service && ./gradlew bootRun
```

### Run Post Service
```bash
cd post-service && ./gradlew bootRun
```

### Run Comment Service
```bash
cd comment-service && node src/app.js
```

### Run Like Service
```bash
cd like-service && ./gradlew bootRun
```

### Run API Gateway
```bash
cd api-gateway && ./gradlew bootRun
```

---

## 📝 License

This project is part of academic coursework for Yunnan University, School of Software and AI.

---

## 👨‍💻 Author

- **Name:** Tuhin Md Abu Hamza
- **Student ID:** 20233120013
- **University:** Yunnan University
- **Course:** Software Service Engineering (YN3012180059)
- **Semester:** Spring 2026
- **GitHub:** [ahtbd](https://github.com/ahtbd)

---
*Last Updated: 10 May 2026*