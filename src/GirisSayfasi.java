// GirisSayfasi.java
// Kullanıcıdan giriş veya kayıt işlemlerini alır.
import java.util.Scanner;
import java.time.LocalDate;

public class GirisSayfasi {
    private RentACarSystem sistem;
    private Scanner scanner;

    public GirisSayfasi(RentACarSystem sistem) {
        this.sistem = sistem;
        this.scanner = new Scanner(System.in);
    }

    public void sayfayiBaslat() {
        sistem.musterileriDosyadanYukle();
        sistem.arabalarDosyadanYukle();
        // Örnek veri kaydı (ilk çalışmada bir müşteri ve bir araç eklenir)
        if (sistem.getMusteriler().isEmpty()) {
            sistem.musteriEkle(new Customer("Ali", "Veli", "12345678901", "05551234567", "ali@eposta.com", "İstanbul", "E123456", "B", java.time.LocalDate.of(1990,1,1), "1234"));
        }
        if (sistem.getArabalar().isEmpty()) {
            sistem.arabaEkle(new Car("Toyota", "Corolla", 2020, "34ABC123", 500.0, "Beyaz", "Otomatik", "Benzin", 25000, false, false));
        }

        // Örnek destek ekibi üyeleri ekle
        if (sistem.getDestekEkibi().isEmpty()) {
            DestekEkibi uye1 = new DestekEkibi("DE001", "Ahmet", "Yılmaz", "5551234567", "ahmet@example.com", "Müşteri Hizmetleri", "destek123");
            DestekEkibi uye2 = new DestekEkibi("DE002", "Mehmet", "Demir", "5559876543", "mehmet@example.com", "Teknik Destek", "destek123");
            sistem.destekEkibiUyesiEkle(uye1);
            sistem.destekEkibiUyesiEkle(uye2);
        }

        while (true) {
            System.out.println("\n--- Rent A Car Sistemi ---");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Kayıt Ol");
            System.out.println("3. Yönetici Girişi");
            System.out.println("4. Destek Ekibi Girişi");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    sistem.musterileriDosyadanYukle();
                    sistem.arabalarDosyadanYukle();
                    kullaniciGirisi();
                    break;
                case "2":
                    kayitOl();
                    sistem.musterileriDosyadanYukle();
                    break;
                case "3":
                    yoneticiGirisi();
                    sistem.arabalarDosyadanYukle();
                    break;
                case "4":
                    destekEkibiGirisi();
                    break;
                case "5":
                    System.out.println("Çıkılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void kullaniciGirisi() {
        System.out.print("TC Kimlik No: ");
        String tc = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();
        for (Customer c : sistem.getMusteriler()) {
            if (c.getTcKimlikNo().equals(tc) && c.getSifre().equals(sifre)) {
                System.out.println("Hoşgeldiniz, " + c.getAd() + " " + c.getSoyad());
                KullaniciPaneli panel = new KullaniciPaneli(sistem, c, scanner);
                panel.paneliBaslat();
                return;
            }
        }
        System.out.println("Hatalı giriş!");
    }

    private void kayitOl() {
        System.out.print("Ad: ");
        String ad = scanner.nextLine();
        System.out.print("Soyad: ");
        String soyad = scanner.nextLine();
        System.out.print("TC Kimlik No: ");
        String tc = scanner.nextLine();
        System.out.print("Telefon: ");
        String tel = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Adres: ");
        String adres = scanner.nextLine();
        System.out.print("Ehliyet No: ");
        String ehliyetNo = scanner.nextLine();
        System.out.print("Ehliyet Sınıfı: ");
        String ehliyetSinifi = scanner.nextLine();
        System.out.print("Doğum Yılı (yyyy): ");
        int yil = Integer.parseInt(scanner.nextLine());
        System.out.print("Doğum Ayı (1-12): ");
        int ay = Integer.parseInt(scanner.nextLine());
        System.out.print("Doğum Günü: ");
        int gun = Integer.parseInt(scanner.nextLine());
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();
        Customer yeni = new Customer(ad, soyad, tc, tel, email, adres, ehliyetNo, ehliyetSinifi, LocalDate.of(yil, ay, gun), sifre);
        sistem.musteriEkle(yeni);
        System.out.println("Kayıt başarılı! Giriş yapabilirsiniz.");
    }

    private void yoneticiGirisi() {
        System.out.print("Yönetici kullanıcı adı: ");
        String kullanici = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();
        if (kullanici.equals("admin") && sifre.equals("admin123")) {
            System.out.println("Yönetici girişi başarılı!");
            YoneticiPaneli panel = new YoneticiPaneli(sistem, scanner);
            panel.paneliBaslat();
        } else {
            System.out.println("Hatalı yönetici girişi!");
        }
    }

    private void destekEkibiGirisi() {
        System.out.println("\n=== Destek Ekibi Girişi ===");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();
        
        for (DestekEkibi uye : sistem.getDestekEkibi()) {
            if (uye.getId().equals(id) && uye.sifreKontrol(sifre)) {
                System.out.println("Hoşgeldiniz, " + uye.getAd() + " " + uye.getSoyad());
                DestekEkibiPaneli panel = new DestekEkibiPaneli(sistem, uye, scanner);
                panel.paneliBaslat();
                return;
            }
        }
        System.out.println("Hatalı giriş!");
    }
} 