/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Kategori;

import model.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOKategori implements InterfaceDAOKategori {

    private ModelKategori mapRow(ResultSet rs) throws SQLException {
        ModelKategori k = new ModelKategori();
        k.setId(rs.getInt("id"));
        k.setNama(rs.getString("nama"));
        k.setDeskripsi(rs.getString("deskripsi"));
        return k;
    }

    @Override
    public boolean namaExists(String nama) {
        String sql = "SELECT COUNT(*) FROM kategori WHERE nama=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("[DAOKategori.namaExists] " + e.getMessage());
        }
        return false;
    }

    @Override
    public void insert(ModelKategori k) {
        String sql = "INSERT INTO kategori (nama, deskripsi) VALUES (?,?)";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, k.getNama());
            ps.setString(2, k.getDeskripsi());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOKategori.insert] " + e.getMessage());
        }
    }

    @Override
    public void update(ModelKategori k) {
        String sql = "UPDATE kategori SET nama=?, deskripsi=? WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, k.getNama());
            ps.setString(2, k.getDeskripsi());
            ps.setInt(3, k.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOKategori.update] " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM kategori WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOKategori.delete] " + e.getMessage());
        }
    }

    @Override
    public ModelKategori getById(int id) {
        String sql = "SELECT * FROM kategori WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[DAOKategori.getById] " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ModelKategori> getAll() {
        List<ModelKategori> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori ORDER BY nama";
        try (Statement st = Connector.Connect().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOKategori.getAll] " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ModelKategori> search(String keyword) {
        List<ModelKategori> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori WHERE nama LIKE ? OR deskripsi LIKE ? ORDER BY nama";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOKategori.search] " + e.getMessage());
        }
        return list;
    }
}
