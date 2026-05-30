/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Kategori.DAOKategori;
import model.Kategori.ModelKategori;
import view.data.AddKategoriDialog;

/**
 *
 * @author umair
 */
public class AddKategoriController {
    private AddKategoriDialog dialog;
    private DAOKategori daoKategori;
    private boolean sukses = false;

    public AddKategoriController(AddKategoriDialog dialog) {
        this.dialog = dialog;
        this.daoKategori = new DAOKategori();
        this.dialog.addSimpanListener(new SimpanKategoriListener());
    }

    private class SimpanKategoriListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nama = dialog.getNamaKategori();
            String prefix = dialog.getKodePrefix();
            String deskripsi = dialog.getDeskripsiKategori();

            // SINKRONISASI CONSTRAINT: Kolom prefix wajib terisi penuh karena berstatus NOT NULL di database
            if (nama.isEmpty() || prefix.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Nama Kategori dan Kode Prefix wajib diisi (NOT NULL)!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (prefix.length() > 4) {
                JOptionPane.showMessageDialog(dialog, "Kode Prefix maksimal sepanjang 4 karakter!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (daoKategori.namaExists(nama)) {
                JOptionPane.showMessageDialog(dialog, "Kategori dengan nama '" + nama + "' sudah terdaftar!", "Duplikasi Data", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ModelKategori kategoriBaru;
                
                // >>> SYARAT PROYEK: Implementasi pemanggilan Overload Constructor di tingkatan Controller
                if (deskripsi.isEmpty() || deskripsi.trim().equals("-")) {
                    // Memicu Overload Constructor ke-3 (Deskripsi Opsional)
                    kategoriBaru = new ModelKategori(nama, prefix);
                } else {
                    // Memicu Overload Constructor ke-2 (Lengkap dengan data deskripsi)
                    kategoriBaru = new ModelKategori(nama, deskripsi, prefix);
                }
                
                daoKategori.insert(kategoriBaru);
                
                JOptionPane.showMessageDialog(dialog, "Kategori berhasil disimpan ke database!");
                sukses = true;
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Database Error: " + ex.getMessage());
            }
        }
    }

    public boolean isSukses() { return sukses; }
}