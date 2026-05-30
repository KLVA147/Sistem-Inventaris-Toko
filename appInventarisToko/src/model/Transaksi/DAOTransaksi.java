/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Transaksi;

import model.Connector;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOTransaksi {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

    public String generateKodeTransaksi() {
        return "TRX-" + SDF.format(new Date());
    }

    // >>> PERBAIKAN SINKRONISASI: Mengamankan return type boolean untuk mendeteksi kesuksesan transaksi atomik
    public boolean insert(ModelTransaksi trx) {
        Connection conn = Connector.Connect();
        try {
            conn.setAutoCommit(false);

            // 1) Insert transaksi header
            String sqlTrx = "INSERT INTO transaksi (kode_transaksi,id_user,total_harga,total_bayar,kembalian,status,catatan) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement psTrx = conn.prepareStatement(sqlTrx, Statement.RETURN_GENERATED_KEYS);
            psTrx.setString(1, trx.getKodeTransaksi());
            psTrx.setInt(2, trx.getIdUser());
            psTrx.setDouble(3, trx.getTotalHarga());
            psTrx.setDouble(4, trx.getTotalBayar());
            psTrx.setDouble(5, trx.getKembalian());
            psTrx.setString(6, trx.getStatus());
            psTrx.setString(7, trx.getCatatan());
            psTrx.executeUpdate();

            ResultSet gk = psTrx.getGeneratedKeys();
            if (!gk.next()) throw new SQLException("Gagal mendapat ID transaksi.");
            int idTrx = gk.getInt(1);
            trx.setId(idTrx);
            psTrx.close();

            // 2) Insert detail + update stok fisik produk
            String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi,id_produk,nama_produk,harga_jual,jumlah,subtotal) VALUES (?,?,?,?,?,?)";
            String sqlStok   = "UPDATE produk SET stok = stok - ? WHERE id=? AND stok >= ?";
            String sqlLog    = "INSERT INTO log_stok (id_produk,id_user,jenis,jumlah,stok_sebelum,stok_sesudah,keterangan) " +
                               "SELECT id, ?, 'keluar', ?, stok+?, stok, ? FROM produk WHERE id=?";

            // Menggunakan method getDetailList() yang ada di ModelTransaksi.java
            for (ModelDetailTransaksi d : trx.getDetailList()) {
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                psDetail.setInt(1, idTrx);
                psDetail.setInt(2, d.getIdProduk());
                psDetail.setString(3, d.getNamaProduk());
                psDetail.setDouble(4, d.getHargaJual());
                psDetail.setInt(5, d.getJumlah());
                psDetail.setDouble(6, d.getSubtotal());
                psDetail.executeUpdate();
                psDetail.close();

                PreparedStatement psStok = conn.prepareStatement(sqlStok);
                psStok.setInt(1, d.getJumlah());
                psStok.setInt(2, d.getIdProduk());
                psStok.setInt(3, d.getJumlah());
                int rows = psStok.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    throw new SQLException("Stok tidak cukup untuk produk: " + d.getNamaProduk());
                }
                psStok.close();

                PreparedStatement psLog = conn.prepareStatement(sqlLog);
                psLog.setInt(1, trx.getIdUser());
                psLog.setInt(2, d.getJumlah());
                psLog.setInt(3, d.getJumlah());
                psLog.setString(4, "Penjualan " + trx.getKodeTransaksi());
                psLog.setInt(5, d.getIdProduk());
                psLog.executeUpdate();
                psLog.close();
            }

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            System.err.println("[DAOTransaksi.insert] " + e.getMessage());
            try { conn.rollback(); conn.setAutoCommit(true); } catch (SQLException ignored) {}
            return false;
        }
    }

    public List<ModelTransaksi> getAll() {
        List<ModelTransaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, u.nama_lengkap AS nama_user FROM transaksi t " +
                     "JOIN users u ON t.id_user = u.id ORDER BY t.created_at DESC";
        try (Statement st = Connector.Connect().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ModelTransaksi t = new ModelTransaksi();
                t.setId(rs.getInt("id"));
                t.setKodeTransaksi(rs.getString("kode_transaksi"));
                t.setIdUser(rs.getInt("id_user"));
                t.setNamaUser(rs.getString("nama_user"));
                t.setTotalHarga(rs.getDouble("total_harga"));
                t.setTotalBayar(rs.getDouble("total_bayar"));
                t.setKembalian(rs.getDouble("kembalian"));
                t.setStatus(rs.getString("status"));
                t.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("[DAOTransaksi.getAll] " + e.getMessage());
        }
        return list;
    }

    public List<ModelDetailTransaksi> getDetailByTransaksi(int idTransaksi) {
        List<ModelDetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM detail_transaksi WHERE id_transaksi=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelDetailTransaksi d = new ModelDetailTransaksi();
                d.setId(rs.getInt("id"));
                d.setIdTransaksi(rs.getInt("id_transaksi"));
                d.setIdProduk(rs.getInt("id_produk"));
                d.setNamaProduk(rs.getString("nama_produk"));
                d.setHargaJual(rs.getDouble("harga_jual"));
                d.setJumlah(rs.getInt("jumlah"));
                d.setSubtotal(rs.getDouble("subtotal"));
                list.add(d);
            }
        } catch (SQLException e) {
            System.err.println("[DAOTransaksi.getDetailByTransaksi] " + e.getMessage());
        }
        return list;
    }
}