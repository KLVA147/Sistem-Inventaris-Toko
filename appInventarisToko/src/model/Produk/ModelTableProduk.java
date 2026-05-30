/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Produk;

import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ModelTableProduk extends AbstractTableModel {

    private List<ModelProduk> daftarProduk;
    private final String[] KOLOM = {"ID", "Kode", "Nama Produk", "Kategori", "Harga Jual", "Stok", "Satuan", "Status Stok"};

    public ModelTableProduk(List<ModelProduk> daftarProduk) {
        this.daftarProduk = daftarProduk;
    }

    public void setData(List<ModelProduk> data) {
        this.daftarProduk = data;
        fireTableDataChanged();
    }

    @Override public int getRowCount()    { return daftarProduk.size(); }
    @Override public int getColumnCount() { return KOLOM.length; }
    @Override public String getColumnName(int col) { return KOLOM[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        ModelProduk p = daftarProduk.get(row);
        switch (col) {
            case 0: return p.getId();
            case 1: return p.getKodeProduk();
            case 2: return p.getNama();
            case 3: return p.getNamaKategori();
            case 4: return String.format("Rp %,.0f", p.getHargaJual());
            case 5: return p.getStok();
            case 6: return p.getSatuan();
            case 7: return p.isStokRendah() ? "⚠ RENDAH" : "OK";
            default: return null;
        }
    }

    public ModelProduk getProdukAt(int row) {
        return daftarProduk.get(row);
    }
}