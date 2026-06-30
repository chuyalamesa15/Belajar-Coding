package coffeeshop.ui;

import coffeeshop.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menu Utama - Dashboard setelah login berhasil
 */
public class MenuUtamaFrame extends JFrame {

    private User currentUser;

    public MenuUtamaFrame(User user) {
        this.currentUser = user;
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("Menu Utama - Coffee Shop & Cafe");
        setSize(700, 520);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 235, 220));

        // Header
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 700, 90);
        header.setBackground(new Color(80, 45, 15));
        add(header);

        JLabel lblTitle = new JLabel("☕  COFFEE SHOP & CAFE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 15, 450, 40);
        header.add(lblTitle);

        JLabel lblUser = new JLabel("Login sebagai: " + currentUser.getNamaLengkap()
                                   + "  |  Role: " + currentUser.getRole().toUpperCase());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(new Color(220, 200, 170));
        lblUser.setBounds(20, 58, 500, 20);
        header.add(lblUser);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(580, 25, 90, 35);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setBackground(new Color(200, 80, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(btnLogout);

        // Subtitle
        JLabel lblSub = new JLabel("Pilih menu yang ingin dibuka:", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSub.setForeground(new Color(80, 45, 15));
        lblSub.setBounds(0, 105, 700, 30);
        add(lblSub);

        // Tombol Menu
        String[][] menus = {
            {"📋 Data Menu", "Lihat & kelola daftar menu\nminuman dan makanan"},
            {"🗂️ Kategori", "Kelola kategori menu\n(kopi panas, dingin, dll)"},
            {"👤 Data User", "Kelola akun pengguna\ndan kasir"},
            {"📊 Laporan", "Laporan menu per kategori\ndan rekapitulasi stok"}
        };

        Color[] warnaTombol = {
            new Color(34, 139, 34),
            new Color(30, 100, 180),
            new Color(150, 50, 150),
            new Color(180, 100, 20)
        };

        int x = 50;
        for (int i = 0; i < menus.length; i++) {
            final int idx = i;
            JButton btn = new JButton("<html><center>" + menus[i][0]
                        + "<br><small>" + menus[i][1].replace("\n", "<br>") + "</small></center></html>");
            btn.setBounds(x, 155, 140, 120);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setBackground(warnaTombol[i]);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> bukaMenu(idx));
            add(btn);
            x += 160;
        }

        // Info footer
        JLabel lblFooter = new JLabel(
            "Tip: Klik kanan pada tabel untuk opsi Edit & Hapus | Data otomatis tersimpan ke database",
            SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBounds(0, 420, 700, 20);
        add(lblFooter);

        // Aksi logout
        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin logout?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void bukaMenu(int idx) {
        switch (idx) {
            case 0: new MenuFrame(currentUser).setVisible(true);     break;
            case 1: new KategoriFrame(currentUser).setVisible(true); break;
            case 2:
                if (currentUser.getRole().equals("admin")) {
                    new UserFrame(currentUser).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Hanya Admin yang dapat mengakses Data User!",
                        "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case 3: new LaporanFrame(currentUser).setVisible(true);  break;
        }
    }
}
