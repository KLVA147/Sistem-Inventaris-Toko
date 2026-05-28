/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;

public class Connector {
    static String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    static String nama_db = "inventaris_toko";
    static String url_db = "jdbc:mysql://localhost:3306/" + nama_db;
    static String username_db = "root";
    static String password_db = "";

    static Connection conn;

    public static Connection Connect() {
        try {
            Class.forName(jdbc_driver);
            conn = DriverManager.getConnection(url_db, username_db, password_db);
            System.out.println("MySQL Connected");
            
            if (conn == null)
                System.out.println("CRITICAL ERROR: Database connection is null! Check Connector.java");
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println("Connection Failed: " + exception.getMessage());
        }
        return conn;
    }
}
