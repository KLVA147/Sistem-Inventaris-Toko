/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import model.User.ModelUser;
import model.Produk.DAOProduk;
import model.Produk.ModelProduk;
import model.Produk.ModelTableProduk;
import model.Kategori.DAOKategori;
import model.Kategori.ModelKategori;
import view.data.AllDataPanel;
import view.MainMenuView;
import view.data.AddKategoriDialog;
import view.data.AddProdukDialog;

/**
 *
 * @author umair
 */
public class AllDataController {
    private AllDataPanel view;
    private ModelUser userAktif;
    private MainMenuView mainFrame;

    private DAOProduk daoProduk;
    private DAOKategori daoKategori;
    private ModelTableProduk modelTableProduk;

    public AllDataController(AllDataPanel view, ModelUser userAktif, MainMenuView mainFrame) {
        this.view = view;
        this.userAktif = userAktif;
        this.mainFrame = mainFrame;

        this.daoProduk = new DAOProduk();
        this.daoKategori = new DAOKategori();

        initListeners();
        refreshTabelProduk();
        refreshTabelKategori();
        aturHakAkses();
    }

    private void initListeners() {
        view.btnRefreshKategori.addActionListener(e -> refreshTabelKategori());

        view.btnTambahKategori.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame, "Membuka Form Tambah Kategori Baru.", "Pemberitahuan", JOptionPane.WARNING_MESSAGE);
            AddKategoriDialog dialog = new AddKategoriDialog(mainFrame);
            AddKategoriController ctrl = new AddKategoriController(dialog);
            dialog.setVisible(true);
            if (ctrl.isSukses()) refreshTabelKategori();
        });

        view.btnTambahProduk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ModelKategori> listKategori = daoKategori.getAll();
                if (listKategori.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Kategori kosong! Diwajibkan mengisi kategori terbaru terlebih dahulu.", "Aksi Ditolak", JOptionPane.ERROR_MESSAGE);
                    view.btnTambahKategori.doClick();
                    return;
                }

                JOptionPane.showMessageDialog(mainFrame, "Membuka Form Tambah Produk Baru.", "Pemberitahuan", JOptionPane.WARNING_MESSAGE);
                AddProdukDialog prodDialog = new AddProdukDialog(mainFrame);
                
                for (ModelKategori kat : listKategori) prodDialog.cbKategori.addItem(kat);

                if(prodDialog.cbKategori.getSelectedItem() != null) {
                    ModelKategori selected = (ModelKategori) prodDialog.cbKategori.getSelectedItem();
                    prodDialog.lblPrefix.setText(selected.getKodePrefix());
                }
                
                prodDialog.cbKategori.addActionListener(ev -> {
                    ModelKategori selected = (ModelKategori) prodDialog.cbKategori.getSelectedItem();
                    if(selected != null) {
                        prodDialog.lblPrefix.setText(selected.getKodePrefix());
                    }
                });

                prodDialog.btnSimpan.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        try {
                            String nomorKode = prodDialog.txtKodeAngka.getText().trim();
                            String nama = prodDialog.txtNama.getText().trim();
                            ModelKategori katTerpilih = (ModelKategori) prodDialog.cbKategori.getSelectedItem();
                            
                            String fullKodeProduk = katTerpilih.getKodePrefix() + nomorKode;

                            double hBeli = Double.parseDouble(prodDialog.txtHargaBeli.getText());
                            double hJual = Double.parseDouble(prodDialog.txtHargaJual.getText());
                            int stok = Integer.parseInt(prodDialog.txtStok.getText());
                            int stokMin = Integer.parseInt(prodDialog.txtStokMin.getText());
                            String satuan = prodDialog.txtSatuan.getText().trim();

                            if (nomorKode.isEmpty() || nama.isEmpty()) {
                                JOptionPane.showMessageDialog(prodDialog, "Field input tidak boleh kosong!");
                                return;
                            }

                            if(daoProduk.kodeExists(fullKodeProduk)) {
                                JOptionPane.showMessageDialog(prodDialog, "Kode produk " + fullKodeProduk + " sudah terdaftar!");
                                return;
                            }

                            ModelProduk p = new ModelProduk();
                            p.setKodeProduk(fullKodeProduk); 
                            p.setNama(nama);
                            p.setIdKategori(katTerpilih.getId());
                            p.setHargaBeli(hBeli); p.setHargaJual(hJual);
                            p.setStok(stok); p.setStokMinimum(stokMin);
                            p.setSatuan(satuan); p.setDeskripsi("Produk Baru");

                            daoProduk.insert(p);
                            JOptionPane.showMessageDialog(prodDialog, "Produk Bersiril Disimpan!");
                            prodDialog.dispose();
                            refreshTabelProduk();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(prodDialog, "Validasi input angka gagal!");
                        }
                    }
                });
                prodDialog.setVisible(true);
            }
        });

        view.btnHapusProduk.addActionListener(e -> {
            int row = view.tabelProduk.getSelectedRow();
            if (row >= 0) {
                ModelProduk p = modelTableProduk.getProdukAt(row);
                if (JOptionPane.showConfirmDialog(mainFrame, "Hapus " + p.getNama() + "?") == JOptionPane.YES_OPTION) {
                    daoProduk.delete(p.getId());
                    refreshTabelProduk();
                }
            }
        });
    }

    public void refreshTabelProduk() {
        modelTableProduk = new ModelTableProduk(daoProduk.getAll());
        view.tabelProduk.setModel(modelTableProduk);
    }

    public void refreshTabelKategori() {
        view.modelKategori.setRowCount(0);
        List<ModelKategori> list = daoKategori.getAll();
        for (ModelKategori k : list) {
            view.modelKategori.addRow(new Object[]{
                k.getId(), 
                k.getNama(), 
                k.getKodePrefix(), 
                k.getDeskripsi()
            });
        }
    }

    private void aturHakAkses() {
        if (userAktif.isKasir()) {
            view.btnTambahProduk.setEnabled(false);
            view.btnEditProduk.setEnabled(false);
            view.btnUpdateStok.setEnabled(false);
            view.btnHapusProduk.setEnabled(false);
            view.btnTambahKategori.setEnabled(false);
        }
    }
}