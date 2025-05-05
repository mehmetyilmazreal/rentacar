// YoneticiPaneli.java
// Yönetici işlemleri menüsü.
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class YoneticiPaneli {
    private RentACarSystem sistem;
    private Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public YoneticiPaneli(RentACarSystem sistem, Scanner scanner) {
        this.sistem = sistem;
        this.scanner = scanner;
    }

    public void paneliBaslat() {
        int secim;
        do {
            System.out.println("\n=== Yönetici Paneli ===");
            System.out.println("1. Araç İşlemleri");
            System.out.println("2. Müşteri İşlemleri");
            System.out.println("3. Kiralama İşlemleri");
            System.out.println("4. Rezervasyon İşlemleri");
            System.out.println("5. Muhasebe İşlemleri");
            System.out.println("6. Destek Ekibi Yönetimi");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            
            secim = scanner.nextInt();
            scanner.nextLine();
            
            switch (secim) {
                case 1:
                    araçIslemleri();
                    break;
                case 2:
                    musteriIslemleri();
                    break;
                case 3:
                    kiralamaIslemleri();
                    break;
                case 4:
                    rezervasyonIslemleri();
                    break;
                case 5:
                    muhasebeIslemleri();
                    break;
                case 6:
                    destekEkibiYonetimi();
                    break;
                case 0:
                    System.out.println("Yönetici panelinden çıkılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (secim != 0);
    }

    private void destekEkibiYonetimi() {
        int secim;
        do {
            System.out.println("\n=== Destek Ekibi Yönetimi ===");
            System.out.println("1. Destek Ekibi Üyesi Ekle");
            System.out.println("2. Destek Ekibi Üyesi Sil");
            System.out.println("3. Görev Ata");
            System.out.println("4. Görev Tamamla");
            System.out.println("5. Destek Ekibi Raporu");
            System.out.println("0. Ana Menüye Dön");
            System.out.print("Seçiminiz: ");
            
            secim = scanner.nextInt();
            scanner.nextLine();
            
            switch (secim) {
                case 1:
                    destekEkibiUyesiEkle();
                    break;
                case 2:
                    destekEkibiUyesiSil();
                    break;
                case 3:
                    gorevAta();
                    break;
                case 4:
                    gorevTamamla();
                    break;
                case 5:
                    sistem.destekEkibiRaporuOlustur();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (secim != 0);
    }

    private void destekEkibiUyesiEkle() {
        System.out.println("\n=== Yeni Destek Ekibi Üyesi Ekle ===");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Ad: ");
        String ad = scanner.nextLine();
        System.out.print("Soyad: ");
        String soyad = scanner.nextLine();
        System.out.print("Telefon: ");
        String telefon = scanner.nextLine();
        System.out.print("E-posta: ");
        String email = scanner.nextLine();
        System.out.print("Departman: ");
        String departman = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();
        
        DestekEkibi yeniUye = new DestekEkibi(id, ad, soyad, telefon, email, departman, sifre);
        sistem.destekEkibiUyesiEkle(yeniUye);
        System.out.println("Yeni destek ekibi üyesi başarıyla eklendi!");
    }

    private void destekEkibiUyesiSil() {
        System.out.println("\n=== Destek Ekibi Üyesi Sil ===");
        List<DestekEkibi> uyeler = sistem.getDestekEkibi();
        
        if (uyeler.isEmpty()) {
            System.out.println("Sistemde kayıtlı destek ekibi üyesi bulunmamaktadır.");
            return;
        }
        
        System.out.println("Mevcut Üyeler:");
        for (int i = 0; i < uyeler.size(); i++) {
            DestekEkibi uye = uyeler.get(i);
            System.out.println((i + 1) + ". " + uye.getAd() + " " + uye.getSoyad() + " - " + uye.getDepartman());
        }
        
        System.out.print("Silmek istediğiniz üyenin numarasını girin (0: İptal): ");
        int secim = scanner.nextInt();
        scanner.nextLine();
        
        if (secim > 0 && secim <= uyeler.size()) {
            sistem.destekEkibiUyesiSil(uyeler.get(secim - 1));
            System.out.println("Üye başarıyla silindi!");
        } else if (secim != 0) {
            System.out.println("Geçersiz seçim!");
        }
    }

    private void gorevAta() {
        System.out.println("\n=== Görev Ata ===");
        List<DestekEkibi> uyeler = sistem.getDestekEkibi();
        
        if (uyeler.isEmpty()) {
            System.out.println("Sistemde kayıtlı destek ekibi üyesi bulunmamaktadır.");
            return;
        }
        
        System.out.println("Mevcut Üyeler:");
        for (int i = 0; i < uyeler.size(); i++) {
            DestekEkibi uye = uyeler.get(i);
            System.out.println((i + 1) + ". " + uye.getAd() + " " + uye.getSoyad() + " - " + uye.getDepartman());
        }
        
        System.out.print("Görev atamak istediğiniz üyenin numarasını girin (0: İptal): ");
        int secim = scanner.nextInt();
        scanner.nextLine();
        
        if (secim > 0 && secim <= uyeler.size()) {
            System.out.print("Görev açıklamasını girin: ");
            String gorev = scanner.nextLine();
            sistem.gorevAta(uyeler.get(secim - 1), gorev);
            System.out.println("Görev başarıyla atandı!");
        } else if (secim != 0) {
            System.out.println("Geçersiz seçim!");
        }
    }

    private void gorevTamamla() {
        System.out.println("\n=== Görev Tamamla ===");
        List<DestekEkibi> uyeler = sistem.getDestekEkibi();
        
        if (uyeler.isEmpty()) {
            System.out.println("Sistemde kayıtlı destek ekibi üyesi bulunmamaktadır.");
            return;
        }
        
        System.out.println("Mevcut Üyeler:");
        for (int i = 0; i < uyeler.size(); i++) {
            DestekEkibi uye = uyeler.get(i);
            System.out.println((i + 1) + ". " + uye.getAd() + " " + uye.getSoyad() + " - " + uye.getDepartman());
        }
        
        System.out.print("Görevini tamamlamak istediğiniz üyenin numarasını girin (0: İptal): ");
        int secim = scanner.nextInt();
        scanner.nextLine();
        
        if (secim > 0 && secim <= uyeler.size()) {
            DestekEkibi uye = uyeler.get(secim - 1);
            List<String> gorevler = uye.getGorevler();
            
            if (gorevler.isEmpty()) {
                System.out.println("Seçilen üyenin aktif görevi bulunmamaktadır.");
                return;
            }
            
            System.out.println("Aktif Görevler:");
            for (int i = 0; i < gorevler.size(); i++) {
                System.out.println((i + 1) + ". " + gorevler.get(i));
            }
            
            System.out.print("Tamamlanan görevin numarasını girin (0: İptal): ");
            int gorevSecim = scanner.nextInt();
            scanner.nextLine();
            
            if (gorevSecim > 0 && gorevSecim <= gorevler.size()) {
                sistem.gorevTamamla(uye, gorevler.get(gorevSecim - 1));
                System.out.println("Görev başarıyla tamamlandı olarak işaretlendi!");
            } else if (gorevSecim != 0) {
                System.out.println("Geçersiz seçim!");
            }
        } else if (secim != 0) {
            System.out.println("Geçersiz seçim!");
        }
    }

    private void muhasebeIslemleri() {
        while (true) {
            System.out.println("\n--- Muhasebe İşlemleri ---");
            System.out.println("1. Gider Ekle");
            System.out.println("2. Muhasebe Raporu Oluştur");
            System.out.println("3. Geri Dön");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    giderEkle();
                    break;
                case "2":
                    muhasebeRaporuOlustur();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void giderEkle() {
        System.out.println("\n--- Gider Ekle ---");
        System.out.print("Gider Açıklaması: ");
        String aciklama = scanner.nextLine();
        
        System.out.print("Gider Miktarı (TL): ");
        double miktar = Double.parseDouble(scanner.nextLine());
        
        System.out.println("Gider Türü:");
        System.out.println("1. Bakım");
        System.out.println("2. Yakıt");
        System.out.println("3. Sigorta");
        System.out.println("4. Personel");
        System.out.println("5. Kira");
        System.out.println("6. Diğer");
        System.out.print("Seçiminiz: ");
        int turSecim = Integer.parseInt(scanner.nextLine());
        
        GiderTuru tur;
        switch (turSecim) {
            case 1: tur = GiderTuru.BAKIM; break;
            case 2: tur = GiderTuru.YAKIT; break;
            case 3: tur = GiderTuru.SIGORTA; break;
            case 4: tur = GiderTuru.PERSONEL; break;
            case 5: tur = GiderTuru.KIRA; break;
            case 6: tur = GiderTuru.DIGER; break;
            default:
                System.out.println("Geçersiz seçim!");
                return;
        }
        
        sistem.giderEkle(aciklama, miktar, tur);
        System.out.println("Gider başarıyla eklendi!");
    }

    private void muhasebeRaporuOlustur() {
        System.out.println("\n--- Muhasebe Raporu Oluştur ---");
        LocalDate baslangic = tarihSec("Başlangıç tarihi (GG/AA/YYYY): ");
        LocalDate bitis = tarihSec("Bitiş tarihi (GG/AA/YYYY): ");
        
        if (bitis.isBefore(baslangic)) {
            System.out.println("Bitiş tarihi başlangıç tarihinden önce olamaz!");
            return;
        }
        
        sistem.muhasebeRaporuOlustur(baslangic, bitis);
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

    private void araçIslemleri() {
        while (true) {
            System.out.println("\n--- Araç İşlemleri ---");
            System.out.println("1. Araç Ekle");
            System.out.println("2. Araçları Listele");
            System.out.println("3. Araç Sil");
            System.out.println("4. Geri Dön");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    aracEkle();
                    break;
                case "2":
                    araclariListele();
                    break;
                case "3":
                    arabaSil();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void musteriIslemleri() {
        while (true) {
            System.out.println("\n--- Müşteri İşlemleri ---");
            System.out.println("1. Müşterileri Listele");
            System.out.println("2. Geri Dön");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    musterileriListele();
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void kiralamaIslemleri() {
        // Kiralama işlemleri için gerekli kodlar burada eklenecek
    }

    private void rezervasyonIslemleri() {
        // Rezervasyon işlemleri için gerekli kodlar burada eklenecek
    }

    private void aracEkle() {
        System.out.print("Marka: ");
        String marka = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Yıl: ");
        int yil = Integer.parseInt(scanner.nextLine());
        System.out.print("Plaka: ");
        String plaka = scanner.nextLine();
        System.out.print("Günlük Fiyat: ");
        double fiyat = Double.parseDouble(scanner.nextLine());
        System.out.print("Renk: ");
        String renk = scanner.nextLine();
        System.out.print("Vites: ");
        String vites = scanner.nextLine();
        System.out.print("Yakıt: ");
        String yakit = scanner.nextLine();
        System.out.print("Kilometre: ");
        int km = Integer.parseInt(scanner.nextLine());
        System.out.print("Kış Lastiği Var mı? (true/false): ");
        boolean kisMit = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Bakım Gerekli mi? (true/false): ");
        boolean bakim = Boolean.parseBoolean(scanner.nextLine());
        Car yeni = new Car(marka, model, yil, plaka, fiyat, renk, vites, yakit, km, kisMit, bakim);
        sistem.arabaEkle(yeni);
        System.out.println("Araç başarıyla eklendi!");
    }

    private void araclariListele() {
        System.out.println("\n--- Araçlar ---");
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

    private void musterileriListele() {
        System.out.println("\n--- Müşteriler ---");
        int i = 1;
        for (Customer c : sistem.getMusteriler()) {
            System.out.println(i + ". " + c.getAd() + " " + c.getSoyad() + " - TC: " + c.getTcKimlikNo() + " - Tel: " + c.getTelefon());
            i++;
        }
    }

    private void arabaSil() {
        araclariListele();
        System.out.print("Silmek istediğiniz aracın numarasını girin: ");
        int secim = Integer.parseInt(scanner.nextLine());
        if (secim < 1 || secim > sistem.getArabalar().size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }
        Car silinecek = sistem.getArabalar().get(secim - 1);
        sistem.arabaSil(silinecek);
        System.out.println("Araç başarıyla silindi!");
    }
} 