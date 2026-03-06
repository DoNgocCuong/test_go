# 🦉 Test Golden Owl

Ứng dụng tra cứu điểm thi THPT 2024 — gồm Backend (Spring Boot), Frontend (Vite/React) và MySQL, chạy hoàn toàn qua Docker.

---

## 📋 Yêu cầu

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) đã được cài và đang chạy
- Git

---

## 🚀 Hướng dẫn chạy

### 1. Clone repo

```bash
git clone <repo_url>
cd test-go
```

### 2. Sửa file `.env`

File `.env` ở thư mục gốc (cùng cấp với `docker-compose.yml`) với nội dung sau:

```env
DB_PASSWORD=yourpassword
DB_HOST=mysql
DB_PORT=3306
DB_NAME=test_golden
DB_USERNAME=root
PORT=8080
VITE_API_URL=http://localhost:8080
```

> ⚠️ Thay `yourpassword` bằng mật khẩu bạn muốn đặt cho MySQL.

### 3. Chạy Docker

```bash
docker-compose up -d --build
```

### 4. Chờ import dữ liệu

Backend sẽ tự động tải và import dữ liệu điểm thi.

---

## 🌐 Truy cập ứng dụng

| Service  | URL                          |
|----------|------------------------------|
| Frontend | http://localhost:3000        |
| Backend  | http://localhost:8080        |
| MySQL    | localhost:3307               |

---

## 📁 Cấu trúc project

```
test-golden/
├── Backend/
│   └── test_golden_owl/     # Spring Boot API
├── Frontend/
│   └── test-golden-fe/      # Vite + React
├── docker-compose.yml
└── .env                     # ← Bạn tự tạo, không commit lên git
```
