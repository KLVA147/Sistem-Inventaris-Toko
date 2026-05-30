/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controller;

import model.repository.FrozenFoodRepository;
import model.repository.MakananRepository;
import model.repository.MinumanRepository;
import model.repository.SabunRepository;
import view.MainMenuView;
import model.dao.BarangDAO;
import model.dao.TransaksiDAO;
import model.objects.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author umair
 */
public class MainMenuController {
    private MainMenuView view;
    private TransactionController transactionController; 
    private AllDataController allDataController; // Tambahkan referensi controller baru

    public MainMenuController(MainMenuView view, BarangDAO barangDao, TransaksiDAO transaksiDao,
                              MakananRepository mknRepo, MinumanRepository mnmRepo,
                              FrozenFoodRepository fzRepo, SabunRepository sbRepo) {
        this.view = view;

        this.transactionController = new TransactionController(view.transaction, barangDao, transaksiDao);
        this.allDataController = new AllDataController(view.panelLihatDataReal, mknRepo, mnmRepo, fzRepo, sbRepo);

        this.view.btnTransaksi.addActionListener(e -> bukaHalamanTransaksi());
        this.view.btnLihatData.addActionListener(e -> bukaHalamanLihatData());
        this.view.btnExit.addActionListener(e -> keluarAplikasi());
    }

    private void bukaHalamanTransaksi() {
        view.cardLayout.show(view.panelKonten, "Transaksi");
    }

    private void bukaHalamanLihatData() {
        view.cardLayout.show(view.panelKonten, "LihatData");
    }

    private void keluarAplikasi() {
        int konfirmasi = JOptionPane.showConfirmDialog(
            view, 
            "Apakah Anda yakin ingin keluar dari aplikasi?", 
            "Konfirmasi Keluar", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            System.exit(0); 
        }
    }
}