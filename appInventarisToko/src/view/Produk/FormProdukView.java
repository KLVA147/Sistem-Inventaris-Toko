/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Produk;

import controller.ControllerKategori;
import controller.ControllerProduk;
import model.Kategori.ModelKategori;
import model.Produk.ModelProduk;
import model.User.ModelUser;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FormProdukView extends JFrame {

    private final ControllerProduk   ctrlProduk   = new ControllerProduk();
    private final ControllerKategori ctrlKategori = new ControllerKategori();

    private final JTextField     inputKode    = new JTextField(15);
    private final JTextField     inputNama    = new JTextField(25);
    private final JComboBox<ModelKategori> comboKat = new JComboBox<>();
    private final JTextField     inputHargaBeli = new JTextField(12);
    private final JTextField     inputHargaJual = new JTextField(12);
    private final JTextField     inputStok      = new JTextField(8);
    private final JTextField     inputStokMin   = new JTextField(8);
    private final JTextField     inputSatuan    = new JTextField(10);
    private final JTextArea      inputDesk      = new JTextArea(2, 30);

    public FormProdukView(ModelUser user, ModelProduk editProduk, ProdukView parent) {
        boolean isEdit = (editProduk != null);
        setTitle(isEdit ? "Edit Produk" : "Tambah Produk Baru");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<ModelKategori> kategoriList = ctrlKategori.getAll();
        for (ModelKategori k : kategoriList) comboKat.addItem(k);

        if (isEdit) {
            inputKode.setText(editProduk.getKodeProduk());
            inputKode.setEditable(false);
            inputNama.setText(editProduk.getNama());
            for (int i = 0; i < comboKat.getItemCount(); i++) {
                if (comboKat.getItemAt(i).getId().equals(editProduk.getIdKategori())) {
                    comboKat.setSelectedIndex(i); break;
                }
            }
            inputHargaBeli.setText(String.valueOf((long) editProduk.getHargaBeli()));
            inputHargaJual.setText(String.valueOf((long) editProduk.getHargaJual()));
            inputStok.setText(String.valueOf(editProduk.getStok()));
            inputStokMin.setText(String.valueOf(editProduk.getStokMinimum()));
            inputSatuan.setText(editProduk.getSatuan());
            inputDesk.setText(editProduk.getDeskripsi() != null ? editProduk.getDeskripsi() : "");
        }

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,4,5,4); gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Kode Produk:", "Nama Produk:", "Kategori:", "Harga Beli (Rp):",
                           "Harga Jual (Rp):", "Stok Awal:", "Stok Minimum:", "Satuan:", "Deskripsi:"};
        JComponent[] fields = {inputKode, inputNama, comboKat, inputHargaBeli,
                                inputHargaJual, inputStok, inputStokMin, inputSatuan, new JScrollPane(inputDesk)};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx=0; gbc.gridy=i; gbc.weightx=0.25; form.add(new JLabel(labels[i]), gbc);
            gbc.gridx=1; gbc.weightx=0.75; form.add(fields[i], gbc);
        }

        JButton btnSimpan = new JButton(isEdit ? "Simpan Perubahan" : "Tambah Produk");
        JButton btnBatal  = new JButton("Batal");
        JPanel btnPanel   = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnBatal); btnPanel.add(btnSimpan);

        add(new JScrollPane(form), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        btnBatal.addActionListener(e -> { dispose(); parent.refresh(); });

        btnSimpan.addActionListener(e -> {
            try {
                String kode   = inputKode.getText().trim().toUpperCase();
                String nama   = inputNama.getText().trim();
                ModelKategori kat = (ModelKategori) comboKat.getSelectedItem();
                double hb = Double.parseDouble(inputHargaBeli.getText().trim());
                double hj = Double.parseDouble(inputHargaJual.getText().trim());
                int stok    = Integer.parseInt(inputStok.getText().trim());
                int stokMin = Integer.parseInt(inputStokMin.getText().trim());
                String satuan = inputSatuan.getText().trim();
                String desk   = inputDesk.getText().trim();

                boolean ok;
                if (isEdit) {
                    editProduk.setNama(nama);
                    editProduk.setIdKategori(kat.getId());
                    editProduk.setHargaBeli(hb); editProduk.setHargaJual(hj);
                    editProduk.setStok(stok); editProduk.setStokMinimum(stokMin);
                    editProduk.setSatuan(satuan); editProduk.setDeskripsi(desk);
                    ok = ctrlProduk.ubah(editProduk);
                } else {
                    ok = ctrlProduk.tambah(kode, nama, kat.getId(), hb, hj, stok, stokMin, satuan, desk);
                }
                if (ok) { dispose(); parent.refresh(); }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Harga dan stok harus berupa angka!", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
