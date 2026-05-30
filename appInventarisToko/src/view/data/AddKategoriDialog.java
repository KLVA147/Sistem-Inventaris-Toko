/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author umair
 */
public class AddKategoriDialog extends JDialog {
    public JTextField txtNamaKategori = new JTextField(20);
    public JTextField txtKodePrefix = new JTextField(5); // Sesuai pedoman kolom kode baru database
    public JTextArea txtDeskripsi = new JTextArea(4, 20);
    public JButton btnSimpan = new JButton("Simpan Kategori");
    public JButton btnBatal = new JButton("Batal");

    public AddKategoriDialog(JFrame parent) {
        super(parent, "Tambah Kategori Baru", true);
        setSize(380, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(155, 89, 182)); 
        JLabel lblHeader = new JLabel("FORM TAMBAH KATEGORI");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 14));
        lblHeader.setForeground(Color.WHITE);
        panelHeader.add(lblHeader);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nama Kategori :"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelForm.add(txtNamaKategori, gbc);

        // Input Tambahan untuk Kode Prefix Baru (MKN, MNM, dsb.)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panelForm.add(new JLabel("Kode Prefix (Max 4):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelForm.add(txtKodePrefix, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelForm.add(new JLabel("Deskripsi :"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        JScrollPane scrollDeskripsi = new JScrollPane(txtDeskripsi);
        panelForm.add(scrollDeskripsi, gbc);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelTombol.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 20));
        
        btnSimpan.setBackground(new Color(46, 204, 113)); 
        btnSimpan.setForeground(Color.WHITE);
        btnBatal.setBackground(new Color(192, 41, 43)); 
        btnBatal.setForeground(Color.WHITE);

        panelTombol.add(btnSimpan);
        panelTombol.add(btnBatal);

        add(panelHeader, BorderLayout.NORTH);
        add(panelForm, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);

        btnBatal.addActionListener(e -> dispose());
    }

    public String getNamaKategori() { return txtNamaKategori.getText().trim(); }
    public String getKodePrefix() { return txtKodePrefix.getText().trim(); }
    public String getDeskripsiKategori() { return txtDeskripsi.getText().trim(); }

    public void addSimpanListener(ActionListener listener) { btnSimpan.addActionListener(listener); }
}