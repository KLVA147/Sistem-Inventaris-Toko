/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User.*;
import view.Login.LoginView;
import view.Menu.MenuView;
import javax.swing.*;
import java.util.List;

public class ControllerUser {

    private final InterfaceDAOUser daoUser = new DAOUser();

    private LoginView halamanLogin;
    private JFrame    halamanUser;

    public ControllerUser(LoginView v) { this.halamanLogin = v; }
    public ControllerUser(JFrame v)    { this.halamanUser  = v; }

    public void login() {
        try {
            String username = halamanLogin.getInputUsername();
            String password = halamanLogin.getInputPassword();

            if (username.isEmpty() || password.isEmpty())
                throw new Exception("Username dan password tidak boleh kosong!");

            ModelUser user = daoUser.login(username, password);
            if (user == null)
                throw new Exception("Username atau password salah, atau akun tidak aktif.");

            JOptionPane.showMessageDialog(null,
                "Selamat datang, " + user.getNamaLengkap() + "!\nRole: " + user.getRole().toUpperCase(),
                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);

            halamanLogin.dispose();
            new MenuView(user);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<ModelUser> getAllUser()              { return daoUser.getAll(); }
    public List<ModelUser> searchUser(String kw)    { return daoUser.search(kw); }

    public void tambahUser(String username, String password, String role, String namaLengkap) {
        try {
            if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty())
                throw new Exception("Semua field wajib diisi!");
            if (daoUser.usernameExists(username))
                throw new Exception("Username '" + username + "' sudah digunakan!");

            ModelUser u = new ModelUser(username, password, role, namaLengkap);
            daoUser.insert(u);
            JOptionPane.showMessageDialog(null, "User berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hapusUser(int id) {
        int ok = JOptionPane.showConfirmDialog(null, "Nonaktifkan user ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            daoUser.delete(id);
            JOptionPane.showMessageDialog(null, "User berhasil dinonaktifkan.");
        }
    }
}
