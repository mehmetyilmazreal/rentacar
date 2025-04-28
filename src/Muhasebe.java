// Muhasebe.java
// Muhasebe işlemlerini yönetir.
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Muhasebe {
    private List<Gelir> gelirler;
    private List<Gider> giderler;
    private double toplamGelir;
    private double toplamGider;
    private static final String GELIR_DOSYA = "gelirler.txt";
    private static final String GIDER_DOSYA = "giderler.txt";

    public Muhasebe() {
        gelirler = new ArrayList<>();
        giderler = new ArrayList<>();
        toplamGelir = 0;
        toplamGider = 0;
        // Verileri dosyadan yükle
        // gelirler = dosyadanGelirlerYukle("gelirler.txt");
        // giderler = dosyadanGiderlerYukle("giderler.txt");
    }

    // Gelir ekler
    public void gelirEkle(Gelir gelir) {
        gelirler.add(gelir);
        toplamGelir += gelir.getMiktar();
        geliriDosyayaYaz(gelir);
    }

    // Gider ekler
    public void giderEkle(Gider gider) {
        giderler.add(gider);
        toplamGider += gider.getMiktar();
        gideriDosyayaYaz(gider);
    }

    // Dosyaya gelir ekler
    private void geliriDosyayaYaz(Gelir gelir) {
        try (java.io.FileWriter fw = new java.io.FileWriter(GELIR_DOSYA, true)) {
            fw.write(gelir.toString() + "\n");
        } catch (Exception e) {
            System.out.println("Gelir dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Dosyaya gider ekler
    private void gideriDosyayaYaz(Gider gider) {
        try (java.io.FileWriter fw = new java.io.FileWriter(GIDER_DOSYA, true)) {
            fw.write(gider.toString() + "\n");
        } catch (Exception e) {
            System.out.println("Gider dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Belirli bir tarih aralığındaki gelirleri getirir
    public List<Gelir> tarihAraligindakiGelirler(LocalDate baslangic, LocalDate bitis) {
        List<Gelir> sonuc = new ArrayList<>();
        for (Gelir gelir : gelirler) {
            if (!gelir.getTarih().isBefore(baslangic) && !gelir.getTarih().isAfter(bitis)) {
                sonuc.add(gelir);
            }
        }
        return sonuc;
    }

    // Belirli bir tarih aralığındaki giderleri getirir
    public List<Gider> tarihAraligindakiGiderler(LocalDate baslangic, LocalDate bitis) {
        List<Gider> sonuc = new ArrayList<>();
        for (Gider gider : giderler) {
            if (!gider.getTarih().isBefore(baslangic) && !gider.getTarih().isAfter(bitis)) {
                sonuc.add(gider);
            }
        }
        return sonuc;
    }

    // Net karı hesaplar
    public double netKarHesapla() {
        return toplamGelir - toplamGider;
    }

    // Getter metodları
    public List<Gelir> getGelirler() { return gelirler; }
    public List<Gider> getGiderler() { return giderler; }
    public double getToplamGelir() { return toplamGelir; }
    public double getToplamGider() { return toplamGider; }
} 