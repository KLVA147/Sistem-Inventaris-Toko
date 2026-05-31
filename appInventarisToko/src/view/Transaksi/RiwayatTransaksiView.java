/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Transaksi;

import controller.ControllerTransaksi;
import model.Transaksi.ModelDetailTransaksi;
import model.Transaksi.ModelTransaksi;
import model.User.ModelUser;
import view.Menu.MenuView;
import view.theme.MetroTheme;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RiwayatTransaksiView extends JFrame {

    private final ControllerTransaksi ctrl;
    private final ModelUser           user;
    private final DefaultTableModel   tableModel;
    private final JTable              table;
    private List<ModelTransaksi>      riwayat;

    public RiwayatTransaksiView(ModelUser user) {
        this.user = user;
        this.ctrl = new ControllerTransaksi(null);
        MetroTheme.install();

        setTitle("Riwayat Transaksi");
        setSize(860, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setBackground(MetroTheme.BG_SURFACE);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(MetroTheme.ACCENT);
        accentStrip.setPreferredSize(new Dimension(0, 4));
        header.add(accentStrip, BorderLayout.NORTH);
        header.add(MetroTheme.titleLabel("📋  Riwayat Transaksi"), BorderLayout.WEST);

        JPanel hBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        hBtns.setOpaque(false);
        JButton btnRefresh = MetroTheme.primaryButton("🔄 Refresh");
        JButton btnDetail  = MetroTheme.ghostButton("📋 Lihat Detail");
        JButton btnMenu    = MetroTheme.ghostButton("← Kembali");
        hBtns.add(btnMenu);
        hBtns.add(btnDetail);
        hBtns.add(btnRefresh);
        header.add(hBtns, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        String[] kolom = {"ID", "Kode Transaksi", "Kasir", "Total", "Bayar", "Kembalian", "Status", "Waktu"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        MetroTheme.styleTable(table);

        JPanel tableWrap = new JPanel(new BorderLayout());
        tableWrap.setBackground(MetroTheme.BG_DARK);
        tableWrap.setBorder(new EmptyBorder(10, 12, 12, 12));
        tableWrap.add(MetroTheme.styledScrollPane(table), BorderLayout.CENTER);
        root.add(tableWrap, BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> loadData());
        btnMenu.addActionListener(e -> { dispose(); new MenuView(user); });

        btnDetail.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Pilih transaksi dulu!"); return; }
            tampilkanDetail(riwayat.get(row));
        });

        loadData();
        setVisible(true);
    }

    private void loadData() {
        SwingWorker<List<ModelTransaksi>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ModelTransaksi> doInBackground() {
                System.out.println("[Thread] Loading riwayat di: " + Thread.currentThread().getName());
                return ctrl.getRiwayat();
            }
            @Override
            protected void done() {
                try {
                    riwayat = get();
                    tableModel.setRowCount(0);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    for (ModelTransaksi t : riwayat) {
                        tableModel.addRow(new Object[]{
                            t.getId(),
                            t.getKodeTransaksi(),
                            t.getNamaUser(),
                            String.format("Rp %,.0f", t.getTotalHarga()),
                            String.format("Rp %,.0f", t.getTotalBayar()),
                            String.format("Rp %,.0f", t.getKembalian()),
                            t.getStatus(),
                            t.getCreatedAt() != null ? sdf.format(t.getCreatedAt()) : "-"
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void tampilkanDetail(ModelTransaksi trx) {
        List<ModelDetailTransaksi> detail = ctrl.getDetailTransaksi(trx.getId());

        String[] kolom = {"Produk", "Harga", "Jumlah", "Subtotal"};
        DefaultTableModel dm = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (ModelDetailTransaksi d : detail) {
            dm.addRow(new Object[]{
                d.getNamaProduk(),
                String.format("Rp %,.0f", d.getHargaJual()),
                d.getJumlah(),
                String.format("Rp %,.0f", d.getSubtotal())
            });
        }

        JTable detailTable = new JTable(dm);
        MetroTheme.styleTable(detailTable);

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(MetroTheme.BG_SURFACE);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel info = new JLabel(String.format(
            "<html><span style='color:#9999B8'>Kode:</span> <b>%s</b> &nbsp;|&nbsp;"
            + "<span style='color:#9999B8'>Kasir:</span> <b>%s</b> &nbsp;|&nbsp;"
            + "<span style='color:#9999B8'>Total:</span> <b>Rp %,.0f</b> &nbsp;|&nbsp;"
            + "<span style='color:#9999B8'>Kembalian:</span> <b>Rp %,.0f</b></html>",
            trx.getKodeTransaksi(), trx.getNamaUser(), trx.getTotalHarga(), trx.getKembalian()
        ));
        info.setFont(MetroTheme.FONT_BODY);
        info.setForeground(MetroTheme.TEXT_PRIMARY);
        panel.add(info, BorderLayout.NORTH);
        panel.add(MetroTheme.styledScrollPane(detailTable), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(600, 340));

        JOptionPane.showMessageDialog(this, panel,
            "Detail Transaksi " + trx.getKodeTransaksi(), JOptionPane.PLAIN_MESSAGE);
    }
}
