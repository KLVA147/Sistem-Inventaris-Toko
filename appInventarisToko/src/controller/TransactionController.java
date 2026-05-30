/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker; // >>> TAMBAHAN KODE: Manajemen thread asinkron agar aman dari race condition
import model.Produk.DAOProduk;
import model.Produk.ModelProduk;
import model.Transaksi.DAOTransaksi;
import model.Transaksi.ModelTransaksi;
import model.Transaksi.ModelDetailTransaksi;
import model.User.ModelUser;
import view.MainMenuView;
import view.transaksi.DetailTransaksiDialog; 
import view.transaksi.TransactionPanel;

public class TransactionController {
    private TransactionPanel view;
    private ModelUser userAktif;
    private MainMenuView mainFrame;
    
    private DAOProduk daoProduk;
    private DAOTransaksi daoTransaksi;
    private List<ModelDetailTransaksi> listKeranjang;
    private double totalBelanja = 0;

    public TransactionController(TransactionPanel view, ModelUser userAktif, MainMenuView mainFrame) {
        this.view = view;
        this.userAktif = userAktif;
        this.mainFrame = mainFrame;
        
        this.daoProduk = new DAOProduk();
        this.daoTransaksi = new DAOTransaksi();
        this.listKeranjang = new ArrayList<>();

        initListeners();
        refreshRiwayatTabel();
    }

    private void initListeners() {
        // Memicu proses pencarian terisolasi thread saat tombol Cari diklik
        view.btnCariBarang.addActionListener(e -> prosesPencarianSistemSearch());

        // Memicu proses pencarian terisolasi thread saat tombol ENTER ditekan
        view.txtKodeBarang.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    prosesPencarianSistemSearch();
                }
            }
        });

        view.btnTambahKeranjang.addActionListener(e -> tambahItemKeKeranjang());

        view.btnHapusItem.addActionListener(e -> {
            int row = view.tabelKeranjang.getSelectedRow();
            if (row >= 0) {
                totalBelanja -= listKeranjang.get(row).getSubtotal();
                listKeranjang.remove(row);
                view.modelKeranjang.removeRow(row);
                view.lblTotalHarga.setText("Total: Rp " + String.format("%,.0f", totalBelanja));
            }
        });

        view.btnCheckout.addActionListener(e -> {
            if (listKeranjang.isEmpty()) return;
            String input = JOptionPane.showInputDialog(mainFrame, "Total: Rp " + String.format("%,.0f", totalBelanja) + "\nUang Tunai (Rp):");
            if (input == null) return;

            try {
                double bayar = Double.parseDouble(input);
                if (bayar < totalBelanja) {
                    JOptionPane.showMessageDialog(mainFrame, "Uang tunai kurang!");
                    return;
                }

                ModelTransaksi trx = new ModelTransaksi();
                trx.setKodeTransaksi(daoTransaksi.generateKodeTransaksi());
                trx.setIdUser(userAktif.getId());
                trx.setTotalHarga(totalBelanja);
                trx.setTotalBayar(bayar);
                trx.setKembalian(bayar - totalBelanja);
                trx.setStatus("selesai");
                
                // >>> PERBAIKAN: Menggunakan method addDetail tunggal secara konsisten loop [cite: 234, 235]
                for (ModelDetailTransaksi d : listKeranjang) {
                    trx.addDetail(d);
                }

                // Operasi insert database dibungkus menggunakan SwingWorker asinkron 
                SwingWorker<Boolean, Void> checkoutWorker = new SwingWorker<>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        return daoTransaksi.insert(trx);
                    }

                    @Override
                    protected void done() {
                        try {
                            boolean sukses = get();
                            if (sukses) {
                                JOptionPane.showMessageDialog(mainFrame, "Checkout Transaksi Berhasil Diproses!");
                                listKeranjang.clear();
                                view.modelKeranjang.setRowCount(0);
                                totalBelanja = 0;
                                view.lblTotalHarga.setText("Total: Rp 0");
                                refreshRiwayatTabel();
                            } else {
                                JOptionPane.showMessageDialog(mainFrame, "Gagal memproses transaksi database!");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(mainFrame, "Thread Error: " + ex.getMessage());
                        }
                    }
                };
                checkoutWorker.execute();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Format pembayaran salah!");
            }
        });

        view.btnRefresh.addActionListener(e -> refreshRiwayatTabel());

        view.btnLihatDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = view.tabelRiwayat.getSelectedRow();
                if (row >= 0) {
                    int idTransaksi = Integer.parseInt(view.tabelRiwayat.getValueAt(row, 0).toString());
                    JOptionPane.showMessageDialog(mainFrame, "PERINGATAN: Membuka JDialog baru untuk Nota ID: #" + idTransaksi, "Guardrail System", JOptionPane.WARNING_MESSAGE);
                    DetailTransaksiDialog dialogDetail = new DetailTransaksiDialog(mainFrame, idTransaksi, daoTransaksi);
                    dialogDetail.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Silakan pilih baris riwayat terlebih dahulu!");
                }
            }
        });
    }

    // >>> PERBAIKAN SINKRONISASI THREAD: Menjamin instansiasi query dikerjakan mandiri tanpa memotong siklus List search
    private void prosesPencarianSistemSearch() {
        final String kode = view.txtKodeBarang.getText().trim();
        if (kode.isEmpty()) return;

        view.txtNamaBarang.setText("Mencari data...");

        SwingWorker<ModelProduk, Void> searchWorker = new SwingWorker<>() {
            @Override
            protected ModelProduk doInBackground() throws Exception {
                // Berjalan aman pada Worker Background Thread tersendiri [cite: 223]
                return daoProduk.getByKode(kode);
            }

            @Override
            protected void done() {
                try {
                    ModelProduk produk = get();
                    if (produk != null) {
                        view.txtNamaBarang.setText(produk.getNama());
                        view.txtHarga.setText(String.valueOf(produk.getHargaJual()));
                        view.txtJumlah.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Kode produk tidak terdaftar!");
                        view.txtNamaBarang.setText("");
                        view.txtHarga.setText("");
                    }
                } catch (Exception e) {
                    view.txtNamaBarang.setText("Error Pencarian");
                }
            }
        };
        searchWorker.execute();
    }

    private void tambahItemKeKeranjang() {
        String kode = view.txtKodeBarang.getText().trim();
        String qtyStr = view.txtJumlah.getText().trim();
        if (kode.isEmpty() || qtyStr.isEmpty()) return;

        try {
            int qty = Integer.parseInt(qtyStr);
            ModelProduk produk = daoProduk.getByKode(kode);
            if (produk == null || produk.getStok() < qty) {
                JOptionPane.showMessageDialog(mainFrame, "Stok tidak mencukupi!");
                return;
            }

            double subtotal = produk.getHargaJual() * qty;
            ModelDetailTransaksi detail = new ModelDetailTransaksi(produk.getId(), produk.getNama(), produk.getHargaJual(), qty);
            listKeranjang.add(detail);
            
            view.modelKeranjang.addRow(new Object[]{kode, produk.getNama(), "Rp " + produk.getHargaJual(), qty, "Rp " + subtotal});
            totalBelanja += subtotal;
            view.lblTotalHarga.setText("Total: Rp " + String.format("%,.0f", totalBelanja));
            
            view.txtKodeBarang.setText(""); view.txtNamaBarang.setText(""); view.txtHarga.setText(""); view.txtJumlah.setText("");
            view.txtKodeBarang.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Input tidak valid!");
        }
    }

    private void refreshRiwayatTabel() {
        view.modelRiwayat.setRowCount(0);
        for (ModelTransaksi t : daoTransaksi.getAll()) {
            view.modelRiwayat.addRow(new Object[]{t.getId(), t.getKodeTransaksi(), t.getNamaUser(), "Rp " + String.format("%,.0f", t.getTotalHarga()), "Rp " + String.format("%,.0f", t.getTotalBayar()), "Rp " + String.format("%,.0f", t.getKembalian()), t.getCreatedAt()});
        }
    }
}