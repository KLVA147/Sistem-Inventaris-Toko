/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Menu;

import model.User.ModelUser;
import view.Login.LoginView;
import view.Produk.ProdukView;
import view.Kategori.KategoriView;
import view.Transaksi.TransaksiView;
import view.Transaksi.RiwayatTransaksiView;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Main menu — shows only the buttons the logged-in user's role is allowed to use.
 * OOP Pillar: Encapsulation (user object passed in, role checked inside).
 */
public class MenuView extends JFrame {

    private final ModelUser user;

    public MenuView(ModelUser user) {
        this.user = user;

        setTitle("Sistem Inventaris Toko - Menu Utama");
        setSize(440, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(24, 40, 24, 40));

        JLabel header = new JLabel("Selamat datang, " + user.getNamaLengkap() + "!");
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(header);

        JLabel roleLabel = new JLabel("Role: " + user.getRole().toUpperCase());
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        roleLabel.setForeground(Color.GRAY);
        roleLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(roleLabel);
        panel.add(Box.createVerticalStrut(24));

        // -- Kasir & Admin: Transaksi
        if (user.isKasir() || user.isAdmin()) {
            panel.add(buatTombol("🧾  Transaksi Penjualan", e -> {
                dispose(); new TransaksiView(user);
            }));
            panel.add(Box.createVerticalStrut(8));
            panel.add(buatTombol("📋  Riwayat Transaksi", e -> {
                dispose(); new RiwayatTransaksiView(user);
            }));
            panel.add(Box.createVerticalStrut(8));
        }

        // -- Gudang & Admin: Produk
        if (user.isGudang() || user.isAdmin()) {
            panel.add(buatTombol("📦  Kelola Produk", e -> {
                dispose(); new ProdukView(user);
            }));
            panel.add(Box.createVerticalStrut(8));
        }

        // -- Admin only: Kategori
        if (user.isAdmin()) {
            panel.add(buatTombol("🗂  Kelola Kategori", e -> {
                dispose(); new KategoriView(user);
            }));
            panel.add(Box.createVerticalStrut(8));
        }

        panel.add(Box.createVerticalStrut(16));
        panel.add(buatTombol("🚪  Logout", e -> {
            int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin logout?",
                "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (ok == 0) { dispose(); new LoginView(); }
        }));

        add(panel);
        setVisible(true);
    }

    private JButton buatTombol(String text, java.awt.event.ActionListener al) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(340, 44));
        btn.setMaximumSize(new Dimension(340, 44));
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.addActionListener(al);
        return btn;
    }
}
