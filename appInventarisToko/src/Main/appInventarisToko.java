/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import model.repository.FrozenFoodRepository;
import model.repository.MakananRepository;
import model.repository.MinumanRepository;
import model.repository.SabunRepository;
import view.LoginView;
import controller.LoginController;
import model.repository.Login;
import model.dao.*;
/**
 *
 * @author umair
 */
public class appInventarisToko {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            
            // 1. Buat Data Access Object tunggal
            Login login = new Login(); 
            BarangDAO barangDao = new BarangDAO(); // Sesuai implementasi DB Anda
            TransaksiDAO transaksiDao = new TransaksiDAO();

            // 2. Masukkan DAO ke dalam masing-masing Repository
            MakananRepository makananRepo = new MakananRepository(barangDao);
            MinumanRepository minumanRepo = new MinumanRepository(barangDao);
            FrozenFoodRepository frozenfoodRepo = new FrozenFoodRepository(barangDao);
            SabunRepository sabunRepo = new SabunRepository(barangDao);

            LoginView view = new LoginView();
            LoginController controller = new LoginController(view, login, barangDao, 
                                            transaksiDao, makananRepo, minumanRepo, 
                                            frozenfoodRepo, sabunRepo);
            view.setVisible(true);
    });
                }
}
