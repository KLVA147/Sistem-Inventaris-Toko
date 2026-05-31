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
import view.theme.MetroTheme;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuView extends JFrame {

    private final ModelUser user;

    public MenuView(ModelUser user) {
        this.user = user;
        MetroTheme.install();

        setTitle("Sistem Inventaris Toko - Menu Utama");
        setSize(480, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Root ─────────────────────────────────────────────────────────────
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        // ── Header ───────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MetroTheme.BG_SURFACE);
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JPanel headerLeft = new JPanel(new GridLayout(2, 1, 0, 2));
        headerLeft.setBackground(MetroTheme.BG_SURFACE);

        JLabel welcome = MetroTheme.titleLabel("Selamat datang, " + user.getNamaLengkap() + "!");
        JLabel roleLabel = MetroTheme.mutedLabel("Role: " + user.getRole().toUpperCase()
            + "   •   Sistem Inventaris Toko");
        headerLeft.add(welcome);
        headerLeft.add(roleLabel);
        header.add(headerLeft, BorderLayout.CENTER);

        JPanel accentBar = new JPanel();
        accentBar.setBackground(MetroTheme.ACCENT);
        accentBar.setPreferredSize(new Dimension(0, 3));
        root.add(accentBar, BorderLayout.NORTH);
        root.add(header, BorderLayout.NORTH);

        // Rebuild – accent strip must be first
        root.removeAll();
        JPanel topWrap = new JPanel(new BorderLayout());
        topWrap.setBackground(MetroTheme.BG_DARK);
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(MetroTheme.ACCENT);
        accentStrip.setPreferredSize(new Dimension(0, 4));
        topWrap.add(accentStrip, BorderLayout.NORTH);
        topWrap.add(header, BorderLayout.CENTER);
        root.add(topWrap, BorderLayout.NORTH);

        // ── Menu tiles ────────────────────────────────────────────────────────
        JPanel tilesArea = new JPanel();
        tilesArea.setLayout(new BoxLayout(tilesArea, BoxLayout.Y_AXIS));
        tilesArea.setBackground(MetroTheme.BG_DARK);
        tilesArea.setBorder(new EmptyBorder(24, 40, 24, 40));

        if (user.isKasir() || user.isAdmin()) {
            tilesArea.add(tile("🧾", "Transaksi Penjualan", "Proses penjualan produk", MetroTheme.ACCENT, e -> {
                dispose(); new TransaksiView(user);
            }));
            tilesArea.add(Box.createVerticalStrut(10));
            tilesArea.add(tile("📋", "Riwayat Transaksi", "Lihat history transaksi", MetroTheme.BG_CARD, e -> {
                dispose(); new RiwayatTransaksiView(user);
            }));
            tilesArea.add(Box.createVerticalStrut(10));
        }

        if (user.isGudang() || user.isAdmin()) {
            tilesArea.add(tile("📦", "Kelola Produk", "Manajemen stok & produk", MetroTheme.BG_CARD, e -> {
                dispose(); new ProdukView(user);
            }));
            tilesArea.add(Box.createVerticalStrut(10));
        }

        if (user.isAdmin()) {
            tilesArea.add(tile("🗂", "Kelola Kategori", "Atur kategori produk", MetroTheme.BG_CARD, e -> {
                dispose(); new KategoriView(user);
            }));
            tilesArea.add(Box.createVerticalStrut(10));
        }

        tilesArea.add(Box.createVerticalStrut(6));
        tilesArea.add(tile("🚪", "Logout", "Keluar dari sesi ini", MetroTheme.BG_SURFACE, e -> {
            int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin logout?",
                "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (ok == 0) { dispose(); new LoginView(); }
        }));

        JScrollPane scroll = new JScrollPane(tilesArea);
        scroll.setBorder(null);
        scroll.setBackground(MetroTheme.BG_DARK);
        scroll.getViewport().setBackground(MetroTheme.BG_DARK);
        root.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    /** A full-width tile button with icon + title + subtitle */
    private JPanel tile(String icon, String title, String subtitle, Color bg, ActionListener al) {
        JPanel tile = new JPanel(new BorderLayout(14, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        tile.setBackground(bg);
        tile.setBorder(new EmptyBorder(14, 18, 14, 18));
        tile.setOpaque(false);
        tile.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        tile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        tile.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(MetroTheme.FONT_HEADING);
        titleLabel.setForeground(MetroTheme.TEXT_PRIMARY);
        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(MetroTheme.FONT_SMALL);
        subLabel.setForeground(MetroTheme.TEXT_SECONDARY);
        textPanel.add(titleLabel);
        textPanel.add(subLabel);
        tile.add(textPanel, BorderLayout.CENTER);

        JLabel arrow = new JLabel("›");
        arrow.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        arrow.setForeground(MetroTheme.TEXT_MUTED);
        tile.add(arrow, BorderLayout.EAST);

        tile.addMouseListener(new java.awt.event.MouseAdapter() {
            final Color normal = bg;
            final Color hover  = bg.brighter();
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { tile.setBackground(hover); tile.repaint(); }
            @Override public void mouseExited(java.awt.event.MouseEvent e)  { tile.setBackground(normal); tile.repaint(); }
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { al.actionPerformed(null); }
        });

        return tile;
    }
}
