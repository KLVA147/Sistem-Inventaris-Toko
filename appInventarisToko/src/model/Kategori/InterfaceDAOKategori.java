/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.Kategori;

import model.InterfaceDAO;

public interface InterfaceDAOKategori extends InterfaceDAO<ModelKategori> {
    boolean namaExists(String nama);
}
