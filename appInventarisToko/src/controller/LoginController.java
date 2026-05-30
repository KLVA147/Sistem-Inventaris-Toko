/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.repository.FrozenFoodRepository;
import model.repository.MakananRepository;
import model.repository.MinumanRepository;
import model.repository.SabunRepository;
import model.repository.Login;
import view.MainMenuView;
import view.LoginView;
import model.dao.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author umair
 */
public class LoginController {
    private LoginView view;
    private Login model;
    private BarangDAO barangDao;
    private TransaksiDAO transaksiDao;
    private MakananRepository mknRepo;
    private MinumanRepository mnmRepo;
    private FrozenFoodRepository fzRepo;
    private SabunRepository sbRepo;

    public LoginController(LoginView loginView, Login model, BarangDAO barangDao, 
                           TransaksiDAO transaksiDao,
                           MakananRepository mknRepo, 
                           MinumanRepository mnmRepo, FrozenFoodRepository fzRepo, SabunRepository sbRepo ) {
        this.view = loginView;
        this.model = model;
        this.barangDao = barangDao;
        this.transaksiDao = transaksiDao;
        this.mknRepo = mknRepo;
        this.mnmRepo = mnmRepo;
        this.fzRepo = fzRepo;
        this.sbRepo = sbRepo;

        this.view.btnLogin.addActionListener(e -> prosesLogin());
    }

    private void prosesLogin() {
        String username = view.txtUsername.getText().trim();
        String password = new String(view.txtPassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isUserValid = model.loginValidasi(username, password);

        if (isUserValid) {
            JOptionPane.showMessageDialog(view, "Login Berhasil! Selamat Datang.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
     
            view.dispose();
            
            MainMenuView menuView = new MainMenuView();
            new MainMenuController(menuView, barangDao, transaksiDao, mknRepo, mnmRepo, fzRepo, sbRepo); 
            menuView.setVisible(true); 
            
        } else {
            JOptionPane.showMessageDialog(view, "Username atau Password salah!", "Gagal Login", JOptionPane.ERROR_MESSAGE);

            view.txtPassword.setText("");
        }
    }
}
