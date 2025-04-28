// RentACarSystem.java
// Sistemin ana işleyişini ve veri yönetimini sağlar.
import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class RentACarSystem {
    private List<Car> arabalar;
    private List<Customer> musteriler;
    private List<Rental> kiralamalar;
    public static final String MUSTERI_DOSYA = "musteriler.txt";
    public static final String ARABA_DOSYA = "arabalar.txt";

    public RentACarSystem() {
        arabalar = new ArrayList<>();
        musteriler = new ArrayList<>();
        kiralamalar = new ArrayList<>();
        // Verileri dosyadan yükle
        // arabalar = dosyadanArabalarYukle("arabalar.txt");
        // musteriler = dosyadanMusterilerYukle("musteriler.txt");
        // kiralamalar = dosyadanKiralamalarYukle("kiralamalar.txt");
    }

    // Arabaları ekler
    public void arabaEkle(Car araba) {
        arabalar.add(araba);
        arabayiDosyayaYaz(araba);
    }

    // Dosyaya araç ekler
    private void arabayiDosyayaYaz(Car araba) {
        try (java.io.FileWriter fw = new java.io.FileWriter(ARABA_DOSYA, true)) {
            fw.write(araba.toString() + "\n");
        } catch (Exception e) {
            System.out.println("Araç dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Dosyadan tüm araçları yükler
    public void arabalarDosyadanYukle() {
        arabalar.clear();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(ARABA_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                if (d.length >= 11) {
                    Car c = new Car(
                        d[0], d[1], Integer.parseInt(d[2]), d[3], Double.parseDouble(d[4]),
                        d[5], d[6], d[7], Integer.parseInt(d[8]), Boolean.parseBoolean(d[9]), Boolean.parseBoolean(d[10])
                    );
                    arabalar.add(c);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            // Dosya yoksa sorun değil, ilk kayıt yapılacak demektir
        } catch (Exception e) {
            System.out.println("Araç dosyasından okuma hatası: " + e.getMessage());
        }
    }

    // Müşteri ekler
    public void musteriEkle(Customer musteri) {
        musteriler.add(musteri);
        musteriyiDosyayaYaz(musteri);
    }

    // Dosyaya müşteri ekler
    private void musteriyiDosyayaYaz(Customer musteri) {
        try (java.io.FileWriter fw = new java.io.FileWriter(MUSTERI_DOSYA, true)) {
            fw.write(musteri.toString() + "\n");
        } catch (Exception e) {
            System.out.println("Müşteri dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Dosyadan tüm müşterileri yükler
    public void musterileriDosyadanYukle() {
        musteriler.clear();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(MUSTERI_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                if (d.length >= 12) {
                    Customer c = new Customer(
                        d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7],
                        java.time.LocalDate.parse(d[8]), d[9]
                    );
                    c.harcamaEkle(Double.parseDouble(d[10]));
                    if (Boolean.parseBoolean(d[11])) c.harcamaEkle(10001); // sadık müşteri işareti
                    musteriler.add(c);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            // Dosya yoksa sorun değil, ilk kayıt yapılacak demektir
        } catch (Exception e) {
            System.out.println("Müşteri dosyasından okuma hatası: " + e.getMessage());
        }
    }

    // Kiralama ekler
    public Rental arabaKirala(Car araba, Customer musteri, LocalDate baslangic, LocalDate bitis, double depozito, String not, String odemeYontemi) {
        Rental rental = new Rental(araba, musteri, baslangic, bitis, depozito, not, odemeYontemi);
        kiralamalar.add(rental);
        // kiralamalarDosyayaKaydet();
        return rental;
    }

    // Arabaları listeler
    public List<Car> getArabalar() {
        return arabalar;
    }

    // Müşterileri listeler
    public List<Customer> getMusteriler() {
        return musteriler;
    }

    // Kiralamaları listeler
    public List<Rental> getKiralamalar() {
        return kiralamalar;
    }

    public void arabaSil(Car araba) {
        arabalar.remove(araba);
        arabalarDosyayaYazTumListe();
    }

    // Tüm arabalar listesini dosyaya yazar (silme için)
    private void arabalarDosyayaYazTumListe() {
        try (java.io.FileWriter fw = new java.io.FileWriter(ARABA_DOSYA, false)) {
            for (Car c : arabalar) {
                fw.write(c.toString() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Araç dosyasına yazılamadı: " + e.getMessage());
        }
    }
} 