/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Transaksi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelTransaksi {
    private Integer id;
    private String  kodeTransaksi;
    private Integer idUser;
    private String  namaUser;
    private double  totalHarga;
    private double  totalBayar;
    private double  kembalian;
    private String  status;
    private String  catatan;
    private Date    createdAt;

    // Encapsulated List penampung detail item belanja
    private List<ModelDetailTransaksi> detailList = new ArrayList<>();

    public ModelTransaksi() { this.status = "selesai"; }

    // ---- Getters / Setters ----
    public Integer getId()                   { return id; }
    public void    setId(Integer id)         { this.id = id; }

    public String  getKodeTransaksi()               { return kodeTransaksi; }
    public void    setKodeTransaksi(String k)       { this.kodeTransaksi = k; }

    public Integer getIdUser()                   { return idUser; }
    public void    setIdUser(Integer u)          { this.idUser = u; }

    public String  getNamaUser()                    { return namaUser; }
    public void    setNamaUser(String n)            { this.namaUser = n; }

    public double  getTotalHarga()                  { return totalHarga; }
    public void    setTotalHarga(double t)          { this.totalHarga = t; }

    public double  getTotalBayar()                  { return totalBayar; }
    public void    setTotalBayar(double t)          { this.totalBayar = t; }

    public double  getKembalian()                   { return kembalian; }
    public void    setKembalian(double k)           { this.kembalian = k; }

    public String  getStatus()                  { return status; }
    public void    setStatus(String s)          { this.status = s; }

    public String  getCatatan()                 { return catatan; }
    public void    setCatatan(String c)         { this.catatan = c; }

    public Date    getCreatedAt()               { return createdAt; }
    public void    setCreatedAt(Date d)         { this.createdAt = d; }

    // Getter untuk menarik koleksi List Detail belanja secara utuh ke JDialog Riwayat
    public List<ModelDetailTransaksi> getDetailList() { return detailList; }
    
    // >>> PERBAIKAN SINKRONISASI: Menggunakan method addDetail bertipe parameter class ModelDetailTransaksi tunggal
    public void addDetail(ModelDetailTransaksi d) { 
        detailList.add(d); 
    }

    public void hitungTotalDanKembalian() {
        totalHarga = detailList.stream().mapToDouble(ModelDetailTransaksi::getSubtotal).sum();
        kembalian  = totalBayar - totalHarga;
    }
}