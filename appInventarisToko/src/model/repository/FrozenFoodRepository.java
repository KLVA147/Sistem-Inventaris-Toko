/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.repository;

import java.util.List;
import model.dao.BarangDAO;
import model.objects.Barang;
import model.objects.FrozenFood;

/**
 *
 * @author umair
 */
public class FrozenFoodRepository {
    private BarangDAO barangDao;

    public FrozenFoodRepository(BarangDAO barangDao) {
        this.barangDao = barangDao;
    }

    public List<Barang> ambilSemuaFrozenFood() {
        return barangDao.getByTabel("frozen_food", "Frozen Food");
    }

    public void tambahFrozenFood(FrozenFood fz) {
        barangDao.insert(fz);
    }

    public void ubahFrozenFood(FrozenFood fz) {
        barangDao.update(fz);
    }

    public void hapusFrozenFood(String id) {
        barangDao.delete(id, "frozen_food");
    }

    public void sesuaikanStok(String id, int stokBaru) {
        barangDao.updateStok(id, stokBaru, "frozen_food");
    }
}
