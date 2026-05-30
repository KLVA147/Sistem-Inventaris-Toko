/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repository;

import model.dao.LoginDAO;
import model.connector.DBConnector;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
/**
 *
 * @author umair
 */
public class Login implements LoginDAO{
    private DBConnector connector;

    public Login() {
        this.connector = new DBConnector();
    }

    @Override
    public boolean loginValidasi(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connector.Connect();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Model: Akun ditemukan di database.");
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Model Error (Login): " + e.getMessage());
            return false;
        }
        
        System.out.println("Model: Akun tidak ditemukan / password salah.");
        return false; 
    }
}
