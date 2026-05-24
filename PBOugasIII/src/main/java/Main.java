import cafe.CafeMenu;

public class Main {

    public static void main(String[] args) {

        // Object Cafe
        Cafe cf = new Cafe(
                "Senja Coffee",
                "Samarinda",
                4.8,
                25
        );

        // Tampilkan data cafe
        System.out.println("=== DATA CAFE ===");
        cf.tampilData();

        System.out.println();

        // Object MenuCafe
        CafeMenu menu = new CafeMenu(
                "CF01",
                "Es Kopi Susu"
        );

        // Tampilkan data menu
        System.out.println("=== MENU CAFE ===");
        menu.tampilData();
    }
}