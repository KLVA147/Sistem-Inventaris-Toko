/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author umair
 */
public class Minuman extends Barang{
    public Minuman(String id, String nama, int harga, int stok) {
        super(id, nama, harga, stok);
    }

    @Override
    public String getNamaTabel() {
        return "minuman";
    }
}
