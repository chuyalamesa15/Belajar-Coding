package coffeeshop.model;

public class Menu {
    private int     idMenu;
    private String  kodeMenu;
    private String  namaMenu;
    private int     idKategori;
    private String  namaKategori;   // join dari tbl_kategori
    private double  harga;
    private int     stok;
    private String  deskripsi;
    private boolean tersedia;

    public Menu() {}

    public Menu(int idMenu, String kodeMenu, String namaMenu,
                int idKategori, double harga, int stok,
                String deskripsi, boolean tersedia) {
        this.idMenu     = idMenu;
        this.kodeMenu   = kodeMenu;
        this.namaMenu   = namaMenu;
        this.idKategori = idKategori;
        this.harga      = harga;
        this.stok       = stok;
        this.deskripsi  = deskripsi;
        this.tersedia   = tersedia;
    }

    // Getters & Setters
    public int    getIdMenu()      { return idMenu; }
    public void   setIdMenu(int v) { this.idMenu = v; }

    public String getKodeMenu()         { return kodeMenu; }
    public void   setKodeMenu(String v) { this.kodeMenu = v; }

    public String getNamaMenu()         { return namaMenu; }
    public void   setNamaMenu(String v) { this.namaMenu = v; }

    public int  getIdKategori()      { return idKategori; }
    public void setIdKategori(int v) { this.idKategori = v; }

    public String getNamaKategori()         { return namaKategori; }
    public void   setNamaKategori(String v) { this.namaKategori = v; }

    public double getHarga()         { return harga; }
    public void   setHarga(double v) { this.harga = v; }

    public int  getStok()      { return stok; }
    public void setStok(int v) { this.stok = v; }

    public String getDeskripsi()         { return deskripsi; }
    public void   setDeskripsi(String v) { this.deskripsi = v; }

    public boolean isTersedia()         { return tersedia; }
    public void    setTersedia(boolean v) { this.tersedia = v; }

    @Override
    public String toString() { return kodeMenu + " - " + namaMenu; }
}