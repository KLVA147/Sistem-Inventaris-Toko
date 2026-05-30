/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author IKHWANUL HASANI
 */
public abstract class Barang {
    private String id;
    private String nama;
    private int harga;
    private int stok;
    
    public Barang(String id, String nama, int harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }
    
    public abstract String getNamaTabel();
    
    public String getNama() {return nama;}
    public void setNama(String nama){this.nama = nama;}
    public String getId() {return id;}
    public void setId(String id){this.id = id;}
    public int getStok() {return stok;}
    public void setStok(int stok){this.stok = stok;}
    public int getHarga() {return harga;}
    public void setHarga(int harga){this.harga = harga;}
}
