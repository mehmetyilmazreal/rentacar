// KullaniciPaneli.java
// Kullanıcı işlemleri menüsü.
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class KullaniciPaneli {
    private RentACarSystem sistem;
    private Customer musteri;
    private Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public KullaniciPaneli(RentACarSystem sistem, Customer musteri, Scanner scanner) {
        this.sistem = sistem;
        this.musteri = musteri;
        this.scanner = scanner;
    }

    public void paneliBaslat() {
        while (true) {
            System.out.println("\n--- Kullanıcı Paneli ---");
            System.out.println("1. Araçları Listele");
            System.out.println("2. Araç Kirala");
            System.out.println("3. Aktif Kiralamalarım");
            System.out.println("4. Profil Bilgilerim");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    araclariListele();
                    break;
                case "2":
                    aracKirala();
                    break;
                case "3":
                    aktifKiralamalarim();
                    break;
                case "4":
                    profilBilgilerim();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void araclariListele() {
        System.out.println("\n--- Mevcut Araçlar ---");
        int i = 1;
        for (Car c : sistem.getArabalar()) {
            System.out.println(i + ". " +
                "Marka: " + c.getMarka() +
                ", Model: " + c.getModel() +
                ", Yıl: " + c.getYil() +
                ", Plaka: " + c.getPlaka() +
                ", Günlük Fiyat: " + c.getGunlukFiyat() + " TL" +
                ", Renk: " + c.getRenk() +
                ", Vites: " + c.getVites() +
                ", Yakıt: " + c.getYakit() +
                ", Kilometre: " + c.getKilometre() +
                ", Kış Lastiği: " + (c.isKisMit() ? "Var" : "Yok") +
                ", Bakım Gerekli: " + (c.isBakimGerekliMi() ? "Evet" : "Hayır")
            );
            i++;
        }
    }

    private void aracKirala() {
        // Tarih seçimi
        LocalDate baslangicTarihi = tarihSec("Başlangıç tarihi (GG/AA/YYYY): ");
        LocalDate bitisTarihi = tarihSec("Bitiş tarihi (GG/AA/YYYY): ");
        
        if (bitisTarihi.isBefore(baslangicTarihi)) {
            System.out.println("Bitiş tarihi başlangıç tarihinden önce olamaz!");
            return;
        }

        // Müsait araçları listele
        List<Car> müsaitArabalar = sistem.müsaitArabalariGetir(baslangicTarihi, bitisTarihi);
        
        if (müsaitArabalar.isEmpty()) {
            System.out.println("Seçilen tarih aralığında müsait araç bulunmamaktadır.");
            return;
        }

        System.out.println("\n--- Müsait Araçlar (" + baslangicTarihi.format(DATE_FORMATTER) + " - " + bitisTarihi.format(DATE_FORMATTER) + ") ---");
        int i = 1;
        for (Car c : müsaitArabalar) {
            System.out.println(i + ". " +
                "Marka: " + c.getMarka() +
                ", Model: " + c.getModel() +
                ", Yıl: " + c.getYil() +
                ", Plaka: " + c.getPlaka() +
                ", Günlük Fiyat: " + c.getGunlukFiyat() + " TL" +
                ", Renk: " + c.getRenk() +
                ", Vites: " + c.getVites() +
                ", Yakıt: " + c.getYakit()
            );
            i++;
        }

        System.out.print("Kiralamak istediğiniz aracın numarası: ");
        int secim = Integer.parseInt(scanner.nextLine());
        if (secim < 1 || secim > müsaitArabalar.size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }

        Car secili = müsaitArabalar.get(secim - 1);
        long gunSayisi = java.time.temporal.ChronoUnit.DAYS.between(baslangicTarihi, bitisTarihi) + 1;
        System.out.print("Ödeme yöntemi (Nakit/Kredi Kartı/Banka Kartı): ");
        String odemeYontemi = scanner.nextLine();
        double depozito = secili.getGunlukFiyat() * 0.2;
        double toplam = secili.getGunlukFiyat() * gunSayisi;
        
        sistem.arabaKirala(secili, musteri, baslangicTarihi, bitisTarihi, depozito, "", odemeYontemi);
        System.out.println("Kiralama başarılı! Toplam ücret: " + toplam + " TL, Depozito: " + depozito + " TL, Ödeme Yöntemi: " + odemeYontemi);
    }

    private LocalDate tarihSec(String mesaj) {
        while (true) {
            try {
                System.out.print(mesaj);
                String tarihStr = scanner.nextLine();
                return LocalDate.parse(tarihStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Geçersiz tarih formatı! Lütfen GG/AA/YYYY formatında giriniz.");
            }
        }
    }

    private void aktifKiralamalarim() {
        System.out.println("\n--- Aktif Kiralamalarım ---");
        int i = 1;
        for (Rental r : sistem.getKiralamalar()) {
            if (r.getMusteri().equals(musteri) && r.getDurum() == Durum.AKTIF) {
                System.out.println(i + ". " + r.getAraba().getMarka() + " " + r.getAraba().getModel() + 
                    " - " + r.getBaslangicTarihi().format(DATE_FORMATTER) + " - " + r.getBitisTarihi().format(DATE_FORMATTER));
                i++;
            }
        }
        if (i == 1) System.out.println("Aktif kiralamanız yok.");
    }

    private void profilBilgilerim() {
        System.out.println("\n--- Profil Bilgilerim ---");
        System.out.println("Ad: " + musteri.getAd());
        System.out.println("Soyad: " + musteri.getSoyad());
        System.out.println("TC: " + musteri.getTcKimlikNo());
        System.out.println("Telefon: " + musteri.getTelefon());
        System.out.println("Email: " + musteri.getEmail());
        System.out.println("Adres: " + musteri.getAdres());
        System.out.println("Ehliyet No: " + musteri.getEhliyetNo());
        System.out.println("Ehliyet Sınıfı: " + musteri.getEhliyetSinifi());
        System.out.println("Doğum Tarihi: " + musteri.getDogumTarihi());
        System.out.println("Toplam Harcama: " + musteri.getToplamHarcamasi());
        System.out.println("Sadık Müşteri: " + (musteri.isSadikMusteri() ? "Evet" : "Hayır"));
    }
} 