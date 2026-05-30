/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.objects;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author umair
 */
public class Transaksi {
    private String idTransaksi;
    private String tanggalTransaksi;
    private int totalHarga;
    private List<DetailTransaksi> listDetail; // Menyimpan daftar item belanjaan

    public Transaksi(String idTransaksi, String tanggalTransaksi, int totalHarga) {
        this.idTransaksi = idTransaksi;
        this.tanggalTransaksi = tanggalTransaksi;
        this.totalHarga = totalHarga;
        this.listDetail = new ArrayList<>();
    }

    // Getter dan Setter
    public String getIdTransaksi() { return idTransaksi; }
    public String getTanggalTransaksi() { return tanggalTransaksi; }
    public int getTotalHarga() { return totalHarga; }
    public List<DetailTransaksi> getListDetail() { return listDetail; }
    public void setListDetail(List<DetailTransaksi> listDetail) { this.listDetail = listDetail; }
}
