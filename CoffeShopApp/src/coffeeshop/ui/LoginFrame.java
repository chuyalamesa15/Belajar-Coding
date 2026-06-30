package coffeeshop.ui;

import coffeeshop.dao.UserDAO;
import coffeeshop.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Form Login - Halaman pertama aplikasi
 */
public class LoginFrame extends JFrame {

    private JTextField    txtUsername;
    private JPasswordField txtPassword;
    private JButton       btnLogin, btnKeluar;
    private UserDAO       userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setTitle("Login - Coffee Shop & Cafe");
        setSize(420, 340);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(40, 25, 15)); // coklat gelap

        // ---- Panel putih di tengah ----
        JPanel panel = new JPanel(null);
        panel.setBounds(30, 30, 360, 270);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(180, 120, 60), 2));
        add(panel);

        // Judul
        JLabel lblJudul = new JLabel("☕  COFFEE SHOP", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblJudul.setForeground(new Color(100, 60, 20));
        lblJudul.setBounds(0, 15, 360, 35);
        panel.add(lblJudul);

        JLabel lblSub = new JLabel("Silakan login untuk melanjutkan", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(Color.GRAY);
        lblSub.setBounds(0, 50, 360, 20);
        panel.add(lblSub);

        // Username
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setBounds(40, 85, 100, 25);
        panel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(40, 110, 280, 30);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtUsername);

        // Password
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setBounds(40, 145, 100, 25);
        panel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 170, 280, 30);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtPassword);

        // Tombol Login
        btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(40, 215, 130, 35);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBackground(new Color(139, 90, 43));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnLogin);

        // Tombol Keluar
        btnKeluar = new JButton("KELUAR");
        btnKeluar.setBounds(190, 215, 130, 35);
        btnKeluar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnKeluar.setBackground(new Color(180, 60, 60));
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFocusPainted(false);
        btnKeluar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnKeluar);

        // ---- Events ----
        btnLogin.addActionListener(e -> doLogin());
        btnKeluar.addActionListener(e -> System.exit(0));

        // Enter di password langsung login
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) doLogin();
            }
        });
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username dan Password tidak boleh kosong!", "Perhatian",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDAO.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                "Selamat datang, " + user.getNamaLengkap() + "!",
                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
            new MenuUtamaFrame(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Username atau Password salah!", "Login Gagal",
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { /* pakai default */ }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
