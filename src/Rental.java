// Rental.java
// Kiralama işlemlerini ve ilişkili bilgileri tutar.
import java.time.LocalDate;

public class Rental {
    private Car araba;
    private Customer musteri;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private double depozito;
    private String kiralamaNotu;
    private Durum durum;
    private double toplamUcret;
    private String odemeYontemi;

    // Yapıcı metod
    public Rental(Car araba, Customer musteri, LocalDate baslangicTarihi, LocalDate bitisTarihi, double depozito, String kiralamaNotu, String odemeYontemi) {
        this.araba = araba;
        this.musteri = musteri;
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.depozito = depozito;
        this.kiralamaNotu = kiralamaNotu;
        this.durum = Durum.AKTIF;
        this.toplamUcret = 0;
        this.odemeYontemi = odemeYontemi;
    }

    // Getter ve setter metodları
    public Car getAraba() { return araba; }
    public Customer getMusteri() { return musteri; }
    public LocalDate getBaslangicTarihi() { return baslangicTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public double getDepozito() { return depozito; }
    public String getKiralamaNotu() { return kiralamaNotu; }
    public Durum getDurum() { return durum; }
    public double getToplamUcret() { return toplamUcret; }
    public String getOdemeYontemi() { return odemeYontemi; }

    // Kiralama işlemini tamamlar
    public void tamamla(double toplamUcret) {
        this.toplamUcret = toplamUcret;
        this.durum = Durum.TAMAMLANDI;
    }

    // Kiralamayı string olarak döndürür (dosya kaydı için)
    public String toString() {
        return araba.getPlaka() + "," + musteri.getTcKimlikNo() + "," + baslangicTarihi + "," + bitisTarihi + "," + depozito + "," + kiralamaNotu + "," + durum + "," + toplamUcret + "," + odemeYontemi;
    }
} 