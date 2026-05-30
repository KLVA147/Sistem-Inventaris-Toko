/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 *
 * @author umair
 */
public class AllDataDialog extends JDialog{
    public JTable tableProduk;
    public DefaultTableModel tableModel;
    
    public JButton btnTambah = new JButton("Tambah");
    public JButton btnEdit = new JButton("Edit");
    public JButton btnUpdateStok = new JButton("Update Stok");
    public JButton btnHapus = new JButton("Hapus");
    public JButton btnTutup = new JButton("Tutup");

    public AllDataDialog(JFrame parent, String kategori) {
        super(parent, "Daftar Inventaris - Kategori " + kategori, true);
        setSize(600, 380);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Header Dialog
        JLabel lblHeader = new JLabel("Menampilkan Produk: " + kategori.toUpperCase(), SwingConstants.LEFT);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        add(lblHeader, BorderLayout.NORTH);

        // Inisialisasi Tabel Data Produk
        String[] kolom = {"Kode Barang", "Nama Barang", "Stok", "Harga Satuan"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Cell tidak bisa diubah langsung dengan mengetik di tabel
            }
        };
        tableProduk = new JTable(tableModel);
        tableProduk.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tableProduk);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        add(scrollPane, BorderLayout.CENTER);
        
        // --- DESIGN PANEL ACTION (CRUD & STOK) ---
        JPanel panelAksiGudang = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelAksiGudang.add(btnTambah);
        panelAksiGudang.add(btnEdit);
        panelAksiGudang.add(btnUpdateStok);
        
        // Desain tombol hapus dengan warna merah peringatan
        btnHapus.setBackground(new Color(192, 41, 43));
        btnHapus.setForeground(Color.WHITE);
        panelAksiGudang.add(btnHapus);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        panelBawah.add(panelAksiGudang, BorderLayout.WEST);
        panelBawah.add(btnTutup, BorderLayout.EAST);
        
        add(panelBawah, BorderLayout.SOUTH);
        
        // Tombol Tutup di bawah
        btnTutup.addActionListener(e -> dispose());
    }
}
