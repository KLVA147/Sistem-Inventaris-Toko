/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Transaksi;

public class ModelDetailTransaksi {
    private Integer id;
    private Integer idTransaksi;
    private Integer idProduk;
    private String  namaProduk;
    private double  hargaJual;
    private int     jumlah;
    private double  subtotal;

    public ModelDetailTransaksi() {}

    public ModelDetailTransaksi(Integer idProduk, String namaProduk, double hargaJual, int jumlah) {
        this.idProduk   = idProduk;
        this.namaProduk = namaProduk;
        this.hargaJual  = hargaJual;
        this.jumlah     = jumlah;
        this.subtotal   = hargaJual * jumlah;
    }

    public Integer getId()                  { return id; }
    public void    setId(Integer id)        { this.id = id; }

    public Integer getIdTransaksi()              { return idTransaksi; }
    public void    setIdTransaksi(Integer t)     { this.idTransaksi = t; }

    public Integer getIdProduk()                { return idProduk; }
    public void    setIdProduk(Integer p)       { this.idProduk = p; }

    public String  getNamaProduk()              { return namaProduk; }
    public void    setNamaProduk(String n)      { this.namaProduk = n; }

    public double  getHargaJual()               { return hargaJual; }
    public void    setHargaJual(double h)       { this.hargaJual = h; }

    public int     getJumlah()              { return jumlah; }
    public void    setJumlah(int j)         { this.jumlah = j; recalcSubtotal(); }

    public double  getSubtotal()            { return subtotal; }
    public void    setSubtotal(double s)    { this.subtotal = s; }

    public void recalcSubtotal() { this.subtotal = hargaJual * jumlah; }
}
