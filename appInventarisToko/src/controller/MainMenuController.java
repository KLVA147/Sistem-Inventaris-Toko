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
import javax.swing.table.DefaultTableModel;

import model.User.ModelUser;
import model.Produk.DAOProduk;
import model.Produk.ModelProduk;
import model.Transaksi.DAOTransaksi;
import model.Transaksi.ModelTransaksi;
import model.Transaksi.ModelDetailTransaksi;
import view.MainMenuView;
import view.transaksi.TransactionPanel;

/**
 *
 * @author umair
 */
public class MainMenuController {
    private MainMenuView mainMenuView;
    private TransactionPanel transactionPanel;
    private ModelUser userAktif;
    
    // Data Access Object (DAO) untuk berhubungan dengan Database
    private DAOProduk daoProduk;
    private DAOTransaksi daoTransaksi;

    // List penampung sementara item di keranjang belanja sebelum checkout
    private List<ModelDetailTransaksi> listKeranjang;
    private double totalBelanja = 0;

    public MainMenuController(MainMenuView mainMenuView, ModelUser userAktif) {
        this.mainMenuView = mainMenuView;
        this.transactionPanel = mainMenuView.transaction;
        this.userAktif = userAktif;
        
        // Inisialisasi DAO
        this.daoProduk = new DAOProduk();
        this.daoTransaksi = new DAOTransaksi();
        this.listKeranjang = new ArrayList<>();

        // 1. Jalankan fungsi otorisasi role pada menu utama
        this.mainMenuView.setAksesMasingMasingRole(this.userAktif);

        // 2. Pasang Listener Komponen Transaksi
        initTransactionListeners();
        
        // Load data riwayat penjualan ke tabel saat pertama dibuka
        refreshRiwayatTabel();
    }

    // Menginisialisasi event-event kontroler pada panel transaksi kasir
    private void initTransactionListeners() {
        // Event ketik Kode Barang -> Tekan ENTER untuk cari barang otomatis di DB
        transactionPanel.txtKodeBarang.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cariBarangOtomatis();
                }
            }
        });

        // Tombol tambah item ke keranjang
        transactionPanel.btnTambahKeranjang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahKeKeranjangTabel();
            }
        });

        // Tombol hapus item terpilih di keranjang
        transactionPanel.btnHapusItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusItemDariKeranjang();
            }
        });

        // Tombol simpan transaksi total (Checkout)
        transactionPanel.btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesCheckoutTransaksi();
            }
        });

        // Tombol segarkan tabel nota riwayat transaksi
        transactionPanel.btnRefresh.addActionListener(e -> refreshRiwayatTabel());
        
        // Peringatan JDialog Baru: Sesuai instruksi Anda, ingatkan jika ingin membuka jendela baru
        transactionPanel.btnLihatDetail.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainMenuView, 
                "PERINGATAN: Fitur ini memerlukan JDialog/Jendela Detail Transaksi eksternal baru.", 
                "Pemberitahuan Sistem", JOptionPane.WARNING_MESSAGE);
            // Kode pemanggilan DetailTransaksiDialog dapat diletakkan di bawah sini jika disetujui.
        });
    }

    /**
     * Membaca input kode barang, mencocokkan ke tabel produk di DB, dan menampilkan datanya di Form
     */
    private void cariBarangOtomatis() {
        String kode = transactionPanel.txtKodeBarang.getText().trim();
        if (kode.isEmpty()) return;

        // Cari produk via DAO
        ModelProduk produk = daoProduk.getByKode(kode); // Pastikan metode ini tersedia di DAOProduk Anda
        if (produk != null) {
            transactionPanel.txtNamaBarang.setText(produk.getNama());
            transactionPanel.txtHarga.setText(String.valueOf(produk.getHargaJual()));
            transactionPanel.txtJumlah.requestFocus(); // Alihkan fokus kursor ke input Qty
        } else {
            JOptionPane.showMessageDialog(mainMenuView, "Kode barang tidak ditemukan di sistem!", "Data Kosong", JOptionPane.ERROR_MESSAGE);
            transactionPanel.txtNamaBarang.setText("");
            transactionPanel.txtHarga.setText("");
        }
    }

    /**
     * Memasukkan data form input kasir ke dalam daftar list & tabel GUI keranjang sementara
     */
    private void tambahKeKeranjangTabel() {
        String kode = transactionPanel.txtKodeBarang.getText().trim();
        String nama = transactionPanel.txtNamaBarang.getText().trim();
        String hargaStr = transactionPanel.txtHarga.getText().trim();
        String qtyStr = transactionPanel.txtJumlah.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(mainMenuView, "Silakan pilih barang dan tentukan Qty terlebih dahulu!", "Input Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            int qty = Integer.parseInt(qtyStr);

            if (qty <= 0) {
                JOptionPane.showMessageDialog(mainMenuView, "Jumlah kuantitas beli minimal 1!", "Input Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double subtotal = harga * qty;

            // Cek stok produk asli di database mencukupi atau tidak
            ModelProduk produk = daoProduk.getByKode(kode);
            if (produk.getStok() < qty) {
                JOptionPane.showMessageDialog(mainMenuView, "Stok tidak mencukupi! Stok tersisa: " + produk.getStok(), "Stok Kurang", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buat objek detail transaksi sementara
            ModelDetailTransaksi detail = new ModelDetailTransaksi();
            detail.setIdProduk(produk.getId());
            detail.setNamaProduk(nama);
            detail.setHargaJual(harga);
            detail.setJumlah(qty);
            detail.setSubtotal(subtotal);

            // Tambahkan ke penampung list & baris tabel view
            listKeranjang.add(detail);
            transactionPanel.modelKeranjang.addRow(new Object[]{kode, nama, "Rp " + harga, qty, "Rp " + subtotal});

            // Hitung ulang total belanja terkini
            hitungTotalBelanja();
            
            // Reset field input barang agar siap input kode baru
            transactionPanel.txtKodeBarang.setText("");
            transactionPanel.txtNamaBarang.setText("");
            transactionPanel.txtHarga.setText("");
            transactionPanel.txtJumlah.setText("");
            transactionPanel.txtKodeBarang.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainMenuView, "Format angka harga atau kuantitas tidak valid!", "Error Tipe Data", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus satu item terpilih dari tabel keranjang belanja kasir
    private void hapusItemDariKeranjang() {
        int barisTerpilih = transactionPanel.tabelKeranjang.getSelectedRow();
        if (barisTerpilih >= 0) {
            listKeranjang.remove(barisTerpilih);
            transactionPanel.modelKeranjang.removeRow(barisTerpilih);
            hitungTotalBelanja();
        } else {
            JOptionPane.showMessageDialog(mainMenuView, "Pilih baris item pada tabel keranjang yang ingin dihapus!", "Aksi Ditolak", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Akumulasi total biaya belanja dari list keranjang dan menampilkannya pada label teks
     */
    private void hitungTotalBelanja() {
        totalBelanja = 0;
        for (ModelDetailTransaksi item : listKeranjang) {
            totalBelanja += item.getSubtotal();
        }
        transactionPanel.lblTotalHarga.setText("Total: Rp " + totalBelanja);
    }

    /**
     * Menyimpan transaksi final secara penuh ke DB relasi `transaksi` & `detail_transaksi` via DAO
     */
    private void prosesCheckoutTransaksi() {
        if (listKeranjang.isEmpty()) {
            JOptionPane.showMessageDialog(mainMenuView, "Keranjang belanja masih kosong!", "Checkout Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Input Nominal pembayaran tunai dari pembeli
        String inputBayar = JOptionPane.showInputDialog(mainMenuView, "Total Belanja: Rp " + totalBelanja + "\nMasukkan Jumlah Uang Tunai Pembayaran (Rp):");
        if (inputBayar == null) return; // User membatalkan proses checkout

        try {
            double totalBayar = Double.parseDouble(inputBayar);
            if (totalBayar < totalBelanja) {
                JOptionPane.showMessageDialog(mainMenuView, "Uang pembayaran kurang dari total belanja!", "Transaksi Ditolak", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double kembalian = totalBayar - totalBelanja;

            // Generate Kode Transaksi Unik secara otomatis (contoh: TRX-1717202391)
            String kodeTrx = "TRX-" + (System.currentTimeMillis() / 1000);

            // Set data induk transaksi sesuai schema SQL
            ModelTransaksi transaksi = new ModelTransaksi();
            transaksi.setKodeTransaksi(kodeTrx);
            transaksi.setIdUser(userAktif.getId()); // ID Kasir yang sedang login
            transaksi.setTotalHarga(totalBelanja);
            transaksi.setTotalBayar(totalBayar);
            transaksi.setKembalian(kembalian);
            transaksi.setStatus("selesai");

            // >>> PERBAIKAN SINKRONISASI: Mengganti setListDetail dengan memanggil method addDetail() berulang dari model/Transaksi/ModelTransaksi.java
            for (ModelDetailTransaksi d : listKeranjang) {
                transaksi.addDetail(d);
            }
            // Panggil operasi transaksi database (DAO)
            boolean suksesSave = daoTransaksi.insert(transaksi); // Pastikan fungsi insert menangani batch detail_transaksi & mutasi log_stok

            if (suksesSave) {
                JOptionPane.showMessageDialog(mainMenuView, "Transaksi Sukses Disimpan!\nKembalian: Rp " + kembalian, "Sukses Nota", JOptionPane.INFORMATION_MESSAGE);
                
                // Reset keranjang belanja total setelah transaksi bersih
                listKeranjang.clear();
                DefaultTableModel model = (DefaultTableModel) transactionPanel.tabelKeranjang.getModel();
                model.setRowCount(0);
                hitungTotalBelanja();
                
                // Segarkan tabel riwayat penjualan
                refreshRiwayatTabel();
            } else {
                JOptionPane.showMessageDialog(mainMenuView, "Gagal memproses simpan database!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainMenuView, "Input jumlah uang harus berupa angka nominal valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Memperbarui daftar seluruh riwayat penjualan dari database ke tabel Tab Riwayat
    private void refreshRiwayatTabel() {
        transactionPanel.modelRiwayat.setRowCount(0); // Kosongkan row lama
        List<ModelTransaksi> listAll = daoTransaksi.getAll(); // Ambil dari database
        
        for (ModelTransaksi t : listAll) {
            transactionPanel.modelRiwayat.addRow(new Object[]{
                t.getId(),
                t.getKodeTransaksi(),
                t.getNamaUser(), // Relasi ambil dari inner join users nama_lengkap
                "Rp " + t.getTotalHarga(),
                "Rp " + t.getTotalBayar(),
                "Rp " + t.getKembalian(),
                t.getCreatedAt()
            });
        }
    }
}