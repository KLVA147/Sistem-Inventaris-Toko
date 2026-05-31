/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Kategori;

public class ModelKategori {
    private Integer id;
    private String  nama;
    private String  deskripsi;

    public ModelKategori() {}

    public ModelKategori(String nama, String deskripsi) {
        this.nama      = nama;
        this.deskripsi = deskripsi;
    }

    public Integer getId()              { return id; }
    public void    setId(Integer id)    { this.id = id; }

    public String  getNama()              { return nama; }
    public void    setNama(String nama)   { this.nama = nama; }

    public String  getDeskripsi()               { return deskripsi; }
    public void    setDeskripsi(String d)       { this.deskripsi = d; }

    @Override
    public String toString() { return nama; }
}
