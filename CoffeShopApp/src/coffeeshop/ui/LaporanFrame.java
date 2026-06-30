package coffeeshop.ui;

import coffeeshop.dao.MenuDAO;
import coffeeshop.model.User;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Form Laporan - Rekapitulasi menu per kategori (Fitur Opsional)
 */
public class LaporanFrame extends JFrame {

    private JTable            table;
    private DefaultTableModel tableModel;
    private JLabel            lblTotalMenu, lblTotalStok, lblRataHarga;
    private MenuDAO           menuDAO;
    private User              currentUser;

    private static final String[] KOLOM = {
        "No", "Nama Kategori", "Jumlah Menu", "Total Stok", "Rata-rata Harga (Rp)"
    };

    public LaporanFrame(User user) {
        this.currentUser = user;
        this.menuDAO     = new MenuDAO();
        initComponents();
        muatLaporan();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Laporan Menu per Kategori - Coffee Shop & Cafe");
        setSize(720, 520);
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ---- Header ----
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(new Color(139, 90, 43));
        pHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblJudul = new JLabel("📊  LAPORAN MENU PER KATEGORI");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);
        pHeader.add(lblJudul, BorderLayout.WEST);

        JLabel lblCafe = new JLabel("Coffee Shop & Cafe");
        lblCafe.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblCafe.setForeground(new Color(230, 210, 180));
        pHeader.add(lblCafe, BorderLayout.EAST);
        add(pHeader, BorderLayout.NORTH);

        // ---- Tabel ----
        tableModel = new DefaultTableModel(KOLOM, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(139, 90, 43));
        table.getTableHeader().setForeground(Color.WHITE);

        table.getColumnModel().getColumn(0).setMaxWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(180);

        // Rata tengah untuk kolom angka
        DefaultTableCellRenderer tengah = new DefaultTableCellRenderer();
        tengah.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 5; i++) table.getColumnModel().getColumn(i).setCellRenderer(tengah);

        // Warna baris selang-seling
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, focus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (sel) {
                    setBackground(new Color(200, 160, 100));
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(255, 245, 230));
                }
                return this;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- Panel Bawah: Ringkasan & Tombol ----
        JPanel pBot = new JPanel(new BorderLayout(10, 5));
        pBot.setBackground(new Color(245, 235, 220));
        pBot.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Ringkasan
        JPanel pRingkas = new JPanel(new GridLayout(1, 3, 15, 0));
        pRingkas.setBackground(new Color(245, 235, 220));

        lblTotalMenu  = buatKartuRingkasan("Total Menu", "0", new Color(34, 139, 34));
        lblTotalStok  = buatKartuRingkasan("Total Stok", "0", new Color(30, 100, 180));
        lblRataHarga  = buatKartuRingkasan("Rata-rata Harga", "Rp 0", new Color(139, 90, 43));

        pRingkas.add(lblTotalMenu.getParent());
        pRingkas.add(lblTotalStok.getParent());
        pRingkas.add(lblRataHarga.getParent());
        pBot.add(pRingkas, BorderLayout.CENTER);

        // Tombol
        JPanel pTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pTombol.setBackground(new Color(245, 235, 220));

        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setBackground(new Color(139, 90, 43));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> muatLaporan());
        pTombol.add(btnRefresh);

        JButton btnTutup = new JButton("❌ Tutup");
        btnTutup.setBackground(new Color(180, 60, 60));
        btnTutup.setForeground(Color.WHITE);
        btnTutup.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnTutup.setFocusPainted(false);
        btnTutup.addActionListener(e -> dispose());
        pTombol.add(btnTutup);

        pBot.add(pTombol, BorderLayout.EAST);
        add(pBot, BorderLayout.SOUTH);
    }

    private void muatLaporan() {
        tableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));

        List<Object[]> data = menuDAO.getReportPerKategori();

        int totalMenu = 0, totalStok = 0;
        double totalHarga = 0;
        int no = 1;

        for (Object[] row : data) {
            String namaKat  = (String) row[0];
            int    jmlMenu  = (int)    row[1];
            int    totStok  = (int)    row[2];
            double rataHrg  = (double) row[3];

            tableModel.addRow(new Object[]{
                no++,
                namaKat,
                jmlMenu + " item",
                totStok + " pcs",
                jmlMenu > 0 ? "Rp " + nf.format((long) rataHrg) : "-"
            });

            totalMenu  += jmlMenu;
            totalStok  += totStok;
            if (jmlMenu > 0) totalHarga += rataHrg;
        }

        // Update ringkasan
        lblTotalMenu.setText(String.valueOf(totalMenu) + " item");
        lblTotalStok.setText(String.valueOf(totalStok) + " pcs");
        lblRataHarga.setText("Rp " + nf.format((long)(totalHarga / Math.max(1, data.stream()
            .filter(r -> (int)r[1] > 0).count()))));
    }

    /** Buat kartu ringkasan dengan label nilai yang dikembalikan */
    private JLabel buatKartuRingkasan(String judul, String nilai, Color warna) {
        JPanel kartu = new JPanel(new BorderLayout());
        kartu.setBackground(Color.WHITE);
        kartu.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(warna, 2),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        JLabel lblJudul = new JLabel(judul, SwingConstants.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblJudul.setForeground(Color.GRAY);
        kartu.add(lblJudul, BorderLayout.NORTH);

        JLabel lblNilai = new JLabel(nilai, SwingConstants.CENTER);
        lblNilai.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNilai.setForeground(warna);
        kartu.add(lblNilai, BorderLayout.CENTER);

        return lblNilai; // kembalikan label nilai agar bisa diupdate
    }
}
