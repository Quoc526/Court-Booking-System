# 🔐 HƯỚNG DẪN SETUP GOOGLE OAUTH2 LOGIN

## Bước 1: Tạo Google Cloud Project

1. Truy cập [Google Cloud Console](https://console.cloud.google.com/)
2. Click "Select a project" → "NEW PROJECT"
3. Nhập tên project (ví dụ: "Court Booking System")
4. Click "CREATE"

## Bước 2: Enable Google+ API

1. Vào menu bên trái → "APIs & Services" → "Library"
2. Tìm "Google+ API"
3. Click "ENABLE"

## Bước 3: Tạo OAuth 2.0 Credentials

1. Vào menu "APIs & Services" → "Credentials"
2. Click "CREATE CREDENTIALS" → "OAuth client ID"
3. Nếu chưa có OAuth consent screen:
   - Click "CONFIGURE CONSENT SCREEN"
   - Chọn "External" → Click "CREATE"
   - Điền thông tin:
     * App name: Court Booking System
     * User support email: [email của bạn]
     * Developer contact: [email của bạn]
   - Click "SAVE AND CONTINUE" qua các bước
   - Ở phần "Test users", thêm email test của bạn
   - Click "SAVE AND CONTINUE"

4. Quay lại "Credentials" → "CREATE CREDENTIALS" → "OAuth client ID"
5. Chọn "Application type": **Web application**
6. Nhập "Name": Court Booking OAuth
7. Add "Authorized redirect URIs":
   ```
   http://localhost:8080/login/oauth2/code/google
   ```
   (Khi deploy production, thêm URL production)

8. Click "CREATE"
9. Sao chép **Client ID** và **Client Secret**

## Bước 4: Cấu hình Application

### Option 1: Environment Variables (Khuyến nghị cho Production)

```bash
export GOOGLE_CLIENT_ID="your-client-id-here.apps.googleusercontent.com"
export GOOGLE_CLIENT_SECRET="your-client-secret-here"
```

### Option 2: Cập nhật application.yml (Development)

Mở `src/main/resources/application.yml` và thay thế:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_ACTUAL_CLIENT_ID_HERE
            client-secret: YOUR_ACTUAL_CLIENT_SECRET_HERE
```

## Bước 5: Rebuild và Test

```bash
# Stop containers
docker compose down

# Rebuild with new dependencies
docker compose build app

# Start with environment variables
GOOGLE_CLIENT_ID="your-client-id" \
GOOGLE_CLIENT_SECRET="your-client-secret" \
docker compose up -d

# Hoặc edit docker-compose.yml thêm:
environment:
  - GOOGLE_CLIENT_ID=your-client-id-here
  - GOOGLE_CLIENT_SECRET=your-client-secret-here
```

## Bước 6: Test Google Login

1. Mở http://localhost:8080/login/user
2. Click nút "Continue with Google"
3. Chọn tài khoản Google
4. Cho phép quyền truy cập
5. Sẽ tự động redirect về /dashboard

## 📝 Lưu Ý Quan Trọng

### Khi Deploy Production:

1. **Update Authorized redirect URIs** trong Google Console:
   ```
   https://yourdomain.com/login/oauth2/code/google
   ```

2. **Đưa OAuth consent screen ra Production**:
   - Vào "OAuth consent screen"
   - Click "PUBLISH APP"
   - Submit verification (nếu cần)

3. **Bảo mật Client Secret**:
   - KHÔNG commit vào Git
   - Dùng environment variables hoặc secrets manager
   - Rotate secret định kỳ

### Troubleshooting:

**Error: redirect_uri_mismatch**
- Check lại Authorized redirect URIs phải khớp chính xác
- Format: `http://localhost:8080/login/oauth2/code/google`

**Error: access_denied**
- Kiểm tra email đã được thêm vào Test users chưa
- OAuth consent screen phải được configure đúng

**Error: invalid_client**
- Client ID hoặc Client Secret sai
- Check lại environment variables

## 🎯 Các Tính Năng Đã Implement

✅ **Google OAuth2 Login**
- Đăng nhập nhanh bằng tài khoản Google
- Tự động tạo user mới nếu chưa tồn tại
- Không cần nhập password

✅ **Email Validation Chặt Chẽ**
- Regex pattern validation
- Format: username@domain.ext

✅ **Password Mạnh**
- Tối thiểu 8 ký tự
- Phải có: chữ hoa, chữ thường, số, ký tự đặc biệt
- Pattern: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$`

✅ **Phone Number Vietnam**
- Format: 0912345678 hoặc +84912345678
- Pattern: `^(0|\+84)[0-9]{9,10}$`

✅ **Duplicate Email Check**
- Kiểm tra cả 2 bảng: `users` và `court_owners`
- Throw `BookingException` nếu email đã tồn tại

✅ **Full Name Validation**
- Chỉ cho phép chữ cái và khoảng trắng
- Hỗ trợ tiếng Việt có dấu
- Min 2 ký tự, max 100 ký tự

## 📚 Testing

### Test Email Duplicate:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
    "fullName": "Test User",
    "email": "user@example.com",
    "password": "Test@123",
    "phoneNumber": "0912345678",
    "roleType": "USER"
  }'
# Expected: "Email already exists"
```

### Test Password Weak:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
    "fullName": "New User",
    "email": "newuser@example.com",
    "password": "weak",
    "phoneNumber": "0912345678",
    "roleType": "USER"
  }'
# Expected: Validation error - password must be strong
```

### Test Phone Invalid:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
    "fullName": "New User",
    "email": "newuser@example.com",
    "password": "Strong@123",
    "phoneNumber": "123",
    "roleType": "USER"
  }'
# Expected: Validation error - invalid phone format
```
