/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mtuma
 */
public class User {
    int id;
    String email;
    String password;

    public void index() {
        System.out.println("SELECT * FROM user;");
    }

    public void create() {
        System.out.println("Menampilkan form tambah user");
    }

    public void store() {
        System.out.println("INSERT INTO user (id, email, password) VALUES (" 
                + id + ", '" + email + "', '" + password + "');");
    }

    public void edit() {
        System.out.println("Menampilkan form edit user dengan id = " + id);
    }

    public void update() {
        System.out.println("UPDATE user SET email='" + email + "', password='" 
                + password + "' WHERE id=" + id + ";");
    }

    public void destroy() {
        System.out.println("DELETE FROM user WHERE id=" + id + ";");
    }
}
