package main;

import model.User.DAOUser;
import view.login.LoginView;
import controller.LoginController;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author umair
 */
public class appInventarisToko {

    public static void main(String[] args) {
     
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {
            System.err.println("Gagal memuat Look and Feel sistem: " + e.getMessage());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("[Thread Info] Aplikasi Utama dimulai pada Thread: " + Thread.currentThread().getName());
                LoginView loginView = new LoginView();
                DAOUser daoUser = new DAOUser();
                LoginController login = new LoginController(loginView, daoUser);
                loginView.setVisible(true);
                System.out.println("[Thread Info] EDT sukses dijalankan. Background Workers (SwingWorker) siap melayani perintah.");
            }
        });
    }
}