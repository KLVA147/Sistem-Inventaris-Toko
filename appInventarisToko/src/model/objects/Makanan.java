/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author umair
 */
public class Makanan extends Barang{
    private String expiredDate;

    public Makanan(String id, String nama, int harga, int stok, String expiredDate) {
        super(id, nama, harga, stok);
        this.expiredDate = expiredDate;
    }

    @Override
    public String getNamaTabel() {
        return "makanan";
    }

    public String getExpiredDate() { return expiredDate; }
    public void setExpiredDate(String expiredDate) { this.expiredDate = expiredDate; }
}
