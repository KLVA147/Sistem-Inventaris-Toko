package view.data;

import javax.swing.*;
import java.awt.*;
import model.Kategori.ModelKategori;

/**
 *
 * @author umair
 */
public class AddProdukDialog extends JDialog {
    public JComboBox<ModelKategori> cbKategori = new JComboBox<>();
    
    // Komponen Input Kode Unik Angka
    public JLabel lblPrefix = new JLabel("BRG-");
    public JTextField txtKodeAngka = new JTextField(10);
    
    public JTextField txtNama = new JTextField(20);
    public JTextField txtHargaBeli = new JTextField(12);
    public JTextField txtHargaJual = new JTextField(12);
    public JTextField txtStok = new JTextField(8);
    public JTextField txtStokMin = new JTextField(8);
    public JTextField txtSatuan = new JTextField(10);
    
    public JButton btnSimpan = new JButton("Simpan Produk");
    public JButton btnBatal = new JButton("Batal");

    public AddProdukDialog(JFrame parent) {
        super(parent, "Form Tambah Produk Baru", true);
        setSize(450, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(46, 204, 113));
        JLabel lblTitle = new JLabel("MASUKKAN DATA BARANG BARU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitle.setForeground(Color.WHITE);
        panelHeader.add(lblTitle);
        add(panelHeader, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panelForm.add(new JLabel("Pilih Kategori:")); 
        panelForm.add(cbKategori);

        // Gabungkan Label Prefix dan TextField Kode Nomor ke dalam satu panel kecil
        panelForm.add(new JLabel("Kode Produk (Nomor):")); 
        JPanel panelKodeOtomatis = new JPanel(new BorderLayout(5, 0));
        lblPrefix.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrefix.setForeground(Color.BLUE);
        panelKodeOtomatis.add(lblPrefix, BorderLayout.WEST);
        panelKodeOtomatis.add(txtKodeAngka, BorderLayout.CENTER);
        panelForm.add(panelKodeOtomatis);

        panelForm.add(new JLabel("Nama Barang:")); panelForm.add(txtNama);
        panelForm.add(new JLabel("Harga Beli (Rp):")); panelForm.add(txtHargaBeli);
        panelForm.add(new JLabel("Harga Jual (Rp):")); panelForm.add(txtHargaJual);
        panelForm.add(new JLabel("Jumlah Stok Awal:")); panelForm.add(txtStok);
        panelForm.add(new JLabel("Batas Stok Minimum:")); panelForm.add(txtStokMin);
        panelForm.add(new JLabel("Satuan (Pcs/Pack/Botol):")); panelForm.add(txtSatuan);
        add(panelForm, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSimpan.setBackground(new Color(46, 204, 113)); btnSimpan.setForeground(Color.WHITE);
        btnBatal.setBackground(new Color(192, 41, 43)); btnBatal.setForeground(Color.WHITE);
        panelBottom.add(btnSimpan); panelBottom.add(btnBatal);
        add(panelBottom, BorderLayout.SOUTH);

        btnBatal.addActionListener(e -> dispose());
    }
}