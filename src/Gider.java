// Gider.java
// Gider kayıtlarını tutar.
import java.time.LocalDate;

public class Gider {
    private String aciklama;
    private double miktar;
    private LocalDate tarih;
    private GiderTuru tur;

    public Gider(String aciklama, double miktar, LocalDate tarih, GiderTuru tur) {
        this.aciklama = aciklama;
        this.miktar = miktar;
        this.tarih = tarih;
        this.tur = tur;
    }

    // Getter metodları
    public String getAciklama() { return aciklama; }
    public double getMiktar() { return miktar; }
    public LocalDate getTarih() { return tarih; }
    public GiderTuru getTur() { return tur; }

    // Dosya kaydı için string dönüşümü
    public String toString() {
        return aciklama + "," + miktar + "," + tarih + "," + tur;
    }
} 