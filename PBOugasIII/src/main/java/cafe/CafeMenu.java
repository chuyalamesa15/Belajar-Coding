package cafe;

public class CafeMenu {

    String kodeMenu;
    String namaMenu;

    // Constructor
    public CafeMenu(String kodeMenu, String namaMenu) {
        this.kodeMenu = kodeMenu;
        this.namaMenu = namaMenu;
    }

    // Method tampil data
    public void tampilData() {
        System.out.println("Kode Menu : " + kodeMenu);
        System.out.println("Nama Menu : " + namaMenu);
    }
}