/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Produk;

/**
 *
 * @author IKHWANUL HASANI
 */
public class ModelProduk {
    private static String id;
    private static String nama;
    private static int harga;
    private static int stok;
    
    public ModelProduk(String nama, int harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }
    
    public String getNama() {return nama;}
    public void setNama(String nama){this.nama = nama;}
    public String getId() {return id;}
    public void setId(String id){this.id = id;}
    public int getStok() {return stok;}
    public void setStok(int nama){this.stok = stok;}
    public int getHarga() {return harga;}
    public void setHarga(int harga){this.harga = harga;}
}
