/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Kategori;

public class ModelKategori {
    private Integer id;
    private String  nama;
    private String  deskripsi;
    private String  kode; 

    public ModelKategori() {}

    public ModelKategori(String nama, String deskripsi, String kode) {
        this.nama      = nama;
        this.deskripsi = deskripsi;
        this.kode      = kode;
    }

    public ModelKategori(String nama, String kode) {
        this.nama      = nama;
        this.kode      = kode;
        this.deskripsi = "-"; // Default value jika deskripsi kosong
    }

    public Integer getId()              { return id; }
    public void    setId(Integer id)    { this.id = id; }

    public String  getNama()              { return nama; }
    public void    setNama(String nama)   { this.nama = nama; }

    public String  getDeskripsi()               { return deskripsi; }
    public void    setDeskripsi(String d)       { this.deskripsi = d; }

    public String  getKode()            { return kode; }
    public void    setKode(String kode) { this.kode = kode; }

    public String getKodePrefix() {
        if (kode != null && !kode.trim().isEmpty()) {
            String clean = kode.trim().toUpperCase();
            return clean.endsWith("-") ? clean : clean + "-";
        }
        return "BRG-";
    }

    // Override dari superclass Object
    @Override
    public String toString() { 
        return nama + " (" + getKodePrefix() + ")"; 
    } 
}