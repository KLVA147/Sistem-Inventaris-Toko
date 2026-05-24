/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.Produk;

import model.InterfaceDAO;
import java.util.List;

public interface InterfaceDAOProduk extends InterfaceDAO<ModelProduk> {
    List<ModelProduk> getByKategori(int idKategori);
    List<ModelProduk> getStokRendah();
    ModelProduk getByKode(String kodeProduk);
    void updateStok(int idProduk, int jumlah, String jenis);
    boolean kodeExists(String kodeProduk);
}
