/*
 
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template*/
package Akademis;

/**
 *
 
@author HP*/
public class MataKuliah {
    // Atribut
    public String kode_mk;
    public String nama_mk;

    // Constructor
    public MataKuliah(String kode_mk, String nama_mk) {
        this.kode_mk = kode_mk;
        this.nama_mk = nama_mk;
    }

    // Method untuk menampilkan info
    public void cetakInfoMK() {
        System.out.println("Kode MK        : " + kode_mk);
        System.out.println("Nama MK        : " + nama_mk);
    }
}