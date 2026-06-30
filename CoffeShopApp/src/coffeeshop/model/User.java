package coffeeshop.model;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String namaLengkap;
    private String role;

    public User() {}

    public User(int idUser, String username, String password, String namaLengkap, String role) {
        this.idUser      = idUser;
        this.username    = username;
        this.password    = password;
        this.namaLengkap = namaLengkap;
        this.role        = role;
    }

    // Getters & Setters
    public int    getIdUser()      { return idUser; }
    public void   setIdUser(int v) { this.idUser = v; }

    public String getUsername()         { return username; }
    public void   setUsername(String v) { this.username = v; }

    public String getPassword()         { return password; }
    public void   setPassword(String v) { this.password = v; }

    public String getNamaLengkap()         { return namaLengkap; }
    public void   setNamaLengkap(String v) { this.namaLengkap = v; }

    public String getRole()         { return role; }
    public void   setRole(String v) { this.role = v; }

    @Override
    public String toString() { return namaLengkap + " (" + role + ")"; }
}