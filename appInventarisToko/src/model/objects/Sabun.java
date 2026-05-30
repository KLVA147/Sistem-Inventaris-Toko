/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author umair
 */
public class Sabun extends Barang{
    public Sabun(String id, String nama, int harga, int stok) {
        super(id, nama, harga, stok);
    }

    @Override
    public String getNamaTabel() {
        return "sabun";
    }
}
