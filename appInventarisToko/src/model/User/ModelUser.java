/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.User;

public class ModelUser {
    private Integer id;
    private String  username;
    private String  password;
    private String  role;         // "admin" | "kasir" | "gudang"
    private String  namaLengkap;
    private boolean aktif;

    public ModelUser() {}

    public ModelUser(String username, String password, String role, String namaLengkap) {
        this.username    = username;
        this.password    = password;
        this.role        = role;
        this.namaLengkap = namaLengkap;
        this.aktif       = true;
    }

    public Integer getId()                  { return id; }
    public void    setId(Integer id)        { this.id = id; }

    public String  getUsername()                { return username; }
    public void    setUsername(String u)        { this.username = u; }

    public String  getPassword()                { return password; }
    public void    setPassword(String p)        { this.password = p; }

    public String  getRole()                    { return role; }
    public void    setRole(String r)            { this.role = r; }

    public String  getNamaLengkap()             { return namaLengkap; }
    public void    setNamaLengkap(String n)     { this.namaLengkap = n; }

    public boolean isAktif()                    { return aktif; }
    public void    setAktif(boolean a)          { this.aktif = a; }

    public boolean isAdmin()   { return "admin".equals(role); }
    public boolean isKasir()   { return "kasir".equals(role); }
    public boolean isGudang()  { return "gudang".equals(role); }

    @Override
    public String toString() {
        return namaLengkap + " [" + role + "]";
    }
}
