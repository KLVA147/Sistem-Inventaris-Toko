/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import view.TransactionPanel;
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
    public TransactionPanel transaction = new TransactionPanel(); // Silakan aktifkan jika class sudah ada
    public AllDataPanel panelLihatDataReal = new AllDataPanel();
    
    public MainMenuView() {
        // Pengaturan Utama Jendela (Medium Size)
        setTitle("Sistem Manajemen Inventaris Toko");
        setSize(800, 550); // Sedikit dinaikkan agar proporsi dengan lebar sidebar baru lebih pas
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

        // Judul Aplikasi Kecil di Atas Tombol
        JLabel lblMenu = new JLabel("NAVIGASI", SwingConstants.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 12));
        lblMenu.setForeground(new Color(149, 165, 166)); // Warna abu-abu soft agar lebih modern
        
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 12, 25, 12); // Jarak khusus untuk judul menu (jarak bawah lebih besar)
        panelSidebar.add(lblMenu, gbc);

        // Reset inset standar untuk tombol
        gbc.insets = new Insets(0, 12, 10, 12);

        // Desain & Tambah Tombol
        desainTombolSidebar(btnTransaksi);
        gbc.gridy = 1;
        panelSidebar.add(btnTransaksi, gbc);

        desainTombolSidebar(btnLihatData);
        gbc.gridy = 2;
        panelSidebar.add(btnLihatData, gbc);

        // Membuat space kosong fleksibel sebelum tombol exit (mendorong exit ke paling bawah)
        gbc.gridy = 3;
        gbc.weighty = 1.0; 
        panelSidebar.add(Box.createGlue(), gbc);

        // Desain & Tambah Tombol Exit
        desainTombolSidebar(btnExit);
        btnExit.setBackground(new Color(192, 41, 43)); 
        gbc.gridy = 4;
        gbc.weighty = 0.0; // Reset weighty
        gbc.insets = new Insets(0, 12, 20, 12); // Jarak bawah untuk tombol terakhir
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

        // 2. Placeholder Halaman Transaksi
        JPanel panelTransaksi = new JPanel(new BorderLayout());
        panelTransaksi.add(new JLabel("=== HALAMAN TRANSAKSI STOK ===", SwingConstants.CENTER), BorderLayout.NORTH);

        // 3. Placeholder Halaman Lihat Data
        JPanel panelData = new JPanel(new BorderLayout());
        panelData.add(new JLabel("=== HALAMAN SEMUA DATA INVENTARIS ===", SwingConstants.CENTER), BorderLayout.NORTH);
        

        // Memasukkan semua halaman ke dalam CardLayout
        panelKonten.add(panelWelcome, "Welcome");
        panelKonten.add(transaction, "Transaksi"); 
        
        // KUNCI: Daftarkan panelLihatDataReal dengan key "LihatData"
        panelKonten.add(panelLihatDataReal, "LihatData");

        // Satukan ke JFrame Utama
        add(panelSidebar, BorderLayout.WEST);
        add(panelKonten, BorderLayout.CENTER);
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

    
    public static void main(String[] args) {
        // TODO code application logic here
        javax.swing.SwingUtilities.invokeLater(() -> {
        MainMenuView menu  = new MainMenuView();
//        LoginDAO model = new LoginDAO;
//        LoginController controller = new LoginController(login, model);
        menu.setVisible(true);
    });
    }
}