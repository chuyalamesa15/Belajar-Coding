/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mtuma
 */
public class Mobil {
    // atribut
    String merk;
    int tahun;

    // constructor tanpa parameter
    public Mobil() {
        merk = "BMW E36";
        tahun = 1990;
    }

    // constructor dengan parameter
    public Mobil(String merk, int tahun) {
        this.merk = merk;
        this.tahun = tahun;
    }

    // method tanpa nilai balik (void)
    public void tampilkanData() {
        System.out.println("Merk Mobil: " + merk);
        System.out.println("Tahun: " + tahun);
    }

    // method dengan nilai balik
    public int umurMobil() {
        int tahunSekarang = 2025;
        return tahunSekarang - tahun;
    }
}
