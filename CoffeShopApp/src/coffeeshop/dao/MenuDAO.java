package coffeeshop.dao;

import coffeeshop.model.Menu;
import coffeeshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    private Connection conn;

    public MenuDAO() {
        this.conn = DBConnection.getConnection();
    }

    /** Ambil semua menu beserta nama kategori (JOIN) */
    public List<Menu> getAll() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT m.*, k.nama_kategori "
                   + "FROM tbl_menu m "
                   + "JOIN tbl_kategori k ON m.id_kategori = k.id_kategori "
                   + "ORDER BY m.id_menu";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** Cari menu berdasarkan nama atau kode */
    public List<Menu> search(String keyword) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT m.*, k.nama_kategori "
                   + "FROM tbl_menu m "
                   + "JOIN tbl_kategori k ON m.id_kategori = k.id_kategori "
                   + "WHERE m.nama_menu LIKE ? OR m.kode_menu LIKE ? "
                   + "ORDER BY m.id_menu";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** Filter menu berdasarkan kategori */
    public List<Menu> getByKategori(int idKategori) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT m.*, k.nama_kategori "
                   + "FROM tbl_menu m "
                   + "JOIN tbl_kategori k ON m.id_kategori = k.id_kategori "
                   + "WHERE m.id_kategori=? ORDER BY m.nama_menu";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKategori);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /** Ambil satu menu berdasarkan ID */
    public Menu getById(int id) {
        String sql = "SELECT m.*, k.nama_kategori "
                   + "FROM tbl_menu m "
                   + "JOIN tbl_kategori k ON m.id_kategori = k.id_kategori "
                   + "WHERE m.id_menu=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /** Tambah menu baru */
    public boolean insert(Menu m) {
        String sql = "INSERT INTO tbl_menu "
                   + "(kode_menu,nama_menu,id_kategori,harga,stok,deskripsi,tersedia) "
                   + "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getKodeMenu());
            ps.setString(2, m.getNamaMenu());
            ps.setInt(3, m.getIdKategori());
            ps.setDouble(4, m.getHarga());
            ps.setInt(5, m.getStok());
            ps.setString(6, m.getDeskripsi());
            ps.setBoolean(7, m.isTersedia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Update menu */
    public boolean update(Menu m) {
        String sql = "UPDATE tbl_menu SET "
                   + "kode_menu=?,nama_menu=?,id_kategori=?,harga=?,stok=?,deskripsi=?,tersedia=? "
                   + "WHERE id_menu=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getKodeMenu());
            ps.setString(2, m.getNamaMenu());
            ps.setInt(3, m.getIdKategori());
            ps.setDouble(4, m.getHarga());
            ps.setInt(5, m.getStok());
            ps.setString(6, m.getDeskripsi());
            ps.setBoolean(7, m.isTersedia());
            ps.setInt(8, m.getIdMenu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Hapus menu */
    public boolean delete(int id) {
        String sql = "DELETE FROM tbl_menu WHERE id_menu=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Cek kode menu sudah ada atau belum */
    public boolean isKodeExist(String kode, int excludeId) {
        String sql = "SELECT COUNT(*) FROM tbl_menu WHERE kode_menu=? AND id_menu<>?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /** Report: jumlah menu & total stok per kategori */
    public List<Object[]> getReportPerKategori() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT k.nama_kategori, COUNT(m.id_menu) AS jumlah_menu, "
                   + "SUM(m.stok) AS total_stok, AVG(m.harga) AS rata_harga "
                   + "FROM tbl_kategori k "
                   + "LEFT JOIN tbl_menu m ON k.id_kategori = m.id_kategori "
                   + "GROUP BY k.id_kategori, k.nama_kategori "
                   + "ORDER BY k.id_kategori";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("nama_kategori"),
                    rs.getInt("jumlah_menu"),
                    rs.getInt("total_stok"),
                    rs.getDouble("rata_harga")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Menu mapRow(ResultSet rs) throws SQLException {
        Menu m = new Menu();
        m.setIdMenu(rs.getInt("id_menu"));
        m.setKodeMenu(rs.getString("kode_menu"));
        m.setNamaMenu(rs.getString("nama_menu"));
        m.setIdKategori(rs.getInt("id_kategori"));
        m.setNamaKategori(rs.getString("nama_kategori"));
        m.setHarga(rs.getDouble("harga"));
        m.setStok(rs.getInt("stok"));
        m.setDeskripsi(rs.getString("deskripsi"));
        m.setTersedia(rs.getBoolean("tersedia"));
        return m;
    }
}