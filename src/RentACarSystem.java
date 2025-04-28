// RentACarSystem.java
// Sistemin ana işleyişini ve veri yönetimini sağlar.
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentACarSystem {
    private List<Car> arabalar;
    private List<Customer> musteriler;
    private List<Rental> kiralamalar;
    private List<Reservation> rezervasyonlar;
    private Muhasebe muhasebe;
    private List<DestekEkibi> destekEkibi;
    
    // KDV oranları
    private static final double KDV_ORANI = 0.20; // %20 KDV
    private static final double KREDI_KARTI_KDV_ORANI = 0.20; // %20 KDV (kredi kartı)
    private static final double BANK_KARTI_KDV_ORANI = 0.20; // %20 KDV (banka kartı)
    
    public static final String MUSTERI_DOSYA = "musteriler.txt";
    public static final String ARABA_DOSYA = "arabalar.txt";
    public static final String REZERVASYON_DOSYA = "rezervasyonlar.txt";

    public RentACarSystem() {
        arabalar = new ArrayList<>();
        musteriler = new ArrayList<>();
        kiralamalar = new ArrayList<>();
        rezervasyonlar = new ArrayList<>();
        muhasebe = new Muhasebe();
        destekEkibi = new ArrayList<>();
        // Verileri dosyadan yükle
        // arabalar = dosyadanArabalarYukle("arabalar.txt");
        // musteriler = dosyadanMusterilerYukle("musteriler.txt");
        // kiralamalar = dosyadanKiralamalarYukle("kiralamalar.txt");
        // rezervasyonlar = dosyadanRezervasyonlarYukle("rezervasyonlar.txt");
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

    // KDV hesaplama yardımcı metodu
    private double kdvHesapla(double tutar, String odemeYontemi) {
        double kdvOrani;
        switch (odemeYontemi.toUpperCase()) {
            case "KREDI_KARTI":
                kdvOrani = KREDI_KARTI_KDV_ORANI;
                break;
            case "BANK_KARTI":
                kdvOrani = BANK_KARTI_KDV_ORANI;
                break;
            default:
                kdvOrani = KDV_ORANI;
        }
        return tutar * kdvOrani;
    }

    // Kiralama ekler ve muhasebe kaydı oluşturur
    public Rental arabaKirala(Car araba, Customer musteri, LocalDate baslangic, LocalDate bitis, double depozito, String not, String odemeYontemi) {
        Rental rental = new Rental(araba, musteri, baslangic, bitis, depozito, not, odemeYontemi);
        kiralamalar.add(rental);
        
        // Kiralama süresini hesapla
        long gunSayisi = ChronoUnit.DAYS.between(baslangic, bitis) + 1;
        
        // Kiralama ücretini hesapla
        double kiralamaUcreti = araba.getGunlukFiyat() * gunSayisi;
        double kiralamaKDV = kdvHesapla(kiralamaUcreti, odemeYontemi);
        double toplamKiralama = kiralamaUcreti + kiralamaKDV;
        
        // Kiralama geliri ekle
        muhasebe.gelirEkle(new Gelir(
            araba.getMarka() + " " + araba.getModel() + " kiralama geliri (" + gunSayisi + " gün) - KDV: " + kiralamaKDV + " TL",
            toplamKiralama,
            LocalDate.now(),
            GelirTuru.KIRALAMA
        ));
        
        // Depozito geliri ekle (KDV'siz)
        muhasebe.gelirEkle(new Gelir(
            araba.getMarka() + " " + araba.getModel() + " depozito",
            depozito,
            LocalDate.now(),
            GelirTuru.DEPOZITO
        ));
        
        // Müşterinin toplam harcamasını güncelle
        musteri.harcamaEkle(toplamKiralama + depozito);
        
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

    // Rezervasyon ekler ve muhasebe kaydı oluşturur
    public Reservation rezervasyonYap(Car araba, Customer musteri, LocalDate baslangic, LocalDate bitis, double depozito, String not, String odemeYontemi) {
        Reservation rezervasyon = new Reservation(araba, musteri, baslangic, bitis, depozito, not, odemeYontemi);
        rezervasyonlar.add(rezervasyon);
        rezervasyonuDosyayaYaz(rezervasyon);
        
        // Rezervasyon depozitosu için KDV hesapla
        double depozitoKDV = kdvHesapla(depozito, odemeYontemi);
        double toplamDepozito = depozito + depozitoKDV;
        
        // Rezervasyon geliri ekle
        muhasebe.gelirEkle(new Gelir(
            araba.getMarka() + " " + araba.getModel() + " rezervasyon geliri - KDV: " + depozitoKDV + " TL",
            toplamDepozito,
            LocalDate.now(),
            GelirTuru.REZERVASYON
        ));
        
        // Müşterinin toplam harcamasını güncelle
        musteri.harcamaEkle(toplamDepozito);
        
        return rezervasyon;
    }

    // Dosyaya rezervasyon ekler
    private void rezervasyonuDosyayaYaz(Reservation rezervasyon) {
        try (java.io.FileWriter fw = new java.io.FileWriter(REZERVASYON_DOSYA, true)) {
            fw.write(rezervasyon.toString() + "\n");
        } catch (Exception e) {
            System.out.println("Rezervasyon dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Dosyadan tüm rezervasyonları yükler
    public void rezervasyonlariDosyadanYukle() {
        rezervasyonlar.clear();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(REZERVASYON_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] d = satir.split(",");
                if (d.length >= 8) {
                    Car araba = arabalar.stream()
                        .filter(a -> a.getPlaka().equals(d[0]))
                        .findFirst()
                        .orElse(null);
                    
                    Customer musteri = musteriler.stream()
                        .filter(m -> m.getTcKimlikNo().equals(d[1]))
                        .findFirst()
                        .orElse(null);

                    if (araba != null && musteri != null) {
                        Reservation r = new Reservation(
                            araba, musteri, 
                            LocalDate.parse(d[2]), 
                            LocalDate.parse(d[3]), 
                            Double.parseDouble(d[4]), 
                            d[5], 
                            d[7]
                        );
                        rezervasyonlar.add(r);
                    }
                }
            }
        } catch (java.io.FileNotFoundException e) {
            // Dosya yoksa sorun değil, ilk kayıt yapılacak demektir
        } catch (Exception e) {
            System.out.println("Rezervasyon dosyasından okuma hatası: " + e.getMessage());
        }
    }

    // Rezervasyonları listeler
    public List<Reservation> getRezervasyonlar() {
        return rezervasyonlar;
    }

    // Rezervasyonu iptal eder
    public void rezervasyonIptalEt(Reservation rezervasyon) {
        rezervasyon.iptalEt();
        rezervasyonlariDosyayaYazTumListe();
    }

    // Rezervasyonu kiralamaya dönüştürür
    public Rental rezervasyonuKiralamayaDonustur(Reservation rezervasyon) {
        Rental rental = rezervasyon.kiralamayaDonustur();
        kiralamalar.add(rental);
        rezervasyonlar.remove(rezervasyon);
        rezervasyonlariDosyayaYazTumListe();
        return rental;
    }

    // Tüm rezervasyonlar listesini dosyaya yazar
    private void rezervasyonlariDosyayaYazTumListe() {
        try (java.io.FileWriter fw = new java.io.FileWriter(REZERVASYON_DOSYA, false)) {
            for (Reservation r : rezervasyonlar) {
                fw.write(r.toString() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Rezervasyon dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Belirli bir tarih aralığında müsait olan araçları döndürür
    public List<Car> müsaitArabalariGetir(LocalDate baslangicTarihi, LocalDate bitisTarihi) {
        List<Car> müsaitArabalar = new ArrayList<>();
        
        for (Car araba : arabalar) {
            if (arabaMüsaitMi(araba, baslangicTarihi, bitisTarihi)) {
                müsaitArabalar.add(araba);
            }
        }
        
        return müsaitArabalar;
    }

    // Bir aracın belirli bir tarih aralığında müsait olup olmadığını kontrol eder
    private boolean arabaMüsaitMi(Car araba, LocalDate baslangicTarihi, LocalDate bitisTarihi) {
        // Aktif kiralamaları kontrol et
        for (Rental kiralama : kiralamalar) {
            if (kiralama.getAraba().equals(araba) && kiralama.getDurum() == Durum.AKTIF) {
                if (tarihlerCakismiyorMu(kiralama.getBaslangicTarihi(), kiralama.getBitisTarihi(), 
                                       baslangicTarihi, bitisTarihi)) {
                    return false;
                }
            }
        }

        // Aktif rezervasyonları kontrol et
        for (Reservation rezervasyon : rezervasyonlar) {
            if (rezervasyon.getAraba().equals(araba) && rezervasyon.getDurum() == Durum.AKTIF) {
                if (tarihlerCakismiyorMu(rezervasyon.getBaslangicTarihi(), rezervasyon.getBitisTarihi(), 
                                       baslangicTarihi, bitisTarihi)) {
                    return false;
                }
            }
        }

        return true;
    }

    // İki tarih aralığının çakışıp çakışmadığını kontrol eder
    private boolean tarihlerCakismiyorMu(LocalDate kiralamaBaslangic, LocalDate kiralamaBitis,
                                       LocalDate yeniBaslangic, LocalDate yeniBitis) {
        return !(yeniBitis.isBefore(kiralamaBaslangic) || yeniBaslangic.isAfter(kiralamaBitis));
    }

    // Bir aracın belirli bir tarih aralığındaki müsaitlik durumunu kontrol eder
    public boolean arabaMüsaitlikKontrolü(Car araba, LocalDate baslangicTarihi, LocalDate bitisTarihi) {
        return arabaMüsaitMi(araba, baslangicTarihi, bitisTarihi);
    }

    // Gider ekler
    public void giderEkle(String aciklama, double miktar, GiderTuru tur) {
        muhasebe.giderEkle(new Gider(aciklama, miktar, LocalDate.now(), tur));
    }

    // Muhasebe raporu oluşturur
    public void muhasebeRaporuOlustur(LocalDate baslangic, LocalDate bitis) {
        System.out.println("\n--- Muhasebe Raporu (" + baslangic + " - " + bitis + ") ---");
        
        // Gelirler
        System.out.println("\nGelirler:");
        List<Gelir> gelirler = muhasebe.tarihAraligindakiGelirler(baslangic, bitis);
        
        double toplamKiralamaGeliri = 0;
        double toplamKiralamaKDV = 0;
        double toplamDepozitoGeliri = 0;
        double toplamRezervasyonGeliri = 0;
        double toplamRezervasyonKDV = 0;
        
        for (Gelir gelir : gelirler) {
            System.out.println(gelir.getTarih() + " - " + gelir.getAciklama() + ": " + gelir.getMiktar() + " TL");
            
            // Gelir türüne göre toplamları hesapla
            if (gelir.getTuru() == GelirTuru.KIRALAMA) {
                toplamKiralamaGeliri += gelir.getMiktar();
                // KDV tutarını açıklamadan çıkar
                String[] parts = gelir.getAciklama().split("KDV: ");
                if (parts.length > 1) {
                    String kdvStr = parts[1].replace(" TL", "");
                    toplamKiralamaKDV += Double.parseDouble(kdvStr);
                }
            } else if (gelir.getTuru() == GelirTuru.DEPOZITO) {
                toplamDepozitoGeliri += gelir.getMiktar();
            } else if (gelir.getTuru() == GelirTuru.REZERVASYON) {
                toplamRezervasyonGeliri += gelir.getMiktar();
                // KDV tutarını açıklamadan çıkar
                String[] parts = gelir.getAciklama().split("KDV: ");
                if (parts.length > 1) {
                    String kdvStr = parts[1].replace(" TL", "");
                    toplamRezervasyonKDV += Double.parseDouble(kdvStr);
                }
            }
        }
        
        // Gelir Özeti
        System.out.println("\nGelir Özeti:");
        System.out.println("Kiralama Geliri (KDV Hariç): " + (toplamKiralamaGeliri - toplamKiralamaKDV) + " TL");
        System.out.println("Kiralama KDV: " + toplamKiralamaKDV + " TL");
        System.out.println("Toplam Kiralama Geliri (KDV Dahil): " + toplamKiralamaGeliri + " TL");
        System.out.println("Depozito Geliri: " + toplamDepozitoGeliri + " TL");
        System.out.println("Rezervasyon Geliri (KDV Hariç): " + (toplamRezervasyonGeliri - toplamRezervasyonKDV) + " TL");
        System.out.println("Rezervasyon KDV: " + toplamRezervasyonKDV + " TL");
        System.out.println("Toplam Rezervasyon Geliri (KDV Dahil): " + toplamRezervasyonGeliri + " TL");
        System.out.println("Toplam Gelir (KDV Dahil): " + (toplamKiralamaGeliri + toplamDepozitoGeliri + toplamRezervasyonGeliri) + " TL");
        
        // Giderler
        System.out.println("\nGiderler:");
        List<Gider> giderler = muhasebe.tarihAraligindakiGiderler(baslangic, bitis);
        for (Gider gider : giderler) {
            System.out.println(gider.getTarih() + " - " + gider.getAciklama() + ": " + gider.getMiktar() + " TL");
        }
        
        // Özet
        System.out.println("\nGenel Özet:");
        System.out.println("Toplam Gelir: " + muhasebe.getToplamGelir() + " TL");
        System.out.println("Toplam Gider: " + muhasebe.getToplamGider() + " TL");
        System.out.println("Net Kar: " + muhasebe.netKarHesapla() + " TL");
    }

    // Destek ekibi üyesi ekleme
    public void destekEkibiUyesiEkle(DestekEkibi uye) {
        destekEkibi.add(uye);
    }

    // Destek ekibi üyesi silme
    public void destekEkibiUyesiSil(DestekEkibi uye) {
        destekEkibi.remove(uye);
    }

    // Destek ekibi üyesine görev atama
    public void gorevAta(DestekEkibi uye, String gorev) {
        uye.gorevEkle(gorev);
    }

    // Görevi tamamlandı olarak işaretleme
    public void gorevTamamla(DestekEkibi uye, String gorev) {
        uye.gorevTamamla(gorev);
    }

    // Destek ekibi listesini getirme
    public List<DestekEkibi> getDestekEkibi() {
        return destekEkibi;
    }

    // Belirli bir departmandaki destek ekibi üyelerini getirme
    public List<DestekEkibi> departmanUyeleriniGetir(String departman) {
        List<DestekEkibi> departmanUyeleri = new ArrayList<>();
        for (DestekEkibi uye : destekEkibi) {
            if (uye.getDepartman().equalsIgnoreCase(departman)) {
                departmanUyeleri.add(uye);
            }
        }
        return departmanUyeleri;
    }

    // Destek ekibi raporu oluşturur
    public void destekEkibiRaporuOlustur() {
        System.out.println("\n--- Destek Ekibi Raporu ---");
        System.out.println("Toplam Üye Sayısı: " + destekEkibi.size());
        System.out.println("----------------------------------------");

        // Departman bazlı gruplandırma
        for (String departman : getDepartmanlar()) {
            System.out.println("\n" + departman + " Departmanı:");
            System.out.println("----------------------------------------");
            
            List<DestekEkibi> departmanUyeleri = departmanUyeleriniGetir(departman);
            for (DestekEkibi uye : departmanUyeleri) {
                System.out.println("\nÜye Bilgileri:");
                System.out.println("Ad Soyad: " + uye.getAd() + " " + uye.getSoyad());
                System.out.println("Telefon: " + uye.getTelefon());
                System.out.println("Email: " + uye.getEmail());
                
                System.out.println("\nAktif Görevler:");
                if (uye.getGorevler().isEmpty()) {
                    System.out.println("Aktif görev bulunmamaktadır.");
                } else {
                    for (String gorev : uye.getGorevler()) {
                        System.out.println("- " + gorev);
                    }
                }
                System.out.println("----------------------------------------");
            }
        }
    }

    // Tüm departmanları getirir
    private List<String> getDepartmanlar() {
        List<String> departmanlar = new ArrayList<>();
        for (DestekEkibi uye : destekEkibi) {
            if (!departmanlar.contains(uye.getDepartman())) {
                departmanlar.add(uye.getDepartman());
            }
        }
        return departmanlar;
    }

    public void arabaKirala(Customer musteri, Car araba, LocalDate baslangicTarihi, LocalDate bitisTarihi) {
        if (!araba.isMevcut()) {
            System.out.println("Üzgünüz, bu araç şu anda müsait değil.");
            return;
        }

        if (araba.isBakimGerekliMi()) {
            System.out.println("Üzgünüz, bu araç bakımda olduğu için kiralanamaz.");
            return;
        }

        // Kiralama süresini hesapla
        long kiralamaGunSayisi = ChronoUnit.DAYS.between(baslangicTarihi, bitisTarihi);
        double toplamUcret = araba.getGunlukFiyat() * kiralamaGunSayisi;
        double depozito = araba.getGunlukFiyat() * 2;

        System.out.println("\nKiralama Detayları:");
        System.out.println("Araç: " + araba.getMarka() + " " + araba.getModel());
        System.out.println("Kiralama Süresi: " + kiralamaGunSayisi + " gün");
        System.out.println("Günlük Ücret: " + araba.getGunlukFiyat() + " TL");
        System.out.println("Toplam Ücret: " + toplamUcret + " TL");
        System.out.println("Depozito: " + depozito + " TL");
        System.out.println("Toplam Ödeme: " + (toplamUcret + depozito) + " TL");

        System.out.print("\nKiralama işlemini onaylıyor musunuz? (E/H): ");
        Scanner scanner = new Scanner(System.in);
        String onay = scanner.nextLine().toUpperCase();

        if (onay.equals("E")) {
            araba.setMevcut(false);
            musteri.harcamaEkle(toplamUcret + depozito);
            
            // Kiralama gelirini muhasebeye ekle
            muhasebe.gelirEkle(new Gelir(
                "Araç Kiralama - " + araba.getMarka() + " " + araba.getModel() + 
                " (" + kiralamaGunSayisi + " gün)",
                toplamUcret,
                LocalDate.now(),
                GelirTuru.KIRALAMA
            ));
            
            // Depozito gelirini muhasebeye ekle
            muhasebe.gelirEkle(new Gelir(
                "Depozito - " + araba.getMarka() + " " + araba.getModel(),
                depozito,
                LocalDate.now(),
                GelirTuru.DEPOZITO
            ));
            
            System.out.println("\nKiralama işlemi başarıyla tamamlandı!");
            
            // E-posta bildirimi gönder
            System.out.println("\nE-posta Bildirimi:");
            System.out.println("Sayın " + musteri.getAd() + " " + musteri.getSoyad() + ",");
            System.out.println("Kiralama işleminiz başarıyla tamamlanmıştır.");
            System.out.println("Kiralama Detayları:");
            System.out.println("- Araç: " + araba.getMarka() + " " + araba.getModel());
            System.out.println("- Kiralama Tarihi: " + baslangicTarihi);
            System.out.println("- İade Tarihi: " + bitisTarihi);
            System.out.println("- Toplam Ücret: " + toplamUcret + " TL");
            System.out.println("- Depozito: " + depozito + " TL");
            System.out.println("\nBizi tercih ettiğiniz için teşekkür ederiz!");
            System.out.println("İyi yolculuklar dileriz!");
        } else {
            System.out.println("Kiralama işlemi iptal edildi.");
        }
    }
} 