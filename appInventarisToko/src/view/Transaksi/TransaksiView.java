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
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransaksiView extends JFrame {

    private final ControllerTransaksi ctrl;
    private final ModelUser           user;
    private final List<ModelDetailTransaksi> keranjang = new ArrayList<>();

    private final JTextField inputKode   = new JTextField(12);
    private final JTextField inputJumlah = new JTextField("1", 5);
    private final JLabel     lblNama     = new JLabel("-");
    private final JLabel     lblHarga    = new JLabel("-");
    private final JLabel     lblTotal    = new JLabel("Rp 0");
    private final JTextField inputBayar  = new JTextField(14);

    private final DefaultTableModel keranjangModel;
    private final JTable keranjangTable;

    public TransaksiView(ModelUser user) {
        this.user = user;
        this.ctrl = new ControllerTransaksi(this);

        setTitle("Transaksi Penjualan - " + user.getNamaLengkap());
        setSize(860, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] kolom = {"#", "Nama Produk", "Harga", "Jumlah", "Subtotal"};
        keranjangModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        keranjangTable = new JTable(keranjangModel);
        keranjangTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("🛒 Keranjang Belanja"));
        leftPanel.add(new JScrollPane(keranjangTable), BorderLayout.CENTER);

        JButton btnHapusItem = new JButton("🗑 Hapus Item");
        btnHapusItem.addActionListener(e -> hapusItem());
        JPanel belowCart = new JPanel(new FlowLayout(FlowLayout.LEFT));
        belowCart.add(btnHapusItem);
        leftPanel.add(belowCart, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        rightPanel.setPreferredSize(new Dimension(280, 0));

        rightPanel.add(sectionTitle("📦 Cari Produk"));
        rightPanel.add(labeledField("Kode Produk:", inputKode));
        JButton btnCariProduk = new JButton("Cari →");
        rightPanel.add(rightBtn(btnCariProduk));
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(labeledInfo("Nama:", lblNama));
        rightPanel.add(labeledInfo("Harga:", lblHarga));
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(labeledField("Jumlah:", inputJumlah));
        JButton btnTambahKeranjang = new JButton("+ Tambah ke Keranjang");
        rightPanel.add(rightBtn(btnTambahKeranjang));
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(sectionTitle("💳 Pembayaran"));
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTotal.setForeground(new Color(0, 120, 0));
        rightPanel.add(labeledInfo("Total:", lblTotal));
        rightPanel.add(labeledField("Uang Bayar:", inputBayar));

        JButton btnBayar = new JButton("✅ Selesaikan Transaksi");
        btnBayar.setBackground(new Color(0, 160, 0));
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setFont(new Font("SansSerif", Font.BOLD, 13));
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(rightBtn(btnBayar));
        rightPanel.add(Box.createVerticalStrut(16));

        JButton btnMenu = new JButton("← Kembali ke Menu");
        rightPanel.add(rightBtn(btnMenu));

        add(leftPanel,  BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        ActionListener cariAction = e -> cariProduk();
        btnCariProduk.addActionListener(cariAction);
        inputKode.addActionListener(cariAction);

        btnTambahKeranjang.addActionListener(e -> tambahKeKeranjang());
        btnBayar.addActionListener(e -> selesaikanTransaksi());
        btnMenu.addActionListener(e -> { dispose(); new MenuView(user); });

        setVisible(true);
    }

    private void cariProduk() {
        String kode = inputKode.getText().trim().toUpperCase();
        ModelProduk p = ctrl.cariProduk(kode);
        if (p == null) {
            lblNama.setText("Produk tidak ditemukan");
            lblHarga.setText("-");
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
                    refreshKeranjang();
                    return;
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
            double total = keranjang.stream().mapToDouble(ModelDetailTransaksi::getSubtotal).sum();

            ModelTransaksi trx = new ModelTransaksi();
            trx.setKodeTransaksi(new ControllerTransaksi(this).generateKodeTransaksi());
            trx.setIdUser(user.getId());
            trx.setTotalBayar(bayar);
            for (ModelDetailTransaksi d : keranjang) trx.addDetail(d);
            trx.hitungTotalDanKembalian();

            ctrl.simpanTransaksi(trx);  // runs via SwingWorker internally
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Uang bayar harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Called by controller after successful save (on EDT via SwingWorker.done) */
    public void resetKeranjang() {
        keranjang.clear();
        keranjangModel.setRowCount(0);
        lblTotal.setText("Rp 0");
        inputBayar.setText("");
        inputKode.setText("");
        lblNama.setText("-"); lblHarga.setText("-");
        lblNama.putClientProperty("produk", null);
        inputJumlah.setText("1");
    }

    public String generateKodeTransaksi() {
        return new ControllerTransaksi(this).generateKodeTransaksi();
    }

    // Layout helpers
    private JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JPanel labeledField(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(4, 0));
        p.setMaximumSize(new Dimension(270, 50));
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JPanel labeledInfo(String label, JLabel val) {
        JPanel p = new JPanel(new BorderLayout(4, 0));
        p.setMaximumSize(new Dimension(270, 32));
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(val, BorderLayout.CENTER);
        return p;
    }

    private JPanel rightBtn(JButton btn) {
        btn.setMaximumSize(new Dimension(270, 38));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        JPanel p = new JPanel(new BorderLayout());
        p.setMaximumSize(new Dimension(270, 40));
        p.setAlignmentX(LEFT_ALIGNMENT);
        p.add(btn);
        return p;
    }
}
