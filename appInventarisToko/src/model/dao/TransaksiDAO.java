/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.connector.DBConnector;
import model.objects.Transaksi;
import model.objects.DetailTransaksi;
/**
 *
 * @author umair
 */
public class TransaksiDAO {
    private DBConnector connector;

    public TransaksiDAO() {
        this.connector = new DBConnector();
    }

    // MEMASUKKAN DATA CHECKOUT KE DATABASE (2 Tabel sekaligus)
    public boolean simpanTransaksi(Transaksi t) {
        String sqlTransaksi = "INSERT INTO tabel_transaksi (id_transaksi, tanggal_transaksi, total_harga) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO tabel_detail_transaksi (id_transaksi, nama_barang, harga_satuan, jumlah_beli, subtotal) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = connector.Connect();
            if (conn == null) return false;
            
            // Mengaktifkan Transaction Management (Commit manual agar aman)
            conn.setAutoCommit(false);

            // 1. Simpan ke tabel induk
            try (PreparedStatement psTx = conn.prepareStatement(sqlTransaksi)) {
                psTx.setString(1, t.getIdTransaksi());
                psTx.setString(2, t.getTanggalTransaksi());
                psTx.setInt(3, t.getTotalHarga());
                psTx.executeUpdate();
            }

            // 2. Simpan semua item belanja ke tabel detail
            try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                for (DetailTransaksi item : t.getListDetail()) {
                    psDetail.setString(1, t.getIdTransaksi());
                    psDetail.setString(2, item.getNamaBarang());
                    psDetail.setInt(3, item.getHargaSatuan());
                    psDetail.setInt(4, item.getJumlahBeli());
                    psDetail.setInt(5, item.getSubtotal());
                    psDetail.addBatch(); // Gunakan batch agar eksekusi cepat
                }
                psDetail.executeBatch();
            }

            conn.commit(); // Jika sukses semua, simpan permanen
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal simpan transaksi: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    // MENGAMBIL SEMUA NOTA (Untuk Tabel Riwayat di TransactionPanel)
    public List<Transaksi> ambilSemuaTransaksi() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_transaksi ORDER BY id_transaksi DESC";
        
        try (Connection conn = connector.Connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            if (conn == null) return list;
            while (rs.next()) {
                list.add(new Transaksi(
                    rs.getString("id_transaksi"),
                    rs.getString("tanggal_transaksi"),
                    rs.getInt("total_harga")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // MENGAMBIL DETAIL BARANG BELANJAAN (Untuk JDialog Detail Transaksi)
    public List<DetailTransaksi> ambilDetailTransaksi(String idTransaksi) {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM tabel_detail_transaksi WHERE id_transaksi = ?";
        
        try (Connection conn = connector.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (conn == null) return list;
            ps.setString(1, idTransaksi);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DetailTransaksi(
                        rs.getString("nama_barang"),
                        rs.getInt("harga_satuan"),
                        rs.getInt("jumlah_beli"),
                        rs.getInt("subtotal")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
