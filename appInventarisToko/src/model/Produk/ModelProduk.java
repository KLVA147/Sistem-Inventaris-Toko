/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Produk;

public class ModelProduk {
    private Integer id;
    private String  kodeProduk;
    private String  nama;
    private Integer idKategori;
    private String  namaKategori;   
    private double  hargaBeli;
    private double  hargaJual;
    private int     stok;
    private int     stokMinimum;
    private String  satuan;
    private String  deskripsi;
    private boolean aktif;

    public ModelProduk() { this.aktif = true; }

    // ---- Getters / Setters ----
    public Integer getId()                   { return id; }
    public void    setId(Integer id)         { this.id = id; }

    public String  getKodeProduk()                   { return kodeProduk; }
    public void    setKodeProduk(String kp)          { this.kodeProduk = kp; }

    public String  getNama()                    { return nama; }
    public void    setNama(String nama)         { this.nama = nama; }

    public Integer getIdKategori()               { return idKategori; }
    public void    setIdKategori(Integer id)     { this.idKategori = id; }

    public String  getNamaKategori()             { return namaKategori; }
    public void    setNamaKategori(String nk)    { this.namaKategori = nk; }

    public double  getHargaBeli()                   { return hargaBeli; }
    public void    setHargaBeli(double h)           { this.hargaBeli = h; }

    public double  getHargaJual()                   { return hargaJual; }
    public void    setHargaJual(double h)           { this.hargaJual = h; }

    public int     getStok()                { return stok; }
    public void    setStok(int s)           { this.stok = s; }

    public int     getStokMinimum()                 { return stokMinimum; }
    public void    setStokMinimum(int sm)           { this.stokMinimum = sm; }

    public String  getSatuan()                  { return satuan; }
    public void    setSatuan(String s)          { this.satuan = s; }

    public String  getDeskripsi()               { return deskripsi; }
    public void    setDeskripsi(String d)       { this.deskripsi = d; }

    public boolean isAktif()                    { return aktif; }
    public void    setAktif(boolean a)          { this.aktif = a; }

    /** Business logic: stok rendah jika di bawah stok minimum */
    public boolean isStokRendah() { return stok <= stokMinimum; }

    /** Hitung margin keuntungan */
    public double getMargin() {
        if (hargaBeli == 0) return 0;
        return ((hargaJual - hargaBeli) / hargaBeli) * 100;
    }

    @Override
    public String toString() { return kodeProduk + " - " + nama; }
}