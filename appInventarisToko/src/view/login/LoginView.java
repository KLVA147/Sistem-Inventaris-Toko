/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author umair
 */
public class LoginView extends JFrame {
    public JTextField txtUsername = new JTextField(15);
    public JPasswordField txtPassword = new JPasswordField(15);
    public JButton btnLogin = new JButton("Masuk");

    public LoginView() {
        setTitle("Sistem Inventaris - Login");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL ATAS: JUDUL ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(41, 128, 185)); // Biru Cerah
        JLabel lblJudul = new JLabel("LOGIN PENGGUNA");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        lblJudul.setForeground(Color.WHITE);
        panelHeader.add(lblJudul);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // --- PANEL TENGAH: FORM INPUT ---
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 15));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        panelForm.add(new JLabel("Username :"));
        panelForm.add(txtUsername);
        panelForm.add(new JLabel("Password :"));
        panelForm.add(txtPassword);

        // --- PANEL BAWAH: TOMBOL ---
        JPanel panelBottom = new JPanel();
        panelBottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        btnLogin.setPreferredSize(new Dimension(150, 35));
        btnLogin.setBackground(new Color(46, 204, 113)); // Hijau Sukses
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        panelBottom.add(btnLogin);

        // Gabungkan Komponen ke Frame Utama
        add(panelHeader, BorderLayout.NORTH);
        add(panelForm, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }
    
    public String getUsername() {
        return txtUsername.getText().trim();
    }
    
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
    
    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    /**
     * Menampilkan dialog pesan sukses setelah verifikasi login berhasil.
     * Menyertakan informasi Nama Lengkap dan Role pengguna.
     */
    public void tampilkanPesanSukses(String namaLengkap, String role) {
        String pesan = "Selamat Datang, " + namaLengkap + "!\n" +
                       "Anda berhasil masuk sebagai [" + role.toUpperCase() + "].";
        JOptionPane.showMessageDialog(this, pesan, "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan dialog pesan kesalahan apabila kredensial salah atau tidak aktif.
     */
    public void tampilkanPesanGagal(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Login Gagal", JOptionPane.ERROR_MESSAGE);
    }
}