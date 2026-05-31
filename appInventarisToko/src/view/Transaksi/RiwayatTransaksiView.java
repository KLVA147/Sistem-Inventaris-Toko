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
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
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

        setTitle("Riwayat Transaksi");
        setSize(820, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] kolom = {"ID", "Kode Transaksi", "Kasir", "Total", "Bayar", "Kembalian", "Status", "Waktu"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnDetail = new JButton("📋 Lihat Detail");
        JButton btnRefresh = new JButton("🔄 Refresh");
        JButton btnMenu   = new JButton("← Kembali");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnDetail); btnPanel.add(btnRefresh); btnPanel.add(btnMenu);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnMenu.addActionListener(e -> { dispose(); new MenuView(user); });

        btnDetail.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Pilih transaksi dulu!"); return; }
            ModelTransaksi trx = riwayat.get(row);
            tampilkanDetail(trx);
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
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.add(new JLabel(String.format(
            "<html><b>Kode:</b> %s &nbsp; <b>Kasir:</b> %s &nbsp; <b>Total:</b> Rp %,.0f &nbsp; <b>Kembalian:</b> Rp %,.0f</html>",
            trx.getKodeTransaksi(), trx.getNamaUser(), trx.getTotalHarga(), trx.getKembalian()
        )), BorderLayout.NORTH);
        panel.add(new JScrollPane(detailTable), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(560, 320));

        JOptionPane.showMessageDialog(this, panel, "Detail Transaksi " + trx.getKodeTransaksi(),
            JOptionPane.PLAIN_MESSAGE);
    }
}
