// Gelir.java
// Gelir kayıtlarını tutar.
import java.time.LocalDate;

public class Gelir {
    private String aciklama;
    private double miktar;
    private LocalDate tarih;
    private GelirTuru tur;

    public Gelir(String aciklama, double miktar, LocalDate tarih, GelirTuru tur) {
        this.aciklama = aciklama;
        this.miktar = miktar;
        this.tarih = tarih;
        this.tur = tur;
    }

    // Getter metodları
    public String getAciklama() { return aciklama; }
    public double getMiktar() { return miktar; }
    public LocalDate getTarih() { return tarih; }
    public GelirTuru getTuru() { return tur; }

    // Dosya kaydı için string dönüşümü
    @Override
    public String toString() {
        return "Gelir{" +
                "aciklama='" + aciklama + '\'' +
                ", miktar=" + miktar +
                ", tarih=" + tarih +
                ", tur=" + tur +
                '}';
    }
} 