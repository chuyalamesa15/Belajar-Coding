// Class Main
public class Main {
    public static void main(String[] args) {

        // Object Constructor 1
        Cafe cafe1 = new Cafe();

        System.out.println();

        // Object Constructor 2
        Cafe cafe2 = new Cafe("CF001", "Cafe Senja");
        cafe2.alamat = "Samarinda";
        cafe2.jumlahMenu = 15;

        cafe2.tampilData();
        System.out.println("Status : " + cafe2.statusCafe());

        System.out.println();

        // Object Constructor 3
        Cafe cafe3 = new Cafe("CF002", "Cafe Malam",
                "Balikpapan", 30);

        cafe3.tampilData();
        System.out.println("Status : " + cafe3.statusCafe());

        System.out.println();

        // Menjalankan CRUD
        cafe3.create();
        cafe3.read();
        cafe3.update();
        cafe3.delete();
    }
}