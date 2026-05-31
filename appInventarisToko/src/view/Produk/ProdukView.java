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
import view.theme.MetroTheme;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class ProdukView extends JFrame {

    private final ControllerProduk   ctrlProduk   = new ControllerProduk();
    private final ControllerKategori ctrlKategori = new ControllerKategori();
    private final ModelUser          user;
    private final JTable             table;
    private ModelTableProduk         tableModel;

    private final JTextField  inputCari  = new JTextField(18);
    private final JComboBox<ModelKategori> comboCari = new JComboBox<>();

    public ProdukView(ModelUser user) {
        this.user = user;
        MetroTheme.install();

        setTitle("Kelola Produk — " + user.getNamaLengkap());
        setSize(960, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        tableModel = new ModelTableProduk(ctrlProduk.getAll());
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        MetroTheme.styleTable(table);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                ModelProduk p = tableModel.getProdukAt(row);
                if (!sel) {
                    c.setBackground(p.isStokRendah()
                        ? MetroTheme.LOW_STOCK_BG
                        : (row % 2 == 0 ? MetroTheme.BG_TABLE_ROW : MetroTheme.BG_TABLE_ALT));
                    c.setForeground(MetroTheme.TEXT_PRIMARY);
                } else {
                    c.setBackground(MetroTheme.BG_TABLE_SEL);
                    c.setForeground(Color.WHITE);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        JScrollPane scrollPane = MetroTheme.styledScrollPane(table);

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        legendPanel.setBackground(MetroTheme.BG_DARK);
        JLabel legendColor = new JLabel("█");
        legendColor.setForeground(MetroTheme.LOW_STOCK_BG.brighter().brighter());
        JLabel legendText = MetroTheme.mutedLabel("Baris merah = stok rendah (di bawah minimum)");
        legendPanel.add(legendColor);
        legendPanel.add(legendText);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setBackground(MetroTheme.BG_DARK);
        centerWrap.setBorder(new EmptyBorder(0, 12, 8, 12));
        centerWrap.add(toolbar, BorderLayout.NORTH);
        centerWrap.add(scrollPane, BorderLayout.CENTER);
        centerWrap.add(legendPanel, BorderLayout.SOUTH);

        root.remove(toolbar);
        root.add(centerWrap, BorderLayout.CENTER);

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

    public void refresh() {
        tableModel.setData(ctrlProduk.getAll());
        setVisible(true);
    }
}
