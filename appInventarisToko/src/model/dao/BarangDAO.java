/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.objects.*;
import model.connector.DBConnector;
/**
 *
 * @author umair
 */
public class BarangDAO {
    private DBConnector connector;
    
    public BarangDAO() {
        this.connector = new DBConnector();
    }
    
    // --- CONTOH FUNGSI INSERT (CREATE) ---
    // CREATE (Dinamis sesuai tabel Objek Anak)
    public void insert(Barang barang) {
        String sql = "INSERT INTO " + barang.getNamaTabel() + " (id, nama, harga, stok) VALUES (?, ?, ?, ?)";
        try (Connection conn = connector.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getId());
            ps.setString(2, barang.getNama());
            ps.setInt(3, barang.getHarga());
            ps.setInt(4, barang.getStok());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    // UPDATE DATA (Dinamis)
    public void update(Barang barang) {
        String sql = "UPDATE " + barang.getNamaTabel() + " SET nama = ?, harga = ? WHERE id = ?";
        try (Connection conn = connector.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, barang.getNama());
            ps.setInt(2, barang.getHarga());
            ps.setString(3, barang.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // UPDATE STOK (Dinamis digunakan CRUD & Kasir)
    public void updateStok(String id, int stokBaru, String namaTabel) {
        String sql = "UPDATE " + namaTabel + " SET stok = ? WHERE id = ?";
        try (Connection conn = connector.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stokBaru);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- CONTOH FUNGSI GET DATA PER TABEL (READ) ---
    // READ PER TABEL (Untuk Dialog Kategori)
    public List<Barang> getByTabel(String namaTabel, String kategori) {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM " + namaTabel;
        try (Connection conn = connector.Connect(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                if (kategori.equals("Makanan")) {
                    list.add(new Makanan(rs.getString("id"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok"), rs.getString("expired_date")));
                } else if (kategori.equals("Minuman")) {
                    list.add(new Minuman(rs.getString("id"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok")));
                } else if (kategori.equals("Sabun")) {
                    list.add(new Sabun(rs.getString("id"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok")));
                } else if (kategori.equals("Frozen Food")) {
                    list.add(new FrozenFood(rs.getString("id"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public Barang cariBerdasarkanNama(String namaBarang) {
        String sql = "SELECT id, nama, harga, stok, 'tabel_makanan' AS sumber FROM tabel_makanan WHERE nama = ? " +
                     "UNION SELECT id, nama, harga, stok, 'tabel_minuman' AS sumber FROM tabel_minuman WHERE nama = ? " +
                     "UNION SELECT id, nama, harga, stok, 'tabel_frozenfood' AS sumber FROM tabel_frozenfood WHERE nama = ? " +
                     "UNION SELECT id, nama, harga, stok, 'tabel_sabun' AS sumber FROM tabel_sabun WHERE nama = ?";
                     
        try (Connection conn = connector.Connect(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for(int i=1; i<=4; i++) ps.setString(i, namaBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("id");
                    String nama = rs.getString("nama");
                    int harga = rs.getInt("harga");
                    int stok = rs.getInt("stok");
                    String sumber = rs.getString("sumber");

                    // Polimorfisme: mengembalikan objek anak anonim dengan membawa identitas asal tabelnya
                    return new Barang(id, nama, harga, stok) {
                        @Override
                        public String getNamaTabel() { return sumber; }
                    };
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    
    // DELETE (Dinamis)
    public void delete(String id, String namaTabel) {
        String sql = "DELETE FROM " + namaTabel + " WHERE id = ?";
        try (Connection conn = connector.Connect(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}

