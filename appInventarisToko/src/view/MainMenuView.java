/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import view.transaksi.TransactionPanel;
import view.data.AllDataPanel;
import javax.swing.*;
import java.awt.*;
import model.User.ModelUser;

/**
 *
 * @author umair
 */
public class MainMenuView extends JFrame {
    // Tombol Navigasi Sidebar
    public JButton btnTransaksi = new JButton("Transaksi Stok");
    public JButton btnLihatData = new JButton("Lihat Semua Data");
    public JButton btnExit = new JButton("Keluar Aplikasi");
    
    // Panel Konten Dinamis (Kanan)
    public JPanel panelKonten = new JPanel();
    public CardLayout cardLayout = new CardLayout();
    
    // Inisialisasi Panel Konten Halaman
    public TransactionPanel transaction = new TransactionPanel(); 
    public AllDataPanel panelLihatDataReal = new AllDataPanel();
    
    public MainMenuView() {
        // Pengaturan Utama Jendela (Medium Size)
        setTitle("Sistem Manajemen Inventaris Toko");
        setSize(950, 600); // Penyesuaian dimensi sedikit agar penataan tabel kasir lebih longgar
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        // --- PANEL KIRI: SIDEBAR NAVIGASI ---
        JPanel panelSidebar = new JPanel();
        // Menggunakan GridBagLayout agar tinggi tombol tidak dipaksa memenuhi layar
        panelSidebar.setLayout(new GridBagLayout()); 
        panelSidebar.setBackground(new Color(44, 62, 80)); 
        
        // KUNCI: Mengatur ukuran lebar maksimal sidebar agar tidak terlalu besar
        panelSidebar.setPreferredSize(new Dimension(180, getHeight()));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 12, 12, 12); // Jarak antar komponen (top, left, bottom, right)

        JLabel lblMenu = new JLabel("NAVIGASI", SwingConstants.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 12));
        lblMenu.setForeground(new Color(149, 165, 166)); // Warna abu-abu soft agar lebih modern
        
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 12, 25, 12); 
        panelSidebar.add(lblMenu, gbc);

        gbc.insets = new Insets(0, 12, 10, 12);

        desainTombolSidebar(btnTransaksi);
        gbc.gridy = 1;
        panelSidebar.add(btnTransaksi, gbc);

        desainTombolSidebar(btnLihatData);
        gbc.gridy = 2;
        panelSidebar.add(btnLihatData, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1.0; 
        panelSidebar.add(Box.createGlue(), gbc);

        desainTombolSidebar(btnExit);
        btnExit.setBackground(new Color(192, 41, 43)); 
        gbc.gridy = 4;
        gbc.weighty = 0.0; 
        gbc.insets = new Insets(0, 12, 20, 12); 
        panelSidebar.add(btnExit, gbc);


        // --- PANEL KANAN: KONTEN UTAMA (CARD LAYOUT) ---
        panelKonten.setLayout(cardLayout);
        panelKonten.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelKonten.setBackground(Color.WHITE); // Background putih bersih untuk konten

        // 1. Halaman Selamat Datang (Default)
        JPanel panelWelcome = new JPanel(new GridBagLayout());
        panelWelcome.setBackground(Color.WHITE);
        JLabel lblJudulUtama = new JLabel("Selamat Datang di App Inventaris");
        lblJudulUtama.setFont(new Font("Arial", Font.BOLD, 22));
        lblJudulUtama.setForeground(new Color(44, 62, 80));
        panelWelcome.add(lblJudulUtama);

        // Memasukkan semua halaman ke dalam CardLayout
        panelKonten.add(panelWelcome, "Welcome");
        panelKonten.add(transaction, "Transaksi"); 
        panelKonten.add(panelLihatDataReal, "LihatData");

        // Satukan ke JFrame Utama
        add(panelSidebar, BorderLayout.WEST);
        add(panelKonten, BorderLayout.CENTER);
        
        // Inisialisasi aksi pertukaran panel internal (CardLayout Switcher)
        btnTransaksi.addActionListener(e -> panggilPanelHalaman("Transaksi"));
        btnLihatData.addActionListener(e -> panggilPanelHalaman("LihatData"));
        btnExit.addActionListener(e -> System.exit(0));
    }

    // Helper untuk merapikan tampilan tombol sidebar
    private void desainTombolSidebar(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Memberikan padding/tinggi yang ideal di dalam tombol
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 35));
    }


    public void panggilPanelHalaman(String keyNamaHalaman) {
        cardLayout.show(panelKonten, keyNamaHalaman);
    }

    /**
     * Mengatur hak akses visibilitas tombol menu berdasarkan role user yang aktif.
     * Admin  -> Bisa ke semua menu.
     * Kasir  -> Hanya menu Transaksi.
     * Gudang -> Hanya menu Lihat Data (Kelola Produk & Kategori).
     * @param user
     */
    public void setAksesMasingMasingRole(ModelUser user) {
        if (user.isAdmin()) {
            btnTransaksi.setVisible(true);
            btnLihatData.setVisible(true);
            panggilPanelHalaman("Welcome");
        } else if (user.isKasir()) {
            btnTransaksi.setVisible(true);
            btnLihatData.setVisible(false);
            panggilPanelHalaman("Transaksi"); // Langsung arahkan ke transaksi
        } else if (user.isGudang()) {
            btnTransaksi.setVisible(false);
            btnLihatData.setVisible(true);
            panggilPanelHalaman("LihatData"); // Langsung arahkan ke manajemen data produk
        }
    }
}