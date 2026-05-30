/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.AllDataController;
/**
 *
 * @author umair
 */
public class TransactionPanel extends JPanel {
    // === KOMPONEN TAB 1: FORM CHECKOUT (KERANJANG BELANJA) ===
    public JTextField txtNamaBarang = new JTextField(15);
    public JTextField txtHarga = new JTextField(10);
    public JTextField txtJumlah = new JTextField(5);
    public JButton btnTambahKeranjang = new JButton("Tambah ke Keranjang");
    
    // Tabel Keranjang Sementara
    public JTable tabelKeranjang;
    public DefaultTableModel modelKeranjang;
    public JButton btnHapusItem = new JButton("Hapus Item");
    public JLabel lblTotalHarga = new JLabel("Total: Rp 0");
    public JButton btnCheckout = new JButton("Checkout");

    // === KOMPONEN TAB 2: RIWAYAT TRANSAKSI ===
    public JTable tabelRiwayat;
    public DefaultTableModel modelRiwayat;
    public JButton btnLihatDetail = new JButton("Lihat Detail Barang");
    public JButton btnRefresh = new JButton("Segarkan Riwayat");

    public TransactionPanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // ------------------ TAB 1: KASIR / CHECKOUT ------------------
        JPanel panelKasir = new JPanel(new BorderLayout(10, 10));
        panelKasir.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 1. Form Input Atas (Bungkus dengan panel tambahan agar tingginya terkunci)
        JPanel panelInputWrapper = new JPanel(new BorderLayout());
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Barang"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Perbesar padding textfield agar rapi
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Berikan bobot horizontal agar textfield melebar proporsional

        gbc.gridx = 0; gbc.gridy = 0; panelInput.add(new JLabel("Nama Barang:"), gbc);
        gbc.gridx = 1; panelInput.add(txtNamaBarang, gbc);

        gbc.gridx = 2; panelInput.add(new JLabel("Harga Satuan (Rp):"), gbc);
        gbc.gridx = 3; panelInput.add(txtHarga, gbc);

        gbc.gridx = 4; panelInput.add(new JLabel("Qty:"), gbc);
        gbc.gridx = 5; panelInput.add(txtJumlah, gbc);

        gbc.gridx = 6;
        btnTambahKeranjang.setBackground(new Color(46, 204, 113));
        btnTambahKeranjang.setForeground(Color.WHITE);
        panelInput.add(btnTambahKeranjang, gbc);

        // KUNCI 1: Masukkan panelInput ke NORTH dari wrapper agar tingginya tidak melar ke bawah
        panelInputWrapper.add(panelInput, BorderLayout.NORTH);

        // 2. Tabel Keranjang Tengah
        String[] kolomKeranjang = {"Nama Barang", "Harga Satuan", "Jumlah", "Subtotal"};
        modelKeranjang = new DefaultTableModel(kolomKeranjang, 0);
        tabelKeranjang = new JTable(modelKeranjang);

        // KUNCI 2: Atur viewport table agar tidak memaksa meminta ukuran terlalu besar di awal
        tabelKeranjang.setPreferredScrollableViewportSize(new Dimension(500, 150)); 
        JScrollPane scrollKeranjang = new JScrollPane(tabelKeranjang);

        // Panel Aksi Keranjang (Bawah Tabel Keranjang)
        JPanel panelAksiKeranjang = new JPanel(new BorderLayout());
        panelAksiKeranjang.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        lblTotalHarga.setFont(new Font("Arial", Font.BOLD, 16));
        panelAksiKeranjang.add(btnHapusItem, BorderLayout.WEST);
        panelAksiKeranjang.add(lblTotalHarga, BorderLayout.EAST);

        // Tombol Checkout Paling Bawah
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 14));
        btnCheckout.setBackground(new Color(41, 128, 185));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setPreferredSize(new Dimension(0, 40));

        // Gabungkan komponen Tab 1 ke panelKasir utama
        JPanel panelTengahKasir = new JPanel(new BorderLayout(5, 5));
        panelTengahKasir.add(scrollKeranjang, BorderLayout.CENTER); // CENTER akan otomatis mengambil sisa ruang luas
        panelTengahKasir.add(panelAksiKeranjang, BorderLayout.SOUTH);

        // KUNCI 3: Distribusi BorderLayout yang ideal
        panelKasir.add(panelInputWrapper, BorderLayout.NORTH); // Form di atas
        panelKasir.add(panelTengahKasir, BorderLayout.CENTER);  // Tabel & Total di tengah (fleksibel)
        panelKasir.add(btnCheckout, BorderLayout.SOUTH);       // Tombol checkout di bawah
        }
}