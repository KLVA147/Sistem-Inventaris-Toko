/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repository;

import java.util.List;
import model.dao.BarangDAO;
import model.objects.Barang;
import model.objects.Sabun;
/**
 *
 * @author umair
 */
public class SabunRepository {
    private BarangDAO barangDao;

    public SabunRepository(BarangDAO barangDao) {
        this.barangDao = barangDao;
    }

    public List<Barang> ambilSemuaSabun() {
        return barangDao.getByTabel("sabun", "Sabun");
    }

    public void tambahSabun(Sabun sb) {
        barangDao.insert(sb);
    }

    public void ubahSabun(Sabun sb) {
        barangDao.update(sb);
    }

    public void hapusSabun(String id) {
        barangDao.delete(id, "sabun");
    }

    public void sesuaikanStok(String id, int stokBaru) {
        barangDao.updateStok(id, stokBaru, "sabun");
    }
}

