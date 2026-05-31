/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Produk.*;
import javax.swing.*;
import java.util.List;

public class ControllerProduk {

    private final InterfaceDAOProduk dao = new DAOProduk();

    public List<ModelProduk> getAll()                  { return dao.getAll(); }
    public List<ModelProduk> search(String kw)         { return dao.search(kw); }
    public List<ModelProduk> getStokRendah()           { return dao.getStokRendah(); }
    public List<ModelProduk> getByKategori(int idKat)  { return dao.getByKategori(idKat); }
    public ModelProduk       getByKode(String kode)    { return dao.getByKode(kode); }
    public ModelProduk       getById(int id)           { return dao.getById(id); }

    public boolean tambah(String kode, String nama, int idKat, double hargaBeli,
                          double hargaJual, int stok, int stokMin, String satuan, String deskripsi) {
        try {
            if (kode.isBlank() || nama.isBlank()) throw new Exception("Kode dan nama produk wajib diisi!");
            if (dao.kodeExists(kode)) throw new Exception("Kode produk '" + kode + "' sudah digunakan!");
            if (hargaJual < hargaBeli) throw new Exception("Harga jual tidak boleh lebih kecil dari harga beli!");
            if (stok < 0 || stokMin < 0) throw new Exception("Stok tidak boleh negatif!");

            ModelProduk p = new ModelProduk();
            p.setKodeProduk(kode); p.setNama(nama); p.setIdKategori(idKat);
            p.setHargaBeli(hargaBeli); p.setHargaJual(hargaJual);
            p.setStok(stok); p.setStokMinimum(stokMin);
            p.setSatuan(satuan); p.setDeskripsi(deskripsi);
            dao.insert(p);

            JOptionPane.showMessageDialog(null, "Produk berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean ubah(ModelProduk p) {
        try {
            if (p.getNama().isBlank()) throw new Exception("Nama produk tidak boleh kosong!");
            if (p.getHargaJual() < p.getHargaBeli()) throw new Exception("Harga jual tidak boleh lebih kecil dari harga beli!");
            dao.update(p);
            JOptionPane.showMessageDialog(null, "Produk berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void hapus(int id) {
        int ok = JOptionPane.showConfirmDialog(null,
            "Nonaktifkan produk ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            dao.delete(id);
            JOptionPane.showMessageDialog(null, "Produk berhasil dinonaktifkan.");
        }
    }

    public boolean tambahStok(int idProduk, int jumlah, int idUser, String keterangan) {
        try {
            if (jumlah <= 0) throw new Exception("Jumlah stok masuk harus lebih dari 0!");
            dao.updateStok(idProduk, jumlah, "masuk");
            // Log stok masuk
            logStokMasuk(idProduk, idUser, jumlah, keterangan);
            JOptionPane.showMessageDialog(null, "Stok berhasil ditambah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void logStokMasuk(int idProduk, int idUser, int jumlah, String keterangan) {
        String sql = "INSERT INTO log_stok (id_produk,id_user,jenis,jumlah,stok_sebelum,stok_sesudah,keterangan) " +
                     "SELECT id, ?, 'masuk', ?, stok-?, stok, ? FROM produk WHERE id=?";
        try (var ps = model.Connector.Connect().prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ps.setInt(2, jumlah);
            ps.setInt(3, jumlah);
            ps.setString(4, keterangan);
            ps.setInt(5, idProduk);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            System.err.println("[ControllerProduk.logStokMasuk] " + e.getMessage());
        }
    }
}

