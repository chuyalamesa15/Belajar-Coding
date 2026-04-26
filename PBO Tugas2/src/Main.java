/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author mtuma
 */
public class Main {
    public static void main(String[] args) {

        // objek dari constructor tanpa parameter
        Mobil mobil1 = new Mobil();

        // objek dari constructor dengan parameter
        Mobil mobil2 = new Mobil("Toyota", 2018);

        // tampilkan data
        System.out.println("Mobil 1:");
        mobil1.tampilkanData();
        System.out.println("Umur Mobil: " + mobil1.umurMobil() + " tahun");

        System.out.println("\nMobil 2:");
        mobil2.tampilkanData();
        System.out.println("Umur Mobil: " + mobil2.umurMobil() + " tahun");
    }
}
