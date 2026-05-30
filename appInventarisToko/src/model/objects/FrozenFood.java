/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

/**
 *
 * @author umair
 */
public class FrozenFood extends Barang{
    public FrozenFood(String id, String nama, int harga, int stok) {
        super(id, nama, harga, stok);
    }

    @Override
    public String getNamaTabel() {
        return "frozen_food";
    }
}
