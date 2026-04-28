/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mtuma
 */
public class Buku {
    int id;
    String judul;
    String penulis;
    int tahun;
    Penerbit penerbit;

    public void index() {
        System.out.println("SELECT * FROM buku;");
    }

    public void create() {
        System.out.println("Menampilkan form tambah buku");
    }

    public void store() {
        System.out.println("INSERT INTO buku (id, judul, penulis, tahun, penerbit_id) VALUES (" 
                + id + ", '" + judul + "', '" + penulis + "', " 
                + tahun + ", " + penerbit.id + ");");
    }

    public void edit() {
        System.out.println("Menampilkan form edit buku dengan id = " + id);
    }

    public void update() {
        System.out.println("UPDATE buku SET judul='" + judul 
                + "', penulis='" + penulis 
                + "', tahun=" + tahun 
                + ", penerbit_id=" + penerbit.id 
                + " WHERE id=" + id + ";");
    }

    public void destroy() {
        System.out.println("DELETE FROM buku WHERE id=" + id + ";");
    }
}
