// Customer.java
// Müşteri bilgilerini tutar.
import java.time.LocalDate;

public class Customer {
    private String ad;
    private String soyad;
    private String tcKimlikNo;
    private String telefon;
    private String email;
    private String adres;
    private String ehliyetNo;
    private String ehliyetSinifi;
    private LocalDate dogumTarihi;
    private double toplamHarcamasi;
    private boolean sadikMusteri;
    private String sifre;

    // Yapıcı metod
    public Customer(String ad, String soyad, String tcKimlikNo, String telefon, String email, String adres, String ehliyetNo, String ehliyetSinifi, LocalDate dogumTarihi, String sifre) {
        this.ad = ad;
        this.soyad = soyad;
        this.tcKimlikNo = tcKimlikNo;
        this.telefon = telefon;
        this.email = email;
        this.adres = adres;
        this.ehliyetNo = ehliyetNo;
        this.ehliyetSinifi = ehliyetSinifi;
        this.dogumTarihi = dogumTarihi;
        this.sifre = sifre;
        this.toplamHarcamasi = 0;
        this.sadikMusteri = false;
    }

    // Getter ve setter metodları
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getTcKimlikNo() { return tcKimlikNo; }
    public String getTelefon() { return telefon; }
    public String getEmail() { return email; }
    public String getAdres() { return adres; }
    public String getEhliyetNo() { return ehliyetNo; }
    public String getEhliyetSinifi() { return ehliyetSinifi; }
    public LocalDate getDogumTarihi() { return dogumTarihi; }
    public double getToplamHarcamasi() { return toplamHarcamasi; }
    public boolean isSadikMusteri() { return sadikMusteri; }
    public String getSifre() { return sifre; }

    // Harcama ekler ve sadık müşteri durumunu günceller
    public void harcamaEkle(double tutar) {
        this.toplamHarcamasi += tutar;
        if (this.toplamHarcamasi > 10000) this.sadikMusteri = true;
    }

    // Müşteriyi string olarak döndürür (dosya kaydı için)
    public String toString() {
        return ad + "," + soyad + "," + tcKimlikNo + "," + telefon + "," + email + "," + adres + "," + ehliyetNo + "," + ehliyetSinifi + "," + dogumTarihi + "," + sifre + "," + toplamHarcamasi + "," + sadikMusteri;
    }
} 