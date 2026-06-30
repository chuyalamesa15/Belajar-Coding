package coffeeshop.ui;

import coffeeshop.dao.KategoriDAO;
import coffeeshop.model.Kategori;
import coffeeshop.model.User;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Form Data Kategori - Tampil + CRUD lengkap
 */
public class KategoriFrame extends JFrame {

    private JTable             table;
    private DefaultTableModel  tableModel;
    private JTextField         txtNama, txtKeterangan;
    private JButton            btnTambah, btnUpdate, btnHapus, btnBersih;
    private KategoriDAO        kategoriDAO;
    private User               currentUser;
    private int                idEdit = -1; // -1 = mode tambah

    private static final String[] KOLOM = {"ID", "Nama Kategori", "Keterangan"};

    public KategoriFrame(User user) {
        this.currentUser = user;
        this.kategoriDAO = new KategoriDAO();
        initComponents();
        muatData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Data Kategori - Coffee Shop & Cafe");
        setSize(750, 520);
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ---- PANEL KIRI: Tabel ----
        tableModel = new DefaultTableModel(KOLOM, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(260);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(430, 0));

        // ---- PANEL KANAN: Form input ----
        JPanel pForm = new JPanel(null);
        pForm.setPreferredSize(new Dimension(290, 0));
        pForm.setBackground(new Color(245, 235, 220));
        pForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(139, 90, 43), 2),
            "Form Kategori",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(80, 45, 15)));

        JLabel lblNama = new JLabel("Nama Kategori:");
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNama.setBounds(15, 35, 130, 25);
        pForm.add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(15, 60, 255, 30);
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pForm.add(txtNama);

        JLabel lblKet = new JLabel("Keterangan:");
        lblKet.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblKet.setBounds(15, 100, 130, 25);
        pForm.add(lblKet);

        txtKeterangan = new JTextField();
        txtKeterangan.setBounds(15, 125, 255, 30);
        txtKeterangan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pForm.add(txtKeterangan);

        // Tombol Tambah
        btnTambah = buatTombol("➕ Tambah", new Color(34, 139, 34));
        btnTambah.setBounds(15, 180, 120, 35);
        pForm.add(btnTambah);

        // Tombol Update
        btnUpdate = buatTombol("✏️ Update", new Color(30, 100, 180));
        btnUpdate.setBounds(150, 180, 120, 35);
        btnUpdate.setEnabled(false);
        pForm.add(btnUpdate);

        // Tombol Hapus
        btnHapus = buatTombol("🗑️ Hapus", new Color(200, 60, 60));
        btnHapus.setBounds(15, 230, 120, 35);
        btnHapus.setEnabled(false);
        pForm.add(btnHapus);

        // Tombol Bersih
        btnBersih = buatTombol("🔄 Bersih", new Color(120, 120, 120));
        btnBersih.setBounds(150, 230, 120, 35);
        pForm.add(btnBersih);

        // Info mode
        JLabel lblInfo = new JLabel("Klik baris tabel untuk edit/hapus", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setBounds(0, 285, 290, 20);
        pForm.add(lblInfo);

        // ---- Gabung panel ----
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, pForm);
        split.setDividerLocation(430);
        split.setDividerSize(4);
        split.setEnabled(false);
        add(split, BorderLayout.CENTER);

        // ---- Header ----
        JPanel pHeader = new JPanel();
        pHeader.setBackground(new Color(80, 45, 15));
        JLabel lblHeader = new JLabel("🗂️  Data Kategori Menu");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        pHeader.add(lblHeader);
        add(pHeader, BorderLayout.NORTH);

        // ---- EVENTS ----
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) isiFormDariTabel();
        });

        btnTambah.addActionListener(e -> tambahKategori());
        btnUpdate.addActionListener(e -> updateKategori());
        btnHapus.addActionListener(e -> hapusKategori());
        btnBersih.addActionListener(e -> bersihForm());
    }

    private void muatData() {
        tableModel.setRowCount(0);
        List<Kategori> list = kategoriDAO.getAll();
        for (Kategori k : list) {
            tableModel.addRow(new Object[]{
                k.getIdKategori(),
                k.getNamaKategori(),
                k.getKeterangan()
            });
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        idEdit = (int) tableModel.getValueAt(row, 0);
        txtNama.setText((String) tableModel.getValueAt(row, 1));
        txtKeterangan.setText((String) tableModel.getValueAt(row, 2));

        btnUpdate.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
    }

    private void tambahKategori() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        Kategori k = new Kategori(0, nama, txtKeterangan.getText().trim());
        if (kategoriDAO.insert(k)) {
            JOptionPane.showMessageDialog(this, "Kategori berhasil ditambahkan!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            muatData();
            bersihForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambah kategori!", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateKategori() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        Kategori k = new Kategori(idEdit, nama, txtKeterangan.getText().trim());
        if (kategoriDAO.update(k)) {
            JOptionPane.showMessageDialog(this, "Kategori berhasil diperbarui!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            muatData();
            bersihForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui kategori!", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusKategori() {
        if (kategoriDAO.isUsedByMenu(idEdit)) {
            JOptionPane.showMessageDialog(this,
                "Kategori tidak bisa dihapus karena masih digunakan oleh menu!",
                "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin hapus kategori \"" + txtNama.getText() + "\"?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (kategoriDAO.delete(idEdit)) {
                JOptionPane.showMessageDialog(this, "Kategori berhasil dihapus!", "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
                muatData();
                bersihForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus kategori!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void bersihForm() {
        idEdit = -1;
        txtNama.setText("");
        txtKeterangan.setText("");
        txtNama.requestFocus();
        btnTambah.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        table.clearSelection();
    }

    private JButton buatTombol(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
