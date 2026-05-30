/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repository;

import java.util.List;
import model.dao.BarangDAO;
import model.objects.Barang;
import model.objects.Makanan;
/**
 *
 * @author umair
 */
public class MakananRepository {
    private BarangDAO barangDao;

    public MakananRepository(BarangDAO barangDao) {
        this.barangDao = barangDao;
    }

    public List<Barang> ambilSemuaMakanan() {
        return barangDao.getByTabel("makanan", "Makanan");
    }

    public void tambahMakanan(Makanan mkn) {
        barangDao.insert(mkn);
    }

    public void ubahMakanan(Makanan mkn) {
        barangDao.update(mkn);
    }

    public void hapusMakanan(String id) {
        barangDao.delete(id, "makanan");
    }

    public void sesuaikanStok(String id, int stokBaru) {
        barangDao.updateStok(id, stokBaru, "makanan");
    }
}
