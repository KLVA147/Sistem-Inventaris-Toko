/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User.DAOUser;
import model.User.ModelUser;
import view.login.LoginView;
import view.MainMenuView;

/**
 *
 * @author umair
 */
public class LoginController {
    private LoginView loginView;
    private DAOUser daoUser;

    public LoginController(LoginView loginView, DAOUser daoUser) {
        this.loginView = loginView;
        this.daoUser = daoUser;

        // Menambahkan listener tombol masuk di view
        this.loginView.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // Validasi input kosong sederhana
            if (username.isEmpty() || password.isEmpty()) {
                loginView.tampilkanPesanGagal("Username atau Password tidak boleh kosong!");
                return;
            }

            // Lakukan pengecekan ke database melalui DAOUser
            ModelUser userLogin = daoUser.login(username, password);

            if (userLogin != null) {
                // Periksa apakah status user aktif
                if (!userLogin.isAktif()) {
                    loginView.tampilkanPesanGagal("Akun Anda dinonaktifkan. Hubungi Administrator!");
                    return;
                }

                // Sesuai Request: Berikan message login sukses yang ditambah nama role yang login
                loginView.tampilkanPesanSukses(userLogin.getNamaLengkap(), userLogin.getRole());

                // Tutup jendela login
                loginView.dispose();

                // Buka Main Menu Frame beserta Controller utamanya
                MainMenuView mainMenuView = new MainMenuView();
                MainMenuController mainMenuController = new MainMenuController(mainMenuView, userLogin);
                
                // Tampilkan Jendela Utama Aplikasi
                mainMenuView.setVisible(true);
            } else {
                loginView.tampilkanPesanGagal("Username atau Password salah!");
            }
        }
    }
}