-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 31 Bulan Mei 2026 pada 03.05
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventaris_toko`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id` int(11) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `nama_produk` varchar(150) NOT NULL,
  `harga_jual` decimal(15,2) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `subtotal` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategori`
--

CREATE TABLE `kategori` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `kategori`
--

INSERT INTO `kategori` (`id`, `nama`, `deskripsi`, `created_at`) VALUES
(1, 'Minuman', 'Semua jenis minuman kemasan dan segar', '2026-05-30 01:45:20'),
(2, 'Makanan', 'Makanan ringan, cemilan, dan makanan pokok', '2026-05-30 01:45:20'),
(3, 'Kebersihan', 'Produk pembersih dan perawatan rumah', '2026-05-30 01:45:20'),
(4, 'Kesehatan', 'Obat-obatan dan suplemen kesehatan', '2026-05-30 01:45:20'),
(5, 'Elektronik', 'Aksesoris dan gadget elektronik kecil', '2026-05-30 01:45:20');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_stok`
--

CREATE TABLE `log_stok` (
  `id` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `jenis` enum('masuk','keluar','koreksi') NOT NULL,
  `jumlah` int(11) NOT NULL,
  `stok_sebelum` int(11) NOT NULL,
  `stok_sesudah` int(11) NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE `produk` (
  `id` int(11) NOT NULL,
  `kode_produk` varchar(20) NOT NULL,
  `nama` varchar(150) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `harga_beli` decimal(15,2) NOT NULL DEFAULT 0.00,
  `harga_jual` decimal(15,2) NOT NULL DEFAULT 0.00,
  `stok` int(11) NOT NULL DEFAULT 0,
  `stok_minimum` int(11) NOT NULL DEFAULT 5,
  `satuan` varchar(20) NOT NULL DEFAULT 'pcs',
  `deskripsi` varchar(255) DEFAULT NULL,
  `aktif` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`id`, `kode_produk`, `nama`, `id_kategori`, `harga_beli`, `harga_jual`, `stok`, `stok_minimum`, `satuan`, `deskripsi`, `aktif`, `created_at`, `updated_at`) VALUES
(1, 'MIN-001', 'Aqua 600ml', 1, 2500.00, 4000.00, 100, 10, 'botol', '', 1, '2026-05-30 01:45:21', '2026-05-30 13:08:24'),
(2, 'MIN-002', 'Teh Botol Sosro', 1, 4000.00, 6000.00, 80, 10, 'botol', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(3, 'MIN-003', 'Coca Cola 330ml', 1, 5000.00, 8000.00, 60, 10, 'kaleng', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(4, 'MKN-001', 'Indomie Goreng', 2, 2800.00, 4000.00, 200, 20, 'bungkus', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(5, 'MKN-002', 'Chitato 68g', 2, 7000.00, 10000.00, 50, 5, 'pcs', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(6, 'MKN-003', 'Richeese Ahh', 2, 6000.00, 8500.00, 45, 5, 'pcs', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(7, 'KBR-001', 'Rinso 800g', 3, 18000.00, 25000.00, 30, 5, 'pcs', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(8, 'KBR-002', 'Sunlight 755ml', 3, 12000.00, 17000.00, 25, 5, 'botol', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(9, 'KSH-001', 'Tolak Angin Cair', 4, 3500.00, 5000.00, 40, 5, 'sachet', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21'),
(10, 'ELK-001', 'Baterai AA Alkaline', 5, 5000.00, 8000.00, 60, 10, 'pcs', NULL, 1, '2026-05-30 01:45:21', '2026-05-30 01:45:21');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id` int(11) NOT NULL,
  `kode_transaksi` varchar(30) NOT NULL,
  `id_user` int(11) NOT NULL,
  `total_harga` decimal(15,2) NOT NULL DEFAULT 0.00,
  `total_bayar` decimal(15,2) NOT NULL DEFAULT 0.00,
  `kembalian` decimal(15,2) NOT NULL DEFAULT 0.00,
  `status` enum('selesai','dibatalkan') NOT NULL DEFAULT 'selesai',
  `catatan` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','kasir','gudang') NOT NULL DEFAULT 'kasir',
  `nama_lengkap` varchar(100) NOT NULL,
  `aktif` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `nama_lengkap`, `aktif`, `created_at`) VALUES
(1, 'admin', 'admin123', 'admin', 'Administrator', 1, '2026-05-30 01:45:20'),
(2, 'kasir1', 'kasir123', 'kasir', 'Budi Santoso', 1, '2026-05-30 01:45:20'),
(3, 'gudang1', 'gudang123', 'gudang', 'Dewi Rahayu', 1, '2026-05-30 01:45:20');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_transaksi` (`id_transaksi`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indeks untuk tabel `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nama` (`nama`);

--
-- Indeks untuk tabel `log_stok`
--
ALTER TABLE `log_stok`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_produk` (`id_produk`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `kode_produk` (`kode_produk`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `kode_transaksi` (`kode_transaksi`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `log_stok`
--
ALTER TABLE `log_stok`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `produk`
--
ALTER TABLE `produk`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id`);

--
-- Ketidakleluasaan untuk tabel `log_stok`
--
ALTER TABLE `log_stok`
  ADD CONSTRAINT `log_stok_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id`),
  ADD CONSTRAINT `log_stok_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Ketidakleluasaan untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD CONSTRAINT `produk_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id`) ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
