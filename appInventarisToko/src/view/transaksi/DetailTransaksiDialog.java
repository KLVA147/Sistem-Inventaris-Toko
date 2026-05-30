/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.transaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;                     
import model.Transaksi.DAOTransaksi; // Diselaraskan ke package model baru
import model.Transaksi.ModelDetailTransaksi; // Diselaraskan ke package model baru

/**
 * JDialog Pop-up untuk menampilkan daftar item barang dalam satu nota belanja.
 * @author umair
 */
public class DetailTransaksiDialog extends JDialog {
    
    // Parameter diubah menggunakan int idTransaksi agar sinkron dengan DAOTransaksi.java
    public DetailTransaksiDialog(JFrame parent, int idTransaksi, DAOTransaksi transaksiDao) {
        super(parent, "Detail Barang - Nota #" + idTransaksi, true);
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        JLabel lblInfo = new JLabel("Barang yang dibeli pada Transaksi ID: " + idTransaksi);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
        add(lblInfo, BorderLayout.NORTH);

        String[] kolom = {"Nama Barang", "Harga", "Jumlah", "Subtotal"};
        DefaultTableModel modelDetail = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable tableDetail = new JTable(modelDetail);
        
        // Memanggil nama method yang valid dan ada di model/Transaksi/DAOTransaksi.java
        List<ModelDetailTransaksi> items = transaksiDao.getDetailByTransaksi(idTransaksi);
        for (ModelDetailTransaksi item : items) {
            modelDetail.addRow(new Object[]{
                item.getNamaProduk(), // Menggunakan getter nama produk yang benar
                "Rp " + String.format("%,.0f", item.getHargaJual()), // Menggunakan getter harga jual yang benar
                item.getJumlah(), // Menggunakan getter jumlah yang benar
                "Rp " + String.format("%,.0f", item.getSubtotal()) // Menggunakan getter subtotal yang benar
            });
        }

        JScrollPane scroll = new JScrollPane(tableDetail);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);
    }
}