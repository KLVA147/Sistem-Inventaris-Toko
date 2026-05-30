/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;                   
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import view.DetailTransaksiDialog;
import view.TransactionPanel;
import model.dao.BarangDAO;
import model.dao.TransaksiDAO;
import model.objects.Barang;
/**
 *
 * @author umair
 */
public class TransactionController {
    private TransactionPanel view;
    private BarangDAO barangDao;       
    private TransaksiDAO transaksiRepo;
    
    public TransactionController(TransactionPanel view, BarangDAO barangDao, TransaksiDAO transaksiDao) { 
        this.view = view;
        this.barangRepo = barangDao;
        this.transaksiRepo = transaksiDao;   // <-- DIUBAH: Diisi langsung dari parameter

        // Listener Tab 1: Kasir
        this.view.btnTambahKeranjang.addActionListener(e -> tambahKeKeranjang());
        this.view.btnHapusItem.addActionListener(e -> hapusItemDariKeranjang());
        this.view.btnCheckout.addActionListener(e -> prosesCheckout());

        // Listener Tab 2: Riwayat
        this.view.btnLihatDetail.addActionListener(e -> lihatDetailTransaksi());
        this.view.btnRefresh.addActionListener(e -> muatRiwayatKeTabel()); // <-- DIUBAH: Panggil data DB
        
        // Ambil data riwayat pertama kali saat aplikasi dibuka
        muatRiwayatKeTabel(); // <-- DIUBAH: Tambahkan pemanggilan awal
    }

    private void tambahKeKeranjang() {
        String nama = view.txtNamaBarang.getText().trim();
        String qtyStr = view.txtJumlah.getText().trim();

        try {
            int qtyInput = Integer.parseInt(qtyStr);
            Barang modelBarang = barangRepo.cariBerdasarkanNama(nama); 

            if (modelBarang == null) {
                JOptionPane.showMessageDialog(view, "Barang tidak ditemukan!");
                return;
            }

            if (modelBarang.getStok() < qtyInput) {
                JOptionPane.showMessageDialog(view, "Stok tidak cukup! Sisa: " + modelBarang.getStok());
                return;
            }

            view.txtHarga.setText(String.valueOf(modelBarang.getHarga()));
            int subtotal = modelBarang.getHarga() * qtyInput;
            
            view.modelKeranjang.addRow(new Object[]{modelBarang.getId(), nama, modelBarang.getHarga(), qtyInput, subtotal});
            hitungTotalHarga();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Jumlah harus berupa angka!");
        }
    }
    
    // <-- DIUBAH TOTAL: Logika Checkout sekarang menyimpan ke Database secara riil
    private void prosesCheckout() {
        if (view.modelKeranjang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Keranjang belanja masih kosong!");
            return;
        }

        String txId = "TX-" + (System.currentTimeMillis() % 100000);
        String tanggal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
        // Ambil string nominal total (Misal dari "Total: Rp 15000" diambil "15000")
        String totalStr = view.lblTotalHarga.getText().replaceAll("[^0-9]", "");
        int totalHarga = Integer.parseInt(totalStr);

        // 1. Instansiasi objek Transaksi utama
        Transaksi transaksi = new Transaksi(txId, tanggal, totalHarga);

        // 2. Loop baris keranjang untuk memotong stok DB sekaligus mengisi DetailTransaksi
        for (int i = 0; i < view.modelKeranjang.getRowCount(); i++) {
            String idBarang = view.modelKeranjang.getValueAt(i, 0).toString();
            String namaBarang = view.modelKeranjang.getValueAt(i, 1).toString();
            int harga = (int) view.modelKeranjang.getValueAt(i, 2);
            int qty = (int) view.modelKeranjang.getValueAt(i, 3);
            int subtotal = (int) view.modelKeranjang.getValueAt(i, 4);

            // Tambahkan item belanja ke list object transaksi
            DetailTransaksi detailItem = new DetailTransaksi(namaBarang, harga, qty, subtotal);
            transaksi.getListDetail().add(detailItem);

            // Potong stok produk di masing-masing tabel kategorinya
            Barang b = barangRepo.cariBerdasarkanNama(namaBarang);
            if (b != null) {
                int stokTerbaru = b.getStok() - qty;
                barangRepo.updateStok(idBarang, stokTerbaru, b.getNamaTabel());
            }
        }

        // 3. Simpan data transaksi & detail ke DB via TransaksiDAO
        boolean suksesSimpan = transaksiRepo.simpanTransaksi(transaksi);

        if (suksesSimpan) {
            JOptionPane.showMessageDialog(view, "Transaksi Berhasil Disimpan ke Database!");
            view.modelKeranjang.setRowCount(0);
            view.lblTotalHarga.setText("Total: Rp 0");
            muatRiwayatKeTabel(); // Segarkan tampilan tabel riwayat secara real-time
        } else {
            JOptionPane.showMessageDialog(view, "Gagal memproses transaksi ke database.");
        }
    }
    
    private void hitungTotalHarga() {
        int total = 0;
        for (int i = 0; i < view.modelKeranjang.getRowCount(); i++) {
            total += (int) view.modelKeranjang.getValueAt(i, 4);
        }
        view.lblTotalHarga.setText("Total: Rp " + total);
    }

    private void hapusItemDariKeranjang() {
        int row = view.tabelKeranjang.getSelectedRow();
        if (row != -1) {
            view.modelKeranjang.removeRow(row);
            hitungTotalHarga();
        }
    }

    private void lihatDetailTransaksi() {
        int row = view.tabelRiwayat.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris nota di tabel riwayat terlebih dahulu!");
            return;
        }
        String idTx = view.modelRiwayat.getValueAt(row, 0).toString();
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        
        // Oper transaksiRepo ke dialog agar dialog bisa membaca data detail asli dari DB
        DetailTransaksiDialog dialog = new DetailTransaksiDialog(parentFrame, idTx, transaksiRepo); // <-- DIUBAH: Kirim repo
        dialog.setVisible(true);
    }


    // <-- DIUBAH: Fungsi Baru untuk meload data transaksi asli dari database MySQL
    private void muatRiwayatKeTabel() {
        view.modelRiwayat.setRowCount(0); // Kosongkan tabel riwayat lama di UI
        List<Transaksi> daftarTx = transaksiRepo.ambilSemuaTransaksi();
        
        for (Transaksi t : daftarTx) {
            view.modelRiwayat.addRow(new Object[]{
                t.getIdTransaksi(), 
                t.getTanggalTransaksi(), 
                "Rp " + t.getTotalHarga()
            });
        }
    }
}