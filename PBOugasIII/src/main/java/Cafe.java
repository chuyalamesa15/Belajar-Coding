public class Cafe {

    String namaCafe;
    String alamat;
    double rating;
    int jumlahMenu;

    // Constructor
    public Cafe(String namaCafe, String alamat, double rating, int jumlahMenu) {
        this.namaCafe = namaCafe;
        this.alamat = alamat;
        this.rating = rating;
        this.jumlahMenu = jumlahMenu;
    }

    // Method tampil data
    void tampilData() {
        System.out.println("Nama Cafe    : " + namaCafe);
        System.out.println("Alamat       : " + alamat);
        System.out.println("Rating       : " + rating);
        System.out.println("Jumlah Menu  : " + jumlahMenu);
    }
}