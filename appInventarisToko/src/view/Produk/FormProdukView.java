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
import view.theme.MetroTheme;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FormProdukView extends JFrame {

    private final ControllerProduk   ctrlProduk   = new ControllerProduk();
    private final ControllerKategori ctrlKategori = new ControllerKategori();

    private final JTextField     inputKode      = new JTextField(15);
    private final JTextField     inputNama      = new JTextField(25);
    private final JComboBox<ModelKategori> comboKat = new JComboBox<>();
    private final JTextField     inputHargaBeli = new JTextField(12);
    private final JTextField     inputHargaJual = new JTextField(12);
    private final JTextField     inputStok      = new JTextField(8);
    private final JTextField     inputStokMin   = new JTextField(8);
    private final JTextField     inputSatuan    = new JTextField(10);
    private final JTextArea      inputDesk      = new JTextArea(3, 30);

    public FormProdukView(ModelUser user, ModelProduk editProduk, ProdukView parent) {
        boolean isEdit = (editProduk != null);
        MetroTheme.install();

        setTitle(isEdit ? "Edit Produk" : "Tambah Produk Baru");
        setSize(540, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MetroTheme.BG_SURFACE);
        header.setBorder(new EmptyBorder(14, 20, 14, 20));
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(MetroTheme.ACCENT);
        accentStrip.setPreferredSize(new Dimension(0, 4));
        header.add(accentStrip, BorderLayout.NORTH);
        header.add(MetroTheme.titleLabel(isEdit ? "✏  Edit Produk" : "+  Tambah Produk Baru"),
            BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

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

        for (JTextField f : new JTextField[]{inputKode, inputNama, inputHargaBeli,
                inputHargaJual, inputStok, inputStokMin, inputSatuan}) {
            MetroTheme.styleTextField(f);
        }
        MetroTheme.styleComboBox(comboKat);
        MetroTheme.styleTextArea(inputDesk);
        inputDesk.setLineWrap(true);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(MetroTheme.BG_DARK);
        form.setBorder(new EmptyBorder(16, 24, 8, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Kode Produk:", "Nama Produk:", "Kategori:", "Harga Beli (Rp):",
                           "Harga Jual (Rp):", "Stok Awal:", "Stok Minimum:", "Satuan:", "Deskripsi:"};
        JComponent[] fields = {inputKode, inputNama, comboKat, inputHargaBeli,
                                inputHargaJual, inputStok, inputStokMin, inputSatuan,
                                MetroTheme.styledScrollPane(inputDesk)};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = MetroTheme.bodyLabel(labels[i]);
            lbl.setForeground(MetroTheme.TEXT_SECONDARY);
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.28;
            form.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 0.72;
            form.add(fields[i], gbc);
        }

        JScrollPane formScroll = new JScrollPane(form);
        formScroll.setBorder(null);
        formScroll.setBackground(MetroTheme.BG_DARK);
        formScroll.getViewport().setBackground(MetroTheme.BG_DARK);
        root.add(formScroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(MetroTheme.BG_SURFACE);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MetroTheme.BORDER));

        JButton btnBatal  = MetroTheme.ghostButton("Batal");
        JButton btnSimpan = MetroTheme.primaryButton(isEdit ? "💾  Simpan Perubahan" : "+  Tambah Produk");

        btnPanel.add(btnBatal);
        btnPanel.add(btnSimpan);
        root.add(btnPanel, BorderLayout.SOUTH);

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
                JOptionPane.showMessageDialog(null,
                    "Harga dan stok harus berupa angka!", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
