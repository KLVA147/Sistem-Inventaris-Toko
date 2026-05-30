/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author umair
 */
public class AllDataPanel extends JPanel {
    
    // --- TAB 1: PRODUK ---
    public JTable tabelProduk;
    public JScrollPane scrollProduk;
    public JButton btnTambahProduk = new JButton("➕ Tambah Produk");
    public JButton btnEditProduk = new JButton("✏️ Edit Produk");
    public JButton btnUpdateStok = new JButton("🔄 Update Stok");
    public JButton btnHapusProduk = new JButton("❌ Hapus Produk");

    // --- TAB 2: KATEGORI ---
    public JTable tabelKategori;
    public DefaultTableModel modelKategori;
    public JButton btnTambahKategori = new JButton("➕ Tambah Kategori Baru");
    public JButton btnRefreshKategori = new JButton("🔄 Segarkan Kategori");

    public AllDataPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblJudul = new JLabel("Pusat Manajemen Data Inventaris", SwingConstants.LEFT);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));
        lblJudul.setForeground(new Color(44, 62, 80));
        add(lblJudul, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ================= TAB 1: KELOLA PRODUK =================
        JPanel panelProduk = new JPanel(new BorderLayout(10, 10));
        panelProduk.setBackground(Color.WHITE);
        panelProduk.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tabelProduk = new JTable();
        tabelProduk.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollProduk = new JScrollPane(tabelProduk);
        panelProduk.add(scrollProduk, BorderLayout.CENTER);

        JPanel panelAksiProduk = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAksiProduk.setBackground(Color.WHITE);
        
        btnTambahProduk.setBackground(new Color(46, 204, 113));
        btnTambahProduk.setForeground(Color.WHITE);
        btnEditProduk.setBackground(new Color(241, 196, 15));
        btnEditProduk.setForeground(Color.BLACK);
        btnUpdateStok.setBackground(new Color(52, 152, 219));
        btnUpdateStok.setForeground(Color.WHITE);
        btnHapusProduk.setBackground(new Color(192, 41, 43));
        btnHapusProduk.setForeground(Color.WHITE);

        panelAksiProduk.add(btnTambahProduk);
        panelAksiProduk.add(btnEditProduk);
        panelAksiProduk.add(btnUpdateStok);
        panelAksiProduk.add(btnHapusProduk);
        panelProduk.add(panelAksiProduk, BorderLayout.SOUTH);

        // ================= TAB 2: KELOLA KATEGORI =================
        JPanel panelKategori = new JPanel(new BorderLayout(10, 10));
        panelKategori.setBackground(Color.WHITE);
        panelKategori.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Pedoman Baru: Kolom tabel ditambahkan "Kode Prefix" agar sesuai database fisik
        String[] kolomKategori = {"ID Kategori", "Nama Kategori", "Kode Prefix", "Deskripsi Keterangan"};
        modelKategori = new DefaultTableModel(kolomKategori, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabelKategori = new JTable(modelKategori);
        tabelKategori.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollKategori = new JScrollPane(tabelKategori);
        panelKategori.add(scrollKategori, BorderLayout.CENTER);

        JPanel panelAksiKategori = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAksiKategori.setBackground(Color.WHITE);
        
        btnTambahKategori.setBackground(new Color(155, 89, 182));
        btnTambahKategori.setForeground(Color.WHITE);
        btnRefreshKategori.setBackground(new Color(149, 165, 166));
        btnRefreshKategori.setForeground(Color.WHITE);

        panelAksiKategori.add(btnTambahKategori);
        panelAksiKategori.add(btnRefreshKategori);
        panelKategori.add(panelAksiKategori, BorderLayout.SOUTH);

        tabbedPane.addTab("📦 Data Master Produk", panelProduk);
        tabbedPane.addTab("🏷️ Daftar Kategori Barang", panelKategori);

        add(tabbedPane, BorderLayout.CENTER);
    }
}