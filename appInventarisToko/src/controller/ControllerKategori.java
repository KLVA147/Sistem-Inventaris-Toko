/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Kategori.*;
import javax.swing.*;
import java.util.List;

public class ControllerKategori {

    private final InterfaceDAOKategori dao = new DAOKategori();

    public List<ModelKategori> getAll()                    { return dao.getAll(); }
    public List<ModelKategori> search(String kw)           { return dao.search(kw); }
    public ModelKategori       getById(int id)             { return dao.getById(id); }

    public boolean tambah(String nama, String deskripsi) {
        try {
            if (nama.isBlank()) throw new Exception("Nama kategori tidak boleh kosong!");
            if (dao.namaExists(nama)) throw new Exception("Kategori '" + nama + "' sudah ada!");
            dao.insert(new ModelKategori(nama, deskripsi));
            JOptionPane.showMessageDialog(null, "Kategori berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean ubah(ModelKategori k, String nama, String deskripsi) {
        try {
            if (nama.isBlank()) throw new Exception("Nama kategori tidak boleh kosong!");
            k.setNama(nama);
            k.setDeskripsi(deskripsi);
            dao.update(k);
            JOptionPane.showMessageDialog(null, "Kategori berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void hapus(int id) {
        int ok = JOptionPane.showConfirmDialog(null,
            "Hapus kategori ini? Pastikan tidak ada produk yang menggunakannya.",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                dao.delete(id);
                JOptionPane.showMessageDialog(null, "Kategori berhasil dihapus.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
