/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mtuma
 */
public class Penerbit {
    int id;
    String namaPenerbit;
    String alamatPenerbit;

    public void index() {
        System.out.println("SELECT * FROM penerbit;");
    }

    public void create() {
        System.out.println("Menampilkan form tambah penerbit");
    }

    public void store() {
        System.out.println("INSERT INTO penerbit (id, namaPenerbit, alamatPenerbit) VALUES (" 
                + id + ", '" + namaPenerbit + "', '" + alamatPenerbit + "');");
    }

    public void edit() {
        System.out.println("Menampilkan form edit penerbit dengan id = " + id);
    }

    public void update() {
        System.out.println("UPDATE penerbit SET namaPenerbit='" + namaPenerbit 
                + "', alamatPenerbit='" + alamatPenerbit 
                + "' WHERE id=" + id + ";");
    }

    public void destroy() {
        System.out.println("DELETE FROM penerbit WHERE id=" + id + ";");
    }
}
