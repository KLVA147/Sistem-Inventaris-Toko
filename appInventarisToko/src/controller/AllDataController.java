/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.repository.FrozenFoodRepository;
import model.repository.MakananRepository;
import model.repository.MinumanRepository;
import model.repository.SabunRepository;
import model.objects.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import view.AllDataPanel;
import view.AllDataDialog;
import model.objects.*;
/**
 *
 * @author umair
 */
public class AllDataController {
    private AllDataPanel view;
    private MakananRepository makananRepo;
    private MinumanRepository minumanRepo; // Anggap sudah dibuat
    private FrozenFoodRepository frozenRepo;
    private SabunRepository sabunRepo;

    public AllDataController(AllDataPanel view, MakananRepository mknRepo, MinumanRepository mnmRepo,
                            FrozenFoodRepository fzRepo, SabunRepository sbRepo) {
        this.view = view;
        
        this.makananRepo = mknRepo;
        this.minumanRepo = mnmRepo;
        this.frozenRepo = fzRepo;
        this.sabunRepo = sbRepo;
        
        // Pasang action listener ke tombol-tombol kategori
        this.view.btnMakanan.addActionListener(e -> tampilkanDialogKategori("Makanan"));
        this.view.btnMinuman.addActionListener(e -> tampilkanDialogKategori("Minuman"));
        this.view.btnFrozenFood.addActionListener(e -> tampilkanDialogKategori("Frozen Food"));
        this.view.btnSabun.addActionListener(e -> tampilkanDialogKategori("Sabun & Kosmetik"));
    }

    private void tampilkanDialogKategori(String kategori) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        AllDataDialog dialog = new AllDataDialog(parentFrame, kategori);

        tampilkanDataKeTabel(dialog.tableModel, kategori);

        dialog.btnTambah.addActionListener(e -> aksiTambah(dialog, kategori));
        dialog.btnEdit.addActionListener(e -> aksiEdit(dialog, kategori));
        dialog.btnUpdateStok.addActionListener(e -> aksiUpdateStok(dialog, kategori));
        dialog.btnHapus.addActionListener(e -> aksiHapus(dialog, kategori));

        dialog.setVisible(true);
    }
    
    // ================= LOGIKA AKSI MANAGEMENT BARANG =================
    
    private void tampilkanDataKeTabel(DefaultTableModel model, String kategori) {
        model.setRowCount(0); 
        List<Barang> daftarBarang = null; 

        if (kategori.equals("Makanan")) {
            daftarBarang = makananRepo.ambilSemuaMakanan();
        } else if (kategori.equals("Minuman")) {
            daftarBarang = minumanRepo.ambilSemuaMinuman();
        } else if (kategori.equals("Frozen Food")) {
            daftarBarang = frozenRepo.ambilSemuaFrozenFood();
        } else if (kategori.equals("Sabun")) {
            daftarBarang = sabunRepo.ambilSemuaSabun();
        }

        if (daftarBarang != null) {
            for (Barang b : daftarBarang) {
                // Polimorfisme: b.getId() & b.getNama() dipanggil dari object induk Barang
                model.addRow(new Object[]{b.getId(), b.getNama(), b.getStok(), b.getHarga()});
            }
        }
    }

    private void aksiTambah(AllDataDialog dialog, String kategori) {
        String id = JOptionPane.showInputDialog(dialog, "Masukkan ID Barang:");
        if (id == null || id.trim().isEmpty()) return;
        String nama = JOptionPane.showInputDialog(dialog, "Masukkan Nama Barang:");
        if (nama == null || nama.trim().isEmpty()) return;

        try {
            int harga = Integer.parseInt(JOptionPane.showInputDialog(dialog, "Harga Satuan:"));
            int stok = Integer.parseInt(JOptionPane.showInputDialog(dialog, "Stok Awal:"));

            if (kategori.equals("Makanan")) {
                String expired = JOptionPane.showInputDialog(dialog, "Tanggal Expired (YYYY-MM-DD):");
                Makanan mkn = new Makanan(id.toUpperCase(), nama, harga, stok, expired);
                makananRepo.tambahMakanan(mkn);
            } else if (kategori.equals("Minuman")) {
                Minuman mnm = new Minuman(id.toUpperCase(), nama, harga, stok);
                minumanRepo.tambahMinuman(mnm);
            } else if (kategori.equals("Frozen Food")) {
                FrozenFood fz = new FrozenFood(id.toUpperCase(), nama, harga, stok);
                frozenRepo.tambahFrozenFood(fz);
            } else if (kategori.equals("Sabun")) {
                Sabun sb = new Sabun(id.toUpperCase(), nama, harga, stok);
                sabunRepo.tambahSabun(sb);
            }

            tampilkanDataKeTabel(dialog.tableModel, kategori);
            JOptionPane.showMessageDialog(dialog, "Data berhasil disimpan!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Format angka tidak valid!");
        }
    }

    private void aksiEdit(AllDataDialog dialog, String kategori) {
        int row = dialog.tableProduk.getSelectedRow();
        if (row == -1) return;

        String id = dialog.tableModel.getValueAt(row, 0).toString();
        String namaLama = dialog.tableModel.getValueAt(row, 1).toString();
        String hargaLama = dialog.tableModel.getValueAt(row, 3).toString();

        String namaBaru = JOptionPane.showInputDialog(dialog, "Ubah Nama Barang:", namaLama);
        if (namaBaru == null || namaBaru.trim().isEmpty()) return;

        try {
            int hargaBaru = Integer.parseInt(JOptionPane.showInputDialog(dialog, "Ubah Harga Satuan:", hargaLama));
            int stokLama = (int) dialog.tableModel.getValueAt(row, 2);

            if (kategori.equals("Makanan")) {
                Makanan mkn = new Makanan(id, namaBaru, hargaBaru, stokLama, null);
                makananRepo.ubahMakanan(mkn);
            } else if (kategori.equals("Minuman")) {
                Minuman mnm = new Minuman(id, namaBaru, hargaBaru, stokLama);
                minumanRepo.ubahMinuman(mnm);
            } else if (kategori.equals("Sabun")) {
                Sabun sb = new Sabun(id, namaBaru, hargaBaru, stokLama);
                sabunRepo.ubahSabun(sb);
            } else if (kategori.equals("Frozen Food")) {
                FrozenFood fz = new FrozenFood(id, namaBaru, hargaBaru, stokLama);
                frozenRepo.ubahFrozenFood(fz);
            }
            tampilkanDataKeTabel(dialog.tableModel, kategori);
            JOptionPane.showMessageDialog(dialog, "Data berhasil diperbarui!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Harga harus berupa angka!");
        }
    }

    private void aksiUpdateStok(AllDataDialog dialog, String kategori) {
        int row = dialog.tableProduk.getSelectedRow();
        if (row == -1) return;

        String id = dialog.tableModel.getValueAt(row, 0).toString();
        int stokLama = (int) dialog.tableModel.getValueAt(row, 2);

        String input = JOptionPane.showInputDialog(dialog, "Masukkan Jumlah Perubahan Stok (gunakan minus untuk mengurangi):");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int perubahan = Integer.parseInt(input);
            int stokBaru = stokLama + perubahan;

            if (stokBaru < 0) {
                JOptionPane.showMessageDialog(dialog, "Stok tidak boleh minus!");
                return;
            }

            if (kategori.equals("Makanan")) {
                makananRepo.sesuaikanStok(id, stokBaru);
            } else if (kategori.equals("Minuman")) {
                minumanRepo.sesuaikanStok(id, stokBaru);
            } else if (kategori.equals("Sabun")) {
                sabunRepo.sesuaikanStok(id, stokBaru);
            } else if (kategori.equals("Frozen Food")) {
                frozenRepo.sesuaikanStok(id, stokBaru);
            }
            tampilkanDataKeTabel(dialog.tableModel, kategori);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Input harus angka!");
        }
    }
    
    private void aksiHapus(AllDataDialog dialog, String kategori) {
        int row = dialog.tableProduk.getSelectedRow();
        if (row == -1) return;

        String id = dialog.tableModel.getValueAt(row, 0).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(dialog, "Hapus data ini?", "Hapus", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (kategori.equals("Makanan")) {
                makananRepo.hapusMakanan(id);
            } else if (kategori.equals("Minuman")) {
                minumanRepo.hapusMinuman(id);
            } else if (kategori.equals("Sabun")) {
                sabunRepo.hapusSabun(id);
            } else if (kategori.equals("Frozen Food")) {
                frozenRepo.hapusFrozenFood(id);
            }
            tampilkanDataKeTabel(dialog.tableModel, kategori);
        }
    }
}
