-- =============================================
-- Database: inventaris_toko
-- Sistem Inventaris Toko - Java MVC + OOP + Multithreading
-- =============================================
 
CREATE DATABASE IF NOT EXISTS inventaris_toko
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
 
USE inventaris_toko;
 
-- =============================================
-- TABLE: users
-- =============================================
CREATE TABLE IF NOT EXISTS users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('admin', 'kasir', 'gudang') NOT NULL DEFAULT 'kasir',
    nama_lengkap VARCHAR(100) NOT NULL,
    aktif       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 
INSERT INTO users (username, password, role, nama_lengkap) VALUES
('admin',  'admin123',  'admin',  'Administrator'),
('kasir1', 'kasir123',  'kasir',  'Budi Santoso'),
('gudang1','gudang123', 'gudang', 'Dewi Rahayu');
 
-- =============================================
-- TABLE: kategori
-- =============================================
CREATE TABLE IF NOT EXISTS kategori (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nama        VARCHAR(100) NOT NULL UNIQUE,
    deskripsi   VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 
INSERT INTO kategori (nama, deskripsi) VALUES
('Minuman',      'Semua jenis minuman kemasan dan segar'),
('Makanan',      'Makanan ringan, cemilan, dan makanan pokok'),
('Kebersihan',   'Produk pembersih dan perawatan rumah'),
('Kesehatan',    'Obat-obatan dan suplemen kesehatan'),
('Elektronik',   'Aksesoris dan gadget elektronik kecil');
 
-- =============================================
-- TABLE: produk
-- =============================================
CREATE TABLE IF NOT EXISTS produk (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    kode_produk     VARCHAR(20) NOT NULL UNIQUE,
    nama            VARCHAR(150) NOT NULL,
    id_kategori     INT NOT NULL,
    harga_beli      DECIMAL(15,2) NOT NULL DEFAULT 0,
    harga_jual      DECIMAL(15,2) NOT NULL DEFAULT 0,
    stok            INT NOT NULL DEFAULT 0,
    stok_minimum    INT NOT NULL DEFAULT 5,
    satuan          VARCHAR(20) NOT NULL DEFAULT 'pcs',
    deskripsi       VARCHAR(255),
    aktif           BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_kategori) REFERENCES kategori(id) ON UPDATE CASCADE
);
 
INSERT INTO produk (kode_produk, nama, id_kategori, harga_beli, harga_jual, stok, stok_minimum, satuan) VALUES
('MIN-001', 'Aqua 600ml',          1,  2500,   4000,  100, 10, 'botol'),
('MIN-002', 'Teh Botol Sosro',     1,  4000,   6000,  80,  10, 'botol'),
('MIN-003', 'Coca Cola 330ml',     1,  5000,   8000,  60,  10, 'kaleng'),
('MKN-001', 'Indomie Goreng',      2,  2800,   4000,  200, 20, 'bungkus'),
('MKN-002', 'Chitato 68g',         2,  7000,  10000,  50,   5, 'pcs'),
('MKN-003', 'Richeese Ahh',        2,  6000,   8500,  45,   5, 'pcs'),
('KBR-001', 'Rinso 800g',          3, 18000,  25000,  30,   5, 'pcs'),
('KBR-002', 'Sunlight 755ml',      3, 12000,  17000,  25,   5, 'botol'),
('KSH-001', 'Tolak Angin Cair',    4,  3500,   5000,  40,   5, 'sachet'),
('ELK-001', 'Baterai AA Alkaline', 5,  5000,   8000,  60,  10, 'pcs');
 
-- =============================================
-- TABLE: transaksi (penjualan)
-- =============================================
CREATE TABLE IF NOT EXISTS transaksi (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    kode_transaksi  VARCHAR(30) NOT NULL UNIQUE,
    id_user         INT NOT NULL,
    total_harga     DECIMAL(15,2) NOT NULL DEFAULT 0,
    total_bayar     DECIMAL(15,2) NOT NULL DEFAULT 0,
    kembalian       DECIMAL(15,2) NOT NULL DEFAULT 0,
    status          ENUM('selesai', 'dibatalkan') NOT NULL DEFAULT 'selesai',
    catatan         VARCHAR(255),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES users(id)
);
 
-- =============================================
-- TABLE: detail_transaksi
-- =============================================
CREATE TABLE IF NOT EXISTS detail_transaksi (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    id_transaksi    INT NOT NULL,
    id_produk       INT NOT NULL,
    nama_produk     VARCHAR(150) NOT NULL,
    harga_jual      DECIMAL(15,2) NOT NULL,
    jumlah          INT NOT NULL,
    subtotal        DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (id_transaksi) REFERENCES transaksi(id) ON DELETE CASCADE,
    FOREIGN KEY (id_produk)    REFERENCES produk(id)
);
 
-- =============================================
-- TABLE: log_stok (mutasi stok)
-- =============================================
CREATE TABLE IF NOT EXISTS log_stok (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    id_produk   INT NOT NULL,
    id_user     INT NOT NULL,
    jenis       ENUM('masuk', 'keluar', 'koreksi') NOT NULL,
    jumlah      INT NOT NULL,
    stok_sebelum INT NOT NULL,
    stok_sesudah INT NOT NULL,
    keterangan  VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_produk) REFERENCES produk(id),
    FOREIGN KEY (id_user)   REFERENCES users(id)
);