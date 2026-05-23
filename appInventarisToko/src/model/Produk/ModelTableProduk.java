/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Produk;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableProduk extends AbstractTableModel {

    List<ModelProduk> daftarProduk;
    String kolom[] = {"ID", "Nama", "Stok", "Harga"};

    public ModelTableProduk(List<ModelProduk> daftarProduk) {
        this.daftarProduk = daftarProduk;
    }

    @Override
    public int getRowCount() {
        return daftarProduk.size();
    }

    @Override
    public int getColumnCount() {
        return kolom.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return daftarProduk.get(rowIndex).getId();
            case 1: return daftarProduk.get(rowIndex).getNama();
            case 2: return daftarProduk.get(rowIndex).getStok();
            case 3: return daftarProduk.get(rowIndex).getHarga();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return kolom[columnIndex];
    }
}
