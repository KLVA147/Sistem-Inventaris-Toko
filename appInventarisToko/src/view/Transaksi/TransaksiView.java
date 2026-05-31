/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Transaksi;

import controller.ControllerTransaksi;
import model.Produk.ModelProduk;
import model.Transaksi.ModelDetailTransaksi;
import model.Transaksi.ModelTransaksi;
import model.User.ModelUser;
import view.Menu.MenuView;
import view.theme.MetroTheme;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TransaksiView extends JFrame {

    private final ControllerTransaksi ctrl;
    private final ModelUser           user;
    private final List<ModelDetailTransaksi> keranjang = new ArrayList<>();

    private final JTextField inputKode   = new JTextField(12);
    private final JTextField inputJumlah = new JTextField("1", 5);
    private final JLabel     lblNama     = new JLabel("—");
    private final JLabel     lblHarga    = new JLabel("—");
    private final JLabel     lblTotal    = new JLabel("Rp 0");
    private final JTextField inputBayar  = new JTextField(14);

    private final DefaultTableModel keranjangModel;
    private final JTable keranjangTable;

    public TransaksiView(ModelUser user) {
        this.user = user;
        this.ctrl = new ControllerTransaksi(this);
        MetroTheme.install();

        setTitle("Transaksi Penjualan — " + user.getNamaLengkap());
        setSize(920, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MetroTheme.BG_SURFACE);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(MetroTheme.ACCENT);
        accentStrip.setPreferredSize(new Dimension(0, 4));
        header.add(accentStrip, BorderLayout.NORTH);
        header.add(MetroTheme.titleLabel("🧾  Transaksi Penjualan"), BorderLayout.WEST);
        JButton btnMenuTop = MetroTheme.ghostButton("← Kembali ke Menu");
        btnMenuTop.addActionListener(e -> { dispose(); new MenuView(user); });
        JPanel hRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        hRight.setOpaque(false); hRight.add(btnMenuTop);
        header.add(hRight, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        String[] kolom = {"#", "Nama Produk", "Harga", "Jumlah", "Subtotal"};
        keranjangModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        keranjangTable = new JTable(keranjangModel);
        keranjangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        MetroTheme.styleTable(keranjangTable);

        JPanel cartPanel = new JPanel(new BorderLayout(0, 8));
        cartPanel.setBackground(MetroTheme.BG_DARK);
        cartPanel.setBorder(MetroTheme.cardBorder("🛒 Keranjang Belanja"));

        JButton btnHapusItem = MetroTheme.dangerButton("🗑  Hapus Item");
        btnHapusItem.addActionListener(e -> hapusItem());
        JPanel cartBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cartBottom.setOpaque(false);
        cartBottom.add(btnHapusItem);

        cartPanel.add(MetroTheme.styledScrollPane(keranjangTable), BorderLayout.CENTER);
        cartPanel.add(cartBottom, BorderLayout.SOUTH);

        JPanel cartWrap = new JPanel(new BorderLayout());
        cartWrap.setBackground(MetroTheme.BG_DARK);
        cartWrap.setBorder(new EmptyBorder(10, 12, 10, 6));
        cartWrap.add(cartPanel, BorderLayout.CENTER);
        root.add(cartWrap, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(MetroTheme.BG_SURFACE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, MetroTheme.BORDER),
            new EmptyBorder(16, 16, 16, 16)));
        rightPanel.setPreferredSize(new Dimension(300, 0));

        rightPanel.add(sectionLabel("📦  Cari Produk"));
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(fieldRow("Kode:", inputKode));
        rightPanel.add(Box.createVerticalStrut(6));

        JButton btnCariProduk = MetroTheme.primaryButton("Cari Produk →");
        btnCariProduk.setAlignmentX(LEFT_ALIGNMENT);
        btnCariProduk.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        rightPanel.add(btnCariProduk);
        rightPanel.add(Box.createVerticalStrut(10));

        lblNama.setFont(MetroTheme.FONT_BODY);
        lblNama.setForeground(MetroTheme.TEXT_PRIMARY);
        lblHarga.setFont(MetroTheme.FONT_SMALL);
        lblHarga.setForeground(MetroTheme.TEXT_SECONDARY);
        rightPanel.add(infoRow("Nama:", lblNama));
        rightPanel.add(Box.createVerticalStrut(2));
        rightPanel.add(infoRow("Harga:", lblHarga));
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(fieldRow("Jumlah:", inputJumlah));
        rightPanel.add(Box.createVerticalStrut(6));

        JButton btnTambahKeranjang = MetroTheme.ghostButton("+ Tambah ke Keranjang");
        btnTambahKeranjang.setAlignmentX(LEFT_ALIGNMENT);
        btnTambahKeranjang.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        rightPanel.add(btnTambahKeranjang);
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(MetroTheme.accentSeparator());
        rightPanel.add(Box.createVerticalStrut(16));

        rightPanel.add(sectionLabel("💳  Pembayaran"));
        rightPanel.add(Box.createVerticalStrut(10));

        lblTotal.setFont(MetroTheme.FONT_TOTAL);
        lblTotal.setForeground(MetroTheme.ACCENT_GREEN);
        lblTotal.setAlignmentX(LEFT_ALIGNMENT);
        rightPanel.add(MetroTheme.mutedLabel("Total"));
        rightPanel.add(lblTotal);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(fieldRow("Uang Bayar:", inputBayar));
        rightPanel.add(Box.createVerticalStrut(14));

        JButton btnBayar = MetroTheme.successButton("✅  Selesaikan Transaksi");
        btnBayar.setAlignmentX(LEFT_ALIGNMENT);
        btnBayar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnBayar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(btnBayar);

        root.add(rightPanel, BorderLayout.EAST);

        MetroTheme.styleTextField(inputKode);
        MetroTheme.styleTextField(inputJumlah);
        MetroTheme.styleTextField(inputBayar);

        ActionListener cariAction = e -> cariProduk();
        btnCariProduk.addActionListener(cariAction);
        inputKode.addActionListener(cariAction);
        btnTambahKeranjang.addActionListener(e -> tambahKeKeranjang());
        btnBayar.addActionListener(e -> selesaikanTransaksi());

        setVisible(true);
    }

    private void cariProduk() {
        String kode = inputKode.getText().trim().toUpperCase();
        ModelProduk p = ctrl.cariProduk(kode);
        if (p == null) {
            lblNama.setText("Produk tidak ditemukan");
            lblHarga.setText("—");
        } else {
            lblNama.setText(p.getNama());
            lblHarga.setText(String.format("Rp %,.0f / %s", p.getHargaJual(), p.getSatuan()));
            lblNama.putClientProperty("produk", p);
        }
    }

    private void tambahKeKeranjang() {
        ModelProduk p = (ModelProduk) lblNama.getClientProperty("produk");
        if (p == null) { JOptionPane.showMessageDialog(null, "Cari produk dulu!"); return; }
        try {
            int jumlah = Integer.parseInt(inputJumlah.getText().trim());
            if (jumlah <= 0) throw new NumberFormatException();
            if (jumlah > p.getStok()) {
                JOptionPane.showMessageDialog(null,
                    "Stok tidak cukup! Stok tersedia: " + p.getStok() + " " + p.getSatuan());
                return;
            }
            for (ModelDetailTransaksi d : keranjang) {
                if (d.getIdProduk().equals(p.getId())) {
                    d.setJumlah(d.getJumlah() + jumlah);
                    refreshKeranjang(); return;
                }
            }
            keranjang.add(new ModelDetailTransaksi(p.getId(), p.getNama(), p.getHargaJual(), jumlah));
            refreshKeranjang();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Jumlah harus angka positif!");
        }
    }

    private void hapusItem() {
        int row = keranjangTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(null, "Pilih item dulu!"); return; }
        keranjang.remove(row);
        refreshKeranjang();
    }

    private void refreshKeranjang() {
        keranjangModel.setRowCount(0);
        double total = 0;
        int no = 1;
        for (ModelDetailTransaksi d : keranjang) {
            keranjangModel.addRow(new Object[]{
                no++,
                d.getNamaProduk(),
                String.format("Rp %,.0f", d.getHargaJual()),
                d.getJumlah(),
                String.format("Rp %,.0f", d.getSubtotal())
            });
            total += d.getSubtotal();
        }
        lblTotal.setText(String.format("Rp %,.0f", total));
    }

    private void selesaikanTransaksi() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Keranjang masih kosong!"); return;
        }
        try {
            double bayar = Double.parseDouble(inputBayar.getText().trim().replace(",", "").replace(".", ""));
            ModelTransaksi trx = new ModelTransaksi();
            trx.setKodeTransaksi(new ControllerTransaksi(this).generateKodeTransaksi());
            trx.setIdUser(user.getId());
            trx.setTotalBayar(bayar);
            for (ModelDetailTransaksi d : keranjang) trx.addDetail(d);
            trx.hitungTotalDanKembalian();
            ctrl.simpanTransaksi(trx);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Uang bayar harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetKeranjang() {
        keranjang.clear();
        keranjangModel.setRowCount(0);
        lblTotal.setText("Rp 0");
        inputBayar.setText("");
        inputKode.setText("");
        lblNama.setText("—"); lblHarga.setText("—");
        lblNama.putClientProperty("produk", null);
        inputJumlah.setText("1");
    }

    public String generateKodeTransaksi() {
        return new ControllerTransaksi(this).generateKodeTransaksi();
    }

    // Layout helpers
    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(MetroTheme.FONT_HEADING);
        l.setForeground(MetroTheme.TEXT_SECONDARY);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JPanel fieldRow(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        JLabel lbl = MetroTheme.bodyLabel(label);
        lbl.setForeground(MetroTheme.TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(72, 0));
        p.add(lbl, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JPanel infoRow(String label, JLabel val) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel lbl = MetroTheme.mutedLabel(label);
        lbl.setPreferredSize(new Dimension(50, 0));
        p.add(lbl, BorderLayout.WEST);
        p.add(val, BorderLayout.CENTER);
        return p;
    }
}
