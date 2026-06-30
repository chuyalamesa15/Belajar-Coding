package coffeeshop.ui;

import coffeeshop.dao.KategoriDAO;
import coffeeshop.dao.MenuDAO;
import coffeeshop.model.Kategori;
import coffeeshop.model.Menu;
import coffeeshop.model.User;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Form Data Menu - Tampil + CRUD lengkap
 */
public class MenuFrame extends JFrame {

    private JTable         table;
    private DefaultTableModel tableModel;
    private JTextField     txtCari;
    private JComboBox<Kategori> cbFilterKat;
    private JButton        btnTambah, btnEdit, btnHapus, btnRefresh, btnCari;
    private MenuDAO        menuDAO;
    private KategoriDAO    kategoriDAO;
    private User           currentUser;

    private static final String[] KOLOM = {
        "ID", "Kode", "Nama Menu", "Kategori", "Harga (Rp)", "Stok", "Tersedia"
    };

    public MenuFrame(User user) {
        this.currentUser = user;
        menuDAO    = new MenuDAO();
        kategoriDAO = new KategoriDAO();
        initComponents();
        muatData(null, 0);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Data Menu - Coffee Shop & Cafe");
        setSize(850, 560);
        setLayout(new BorderLayout(5, 5));

        // ---- PANEL ATAS (cari & filter) ----
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pTop.setBackground(new Color(245, 235, 220));

        pTop.add(new JLabel("Cari:"));
        txtCari = new JTextField(18);
        pTop.add(txtCari);

        btnCari = new JButton("🔍 Cari");
        pTop.add(btnCari);

        pTop.add(Box.createHorizontalStrut(10));
        pTop.add(new JLabel("Kategori:"));
        cbFilterKat = new JComboBox<>();
        cbFilterKat.addItem(new Kategori(0, "-- Semua --", ""));
        kategoriDAO.getAll().forEach(cbFilterKat::addItem);
        pTop.add(cbFilterKat);

        btnRefresh = new JButton("🔄 Refresh");
        pTop.add(btnRefresh);
        add(pTop, BorderLayout.NORTH);

        // ---- TABEL ----
        tableModel = new DefaultTableModel(KOLOM, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setMaxWidth(60);
        table.getColumnModel().getColumn(6).setMaxWidth(70);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- PANEL BAWAH (tombol CRUD) ----
        JPanel pBot = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        pBot.setBackground(new Color(230, 215, 195));

        btnTambah = buatTombol("➕ Tambah", new Color(34, 139, 34));
        btnEdit   = buatTombol("✏️ Edit",   new Color(30, 100, 180));
        btnHapus  = buatTombol("🗑️ Hapus",  new Color(200, 60, 60));

        pBot.add(btnTambah);
        pBot.add(btnEdit);
        pBot.add(btnHapus);
        add(pBot, BorderLayout.SOUTH);

        // ---- EVENTS ----
        btnTambah.addActionListener(e -> bukaFormTambah());
        btnEdit.addActionListener(e -> bukaFormEdit());
        btnHapus.addActionListener(e -> hapusMenu());
        btnCari.addActionListener(e -> muatData(txtCari.getText().trim(), 0));
        btnRefresh.addActionListener(e -> {
            txtCari.setText("");
            cbFilterKat.setSelectedIndex(0);
            muatData(null, 0);
        });
        cbFilterKat.addActionListener(e -> {
            Kategori sel = (Kategori) cbFilterKat.getSelectedItem();
            muatData(txtCari.getText().trim(), sel != null ? sel.getIdKategori() : 0);
        });
        txtCari.addActionListener(e -> muatData(txtCari.getText().trim(), 0));
    }

    /** Muat data ke tabel */
    public void muatData(String keyword, int idKategori) {
        tableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("id","ID"));
        List<Menu> list;

        if (idKategori > 0) {
            list = menuDAO.getByKategori(idKategori);
        } else if (keyword != null && !keyword.isEmpty()) {
            list = menuDAO.search(keyword);
        } else {
            list = menuDAO.getAll();
        }

        for (Menu m : list) {
            tableModel.addRow(new Object[]{
                m.getIdMenu(),
                m.getKodeMenu(),
                m.getNamaMenu(),
                m.getNamaKategori(),
                "Rp " + nf.format(m.getHarga()),
                m.getStok(),
                m.isTersedia() ? "✅ Ya" : "❌ Tidak"
            });
        }
    }

    private void bukaFormTambah() {
        MenuFormDialog dlg = new MenuFormDialog(this, null, currentUser);
        dlg.setVisible(true);
        muatData(null, 0);
    }

    private void bukaFormEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin diedit!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMenu = (int) tableModel.getValueAt(row, 0);
        Menu menu = menuDAO.getById(idMenu);
        MenuFormDialog dlg = new MenuFormDialog(this, menu, currentUser);
        dlg.setVisible(true);
        muatData(null, 0);
    }

    private void hapusMenu() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih menu yang ingin dihapus!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        String namaMenu = (String) tableModel.getValueAt(row, 2);
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin hapus menu \"" + namaMenu + "\"?", "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            int idMenu = (int) tableModel.getValueAt(row, 0);
            if (menuDAO.delete(idMenu)) {
                JOptionPane.showMessageDialog(this, "Menu berhasil dihapus.", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
                muatData(null, 0);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus menu!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton buatTombol(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
