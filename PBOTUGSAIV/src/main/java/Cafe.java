// Class Cafe
public class Cafe {

    // Atribut
    String kodeCafe;
    String namaCafe;
    String alamat;
    int jumlahMenu;

    // Constructor 1
    public Cafe() {
        System.out.println("Constructor tanpa parameter");
    }

    // Constructor 2
    public Cafe(String kodeCafe, String namaCafe) {
        this.kodeCafe = kodeCafe;
        this.namaCafe = namaCafe;
    }

    // Constructor 3
    public Cafe(String kodeCafe, String namaCafe, String alamat, int jumlahMenu) {
        this.kodeCafe = kodeCafe;
        this.namaCafe = namaCafe;
        this.alamat = alamat;
        this.jumlahMenu = jumlahMenu;
    }

    // Method tanpa nilai balik
    public void tampilData() {
        System.out.println("Kode Cafe : " + kodeCafe);
        System.out.println("Nama Cafe : " + namaCafe);
        System.out.println("Alamat    : " + alamat);
        System.out.println("Jumlah Menu : " + jumlahMenu);
    }

    // Method dengan nilai balik
    public String statusCafe() {
        if (jumlahMenu > 20) {
            return "Cafe Lengkap";
        } else {
            return "Cafe Sederhana";
        }
    }

    // CREATE
    public void create() {
        String sql = "INSERT INTO cafe VALUES ('"
                + kodeCafe + "','"
                + namaCafe + "','"
                + alamat + "',"
                + jumlahMenu + ")";
        System.out.println("SQL CREATE : " + sql);
    }

    // READ
    public void read() {
        String sql = "SELECT * FROM cafe";
        System.out.println("SQL READ : " + sql);
    }

    // UPDATE
    public void update() {
        String sql = "UPDATE cafe SET "
                + "namaCafe='" + namaCafe + "', "
                + "alamat='" + alamat + "', "
                + "jumlahMenu=" + jumlahMenu
                + " WHERE kodeCafe='" + kodeCafe + "'";
        System.out.println("SQL UPDATE : " + sql);
    }

    // DELETE
    public void delete() {
        String sql = "DELETE FROM cafe WHERE kodeCafe='" + kodeCafe + "'";
        System.out.println("SQL DELETE : " + sql);
    }
}