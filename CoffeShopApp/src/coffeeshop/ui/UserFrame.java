package coffeeshop.ui;

import coffeeshop.dao.UserDAO;
import coffeeshop.model.User;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Form Data User - Hanya bisa diakses oleh Admin
 */
public class UserFrame extends JFrame {

    private JTable            table;
    private DefaultTableModel tableModel;
    private JTextField        txtUsername, txtPassword, txtNama;
    private JComboBox<String> cbRole;
    private JButton           btnTambah, btnUpdate, btnHapus, btnBersih;
    private UserDAO           userDAO;
    private User              currentUser;
    private int               idEdit = -1;

    private static final String[] KOLOM = {"ID", "Username", "Nama Lengkap", "Role"};

    public UserFrame(User user) {
        this.currentUser = user;
        this.userDAO     = new UserDAO();
        initComponents();
        muatData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Data User - Coffee Shop & Cafe  [Admin Only]");
        setSize(780, 520);
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ---- Header ----
        JPanel pHeader = new JPanel();
        pHeader.setBackground(new Color(60, 30, 100));
        JLabel lblHeader = new JLabel("👤  Manajemen User  (Admin Only)");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        pHeader.add(lblHeader);
        add(pHeader, BorderLayout.NORTH);

        // ---- Tabel ----
        tableModel = new DefaultTableModel(KOLOM, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(440, 0));

        // ---- Panel Form ----
        JPanel pForm = new JPanel(null);
        pForm.setBackground(new Color(240, 230, 255));
        pForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 40, 140), 2),
            "Form User",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(60, 30, 100)));

        // Username
        tambahLabel(pForm, "Username:", 15, 35);
        txtUsername = new JTextField();
        txtUsername.setBounds(15, 60, 255, 30);
        pForm.add(txtUsername);

        // Password
        tambahLabel(pForm, "Password:", 15, 100);
        txtPassword = new JTextField();
        txtPassword.setBounds(15, 125, 255, 30);
        pForm.add(txtPassword);

        // Nama Lengkap
        tambahLabel(pForm, "Nama Lengkap:", 15, 165);
        txtNama = new JTextField();
        txtNama.setBounds(15, 190, 255, 30);
        pForm.add(txtNama);

        // Role
        tambahLabel(pForm, "Role:", 15, 230);
        cbRole = new JComboBox<>(new String[]{"admin", "kasir"});
        cbRole.setBounds(15, 255, 255, 30);
        pForm.add(cbRole);

        // Tombol-tombol
        btnTambah = buatTombol("➕ Tambah", new Color(34, 139, 34));
        btnTambah.setBounds(15, 305, 120, 35);
        pForm.add(btnTambah);

        btnUpdate = buatTombol("✏️ Update", new Color(30, 100, 180));
        btnUpdate.setBounds(150, 305, 120, 35);
        btnUpdate.setEnabled(false);
        pForm.add(btnUpdate);

        btnHapus = buatTombol("🗑️ Hapus", new Color(200, 60, 60));
        btnHapus.setBounds(15, 355, 120, 35);
        btnHapus.setEnabled(false);
        pForm.add(btnHapus);

        btnBersih = buatTombol("🔄 Bersih", new Color(120, 120, 120));
        btnBersih.setBounds(150, 355, 120, 35);
        pForm.add(btnBersih);

        JLabel lblInfo = new JLabel("Klik baris tabel untuk edit/hapus", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setBounds(0, 405, 290, 20);
        pForm.add(lblInfo);

        // ---- Split ----
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, pForm);
        split.setDividerLocation(440);
        split.setDividerSize(4);
        split.setEnabled(false);
        add(split, BorderLayout.CENTER);

        // ---- Events ----
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) isiFormDariTabel();
        });
        btnTambah.addActionListener(e -> tambahUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnHapus.addActionListener(e -> hapusUser());
        btnBersih.addActionListener(e -> bersihForm());
    }

    private void muatData() {
        tableModel.setRowCount(0);
        List<User> list = userDAO.getAll();
        for (User u : list) {
            tableModel.addRow(new Object[]{
                u.getIdUser(),
                u.getUsername(),
                u.getNamaLengkap(),
                u.getRole()
            });
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        idEdit = (int) tableModel.getValueAt(row, 0);
        txtUsername.setText((String) tableModel.getValueAt(row, 1));
        txtNama.setText((String) tableModel.getValueAt(row, 2));
        cbRole.setSelectedItem(tableModel.getValueAt(row, 3));
        txtPassword.setText(""); // password tidak ditampilkan

        btnUpdate.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambah.setEnabled(false);
    }

    private void tambahUser() {
        if (!validasiForm()) return;

        // Cek username duplikat
        if (userDAO.isUsernameExist(txtUsername.getText().trim(), 0)) {
            JOptionPane.showMessageDialog(this,
                "Username sudah digunakan!", "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User u = buatObjekUser();
        if (userDAO.insert(u)) {
            JOptionPane.showMessageDialog(this, "User berhasil ditambahkan!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            muatData();
            bersihForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambah user!", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUser() {
        if (!validasiForm()) return;

        // Cek username duplikat (kecuali diri sendiri)
        if (userDAO.isUsernameExist(txtUsername.getText().trim(), idEdit)) {
            JOptionPane.showMessageDialog(this,
                "Username sudah digunakan!", "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cegah admin menghapus/mengubah role dirinya sendiri menjadi kasir
        if (idEdit == currentUser.getIdUser() && cbRole.getSelectedItem().equals("kasir")) {
            JOptionPane.showMessageDialog(this,
                "Anda tidak bisa mengubah role diri sendiri menjadi kasir!",
                "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User u = buatObjekUser();
        u.setIdUser(idEdit);
        if (userDAO.update(u)) {
            JOptionPane.showMessageDialog(this, "User berhasil diperbarui!", "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            muatData();
            bersihForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui user!", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusUser() {
        if (idEdit == currentUser.getIdUser()) {
            JOptionPane.showMessageDialog(this,
                "Anda tidak bisa menghapus akun sendiri!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin hapus user \"" + txtUsername.getText() + "\"?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (userDAO.delete(idEdit)) {
                JOptionPane.showMessageDialog(this, "User berhasil dihapus!", "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
                muatData();
                bersihForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus user!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validasiForm() {
        if (txtUsername.getText().trim().isEmpty() ||
            txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username dan Nama Lengkap wajib diisi!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (idEdit == -1 && txtPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Password wajib diisi untuk user baru!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private User buatObjekUser() {
        User u = new User();
        u.setUsername(txtUsername.getText().trim());
        u.setNamaLengkap(txtNama.getText().trim());
        u.setRole((String) cbRole.getSelectedItem());
        String pass = txtPassword.getText().trim();
        u.setPassword(pass.isEmpty() ? null : pass);
        return u;
    }

    private void bersihForm() {
        idEdit = -1;
        txtUsername.setText("");
        txtPassword.setText("");
        txtNama.setText("");
        cbRole.setSelectedIndex(1); // default kasir
        btnTambah.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
        table.clearSelection();
    }

    private void tambahLabel(JPanel panel, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBounds(x, y, 140, 25);
        panel.add(lbl);
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
