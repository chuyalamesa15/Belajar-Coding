package coffeeshop.dao;

import coffeeshop.model.Kategori;
import coffeeshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {

    private Connection conn;

    public KategoriDAO() {
        this.conn = DBConnection.getConnection();
    }

    public List<Kategori> getAll() {
        List<Kategori> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_kategori ORDER BY id_kategori";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Kategori getById(int id) {
        String sql = "SELECT * FROM tbl_kategori WHERE id_kategori=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Kategori k) {
        String sql = "INSERT INTO tbl_kategori (nama_kategori, keterangan) VALUES (?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getNamaKategori());
            ps.setString(2, k.getKeterangan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Kategori k) {
        String sql = "UPDATE tbl_kategori SET nama_kategori=?, keterangan=? WHERE id_kategori=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getNamaKategori());
            ps.setString(2, k.getKeterangan());
            ps.setInt(3, k.getIdKategori());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM tbl_kategori WHERE id_kategori=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /** Cek apakah kategori masih digunakan di tbl_menu */
    public boolean isUsedByMenu(int idKategori) {
        String sql = "SELECT COUNT(*) FROM tbl_menu WHERE id_kategori=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKategori);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Kategori mapRow(ResultSet rs) throws SQLException {
        Kategori k = new Kategori();
        k.setIdKategori(rs.getInt("id_kategori"));
        k.setNamaKategori(rs.getString("nama_kategori"));
        k.setKeterangan(rs.getString("keterangan"));
        return k;
    }
}