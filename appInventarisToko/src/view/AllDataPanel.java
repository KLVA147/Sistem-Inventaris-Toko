/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import controller.AllDataController;
/**
 *
 * @author umair
 */
public class AllDataPanel extends JPanel{
    // Tombol Opsi Kategori Produk
    public JButton btnMakanan = new JButton("🍔 Makanan");
    public JButton btnMinuman = new JButton("🍹 Minuman");
    public JButton btnFrozenFood = new JButton("❄️ Frozen Food");
    public JButton btnSabun = new JButton("🧼 Sabun & Kosmetik");

    public AllDataPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Judul Atas
        JLabel lblJudul = new JLabel("Kategori Inventaris Barang", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 22));
        lblJudul.setForeground(new Color(44, 62, 80));
        add(lblJudul, BorderLayout.NORTH);

        // Panel Tengah tempat Tombol Kategori (Grid Layout)
        JPanel panelGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        panelGrid.setBackground(Color.WHITE);

        // Desain Tombol/Kartu Opsi Kategori
        desainTombolKategori(btnMakanan, new Color(230, 126, 34));   // Orange
        desainTombolKategori(btnMinuman, new Color(52, 152, 219));   // Blue
        desainTombolKategori(btnFrozenFood, new Color(26, 188, 156)); // Turquoise
        desainTombolKategori(btnSabun, new Color(155, 89, 182));     // Purple

        panelGrid.add(btnMakanan);
        panelGrid.add(btnMinuman);
        panelGrid.add(btnFrozenFood);
        panelGrid.add(btnSabun);

        add(panelGrid, BorderLayout.CENTER);
    }

    private void desainTombolKategori(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efek transisi sederhana saat diklik bisa di-handle look and feel, 
        // tapi prefered size dikunci agar proporsional
        button.setPreferredSize(new Dimension(150, 100));
    }
}
