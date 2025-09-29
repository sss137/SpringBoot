-- ==================================================================================
-- 물리 모델 DDL 스크립트
-- 
-- * 관계 설정
--   1) 사용자 - 주문      : 한 사용자는 여러 주문을 할 수 있음 (1:N)
--   2) 사용자 - 결제      : 한 사용자는 여러 결제를 할 수 있음 (1:N)
--   3) 주문 - 상품        : 다대다 관계 (주문상품 테이블을 통해서 연결)
--   4) 카테고리 자기 참조 : 상위 카테고리는 여러 하위 카테고리를 가질 수 있음 (1:N)
--   5) 카테고리 - 상품    : 한 카테고리에 여러 상품이 속할 수 있음 (1:N)
--   6) 주문 - 결제        : 다대다 관계 (주문결제 테이블을 통해서 연결)
-- ==================================================================================

DROP DATABASE IF EXISTS db_jpa;
CREATE DATABASE db_jpa;

USE db_jpa;

-- 1. 기존 테이블 삭제 (역순)
DROP TABLE IF EXISTS order_payment;
DROP TABLE IF EXISTS order_product;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS access_logs;

-- 2. 테이블 생성 (의존성 순서)

-- 로그 테이블 (외래키 없음)
CREATE TABLE access_logs (
    log_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    log_type VARCHAR(30) NOT NULL,
    log_message VARCHAR(500) NOT NULL,
    log_level VARCHAR(20) NOT NULL DEFAULT 'INFO',
    client_ip VARCHAR(45),
    user_agent VARCHAR(200),
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자 테이블
CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(200),
    birth_date DATE,
    join_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    member_grade VARCHAR(20) NOT NULL DEFAULT 'GENERAL',
    withdraw_yn BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 카테고리 테이블
CREATE TABLE categories (
    category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL,
    parent_category_id INT,
    FOREIGN KEY (parent_category_id) REFERENCES categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 상품 테이블
CREATE TABLE products (
    product_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    product_price INT NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    sale_status_yn BOOLEAN NOT NULL DEFAULT TRUE,
    product_description TEXT,
    register_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 주문 테이블
CREATE TABLE orders (
    order_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_date DATE NOT NULL,
    order_time TIME NOT NULL,
    total_order_amount INT NOT NULL,
    order_status VARCHAR(20) NOT NULL DEFAULT 'RECEIVED',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
      ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 결제 테이블
CREATE TABLE payments (
    payment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    payment_date DATE NOT NULL,
    payment_time TIME NOT NULL,
    payment_amount INT NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
      ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 주문상품 테이블
CREATE TABLE order_product (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    order_quantity INT NOT NULL,
    unit_price INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
      ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 주문결제 테이블
CREATE TABLE order_payment (
    order_id INT NOT NULL,
    payment_id INT NOT NULL,
    PRIMARY KEY (order_id, payment_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
      ON DELETE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES payments(payment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 샘플 데이터 INSERT

-- 로그 데이터 (8개)
INSERT INTO access_logs (log_type, log_message, log_level, client_ip, user_agent, create_date) VALUES
('USER_LOGIN', '사용자 로그인 성공', 'INFO', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0)', '2025-09-02 09:15:23'),
('PRODUCT_VIEW', '상품 상세페이지 조회', 'INFO', '192.168.1.105', 'Mozilla/5.0 (iPhone; CPU iPhone OS)', '2025-09-02 10:30:15'),
('ORDER_CREATE', '주문 생성 완료', 'INFO', '192.168.1.120', 'Mozilla/5.0 (Macintosh; Intel Mac OS)', '2025-09-02 11:45:30'),
('PAYMENT_ERROR', '결제 처리 중 오류 발생', 'ERROR', '192.168.1.135', 'Mozilla/5.0 (Linux; Android 13)', '2025-09-01 15:20:45'),
('SYSTEM_MAINTENANCE', '시스템 점검 시작', 'WARN', '127.0.0.1', 'System Process', '2025-09-01 02:00:00'),
('DATABASE_BACKUP', '데이터베이스 백업 완료', 'INFO', '127.0.0.1', 'Backup Service', '2025-09-01 03:30:00'),
('API_CALL', '외부 API 호출 실패', 'ERROR', '10.0.0.50', 'RestTemplate/4.3', '2025-08-31 14:25:10'),
('USER_LOGOUT', '사용자 로그아웃', 'INFO', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0)', '2025-08-31 18:45:55');

-- 사용자 데이터 (10개)
INSERT INTO users (username, email, phone_number, address, birth_date, member_grade) VALUES
('김철수', 'kim.cs@email.com', '010-1234-5678', '서울시 강남구', '1990-05-15', 'VIP'),
('이영희', 'lee.yh@email.com', '010-2345-6789', '서울시 서초구', '1985-08-22', 'GENERAL'),
('박민수', 'park.ms@email.com', '010-3456-7890', '부산시 해운대구', '1992-12-03', 'GENERAL'),
('최수진', 'choi.sj@email.com', '010-4567-8901', '대구시 중구', '1988-03-18', 'SILVER'),
('정대호', 'jung.dh@email.com', '010-5678-9012', '인천시 남동구', '1995-07-25', 'GENERAL'),
('강미래', 'kang.mr@email.com', '010-6789-0123', '광주시 서구', '1991-11-08', 'GOLD'),
('윤하늘', 'yoon.hn@email.com', '010-7890-1234', '대전시 유성구', '1987-09-14', 'VIP'),
('임바다', 'lim.bd@email.com', '010-8901-2345', '울산시 남구', '1993-04-30', 'GENERAL'),
('한별님', 'han.bn@email.com', '010-9012-3456', '경기도 수원시', '1989-01-12', 'SILVER'),
('조은별', 'cho.eb@email.com', '010-0123-4567', '충남 천안시', '1994-06-27', 'GENERAL');

-- 카테고리 데이터 (계층 구조 - 전자제품)
INSERT INTO categories (category_name, parent_category_id) VALUES
('전자제품', NULL),
('컴퓨터', NULL),
('스마트폰', NULL),
('노트북', 2),
('데스크톱', 2),
('태블릿', 2),
('iPhone', 3),
('갤럭시', 3),
('헤드폰', 1),
('스마트워치', 1);

-- 상품 데이터 (전자제품 기반)
INSERT INTO products (product_name, product_price, stock_quantity, product_description, category_id) VALUES
('MacBook Pro 14인치', 2890000, 15, 'M3 Pro 칩, 16GB 메모리, 512GB SSD', 4),
('iPad Air 5세대', 899000, 25, '10.9인치 Liquid Retina 디스플레이, M1 칩', 6),
('iPhone 15 Pro', 1550000, 30, '6.1인치 Super Retina XDR, A17 Pro 칩', 7),
('갤럭시 S24 Ultra', 1698000, 20, '6.8인치 Dynamic AMOLED, S펜 포함', 8),
('AirPods Pro 2세대', 359000, 50, '액티브 노이즈 캔슬링, MagSafe 케이스', 9),
('Apple Watch Ultra', 1149000, 12, '49mm 티타늄 케이스, GPS + Cellular', 10),
('LG 그램 17인치', 2190000, 8, 'Intel 13세대 i7, 32GB RAM, 1TB SSD', 4),
('삼성 갤럭시 탭 S9+', 1099000, 18, '12.4인치 Super AMOLED, S펜 포함', 6),
('Sony WH-1000XM5', 459000, 35, '무선 노이즈 캔슬링 헤드폰, 30시간 재생', 9),
('맥 미니 M2', 899000, 22, 'M2 칩, 8GB 메모리, 256GB SSD', 5),
('갤럭시 워치 6', 369000, 40, '40mm/44mm, 건강 모니터링 기능', 10),
('iPad Pro 12.9인치', 1699000, 10, 'M2 칩, 128GB, Liquid Retina XDR', 6);

-- 주문 데이터 (전자제품 구매)
INSERT INTO orders (order_date, order_time, total_order_amount, order_status, user_id) VALUES
('2025-09-01', '10:30:00', 2890000, 'COMPLETED', 1),
('2025-09-01', '11:15:00', 1550000, 'COMPLETED', 2),
('2025-09-01', '14:00:00', 1558000, 'PROCESSING', 3),
('2025-08-31', '15:20:00', 1699000, 'COMPLETED', 4),
('2025-08-31', '16:45:00', 459000, 'COMPLETED', 5),
('2025-08-30', '17:30:00', 3548000, 'CANCELLED', 6),
('2025-08-30', '18:10:00', 2649000, 'COMPLETED', 7),
('2025-08-29', '19:25:00', 1468000, 'COMPLETED', 8);

-- 결제 데이터 (취소된 주문 6번 제외)
INSERT INTO payments (payment_date, payment_time, payment_amount, payment_type, payment_status, user_id) VALUES
('2025-09-01', '10:31:00', 2890000, 'CARD', 'COMPLETED', 1),
('2025-09-01', '11:16:00', 1550000, 'CARD', 'COMPLETED', 2),
('2025-09-01', '14:01:00', 1558000, 'CARD', 'PENDING', 3),
('2025-08-31', '15:21:00', 1699000, 'INSTALLMENT', 'COMPLETED', 4),
('2025-08-31', '16:46:00', 459000, 'MOBILE', 'COMPLETED', 5),
('2025-08-30', '18:11:00', 2649000, 'CARD', 'COMPLETED', 7),
('2025-08-29', '19:26:00', 1468000, 'BANK_TRANSFER', 'COMPLETED', 8);

-- 주문상품 데이터 (전자제품 주문 내역)
INSERT INTO order_product (order_id, product_id, order_quantity, unit_price) VALUES
(1, 1, 1, 2890000), -- MacBook Pro 1대
(2, 3, 1, 1550000), -- iPhone 15 Pro 1대
(3, 3, 1, 1550000), -- iPhone 15 Pro 1대
(3, 5, 1, 359000),  -- AirPods Pro 1개 (번들 구매)
(4, 12, 1, 1699000), -- iPad Pro 12.9인치 1대
(5, 9, 1, 459000),   -- Sony 헤드폰 1개
(7, 1, 1, 2890000),  -- MacBook Pro 1대
(7, 5, 2, 359000),   -- AirPods Pro 2개 (선물용)
(8, 7, 1, 2190000),  -- LG 그램 1대
(8, 4, 1, 1698000),  -- 갤럭시 S24 Ultra 1대
(8, 6, 1, 1149000);  -- Apple Watch Ultra 1개 (취소된 주문 6번은 제외)

-- 주문결제 연결 데이터 (취소된 주문 제외)
INSERT INTO order_payment (order_id, payment_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(7, 6),
(8, 7);

COMMIT;
