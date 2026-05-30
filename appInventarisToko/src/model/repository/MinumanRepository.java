/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repository;

import java.util.List;
import model.dao.BarangDAO;
import model.objects.Barang;
import model.objects.Minuman;
/**
 *
 * @author umair
 */
public class MinumanRepository {
    private BarangDAO barangDao;

    public MinumanRepository(BarangDAO barangDao) {
        this.barangDao = barangDao;
    }

    public List<Barang> ambilSemuaMinuman() {
        return barangDao.getByTabel("minuman", "Minuman");
    }

    public void tambahMinuman(Minuman mnm) {
        barangDao.insert(mnm);
    }

    public void ubahMinuman(Minuman mnm) {
        barangDao.update(mnm);
    }

    public void hapusMinuman(String id) {
        barangDao.delete(id, "minuman");
    }

    public void sesuaikanStok(String id, int stokBaru) {
        barangDao.updateStok(id, stokBaru, "minuman");
    }
}
