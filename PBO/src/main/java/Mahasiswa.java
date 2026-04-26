/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mtuma
 */
public class Mahasiswa {
    // Atribut
    String npm;
    String nama;
    double ipk;
    int semester;

    // Constructor
    public Mahasiswa(String npm, String nama, double ipk, int semester) {
        this.npm = npm;
        this.nama = nama;
        this.ipk = ipk;
        this.semester = semester;
    }

    // Method untuk menampilkan info
    public void cetakInfo() {
        System.out.println("NPM Mahasiswa  : " + npm);
        System.out.println("Nama Mahasiswa : " + nama);
        System.out.println("IPK            : " + ipk);
        System.out.println("Semester       : " + semester);
    }
}
