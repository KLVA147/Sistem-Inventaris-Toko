/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;                     
import model.dao.TransaksiDAO;
import model.objects.DetailTransaksi;
/**
 *
 * @author umair
 */
public class DetailTransaksiDialog extends JDialog {
    
    public DetailTransaksiDialog(JFrame parent, String idTransaksi, TransaksiDAO transaksiDao) {
        super(parent, "Detail Barang - Nota #" + idTransaksi, true);
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        JLabel lblInfo = new JLabel("Barang yang dibeli pada Transaksi ID: " + idTransaksi);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
        add(lblInfo, BorderLayout.NORTH);

        String[] kolom = {"Nama Barang", "Harga", "Jumlah", "Subtotal"};
        DefaultTableModel modelDetail = new DefaultTableModel(kolom, 0);
        JTable tableDetail = new JTable(modelDetail);
        
        List<DetailTransaksi> items = transaksiDao.ambilDetailTransaksi(idTransaksi);
        for (DetailTransaksi item : items) {
            modelDetail.addRow(new Object[]{
                item.getNamaBarang(),
                "Rp " + item.getHargaSatuan(),
                item.getJumlahBeli(),
                "Rp " + item.getSubtotal()
            });
        }
        // ---------------------------------------------------------------------------------

        JScrollPane scroll = new JScrollPane(tableDetail);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);
    }
}
