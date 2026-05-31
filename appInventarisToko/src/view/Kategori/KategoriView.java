/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Kategori;

import controller.ControllerKategori;
import model.Kategori.ModelKategori;
import model.User.ModelUser;
import view.Menu.MenuView;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class KategoriView extends JFrame {

    private final ControllerKategori controller = new ControllerKategori();
    private final ModelUser user;

    private final JTextField     inputCari   = new JTextField(20);
    private final JTextField     inputNama   = new JTextField(20);
    private final JTextArea      inputDesk   = new JTextArea(2, 20);
    private final JTable         table;
    private final DefaultTableModel tableModel;
    private Integer              selectedId  = null;

    public KategoriView(ModelUser user) {
        this.user = user;
        setTitle("Kelola Kategori");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] kolom = {"ID", "Nama Kategori", "Deskripsi"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Layout
        JPanel cariPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cariPanel.add(new JLabel("Cari:"));
        cariPanel.add(inputCari);
        JButton btnCari  = new JButton("Cari");
        JButton btnReset = new JButton("Reset");
        cariPanel.add(btnCari); cariPanel.add(btnReset);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Kategori"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,6,4,6); gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0; gbc.gridy=0; formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx=1; gbc.weightx=1; formPanel.add(inputNama, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.weightx=0; formPanel.add(new JLabel("Deskripsi:"), gbc);
        gbc.gridx=1; gbc.weightx=1; formPanel.add(new JScrollPane(inputDesk), gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah   = new JButton("Ubah");
        JButton btnHapus  = new JButton("Hapus");
        JButton btnBatal  = new JButton("Batal");
        JButton btnMenu   = new JButton("← Menu");
        btnPanel.add(btnTambah); btnPanel.add(btnUbah); btnPanel.add(btnHapus);
        btnPanel.add(btnBatal); btnPanel.add(btnMenu);

        JPanel south = new JPanel(new BorderLayout());
        south.add(formPanel, BorderLayout.CENTER);
        south.add(btnPanel,  BorderLayout.SOUTH);

        add(cariPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        // Events
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;
                selectedId = (Integer) tableModel.getValueAt(row, 0);
                inputNama.setText(tableModel.getValueAt(row, 1).toString());
                inputDesk.setText(tableModel.getValueAt(row, 2) != null
                    ? tableModel.getValueAt(row, 2).toString() : "");
            }
        });

        btnCari.addActionListener(e -> loadData(inputCari.getText()));
        btnReset.addActionListener(e -> { inputCari.setText(""); loadData(""); });

        btnTambah.addActionListener(e -> {
            if (controller.tambah(inputNama.getText().trim(), inputDesk.getText().trim())) {
                loadData(""); resetForm();
            }
        });

        btnUbah.addActionListener(e -> {
            if (selectedId == null) { JOptionPane.showMessageDialog(null, "Pilih data dulu!"); return; }
            ModelKategori k = controller.getById(selectedId);
            if (k != null && controller.ubah(k, inputNama.getText().trim(), inputDesk.getText().trim())) {
                loadData(""); resetForm();
            }
        });

        btnHapus.addActionListener(e -> {
            if (selectedId == null) { JOptionPane.showMessageDialog(null, "Pilih data dulu!"); return; }
            controller.hapus(selectedId);
            loadData(""); resetForm();
        });

        btnBatal.addActionListener(e -> resetForm());
        btnMenu.addActionListener(e -> { dispose(); new MenuView(user); });

        loadData("");
        setVisible(true);
    }

    private void loadData(String keyword) {
        tableModel.setRowCount(0);
        List<ModelKategori> list = keyword.isBlank() ? controller.getAll() : controller.search(keyword);
        for (ModelKategori k : list) {
            tableModel.addRow(new Object[]{ k.getId(), k.getNama(), k.getDeskripsi() });
        }
    }

    private void resetForm() {
        selectedId = null;
        inputNama.setText(""); inputDesk.setText("");
        table.clearSelection();
    }
}
