/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Produk.DAOProduk;
import model.Produk.ModelProduk;
import model.Transaksi.*;
import view.Transaksi.TransaksiView;
import javax.swing.*;
import java.util.List;

/**
 * OOP Pillar: Encapsulation — manages full sales transaction lifecycle.
 *
 * MULTITHREADING:
 *   - DB save runs in SwingWorker's background thread (doInBackground)
 *     to keep the Swing EDT responsive during the database round-trip.
 *   - UI updates happen in done() which is back on the EDT.
 *   - A loading dialog is shown on the EDT while the worker runs.
 */
public class ControllerTransaksi {

    private final DAOTransaksi  daoTransaksi = new DAOTransaksi();
    private final DAOProduk     daoProduk    = new DAOProduk();
    private TransaksiView       view;

    public ControllerTransaksi(TransaksiView v) { this.view = v; }

    public ModelProduk cariProduk(String kode) {
        if (kode == null || kode.isBlank()) return null;
        return daoProduk.getByKode(kode.trim().toUpperCase());
    }

    public void simpanTransaksi(ModelTransaksi trx) {
        if (trx.getDetailList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Keranjang belanja masih kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (trx.getTotalBayar() < trx.getTotalHarga()) {
            JOptionPane.showMessageDialog(null,
                String.format("Uang bayar (Rp %,.0f) kurang dari total (Rp %,.0f)!",
                    trx.getTotalBayar(), trx.getTotalHarga()),
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ---- MULTITHREADING: SwingWorker ----
        // doInBackground() runs on a background thread → non-blocking DB save
        // done()           runs back on the EDT → safe UI update
        JDialog loadingDialog = buatLoadingDialog();

        SwingWorker<ModelTransaksi, Void> worker = new SwingWorker<>() {
            @Override
            protected ModelTransaksi doInBackground() throws Exception {
                System.out.println("[Thread] Menyimpan transaksi di: " + Thread.currentThread().getName());
                return daoTransaksi.simpanTransaksi(trx);
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    ModelTransaksi saved = get();
                    JOptionPane.showMessageDialog(null,
                        String.format("Transaksi %s berhasil!\nKembalian: Rp %,.0f",
                            saved.getKodeTransaksi(), saved.getKembalian()),
                        "Transaksi Selesai", JOptionPane.INFORMATION_MESSAGE);
                    view.resetKeranjang();
                } catch (Exception e) {
                    String msg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                    JOptionPane.showMessageDialog(null,
                        "Transaksi gagal: " + msg, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        loadingDialog.setVisible(true);
        worker.execute();
        // Loading dialog will block visually but EDT remains responsive via SwingWorker
    }

    private JDialog buatLoadingDialog() {
        JDialog dialog = new JDialog((JFrame) null, "Memproses...", false);
        dialog.setSize(260, 80);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JLabel label = new JLabel("  ⏳ Menyimpan transaksi...", JLabel.CENTER);
        dialog.add(label);
        return dialog;
    }

    public List<ModelTransaksi> getRiwayat() {
        return daoTransaksi.getRiwayat(100);
    }

    public List<ModelDetailTransaksi> getDetailTransaksi(int idTransaksi) {
        return daoTransaksi.getDetailByTransaksi(idTransaksi);
    }
    
    public String generateKodeTransaksi(){
        return daoTransaksi.generateKodeTransaksi();
    }
    //public String generateKodeTransaksi() {
    //    return "TRX-" + SDF.format(new Date());
    //}
}
