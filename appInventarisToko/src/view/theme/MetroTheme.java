/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.theme;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

public class MetroTheme {

    public static final Color BG_DARK       = new Color(0x1E1E2E);   // main window bg
    public static final Color BG_SURFACE    = new Color(0x28283D);   // panels
    public static final Color BG_CARD       = new Color(0x313150);   // cards / form panels
    public static final Color BG_INPUT      = new Color(0x3A3A55);   // text fields
    public static final Color BG_TABLE_ROW  = new Color(0x28283D);
    public static final Color BG_TABLE_ALT  = new Color(0x2E2E45);
    public static final Color BG_TABLE_SEL  = new Color(0x0078D4);   // Metro blue selection

    public static final Color ACCENT        = new Color(0x0078D4);   // Windows blue
    public static final Color ACCENT_HOVER  = new Color(0x1084D8);
    public static final Color ACCENT_GREEN  = new Color(0x107C10);   // success
    public static final Color ACCENT_RED    = new Color(0xC50F1F);   // danger
    public static final Color ACCENT_AMBER  = new Color(0xCA5010);   // warning

    public static final Color TEXT_PRIMARY   = new Color(0xF3F3F7);
    public static final Color TEXT_SECONDARY = new Color(0x9999B8);
    public static final Color TEXT_MUTED     = new Color(0x6B6B8A);
    public static final Color BORDER         = new Color(0x3E3E60);
    public static final Color BORDER_FOCUS   = ACCENT;
    public static final Color LOW_STOCK_BG   = new Color(0x3A1A1A);  // subtle red tint

    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,   20);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD,   14);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN,  13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN,  11);
    public static final Font FONT_MONO    = new Font("Consolas", Font.PLAIN,  13);
    public static final Font FONT_BTN     = new Font("Segoe UI", Font.BOLD,   13);
    public static final Font FONT_TOTAL   = new Font("Segoe UI", Font.BOLD,   22);

    public static void install() {
        // Force Nimbus as base then override, or just let Metal be and paint over
        UIManager.put("Panel.background",          BG_SURFACE);
        UIManager.put("OptionPane.background",     BG_SURFACE);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Label.foreground",          TEXT_PRIMARY);
        UIManager.put("Label.font",                FONT_BODY);
        UIManager.put("TextField.background",      BG_INPUT);
        UIManager.put("TextField.foreground",      TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground", ACCENT);
        UIManager.put("TextField.border",
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        UIManager.put("TextField.font",            FONT_BODY);
        UIManager.put("PasswordField.background",  BG_INPUT);
        UIManager.put("PasswordField.foreground",  TEXT_PRIMARY);
        UIManager.put("PasswordField.caretForeground", ACCENT);
        UIManager.put("PasswordField.border",
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        UIManager.put("PasswordField.font",        FONT_BODY);
        UIManager.put("TextArea.background",       BG_INPUT);
        UIManager.put("TextArea.foreground",       TEXT_PRIMARY);
        UIManager.put("TextArea.caretForeground",  ACCENT);
        UIManager.put("TextArea.font",             FONT_BODY);
        UIManager.put("ComboBox.background",       BG_INPUT);
        UIManager.put("ComboBox.foreground",       TEXT_PRIMARY);
        UIManager.put("ComboBox.selectionBackground", ACCENT);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.font",             FONT_BODY);
        UIManager.put("Table.background",          BG_TABLE_ROW);
        UIManager.put("Table.foreground",          TEXT_PRIMARY);
        UIManager.put("Table.selectionBackground", BG_TABLE_SEL);
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.gridColor",           BORDER);
        UIManager.put("Table.font",                FONT_BODY);
        UIManager.put("TableHeader.background",    BG_CARD);
        UIManager.put("TableHeader.foreground",    TEXT_SECONDARY);
        UIManager.put("TableHeader.font",          FONT_HEADING);
        UIManager.put("ScrollPane.background",     BG_DARK);
        UIManager.put("ScrollBar.background",      BG_SURFACE);
        UIManager.put("ScrollBar.thumb",           BORDER);
        UIManager.put("ScrollBar.thumbHighlight",  ACCENT);
        UIManager.put("ScrollBar.thumbDarkShadow", BG_DARK);
        UIManager.put("ScrollBar.track",           BG_SURFACE);
        UIManager.put("TitledBorder.titleColor",   TEXT_SECONDARY);
        UIManager.put("TitledBorder.border",       BorderFactory.createLineBorder(BORDER));
        UIManager.put("Button.background",         BG_CARD);
        UIManager.put("Button.foreground",         TEXT_PRIMARY);
        UIManager.put("Button.font",               FONT_BTN);
        UIManager.put("Button.border",
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    BorderFactory.createEmptyBorder(6, 16, 6, 16)));
        UIManager.put("OptionPane.messageFont",    FONT_BODY);
        UIManager.put("OptionPane.buttonFont",     FONT_BTN);
    }

    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(BG_DARK);
    }

    public static JPanel surfacePanel() {
        JPanel p = new JPanel();
        p.setBackground(BG_SURFACE);
        return p;
    }

    public static JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        return p;
    }

    public static JLabel titleLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static JLabel headingLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_HEADING);
        l.setForeground(TEXT_SECONDARY);
        return l;
    }

    public static JLabel bodyLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static JLabel mutedLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SMALL);
        l.setForeground(TEXT_MUTED);
        return l;
    }


    public static JButton primaryButton(String text) {
        return makeBtn(text, ACCENT, Color.WHITE, ACCENT_HOVER);
    }

    public static JButton successButton(String text) {
        return makeBtn(text, ACCENT_GREEN, Color.WHITE, new Color(0x148B14));
    }

    public static JButton dangerButton(String text) {
        return makeBtn(text, ACCENT_RED, Color.WHITE, new Color(0xD81429));
    }

    public static JButton ghostButton(String text) {
        return makeBtn(text, BG_CARD, TEXT_PRIMARY, new Color(0x3E3E5A));
    }

    private static JButton makeBtn(String text, Color bg, Color fg, Color hover) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = getModel().isRollover() ? hover : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void styleTextField(JTextField f) {
        f.setBackground(BG_INPUT);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public static void stylePasswordField(JPasswordField f) {
        f.setBackground(BG_INPUT);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public static void styleTextArea(JTextArea a) {
        a.setBackground(BG_INPUT);
        a.setForeground(TEXT_PRIMARY);
        a.setCaretColor(ACCENT);
        a.setFont(FONT_BODY);
        a.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
    }

    public static void styleComboBox(JComboBox<?> cb) {
        cb.setBackground(BG_INPUT);
        cb.setForeground(TEXT_PRIMARY);
        cb.setFont(FONT_BODY);
        cb.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int idx, boolean sel, boolean foc) {
                super.getListCellRendererComponent(list, value, idx, sel, foc);
                setBackground(sel ? ACCENT : BG_INPUT);
                setForeground(sel ? Color.WHITE : TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                return this;
            }
        });
    }

    public static void styleTable(JTable table) {
        table.setBackground(BG_TABLE_ROW);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(FONT_BODY);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(BG_TABLE_SEL);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_CARD);
        header.setForeground(TEXT_SECONDARY);
        header.setFont(FONT_HEADING);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.LEFT);
    }

    public static JScrollPane styledScrollPane(Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_TABLE_ROW);
        sp.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        sp.getVerticalScrollBar().setBackground(BG_SURFACE);
        sp.getHorizontalScrollBar().setBackground(BG_SURFACE);
        return sp;
    }

    public static JSeparator accentSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(ACCENT);
        sep.setBackground(BG_SURFACE);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        return sep;
    }

    public static Border cardBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER, 1), " " + title + " ");
        tb.setTitleColor(TEXT_SECONDARY);
        tb.setTitleFont(FONT_HEADING);
        return BorderFactory.createCompoundBorder(tb,
            BorderFactory.createEmptyBorder(6, 8, 6, 8));
    }
}
