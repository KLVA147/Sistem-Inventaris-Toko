/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Produk;

import controller.ControllerKategori;
import controller.ControllerProduk;
import model.Kategori.ModelKategori;
import model.Produk.ModelProduk;
import model.Produk.ModelTableProduk;
import model.User.ModelUser;
import view.Menu.MenuView;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ProdukView extends JFrame {

    private final ControllerProduk   ctrlProduk   = new ControllerProduk();
    private final ControllerKategori ctrlKategori = new ControllerKategori();
    private final ModelUser          user;
    private final JTable             table;
    private ModelTableProduk         tableModel;

    private final JTextField  inputCari    = new JTextField(20);
    private final JComboBox<ModelKategori> comboCari = new JComboBox<>();

    public ProdukView(ModelUser user) {
        this.user = user;
        setTitle("Kelola Produk - " + user.getNamaLengkap());
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

<<<<<<< Updated upstream
        // Table
=======
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MetroTheme.BG_SURFACE);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(MetroTheme.ACCENT);
        accentStrip.setPreferredSize(new Dimension(0, 4));
        header.add(accentStrip, BorderLayout.NORTH);

        JLabel titleLbl = MetroTheme.titleLabel("📦  Kelola Produk");
        titleLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        header.add(titleLbl, BorderLayout.WEST);

        JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionBtns.setOpaque(false);
        JButton btnTambah     = MetroTheme.primaryButton("+ Tambah");
        JButton btnEdit       = MetroTheme.ghostButton("✏ Edit");
        JButton btnHapus      = MetroTheme.dangerButton("🗑 Hapus");
        JButton btnTambahStok = MetroTheme.ghostButton("📥 Stok Masuk");
        JButton btnMenu       = MetroTheme.ghostButton("← Menu");
        actionBtns.add(btnMenu);
        actionBtns.add(btnTambah);
        actionBtns.add(btnEdit);
        actionBtns.add(btnHapus);
        actionBtns.add(btnTambahStok);
        header.add(actionBtns, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        toolbar.setBackground(MetroTheme.BG_DARK);
        toolbar.setBorder(new EmptyBorder(0, 12, 0, 12));

        MetroTheme.styleTextField(inputCari);
        inputCari.setPreferredSize(new Dimension(200, 32));
        MetroTheme.styleComboBox(comboCari);

        ModelKategori semua = new ModelKategori();
        semua.setNama("— Semua Kategori —");
        semua.setId(0);
        comboCari.addItem(semua);
        for (ModelKategori k : ctrlKategori.getAll()) comboCari.addItem(k);

        JButton btnCari   = MetroTheme.primaryButton("Cari");
        JButton btnReset  = MetroTheme.ghostButton("Reset");
        JButton btnRendah = MetroTheme.ghostButton("⚠ Stok Rendah");

        toolbar.add(MetroTheme.bodyLabel("Cari:"));
        toolbar.add(inputCari);
        toolbar.add(MetroTheme.bodyLabel("Kategori:"));
        toolbar.add(comboCari);
        toolbar.add(btnCari);
        toolbar.add(btnReset);
        toolbar.add(btnRendah);

        root.add(toolbar, BorderLayout.CENTER);

>>>>>>> Stashed changes
        tableModel = new ModelTableProduk(ctrlProduk.getAll());
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Color rows with low stock RED
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                ModelProduk p = tableModel.getProdukAt(row);
                if (!sel) c.setBackground(p.isStokRendah() ? new Color(255, 220, 220) : Color.WHITE);
                return c;
            }
        });

        // Search panel
        JPanel cariPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cariPanel.add(new JLabel("Cari:"));
        cariPanel.add(inputCari);
        cariPanel.add(new JLabel("Kategori:"));
        ModelKategori semua = new ModelKategori(); semua.setNama("-- Semua --"); semua.setId(0);
        comboCari.addItem(semua);
        for (ModelKategori k : ctrlKategori.getAll()) comboCari.addItem(k);
        cariPanel.add(comboCari);
        JButton btnCari  = new JButton("Cari");
        JButton btnReset = new JButton("Reset");
        JButton btnRendah = new JButton("⚠ Stok Rendah");
        cariPanel.add(btnCari); cariPanel.add(btnReset); cariPanel.add(btnRendah);

        // Action buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah     = new JButton("+ Tambah Produk");
        JButton btnEdit       = new JButton("✏ Edit");
        JButton btnHapus      = new JButton("🗑 Hapus");
        JButton btnTambahStok = new JButton("📥 Tambah Stok");
        JButton btnMenu       = new JButton("← Menu");
        btnPanel.add(btnTambah); btnPanel.add(btnEdit); btnPanel.add(btnHapus);
        btnPanel.add(btnTambahStok); btnPanel.add(btnMenu);

        JLabel legendLabel = new JLabel("  ⚠ Baris merah = stok rendah (di bawah minimum)");
        legendLabel.setForeground(new Color(180,0,0));
        legendLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(btnPanel,   BorderLayout.CENTER);
        southPanel.add(legendLabel, BorderLayout.SOUTH);

        add(cariPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Events
        btnCari.addActionListener(e -> {
            String kw = inputCari.getText().trim();
            ModelKategori kat = (ModelKategori) comboCari.getSelectedItem();
            List<ModelProduk> list;
            if (kat != null && kat.getId() != 0) list = ctrlProduk.getByKategori(kat.getId());
            else list = kw.isEmpty() ? ctrlProduk.getAll() : ctrlProduk.search(kw);
            tableModel.setData(list);
        });

        btnReset.addActionListener(e -> {
            inputCari.setText(""); comboCari.setSelectedIndex(0);
            tableModel.setData(ctrlProduk.getAll());
        });

        btnRendah.addActionListener(e -> tableModel.setData(ctrlProduk.getStokRendah()));

        btnTambah.addActionListener(e -> {
            if (!user.isAdmin() && !user.isGudang()) {
                JOptionPane.showMessageDialog(null, "Akses ditolak."); return;
            }
            new FormProdukView(user, null, this);
            setVisible(false);
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Pilih produk dulu!"); return; }
            ModelProduk p = tableModel.getProdukAt(row);
            new FormProdukView(user, p, this);
            setVisible(false);
        });

        btnHapus.addActionListener(e -> {
            if (!user.isAdmin()) { JOptionPane.showMessageDialog(null, "Hanya admin yang bisa menghapus."); return; }
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Pilih produk dulu!"); return; }
            ctrlProduk.hapus(tableModel.getProdukAt(row).getId());
            tableModel.setData(ctrlProduk.getAll());
        });

        btnTambahStok.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Pilih produk dulu!"); return; }
            ModelProduk p = tableModel.getProdukAt(row);
            String jumlahStr = JOptionPane.showInputDialog(null,
                "Masukkan jumlah stok yang masuk untuk:\n" + p.getNama(),
                "Tambah Stok", JOptionPane.PLAIN_MESSAGE);
            if (jumlahStr == null) return;
            try {
                int jumlah = Integer.parseInt(jumlahStr.trim());
                String ket = JOptionPane.showInputDialog("Keterangan (misal: Restock supplier):");
                ctrlProduk.tambahStok(p.getId(), jumlah, user.getId(), ket != null ? ket : "");
                tableModel.setData(ctrlProduk.getAll());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Jumlah harus berupa angka!");
            }
        });

        btnMenu.addActionListener(e -> { dispose(); new MenuView(user); });

        setVisible(true);
    }

    /** Called by FormProdukView after save to refresh the table */
    public void refresh() {
        tableModel.setData(ctrlProduk.getAll());
        setVisible(true);
    }
}
