/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.transaksi;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author umair
 */
public class TransactionPanel extends JPanel {
    public JTextField txtKodeBarang = new JTextField(10);
    public JButton btnCariBarang = new JButton("🔍 Cari");
    
    public JTextField txtNamaBarang = new JTextField(15);
    public JTextField txtHarga = new JTextField(12);
    public JTextField txtJumlah = new JTextField(5);
    public JButton btnTambahKeranjang = new JButton("Tambah ke Keranjang");
    
    public JTable tabelKeranjang;
    public DefaultTableModel modelKeranjang;
    public JButton btnHapusItem = new JButton("Hapus Item");
    public JLabel lblTotalHarga = new JLabel("Total: Rp 0");
    public JButton btnCheckout = new JButton("Checkout");

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

        JPanel panelInputWrapper = new JPanel(new BorderLayout());
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Barang Belanja (Sistem Pencarian)"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 0; panelInput.add(new JLabel("Kode Barang:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; panelInput.add(txtKodeBarang, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        btnCariBarang.setBackground(new Color(52, 152, 219));
        btnCariBarang.setForeground(Color.WHITE);
        panelInput.add(btnCariBarang, gbc);

        gbc.gridx = 3; gbc.weightx = 0.0; panelInput.add(new JLabel("Nama Barang:"), gbc);
        gbc.gridx = 4; gbc.weightx = 1.0; panelInput.add(txtNamaBarang, gbc);
        txtNamaBarang.setEditable(false);
        txtNamaBarang.setBackground(new Color(245, 245, 245));

        gbc.gridx = 5; gbc.weightx = 0.0; panelInput.add(new JLabel("Harga (Rp):"), gbc);
        gbc.gridx = 6; gbc.weightx = 1.0; panelInput.add(txtHarga, gbc);
        txtHarga.setEditable(false);
        txtHarga.setBackground(new Color(245, 245, 245));

        gbc.gridx = 7; gbc.weightx = 0.0; panelInput.add(new JLabel("Qty:"), gbc);
        gbc.gridx = 8; gbc.weightx = 0.5; panelInput.add(txtJumlah, gbc);

        gbc.gridx = 9; gbc.weightx = 0.0;
        btnTambahKeranjang.setBackground(new Color(46, 204, 113));
        btnTambahKeranjang.setForeground(Color.WHITE);
        btnTambahKeranjang.setFont(new Font("Arial", Font.BOLD, 12));
        panelInput.add(btnTambahKeranjang, gbc);

        panelInputWrapper.add(panelInput, BorderLayout.CENTER);
        panelInputWrapper.setPreferredSize(new Dimension(0, 85)); 

        String[] kolomKeranjang = {"Kode Barang", "Nama Barang", "Harga Satuan", "Jumlah", "Subtotal"};
        modelKeranjang = new DefaultTableModel(kolomKeranjang, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabelKeranjang = new JTable(modelKeranjang);
        tabelKeranjang.setPreferredScrollableViewportSize(new Dimension(500, 200)); 
        JScrollPane scrollKeranjang = new JScrollPane(tabelKeranjang);

        JPanel panelAksiKeranjang = new JPanel(new BorderLayout());
        panelAksiKeranjang.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        lblTotalHarga.setFont(new Font("Arial", Font.BOLD, 18));
        
        btnHapusItem.setBackground(new Color(231, 76, 60));
        btnHapusItem.setForeground(Color.WHITE);
        panelAksiKeranjang.add(btnHapusItem, BorderLayout.WEST);
        panelAksiKeranjang.add(lblTotalHarga, BorderLayout.EAST);

        btnCheckout.setFont(new Font("Arial", Font.BOLD, 15));
        btnCheckout.setBackground(new Color(41, 128, 185));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setPreferredSize(new Dimension(0, 45));

        JPanel panelTengahKasir = new JPanel(new BorderLayout(5, 5));
        panelTengahKasir.add(scrollKeranjang, BorderLayout.CENTER); 
        panelTengahKasir.add(panelAksiKeranjang, BorderLayout.SOUTH);

        panelKasir.add(panelInputWrapper, BorderLayout.NORTH); 
        panelKasir.add(panelTengahKasir, BorderLayout.CENTER);  
        panelKasir.add(btnCheckout, BorderLayout.SOUTH);       

        // ------------------ TAB 2: RIWAYAT TRANSAKSI ------------------
        JPanel panelRiwayat = new JPanel(new BorderLayout(10, 10));
        panelRiwayat.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] kolomRiwayat = {"ID Transaksi", "Kode Transaksi", "Kasir / User", "Total Harga", "Total Bayar", "Kembalian", "Tanggal"};
        
        modelRiwayat = new DefaultTableModel(kolomRiwayat, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabelRiwayat = new JTable(modelRiwayat);
        JScrollPane scrollRiwayat = new JScrollPane(tabelRiwayat);

        JPanel panelAksiRiwayat = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnLihatDetail.setBackground(new Color(52, 152, 219));
        btnLihatDetail.setForeground(Color.WHITE);
        panelAksiRiwayat.add(btnLihatDetail);
        panelAksiRiwayat.add(btnRefresh);

        panelRiwayat.add(scrollRiwayat, BorderLayout.CENTER);
        panelRiwayat.add(panelAksiRiwayat, BorderLayout.SOUTH);

        tabbedPane.addTab("Kasir / Transaksi Baru", panelKasir);
        tabbedPane.addTab("Riwayat Nota Penjualan", panelRiwayat);

        add(tabbedPane, BorderLayout.CENTER);
    }
}