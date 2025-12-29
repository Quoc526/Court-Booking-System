# 🏟️ HƯỚNG DẪN ĐẦY ĐỦ HỆ THỐNG ĐẶT SÂN - COURT BOOKING SYSTEM

## 📑 Mục lục
1. [Yêu cầu môi trường](#-yêu-cầu-môi-trường)
2. [Hướng dẫn cài đặt](#-hướng-dẫn-cài-đặt)
3. [Chạy hệ thống](#-chạy-hệ-thống)
4. [Hướng dẫn User sử dụng](#-hướng-dẫn-user-sử-dụng)
5. [Hướng dẫn Court Owner sử dụng](#-hướng-dẫn-court-owner-sử-dụng)

---

## 🔧 Yêu cầu môi trường

### Chạy với Docker (Khuyến nghị)
- **Docker Desktop** 20.10 trở lên
- **Docker Compose** V2
- **RAM**: Tối thiểu 4GB RAM trống
- **Disk**: 2GB dung lượng trống
- **OS**: macOS, Windows, hoặc Linux

### Chạy Local với Maven
- **JDK**: 17 hoặc cao hơn
- **Maven**: 3.8+ 
- **MySQL**: 8.0+
- **RAM**: Tối thiểu 2GB RAM trống
- **Port**: 8080 (app), 3306 (MySQL) phải available

---

## 📥 Hướng dẫn cài đặt

### Option 1: Sử dụng Docker (Khuyến nghị)

#### Bước 1: Clone repository
```bash
git clone https://github.com/Quoc526/Court-Booking-System.git
cd Court-Booking-System
```

#### Bước 2: Kiểm tra Docker
```bash
# Kiểm tra Docker đã cài đặt chưa
docker --version
docker compose version
```

Nếu chưa có Docker, tải về tại: https://www.docker.com/products/docker-desktop

#### Bước 3: Chuẩn bị môi trường
```bash
# Dọn dẹp container cũ (nếu có)
docker compose down -v
docker system prune -f
```

### Option 2: Chạy Local với Maven

#### Bước 1: Clone repository
```bash
git clone https://github.com/Quoc526/Court-Booking-System.git
cd Court-Booking-System
```

#### Bước 2: Cài đặt MySQL
```bash
# Tạo database
mysql -u root -p
CREATE DATABASE court_booking;
exit;
```

#### Bước 3: Cấu hình kết nối Database
Chỉnh sửa file `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/court_booking
    username: root
    password: your_password_here
```

---

## 🚀 Chạy hệ thống

### Option 1: Docker (Khuyến nghị)

#### Khởi động hệ thống
```bash
# Build và khởi động tất cả services (MySQL + App)
docker compose up --build -d
```

#### Kiểm tra trạng thái
```bash
# Xem trạng thái containers
docker compose ps

# Xem logs
docker compose logs -f app

# Kiểm tra health
curl http://localhost:8080/actuator/health
```

**Kết quả mong đợi:**
```
NAME                  STATUS                   PORTS
court-booking-mysql   Up (healthy)            0.0.0.0:3306->3306/tcp
court-booking-app     Up (healthy)            0.0.0.0:8080->8080/tcp
```

#### Dừng hệ thống
```bash
# Dừng containers
docker compose down

# Dừng và xóa toàn bộ data
docker compose down -v
```

### Option 2: Maven Local

#### Khởi động MySQL
Đảm bảo MySQL đang chạy trên port 3306

#### Build và chạy ứng dụng
```bash
# Build project
mvn clean package -DskipTests

# Chạy ứng dụng
java -jar target/booking-1.0.0.jar

# Hoặc dùng Maven
mvn spring-boot:run
```

#### Kiểm tra
Truy cập: http://localhost:8080

---

## 👤 Hướng dẫn User sử dụng

### 1. Đăng ký tài khoản

#### Bước 1: Truy cập trang đăng ký
- URL: http://localhost:8080/register
- Hoặc click "Register" từ trang chủ

#### Bước 2: Điền thông tin
```
- Full Name: Họ và tên của bạn
- Email: Email hợp lệ (dùng để đăng nhập)
- Phone Number: Số điện thoại
- Password: Mật khẩu (tối thiểu 6 ký tự)
```

#### Bước 3: Submit
Click nút **"Register"** để hoàn tất đăng ký

### 2. Đăng nhập

#### Sử dụng tài khoản demo có sẵn:
```
Email: user@example.com
Password: password123
```

Hoặc đăng nhập bằng tài khoản vừa tạo

### 3. Xem danh sách sân

#### Sau khi đăng nhập, bạn sẽ thấy Dashboard với 3 tabs

**Tab "Available Courts":**
- Hiển thị tất cả các sân có sẵn
- Thông tin: Tên sân, loại, giá/giờ, địa điểm
- Filter theo loại sân: All, Football, Badminton, Tennis, Futsal

**Các sân có sẵn:**
1. **Football Field A** - 200,000 VND/giờ - Sân bóng đá ngoài trời
2. **Badminton Court B** - 100,000 VND/giờ - Sân cầu lông trong nhà
3. **Tennis Court C** - 150,000 VND/giờ - Sân tennis chuyên nghiệp
4. **Futsal Court D** - 180,000 VND/giờ - Sân bóng đá mini

### 4. Đặt sân

#### Bước 1: Chọn sân
Click nút **"Book Now"** ở sân bạn muốn đặt

#### Bước 2: Chọn Sub-Court (nếu có)
Một số sân có nhiều sub-courts, chọn sub-court phù hợp

#### Bước 3: Chọn lịch (Schedule)
- Xem danh sách các khung giờ available
- Thông tin: Ngày, giờ bắt đầu - kết thúc, giá
- Chỉ hiển thị các slot còn trống (Status: AVAILABLE)

#### Bước 4: Thêm ghi chú (tùy chọn)
Nhập ghi chú đặc biệt nếu cần

#### Bước 5: Submit booking
Click **"Book Now"** để hoàn tất

**Kết quả:**
- Booking được tạo với trạng thái **PENDING** (Chờ xác nhận)
- Bạn sẽ nhận được thông báo thành công
- Booking xuất hiện trong tab "My Bookings"

### 5. Xem lịch sử đặt sân

**Tab "My Bookings":**
- Hiển thị tất cả bookings của bạn
- Thông tin chi tiết:
  - Booking ID
  - Court Name
  - Date & Time
  - Status (PENDING/CONFIRMED/DONE/CANCELED)
  - Total Price
  - Note

**Các trạng thái:**
- 🟡 **PENDING**: Chờ court owner xác nhận
- 🟢 **CONFIRMED**: Court owner đã xác nhận
- ✅ **DONE**: Đã hoàn thành, có thể đánh giá
- 🔴 **CANCELED**: Đã bị hủy

**Filter bookings:**
- All Bookings: Tất cả
- Pending: Chờ xác nhận
- Confirmed: Đã xác nhận
- Completed: Hoàn thành
- Canceled: Đã hủy

### 6. Hủy booking

#### Điều kiện hủy:
- Chỉ được hủy booking ở trạng thái PENDING hoặc CONFIRMED
- Phải hủy ít nhất 2 giờ trước giờ chơi

#### Cách hủy:
1. Vào tab "My Bookings"
2. Tìm booking muốn hủy
3. Click nút **"Cancel"**
4. Xác nhận hủy

### 7. Đánh giá sân (Review)

#### Điều kiện đánh giá:
- Chỉ đánh giá được booking đã DONE (Hoàn thành)
- Mỗi booking chỉ đánh giá 1 lần

#### Cách đánh giá:
1. Vào tab "My Bookings"
2. Tìm booking với status DONE
3. Click nút **"Review"**
4. Điền thông tin:
   - Rating: 1-5 sao
   - Comment: Nhận xét chi tiết
   - Image URL: Link ảnh (tùy chọn)
5. Submit review

**Review sẽ được:**
- Gửi đến admin để duyệt
- Hiển thị công khai sau khi được approve

---

## 🏢 Hướng dẫn Court Owner sử dụng

### 1. Đăng ký tài khoản Court Owner

#### Bước 1: Truy cập trang đăng ký owner
- URL: http://localhost:8080/owner/register
- Hoặc từ trang chủ, click "Court Owner Register"

#### Bước 2: Điền thông tin đăng ký
Điền đầy đủ thông tin vào form:
- **Full Name**: Tên chủ sân (VD: Nguyễn Văn A)
- **Email**: Email để đăng nhập (VD: owner@gmail.com)
- **Phone Number**: Số điện thoại liên hệ
- **Business Name**: Tên doanh nghiệp/cơ sở sân (VD: Sân Bóng Hoàng Anh)
- **Password**: Mật khẩu bảo mật (tối thiểu 6 ký tự)

#### Bước 3: Hoàn tất đăng ký
Click nút **"Register as Court Owner"** để tạo tài khoản

✅ **Thành công:** Hệ thống sẽ chuyển bạn đến trang đăng nhập

### 2. Đăng nhập Court Owner

#### Tài khoản demo (để test):
```
Email: owner@example.com
Password: password123
```

#### Các bước đăng nhập:
1. Truy cập: http://localhost:8080/owner/login
2. Nhập email và password
3. Click **"Login"**
4. Hệ thống chuyển đến Dashboard quản lý

### 3. Dashboard Court Owner

Sau khi đăng nhập, bạn sẽ thấy Dashboard với các chức năng quản lý:

#### Trang tổng quan (Overview)
- Thống kê tổng số sân của bạn
- Số lượng bookings hôm nay
- Doanh thu trong tháng
- Bookings đang chờ xác nhận

### 4. Quản lý Sân (Courts Management)

#### Xem danh sách sân
**Vị trí:** Dashboard → My Courts

**Thông tin hiển thị:**
- Tên sân
- Loại sân (Football, Badminton, Tennis...)
- Giá thuê/giờ
- Địa điểm
- Trạng thái (Available/Unavailable)
- Số sub-courts (nếu có)

#### Tạo sân mới
1. Click nút **"Add New Court"**
2. Điền thông tin sân:
   - **Court Name**: VD: "Sân Bóng Số 1"
   - **Court Type**: Chọn loại (Football/Badminton/Tennis/Futsal)
   - **Price/Hour**: VD: 200000 (200,000 VND)
   - **Location**: Địa chỉ sân
   - **Description**: Mô tả chi tiết
   - **Available**: Tick vào nếu sân đang hoạt động
3. Click **"Save"** để lưu

#### Chỉnh sửa thông tin sân
1. Tìm sân cần sửa trong danh sách
2. Click nút **"Edit"** 
3. Cập nhật thông tin cần thiết
4. Click **"Update"** để lưu

#### Đóng/Mở sân
- **Đóng sân tạm thời**: Bỏ tick "Available" → Khách không đặt được
- **Mở lại sân**: Tick vào "Available" → Cho phép đặt

#### Xóa sân
1. Click nút **"Delete"** ở sân muốn xóa
2. Xác nhận xóa
⚠️ **Lưu ý:** Chỉ xóa được sân không có booking đang active

### 5. Quản lý Sub-Courts (Sân con)

Một sân lớn có thể chia thành nhiều sân nhỏ (sub-courts).

**Ví dụ:** 
- Sân Football A có: A1, A2, A3, A4

#### Tạo Sub-Court
1. Vào chi tiết sân chính
2. Click **"Add Sub-Court"**
3. Điền thông tin:
   - **Name**: VD: "Sân A1"
   - **Available**: Tick nếu sân con đang hoạt động
4. Click **"Save"**

#### Quản lý Sub-Courts
- **Bật/Tắt**: Tick/Untick "Available"
- **Đổi tên**: Click "Edit" → Sửa tên → "Update"
- **Xóa**: Click "Delete" → Xác nhận

### 6. Quản lý Lịch (Schedules)

Tạo các khung giờ để khách đặt sân.

#### Tạo lịch mới
1. Chọn sân cần tạo lịch
2. Click **"Add Schedule"**
3. Điền thông tin:
   - **Date**: Chọn ngày (VD: 30/12/2025)
   - **Start Time**: Giờ bắt đầu (VD: 08:00)
   - **End Time**: Giờ kết thúc (VD: 10:00)
   - **Price**: Giá khung giờ này (có thể khác giá mặc định)
   - **Status**: AVAILABLE (Còn trống)
4. Click **"Save"**

#### Xem lịch đã tạo
- Lọc theo ngày/tuần/tháng
- Xem trạng thái từng khung giờ:
  - 🟢 **AVAILABLE**: Còn trống, chờ đặt
  - 🔴 **BOOKED**: Đã có người đặt

#### Sửa/Xóa lịch
- **Sửa giá**: Click "Edit" → Đổi price → "Update"
- **Xóa lịch**: Chỉ xóa được lịch AVAILABLE (chưa có booking)

💡 **Tip:** Tạo lịch hàng loạt cho cả tuần để tiết kiệm thời gian

### 7. Quản lý Bookings (Đơn đặt sân)

#### Xem tất cả bookings
**Vị trí:** Dashboard → Bookings Management

**Thông tin hiển thị:**
- Booking ID
- Tên khách hàng
- Sân đã đặt
- Ngày giờ
- Trạng thái
- Tổng tiền
- Ghi chú của khách

#### Filter bookings
Lọc theo:
- **Status**: PENDING/CONFIRMED/DONE/CANCELED
- **Date**: Ngày đặt
- **Court**: Theo từng sân

#### Xử lý booking PENDING (Chờ xác nhận)

**Khi có booking mới:**
1. Hệ thống thông báo có booking mới
2. Vào "Bookings Management"
3. Xem thông tin booking:
   - Khách hàng
   - Sân & thời gian
   - Số điện thoại
   - Ghi chú

**Xác nhận booking:**
1. Click nút **"Confirm"** 
2. Booking chuyển sang CONFIRMED
3. Khách hàng nhận thông báo đã xác nhận

**Từ chối booking:**
1. Click nút **"Cancel"**
2. Chọn lý do:
   - Sân đang bảo trì
   - Lịch trình xung đột
   - Lý do khác
3. Click **"Confirm Cancel"**
4. Khách hàng nhận thông báo hủy

#### Đánh dấu hoàn thành

**Sau khi khách đã chơi xong:**
1. Tìm booking đã CONFIRMED
2. Click nút **"Mark as Done"**
3. Booking chuyển sang DONE
4. Khách có thể đánh giá sau khi DONE

### 8. Xem báo cáo & Thống kê

#### Báo cáo doanh thu
**Vị trí:** Dashboard → Reports

**Các chỉ số hiển thị:**
- 💰 **Total Revenue**: Tổng doanh thu
- 📊 **Total Bookings**: Tổng số lượt đặt
- 📈 **Average Value**: Giá trị trung bình/booking
- 📅 **Revenue by Date**: Biểu đồ theo ngày

**Filter báo cáo:**
- Chọn khoảng thời gian (Start Date → End Date)
- Chọn sân cụ thể hoặc tất cả sân
- Click **"Generate Report"**

#### Top sân được đặt nhiều nhất
Xem sân nào hot nhất để tối ưu giá và lịch

#### Thống kê theo thời gian
- Theo ngày: Xem doanh thu hôm nay
- Theo tuần: Xu hướng tuần
- Theo tháng: Báo cáo tháng

### 9. Quản lý Reviews (Đánh giá)

#### Xem reviews của sân
**Vị trí:** Court Details → Reviews

**Thông tin review:**
- ⭐ Rating: 1-5 sao
- 👤 User name: Tên người đánh giá
- 💬 Comment: Nhận xét chi tiết
- 🖼️ Image: Ảnh đính kèm (nếu có)
- 📅 Date: Ngày đánh giá
- ✅ Status: PENDING/APPROVED/REJECTED

#### Phản hồi reviews
1. Đọc review của khách
2. Click **"Reply"** để trả lời
3. Viết phản hồi cảm ơn hoặc giải thích
4. Click **"Send"**

⚠️ **Lưu ý:** 
- Reviews phải được Admin duyệt mới hiển thị công khai
- Chủ sân không thể xóa reviews
- Phản hồi tích cực giúp tăng uy tín

---

## 🔑 Tài khoản Demo

### User thường
```
Email: user@example.com
Password: password123
Role: USER
```

### Court Owner
```
Email: owner@example.com
Password: password123
Role: COURT_OWNER
```

### Admin
```
Email: admin@example.com
Password: strongpassword
Role: ADMIN
```

---

## � Mẹo sử dụng hiệu quả

### Cho User:
- 📱 **Đặt sân sớm**: Đặt trước 1-2 ngày để đảm bảo có chỗ
- ⏰ **Lưu ý thời gian hủy**: Hủy trước 2 giờ nếu không sử dụng
- ⭐ **Đánh giá sau khi chơi**: Giúp người khác có thêm thông tin
- 📝 **Ghi chú rõ ràng**: Viết yêu cầu đặc biệt trong Note khi đặt

### Cho Court Owner:
- ⚡ **Xác nhận booking nhanh**: Xử lý PENDING trong 1-2 giờ
- 📅 **Tạo lịch đầy đủ**: Tạo schedule trước 1 tuần
- 💬 **Phản hồi review**: Tương tác với khách hàng
- 📊 **Theo dõi báo cáo**: Kiểm tra doanh thu định kỳ
- 🔧 **Cập nhật thông tin**: Giữ giá và mô tả luôn chính xác

---

## 🐛 Troubleshooting

### Port đã được sử dụng
```bash
# Kiểm tra port 8080
lsof -i :8080

# Kill process
kill -9 <PID>
```

### Docker build failed
```bash
# Xóa toàn bộ cache và build lại
docker system prune -a
docker compose build --no-cache
docker compose up -d
```

### Không kết nối được database
```bash
# Kiểm tra MySQL container
docker compose logs mysql

# Restart MySQL
docker compose restart mysql
```

### Application không start
```bash
# Xem logs chi tiết
docker compose logs -f app

# Kiểm tra health
curl http://localhost:8080/actuator/health
```


---

## 🌐 URLs quan trọng

- **Trang chủ**: http://localhost:8080
- **Login User**: http://localhost:8080/login
- **Register User**: http://localhost:8080/register
- **Login Owner**: http://localhost:8080/owner/login
- **Register Owner**: http://localhost:8080/owner/register
- **Dashboard**: http://localhost:8080/dashboard
- **Health Check**: http://localhost:8080/actuator/health
- **API Docs**: http://localhost:8080/swagger-ui.html (nếu có)

---

## 📧 Liên hệ & Hỗ trợ

- **GitHub**: https://github.com/Quoc526/Court-Booking-System
- **Issues**: https://github.com/Quoc526/Court-Booking-System/issues

---

**Happy Booking! 🎉**
