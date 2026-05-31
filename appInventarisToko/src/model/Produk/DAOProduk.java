/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Produk;

import model.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOProduk implements InterfaceDAOProduk {

    private static final String SELECT_BASE =
        "SELECT p.*, k.nama AS nama_kategori " +
        "FROM produk p LEFT JOIN kategori k ON p.id_kategori = k.id ";

    private ModelProduk mapRow(ResultSet rs) throws SQLException {
        ModelProduk p = new ModelProduk();
        p.setId(rs.getInt("id"));
        p.setKodeProduk(rs.getString("kode_produk"));
        p.setNama(rs.getString("nama"));
        p.setIdKategori(rs.getInt("id_kategori"));
        p.setNamaKategori(rs.getString("nama_kategori"));
        p.setHargaBeli(rs.getDouble("harga_beli"));
        p.setHargaJual(rs.getDouble("harga_jual"));
        p.setStok(rs.getInt("stok"));
        p.setStokMinimum(rs.getInt("stok_minimum"));
        p.setSatuan(rs.getString("satuan"));
        p.setDeskripsi(rs.getString("deskripsi"));
        p.setAktif(rs.getBoolean("aktif"));
        return p;
    }

    @Override
    public boolean kodeExists(String kodeProduk) {
        String sql = "SELECT COUNT(*) FROM produk WHERE kode_produk=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, kodeProduk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("[DAOProduk.kodeExists] " + e.getMessage());
        }
        return false;
    }

    @Override
    public void insert(ModelProduk p) {
        String sql = "INSERT INTO produk (kode_produk,nama,id_kategori,harga_beli,harga_jual,stok,stok_minimum,satuan,deskripsi,aktif) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, p.getKodeProduk());
            ps.setString(2, p.getNama());
            ps.setInt(3, p.getIdKategori());
            ps.setDouble(4, p.getHargaBeli());
            ps.setDouble(5, p.getHargaJual());
            ps.setInt(6, p.getStok());
            ps.setInt(7, p.getStokMinimum());
            ps.setString(8, p.getSatuan());
            ps.setString(9, p.getDeskripsi());
            ps.setBoolean(10, p.isAktif());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOProduk.insert] " + e.getMessage());
        }
    }

    @Override
    public void update(ModelProduk p) {
        String sql = "UPDATE produk SET kode_produk=?,nama=?,id_kategori=?,harga_beli=?,harga_jual=?,stok=?,stok_minimum=?,satuan=?,deskripsi=?,aktif=? WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, p.getKodeProduk());
            ps.setString(2, p.getNama());
            ps.setInt(3, p.getIdKategori());
            ps.setDouble(4, p.getHargaBeli());
            ps.setDouble(5, p.getHargaJual());
            ps.setInt(6, p.getStok());
            ps.setInt(7, p.getStokMinimum());
            ps.setString(8, p.getSatuan());
            ps.setString(9, p.getDeskripsi());
            ps.setBoolean(10, p.isAktif());
            ps.setInt(11, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOProduk.update] " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE produk SET aktif=FALSE WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOProduk.delete] " + e.getMessage());
        }
    }

    @Override
    public ModelProduk getById(int id) {
        String sql = SELECT_BASE + "WHERE p.id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[DAOProduk.getById] " + e.getMessage());
        }
        return null;
    }

    @Override
    public ModelProduk getByKode(String kodeProduk) {
        String sql = SELECT_BASE + "WHERE p.kode_produk=? AND p.aktif=TRUE";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, kodeProduk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[DAOProduk.getByKode] " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ModelProduk> getAll() {
        List<ModelProduk> list = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.aktif=TRUE ORDER BY k.nama, p.nama";
        try (Statement st = Connector.Connect().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOProduk.getAll] " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ModelProduk> getByKategori(int idKategori) {
        List<ModelProduk> list = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.id_kategori=? AND p.aktif=TRUE ORDER BY p.nama";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, idKategori);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOProduk.getByKategori] " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ModelProduk> getStokRendah() {
        List<ModelProduk> list = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.aktif=TRUE AND p.stok <= p.stok_minimum ORDER BY p.stok ASC";
        try (Statement st = Connector.Connect().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOProduk.getStokRendah] " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ModelProduk> search(String keyword) {
        List<ModelProduk> list = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.aktif=TRUE AND (p.nama LIKE ? OR p.kode_produk LIKE ? OR k.nama LIKE ?) ORDER BY p.nama";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOProduk.search] " + e.getMessage());
        }
        return list;
    }

    @Override
    public void updateStok(int idProduk, int jumlah, String jenis) {
        String sql;
        if ("masuk".equals(jenis)) {
            sql = "UPDATE produk SET stok = stok + ? WHERE id=?";
        } else if ("keluar".equals(jenis)) {
            sql = "UPDATE produk SET stok = stok - ? WHERE id=?";
        } else {
            sql = "UPDATE produk SET stok = ? WHERE id=?";
        }
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, jumlah);
            ps.setInt(2, idProduk);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOProduk.updateStok] " + e.getMessage());
        }
    }
}
