package coffeeshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Utility class untuk koneksi database MySQL (Laragon)
 */
public class DBConnection {

    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "db_coffeeshop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";  // kosong untuk Laragon default

    private static final String URL =
        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
        + "?useSSL=false&serverTimezone=Asia/Makassar&allowPublicKeyRetrieval=true";

    private static Connection connection = null;

    /**
     * Mendapatkan koneksi database (singleton)
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Driver MySQL tidak ditemukan!\nPastikan file mysql-connector-java.jar sudah ditambahkan ke Libraries.",
                "Error Driver", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Gagal koneksi ke database!\n" + e.getMessage()
                + "\n\nPastikan:\n1. Laragon sudah berjalan\n2. Database 'db_coffeeshop' sudah dibuat",
                "Error Koneksi", JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    /**
     * Menutup koneksi database
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("Error menutup koneksi: " + e.getMessage());
        }
    }
}