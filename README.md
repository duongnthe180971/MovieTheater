# Movie Theater Booking System

Một dự án Fullstack cho hệ thống quản lý và đặt vé rạp chiếu phim trực tuyến. Dự án bao gồm hai phần độc lập: **Backend** (RESTful API với Java Spring Boot) và **Frontend** (Giao diện người dùng với ReactJS & Vite).

## Công nghệ sử dụng

### Backend (`/Backend/be-movie-theater`)
- **Framework:** Java Spring Boot
- **Build Tool:** Maven
- **Database & ORM:** MySQL, Spring Data JPA, Hibernate
- **Bảo mật:** Spring Security, JWT (JSON Web Tokens)
- **Tích hợp:** Cổng thanh toán VNPay, JavaMailSender (Gửi email OTP/Vé)
- **Kiến trúc:** Domain-Driven Design (phân chia module theo nghiệp vụ)

### Frontend (`/Frontend/fe-movie-theater`)
- **Core:** ReactJS, Vite
- **Routing:** React Router DOM
- **UI/UX:** Bootstrap, Material-UI (MUI), TailwindCSS, Framer Motion
- **Quản lý state & Gọi API:** Axios

---

## Cấu trúc Dự án

Dự án được chia thành 2 thư mục chính để quản lý code hoàn toàn độc lập:

```text
.
├── Backend/
│   └── be-movie-theater/
│       ├── src/main/java/com/group3/be/movie/theater/
│       │   ├── config/          # Cấu hình bảo mật, CORS, VNPay
│       │   ├── domain/          # Chứa các module nghiệp vụ chính:
│       │   │   ├── account/     # Xử lý Auth, OTP, Email
│       │   │   ├── movie/       # Quản lý phim, danh mục
│       │   │   ├── schedule/    # Quản lý suất chiếu
│       │   │   ├── seat/        # Quản lý ghế, trạng thái ghế
│       │   │   ├── ticket/      # Xử lý đặt vé
│       │   │   └── vnpay/       # Logic xử lý giao dịch thanh toán
│       │   └── util/            # Các hàm dùng chung (validation, file handling, constants)
│       ├── Image/               # Thư mục lưu trữ ảnh (movies, promotions, users)
│       └── pom.xml              # Cấu hình thư viện Maven
│
└── Frontend/
    └── fe-movie-theater/
        ├── src/
        │   ├── api/             # Base API và các hàm gọi xuống Backend
        │   ├── components/      # UI components tái sử dụng
        │   ├── layouts/         # Layout chung (Header, Footer, FilterMovieHeader)
        │   ├── pages/           # Chứa các trang giao diện chính:
        │   │   ├── admin/       # Giao diện quản trị (Dashboard, Quản lý tài khoản, phim, suất chiếu...)
        │   │   ├── auth/        # Giao diện xác thực
        │   │   ├── client/      # Giao diện khách hàng (Trang chủ, Đặt vé, Hóa đơn, Profile...)
        │   │   └── payment/     # Trạng thái thanh toán (Callback từ VNPay)
        │   └── routes/          # Cấu hình luồng di chuyển (React Router)
        └── package.json         # Cấu hình thư viện NPM/Yarn
```

---

## Tính năng chính

- **Đặt vé & Xử lý giao dịch:** Tích hợp cổng thanh toán VNPay Sandbox và tự động rollback (nhả ghế) nếu giao dịch bị hủy hoặc thanh toán lỗi.
- **Phân quyền Admin / Client:** Chia luồng rõ ràng. Khách hàng có trang riêng để chọn phim, đặt vé, quản lý hồ sơ. Admin có Dashboard riêng để thêm phim, tạo suất chiếu, phòng chiếu và quản lý mã giảm giá.
- **Xác thực & Bảo mật:** Đăng nhập/Đăng ký sử dụng token JWT. Tích hợp tính năng gửi mã OTP qua email. Toàn bộ mật khẩu DB và key hệ thống được bảo mật qua biến môi trường (`.env`).
- **Quản lý File:** API hỗ trợ upload và lấy ảnh tĩnh (poster phim, avatar người dùng, banner) lưu trữ trực tiếp tại thư mục local của backend.
- **Giao diện:** Dựng bằng Vite + React, dùng Material UI (MUI) và Bootstrap để lên layout nhanh, kết hợp TailwindCSS và Framer Motion để làm hiệu ứng.

---

## 🛠️ Hướng dẫn Cài đặt & Khởi chạy

### 1. Yêu cầu hệ thống
- **Java 17** trở lên
- **Node.js** (Phiên bản LTS) & NPM/Yarn
- **MySQL Server** (Đang chạy ở port 3306)

### 2. Thiết lập Backend
1. Di chuyển vào thư mục backend:
   ```bash
   cd Backend/be-movie-theater
   ```
2. Cấu hình biến môi trường: Tạo file `.env` ở thư mục gốc của backend (ngang hàng `pom.xml`) và thêm các thông tin sau:
   ```env
   DB_USERNAME=root
   DB_PASSWORD=mật_khẩu_của_bạn
   JWT_SECRET=chuỗi_secret_jwt_của_bạn
   VNPAY_TMN_CODE=mã_tmn_code
   VNPAY_HASH_SECRET=mã_hash_secret
   GMAIL_USERNAME=email_của_bạn@gmail.com
   GMAIL_PASSWORD=mật_khẩu_ứng_dụng_gmail
   ```
3. Khởi chạy server:
   - Dùng Terminal: `mvn spring-boot:run`
   - **Lưu ý:** Bạn không cần tạo database thủ công. Hệ thống sẽ tự động tạo database `db_movie_theater` và toàn bộ các bảng khi chạy lần đầu tiên. Server sẽ chạy tại: `http://localhost:8080`.

### 3. Thiết lập Frontend
1. Mở một terminal mới, di chuyển vào thư mục frontend:
   ```bash
   cd Frontend/fe-movie-theater
   ```
2. Cài đặt các thư viện cần thiết:
   ```bash
   npm install
   ```
3. Khởi chạy ứng dụng với Vite:
   ```bash
   npm run dev
   ```
4. Truy cập giao diện web tại đường dẫn Localhost hiển thị trên terminal (thường là `http://localhost:5173`).

---
