// DestekEkibi.java
// Destek ekibi üyelerini ve görevlerini yönetir.
import java.util.ArrayList;
import java.util.List;

public class DestekEkibi {
    private String ad;
    private String soyad;
    private String telefon;
    private String email;
    private String departman;
    private List<String> gorevler;

    public DestekEkibi(String ad, String soyad, String telefon, String email, String departman) {
        this.ad = ad;
        this.soyad = soyad;
        this.telefon = telefon;
        this.email = email;
        this.departman = departman;
        this.gorevler = new ArrayList<>();
    }

    // Getter metodları
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getTelefon() { return telefon; }
    public String getEmail() { return email; }
    public String getDepartman() { return departman; }
    public List<String> getGorevler() { return gorevler; }

    // Görev ekleme
    public void gorevEkle(String gorev) {
        gorevler.add(gorev);
    }

    // Görev tamamlandı olarak işaretleme
    public void gorevTamamla(String gorev) {
        gorevler.remove(gorev);
    }

    @Override
    public String toString() {
        return "DestekEkibi{" +
                "ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", departman='" + departman + '\'' +
                ", gorevler=" + gorevler +
                '}';
    }
} 