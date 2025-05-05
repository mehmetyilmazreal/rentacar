// DestekEkibi.java
// Destek ekibi üyelerini ve görevlerini yönetir.
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DestekEkibi {
    private String id;
    private String ad;
    private String soyad;
    private String telefon;
    private String email;
    private String departman;
    private String sifre;
    private List<String> gorevler;
    private Map<String, DestekSohbeti> aktifSohbetler;

    public DestekEkibi(String id, String ad, String soyad, String telefon, String email, String departman, String sifre) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.telefon = telefon;
        this.email = email;
        this.departman = departman;
        this.sifre = sifre;
        this.gorevler = new ArrayList<>();
        this.aktifSohbetler = new HashMap<>();
    }

    // Getter metodları
    public String getId() { return id; }
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getTelefon() { return telefon; }
    public String getEmail() { return email; }
    public String getDepartman() { return departman; }
    public String getSifre() { return sifre; }
    public List<String> getGorevler() { return gorevler; }

    // Şifre kontrolü
    public boolean sifreKontrol(String sifre) {
        return this.sifre.equals(sifre);
    }

    // Görev ekleme
    public void gorevEkle(String gorev) {
        gorevler.add(gorev);
    }

    // Görev tamamlandı olarak işaretleme
    public void gorevTamamla(String gorev) {
        gorevler.remove(gorev);
    }

    // Yeni sohbet başlat
    public DestekSohbeti yeniSohbetBaslat(String musteriId) {
        DestekSohbeti yeniSohbet = new DestekSohbeti(musteriId, this.id);
        aktifSohbetler.put(musteriId, yeniSohbet);
        return yeniSohbet;
    }

    // Sohbeti sonlandır
    public void sohbetiSonlandir(String musteriId) {
        DestekSohbeti sohbet = aktifSohbetler.get(musteriId);
        if (sohbet != null) {
            sohbet.sohbetiSonlandir();
            aktifSohbetler.remove(musteriId);
        }
    }

    // Aktif sohbetleri getir
    public List<DestekSohbeti> getAktifSohbetler() {
        return new ArrayList<>(aktifSohbetler.values());
    }

    // Belirli bir müşteri ile olan sohbeti getir
    public DestekSohbeti getSohbet(String musteriId) {
        return aktifSohbetler.get(musteriId);
    }

    @Override
    public String toString() {
        return "DestekEkibi{" +
                "id='" + id + '\'' +
                ", ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", departman='" + departman + '\'' +
                ", gorevler=" + gorevler +
                '}';
    }
} 