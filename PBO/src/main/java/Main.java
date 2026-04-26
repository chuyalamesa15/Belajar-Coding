/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author mtuma
 */
import Akademis.MataKuliah; // Mengimpor class dari package akademis

public class Main {
    public static void main(String[] args) {
        // 1. Memanggil dan membuat objek Mahasiswa
        Mahasiswa mhs = new Mahasiswa("2410010444", "Muhammad Tamirul Umam", 3.85, 4);

        // 2. Memanggil dan membuat objek Mata Kuliah
        MataKuliah mk = new MataKuliah("INF-202", "Pemrograman Berorientasi Objek");

        // Menampilkan Output
        System.out.println("===== DATA AKADEMIK =====");
        mhs.cetakInfo();
        System.out.println("-------------------------");
        mk.cetakInfoMK();
        System.out.println("=========================");
    }
}
