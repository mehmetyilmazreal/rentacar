// Reservation.java
// Rezervasyon işlemlerini ve ilişkili bilgileri tutar.
import java.time.LocalDate;

public class Reservation {
    private Car araba;
    private Customer musteri;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private double depozito;
    private String rezervasyonNotu;
    private Durum durum;
    private String odemeYontemi;

    // Yapıcı metod
    public Reservation(Car araba, Customer musteri, LocalDate baslangicTarihi, LocalDate bitisTarihi, double depozito, String rezervasyonNotu, String odemeYontemi) {
        this.araba = araba;
        this.musteri = musteri;
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.depozito = depozito;
        this.rezervasyonNotu = rezervasyonNotu;
        this.durum = Durum.AKTIF;
        this.odemeYontemi = odemeYontemi;
    }

    // Getter ve setter metodları
    public Car getAraba() { return araba; }
    public Customer getMusteri() { return musteri; }
    public LocalDate getBaslangicTarihi() { return baslangicTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public double getDepozito() { return depozito; }
    public String getRezervasyonNotu() { return rezervasyonNotu; }
    public Durum getDurum() { return durum; }
    public String getOdemeYontemi() { return odemeYontemi; }

    // Rezervasyonu iptal eder
    public void iptalEt() {
        this.durum = Durum.IPTAL_EDILDI;
    }

    // Rezervasyonu kiralamaya dönüştürür
    public Rental kiralamayaDonustur() {
        return new Rental(araba, musteri, baslangicTarihi, bitisTarihi, depozito, rezervasyonNotu, odemeYontemi);
    }

    // Rezervasyonu string olarak döndürür (dosya kaydı için)
    public String toString() {
        return araba.getPlaka() + "," + musteri.getTcKimlikNo() + "," + baslangicTarihi + "," + bitisTarihi + "," + depozito + "," + rezervasyonNotu + "," + durum + "," + odemeYontemi;
    }
} 