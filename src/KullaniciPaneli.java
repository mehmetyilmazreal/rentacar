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
            System.out.println("5. Destek Ekibi ile İletişim");
            System.out.println("6. Çıkış");
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
                    destekEkibiIletisim();
                    break;
                case "6":
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

        // Kiralama işlemini gerçekleştir
        sistem.arabaKirala(musteri, secili, baslangicTarihi, bitisTarihi);
        
        // İşlem tamamlandıktan sonra otomatik çıkış
        System.out.println("\nAna menüye dönmek için herhangi bir tuşa basın...");
        scanner.nextLine();
        return;
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

    private void destekEkibiIletisim() {
        System.out.println("\n=== Destek Ekibi ile İletişim ===");
        
        // Destek ekibini listele
        List<DestekEkibi> destekEkibi = sistem.getDestekEkibi();
        if (destekEkibi.isEmpty()) {
            System.out.println("Şu anda müsait destek ekibi üyesi bulunmamaktadır.");
            return;
        }

        System.out.println("Müsait Destek Ekibi Üyeleri:");
        for (int i = 0; i < destekEkibi.size(); i++) {
            DestekEkibi uye = destekEkibi.get(i);
            System.out.println((i + 1) + ". " + uye.getAd() + " " + uye.getSoyad() + " - " + uye.getDepartman());
        }

        System.out.print("İletişim kurmak istediğiniz destek ekibi üyesinin numarasını girin (0: İptal): ");
        int secim = Integer.parseInt(scanner.nextLine());
        
        if (secim == 0) return;
        if (secim < 1 || secim > destekEkibi.size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }

        DestekEkibi secilenUye = destekEkibi.get(secim - 1);
        DestekSohbeti sohbet = secilenUye.getSohbet(musteri.getTcKimlikNo());
        
        if (sohbet == null) {
            sohbet = secilenUye.yeniSohbetBaslat(musteri.getTcKimlikNo());
            System.out.println("Yeni destek talebi oluşturuldu.");
        }

        System.out.println("\n=== Sohbet ===");
        System.out.println("Destek ekibi üyesi ile sohbet başlatıldı.");
        
        // Tüm mesaj geçmişini göster
        List<ChatMesaji> mesajGecmisi = sohbet.getMesajGecmisi();
        for (ChatMesaji mesaj : mesajGecmisi) {
            String gonderen = mesaj.getGonderen().equals(musteri.getTcKimlikNo()) ? "Siz" : "Destek Ekibi";
            String zaman = mesaj.getZaman().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            System.out.printf("\n[%s] %s:\n%s\n", zaman, gonderen, mesaj.getMesaj());
        }

        System.out.println("\nMesajınızı yazın (çıkmak için 'q' yazın):");

        while (true) {
            // Okunmamış mesajları göster
            List<ChatMesaji> okunmamisMesajlar = sohbet.getOkunmamisMesajlar(musteri.getTcKimlikNo());
            if (!okunmamisMesajlar.isEmpty()) {
                for (ChatMesaji mesaj : okunmamisMesajlar) {
                    String zaman = mesaj.getZaman().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    System.out.printf("\n[%s] Destek Ekibi:\n%s\n", zaman, mesaj.getMesaj());
                }
            }

            // Kullanıcı mesajını al
            System.out.print("\nMesajınız: ");
            String mesaj = scanner.nextLine();
            
            if (mesaj.equalsIgnoreCase("q")) {
                System.out.println("Sohbet sonlandırıldı.");
                break;
            }

            // Mesajı gönder
            sohbet.mesajGonder(musteri.getTcKimlikNo(), mesaj);
            String zaman = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            System.out.printf("\n[%s] Siz:\n%s\n", zaman, mesaj);
            System.out.println("Mesajınız gönderildi.");
        }
    }
} 