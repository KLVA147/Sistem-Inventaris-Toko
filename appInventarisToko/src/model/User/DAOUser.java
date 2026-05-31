/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.User;

import model.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUser implements InterfaceDAOUser {

    private ModelUser mapRow(ResultSet rs) throws SQLException {
        ModelUser u = new ModelUser();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        u.setNamaLengkap(rs.getString("nama_lengkap"));
        u.setAktif(rs.getBoolean("aktif"));
        return u;
    }

    @Override
    public ModelUser login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=? AND aktif=TRUE";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[DAOUser.login] " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("[DAOUser.usernameExists] " + e.getMessage());
        }
        return false;
    }

    @Override
    public void insert(ModelUser u) {
        String sql = "INSERT INTO users (username,password,role,nama_lengkap,aktif) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole());
            ps.setString(4, u.getNamaLengkap());
            ps.setBoolean(5, u.isAktif());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOUser.insert] " + e.getMessage());
        }
    }

    @Override
    public void update(ModelUser u) {
        String sql = "UPDATE users SET username=?,password=?,role=?,nama_lengkap=?,aktif=? WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole());
            ps.setString(4, u.getNamaLengkap());
            ps.setBoolean(5, u.isAktif());
            ps.setInt(6, u.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOUser.update] " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE users SET aktif=FALSE WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DAOUser.delete] " + e.getMessage());
        }
    }

    @Override
    public ModelUser getById(int id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[DAOUser.getById] " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ModelUser> getAll() {
        List<ModelUser> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY role, nama_lengkap";
        try (Statement st = Connector.Connect().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOUser.getAll] " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ModelUser> search(String keyword) {
        List<ModelUser> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE nama_lengkap LIKE ? OR username LIKE ? ORDER BY nama_lengkap";
        try (PreparedStatement ps = Connector.Connect().prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAOUser.search] " + e.getMessage());
        }
        return list;
    }
}
