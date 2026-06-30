package coffeeshop.ui;

import coffeeshop.dao.KategoriDAO;
import coffeeshop.dao.MenuDAO;
import coffeeshop.model.Kategori;
import coffeeshop.model.Menu;
import coffeeshop.model.User;
import java.awt.*;
import javax.swing.*;

/**
 * Dialog Form Tambah / Edit Menu
 */
public class MenuFormDialog extends JDialog {

    private JTextField    txtKode, txtNama, txtHarga, txtStok, txtDeskripsi;
    private JComboBox<Kategori> cbKategori;
    private JCheckBox     chkTersedia;
    private JButton       btnSimpan, btnBatal;

    private MenuDAO       menuDAO;
    private KategoriDAO   kategoriDAO;
    private Menu          menuEdit;   // null = mode tambah
    private User          currentUser;

    public MenuFormDialog(JFrame parent, Menu menu, User user) {
        super(parent, menu == null ? "Tambah Menu" : "Edit Menu", true);
        this.menuEdit    = menu;
        this.currentUser = user;
        this.menuDAO     = new MenuDAO();
        this.kategoriDAO = new KategoriDAO();
        initComponents();
        if (menu != null) isiForm(menu);
        setSize(420, 420);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(null);
        getContentPane().setBackground(new Color(245, 235, 220));

        // Judul
        JLabel lblJudul = new JLabel(menuEdit == null ? "➕ TAMBAH MENU BARU" : "✏️ EDIT DATA MENU",
                SwingConstants.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblJudul.setForeground(new Color(80, 45, 15));
        lblJudul.setBounds(0, 10, 420, 30);
        add(lblJudul);

        // Kode Menu
        tambahLabel("Kode Menu:", 20, 55);
        txtKode = new JTextField();
        txtKode.setBounds(140, 55, 240, 28);
        add(txtKode);

        // Nama Menu
        tambahLabel("Nama Menu:", 20, 95);
        txtNama = new JTextField();
        txtNama.setBounds(140, 95, 240, 28);
        add(txtNama);

        // Kategori
        tambahLabel("Kategori:", 20, 135);
        cbKategori = new JComboBox<>();
        kategoriDAO.getAll().forEach(cbKategori::addItem);
        cbKategori.setBounds(140, 135, 240, 28);
        add(cbKategori);

        // Harga
        tambahLabel("Harga (Rp):", 20, 175);
        txtHarga = new JTextField("0");
        txtHarga.setBounds(140, 175, 240, 28);
        add(txtHarga);

        // Stok
        tambahLabel("Stok:", 20, 215);
        txtStok = new JTextField("0");
        txtStok.setBounds(140, 215, 240, 28);
        add(txtStok);

        // Deskripsi
        tambahLabel("Deskripsi:", 20, 255);
        txtDeskripsi = new JTextField();
        txtDeskripsi.setBounds(140, 255, 240, 28);
        add(txtDeskripsi);

        // Tersedia
        chkTersedia = new JCheckBox("Menu Tersedia");
        chkTersedia.setBounds(140, 295, 200, 28);
        chkTersedia.setBackground(new Color(245, 235, 220));
        chkTersedia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkTersedia.setSelected(true);
        add(chkTersedia);

        // Tombol Simpan
        btnSimpan = new JButton("💾 Simpan");
        btnSimpan.setBounds(60, 345, 130, 38);
        btnSimpan.setBackground(new Color(34, 139, 34));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSimpan.setFocusPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSimpan);

        // Tombol Batal
        btnBatal = new JButton("❌ Batal");
        btnBatal.setBounds(220, 345, 130, 38);
        btnBatal.setBackground(new Color(180, 60, 60));
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBatal.setFocusPainted(false);
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnBatal);

        // Events
        btnSimpan.addActionListener(e -> simpan());
        btnBatal.addActionListener(e -> dispose());
    }

    /** Isi form saat mode EDIT */
    private void isiForm(Menu m) {
        txtKode.setText(m.getKodeMenu());
        txtNama.setText(m.getNamaMenu());
        txtHarga.setText(String.valueOf((int) m.getHarga()));
        txtStok.setText(String.valueOf(m.getStok()));
        txtDeskripsi.setText(m.getDeskripsi());
        chkTersedia.setSelected(m.isTersedia());

        // Set kategori di combobox
        for (int i = 0; i < cbKategori.getItemCount(); i++) {
            if (cbKategori.getItemAt(i).getIdKategori() == m.getIdKategori()) {
                cbKategori.setSelectedIndex(i);
                break;
            }
        }
    }

    private void simpan() {
        // Validasi input
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stokStr  = txtStok.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Kode, Nama, Harga, dan Stok wajib diisi!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        double harga;
        int stok;
        try {
            harga = Double.parseDouble(hargaStr);
            stok  = Integer.parseInt(stokStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Harga dan Stok harus berupa angka!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Kategori katTerpilih = (Kategori) cbKategori.getSelectedItem();
        if (katTerpilih == null) {
            JOptionPane.showMessageDialog(this, "Pilih kategori!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buat objek Menu
        Menu m = new Menu();
        m.setKodeMenu(kode);
        m.setNamaMenu(nama);
        m.setIdKategori(katTerpilih.getIdKategori());
        m.setHarga(harga);
        m.setStok(stok);
        m.setDeskripsi(txtDeskripsi.getText().trim());
        m.setTersedia(chkTersedia.isSelected());

        boolean berhasil;
        if (menuEdit == null) {
            // Cek kode duplikat
            if (menuDAO.isKodeExist(kode, 0)) {
                JOptionPane.showMessageDialog(this,
                    "Kode menu \"" + kode + "\" sudah digunakan!", "Perhatian",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            berhasil = menuDAO.insert(m);
        } else {
            // Cek kode duplikat (kecuali diri sendiri)
            if (menuDAO.isKodeExist(kode, menuEdit.getIdMenu())) {
                JOptionPane.showMessageDialog(this,
                    "Kode menu \"" + kode + "\" sudah digunakan!", "Perhatian",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            m.setIdMenu(menuEdit.getIdMenu());
            berhasil = menuDAO.update(m);
        }

        if (berhasil) {
            JOptionPane.showMessageDialog(this,
                "Data menu berhasil " + (menuEdit == null ? "ditambahkan!" : "diperbarui!"),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data!", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tambahLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBounds(x, y, 120, 28);
        add(lbl);
    }
}
