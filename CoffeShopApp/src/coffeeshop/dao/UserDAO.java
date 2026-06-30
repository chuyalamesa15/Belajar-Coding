package coffeeshop.dao;

import coffeeshop.model.User;
import coffeeshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
        this.conn = DBConnection.getConnection();
    }

    /** Login: cek username & password */
    public User login(String username, String password) {
        String sql = "SELECT * FROM tbl_user WHERE username=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Ambil semua user */
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_user ORDER BY id_user";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** Tambah user baru */
    public boolean insert(User u) {
        String sql = "INSERT INTO tbl_user (username,password,nama_lengkap,role) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNamaLengkap());
            ps.setString(4, u.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Update user */
    public boolean update(User u) {
        String sql = "UPDATE tbl_user SET username=?,password=?,nama_lengkap=?,role=? WHERE id_user=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNamaLengkap());
            ps.setString(4, u.getRole());
            ps.setInt(5, u.getIdUser());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Hapus user */
    public boolean delete(int idUser) {
        String sql = "DELETE FROM tbl_user WHERE id_user=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Cek username sudah ada atau belum */
    public boolean isUsernameExist(String username, int excludeId) {
        String sql = "SELECT COUNT(*) FROM tbl_user WHERE username=? AND id_user<>?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setIdUser(rs.getInt("id_user"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setNamaLengkap(rs.getString("nama_lengkap"));
        u.setRole(rs.getString("role"));
        return u;
    }
}