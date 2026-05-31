/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Kategori;

import controller.ControllerKategori;
import model.Kategori.ModelKategori;
import model.User.ModelUser;
import view.Menu.MenuView;
import view.theme.MetroTheme;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class KategoriView extends JFrame {

    private final ControllerKategori controller = new ControllerKategori();
    private final ModelUser user;

    private final JTextField     inputCari   = new JTextField(18);
    private final JTextField     inputNama   = new JTextField(20);
    private final JTextArea      inputDesk   = new JTextArea(2, 20);
    private final JTable         table;
    private final DefaultTableModel tableModel;
    private Integer              selectedId  = null;

    public KategoriView(ModelUser user) {
        this.user = user;
        MetroTheme.install();

        setTitle("Kelola Kategori");
        setSize(740, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        // ── Header ─────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setBackground(MetroTheme.BG_SURFACE);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(MetroTheme.ACCENT);
        accentStrip.setPreferredSize(new Dimension(0, 4));
        header.add(accentStrip, BorderLayout.NORTH);
        header.add(MetroTheme.titleLabel("🗂  Kelola Kategori"), BorderLayout.WEST);

        JButton btnMenu = MetroTheme.ghostButton("← Menu");
        btnMenu.addActionListener(e -> { dispose(); new MenuView(user); });
        JPanel hRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        hRight.setOpaque(false);
        hRight.add(btnMenu);
        header.add(hRight, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        // ── Toolbar ────────────────────────────────────────────────────────
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        toolbar.setBackground(MetroTheme.BG_DARK);
        toolbar.setBorder(new EmptyBorder(4, 12, 4, 12));

        MetroTheme.styleTextField(inputCari);
        inputCari.setPreferredSize(new Dimension(200, 32));
        JButton btnCari  = MetroTheme.primaryButton("Cari");
        JButton btnReset = MetroTheme.ghostButton("Reset");
        toolbar.add(MetroTheme.bodyLabel("Cari:"));
        toolbar.add(inputCari);
        toolbar.add(btnCari);
        toolbar.add(btnReset);

        // ── Table ──────────────────────────────────────────────────────────
        String[] kolom = {"ID", "Nama Kategori", "Deskripsi"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        MetroTheme.styleTable(table);

        JPanel center = new JPanel(new BorderLayout(0, 4));
        center.setBackground(MetroTheme.BG_DARK);
        center.setBorder(new EmptyBorder(0, 12, 8, 12));
        center.add(toolbar, BorderLayout.NORTH);
        center.add(MetroTheme.styledScrollPane(table), BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        // ── Form panel (south) ─────────────────────────────────────────────
        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(MetroTheme.BG_SURFACE);
        south.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MetroTheme.BORDER));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(MetroTheme.BG_SURFACE);
        formPanel.setBorder(MetroTheme.cardBorder("Form Kategori"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); gbc.fill = GridBagConstraints.HORIZONTAL;

        MetroTheme.styleTextField(inputNama);
        MetroTheme.styleTextArea(inputDesk);
        inputDesk.setLineWrap(true);

        JLabel lblNama = MetroTheme.bodyLabel("Nama:");
        lblNama.setForeground(MetroTheme.TEXT_SECONDARY);
        JLabel lblDesk = MetroTheme.bodyLabel("Deskripsi:");
        lblDesk.setForeground(MetroTheme.TEXT_SECONDARY);

        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.2; formPanel.add(lblNama, gbc);
        gbc.gridx=1; gbc.weightx=0.8; formPanel.add(inputNama, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.weightx=0.2; formPanel.add(lblDesk, gbc);
        JScrollPane deskScroll = MetroTheme.styledScrollPane(inputDesk);
        deskScroll.setPreferredSize(new Dimension(0, 52));
        gbc.gridx=1; gbc.weightx=0.8; formPanel.add(deskScroll, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        btnPanel.setBackground(MetroTheme.BG_SURFACE);
        JButton btnTambah = MetroTheme.primaryButton("Tambah");
        JButton btnUbah   = MetroTheme.ghostButton("Ubah");
        JButton btnHapus  = MetroTheme.dangerButton("Hapus");
        JButton btnBatal  = MetroTheme.ghostButton("Batal");
        btnPanel.add(btnBatal); btnPanel.add(btnHapus);
        btnPanel.add(btnUbah); btnPanel.add(btnTambah);

        south.add(formPanel, BorderLayout.CENTER);
        south.add(btnPanel, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        // ── Events ─────────────────────────────────────────────────────────
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
