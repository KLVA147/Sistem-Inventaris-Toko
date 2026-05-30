/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author umair
 */

public class DetailTransaksi {
    private String namaBarang;
    private int hargaSatuan;
    private int jumlahBeli;
    private int subtotal;

    public DetailTransaksi(String namaBarang, int hargaSatuan, int jumlahBeli, int subtotal) {
        this.namaBarang = namaBarang;
        this.hargaSatuan = hargaSatuan;
        this.jumlahBeli = jumlahBeli;
        this.subtotal = subtotal;
    }

    public String getNamaBarang() { return namaBarang; }
    public int getHargaSatuan() { return hargaSatuan; }
    public int getJumlahBeli() { return jumlahBeli; }
    public int getSubtotal() { return subtotal; }
}
